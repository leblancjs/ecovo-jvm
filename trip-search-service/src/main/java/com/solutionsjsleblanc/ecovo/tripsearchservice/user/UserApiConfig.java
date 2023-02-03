package com.solutionsjsleblanc.ecovo.tripsearchservice.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UserApiConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UserApiClient userApiClient(RestTemplate restTemplate) {
        return new UserApiClient(restTemplate, "http://localhost:8081");
    }
}
