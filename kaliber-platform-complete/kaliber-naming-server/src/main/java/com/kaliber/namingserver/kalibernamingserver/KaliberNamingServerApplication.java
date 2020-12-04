package com.kaliber.namingserver.kalibernamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class KaliberNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaliberNamingServerApplication.class, args);
	}

}
