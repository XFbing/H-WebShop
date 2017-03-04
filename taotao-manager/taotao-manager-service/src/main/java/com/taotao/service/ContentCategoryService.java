package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.utils.TaotaoResult;

import java.util.List;

/**
 * Created by h on 2017-03-03.
 */
public interface ContentCategoryService {

    TaotaoResult insertContentCategory(long parentId,String name);

    TaotaoResult deleteContentCategory(Long parentId,Long id);

    TaotaoResult updateContentCategory(long id, String name);

    List<EUTreeNode> getCategoryList(long parentId);

}
