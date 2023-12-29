package com.oliverchen.springbootmall.dto;

import com.oliverchen.springbootmall.constant.ProductEnum;

import javax.validation.constraints.NotNull;
import java.util.Date;



public class ProductRequest {
    @NotNull
    private String productName;
    @NotNull
    private ProductEnum category;
    @NotNull
    private String imageUrl;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stock;
    @NotNull
    private String description;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductEnum getCategory() {
        return category;
    }

    public void setCategory(ProductEnum category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
