package com.woniu;

import com.auth0.jwt.JWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HospitalUserSystemApplicationTests {

    @Test
    void contextLoads() {
        String s = JWT.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZG1pbiIsImV4cCI6MTY0ODIwMDgzNn0.RG68PhmGvr2XSxU3Xci8Y2VthQa9KCtrqJZsEh6-Q_c")
                .getAudience().get(0);
        System.out.println(s);
    }

}
