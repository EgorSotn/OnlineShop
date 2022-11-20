//package ru.sotn.transactionservice.config;
//
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.core.AbstractOAuth2Token;
//import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//
//
//import java.util.regex.Pattern;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2FeignRequestInterceptor implements RequestInterceptor{
//    private final JwtContext jwtContext;
//    private static final String AUTHORIZATION_HEADER="Authorization";
//    private static final String TOKEN_TYPE = "Bearer";
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//
//
//        String token  = jwtContext.getJwtToken().getTokenValue();
//        requestTemplate.header( AUTHORIZATION_HEADER ,
//                String.format ( "%s %s" , TOKEN_TYPE ,token));
//    }
//
//}
