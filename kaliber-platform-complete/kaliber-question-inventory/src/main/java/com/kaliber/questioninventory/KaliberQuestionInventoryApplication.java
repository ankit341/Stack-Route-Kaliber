package com.kaliber.questioninventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableFeignClients("com.kaliber.questioninventory")
@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2

public class KaliberQuestionInventoryApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KaliberQuestionInventoryApplication.class, args);
	}

}
