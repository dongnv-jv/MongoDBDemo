package com.example.mongodemo.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateCf {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(10000))
                .setReadTimeout(Duration.ofMillis(10000))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
