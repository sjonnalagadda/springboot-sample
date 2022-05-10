package org.example.test.service.impl;


import org.example.test.db.AddressBookRepository;
import org.example.test.db.AddressEntity;
import org.example.test.service.Address;
import org.example.test.service.AddressBookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AddressBookServiceImplTests {

    @Mock
    private AddressBookRepository addressBookRepository;

    private AddressBookService addressBookService;

    @BeforeEach
    void init() {
        addressBookService = new AddressBookServiceImpl();
        ReflectionTestUtils.setField(addressBookService,
                "addressBookRepository", addressBookRepository);
    }

    @Test
    public void whenAddingAddress() {
        addressBookService.addAddress(address());
        Mockito.verify(addressBookRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void whenUpdatingAddressWithoutRecord() {
        Mockito.when(addressBookRepository.getById(Mockito.any())).thenThrow(EntityNotFoundException.class);
        Assertions.assertFalse(addressBookService.updateAddress(UUID.randomUUID().toString(), address()));
    }

    @Test
    public void whenUpdatingAddress() {
        Mockito.when(addressBookRepository.getById(Mockito.any())).thenReturn(addressFromDb());
        Assertions.assertTrue(addressBookService.updateAddress(UUID.randomUUID().toString(), address()));
        Mockito.verify(addressBookRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void whenQueryingAndRecordNotFound() {
        Mockito.when(addressBookRepository.getById(Mockito.any())).thenThrow(EntityNotFoundException.class);
        Assertions.assertTrue(addressBookService.queryAddress(UUID.randomUUID().toString()).isEmpty());
    }

    @Test
    public void whenQueryingAndRecordFound() {
        Mockito.when(addressBookRepository.getById(Mockito.any())).thenReturn(addressFromDb());
        Assertions.assertTrue(addressBookService.queryAddress(UUID.randomUUID().toString()).isPresent());
    }


    private Address address() {
        return Address.builder().address1("1 North Pole").city("NA").zipCode("00001").build();
    }
    private AddressEntity addressFromDb() {
        return AddressEntity.builder().id(UUID.randomUUID().toString())
                .address1("1 North Pole").city("NA").zipCode("00001").build();
    }
}
