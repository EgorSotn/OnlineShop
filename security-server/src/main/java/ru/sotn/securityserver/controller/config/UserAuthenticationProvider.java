package ru.sotn.securityserver.controller.config;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.Collections;
import java.util.Optional;


/**
 * Provider which will validate the Authentication object present in the SecurityContext.
 * The only acceptable Authentication object is the UsernamePasswordAuthenticationToken which comes from the
 * UserAuthenticationConverter. Then, from the username and password present in the Authentication object, I
 * validate the information against the database.
 * If the username and password don't match with the data present in the database, null is returned as the
 * Authentication object in the SecurityContext.
 */
@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

      //  Optional<AuthUser> oUser = userRepository.findByLogin(login);
        UserDetails user= customUserDetailsService.loadUserByUsername(username);

//        if (oUser.isEmpty()) {
//            return null;
//        }

//        AuthUser user = oUser.get();

        if(passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities());
        }
        else{
            throw new BadCredentialsException("Bad Credentials");
        }
//        if (passwordEncoder.matches(CharBuffer.wrap(password), user.getPassword())) {
//            return UsernamePasswordAuthenticationToken.authenticated(login, password, Collections.emptyList());
//        }

//        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
