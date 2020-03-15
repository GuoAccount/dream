package com.dream.controller;

import com.dream.common.pojo.EasyUiTreeNode;
import com.dream.content.service.TbContentCategoryService;
import com.dream.pojo.TbContentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class contentCategoryController {
    @Autowired
    private TbContentCategoryService tbContentCategoryService;
    @RequestMapping("/list")
    /**
     *
     */
    public List<EasyUiTreeNode> list(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        return tbContentCategoryService.getContentCategoryByParentId(parentId);
    }
}
