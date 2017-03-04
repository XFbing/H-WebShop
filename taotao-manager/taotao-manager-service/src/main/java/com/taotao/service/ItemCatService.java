package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;

import java.util.List;

/**
 * Created by h on 2017-02-27.
 */
public interface ItemCatService {
    List<EUTreeNode> getCatList(long parentId);
}
