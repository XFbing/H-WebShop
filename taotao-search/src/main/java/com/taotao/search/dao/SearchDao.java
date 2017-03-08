package com.taotao.search.dao;

import com.taotao.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by h on 2017-03-07.
 */
public interface SearchDao{
    SearchResult search(SolrQuery query) throws Exception;
}
