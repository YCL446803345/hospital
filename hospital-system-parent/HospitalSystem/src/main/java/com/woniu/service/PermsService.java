package com.woniu.service;


import com.woniu.entity.Perms;

import java.util.List;

public interface PermsService {
    List<Perms> byNamefindPerm(String account);


    List<String> getPermByRoleId(Integer id);

    void updatePerms(Integer roleId, String permsId);

    List<Perms> findAllPerms();
}
