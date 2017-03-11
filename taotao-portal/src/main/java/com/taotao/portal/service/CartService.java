package com.taotao.portal.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by h on 2017-03-10.
 */
public interface CartService {
    TaotaoResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);

    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);

    TaotaoResult deleteCartItem(Long itemId,HttpServletRequest request, HttpServletResponse response);
}
