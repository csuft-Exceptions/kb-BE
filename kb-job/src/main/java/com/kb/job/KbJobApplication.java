package com.kb.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 15:30
 */
@SpringBootApplication(scanBasePackages = {"com.kb.*"}, scanBasePackageClasses ={KbJobApplication.class})
@EnableDiscoveryClient
@MapperScan("com.kb.job.dao")
public class KbJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(KbJobApplication.class, args);
    }

}
