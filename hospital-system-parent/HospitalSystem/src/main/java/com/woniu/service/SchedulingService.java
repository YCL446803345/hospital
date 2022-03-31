package com.woniu.service;

import com.woniu.entity.Scheduling;
import com.woniu.entity.Worker;

import java.util.List;

/**
 * 排班业务层
 */
public interface SchedulingService {

    //查询排班列表
    List<Scheduling> findSchedulingList();

    void updateScheduling(Scheduling scheduling);

    List<Scheduling> findSchedulings();

    void scheduling(Worker worker);

    List<Scheduling> findWorkersBySchedulingTime(String time);

    List<Scheduling> findAllWorkersScheduling();
}
