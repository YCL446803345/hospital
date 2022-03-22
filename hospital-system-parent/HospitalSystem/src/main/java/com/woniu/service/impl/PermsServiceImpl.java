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
}
