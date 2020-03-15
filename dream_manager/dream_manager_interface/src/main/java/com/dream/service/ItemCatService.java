package com.dream.service;

import com.dream.common.pojo.EasyUiTreeNode;

import java.util.List;

public interface ItemCatService {
    /**
     * 查找商品类目返回树形节点集合
     * @param parentId
     * @return
     */
    public List<EasyUiTreeNode> getItemCatListByParentId(long parentId);
}
