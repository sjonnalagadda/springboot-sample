package org.example.test.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    @NotNull(message = "Address 1 can not be empty.")
    @Size(max = 100, message = "Address 1 length can not be more than 100 characters.")
    private final String address1;
    @Size(max = 100, message = "Address 2 length can not be more than 100 characters.")
    private final String address2;
    @NotNull(message = "City can not be empty.")
    @Size(min = 2, max=2,  message = "City length can not be more than 2 characters.")
    private final String city;
    @NotNull(message = "zip code can not be empty")
    @Size(min = 2, max=2,  message = "zip code length can not be more than 2 characters.")
    private final String zipCode;
}
