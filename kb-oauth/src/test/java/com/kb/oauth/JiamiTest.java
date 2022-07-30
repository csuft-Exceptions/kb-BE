package com.kb.oauth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author wjx
 * @create 2022/7/11 14:50
 */
@SpringBootTest
public class JiamiTest {
    @Test
    void test(){
        System.out.println(new BCryptPasswordEncoder().encode("kaibai"));
    }
}
