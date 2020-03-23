package com.dream.search.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchItem;
import com.dream.common.pojo.SearchResult;
import com.dream.search.SearchItemService;
import com.dream.search.dao.SearchItemDao;
import com.dream.search.mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemServiceImpl implements SearchItemService {
    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private SearchItemDao searchItemDao;

    @Override
    public DreamResult importAllItems() throws Exception {
        //1.查询所有的商品的数据
        List<SearchItem> itemList = searchItemMapper.getSearchItemMapper();
        for (SearchItem item : itemList) {
            //2.为每一个商品创建一个SolrInputDocument
            SolrInputDocument document = new SolrInputDocument();
            //3为文档添加业务域
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSellPoint());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategoryName());
            document.addField("item_desc", item.getItemDesc());
            //4.向索引库中添加文档
            solrServer.add(document);
        }
        //提交
        solrServer.commit();
        return DreamResult.ok();
    }

    @Override
    public SearchResult search(String queryString, Integer page, Integer rows) throws Exception {
        //1.先创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //2.设置搜索条件
        solrQuery.setQuery(queryString);
        //3.设置分页条件 查询索引库跟查询数据库的概念上是一样的 start跟limit一样起始的下标
        solrQuery.setStart((page-1)*rows);//（页码-1）*每页条数=起始位置
        //设置每页条数
        solrQuery.setRows(rows);
        //指定默认搜索域
        solrQuery.set("df","item_title");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"coloer:red\">");
        //中间就是高亮的内容
        solrQuery.setHighlightSimplePost("</em>");
        //6.执行查询
        SearchResult result = searchItemDao.search(solrQuery);
        //7.设置总页数
        Long totalNum = result.getTotalNum();
        Long totalPages=totalNum/rows;
        if (totalNum%rows>0){
            totalPages++;
        }
        result.setTotalPages(totalPages);
        return result;
    }

    @Override
    public DreamResult updateSearchItemById(Long id) throws Exception {
        return searchItemDao.updateSearchItemById(id);
    }

}
