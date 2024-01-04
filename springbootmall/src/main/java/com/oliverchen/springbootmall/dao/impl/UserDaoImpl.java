package com.oliverchen.springbootmall.dao.impl;

import com.oliverchen.springbootmall.dao.UserDao;
import com.oliverchen.springbootmall.dto.UserRequest;
import com.oliverchen.springbootmall.model.User;
import com.oliverchen.springbootmall.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public Integer createUser(UserRequest userRequest) {
        String sql = "insert into user( email,password,created_date,last_modified_date) " +
                " values (:email,:password,:createdDate,:lastModifiedDate)";
        Map<String,Object> map = new HashMap<>();
        map.put("email",userRequest.getEmail());
        map.put("password",userRequest.getPassword());

        Date now  = new Date();
        map.put("createdDate",now);
        map.put("lastModifiedDate",now);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(map);

        KeyHolder keyHolder  = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource,keyHolder);

        Integer userId = keyHolder.getKey().intValue();
        return userId;
    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "select user_id,email,password,created_date,last_modified_date from user where user_id = :userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        UserRowMapper userRowMapper = new UserRowMapper();
        List<User> list = namedParameterJdbcTemplate.query(sql,map,userRowMapper);

        if(list.size() > 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User getUserByEmail(UserRequest userRequest) {
        String sql = "SELECT user_id,email,password,created_date,last_modified_date from " +
                " user WHERE email = :email";

        Map<String,Object> map = new HashMap<>();
        map.put("email",userRequest.getEmail());

        List<User> users = namedParameterJdbcTemplate.query(sql,map,new UserRowMapper());
        if(users.size() > 0){
           return users.get(0);
        }else{
            return null;
        }
    }


}
