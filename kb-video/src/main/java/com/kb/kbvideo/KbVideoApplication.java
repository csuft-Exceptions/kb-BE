package com.kb.kbvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KbVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KbVideoApplication.class, args);
    }

}
