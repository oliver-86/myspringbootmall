package com.oliverchen.springbootmall.service;

import com.oliverchen.springbootmall.model.Product;
import org.springframework.stereotype.Service;


public interface ProductService {
    Product getProductById(Integer productId);
}
