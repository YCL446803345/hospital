package com.woniu.service;

import com.woniu.entity.Worker;

import java.util.List;

/**
 * 职工业务层
 */
public interface WorkerService {


    //查询职工列表
    List<Worker> findWorkerList(Worker worker);

    //添加职工
    void addWorker(Worker worker);

    void deleteWorkerById(Integer id);

    void updateWorker(Worker worker);


    List<Worker> findDoctorsByDepeId(Integer deptId);

    List<Worker> findNursesByDepeId(Integer deptId);


    Worker getWorker(String account);


    List<String> findAllDoctor();


    List<Worker> findWorkersByDeptIdAndRoleId(Integer deptId, Integer roleId);
}
