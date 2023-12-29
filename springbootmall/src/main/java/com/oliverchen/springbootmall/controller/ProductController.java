package com.oliverchen.springbootmall.controller;

import com.oliverchen.springbootmall.dto.ProductRequest;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId){

        Product product = productService.getProductById(productId);
        if(product != null){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}
