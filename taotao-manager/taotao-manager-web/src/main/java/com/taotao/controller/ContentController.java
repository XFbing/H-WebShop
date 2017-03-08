package com.taotao.controller;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by h on 2017-03-04.
 */
@RequestMapping("/content")
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Value("${SEARCH_IMPORT_URL}")
    private String SEARCH_IMPORT_URL;


    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult getContentListById(Integer page, Integer rows,@RequestParam Long categoryId){
        return contentService.getContentListById(page, rows, categoryId);
    }

    @RequestMapping("/save")
    @ResponseBody
    public TaotaoResult insertContent(TbContent content){
        TaotaoResult result = contentService.insertContent(content);
        HttpClientUtil.doGet(SEARCH_BASE_URL+SEARCH_IMPORT_URL+content.getCategoryId());
        return result;
    }

}
