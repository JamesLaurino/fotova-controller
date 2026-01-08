package com.fotova.firstapp.repository.supplier;

import com.fotova.entity.AddressEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.entity.SupplierEntity;
import com.fotova.repository.address.AddressRepositoryJpa;
import com.fotova.repository.product.ProductRepositoryJpa;
import com.fotova.repository.supplier.SupplierRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SupplierRepositoryUnitTest {

    @Autowired
    private SupplierRepositoryJpa supplierRepositoryJpa;
    @Autowired
    private ProductRepositoryJpa productRepositoryJpa;
    @Autowired
    private AddressRepositoryJpa addressRepositoryJpa;

    private SupplierEntity supplierEntityTwo;

    @BeforeEach
    public void init() {

        // GIVEN
        ProductEntity productEntityOne = new ProductEntity();
        productEntityOne.setName("product 1");
        productEntityOne.setPrice(100.0);
        productEntityOne.setQuantity(4);
        productEntityOne.setUrl("url 1");

        ProductEntity productEntityTwo = new ProductEntity();
        productEntityTwo.setName("product 2");
        productEntityTwo.setPrice(100.0);
        productEntityTwo.setQuantity(4);
        productEntityTwo.setUrl("url 2");

        productRepositoryJpa.saveAll(List.of(productEntityOne, productEntityTwo));

        AddressEntity addressEntityOne = new AddressEntity();
        addressEntityOne.setStreet("street 1");
        addressEntityOne.setCity("city 1");
        addressEntityOne.setCountry("country 1");
        addressEntityOne.setNumber("number 1");

        AddressEntity addressEntityTwo = new AddressEntity();
        addressEntityTwo.setStreet("street 2");
        addressEntityTwo.setCity("city 2");
        addressEntityTwo.setCountry("country 2");
        addressEntityTwo.setNumber("number 2");

        addressRepositoryJpa.saveAll(List.of(addressEntityOne, addressEntityTwo));

        SupplierEntity supplierEntityOne = new SupplierEntity();
        supplierEntityOne.setRegistrationNumber("1234");
        supplierEntityOne.setAddress(addressEntityOne);
        supplierEntityOne.setProduct(productEntityOne);

        supplierEntityTwo = new SupplierEntity();
        supplierEntityTwo.setRegistrationNumber("5678");
        supplierEntityTwo.setAddress(addressEntityTwo);
        supplierEntityTwo.setProduct(productEntityTwo);

        supplierRepositoryJpa.saveAll(List.of(supplierEntityOne, supplierEntityTwo));
    }

    @Test
    @DisplayName("Supplier products are null after updateSupplierProductId")
    @Order(1)
    public void updateSupplierProductId() {
        // WHEN
        supplierRepositoryJpa.updateSupplierProductId(supplierEntityTwo.getId());
        List<SupplierEntity> supplierEntityList = supplierRepositoryJpa.findAll();

        // THEN

        supplierEntityList.forEach((supplier) -> {
            if(supplier.getProduct() != null)
                assertThat(supplier.getId()).isNotEqualTo(supplierEntityTwo.getId());

            if(supplier.getId().equals(supplierEntityTwo.getId()))
                assertThat(supplier.getProduct()).isNull();

            if(supplier.getProduct() == null)
                assertThat(supplier.getId()).isEqualTo(supplierEntityTwo.getId());
        });
    }

    @Test
    @DisplayName("Supplier adress is null after updateSupplierAddressId")
    @Order(2)
    public void updateSupplierAddressId() {
        // WHEN
        supplierRepositoryJpa.updateSupplierAddressId(supplierEntityTwo.getId());
        List<SupplierEntity> supplierEntityList = supplierRepositoryJpa.findAll();

        // THEN

        supplierEntityList.forEach((supplier) -> {
            if(supplier.getAddress() != null)
                assertThat(supplier.getId()).isNotEqualTo(supplierEntityTwo.getId());

            if(supplier.getId().equals(supplierEntityTwo.getId()))
                assertThat(supplier.getAddress()).isNull();

            if(supplier.getAddress() == null)
                assertThat(supplier.getId()).isEqualTo(supplierEntityTwo.getId());
        });
    }

    /*
    @Modifying
    @Transactional
    @Query("UPDATE SupplierEntity s SET s.address = null WHERE s.id = ?1")
    void updateSupplierAddressId(Integer supplierID);
    * */
}
