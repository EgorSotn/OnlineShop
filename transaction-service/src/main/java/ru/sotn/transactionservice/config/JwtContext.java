package ru.sotn.transactionservice.config;


import lombok.Data;
import org.springframework.security.oauth2.jwt.Jwt;

@Data
public class JwtContext {
    public Jwt jwtToken;
}
