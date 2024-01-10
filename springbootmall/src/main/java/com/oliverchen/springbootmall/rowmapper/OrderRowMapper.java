package com.oliverchen.springbootmall.rowmapper;

import com.oliverchen.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order =  new Order();
        order.setOrder_id(resultSet.getInt("order_id"));
        order.setUser_id(resultSet.getInt("user_id"));
        order.setTotal_amount(resultSet.getInt("total_amount"));
        order.setCreated_date(resultSet.getTimestamp("created_date"));
        order.setLast_modified_date(resultSet.getTimestamp("last_modified_date"));

        return order;
    }
}
