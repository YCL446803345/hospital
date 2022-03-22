package com.woniu.service;

import com.woniu.entity.Role;

import java.util.List;

/**
 * 角色业务层
 */
public interface RoleService {

    //查询角色列表
    List<Role> findRoleList();
}
