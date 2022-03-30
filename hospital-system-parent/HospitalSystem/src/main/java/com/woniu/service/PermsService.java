package com.woniu.service;


import com.woniu.entity.Perms;

import java.util.List;
/**
 * 权限业务层
 */
public interface PermsService {

    //查询该用户所有菜单栏权限
    List<Perms> byNamefindPerm(String account);

    //查询该用户权限树
    List<String> getPermByRoleId(Integer id);

    //修改该用户权限
    void updatePerms(Integer roleId, String permsId);

    //查询所有权限
    List<Perms> findAllPerms();
}
