package ru.sotn.transactionservice.config.security;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.sotn.transactionservice.config.JwtContext;

import java.util.Collection;

public class ReactiveKeycloakJwtConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {
    private final JwtContext jwtContext;
    private static final String USERNAME_CLAIM = "preferred_username";
    private final Converter<Jwt, Flux<GrantedAuthority>> jwtGrantedAuthoritiesConverter;

    public ReactiveKeycloakJwtConverter(Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter, JwtContext jwtContext) {
        Assert.notNull(jwtGrantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
        this.jwtContext = jwtContext;
        this.jwtGrantedAuthoritiesConverter = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
                jwtGrantedAuthoritiesConverter);
    }
    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
        jwtContext.setJwtToken(jwt);
        // @formatter:off
        return this.jwtGrantedAuthoritiesConverter.convert(jwt)
                .collectList()
                .map((authorities) -> new JwtAuthenticationToken(jwt, authorities, extractUsername(jwt)));
        // @formatter:on
    }

    protected String extractUsername(Jwt jwt) {
        return jwt.containsClaim(USERNAME_CLAIM) ? jwt.getClaimAsString(USERNAME_CLAIM) : jwt.getSubject();
    }

}
