package com.dream.search.dao;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchItem;
import com.dream.common.pojo.SearchResult;
import com.dream.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchItemDaoImpl implements SearchItemDao {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private SearchItemMapper searchItemMapper;


    @Override
    public SearchResult search(SolrQuery query) throws Exception {
        //根据SolrQuery搜索索引库
        QueryResponse response = solrServer.query(query);
        //取查询结果
        SolrDocumentList list = response.getResults();
        //商品列表
        List<SearchItem> itemList = new ArrayList<>();
        //数据处理
        for(SolrDocument document:list){
            //把查询结果的内容取到一一封装到itemList中
            SearchItem item = new SearchItem();
            item.setId((String)document.get("id"));
            item.setCategoryName((String) document.get("item_category_name"));
            item.setImage((String) document.get("item_image"));
            item.setPrice((Long) document.get("item_price"));
            item.setSellPoint((String) document.get("item_sell_point"));

            //取高亮显示
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //得到高亮显示的title
            List<String> titleList = highlighting.get(document.get("id")).get("item_title");

            String itemTitle="";
            //有高亮显示的内容时
            if(titleList!=null && titleList.size()>0){
                //高亮显示的title不止一个，默认只取第一个
                itemTitle=titleList.get(0);
            }else{
                //如果没有高亮显示的内容
                itemTitle= (String) document.get("item_title");
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

    @Override
    public DreamResult updateSearchItemById(Long itemId) throws Exception {
        //1、调用mapper来查询数据库中商品的内容
        SearchItem item = searchItemMapper.getItemById(itemId);
        //2、创建一个SolrDocument
        SolrInputDocument document = new SolrInputDocument();
        //3、向文本中添加域
        document.addField("id",item.getId());
        document.addField("item_title",item.getTitle());
        document.addField("item_sell_point",item.getSellPoint());
        document.addField("item_price",item.getPrice());
        document.addField("item_image",item.getImage());
        document.addField("item_category_name",item.getCategoryName());
        document.addField("item_desc",item.getItemDesc());

        //4、向索引库中添加文档
        solrServer.add(document);
        //5、提交
        solrServer.commit();
        return DreamResult.ok();
    }
}
