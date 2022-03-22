package com.woniu.service;

import com.woniu.entity.Worker;

import java.util.List;

public interface WorkerService {
    List<Worker> findDoctorsByDepeId(Integer deptId);

    List<Worker> findNursesByDepeId(Integer deptId);
}
