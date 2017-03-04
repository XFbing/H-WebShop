package com.taotao.rest.service;

import com.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by h on 2017-03-04.
 */
public interface ContentService {
    List<TbContent> getContentList(long contentCid);
}
