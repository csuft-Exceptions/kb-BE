package com.kb.kbgateway.config;

import com.kb.kbgateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes().route(x->
                x.path("/service")
                        .uri("http://localhost:9001")
                        .filter(new GatewayFilter())
                        .id("service"))
                        .build();
    }
}
