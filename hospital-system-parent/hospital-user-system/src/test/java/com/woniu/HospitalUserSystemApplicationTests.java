package com.woniu;

import com.auth0.jwt.JWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.NumberFormat;

@SpringBootTest
class HospitalUserSystemApplicationTests {

    @Test
    void contextLoads() {
        int n = 1101;
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(4);
        formatter.setGroupingUsed(false);
        String s = formatter.format(n);
        System.out.println(s);
    }

}
