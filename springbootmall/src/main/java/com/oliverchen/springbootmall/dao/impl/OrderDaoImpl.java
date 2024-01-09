package com.oliverchen.springbootmall.dao.impl;

import com.oliverchen.springbootmall.dao.OrderDao;
import com.oliverchen.springbootmall.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id,total_amount,created_date,last_modified_date) " +
                " VALUES (:UserId,:totalAmount,:createdDate,:lastModifiedDate)";

        Map<String,Object> map = new HashMap<>();

        map.put("UserId",userId);
        map.put("totalAmount",totalAmount);

        Date now = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int key = keyHolder.getKey().intValue();

        return key;
    }

    @Override
    public void createOrderItem(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO `order_item` (order_id,product_id,quantity,amount) " +
                " VALUES (:orderId,:product,:quantity,:amount); ";

        MapSqlParameterSource[] mapSqlParameterSources =new MapSqlParameterSource[orderItemList.size()];
        for(int i = 0; i < mapSqlParameterSources.length;i++){

            OrderItem orderItem = orderItemList.get(i);

            mapSqlParameterSources[i] = new MapSqlParameterSource();
            mapSqlParameterSources[i].addValue("orderId",orderId);
            mapSqlParameterSources[i].addValue("product",orderItem.getProductId());
            mapSqlParameterSources[i].addValue("quantity",orderItem.getQuantity());
            mapSqlParameterSources[i].addValue("amount",orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sql,mapSqlParameterSources);
    }
}
