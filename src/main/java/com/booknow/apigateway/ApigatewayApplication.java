package com.booknow.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@EnableEurekaClient
@Configuration
public class ApigatewayApplication {

	@Value("${usuario.service.url}")
	private String usuarioServiceUrl;

	@Value("${transacoes.service.url}")
	private String transacoesServiceUrl;

	@Value("${livros.service.url}")
	private String livrosServiceUrl;

	public static void main(String[] args) {
		SpringApplication.run(ApigatewayApplication.class, args);
	}

	@Bean
	public JwtValidationGatewayFilter jwtValidationGatewayFilter() {
		return new JwtValidationGatewayFilter(usuarioServiceUrl); // Pass the URL here
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtValidationGatewayFilter jwtValidationGatewayFilter) {
		return builder.routes()
				.route("auth_route", p -> p
						.path("/auth/**")
						.uri(usuarioServiceUrl + "/auth"))

				.route("usuario_route", p -> p
						.path("/Usuario/**")
						.filters(f -> f.filter(jwtValidationGatewayFilter))
						.uri(usuarioServiceUrl + "/Usuario"))

				.route("transacoes_route", p -> p
						.path("/transacoes/**")
						.filters(f -> f.filter(jwtValidationGatewayFilter)) // Use the injected filter bean
						.uri(transacoesServiceUrl + "/transacoes"))

				.route("livros_route", p -> p
						.path("/livros/**")
						.filters(f -> f.filter(jwtValidationGatewayFilter)) // Use the injected filter bean
						.uri(livrosServiceUrl + "/livros"))
				.build();
	}
}
