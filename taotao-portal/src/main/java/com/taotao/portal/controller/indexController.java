package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by h on 2017-03-02.
 */

public class indexController {

    @RequestMapping("/index")
    public String showIndex(){
        return "index";
    }
}
