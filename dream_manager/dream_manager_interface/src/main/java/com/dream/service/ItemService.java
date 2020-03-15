package com.dream.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.pojo.TbItem;

import java.util.List;

public interface ItemService {
    /**
     *
     * @param itemId
     * @return
     */
    TbItem selectByKey(long itemId);

    /**
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    EasyUiDataGridResult list(int pageNum, int pageSize);

    /**
     *
     * @param ids
     * @return
     */
    DreamResult delete(List<Long> ids);

    /**
     *
     * @param tbItem
     * @param desc
     * @return
     */
    DreamResult saveItem(TbItem tbItem, String desc);
}
