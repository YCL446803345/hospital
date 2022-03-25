package com.woniu.config;

import com.woniu.entity.Worker;
import com.woniu.entity.WorkerExample;
import com.woniu.mapper.WorkerMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询职工权限并封装
 */
@Component("workerDetailsServiceImpl")
public class WorkerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private WorkerMapper workerMapper;
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        WorkerExample workerExample = new WorkerExample();
        WorkerExample.Criteria criteria = workerExample.createCriteria();
        criteria.andAccountEqualTo(account);
        List<Worker> workers = workerMapper.selectByExample(workerExample);

        if (workers == null || workers.size() == 0){
            //没有该用户
            return null;
        }
        //当前职工
        Worker worker =workers.get(0);
        //创建存放权限的集合 --暂时为空集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> perCodes = workerMapper.selectPercodeByPerm(worker.getAccount());
        perCodes.forEach(per->{
            authorities.add(new SimpleGrantedAuthority(per));
        });
        //根据数据库查询信息，封装UserDetails对象，并返回
        UserDetails workerDetails = new org.springframework.security.core.userdetails.User(worker.getAccount(),worker.getPassword(),authorities);
        return workerDetails;
    }
}
