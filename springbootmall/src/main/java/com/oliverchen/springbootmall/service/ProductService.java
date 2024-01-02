package com.oliverchen.springbootmall.service;

import com.oliverchen.springbootmall.dto.ProductRequest;
import com.oliverchen.springbootmall.model.Product;

import java.util.List;


public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId,ProductRequest productRequest);

    void deleteProduct(Integer productId);

    List<Product> getAllProducts();
}
