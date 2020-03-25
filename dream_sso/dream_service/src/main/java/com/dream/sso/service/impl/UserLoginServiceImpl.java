package com.dream.sso.service.impl;
import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.dream.common.pojo.DreamResult;
import com.dream.mapper.TbUserMapper;
import com.dream.pojo.TbUser;
import com.dream.pojo.TbUserExample;
import com.dream.sso.service.UserLoginService;
import com.dream.sso.service.jedis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${EXPIRE_TIME}")
    private Integer EXPIRE_TIME;
    @Override
    public DreamResult login(String username, String password) {
        //1。校验用户名和密码
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            return DreamResult.build(400,"用户名或密码错误");
        }
        //2.在校验用户名
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if (tbUsers==null||tbUsers.size()==0){
            return DreamResult.build(400,"账户不存在");
        }
        //3.在校验密码--用户名唯一，如果查到了，肯定就一条数据
        TbUser tbUser = tbUsers.get(0);
        //把密码加密之后进行比较
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!md5DigestAsHex.equals(tbUser.getPassword())){
            return DreamResult.build(400,"密码错误");
        }
        //4.如果校验成功--账号密码都正确。登录成功
        //需要把登录信息保存到redis中作为模拟的session--每一个用户登陆的token都必须唯一且随机
        //4.1随机生成一个token
        String token = UUID.randomUUID().toString();
        //4.2把要保持的用户信息中的密码设置为null
        tbUser.setPassword(null);
        //4.3存放用户数据到redis中，使用jedis的客户端，为了方便管理，加一个前缀USER_INFO:token
        try {
            jedisClient.set(USER_INFO+":"+token, JSON.json(tbUser));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置过期时间
        jedisClient.expire(USER_INFO+":"+token,EXPIRE_TIME);
        //在单点登录中，其它系统如果要从redis中查看是否用登录信息，需要一个此token sessionId（在表现层中设置放到cookie中）
        return DreamResult.ok(token);
    }

    @Override
    public DreamResult getUserByToken(String token) {
        //1。直接根据token从redis服务器中进行查询（相当于通过浏览器的sqlSessionId向服务器查找sqlSession）
        String jsonString = jedisClient.get(USER_INFO + ":" + token);
        //2.判断是否查询到
        if (StringUtils.isNoneBlank(jsonString)){//如果有数据那么就是不控的则true
            //3.每一次重新能访问首页，实际上就要重新计时
            jedisClient.expire(USER_INFO+":"+token,EXPIRE_TIME);
            //把查询到的对象返回
            try {
                TbUser user = JSON.parse(jsonString, TbUser.class);
                //返回对象
                return DreamResult.ok(user);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return DreamResult.build(400,"用户已过期");
    }
}
