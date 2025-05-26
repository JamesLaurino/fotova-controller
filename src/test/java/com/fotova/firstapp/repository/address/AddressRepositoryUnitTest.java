package com.fotova.firstapp.repository.address;

import com.fotova.entity.AddressEntity;
import com.fotova.entity.ClientEntity;
import com.fotova.repository.address.AddressRepositoryJpa;
import com.fotova.repository.client.ClientRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AddressRepositoryUnitTest {
    @Autowired
    private AddressRepositoryJpa addressRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    private AddressEntity addressEntityOne;

    @BeforeEach
    public void init() {

        addressRepositoryJpa.deleteAll();
        clientRepositoryJpa.deleteAll();

        // GIVEN
        addressEntityOne = new AddressEntity();
        addressEntityOne.setStreet("street 1");
        addressEntityOne.setCity("city 1");
        addressEntityOne.setCountry("country 1");
        addressEntityOne.setNumber("number 1");

        addressRepositoryJpa.save(addressEntityOne);

        ClientEntity clientEntityOne = new ClientEntity();
        clientEntityOne.setAddress(addressEntityOne);
        clientEntityOne.setUsername("James");
        clientEntityOne.setPassword("<PASSWORD 1>");
        clientEntityOne.setEmail("<EMAIL 1>");
        clientEntityOne.setCreatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));
        clientEntityOne.setUpdatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));

        clientRepositoryJpa.save(clientEntityOne);

        ClientEntity clientEntityTwo = new ClientEntity();
        clientEntityTwo.setAddress(addressEntityOne);
        clientEntityTwo.setUsername("Thomas");
        clientEntityTwo.setPassword("<PASSWORD 2>");
        clientEntityTwo.setEmail("<EMAIL 2>");
        clientEntityTwo.setCreatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));
        clientEntityTwo.setUpdatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));

        clientRepositoryJpa.save(clientEntityTwo);
    }

    @Test
    @DisplayName("Clients addresses are null after updateClientAddressId")
    @Order(1)
    public void givenAddressRepository_whenFindAll_thenReturnList() {

        //WHEN
        addressRepositoryJpa.updateClientAddressId(addressEntityOne.getId());
        List<ClientEntity> clientEntityList = clientRepositoryJpa.findAll();

        // THEN
        assertThat(clientEntityList).isNotEmpty();
        assertThat(clientEntityList).hasSize(2);
        assertThat(clientEntityList).isNotNull();
        assertThat(clientEntityList).allMatch(client -> client.getAddress() == null);
    }

}
