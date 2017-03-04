package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * Created by h on 2017-02-27.
 */
public interface ItemService {
    TbItem getItemById(Long itemId);

    EUDataGridResult getItemList(int page, int rows);

    TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception;

    TaotaoResult updateItem(TbItem item,String desc,String itemParam) throws Exception;

    String getItemParaMHtml(long itemId);

}
