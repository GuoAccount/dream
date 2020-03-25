package com.dream.sso.service;

import com.dream.common.pojo.DreamResult;
import com.dream.pojo.TbUser;

public interface UserRegisterService {
    /**
     * 检测昵称是否可用
     * @param param 数据
     * @param type 数据类型 1-username 2-phone 3-email
     * @return
     */
    DreamResult checkUserInfo(String param,Integer type);

    /**
     * 账号注册
     * @param user
     * @return
     */
    DreamResult register(TbUser user);
}
