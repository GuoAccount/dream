package com.dream.content.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.pojo.TbContent;

import java.util.List;

public interface TbContentService {
    /**
     *
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    EasyUiDataGridResult list(int page, int rows, long categoryId);

    /**
     *
     * @param tbContent
     * @return
     */
    DreamResult save(TbContent tbContent);


    List<TbContent> selectContentByCategoryId(Long ad1_category_id);

    /**
     *
     * @param ids
     * @return
     */
    DreamResult delete(Long[] ids);

    /**
     *
     * @param tbContent
     * @return
     */
    DreamResult update(TbContent tbContent);
}
