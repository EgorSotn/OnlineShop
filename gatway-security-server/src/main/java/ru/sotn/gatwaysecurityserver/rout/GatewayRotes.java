package ru.sotn.gatwaysecurityserver.rout;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;


@Configuration
@RequiredArgsConstructor
@EnableWebFlux
public class GatewayRotes {

    private final TokenRelayGatewayFilterFactory tokenRelayGatewayFilterFactory;
    @Bean
    public RouteLocator gatewayRoutes (RouteLocatorBuilder builder){
        return builder.routes()
                .route("catalog-service",r-> r.path("/api/clothe/**")
                        .filters(f->f.filters(tokenRelayGatewayFilterFactory.apply())).uri("lb://catalog-service"))
                .route("order-service",r-> r.path("/api/order/**")
                        .filters(f->f.filters(tokenRelayGatewayFilterFactory.apply())).uri("lb://order-service"))
                .route("transaction-service",r-> r.path("/api/charge/**")
                        .filters(f->f.filters(tokenRelayGatewayFilterFactory.apply())).uri("lb://transaction-service"))
                .route("image-service",r-> r.path("/file/**")
                        .filters(f->f.filters(tokenRelayGatewayFilterFactory.apply())).uri("lb://image-service"))
                .build();
    }

}
