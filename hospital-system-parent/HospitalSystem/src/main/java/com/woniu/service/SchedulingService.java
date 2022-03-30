package com.woniu.service;

import com.woniu.entity.Scheduling;

import java.util.List;

/**
 * 排班业务层
 */
public interface SchedulingService {

    //查询排班列表
    List<Scheduling> findSchedulingList();

    void updateScheduling(Scheduling scheduling);

    List<Scheduling> findSchedulings();
}
