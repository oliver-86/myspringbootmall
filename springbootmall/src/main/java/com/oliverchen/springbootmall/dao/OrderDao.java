package com.oliverchen.springbootmall.dao;

import com.oliverchen.springbootmall.dto.OrderQueryParameter;
import com.oliverchen.springbootmall.model.Order;
import com.oliverchen.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItem(Integer orderId, List<OrderItem> orderItemList);

    List<Order> getOrderList(OrderQueryParameter orderQueryParameter);

    Integer getOrderCount(OrderQueryParameter orderQueryParameter);
}
