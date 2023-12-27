package com.oliverchen.springbootmall.service.impl;

import com.oliverchen.springbootmall.dao.ProductDao;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
