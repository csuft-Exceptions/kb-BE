package com.kb.video;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.kb.*"})
public class KbVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KbVideoApplication.class, args);
    }
}
