package com.woniu.service;

import com.woniu.entity.User;
import com.woniu.entity.WorkerScheduling;

import java.util.List;

/**
 * 用户业务层
 */
public interface UserService {


    //添加用户
    void addUser(User User);


    User selectUserByPhone (String phone);

    void commonEmail (String email);


    List<WorkerScheduling> getMyScheduling (String account);

    //根据电话号码查询是否重复
    User getByTelephone(String telephone);


//    void deleteWorkerById(Integer id);
//
//    void updateWorker(Worker worker);
//


}
