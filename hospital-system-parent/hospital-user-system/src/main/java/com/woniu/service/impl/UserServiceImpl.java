package com.woniu.service.impl;

import com.woniu.entity.User;
import com.woniu.mapper.UserMapper;
import com.woniu.service.UserService;
import com.woniu.util.JwtTokenUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户实现业务
 */
@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;


    //添加用户
    public void addUser(User user) {
        try {
            user.setPassword(JwtTokenUitl.createSign(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userMapper.insert(user);
    }



}
