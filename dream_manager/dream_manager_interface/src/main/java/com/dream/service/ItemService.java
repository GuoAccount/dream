package com.dream.service;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.EasyUiDataGridResult;
import com.dream.pojo.TbItem;
import com.dream.pojo.TbItemDesc;

import java.util.List;

public interface ItemService {
    /**
     * 根据商品ID查找商品描述
     * @param itemId
     * @return
     */
    TbItemDesc getItemDescById(Long itemId);
    /**
     *根据商品ID查找商品
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
