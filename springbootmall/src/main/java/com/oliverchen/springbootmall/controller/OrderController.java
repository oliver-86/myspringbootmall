package com.oliverchen.springbootmall.controller;

import com.oliverchen.springbootmall.dto.CreateOrderRequest;
import com.oliverchen.springbootmall.dto.OrderQueryParameter;
import com.oliverchen.springbootmall.model.Order;
import com.oliverchen.springbootmall.service.OrderService;
import com.oliverchen.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Validated
public class OrderController {

@Autowired
    private OrderService orderService;
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody CreateOrderRequest createOrderRequest){
    Integer orderId = orderService.addOrder(userId,createOrderRequest);

    Order order = orderService.getOrderById(orderId);

    return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrderList(@PathVariable Integer userId,
                                                    @RequestParam(defaultValue = "5") @Max(1000) @Min(0)Integer limit,
                                                    @RequestParam(defaultValue = "0") @Min(0) Integer offset){
        OrderQueryParameter orderQueryParameter  = new OrderQueryParameter();
        orderQueryParameter.setLimit(limit);
        orderQueryParameter.setOffset(offset);
        orderQueryParameter.setOrderId(userId);

        List<Order> orderList = orderService.getOrderListByUserId(orderQueryParameter);

        Integer orderCount = orderService.getOrderListCount(orderQueryParameter);

        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(orderCount);
        page.setResult(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);

    }
}
