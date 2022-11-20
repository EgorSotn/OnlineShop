package ru.sotn.gatwaysecurityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;


@SpringBootApplication
@EnableEurekaClient
public class GatwaySecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatwaySecurityServerApplication.class, args);
    }

}
