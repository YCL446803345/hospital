package com.woniu.service.impl;

import com.woniu.entity.User;
import com.woniu.entity.UserExample;
import com.woniu.mapper.UserMapper;
import com.woniu.service.UserService;
import com.woniu.util.JwtTokenUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户实现业务
 */
@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;


//    @Override
//
//    public List<Worker> findWorkerList(String account, Integer roleId,Integer deptId) {
//        WorkerExample workerExample = new WorkerExample();
//        WorkerExample.Criteria criteria = workerExample.createCriteria();
//        if (!StringUtils.isEmpty(account)){
//            criteria.andAccountLike("%"+account+"%");
//        }
//        if (!StringUtils.isEmpty(roleId)){
//            criteria.andDeptIdEqualTo(roleId);
//        }
//        if (!StringUtils.isEmpty(deptId)){
//            criteria.andDeptIdEqualTo(deptId);
//        }
//        List<Worker> workers = workerMapper.select(workerExample);
//        System.out.println("IMPL+"+workers);
//        return workers;
//    }


    public void addUser(User user) {
        try {
            user.setPassword(JwtTokenUitl.createSign(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userMapper.insert(user);
    }

    @Override
    public void updateStatus (String phone) {
        userMapper.updateByPhone(phone,"2");
    }

    @Override
    public String queryUserStatus (String phone) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTelephoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0).getSpare3();
    }

//    @Override
//    public void deleteWorkerById(Integer id) {
//        workerMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    public void updateWorker(Worker worker) {
//        workerMapper.updateByPrimaryKeySelective(worker);
//    }
//    public List<Worker> findDoctorsByDepeId(Integer deptId) {
//        WorkerExample workerExample = new WorkerExample();
//        WorkerExample.Criteria criteria = workerExample.createCriteria();
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(5);
//        criteria.andRoleIdIn(list);
//        criteria.andDeptIdEqualTo(deptId);
//        return workerMapper.selectByExample(workerExample);
//    }
//
//    @Override
//    public List<Worker> findNursesByDepeId(Integer deptId) {
//        WorkerExample workerExample = new WorkerExample();
//        WorkerExample.Criteria criteria = workerExample.createCriteria();
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(2);
//        list.add(4);
//        criteria.andRoleIdIn(list);
//        criteria.andDeptIdEqualTo(deptId);
//        return workerMapper.selectByExample(workerExample);
//
//    }



}
