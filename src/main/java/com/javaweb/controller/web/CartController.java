package com.javaweb.controller.web;

import com.javaweb.entity.CourseEntity;
import com.javaweb.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller(value = "cartControllerOfWeb")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping(value = "/cart")
    public ModelAndView getCart() {
        ModelAndView mav = new ModelAndView("web/courses/cart");


        mav.addObject("cartItems", cartService.getCartItems());
        mav.addObject("cart", cartService.getCart());
        return mav;
    }
}
