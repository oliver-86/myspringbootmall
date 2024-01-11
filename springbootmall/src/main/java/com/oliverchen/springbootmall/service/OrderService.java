package com.oliverchen.springbootmall.service;

import com.oliverchen.springbootmall.dto.CreateOrderRequest;
import com.oliverchen.springbootmall.dto.OrderQueryParameter;
import com.oliverchen.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Order getOrderById(Integer orderId);
    Integer addOrder(Integer userId, CreateOrderRequest createOrderRequest);
    List<Order> getOrderListByUserId(OrderQueryParameter orderQueryParameter);
    Integer getOrderListCount(OrderQueryParameter orderQueryParameter);
}
