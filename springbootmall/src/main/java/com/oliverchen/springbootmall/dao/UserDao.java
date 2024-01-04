package com.oliverchen.springbootmall.dao;

import com.oliverchen.springbootmall.dto.UserRequest;
import com.oliverchen.springbootmall.model.User;

public interface UserDao {
    Integer createUser(UserRequest userRequest);

    User getUserById(Integer UserId);
}
