package com.eureka.homeloan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HomeloanClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeloanClientApplication.class, args);
	}

}
