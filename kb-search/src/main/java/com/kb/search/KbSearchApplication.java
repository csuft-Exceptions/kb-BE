package com.kb.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 15:30
 */
@SpringBootApplication(scanBasePackages = {"com.kb.*"}, scanBasePackageClasses ={KbSearchApplication.class})
@EnableDiscoveryClient
@MapperScan("com.kb.search.dao")
public class KbSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(KbSearchApplication.class, args);
    }

}
