package com.dream.search.dao;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

public interface SearchItemDao {
    /**
     *
     * @param query 搜索的条件
     * @return
     * @throws Exception
     */
    SearchResult search(SolrQuery query) throws Exception;

    /**
     * 根据id更新索引库
     * @param itemId
     * @return
     * @throws Exception
     */
    DreamResult updateSearchItemById(Long itemId) throws  Exception;

}
