package com.woniu.config;

import com.woniu.entity.User;
import com.woniu.entity.UserExample;
import com.woniu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询登录用户权限并封装
 */
@Component("userDetailServiceImp")
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String telephone) throws UsernameNotFoundException {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTelephoneEqualTo(telephone);
        List<User> users = userMapper.selectByExample(userExample);
        if (users == null || users.size() == 0){
            //没有该用户
            return null;
        }
        //当前职工
        User user =users.get(0);
        //创建存放权限的集合 --暂时为空集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> perCodes = userMapper.selectPercodeByPerm(user.getTelephone());
        perCodes.forEach(per->{
            authorities.add(new SimpleGrantedAuthority(per));
        });
        //根据数据库查询信息，封装UserDetails对象，并返回
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getTelephone(),user.getPassword(),authorities);
        return userDetails;

    }
}
