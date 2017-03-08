package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

/**
 * Created by h on 2017-03-07.
 */
public interface ItemService {
    ItemInfo getItemById(Long itemId);

    String getItemDescById(Long itemId);

    String getItemParam(Long itemId);
}
