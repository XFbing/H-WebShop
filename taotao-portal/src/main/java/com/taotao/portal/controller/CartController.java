package com.taotao.portal.controller;

import com.taotao.common.utils.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by h on 2017-03-10.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    public String addCarItem(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
        return "redirect:/cart/success.html";
    }

    @RequestMapping("/success")
    public String showSuccess(){
        return "cartSuccess";
    }

   @RequestMapping("/cart")
    public String showCart(HttpServletRequest request, HttpServletResponse response, Model model){
       List<CartItem> list =cartService.getCartItemList(request, response);
       model.addAttribute("cartList", list);
       return "cart";
   }
   @RequestMapping("delete/{itemId}")
    public String deleCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response){
       cartService.deleteCartItem(itemId, request, response);
       return "redirect:/cart/cart.html";
   }

}
