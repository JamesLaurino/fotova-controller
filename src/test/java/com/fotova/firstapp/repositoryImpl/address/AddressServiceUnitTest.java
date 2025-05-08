package com.fotova.firstapp.repositoryImpl.address;

import com.fotova.entity.AddressEntity;
import com.fotova.entity.ClientEntity;
import com.fotova.repository.address.AddressRepositoryImpl;
import com.fotova.repository.address.AddressRepositoryJpa;
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
public class AddressServiceUnitTest {

    @Mock
    private AddressRepositoryJpa addressRepositoryJpa;

    @InjectMocks
    private AddressRepositoryImpl addressRepositoryImpl;

    private AddressEntity addressEntityOne = new AddressEntity();
    private AddressEntity addressEntityTwo = new AddressEntity();

    @BeforeEach
    public void init() {
        // GIVEN
        addressEntityOne.setId(1);
        addressEntityOne.setStreet("street 1");
        addressEntityOne.setCity("city 1");
        addressEntityOne.setCountry("country 1");
        addressEntityOne.setNumber("number 1");

        addressEntityTwo.setId(2);
        addressEntityTwo.setStreet("street 2");
        addressEntityTwo.setCity("city 2");
        addressEntityTwo.setCountry("country 2");
        addressEntityTwo.setNumber("number 2");
    }

    @Test
    @DisplayName("Find all address")
    @Order(1)
    public void givenAddressRepository_whenFindAll_thenReturnList() {
        // GIVEN
        BDDMockito.given(addressRepositoryJpa.findAll()).willReturn(List.of(addressEntityOne, addressEntityTwo));

        //WHEN
        List<AddressEntity> addressEntityList = addressRepositoryImpl.findAll();

        // THEN
        assertThat(addressEntityList).isNotEmpty();
        assertThat(addressEntityList).hasSize(2);
        assertThat(addressEntityList).isNotNull();
    }

    @Test
    @DisplayName("Find address by id")
    @Order(2)
    public void givenAddressRepository_whenFindById_thenReturnAddress() {

        // GIVEN
        BDDMockito.given(addressRepositoryJpa.findById(2)).willReturn(Optional.ofNullable(addressEntityTwo));

        // WHEN
        AddressEntity addressEntity = addressRepositoryImpl.findById(2);

        // THEN
        assertThat(addressEntity).isNotNull();
        assertThat(addressEntity.getId()).isEqualTo(2);
        assertThat(addressEntity.getCity()).isEqualTo("city 2");
        assertThat(addressEntity.getCountry()).isEqualTo("country 2");
        assertThat(addressEntity.getStreet()).isEqualTo("street 2");
        assertThat(addressEntity.getNumber()).isEqualTo("number 2");
    }

    @Test
    @DisplayName("Delete address by id")
    @Order(3)
    public void givenDeleteAllAddress_whenDeleteById_thenSuccess() {
        // WHEN
        BDDMockito.willDoNothing().given(addressRepositoryJpa).deleteById(2);
        addressRepositoryImpl.deleteById(2);

        // THEN
        verify(addressRepositoryJpa, times(1)).deleteById(2);
    }

    @Test
    @DisplayName("Update addresss")
    @Order(4)
    public void givenUpdateAddress_whenUpdateById_thenSuccess() {

        // GIVEN
        AddressEntity addressUpdate = new AddressEntity();
        addressUpdate.setId(1);
        addressUpdate.setStreet("Updated");
        addressUpdate.setCity("Updated");
        addressUpdate.setCountry("Updated");
        addressUpdate.setNumber("Updated");

        Mockito.when(addressRepositoryJpa.save(addressEntityOne)).thenReturn(addressUpdate);

        // WHEN
        AddressEntity result = addressRepositoryImpl.update(addressEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo("Updated");
        assertThat(result.getStreet()).isEqualTo("Updated");
        assertThat(result.getCity()).isEqualTo("Updated");
        assertThat(result.getCountry()).isEqualTo("Updated");
        verify(addressRepositoryJpa, times(1)).save(addressEntityOne);
    }

    @Test
    @DisplayName("update address client id")
    @Order(5)
    public void given_whenAddressId_thenClientIsNull() {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setUsername("Thomas");
        clientEntity.setPassword("<PASSWORD>");
        clientEntity.setEmail("<EMAIL>");
        clientEntity.setAddress(addressEntityOne);

        // WHEN
        BDDMockito.willDoNothing().given(addressRepositoryJpa)
                .updateClientAddressId(clientEntity.getAddress().getId());

        addressRepositoryImpl.updateClientAddressId(addressEntityOne.getId());


        // THEN
        verify(addressRepositoryJpa, times(1))
                .updateClientAddressId(addressEntityOne.getId());
    }
}
