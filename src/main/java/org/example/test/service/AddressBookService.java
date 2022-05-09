package org.example.test.service;

import java.util.Optional;

public interface AddressBookService {

    String addAddress(final Address address);
    boolean updateAddress(final String id, final Address address);
    void delete(final String id);
    Optional<Address> queryAddress(final String id);
}
