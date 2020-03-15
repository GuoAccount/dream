package com.dream.protal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * 展示的是首页
     * 访问从web.xml中找index.html，没有找到，最后默认访问/index
     * @return
     */
    @RequestMapping("index")
    public  String index(){
        return "index";
    }
}
