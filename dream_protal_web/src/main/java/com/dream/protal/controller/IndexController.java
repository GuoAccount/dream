package com.dream.protal.controller;

import com.alibaba.fastjson.JSON;
import com.dream.content.service.TbContentService;
import com.dream.pojo.TbContent;
import com.dream.protal.pojo.Ad1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;

    @Value("${AD1_HEIGHT}")
    private String AD1_HEIGHT;

    @Value("${AD1_HEIGHT_B}")
    private String AD1_HEIGHT_B;

    @Value("${AD1_WIDTH}")
    private String AD1_WIDTH;

    @Value("${AD1_WIDTH_B}")
    private String AD1_WIDTH_B;

    @Autowired
    private TbContentService tbContentSercice;


    /**
     * 展示的是首页
     * 访问从web.xml中找index.html，没有找到，最后默认访问/index
     *
     * @return
     */
    @RequestMapping("index")
    public String index(Model model) {
        //查找打广告为
        List<TbContent> tbContents=tbContentSercice.selectContentByCategoryId(AD1_CATEGORY_ID);
        //把此集合转换为Ad1Node泛型的集合
        ArrayList<Ad1Node> ad1Nodes = new ArrayList<>();
        for (TbContent tbContent:tbContents){
            Ad1Node ad1Node = new Ad1Node();
            ad1Node.setAlt(tbContent.getTitle());
            ad1Node.setHref(tbContent.getUrl());
            ad1Node.setSrc(tbContent.getPic());
            ad1Node.setSrc2(tbContent.getPic2());
            ad1Node.setHeight(AD1_HEIGHT);
            ad1Node.setHeight2(AD1_HEIGHT_B);
            ad1Node.setWidth(AD1_WIDTH);
            ad1Node.setWidth2(AD1_WIDTH_B);
            ad1Nodes.add(ad1Node);
        }
        //把数据转换为Json转发给index.jsp
        String jsonString = JSON.toJSONString(ad1Nodes);
        model.addAttribute(jsonString);
        return "index";
    }
}
