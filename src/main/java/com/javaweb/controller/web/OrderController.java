package com.javaweb.controller.web;

import com.javaweb.entity.OrderEntity;
import com.javaweb.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller(value = "orderControllerOfWeb")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/orders")
    public ModelAndView getOrders() {
        ModelAndView mav = new ModelAndView("web/orders/list");

        // Lấy danh sách đơn hàng từ service
        List<OrderEntity> orders = orderService.getOrdersForCurrentUser();

        // Thêm dữ liệu vào ModelAndView
        mav.addObject("orders", orders);
        return mav;
    }
}