package com.woniu.service.impl;

import com.woniu.entity.Worker;
import com.woniu.entity.WorkerExample;
import com.woniu.mapper.WorkerMapper;
import com.woniu.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 职工实现业务
 */
@Service("workerService")
public class WorkerServiceImpl implements WorkerService {


    @Autowired
    private WorkerMapper workerMapper;


    @Override

    public List<Worker> findWorkerList(String account, Integer roleId,Integer deptId) {
        WorkerExample workerExample = new WorkerExample();
        WorkerExample.Criteria criteria = workerExample.createCriteria();
        if (!StringUtils.isEmpty(account)){
            criteria.andAccountLike("%"+account+"%");
        }
        if (!StringUtils.isEmpty(roleId)){
            criteria.andDeptIdEqualTo(roleId);
        }
        if (!StringUtils.isEmpty(deptId)){
            criteria.andDeptIdEqualTo(deptId);
        }
        List<Worker> workers = workerMapper.select(workerExample);
        System.out.println("IMPL+"+workers);
        return workers;
    }


    public void addWorker(Worker worker) {
        workerMapper.insert(worker);
    }

    @Override
    public void deleteWorkerById(Integer id) {
        workerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateWorker(Worker worker) {
        workerMapper.updateByPrimaryKeySelective(worker);
    }
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
