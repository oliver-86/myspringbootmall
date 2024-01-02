package com.oliverchen.springbootmall.dto;

import com.oliverchen.springbootmall.constant.ProductEnum;

public class RequestParameter {
    private ProductEnum category;
    private String search;

    public ProductEnum getCategory() {
        return category;
    }

    public void setCategory(ProductEnum category) {
        this.category = category;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
