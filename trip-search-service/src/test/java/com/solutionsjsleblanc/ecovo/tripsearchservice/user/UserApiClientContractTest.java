package com.solutionsjsleblanc.ecovo.tripsearchservice.user;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest
@Import(UserApiConfig.class)
@PactTestFor(providerName = "UserService", pactVersion = PactSpecVersion.V3)
@MockServerConfig(hostInterface = "localhost", port = "8081")
class UserApiClientContractTest {
    @Autowired
    private UserApiClient userApiClient;

    @Pact(consumer = "TripSearchService")
    public RequestResponsePact users(PactDslWithProvider builder) {
        return builder
                .given("At least one user exists")
                .uponReceiving("A request for all users")
                .path("/api/users")
                .method("GET")
                .headers(Map.of(HttpHeaders.ACCEPT, "application/json"))
                .willRespondWith()
                .status(200)
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, "application/json"))
                .body(
                        new PactDslJsonArray()
                                .object()
                                .integerType("id")
                                .stringType("firstName")
                                .stringType("lastName")
                                .closeObject())
                .toPact();
    }

    @Pact(consumer = "TripSearchService")
    public RequestResponsePact userById(PactDslWithProvider builder) {
        return builder
                .given("At least one user exists")
                .uponReceiving("A request for a user with a specific ID")
                .path("/api/users/1")
                .method("GET")
                .headers(Map.of(HttpHeaders.ACCEPT, "application/json"))
                .willRespondWith()
                .status(200)
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, "application/json"))
                .body(
                        new PactDslJsonBody()
                                .integerType("id")
                                .stringType("firstName")
                                .stringType("lastName")
                )
                .toPact();
    }

    @Pact(consumer = "TripSearchService")
    public RequestResponsePact userDoesNotExist(PactDslWithProvider builder) {
        return builder
                .given("No user with the given ID exists")
                .uponReceiving("A request for a user with a specific ID")
                .path("/api/users/1")
                .method("GET")
                .headers(Map.of(HttpHeaders.ACCEPT, "application/json"))
                .willRespondWith()
                .status(404)
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, "application/json"))
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "users")
    void testUsers(MockServer mockServer) {
        final var users = userApiClient.getAll();
        assertThat(users.isEmpty()).isFalse();
    }

    @Test
    @PactTestFor(pactMethod = "userById")
    void testUserById(MockServer mockServer) {
        final var user = userApiClient.getById(1L);
        assertThat(user).isPresent();
    }

    @Test
    @PactTestFor(pactMethod = "userDoesNotExist")
    void testUserDoesNotExist() {
        assertThrows(RestClientException.class, () -> userApiClient.getById(1L));
    }
}
