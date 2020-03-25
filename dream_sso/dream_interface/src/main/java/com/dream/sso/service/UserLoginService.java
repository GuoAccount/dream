package com.dream.sso.service;

import com.dream.common.pojo.DreamResult;

public interface UserLoginService {
    /**
     * 所有网站都必须支持最基本的账号密码登录
     * @param username
     * @param password
     * @return
     */
    DreamResult login(String username,String password);
}
