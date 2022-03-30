package com.woniu.service;

import com.woniu.entity.User;

/**
 * 用户业务层
 */
public interface UserService {


//    //查询职工列表
//    List<Worker> findWorkerList(String account, Integer roleId,Integer deptId);

    //添加职工
    void addUser(User User);

    User selectUserByPhone (String phone);

    void commonEmail (String email);

//    void deleteWorkerById(Integer id);
//
//    void updateWorker(Worker worker);
//

}
