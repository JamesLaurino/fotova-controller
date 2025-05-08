package com.fotova.firstapp.repositoryImpl.supplier;

import com.fotova.entity.SupplierEntity;
import com.fotova.repository.supplier.SupplierRepositoryImpl;
import com.fotova.repository.supplier.SupplierRepositoryJpa;
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
public class SupplierServiceUnitTest {

    @Mock
    private SupplierRepositoryJpa supplierRepositoryJpa;

    @InjectMocks
    private SupplierRepositoryImpl supplierRepositoryImpl;

    private SupplierEntity supplierEntityOne = new SupplierEntity();
    private SupplierEntity supplierEntityTwo = new SupplierEntity();

    @BeforeEach
    public void init() {

        // GIVEN
        supplierEntityOne.setId(1);
        supplierEntityOne.setRegistrationNumber("123456789");

        supplierEntityTwo.setId(1);
        supplierEntityTwo.setRegistrationNumber("987654321");
    }

    @Test
    @DisplayName("Find all supplier")
    @Order(1)
    public void givenSupplierRepository_whenFindAll_thenReturnList() {

        // GIVEN
        Mockito.when(supplierRepositoryJpa.findAll()).thenReturn(List.of(supplierEntityOne, supplierEntityTwo));

        // WHEN
        List<SupplierEntity> supplierEntityList  = supplierRepositoryImpl.findAll();

        // THEN
        assertThat(supplierEntityList).isNotEmpty();
        assertThat(supplierEntityList).hasSize(2);
        assertThat(supplierEntityList).isNotNull();
        verify(supplierRepositoryJpa, times(1)).findAll();
    }

    @Test
    @DisplayName("Find supplier by id")
    @Order(2)
    public void whenFindById_thenReturnSupplier() {

        // GIVEN
        BDDMockito.given(supplierRepositoryJpa.findById(1)).willReturn(Optional.ofNullable(supplierEntityOne));

        // WHEN
        SupplierEntity supplierEntity = supplierRepositoryImpl.findById(1);

        // THEN
        assertThat(supplierEntity).isNotNull();
        assertThat(supplierEntity.getId()).isEqualTo(1);
        assertThat(supplierEntity.getRegistrationNumber()).isEqualTo("123456789");
        verify(supplierRepositoryJpa, times(1)).findById(1);
    }

    @Test
    @DisplayName("Delete supplier by id")
    @Order(3)
    public void givenDeleteComment_whenDeleteById_thenSuccess() {
        // WHEN
        BDDMockito.willDoNothing().given(supplierRepositoryJpa).deleteById(1);
        supplierRepositoryImpl.deleteById(1);

        // THEN : vérifier que la méthode est utilisée avec le bon paramètre
        verify(supplierRepositoryJpa, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Update supplier")
    @Order(4)
    public void givenUpdateComment_whenUpdateById_thenSuccess() {

        // GIVEN
        SupplierEntity supplierEntityUpdate = new SupplierEntity();
        supplierEntityUpdate.setId(1);
        supplierEntityUpdate.setRegistrationNumber("Updated");

        Mockito.when(supplierRepositoryJpa.save(supplierEntityOne)).thenReturn(supplierEntityUpdate);

        // WHEN
        SupplierEntity result = supplierRepositoryImpl.update(supplierEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getRegistrationNumber()).isEqualTo("Updated");
        verify(supplierRepositoryJpa, times(1)).save(supplierEntityOne);
    }

    @Test
    @DisplayName("update Supplier Address Id")
    @Order(5)
    public void given_whenSupplierAddress_thenAddressIsNull() {

        // WHEN
        BDDMockito.willDoNothing().given(supplierRepositoryJpa)
                .updateSupplierAddressId(supplierEntityTwo.getId());

        supplierRepositoryImpl.updateSupplierAddressId(supplierEntityTwo.getId());


        // THEN
        verify(supplierRepositoryJpa, times(1))
                .updateSupplierAddressId(supplierEntityTwo.getId());
    }

    @Test
    @DisplayName("update Supplier Product Id")
    @Order(6)
    public void given_whenUpdateSupplierProduct_thenProductIsNull() {

        // WHEN
        BDDMockito.willDoNothing().given(supplierRepositoryJpa)
                .updateSupplierProductId(supplierEntityTwo.getId());

        supplierRepositoryImpl.updateSupplierProductId(supplierEntityTwo.getId());


        // THEN
        verify(supplierRepositoryJpa, times(1))
                .updateSupplierProductId(supplierEntityTwo.getId());
    }
}
