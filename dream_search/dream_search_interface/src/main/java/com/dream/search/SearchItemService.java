package com.dream.search;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchResult;

public interface SearchItemService {
    /**
     * 把商品导入到索引库中
     * @return
     * @throws Exception
     */
    DreamResult importAllItems() throws Exception;

    /**
     *
     * @param queryString
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    SearchResult search(String queryString,Integer page,Integer rows) throws Exception;
    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public DreamResult updateSearchItemById(Long id) throws Exception;
}
