package org.example.test.service;

public class AddressNotFoundException extends RuntimeException {

    private String id;

    public AddressNotFoundException(final String id) {
        super();
        this.id = id;
    }
}
