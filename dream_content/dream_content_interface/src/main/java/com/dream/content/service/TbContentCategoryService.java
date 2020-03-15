package com.dream.content.service;

import com.dream.common.pojo.EasyUiTreeNode;

import java.util.List;

public interface TbContentCategoryService {
    /**
     * 根据父节点查询内容
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getContentCategoryByParentId(Long parentId);
}
