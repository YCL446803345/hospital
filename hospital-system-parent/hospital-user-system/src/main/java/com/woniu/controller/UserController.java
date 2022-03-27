package com.woniu.controller;

import com.woniu.entity.User;
import com.woniu.service.UserService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;


    //添加用户
    @RequestMapping("user/add")
//    @PreAuthorize("hasAnyAuthority('user:add')")
    public ResponseResult<String> addWorker(@RequestBody User user){
        userService.addUser(user);
        return new ResponseResult<String>(200,"添加成功");
    }

}
