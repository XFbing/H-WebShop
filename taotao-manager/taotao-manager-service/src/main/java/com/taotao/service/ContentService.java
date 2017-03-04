package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * Created by h on 2017-03-03.
 */
public interface ContentService {
    EUDataGridResult getContentListById(Integer page, Integer rows, Long categoryId);
    TaotaoResult insertContent(TbContent content);
}
