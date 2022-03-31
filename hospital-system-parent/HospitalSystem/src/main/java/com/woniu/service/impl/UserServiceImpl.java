package com.woniu.service.impl;

import com.woniu.entity.User;
import com.woniu.entity.UserExample;
import com.woniu.entity.WorkerScheduling;
import com.woniu.mapper.UserMapper;
import com.woniu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;

/**
 * 用户实现业务
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<WorkerScheduling> getMyScheduling (String account) {
        List<WorkerScheduling> workerSchedulings = userMapper.getMyScheduling(account);
        return workerSchedulings;
    }
}
