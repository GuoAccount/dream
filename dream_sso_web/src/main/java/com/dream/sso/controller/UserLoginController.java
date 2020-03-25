package com.dream.sso.controller;

import com.dream.common.pojo.DreamResult;
import com.dream.common.util.CookieUtils;
import com.dream.sso.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;
    @Value("${DREAM_TOKEN_KEY}")
    private String DREAM_TOKEN_KEY;
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public DreamResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        //登录
        DreamResult result = userLoginService.login(username, password);
        //如果登陆成功需要把token加入到cookkie中 返回给浏览器
        if (result.getStatus()==200){
            //把token加入到cookie： DREAM_TOKEN_KEY （key）--》》result.getData().toString();（value）
            CookieUtils.setCookie(request,response,DREAM_TOKEN_KEY,result.getData().toString());
        }

        return result;
    }
}
