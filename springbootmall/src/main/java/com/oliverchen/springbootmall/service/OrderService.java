package com.oliverchen.springbootmall.service;

import com.oliverchen.springbootmall.dto.CreateOrderRequest;

public interface OrderService {
    Integer addOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
