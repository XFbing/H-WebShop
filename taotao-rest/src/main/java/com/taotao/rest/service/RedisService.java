package com.taotao.rest.service;

import com.taotao.common.utils.TaotaoResult;

/**
 * Created by h on 2017-03-06.
 */
public interface RedisService {

    TaotaoResult syncContent(long contentCid);


}
