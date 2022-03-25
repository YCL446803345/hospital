package com.woniu.service;


import com.woniu.entity.Perms;

import java.util.List;

public interface PermsService {
    List<Perms> byNamefindPerm(String account);


    List<String> getPermByUserId(Integer id);

    void updatePerms(Integer userId, String permsId);

    List<Perms> findAllPerms();
}
