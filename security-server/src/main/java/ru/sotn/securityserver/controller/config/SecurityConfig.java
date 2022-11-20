package ru.sotn.securityserver.controller.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.UUID;

//@EnableWebSecurity

@AllArgsConstructor
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig {
    private final UserAuthenticationProvider customAuthenticationProvider;
    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        http
//                // Redirect to the login page when not authenticated from the
//                // authorization endpoint
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint(
//                                new LoginUrlAuthenticationEntryPoint("/login"))
//                )
//        ;
//
//        return http.build();
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        return http.formLogin(Customizer.withDefaults()).build();
    }
//    @Bean
//    @Order(2)
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//                // Form login handles the redirect to the login page from the
//                // authorization server filter chain
//                .formLogin(Customizer.withDefaults());
//        return http.build();
//    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                // client-id and client-secret that must be used from all the OAuth2 clients
                .clientId("gateway-client-id")
                .clientSecret("$2a$12$ZUamdYa4buAoIoxI6lGOMunBKesogEKoenlUSjCfyX8.y.x53HYUG")
                // the Basic authentication method will be used between backend-gateway-client and backend-auth
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                // grant types to be used
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                // permitted redirect URI after the authentication is successful
                .redirectUri("//API-GATEWAY/login/oauth2/code/gateway")
                .redirectUri("//API-GATEWAY/authorized")
                // acceptable scopes for the authorization
                .scope(OidcScopes.OPENID)
                .scope("message.read")
                .scope("message.write")
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return ProviderSettings.builder()
                .issuer("//SECURITY-SERVICE")
                .build();
    }




    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder
                .authenticationProvider(customAuthenticationProvider);
    }
}
