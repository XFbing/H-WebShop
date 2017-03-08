package com.taotao.portal.service;

import com.taotao.portal.pojo.SearchResult;

/**
 * Created by h on 2017-03-07.
 */
public interface SearchService {
    SearchResult search(String queryString, int page);
}
