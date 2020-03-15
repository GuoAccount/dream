package com.dream.service.impl;

//import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.common.util.UUIDUtils;
import com.dream.mapper.TbItemMapper;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbItemDesc;
import com.dream.pojo.TbItemExample;
import com.dream.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TbItem selectByKey(long itemId) {
        //逆向工程提供的根据主键查找信息的方法
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public EasyUiDataGridResult list(int pageNum, int pageSize) {
        //MyBatis逆向工程默认没有分页方法 可以使用MyBatis提供的分页插件PageHelper
        //设置分页，此方法下的第一个查询会被分页
        PageHelper.startPage(pageNum,pageSize);
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.setOrderByClause("updated desc");
        //自动添加limit ？，？ 返回的对象实际上包含了很多信息 不只查询结果，还有分页需要用的信息
        List<TbItem> tbItems=tbItemMapper.selectByExample(tbItemExample);
        //得到数据的总数 count--total
        PageInfo<TbItem> tbItemPageInfo=new PageInfo<>(tbItems);
        long total=tbItemPageInfo.getTotal();
        return new EasyUiDataGridResult(total,tbItems);
    }

    @Override
    public DreamResult delete(List<Long> ids) {
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        //定义删除条件
        criteria.andIdIn(ids);
        try{
        tbItemMapper.deleteByExample(tbItemExample);
        }catch (Exception e){
            System.out.println("失败");
            return DreamResult.build(100,"失败",null);
        }
        return DreamResult.ok();
    }

    @Override
    public DreamResult saveItem(TbItem tbItem, String desc) {
        //1.生成的商品Id
        long itemId= UUIDUtils.getItenId();
        //2.补全tbItem中要添加的属性
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);//1正常
        tbItem.setCreated(new Date());//创建时间
        tbItem.setUpdated(tbItem.getCreated());//第一次修改的时间就是创建的时候
        //3.c插入到tb_itm表中 基本信息 并个不是全部插入 只有数据不为NULL的数据才插入
        tbItemMapper.insertSelective(tbItem);//insert方法的区别 inser就是全部插入
        //4.补全商品描述的信息
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(tbItem.getCreated());
        tbItemDesc.setUpdated(tbItem.getUpdated());
        tbItemDesc.setItemId(itemId);
        return DreamResult.ok();
    }

}
