package com.kaliber.quizinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients("com.kaliber.quizinventory")
//@EnableAutoConfiguration(exclude = JmxAutoConfiguration.class)
//@RibbonClient(name = "server")//, configuration = RibbonConfiguration.class)
public class KaliberQuizInventoryApplication  {

	
	public static void main(String[] args) {
		SpringApplication.run(KaliberQuizInventoryApplication.class, args);
	}
	
}
