package com.kb.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mawz
 * @version 1.0
 * @date
 */
@SpringBootApplication(scanBasePackages = {"com.kb.*"})
@EnableDiscoveryClient
public class KbCommonApplication {
	public static void main(String[] args) {
		SpringApplication.run(KbCommonApplication.class, args);
	}
}
