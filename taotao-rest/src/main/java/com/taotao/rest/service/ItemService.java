package com.taotao.rest.service;

import com.taotao.common.utils.TaotaoResult;

/**
 * Created by h on 2017-03-07.
 */
public interface ItemService {
    TaotaoResult getItemBaseInfo(Long itemId);

    TaotaoResult getItemDesc(long itemId);

    TaotaoResult getItemParam(long itemId);
}
