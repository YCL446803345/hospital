package com.woniu.controller;

import com.woniu.entity.Role;
import com.woniu.service.RoleService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色管理
 */
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;



    //查询角色
    @RequestMapping("role/list")
    public ResponseResult<List<Role>> findRoleList(){
        List<Role> roleList = roleService.findRoleList();
        return new ResponseResult<List<Role>>(roleList,"OK",200);
    }
}
