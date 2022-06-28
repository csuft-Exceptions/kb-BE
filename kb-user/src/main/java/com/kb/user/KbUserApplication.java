package com.kb.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mawz
 * @version 1.0
 * @date
 */
@SpringBootApplication(scanBasePackages ="com.kb.user.dao")
@EnableDiscoveryClient
public class KbUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(KbUserApplication.class, args);
	}
}
