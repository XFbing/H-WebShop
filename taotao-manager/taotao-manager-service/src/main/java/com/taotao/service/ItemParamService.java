package com.taotao.service;

import com.taotao.common.utils.EasyUIResult;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * Created by h on 2017-03-01.
 */
public interface ItemParamService {
    TaotaoResult getItemParamByCid(long cid);

    TaotaoResult inserItemParam(TbItemParam itemParam);

    EasyUIResult getItemParamList(Integer page, Integer rows) throws Exception;
}
