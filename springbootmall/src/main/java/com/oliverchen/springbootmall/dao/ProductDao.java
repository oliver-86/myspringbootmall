package com.oliverchen.springbootmall.dao;

import com.oliverchen.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);
}
