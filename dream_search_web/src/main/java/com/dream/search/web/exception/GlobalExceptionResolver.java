package com.dream.search.web.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalExceptionResolver implements HandlerExceptionResolver {
    //出异常时，直接返回一个错误界面
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
       //1.将日志从控制台打印出来，写出到日志文件中
        ex.printStackTrace();
        //2.通知开发人员（通过第三方接口）
        //3。返回一个友好的界面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("message","网络异常，请重试。");
        return modelAndView;
    }
}
