package com.kaliber.quizplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients("com.kaliber.quizplay")
@SpringBootApplication
public class KaliberQuizPlayApplication {
	
//	private static Logger logger = LogManager.getLogger(KaliberQuizPlayApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(KaliberQuizPlayApplication.class, args);
	// logger.warn("this is quiz-play");
	// logger.error("quiz-play error messaage");
	}


}
