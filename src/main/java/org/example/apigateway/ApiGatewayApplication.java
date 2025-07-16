package org.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
		return builder.routes()
				.route("user-service", r -> r
						.path("/user/**")                            // requests like /user/login, /user/register
						.uri("lb://user-service"))                   // match Eureka service name

				.route("admin-service", r -> r
						.path("/admin/**")                           // optional admin routes
						.uri("lb://admin-service"))                 // must match actual registered name

				.route("payment-ms", r -> r
					.path("/api/payments/**")
					.uri("lb://payment-ms"))
				.build();
	}
}
