package com.taotao.portal.service;

import com.taotao.pojo.TbUser;

/**
 * Created by h on 2017-03-10.
 */
public interface UserService {
    TbUser getUserByToken(String token);
}
