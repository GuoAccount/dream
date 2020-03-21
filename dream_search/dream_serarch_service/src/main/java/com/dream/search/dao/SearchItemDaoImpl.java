package com.dream.search.dao;

import com.dream.common.pojo.SearchItem;
import com.dream.common.pojo.SearchResult;
import com.dream.search.dao.SearchItemDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchItemDaoImpl implements SearchItemDao {
    @Autowired
    private SolrServer solrServer;

    @Override
    public SearchResult search(SolrQuery query) throws Exception {
        //根据SolrQuery搜索索引库
        QueryResponse response = solrServer.query(query);
        //取查询结果
        SolrDocumentList list = response.getResults();
        //商品列表
        List<SearchItem> itemList = new ArrayList<>();
        //数据处理
        for (SolrDocument document : list) {
            //把查询结果的内容一一封装到itemList
            SearchItem item = new SearchItem();
            item.setId((String) document.get("id"));
            item.setCategoryName((String) document.get("item_category_name"));
            item.setImage((String) document.get("item_image"));
            item.setPrice((Long) document.get("item_price"));
            item.setSellPoint((String) document.get("item_sell_point"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //得到高亮显示的title
            List<String> titleList = highlighting.get(document.get("id")).get("item_title");
            String itemTitle = "";
            //有高亮显示的内容时
            if (itemList != null && itemList.size() > 0) {
                //高亮现实的title不止一个，默认值取第一个
                itemTitle = titleList.get(0);

            } else {
                //如果没有高亮显示的内容
                itemTitle=(String)document.get("item_title");
            }
            item.setTitle(itemTitle);
            //把封装好的商品添加到集合
            itemList.add(item);

        }
        //返回SearchResult对象
        SearchResult searchResult = new SearchResult();
        searchResult.setItemList(itemList);
        //总记录数
        searchResult.setTotalNum(list.getNumFound());
        return searchResult;
    }
}
