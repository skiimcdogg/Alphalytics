package com.alphalytics.tradingbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AlphaVantageConfig {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Value("${alphavantage.base.url:https://www.alphavantage.co}")
    private String baseUrl;

    public AlphaVantageConfig() {
        System.out.println("=== AlphaVantageConfig initialized ===");
    }

    @Bean
    public WebClient alphaVantageWebClient() {
        System.out.println("Creating WebClient with baseUrl: " + baseUrl);
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public String getApiKey() {
        System.out.println("getApiKey() called, returning: " + apiKey);
        return apiKey;
    }
}