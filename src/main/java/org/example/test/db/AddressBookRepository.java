package org.example.test.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressBookRepository extends JpaRepository<AddressEntity, UUID> {

}
