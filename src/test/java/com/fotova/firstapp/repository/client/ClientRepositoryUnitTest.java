package com.fotova.firstapp.repository.client;

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
public class ClientRepositoryUnitTest {

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    @Autowired
    private AddressRepositoryJpa addressRepositoryJpa;
    private  ClientEntity clientEntityTwo;


    @BeforeEach
    public void init() {
        clientRepositoryJpa.deleteAll();
        addressRepositoryJpa.deleteAll();

        // GIVEN
        AddressEntity addressEntityOne = new AddressEntity();
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

        clientEntityTwo = new ClientEntity();
        clientEntityTwo.setAddress(addressEntityOne);
        clientEntityTwo.setUsername("Thomas");
        clientEntityTwo.setPassword("<PASSWORD 2>");
        clientEntityTwo.setEmail("<EMAIL 2>");
        clientEntityTwo.setCreatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));
        clientEntityTwo.setUpdatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));

        ClientEntity clientEntityThree = new ClientEntity();
        clientEntityThree.setAddress(addressEntityOne);
        clientEntityThree.setUsername("Thomas");
        clientEntityThree.setPassword("<PASSWORD 3>");
        clientEntityThree.setEmail("<EMAIL 3>");
        clientEntityThree.setCreatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));
        clientEntityThree.setUpdatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));

        clientRepositoryJpa.save(clientEntityTwo);
        clientRepositoryJpa.save(clientEntityThree);

    }
    @Test
    @DisplayName("Client addresses is null after updateUpdateClient (objectif => supprimer le client)")
    @Order(1)
    public void givenAddressRepository_whenFindAll_thenReturnList() {

        //WHEN
        clientRepositoryJpa.updateClientAddress(clientEntityTwo.getId());
        List<ClientEntity> clientEntityList = clientRepositoryJpa.findAll();

        // THEN
        assertThat(clientEntityList).isNotEmpty();
        assertThat(clientEntityList).hasSize(3);
        assertThat(clientEntityList).isNotNull();

        clientEntityList.forEach((client) -> {
            if( client.getAddress() != null)
                assertThat(client.getId()).isNotEqualTo(clientEntityTwo.getId());

            if(client.getId().equals(clientEntityTwo.getId()))
                assertThat(client.getAddress()).isNull();

            if(client.getAddress() == null)
                assertThat(client.getId()).isEqualTo(clientEntityTwo.getId());
        });

    }
}
