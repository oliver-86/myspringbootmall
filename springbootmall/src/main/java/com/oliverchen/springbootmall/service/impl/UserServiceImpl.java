package com.oliverchen.springbootmall.service.impl;

import com.oliverchen.springbootmall.dao.UserDao;
import com.oliverchen.springbootmall.dto.UserLoginRequest;
import com.oliverchen.springbootmall.dto.UserRequest;
import com.oliverchen.springbootmall.model.User;
import com.oliverchen.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Integer register(UserRequest userRequest) {

        User user = userDao.getUserByEmail(userRequest.getEmail());

        if(user != null){
            log.warn("此帳號 {} 已被註冊",userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String md5 = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());

        userRequest.setPassword(md5);

        return userDao.createUser(userRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        User user = userDao.getUserById(userId);
        return user;
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if(user == null){
            log.warn("此帳號 {} 尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String md5 = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        userLoginRequest.setPassword(md5);

        if(user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else{
            log.warn("此帳號 {} 密碼不正確",user.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
