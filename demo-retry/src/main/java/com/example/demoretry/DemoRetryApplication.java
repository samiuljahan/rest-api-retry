package com.example.demoretry;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoRetryApplication implements CommandLineRunner {


	private final RetryTemplate retryTemplate;

	private final RestTemplate restTemplate;

	public DemoRetryApplication(RetryTemplate retryTemplate, RestTemplate restTemplate) {
		this.retryTemplate = retryTemplate;
		this.restTemplate = restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoRetryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		new ApiClient(retryTemplate, restTemplate).callApi();
	}
}
