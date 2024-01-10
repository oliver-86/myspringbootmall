package com.oliverchen.springbootmall.service;

import com.oliverchen.springbootmall.dto.CreateOrderRequest;
import com.oliverchen.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);
    Integer addOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
