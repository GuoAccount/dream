package com.dream.controller;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.content.service.TbContentService;
import com.dream.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private TbContentService tbContentService;
    @RequestMapping("/query/list")
    public EasyUiDataGridResult list(int page,int rows,long categoryId){
        return tbContentService.list(page,rows,categoryId);
    }
    @RequestMapping("/save")
    public DreamResult save(TbContent tbContent){
        return tbContentService.save(tbContent);
    }
    @RequestMapping("/delete")
    public DreamResult delete(Long[] ids){
        return tbContentService.delete(ids);
    }
    @RequestMapping("/update")
    public DreamResult update(TbContent tbContent){
        return tbContentService.update(tbContent);
    }
}
