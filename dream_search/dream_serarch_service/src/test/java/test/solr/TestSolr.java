package test.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class TestSolr {

    @Test
    public void testQuery() throws SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.31.12:8080/solr");

        //创建一个Query对象
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("id:test01");
        QueryResponse response = solrServer.query(solrQuery);
        //response得到多个结果
        SolrDocumentList results = response.getResults();
        for(SolrDocument document:results){
            System.out.println(document.getFieldValue("item_title").toString());
        }
    }

    @Test
    public void testDeleteSolr() throws IOException, SolrServerException {
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.31.12:8080/solr");
        //通过id进行删除
        solrServer.deleteById("test01");

        //通过查询其他字段进行删除
        //solrServer.deleteByQuery("item_title:5G");
        solrServer.commit();
    }

    @Test
    public void testAddSolr() throws IOException, SolrServerException {
        //1、创建一个跟solr服务器进行通信的SolrServer--需要指定solr服务的url
        //如果有多个collection,则需要指定要操作的哪一个connection：数据 只有一个可以不指定
        HttpSolrServer solrServer = new HttpSolrServer("http://192.168.31.12:8080/solr");
        //2、创建一个文档对象，要加到索引库的数据的文档
        SolrInputDocument document = new SolrInputDocument();
        //3、向文档中添加业务域，这里的业务区必须有一个id,必须是schema.xml中配置的
        document.addField("id","test01");
        document.addField("item_title","5G手机");
        document.addField("item_sell_point","前沿技术");
        document.addField("item_price",6000);
        document.addField("item_image","http://www.baidu.com");
        document.addField("item_category_name","电子产品");
        document.addField("item_desc","好用！！");
        //4、把文档写入索引库
        solrServer.add(document);
        //5、提交
        solrServer.commit();
    }
}
