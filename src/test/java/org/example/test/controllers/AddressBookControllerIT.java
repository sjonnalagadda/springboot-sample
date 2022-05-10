package org.example.test.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.test.service.Address;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
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

    @Test
    public void addAnAddress() {
        FluxExchangeResult<Void> result =  webTestClient.post().uri("/address").accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(address()), Address.class).exchange().returnResult(Void.class);
        Assertions.assertEquals(HttpStatus.CREATED.value(), result.getRawStatusCode());
    }

    @Test
    public void doCrudOperationsOnAddressBook() {
        //Add Address
        FluxExchangeResult<Void> result =  webTestClient.post().uri("/address").contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(address()), Address.class).exchange().returnResult(Void.class);
        String location = result.getResponseHeaders().getLocation().toString();
        log.info("location got {}", location);
        String id = location.substring(location.lastIndexOf('/'));
        //Query Address
         Address queryResult = webTestClient.get().uri("/address/" + id).accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().is2xxSuccessful().returnResult(Address.class).getResponseBody().blockFirst();
         Assertions.assertEquals(address(), queryResult);
        //update Address
        webTestClient.put().uri("/address/"+ id).contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newAddress()), Address.class).exchange().expectStatus().is2xxSuccessful();
        //Query for updated address
        Address newQueryResult = webTestClient.get().uri("/address/" + id).accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().is2xxSuccessful().returnResult(Address.class).getResponseBody().blockFirst();
        Assertions.assertEquals(newAddress(), newQueryResult);
        //Delete address
        webTestClient.delete().uri("/address/" + id)
                .exchange().expectStatus().isNoContent();
    }



    private static String getBaseUrl() {
        return new StringBuilder("http://localhost:").append(System.getProperty("application.port")).toString();
    }

    private Address address() {
        return Address.builder().address1("1 North Pole").city("NA").zipCode("00001").build();
    }

    private Address newAddress() {
        return Address.builder().address1("1 South Pole").city("NA").zipCode("00001").build();
    }
}
