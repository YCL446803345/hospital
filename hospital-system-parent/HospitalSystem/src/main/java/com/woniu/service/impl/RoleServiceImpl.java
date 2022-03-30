package com.woniu.service.impl;

import com.woniu.entity.Role;
import com.woniu.entity.RoleExample;
import com.woniu.mapper.RoleMapper;
import com.woniu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色实现业务
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleMapper roleMapper;


    //查询角色列表
    public List<Role> findRoleList() {
        List<Role> roleList = roleMapper.selectByExample(new RoleExample());

        return roleList;
    }

}
