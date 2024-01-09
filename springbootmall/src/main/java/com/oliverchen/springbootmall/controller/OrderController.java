package com.oliverchen.springbootmall.controller;

import com.oliverchen.springbootmall.dto.CreateOrderRequest;
import com.oliverchen.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

@Autowired
    private OrderService orderService;
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody CreateOrderRequest createOrderRequest){
    Integer orderId = orderService.addOrder(userId,createOrderRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }
}
