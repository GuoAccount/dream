package com.dream.content.service.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiTreeNode;
import com.dream.content.service.TbContentCategoryService;
import com.dream.mapper.TbContentCategoryMapper;
import com.dream.pojo.TbContentCategory;
import com.dream.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
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
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            list.add(node);
        }
        return list;
    }

    @Override
    public DreamResult createCategory(Long parentId, String name) {
        //1.先创建TbContentCategory对象，完成所有数据的封装，向数据中插入
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setParentId(parentId);
        tbContentCategory.setName(name);
        //设置 tbContentCategory中其他的数据，新加的数据自己肯定不是父节点
        tbContentCategory.setIsParent(false);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围：大于零的整数
        tbContentCategory.setSortOrder(1);
        //状态。可选值：1正常 2删除
        tbContentCategory.setStatus(1);
        //创建时间和修改时间。刚创建时，修改时间和创建时间一样
        Date date = new Date();
        tbContentCategory.setCreated(date);
        tbContentCategory.setUpdated(tbContentCategory.getCreated());
        //插入增加到数据库中
        tbContentCategoryMapper.insert(tbContentCategory);
        //自动增长的Id会封装到参数对象中
        //3。判断id为partentId的那个分类在添加此子节点之前是否为父节点，如果不是，则改为为父节点
        //现根据partentId来擦回到这个id对应的类别
        TbContentCategory tbContentCategory1 = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!tbContentCategory1.getIsParent()){
            tbContentCategory1.setIsParent(true);
            //更新
            tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory1);
        }
        return DreamResult.ok(tbContentCategory);
    }

    @Override
    public DreamResult deleteContentCategory(Long parentId, Long id, boolean isParentAfterDelete) {
        //1.先删除id的节点
        tbContentCategoryMapper.deleteByPrimaryKey(id);
        //2根据parentId判断是否是父节点如果是，则不变，如果不是则修改
        if (!isParentAfterDelete){
            //如果是true则说明是父节点则父节点信息不需要修改
            TbContentCategory tbContentCategory = new TbContentCategory();
            tbContentCategory.setId(parentId);
            tbContentCategory.setIsParent(isParentAfterDelete);
            tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);

        }
        return DreamResult.ok();
    }

    @Override
    public DreamResult updateContentCategory(Long id, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        tbContentCategory.setUpdated(new Date());
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        return DreamResult.ok();
    }
}
