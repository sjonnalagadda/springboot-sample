package org.example.test.controllers;

import org.example.test.service.Address;
import org.example.test.service.AddressBookService;
import org.example.test.service.AddressNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    @PostMapping(path = "/address", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAddress(@RequestBody Address address, final HttpRequest httpRequest) {
        final String id = addressBookService.addAddress(address);
        final ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.CREATED);
        final StringBuilder sb = new StringBuilder(httpRequest.getURI().toString()).append('/').append(id);
        responseEntity.getHeaders().add(HttpHeaders.LOCATION, sb.toString());
        return responseEntity;
    }

    @PostMapping(path = "/address/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateAddress(@RequestBody Address address, @PathVariable String id) {
        if(addressBookService.updateAddress(id, address)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/address/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAddress(@PathVariable String id) {
        Optional<Address> address = addressBookService.queryAddress(id);
        if(address.isPresent()) {
            return new ResponseEntity<>(address.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        addressBookService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
