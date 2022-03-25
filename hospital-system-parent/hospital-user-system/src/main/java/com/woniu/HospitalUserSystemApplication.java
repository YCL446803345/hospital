package com.woniu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@MapperScan("com.woniu.mapper")
@CrossOrigin
public class HospitalUserSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalUserSystemApplication.class, args);
    }
    //密码加密
    @Bean
    public PasswordEncoder getPassword(){
        return new BCryptPasswordEncoder();
    }
}
