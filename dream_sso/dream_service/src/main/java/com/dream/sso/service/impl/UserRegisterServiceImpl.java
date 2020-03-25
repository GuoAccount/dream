package com.dream.sso.service.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.mapper.TbUserMapper;
import com.dream.pojo.TbUser;
import com.dream.pojo.TbUserExample;
import com.dream.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private TbUserMapper tbUserMapper;
    @Override
    public DreamResult checkUserInfo(String param, Integer type) {
        //type不同需要设置不同条件
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        //设置查询条件
        if (type == 1) {
            //检查username
            //username不能为空--前端已经表单验证检测过了，后面也继续做处理
            if (StringUtils.isEmpty(param)){
                //如果是空
                return DreamResult.ok(false);
            }
            //设置参数
            criteria.andUsernameEqualTo(param);
        }else if (type==2){
            //检查phone
            criteria.andPhoneEqualTo(param);
        }else if (type==3){
            criteria.andEmailEqualTo(param);
        }else {
            return DreamResult.build(400,"非法参数");
        }
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        //4.如果查询到数据--注册账号不可用，已经存在false
        if (tbUsers!=null&&tbUsers.size()>0){
            return DreamResult.ok(false);
        }
        //5.如果没有查询到数据,可用
        return DreamResult.ok(true);
    }

    @Override
    public DreamResult register(TbUser user) {
        //1.验证数据
        //1.1用户名和密码不能为空
        if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
            return DreamResult.build(400,"请校验数据后再提交数据");
        }
        //1.2 校验用户名是否被注册
        DreamResult dreamResult = checkUserInfo(user.getUsername(), 1);
        if (!(boolean)dreamResult.getData()) {//false代表不可用 ！false进入if执行任务
            return DreamResult.build(400, "注册失败");
        }
        //1.3手机号码是否被注册
        if (StringUtils.isNoneBlank(user.getPhone())){
            DreamResult result = checkUserInfo(user.getPhone(), 2);
            if (!(boolean)result.getData()){
                return DreamResult.build(400,"注册失败手机号码已被注册");
            }
        }
        if (StringUtils.isNoneBlank(user.getEmail())){
            DreamResult result = checkUserInfo(user.getEmail(), 3);
            if (!(boolean)result.getData()){
                return DreamResult.build(400,"注册失败邮箱已被注册");
            }
        }
        //2.如果校验通过，则补全其他属性
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        //3.对密码进行加密
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5DigestAsHex);
        tbUserMapper.insert(user);
        return DreamResult.ok();
    }
}
