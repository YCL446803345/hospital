package com.woniu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HospitalSystemApplicationTests {

    @Test
    void contextLoads() {
        String a="0002";
        System.out.println(Integer.parseInt(a)+"----------------");
        int numberOfDigits = String.valueOf(Integer.parseInt(a)).length();
        System.out.println(numberOfDigits);
    }

}
