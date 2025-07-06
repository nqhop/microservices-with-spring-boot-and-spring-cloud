package com.example.bookstore.service.service_prices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableDiscoveryClient
public class ServicePricesApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ServicePricesApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ServicePricesApplication.class, args);
	}

	@Component
	public class PortLogger implements ApplicationListener<WebServerInitializedEvent> {
		@Override
		public void onApplicationEvent(WebServerInitializedEvent event) {
			int port = event.getWebServer().getPort();
			LOG.info("Application is running on port: " + port + "  ...............");
		}
	}
}
