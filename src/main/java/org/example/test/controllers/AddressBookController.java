package org.example.test.controllers;

import org.example.test.service.Address;
import org.example.test.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    @PostMapping(path = "/address", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAddress(@RequestBody Address address, final HttpServletRequest httpRequest) {
        final String id = addressBookService.addAddress(address);
        final StringBuilder sb = new StringBuilder("http://").append(httpRequest.getHeader(HttpHeaders.HOST)).append('/').append(id);
        final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.LOCATION, sb.toString());
        final ResponseEntity<Void> responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping(path = "/address/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
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
