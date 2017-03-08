package com.taotao.search.controller;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.search.service.ItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by h on 2017-03-07.
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 导入商品数据到索引库
     */
    @RequestMapping("/importall")
    @ResponseBody
    public TaotaoResult importAllItems() throws IOException, SolrServerException {

        TaotaoResult result= itemService.importAllItems();
        return result;
    }
}
