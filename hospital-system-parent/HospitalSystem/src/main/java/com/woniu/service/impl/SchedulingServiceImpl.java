package com.woniu.service.impl;

import com.woniu.entity.Scheduling;
import com.woniu.entity.SchedulingExample;
import com.woniu.mapper.SchedulingMapper;
import com.woniu.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 排班实现业务
 */
@Service("schedulingService")
public class SchedulingServiceImpl implements SchedulingService {


    @Autowired
    private SchedulingMapper schedulingMapper;


    @Override
    public List<Scheduling> findSchedulingList() {
        List<Scheduling> schedulingList = schedulingMapper.select(new SchedulingExample());
        System.out.println("SchedulingIMPL"+schedulingList);
        return schedulingList;
    }

    @Override
    public void updateScheduling(Scheduling scheduling) {
        schedulingMapper.update(scheduling);
    }

    @Override
    public List<Scheduling> findSchedulings() {
        return schedulingMapper.selectByExample(null);
    }
}
