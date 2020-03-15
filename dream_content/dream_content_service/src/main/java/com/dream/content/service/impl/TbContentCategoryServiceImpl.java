package com.dream.content.service.impl;

import com.dream.common.pojo.EasyUiTreeNode;
import com.dream.content.service.TbContentCategoryService;
import com.dream.mapper.TbContentCategoryMapper;
import com.dream.pojo.TbContentCategory;
import com.dream.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<EasyUiTreeNode> getContentCategoryByParentId(Long parentId) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);

        //把返回的集合转换成EasyUiTreeNode类型
        ArrayList<EasyUiTreeNode> list = new ArrayList<>();
        for (TbContentCategory tbContentCategory:tbContentCategories){
            EasyUiTreeNode node = new EasyUiTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"close":"open");
            list.add(node);
        }
        return list;
    }
}
