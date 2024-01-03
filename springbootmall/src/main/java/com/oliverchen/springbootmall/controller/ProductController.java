package com.oliverchen.springbootmall.controller;

import com.oliverchen.springbootmall.constant.ProductEnum;
import com.oliverchen.springbootmall.dto.ProductRequest;
import com.oliverchen.springbootmall.dto.RequestParameter;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.service.ProductService;
import com.oliverchen.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId){

        Product product = productService.getProductById(productId);
        if(product != null){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductById(productId);

        if(product == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.updateProduct(productId,productRequest);

        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProduct(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProducts(
           @RequestParam(required = false) ProductEnum category,
           @RequestParam(required = false) String search,

           @RequestParam(defaultValue = "created_date") String orderBy,
           @RequestParam(defaultValue = "desc") String sort,

           @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
           @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
       RequestParameter requestParam = new  RequestParameter();
       requestParam.setSearch(search);
       requestParam.setCategory(category);
       requestParam.setSort(sort);
       requestParam.setOrderBy(orderBy);
       requestParam.setLimit(limit);
       requestParam.setOffset(offset);

       List<Product> lists =  productService.getAllProducts(requestParam);

       Integer total = productService.getProductTotal(requestParam);

        Page<Product> productPage = new Page<>();

        productPage.setLimit(limit);
        productPage.setOffset(offset);
        productPage.setTotal(total);
        productPage.setResult(lists);

       return ResponseEntity.status(HttpStatus.OK).body(productPage);
    }

}
