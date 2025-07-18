package com.example.bookstore.service_discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ServiceDiscoveryApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceDiscoveryApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ServiceDiscoveryApplication.class, args);
		LOG.info("Service Discovery Application Started");
	}

}
