package com.taotao.search.service;

import com.taotao.common.utils.TaotaoResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by h on 2017-03-07.
 */
public interface ItemService {

    TaotaoResult importAllItems() throws IOException, SolrServerException;
}
