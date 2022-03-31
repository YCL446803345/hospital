package com.woniu.service.impl;

import com.woniu.entity.Worker;
import com.woniu.entity.WorkerExample;
import com.woniu.mapper.WorkerMapper;
import com.woniu.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 职工实现业务
 */
@Service("workerService")
public class WorkerServiceImpl implements WorkerService {


    @Autowired
    private WorkerMapper workerMapper;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    //查询职工列表
    public List<Worker> findWorkerList(Worker worker) {

        List<Worker> workers = workerMapper.select(worker);
        return workers;
    }

    //添加职工
    public void addWorker(Worker worker) {
        worker.setPassword(PasswordEncoder.encode(worker.getPassword()));
        worker.setStatus("1");
        worker.setSpare1("1");
        workerMapper.insert(worker);
    }

    //删除职工
    public void deleteWorkerById(Integer id) {
        workerMapper.deleteByPrimaryKey(id);
    }

    //修改职工
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

    @Override
    public Worker getWorker(String account) {
        WorkerExample workerExample = new WorkerExample();
        WorkerExample.Criteria criteria = workerExample.createCriteria();
        criteria.andAccountEqualTo(account);
        List<Worker> workers = workerMapper.selectByExample(workerExample);
        return workers.get(0);
    }


    //药房查找所有医生
    public List<String> findAllDoctor() {
        List<String> doctorList = workerMapper.findAllDoctor();
        return doctorList;
    }

    @Override
    public List<Worker> findWorkersByDeptIdAndRoleId(Integer deptId, Integer roleId) {
        WorkerExample workerExample = new WorkerExample();
        WorkerExample.Criteria criteria = workerExample.createCriteria();
        criteria.andDeptIdEqualTo(deptId);
        criteria.andRoleIdEqualTo(roleId);
        return workerMapper.selectByExample(workerExample);
    }

    @Override
    public Worker getByAccount(String account) {
        WorkerExample workerExample = new WorkerExample();
        WorkerExample.Criteria criteria = workerExample.createCriteria();
        criteria.andAccountEqualTo(account);
        Worker worker = workerMapper.selectByAccount(account);
        return worker;
    }
}
