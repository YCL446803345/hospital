package com.woniu;

import com.woniu.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class HospitalSystemApplicationTests {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        //如果操作string类型   k-v
//        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//        ops.set("age","10");
//        System.out.println(ops.get("age"));
//        redisTemplate.delete("age");
    }

}
