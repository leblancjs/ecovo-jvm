package com.solutionsjsleblanc.ecovo.tripsearchservice.user;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class UserApiClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public UserApiClient(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = requireNonNull(restTemplate);
        this.baseUrl = requireNonNull(baseUrl);
    }

    public List<User> getAll() {
        final var headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final var entity = new HttpEntity<>(null, headers);

        return restTemplate.exchange(baseUrl + "/api/users", HttpMethod.GET, entity, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    public Optional<User> getById(Long id) {
        final var headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        final var entity = new HttpEntity<>(null, headers);

        return Optional.ofNullable(restTemplate.exchange(baseUrl + "/api/users/{id}", HttpMethod.GET, entity, User.class, id).getBody());
    }
}
