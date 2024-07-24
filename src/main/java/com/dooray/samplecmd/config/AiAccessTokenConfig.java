package com.dooray.samplecmd.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiAccessTokenConfig {
    @Value("${dooray.ai.access-token}")
    private String accessToken;

    @Bean
    public String openAiAccessToken() {
        return accessToken;
    }
}
