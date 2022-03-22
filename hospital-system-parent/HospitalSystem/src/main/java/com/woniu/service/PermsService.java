package com.woniu.service;


import com.woniu.entity.Perms;

import java.util.List;

public interface PermsService {
    List<Perms> byNamefindPerm(String account);
}
