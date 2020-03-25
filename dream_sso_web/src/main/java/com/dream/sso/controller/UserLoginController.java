package com.dream.sso.controller;

import com.alibaba.dubbo.common.json.JSON;
import com.dream.common.pojo.DreamResult;
import com.dream.common.util.CookieUtils;
import com.dream.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserLoginController {
    @Autowired
    private UserLoginService userLoginService;
    @Value("${DREAM_TOKEN_KEY}")
    private String DREAM_TOKEN_KEY;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public DreamResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        //登录
        DreamResult result = userLoginService.login(username, password);
        //如果登陆成功需要把token加入到cookkie中 返回给浏览器
        if (result.getStatus() == 200) {
            //把token加入到cookie： DREAM_TOKEN_KEY （key）--》》result.getData().toString();（value）
            CookieUtils.setCookie(request, response, DREAM_TOKEN_KEY, result.getData().toString());
        }

        return result;
    }

    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token, String callback) throws IOException {
        DreamResult result = userLoginService.getUserByToken(token);
        //返回一个callback处理--也是一个json
        if (StringUtils.isNotBlank(callback)) {
            String jsonResult = callback + "(" + JSON.json(result) + ");";
            return jsonResult;
        }
        return JSON.json(result);

    }
}
