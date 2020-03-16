package com.dream.content.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiTreeNode;

import java.util.List;

public interface TbContentCategoryService {
    /**
     * 根据父节点查询内容
     * @param parentId
     * @return
     */
    List<EasyUiTreeNode> getContentCategoryByParentId(Long parentId);

    /**
     *
     * @param parentId
     * @param name
     * @return
     */
    DreamResult createCategory(Long parentId, String name);
}
