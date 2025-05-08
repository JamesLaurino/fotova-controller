package com.fotova.firstapp.repositoryImpl.client;

import com.fotova.entity.ClientEntity;
import com.fotova.repository.client.ClientRepositoryImpl;
import com.fotova.repository.client.ClientRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClientServiceUnitTest {
    @Mock
    private ClientRepositoryJpa clientRepositoryJpa;

    @InjectMocks
    private ClientRepositoryImpl clientRepositoryImpl;

    private ClientEntity clientEntityOne = new ClientEntity();
    private ClientEntity clientEntityTwo = new ClientEntity();

    @BeforeEach
    public void init() {

        // GIVEN
        clientEntityOne.setId(1);
        clientEntityOne.setUsername("name 1");
        clientEntityOne.setEmail("email 1");
        clientEntityOne.setPassword("1234");

        clientEntityTwo.setId(2);
        clientEntityTwo.setUsername("name 2");
        clientEntityTwo.setEmail("email 2");
        clientEntityTwo.setPassword("4321");
    }

    @Test
    @DisplayName("Find all client")
    @Order(1)
    public void givenClientRepository_whenFindAll_thenReturnList() {

        // GIVEN
        BDDMockito.given(clientRepositoryJpa.findAll()).willReturn(List.of(clientEntityOne, clientEntityTwo));

        //WHEN
        List<ClientEntity> clientEntityList = clientRepositoryImpl.findAll();

        // THEN
        assertThat(clientEntityList).isNotEmpty();
        assertThat(clientEntityList).hasSize(2);
        assertThat(clientEntityList).isNotNull();
    }

    @Test
    @DisplayName("Find client by id")
    @Order(2)
    public void givenClientRepository_whenFindById_thenReturnClient() {

        // GIVEN
        BDDMockito.given(clientRepositoryJpa.findById(2)).willReturn(Optional.ofNullable(clientEntityTwo));

        // WHEN
        ClientEntity clientEntity = clientRepositoryImpl.findById(2);

        // THEN
        assertThat(clientEntity).isNotNull();
        assertThat(clientEntity.getId()).isEqualTo(2);
        assertThat(clientEntity.getUsername()).isEqualTo("name 2");
        assertThat(clientEntity.getEmail()).isEqualTo("email 2");
        assertThat(clientEntity.getPassword()).isEqualTo("4321");

    }


    @Test
    @DisplayName("Update client")
    @Order(4)
    public void givenUpdateComment_whenUpdateById_thenSuccess() {

        // GIVEN
        ClientEntity updateClient = new ClientEntity();
        updateClient.setId(1);
        updateClient.setPassword("Updated");
        updateClient.setEmail("Updated");
        updateClient.setUsername("Updated");

        Mockito.when(clientRepositoryJpa.save(clientEntityOne)).thenReturn(updateClient);

        // WHEN
        ClientEntity result = clientRepositoryImpl.update(clientEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getPassword()).isEqualTo("Updated");
        assertThat(result.getEmail()).isEqualTo("Updated");
        assertThat(result.getUsername()).isEqualTo("Updated");
        verify(clientRepositoryJpa, times(1)).save(clientEntityOne);
    }

    @Test
    @DisplayName("Exist client by username")
    @Order(5)
    public void givenUsername_whenCheckIfExists_thenBoolean() {

        // GIVEN
        BDDMockito.given(clientRepositoryJpa.existsByUsername("name 1")).willReturn(true);

        // WHEN
        Boolean result = clientRepositoryImpl.existsByUsername("name 1");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(true);
        verify(clientRepositoryJpa, times(1)).existsByUsername("name 1");
    }

    @Test
    @DisplayName("Exist client by email")
    @Order(6)
    public void givenEmail_whenCheckIfExists_thenBoolean() {

        // GIVEN
        BDDMockito.given(clientRepositoryJpa.existsByEmail("email 1")).willReturn(true);

        // WHEN
        Boolean result = clientRepositoryImpl.existsByEmail("email 1");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(true);
        verify(clientRepositoryJpa, times(1)).existsByEmail("email 1");
    }

    @Test
    @DisplayName("Fond first client by email")
    @Order(7)
    public void givenEmail_whenFindFirstByEmail_thenClient() {

        // GIVEN
        BDDMockito.given(clientRepositoryJpa.findFirstByEmail("email 2")).willReturn(Optional.ofNullable(clientEntityTwo));

        // WHEN
        Optional<ClientEntity> result = clientRepositoryImpl.findFirstByEmail("email 2");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("name 2");
        verify(clientRepositoryJpa, times(1)).findFirstByEmail("email 2");
    }

    @Test
    @DisplayName("client Anonymization")
    @Order(7)
    public void givenUserId_whenAnomymization_thenClient() {

        // GIVEN
        BDDMockito.given(clientRepositoryJpa.findById(2)).willReturn(Optional.ofNullable((clientEntityTwo)));

        // WHEN
        ClientEntity result = clientRepositoryImpl.clientAnonymization(2);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("name 2");
        verify(clientRepositoryJpa, times(1)).findById(2);
    }

}