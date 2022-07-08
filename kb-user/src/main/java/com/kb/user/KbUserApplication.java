package com.kb.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author mawz
 * @version 1.0
 * @date
 */
@SpringBootApplication(scanBasePackages = {"com.kb.*"}, scanBasePackageClasses ={KbUserApplication.class})
@EnableDiscoveryClient
@MapperScan("com.kb.user.dao")
public class KbUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(KbUserApplication.class, args);
	}
}
