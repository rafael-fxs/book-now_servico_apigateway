package com.booknow.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@SpringBootApplication
@EnableEurekaClient
@Configuration
public class ApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/api/transacoes/**")
						.uri("http://localhost:8083/api/transacoes"))
				.route(p -> p
						.path("/livros/**")
						.uri("http://localhost:8082/livros"))
				.route(p -> p
						.path("/Usuario/**")
						.uri("http://localhost:8081/Usuario"))
				.build();
	}
}
