package com.kaliber.configserver.kaliberconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class KaliberConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KaliberConfigServerApplication.class, args);
	}

}
