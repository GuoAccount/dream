package com.dream.search;

import com.dream.common.pojo.DreamResult;

public interface SearchItemService {
    /**
     * 把商品导入到索引库中
     * @return
     * @throws Exception
     */
    DreamResult importAllItems() throws Exception;
}
