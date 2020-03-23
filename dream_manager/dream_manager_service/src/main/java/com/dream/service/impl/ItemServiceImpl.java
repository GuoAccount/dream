package com.dream.service.impl;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.common.util.UUIDUtils;
import com.dream.jedis.JedisClient;
import com.dream.mapper.TbItemDescMapper;
import com.dream.mapper.TbItemMapper;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbItemDesc;
import com.dream.pojo.TbItemExample;
import com.dream.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Destination destination;

    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEM_INFO_KEY}")
    private String ITEM_INFO_KEY;
    @Value("${ITEM_INFO_KEY_EXPIRE}")
    private Integer ITEM_INFO_KEY_EXPIRE;

    //发送MQ消息同步索引库
    private void sendMQMessage(String itemId){
        //添加完之后发送一个MQ消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(itemId);
            }
        });
    }


    public TbItemDesc getItemDescById(Long itemId) {
        //查询缓存
        //添加缓存是不能影响到现在的业务
        String jsonString = jedisClient.get(ITEM_INFO_KEY + ":" + itemId + "BASE");
        //得到的数据不为空，说明有缓存，直接返回。
        if(StringUtils.isNoneBlank(jsonString)){
            //设置一下过期时间 8小时 中午12访问了一次 晚上8点过时 如果中间访问了则从最后一次访问8小时候过期
            jedisClient.expire(ITEM_INFO_KEY+":"+itemId+"BASE",ITEM_INFO_KEY_EXPIRE);
            try {
                return JSON.parse(jsonString,TbItemDesc.class);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //逆向工程提供的根据主键查找信息的方法
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        if (tbItemDesc!=null){
            try {
                jedisClient.set(ITEM_INFO_KEY+":"+itemId+"BASE",JSON.json(tbItemDesc));
                jedisClient.expire(ITEM_INFO_KEY+":"+itemId+"BASE",ITEM_INFO_KEY_EXPIRE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tbItemDesc;
    }

    @Override
    public TbItem selectByKey(long itemId) {
        //查询缓存
        //添加缓存是不能影响到现在的业务
        String jsonString = jedisClient.get(ITEM_INFO_KEY + ":" + itemId + "BASE");
        //得到的数据不为空，说明有缓存，直接返回。
        if(StringUtils.isNoneBlank(jsonString)){
            //设置一下过期时间 8小时 中午12访问了一次 晚上8点过时 如果中间访问了则从最后一次访问8小时候过期
            jedisClient.expire(ITEM_INFO_KEY+":"+itemId+"BASE",ITEM_INFO_KEY_EXPIRE);
            try {
                return JSON.parse(jsonString,TbItem.class);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //逆向工程提供的根据主键查找信息的方法
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        if (tbItem!=null){
            try {
                jedisClient.set(ITEM_INFO_KEY+":"+itemId+"BASE",JSON.json(tbItem));
                jedisClient.expire(ITEM_INFO_KEY+":"+itemId+"BASE",ITEM_INFO_KEY_EXPIRE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tbItem;
    }

    @Override
    public EasyUiDataGridResult list(int pageNum, int pageSize) {
        //MyBatis逆向工程生成的代码是咩有提供分页功能，可以使用MyBatis提供的分页插件PageHelper
        //设置分页信息,此方法下的第一个查询会进行分页
        PageHelper.startPage(pageNum,pageSize);
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.setOrderByClause("updated desc");
        // 自动添加limit ?,?  返回的对象实际上包含了很多信息 不止查询结果，还有分页需要用的的信息
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);

        //得到数据的总数 count--total
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItems);
        long total=tbItemPageInfo.getTotal();
        return new EasyUiDataGridResult(total,tbItems);
    }

    @Override
    public DreamResult delete(List<Long> ids) {

        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        //定义删除条件
        criteria.andIdIn(ids);
        tbItemMapper.deleteByExample(tbItemExample);
        //这种方式要多次连接数据库，最好之前写的 直接发送数组到mapper，在mapper使用动态sql进行循环
        /*for(Long id:ids){
            //做删除
            tbItemMapper.deleteByPrimaryKey(id);
        }*/
        //删除之后发送MQ消息
        for(Long id:ids){
            sendMQMessage(id+"");
        }
        return DreamResult.ok();
    }

    @Override
    public DreamResult saveItem(TbItem tbItem, String desc) {
        //1、生成商品id
        long itemId = UUIDUtils.getItenId();
        //2、补全tbItem中要添加的属性
        tbItem.setId(itemId);
        tbItem.setStatus((byte)1);//1 正常
        tbItem.setCreated(new Date());//创建时间
        tbItem.setUpdated(tbItem.getCreated());//第一次修改时间就是创建的时候
        //3、插入到tb_item表中 基本信息表 并不是全部插入，只有 数据不为null的数据才插入
        tbItemMapper.insertSelective(tbItem);//insert方法的区别 insert就是全部插入
        //4、补全商品描述的信息
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(tbItem.getCreated());
        tbItemDesc.setUpdated(tbItem.getUpdated());
        //5、执行增加
        tbItemDescMapper.insertSelective(tbItemDesc);

        //调用发送MQ消息
        sendMQMessage(itemId+"");

        return DreamResult.ok();
    }
}
