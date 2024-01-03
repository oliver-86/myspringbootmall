package com.oliverchen.springbootmall.service.impl;

import com.oliverchen.springbootmall.dao.ProductDao;
import com.oliverchen.springbootmall.dto.ProductRequest;
import com.oliverchen.springbootmall.dto.RequestParameter;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
       Integer productId =  productDao.createProduct(productRequest);
       return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }

    @Override
    public List<Product> getAllProducts(RequestParameter requestParameter) {
        return productDao.getAllProducts(requestParameter);
    }

    @Override
    public Integer getProductTotal(RequestParameter requestParam) {
        return productDao.getProductTotal(requestParam);
    }
}
