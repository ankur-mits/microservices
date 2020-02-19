package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@RequestMapping("/orderServiceFailback")
	public Mono<String> orderFallback() {
		return Mono.just("Order Service Not working");
	}

	@RequestMapping("/productServiceFailback")
	public Mono<String> productFallback() {
		return Mono.just("Product Service Not working");
	}
}