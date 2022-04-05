package com.woniu.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniu.entity.Worker;
import com.woniu.entity.WorkerExample;
import com.woniu.mapper.WorkerMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询职工权限并封装
 */
@Component("workerDetailsServiceImpl")
public class WorkerDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        ObjectMapper obj = new ObjectMapper();

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
        //查权限,先从redis缓存中去查
        String preocdes = worker.getAccount()+worker.getPassword();
        String perms = ops.get(preocdes);
        //如果缓存中有,从缓存中取
        if (!StringUtils.isEmpty(perms)){
            try {
                List<SimpleGrantedAuthority> premsList = obj.readValue(perms, new TypeReference<List<SimpleGrantedAuthority>>() {
                });
            return new org.springframework.security.core.userdetails.User(worker.getAccount(),worker.getPassword(),premsList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        //缓存中没有,从数据库取查,并且存入缓存
        //创建存放权限的集合 --暂时为空集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> perCodes = null;
        try {
            perCodes = workerMapper.selectPercodeByPerm(worker.getAccount());
            ops.set(preocdes,new ObjectMapper().writeValueAsString(perCodes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        perCodes.forEach(per->{
            authorities.add(new SimpleGrantedAuthority(per));
        });
        //根据数据库查询信息，封装UserDetails对象，并返回
        UserDetails workerDetails = new org.springframework.security.core.userdetails.User(worker.getAccount(),worker.getPassword(),authorities);
        return workerDetails;
    }
}
