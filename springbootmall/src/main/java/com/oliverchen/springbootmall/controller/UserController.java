package com.oliverchen.springbootmall.controller;

import com.oliverchen.springbootmall.dto.UserRequest;
import com.oliverchen.springbootmall.model.User;
import com.oliverchen.springbootmall.service.ProductService;
import com.oliverchen.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/user/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRequest userRequest){

       Integer userId = userService.register(userRequest);

       User user = userService.getUserById(userId);

       return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
