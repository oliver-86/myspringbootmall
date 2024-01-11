package com.oliverchen.springbootmall.service.impl;

import com.oliverchen.springbootmall.dao.OrderDao;
import com.oliverchen.springbootmall.dao.ProductDao;
import com.oliverchen.springbootmall.dao.UserDao;
import com.oliverchen.springbootmall.dto.BuyItem;
import com.oliverchen.springbootmall.dto.CreateOrderRequest;
import com.oliverchen.springbootmall.dto.OrderQueryParameter;
import com.oliverchen.springbootmall.model.Order;
import com.oliverchen.springbootmall.model.OrderItem;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.model.User;
import com.oliverchen.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;
    @Override
    @Transactional
    public Integer addOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        Integer totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        User user = userDao.getUserById(userId);
        if(user == null){
            logger.warn("查無此帳號 {}" , userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){


           Product product = productDao.getProductById(buyItem.getProductId());
           if(product == null){
               logger.warn("此商品 {}不存在",buyItem.getProductId());
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
           }else if(product.getStock() < buyItem.getQuantity()){
               logger.warn("此商品 {} 數量不足 庫存為 {} 購買數量為 {}",product.getProductName(),product.getStock(),buyItem.getQuantity());
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
           }

           productDao.updateStock(buyItem.getProductId(),product.getStock()-buyItem.getQuantity());

            int quantity =  buyItem.getQuantity();
            int productPrice = productDao.getProductById(buyItem.getProductId()).getPrice();

           int amount = quantity*productPrice;
           totalAmount+=amount;

           OrderItem orderItem = new OrderItem();
           orderItem.setQuantity(quantity);
           orderItem.setProductId(buyItem.getProductId());
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

    @Override
    public Integer getOrderListCount(OrderQueryParameter orderQueryParameter) {
        return orderDao.getOrderCount(orderQueryParameter);
    }

    @Override
    public List<Order> getOrderListByUserId(OrderQueryParameter orderQueryParameter) {
       List<Order> orderList =  orderDao.getOrderList(orderQueryParameter);

       for(Order order : orderList){
          List<OrderItem> orderItemList =  orderDao.getOrderItemsByOrderId(order.getOrder_id());
          order.setOrderItemList(orderItemList);
       }


        return orderList;
    }
}
