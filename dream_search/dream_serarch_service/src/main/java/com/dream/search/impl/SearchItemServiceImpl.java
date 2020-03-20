package com.dream.search.impl;

import com.dream.common.pojo.DreamResult;
import com.dream.common.pojo.SearchItem;
import com.dream.search.SearchItemService;
import com.dream.search.mapper.SearchItemMapper;
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
        solrServer.commit();
        return DreamResult.ok();
    }

}
