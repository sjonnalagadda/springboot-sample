package org.example.test.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.test.db.AddressBookRepository;
import org.example.test.db.AddressEntity;
import org.example.test.service.Address;
import org.example.test.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookRepository addressBookRepository;

    @Override
    public String addAddress(Address address) {
        final UUID uuid = UUID.randomUUID();
        addressBookRepository.save(AddressEntity.builder().id(uuid)
                .address1(address.getAddress1())
                .address2(address.getAddress2())
                .city(address.getCity())
                .zipCode(address.getZipCode()).build());
        return uuid.toString();
    }

    @Override
    public boolean updateAddress(String id, Address address) {
        try {
            AddressEntity addressEntity = addressBookRepository.getById(
                    convertToUUID(id));
            addressEntity.setAddress1(address.getAddress1());
            addressEntity.setAddress1(address.getAddress2());
            addressEntity.setAddress1(address.getCity());
            addressEntity.setAddress1(address.getZipCode());
            addressBookRepository.save(addressEntity);
            return true;
        } catch (EntityNotFoundException e) {
            log.warn("Address with id: {} not found", id, e);
            return false;
        }
    }

    @Override
    public void delete(String id) {
        try {
            addressBookRepository.deleteById(
                    convertToUUID(id));
        } catch (EmptyResultDataAccessException e) {
            log.warn("Address with id: {} not found", id, e);
        }
    }

    @Override
    public Optional<Address> queryAddress(String id) {
        try {
            AddressEntity addressEntity = addressBookRepository.getById(
                    convertToUUID(id));
            return Optional.of(Address.builder().address1(
                    addressEntity.getAddress1())
                    .address2(addressEntity.getAddress2())
                    .city(addressEntity.getCity())
                    .zipCode(addressEntity.getZipCode()).build());

        } catch (EntityNotFoundException e) {
            log.warn("Address with id: {} not found", id, e);
            return Optional.empty();
        }
    }

    private UUID convertToUUID(String id) {
        return UUID.fromString(id);
    }
}
