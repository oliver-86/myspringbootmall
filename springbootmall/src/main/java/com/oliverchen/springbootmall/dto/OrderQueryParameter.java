package com.oliverchen.springbootmall.dto;

import com.oliverchen.springbootmall.model.Order;

import java.util.List;

public class OrderQueryParameter {
    private Integer userId;
    private Integer limit;
    private Integer offset;
    private Integer orderId;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
