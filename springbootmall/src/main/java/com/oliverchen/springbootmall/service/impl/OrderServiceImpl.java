package com.oliverchen.springbootmall.service.impl;

import com.oliverchen.springbootmall.dao.OrderDao;
import com.oliverchen.springbootmall.dao.ProductDao;
import com.oliverchen.springbootmall.dto.BuyItem;
import com.oliverchen.springbootmall.dto.CreateOrderRequest;
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
        int totalAmount = 0;
        List<OrderItem> orderItemList  = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            int quantity = buyItem.getQuantity();

            Product p = productDao.getProductById(buyItem.getProductId());
            int price = p.getPrice();

            int amount =quantity*price;

            totalAmount += amount;

            //BuyItem to OrderItem

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(p.getProductId());
            orderItem.setAmount(amount);
            orderItem.setQuantity(quantity);

            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId,totalAmount);

        orderDao.createOrderItem(orderId,orderItemList);

        return orderId;
    }
}
