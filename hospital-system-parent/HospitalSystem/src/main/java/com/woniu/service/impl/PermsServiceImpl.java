package com.woniu.service.impl;

import com.woniu.entity.Perms;
import com.woniu.mapper.PermsMapper;
import com.woniu.service.PermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("permsService")
public class PermsServiceImpl implements PermsService {

    @Autowired
    private PermsMapper permsMapper;

    //查询该用户所有菜单栏权限
    public List<Perms> byNamefindPerm(String account) {
        List<Perms> permsList = permsMapper.byNamefindPerms(account);
        return permsList;
    }

    //查询该用户权限树
    public List<String> getPermByRoleId(Integer id) {
        List<String> permsList = permsMapper.getPermByRoleId(id);

        return permsList;
    }

    //修改该用户权限
    public void updatePerms(Integer roleId, String permsids) {
        permsMapper.deletePermsByRoleId(roleId);
        String[] split = permsids.split(",");
        for (String s : split) {
            int i = Integer.parseInt(s);
            System.err.println(i);
            permsMapper.updatePerms(roleId,i);
        }
    }

    //查询所有权限
    public List<Perms> findAllPerms() {
        List<Perms> allPerms = permsMapper.findAllPerms();

        return allPerms;
    }
}
