package com.oliverchen.springbootmall.service.impl;

import com.oliverchen.springbootmall.dao.UserDao;
import com.oliverchen.springbootmall.dto.UserRequest;
import com.oliverchen.springbootmall.model.User;
import com.oliverchen.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public Integer register(UserRequest userRequest) {
        return userDao.createUser(userRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        User user = userDao.getUserById(userId);
        return user;
    }
}
