package ru.sotn.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.context.ApplicationContext;


@SpringBootApplication
@EnableEurekaClient
public class CatalogServiceApplication {

    public static void main(String[] args) {
        //frsdgdhgfjhktrgfewdqwaesrdgtfhygju
        ApplicationContext context = SpringApplication.run(CatalogServiceApplication.class, args);
    }

}
