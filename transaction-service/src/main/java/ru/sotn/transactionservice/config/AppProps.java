package ru.sotn.transactionservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("stripe")
@Data
public class AppProps {
    private String sercretKey;

    private String publicKey;
}
