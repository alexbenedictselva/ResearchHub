package com.alex.researchhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class ResearchhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchhubApplication.class, args);
	}

}
