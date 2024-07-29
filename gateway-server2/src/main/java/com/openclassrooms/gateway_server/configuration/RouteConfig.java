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
				.route("microback", r -> r.path("/microback/**")
						.filters(f -> f.rewritePath("/microback/(?<path>.*)", "/${path}")).uri("lb://microback"))
				.build();
	}

}
