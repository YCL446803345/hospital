package com.woniu.service.impl;

import com.woniu.entity.Scheduling;
import com.woniu.entity.SchedulingExample;
import com.woniu.entity.Worker;
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

        return schedulingList;
    }

    @Override
    public void updateScheduling(Scheduling scheduling) {
        schedulingMapper.update(scheduling);
    }

    @Override
    public List<Scheduling> findSchedulings() {
        return schedulingMapper.findSchedulings();
    }

    @Override
    public void scheduling(Worker worker) {
        Integer num=schedulingMapper.getWorkerNumBySchedulingTimeAndWorkId(worker);
        if(num==0){
            schedulingMapper.scheduling(worker);
        }
    }

    @Override
    public List<Scheduling> findWorkersBySchedulingTime(String time) {
        return schedulingMapper.findWorkersBySchedulingTime(time);
    }

    @Override
    public List<Scheduling> findAllWorkersScheduling() {
        return schedulingMapper.findAllWorkersScheduling();
    }
}
