package org.example.test.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

public class AddressBookControllerIT {

    private static WebTestClient webTestClient;

    @BeforeAll
    public static void bindTOServer() {
        webTestClient = WebTestClient.bindToServer().baseUrl(getBaseUrl()).build();
    }

    @Test
    public void getWhenAddressIdIsNotValid() {
        webTestClient.get().uri("/address/1").accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    public void getWhenAddressIdIsNotFound() {
        webTestClient.get().uri("/address/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isNotFound();
    }

    @Test
    public void deleteWhenAddressIdIsNotValid() {
        webTestClient.delete().uri("/address/1")
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    public void deleteWhenAddressIdIsNotFound() {
        webTestClient.delete().uri("/address/" + UUID.randomUUID().toString())
                .exchange().expectStatus().isNoContent();
    }


    private static String getBaseUrl() {
        return new StringBuilder("http://localhost:").append(System.getProperty("application.port")).toString();
    }
}
