package com.woniu.service;

import com.woniu.entity.User;

/**
 * 用户业务层
 */
public interface UserService {


    //添加用户
    void addUser(User User);


    User selectUserByPhone (String phone);

    void commonEmail (String email);

    //根据电话号码查询是否重复
    User getByTelephone(String telephone);




//    void deleteWorkerById(Integer id);
//
//    void updateWorker(Worker worker);
//


}
