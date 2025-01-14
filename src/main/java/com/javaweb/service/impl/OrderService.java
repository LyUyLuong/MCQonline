package com.javaweb.service.impl;

import com.javaweb.entity.OrderEntity;
import com.javaweb.repository.OrderRepository;
import com.javaweb.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<OrderEntity> getOrdersForCurrentUser() {
        Long userId = SecurityUtils.getPrincipal().getId(); // Lấy ID người dùng hiện tại
        return orderRepository.findByUserId(userId);
    }

    // Lấy chi tiết đơn hàng theo ID
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng với ID: " + id));
    }
}
