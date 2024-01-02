package com.oliverchen.springbootmall.dao.impl;

import com.oliverchen.springbootmall.dao.ProductDao;
import com.oliverchen.springbootmall.dto.ProductRequest;
import com.oliverchen.springbootmall.dto.RequestParameter;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name,category, image_url, price, stock, description, created_date, last_modified_date\n" +
                "FROM product \n" +
                "WHERE product_id = :productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);
        ProductRowMapper productRowMapper = new ProductRowMapper();
        List<Product> query = namedParameterJdbcTemplate.query(sql, map, productRowMapper);
        if(query.size() > 0){
           return query.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sql = "INSERT INTO product (product_name,category,image_url,price,stock,description,created_date,last_modified_date)" +
                " VALUES (:productName,:category,:imageUrl,:price,:stock,:description,:createDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        Date date = new Date();
        map.put("createDate",date);
        map.put("lastModifiedDate",date);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);
        Integer key = keyHolder.getKey().intValue();

        return key;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "update product set product_name = :productName,category = :category,image_url=:imageUrl,price=:price,stock=:stock,description=:description,last_modified_date=:lastModifiedDate" +
                " where product_id=:productId";
        Map<String,Object> map = new HashMap<>();
        map.put("productName",productRequest.getProductName());
        map.put("category",productRequest.getCategory().toString());
        map.put("imageUrl",productRequest.getImageUrl());
        map.put("price",productRequest.getPrice());
        map.put("stock",productRequest.getStock());
        map.put("description",productRequest.getDescription());

        map.put("lastModifiedDate",new Date());

        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void deleteProduct(Integer productId) {
        String sql = "DELETE FROM product where product_id = :productId";

        Map<String,Object> map = new HashMap<>();
        map.put("productId",productId);

        namedParameterJdbcTemplate.update(sql,map);

    }

    @Override
    public List<Product> getAllProducts(RequestParameter requestParameter) {
        String sql = "SELECT product_id,product_name,category, image_url, price, stock, description," +
                " created_date, last_modified_date FROM product WHERE 1=1";

        Map<String,Object> map = new HashMap<>();

        if(requestParameter.getSearch() != null){
            sql = sql + " AND product_name LIKE :productName";
            map.put("productName" , "%"+requestParameter.getSearch()+"%");
        }

        if(requestParameter.getCategory() != null){
            sql = sql + " AND category = :category";
            map.put("category",requestParameter.getCategory().name());
        }


        ProductRowMapper productRowMapper = new ProductRowMapper();
        List<Product> productList = namedParameterJdbcTemplate.query(sql,map,productRowMapper);
        return productList;
    }
}
