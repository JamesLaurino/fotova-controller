package com.fotova.firstapp.service.client;

import com.fotova.dto.client.ClientDto;
import com.fotova.entity.AddressEntity;
import com.fotova.entity.ClientEntity;
import com.fotova.entity.CommentEntity;
import com.fotova.service.client.ClientMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientMapperServiceUnitTest {
    private ClientMapper clientMapper = new ClientMapper();

    @Test
    @DisplayName("maptoClientDtoList")
    public void maptoClientDtoList() {

        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1);
        commentEntity.setBody("Body entity 1");
        commentEntity.setHeader("Header entity 1");
        commentEntity.setCreateAt(Instant.now());
        commentEntity.setUpdateAt(Instant.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL> 1");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD> 1");
        clientEntity.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntity.setCreatedAt(Timestamp.from(Instant.now()));
        clientEntity.setAddress(addressEntity);
        clientEntity.setCommentEntities(List.of(commentEntity));

        ClientEntity clientEntityTwo = new ClientEntity();
        clientEntityTwo.setId(2);
        clientEntityTwo.setEmail("<EMAIL> 2");
        clientEntityTwo.setUsername("thomas 2");
        clientEntityTwo.setPassword("<PASSWORD> 2");
        clientEntityTwo.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntityTwo.setCreatedAt(Timestamp.from(Instant.now()));

        // WHEN
        List<ClientEntity> clientEntityList = List.of(clientEntity, clientEntityTwo);
        List<ClientDto> clientDtoList = clientMapper.maptoClientDtoList(clientEntityList);

        // THEN
        assertThat(clientDtoList).isNotNull();
        assertThat(clientDtoList).isNotEmpty();
        assertThat(clientDtoList).hasSize(2);

        assertThat(clientDtoList.get(0).getId()).isEqualTo(clientEntity.getId());
        assertThat(clientDtoList.get(0).getEmail()).isEqualTo(clientEntity.getEmail());

        assertThat(clientDtoList.get(1).getId()).isEqualTo(clientEntityTwo.getId());
        assertThat(clientDtoList.get(1).getEmail()).isEqualTo(clientEntityTwo.getEmail());

        assertThat(clientDtoList.get(0).getCommentEntities()).hasSize(1);
        assertThat(clientDtoList.get(0).getCommentEntities().get(0).getBody()).isEqualTo("Body entity 1");
        assertThat(clientDtoList.get(0).getAddress().getCity()).isEqualTo("City 1");

        assertThat(clientDtoList.get(1).getAddress()).isNull();
        assertThat(clientDtoList.get(1).getCommentEntities()).isNull();
    }

    @Test
    @DisplayName("mapAddressToAddressEntity")
    public void anonymization() {
        // GIVEN
        ClientEntity clientEntityTwo = new ClientEntity();
        clientEntityTwo.setId(2);
        clientEntityTwo.setEmail("<EMAIL> 2");
        clientEntityTwo.setUsername("thomas 2");
        clientEntityTwo.setPassword("<PASSWORD> 2");

        // WHEN
        ClientEntity clientEntity = clientMapper.anonymization(clientEntityTwo);

        // THEN
        assertThat(clientEntity).isNotNull();
        assertThat(clientEntity.getUsername()).isEqualTo(clientEntityTwo.getUsername());
        assertThat(clientEntity.getPassword()).isEqualTo(clientEntityTwo.getPassword());
        assertThat(clientEntity.getEmail()).isEqualTo(clientEntityTwo.getEmail());
        assertThat(clientEntity.getEmail().length()).isEqualTo(36);
        assertThat(clientEntity.getPassword().length()).isEqualTo(36);
        assertThat(clientEntity.getUsername().length()).isEqualTo(36);
    }

}
