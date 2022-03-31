package com.woniu.controller;

import com.woniu.entity.User;
import com.woniu.entity.WorkerScheduling;
import com.woniu.service.UserService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    //添加用户
    @RequestMapping("user/add")
//    @PreAuthorize("hasAnyAuthority('user:add')")
    public ResponseResult<String> addWorker(@RequestBody User user){
        if(redisTemplate.delete(user.getSpare2())){
            userService.addUser(user);
            return new ResponseResult<String>(200,"添加成功");
        }else {
            return new ResponseResult<>(888,"验证码错误");
        }
    }

    @GetMapping("queryUserStatus")
    public ResponseEntity<String> queryUserStatus(String phone){
        User user = userService.selectUserByPhone(phone);
        return new ResponseEntity<>(user.getSpare3(), HttpStatus.OK);
    }

    @GetMapping("sendCheck")
    public void sendCheck(String email){
        userService.commonEmail(email);
    }



}
