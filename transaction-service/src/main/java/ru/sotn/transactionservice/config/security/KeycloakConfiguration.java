package ru.sotn.transactionservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.config.JwtContext;

import java.util.Collection;

@Configuration
public class KeycloakConfiguration {
    @Bean
    Converter<Jwt, Collection<GrantedAuthority>> keycloakGrantedAuthoritiesConverter() {
        return new KeycloakGrantedAuthoritiesConverter();
    }
    @Bean
    JwtContext jwtContext(){
        return new JwtContext();
    }

    @Bean
    Converter<Jwt, Mono<AbstractAuthenticationToken>> keycloakJwtAuthenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> converter, JwtContext jwtContext) {
        return new ReactiveKeycloakJwtConverter(converter, jwtContext);
    }
}
