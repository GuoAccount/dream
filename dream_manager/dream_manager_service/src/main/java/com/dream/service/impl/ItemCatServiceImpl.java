package com.dream.service.impl;

import com.dream.common.pojo.EasyUiTreeNode;
import com.dream.mapper.TbItemCatMapper;
import com.dream.pojo.TbItemCat;
import com.dream.pojo.TbItemCatExample;
import com.dream.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<EasyUiTreeNode> getItemCatListByParentId(long parentId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = tbItemCatExample.createCriteria();
        //根据parentId查找
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = tbItemCatMapper.selectByExample(tbItemCatExample);
        List<EasyUiTreeNode> list = new ArrayList<>();
        for (TbItemCat cat:tbItemCats){
            //先创建一个node
            EasyUiTreeNode easyUiTreeNode = new EasyUiTreeNode();
            easyUiTreeNode.setId(cat.getId());
            easyUiTreeNode.setText(cat.getName());
            easyUiTreeNode.setState(cat.getIsParent()?"closed":"open");
            list.add(easyUiTreeNode);
        }
        return list;
    }
}
