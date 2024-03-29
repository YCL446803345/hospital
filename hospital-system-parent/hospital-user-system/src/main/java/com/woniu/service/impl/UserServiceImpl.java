package com.woniu.service.impl;

import com.woniu.entity.User;
import com.woniu.entity.UserExample;
import com.woniu.entity.WorkerScheduling;
import com.woniu.mapper.UserMapper;
import com.woniu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;

/**
 * 用户实现业务
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder PasswordEncoder;

    @Value("${spring.mail.username}")
    private String from;

    //添加用户
    public void addUser(User user) {
        try {
            user.setSpare2("");
            user.setPassword(PasswordEncoder.encode(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        userMapper.insert(user);
    }


    @Override
    public User selectUserByPhone (String phone) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTelephoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        return users.get(0);
    }

    @Override
    public User getByTelephone(String telephone) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTelephoneEqualTo(telephone);
        User user = userMapper.selectByTelephone(telephone);
        return user;
    }
        public void commonEmail(String email){
            //创建简单邮件消息
            SimpleMailMessage message = new SimpleMailMessage();
            //谁发的
            message.setFrom(from);
            //谁要接收
            message.setTo(email);
            //邮件标题
            message.setSubject("成都协和医院注册验证码");
            int i = (int) (Math.random() * 9999);
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumIntegerDigits(4);
            formatter.setGroupingUsed(false);
            String s = formatter.format(i);
            System.out.println(s);
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(s,s);
            //邮件内容
            message.setText("[成都协和医院] 验证码:"+s+"。您正在注册成都协和医院账号,如非本人操作,请忽略该信息。");
            mailSender.send(message);
        }

    @Override
    public List<WorkerScheduling> getMyScheduling (String account) {
        List<WorkerScheduling> workerSchedulings = userMapper.getMyScheduling(account);
        return workerSchedulings;
    }
}
