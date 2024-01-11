package com.oliverchen.springbootmall.dao.impl;

import com.fasterxml.jackson.databind.ObjectReader;
import com.oliverchen.springbootmall.dao.OrderDao;
import com.oliverchen.springbootmall.dto.OrderQueryParameter;
import com.oliverchen.springbootmall.model.Order;
import com.oliverchen.springbootmall.model.OrderItem;
import com.oliverchen.springbootmall.rowmapper.OrderItemRowMapper;
import com.oliverchen.springbootmall.rowmapper.OrderRowMapper;
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



    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {

        String sql = "SELECT oi.order_item_id, oi.order_id,oi.product_id,oi.quantity,oi.amount," +
                "p.product_name,p.image_url" +
                " FROM `order_item` as oi LEFT JOIN `product` as p " +
                " ON oi.product_id = p.product_id WHERE oi.order_id = :orderId";

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT order_id,user_id,total_amount,created_date,last_modified_date" +
                " FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId" , orderId);
        List<Order> list = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());
        if(list.size() > 0){
           return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public Integer getOrderCount(OrderQueryParameter orderQueryParameter) {
        String sql = "SELECT count(*) FROM `order` WHERE 1=1";
        Map<String,Object> map = new HashMap<>();
        sql = addQueryString(sql,map,orderQueryParameter);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return count;
    }

    @Override
    public List<Order> getOrderList(OrderQueryParameter orderQueryParameter) {
        String sql = "SELECT order_id,user_id,total_amount,created_date,last_modified_date" +
                " FROM `order` WHERE 1=1";

        Map<String,Object> map = new HashMap<>();

        Integer limit = orderQueryParameter.getLimit();
        Integer offset = orderQueryParameter.getOffset();

        sql =  addQueryString(sql,map,orderQueryParameter);

        sql = sql + " ORDER BY created_date DESC";

        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit",limit);
        map.put("offset" , offset);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map,new OrderRowMapper());

        return orderList;
    }

    public String addQueryString(String sql,Map<String,Object> map,OrderQueryParameter orderQueryParameter){
        if(orderQueryParameter.getUserId() != null){
            sql = sql + " AND user_id = :userId";
            map.put("userId",orderQueryParameter.getUserId());

        }
        return sql;
    }

}
