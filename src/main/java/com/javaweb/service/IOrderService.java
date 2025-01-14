package com.javaweb.service;

import com.javaweb.entity.OrderEntity;

import java.util.List;

public interface IOrderService {
    List<OrderEntity> getOrdersForCurrentUser();
}
