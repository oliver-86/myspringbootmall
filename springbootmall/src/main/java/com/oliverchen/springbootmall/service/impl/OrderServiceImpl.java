package com.oliverchen.springbootmall.service.impl;

import com.oliverchen.springbootmall.dao.OrderDao;
import com.oliverchen.springbootmall.dao.ProductDao;
import com.oliverchen.springbootmall.dto.BuyItem;
import com.oliverchen.springbootmall.dto.CreateOrderRequest;
import com.oliverchen.springbootmall.model.Order;
import com.oliverchen.springbootmall.model.OrderItem;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;
    @Override
    public Integer addOrder(Integer userId, CreateOrderRequest createOrderRequest) {
//        int totalAmount = 0;
//        List<OrderItem> orderItemList  = new ArrayList<>();
//
//        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
//            int quantity = buyItem.getQuantity();
//
//            Product p = productDao.getProductById(buyItem.getProductId());
//            int price = p.getPrice();
//
//            int amount =quantity*price;
//
//            totalAmount += amount;
//
//            //BuyItem to OrderItem
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProductId(p.getProductId());
//            orderItem.setAmount(amount);
//            orderItem.setQuantity(quantity);
//
//            orderItemList.add(orderItem);
//        }
//
//        Integer orderId = orderDao.createOrder(userId,totalAmount);
//
//        orderDao.createOrderItem(orderId,orderItemList);
//
//        return orderId;
        Integer totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
           int quantity =  buyItem.getQuantity();
           int productId = buyItem.getProductId();
           int productPrice = productDao.getProductById(productId).getPrice();

           int amount = quantity*productPrice;
           totalAmount+=amount;

           OrderItem orderItem = new OrderItem();
           orderItem.setQuantity(quantity);
           orderItem.setProductId(productId);
           orderItem.setAmount(amount);
           orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId,totalAmount);

        orderDao.createOrderItem(orderId,orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItems = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItems);

        return order;
    }
}
