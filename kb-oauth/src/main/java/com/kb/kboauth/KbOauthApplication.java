package com.kb.kboauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KbOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KbOauthApplication.class, args);
    }

}
