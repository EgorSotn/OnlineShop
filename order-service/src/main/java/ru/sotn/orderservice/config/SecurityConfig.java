package ru.sotn.orderservice.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;


@EnableWebFluxSecurity
public class SecurityConfig {


    private SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .pathMatchers( "/api/**").hasAnyRole( "ADMIN", "MANAGER")
                .pathMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER")
                .pathMatchers( HttpMethod.PUT,"/api/order/item/**").hasAnyRole("USER", "ADMIN", "MANAGER")
                .anyExchange().authenticated()
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .oauth2ResourceServer()
                .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter()));
        return http.build();
    }
    private Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new RealmRoleConverter());
        return (Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>>) Mono.just(jwtConverter);
    }
}
