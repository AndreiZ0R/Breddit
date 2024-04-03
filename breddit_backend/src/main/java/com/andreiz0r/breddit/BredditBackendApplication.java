package com.andreiz0r.breddit;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class BredditBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BredditBackendApplication.class, args);
	}
}
