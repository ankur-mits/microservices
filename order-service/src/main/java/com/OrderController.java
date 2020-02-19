package com;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
@RequestMapping("/order-service")
public class OrderController {

	@Value("${message:Hello default}")
	private String message;

	@Autowired
	private DiscoveryClient discoveryClient;

	private static final String template = "Hello Order Service, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/order")
	public Order greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Order(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}

	@HystrixCommand
	@RequestMapping("/message")
	String getMessage() {
		return this.message;
	}
}