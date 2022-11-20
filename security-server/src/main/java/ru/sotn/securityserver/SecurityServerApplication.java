package ru.sotn.securityserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }

}
