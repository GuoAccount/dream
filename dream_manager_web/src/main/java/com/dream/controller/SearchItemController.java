package com.dream.controller;

import com.dream.common.pojo.DreamResult;
import com.dream.search.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;
    @RequestMapping("/index/importAll")
    public DreamResult importAllItems(){
        try {
            return searchItemService.importAllItems();
        } catch (Exception e) {
            e.printStackTrace();
            return DreamResult.build(500,"导入数据失败",null);
        }
    }
}
