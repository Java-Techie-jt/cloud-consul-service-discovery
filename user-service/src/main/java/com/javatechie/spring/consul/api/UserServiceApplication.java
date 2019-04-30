package com.javatechie.spring.consul.api;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class UserServiceApplication {

	@Autowired
	private RestTemplate template;

	@Autowired
	private DiscoveryClient client;

	@GetMapping("/accessAPI")
	public String invokeGreetingService() {
		URI uri = client.getInstances("Greeting-service").stream().map(si -> si.getUri()).findFirst()
				.map(s -> s.resolve("/greeting")).get();
		return template.getForObject(uri, String.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}
}
