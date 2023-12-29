package com.oliverchen.springbootmall.dao;

import com.oliverchen.springbootmall.dto.ProductRequest;
import com.oliverchen.springbootmall.model.Product;

public interface ProductDao {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);
}
