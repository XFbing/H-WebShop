package com.taotao.rest.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by h on 2017-03-07.
 */
public class SolrJTest {

    @Test
    public void addDocument() throws Exception{
        //创建一个连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.109.130:8080/solr");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "148895003938230");
        document.addField("item_title","大秦");
        document.addField("item_price", 9999);
        //把文档对象写入到索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws  Exception{
        //创建一个连接
        SolrServer solrServer = new HttpSolrServer("http://192.168.109.130:8080/solr");
       // solrServer.deleteById("test001");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();

    }
}
