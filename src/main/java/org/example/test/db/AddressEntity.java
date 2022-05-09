package org.example.test.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {
    @Id
    private UUID id;
    private String address1;
    private String address2;
    private String city;
    private String zipCode;

}
