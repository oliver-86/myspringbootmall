package com.oliverchen.springbootmall.service;

import com.oliverchen.springbootmall.dto.ProductRequest;
import com.oliverchen.springbootmall.model.Product;


public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
