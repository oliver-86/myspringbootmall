package com.oliverchen.springbootmall.service;

import com.oliverchen.springbootmall.dto.UserLoginRequest;
import com.oliverchen.springbootmall.dto.UserRequest;
import com.oliverchen.springbootmall.model.User;

public interface UserService {
    Integer register(UserRequest userRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);
}
