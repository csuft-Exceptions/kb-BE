package com.kb.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mawz
 * @version 1.0
 * @date 2022-07-24 - 15:30
 */
@SpringBootApplication(scanBasePackages = {"com.kb.*"}, scanBasePackageClasses ={KbFileApplication.class})
@EnableDiscoveryClient
@MapperScan("com.kb.file.dao")
public class KbFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(KbFileApplication.class, args);
    }

}
