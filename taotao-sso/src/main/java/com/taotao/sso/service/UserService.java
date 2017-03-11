package com.taotao.sso.service;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by h on 2017-03-09.
 */
public interface UserService {
    TaotaoResult checkData(String content, Integer type);

    TaotaoResult createUser(TbUser user);

    TaotaoResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);

    TaotaoResult getUserByToken(String token);

    TaotaoResult delUserByToken(String token);
}
