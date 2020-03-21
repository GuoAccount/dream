package com.dream.search.controller;

import com.dream.common.pojo.SearchResult;
import com.dream.search.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class SearchItemController {
    @Autowired
    private SearchItemService searchItemService;
    //注入rows 给bean使用setter、注入 注入值 ${spring表达式}
    @Value("${ITEM_ROWS}")
    private Integer ITEM_ROWS;
    @RequestMapping("/search")
    public String search(@RequestParam("q") String queryString,
                         @RequestParam(defaultValue = "1") Integer page,
                         Model model) throws Exception {
         queryString=new String(queryString.getBytes("ISO8859-1"),"UTF-8");
        SearchResult search = searchItemService.search(queryString, page, ITEM_ROWS);
        model.addAttribute("query",queryString);
        model.addAttribute("totalPages",search.getTotalPages());
        model.addAttribute("itemList",search.getItemList());
        model.addAttribute("page",page);
        return "search";
    }
}
