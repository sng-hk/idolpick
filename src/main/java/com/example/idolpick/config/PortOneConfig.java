package com.example.idolpick.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortOneConfig {
    @Value("${imp.key}")
    private String apiKey;

    @Value("${imp.secret_key}")
    private String secretKey;
}
