package com.oliverchen.springbootmall.dao.impl;

import com.oliverchen.springbootmall.dao.ProductDao;
import com.oliverchen.springbootmall.model.Product;
import com.oliverchen.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Product getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name,category, image_url, price, stock, description, create_date, last_modified_date\n" +
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
}
