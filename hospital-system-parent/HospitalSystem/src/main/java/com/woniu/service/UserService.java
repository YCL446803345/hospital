package com.woniu.service;

import com.woniu.entity.User;
import com.woniu.entity.WorkerScheduling;

import java.util.List;

/**
 * 用户业务层
 */
public interface UserService {



    List<WorkerScheduling> getMyScheduling (String account);



}
