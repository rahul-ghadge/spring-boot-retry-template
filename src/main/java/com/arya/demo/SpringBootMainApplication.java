package com.arya.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringBootMainApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootMainApplication.class, args);
	}
}
