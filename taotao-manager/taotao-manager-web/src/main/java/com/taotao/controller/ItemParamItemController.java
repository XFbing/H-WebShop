package com.taotao.controller;

import com.taotao.service.ItemPramItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by h on 2017-03-02.
 */
@Controller
public class ItemParamItemController {

    @Autowired
    private ItemPramItemService itemPramItemService;

    @RequestMapping("/showitem/{itemId}")
    public String showItemParam(@PathVariable long itemId, Model model){
        String string= itemPramItemService.getItemParamByItemId(itemId);
        model.addAttribute("itemParam", string);
        return "item";
    }
}
