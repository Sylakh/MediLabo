package com.openclassrooms.gateway_server.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	@Bean
	RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("microback",
						r -> r.path("/microback/**").filters(f -> f.rewritePath("/microback/(?<path>.*)", "/${path}"))
								.uri("lb://microback"))
				.route("medilabo-note",
						r -> r.path("/medilabo-note/**")
								.filters(f -> f.rewritePath("/medilabo-note/(?<path>.*)", "/${path}"))
								.uri("lb://medilabo-note"))
				.route("medilabo-report",
						r -> r.path("/medilabo-report/**")
								.filters(f -> f.rewritePath("/medilabo-report/(?<path>.*)", "/${path}"))
								.uri("lb://medilabo-report"))
				.build();

	}
}
