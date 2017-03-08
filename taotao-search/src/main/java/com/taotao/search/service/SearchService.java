package com.taotao.search.service;

import com.taotao.search.pojo.SearchResult;

/**
 * Created by h on 2017-03-07.
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}

