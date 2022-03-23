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
    public List<String> getPermByUserId(Integer id) {
        List<String> permsList = permsMapper.getPermByUserId(id);
        System.err.println(permsList);
        return permsList;
    }

    //修改该用户权限
    public void updatePerms(Integer userId, String permsids) {
        permsMapper.deletePermsByUserid(userId);
        String[] split = permsids.split(",");
        for (String s : split) {
            int i = Integer.parseInt(s);
            System.err.println(i);
            permsMapper.updatePerms(userId,i);
        }
    }

    @Override
    public List<Perms> findAllPerms() {
        List<Perms> allPerms = permsMapper.findAllPerms();
        System.err.println(allPerms);
        return allPerms;
    }
}
