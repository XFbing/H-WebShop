package com.taotao.rest.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by h on 2017-03-03.
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatRestService itemCatRestService;

    @RequestMapping(value = "/itemcat/all",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        CatResult catResult =  itemCatRestService.getItemCatList();
        //将pojo转换成字符串
        String json = JsonUtils.objectToJson(catResult);
        //拼接返回值
        String result = callback + "("+json+")";
        return result;
    }
}
