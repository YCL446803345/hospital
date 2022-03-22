package com.woniu.service.impl;

import com.woniu.entity.Worker;
import com.woniu.entity.WorkerExample;
import com.woniu.mapper.WorkerMapper;
import com.woniu.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("workerService")
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;
    @Override
    public List<Worker> findDoctorsByDepeId(Integer deptId) {
        WorkerExample workerExample = new WorkerExample();
        WorkerExample.Criteria criteria = workerExample.createCriteria();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(5);
        criteria.andRoleIdIn(list);
        criteria.andDeptIdEqualTo(deptId);
        return workerMapper.selectByExample(workerExample);
    }

    @Override
    public List<Worker> findNursesByDepeId(Integer deptId) {
        WorkerExample workerExample = new WorkerExample();
        WorkerExample.Criteria criteria = workerExample.createCriteria();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(4);
        criteria.andRoleIdIn(list);
        criteria.andDeptIdEqualTo(deptId);
        return workerMapper.selectByExample(workerExample);
    }



}
