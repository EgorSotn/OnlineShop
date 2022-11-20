package ru.sotn.imageservice.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class OAuth2FeignRequestInterceptor implements RequestInterceptor{
    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        final AbstractOAuth2TokenAuthenticationToken<? extends AbstractOAuth2Token> auth =
                (AbstractOAuth2TokenAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        requestTemplate.header( AUTHORIZATION_HEADER ,
                String.format ( "%s %s" , TOKEN_TYPE , auth.getToken().getTokenValue()));
    }

}
