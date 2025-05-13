package com.fotova.firstapp.service.supplier;

import com.fotova.dto.supplier.SupplierDto;
import com.fotova.dto.supplier.SupplierProductDto;
import com.fotova.entity.AddressEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.entity.SupplierEntity;
import com.fotova.service.supplier.SupplierMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SupplierMapperServiceUnitTest
{
    private SupplierMapper supplierMapper = new SupplierMapper();

    @Test
    @DisplayName("Map supplier entity to supplier dto")
    void mapToDto() {
        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Product 1");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(10);
        productEntity.setUrl("url 1");


        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");
        supplierEntity.setAddress(addressEntity);
        supplierEntity.setProduct(productEntity);

        SupplierEntity supplierEntityTwo = new SupplierEntity();
        supplierEntityTwo.setId(2);
        supplierEntityTwo.setRegistrationNumber("654321");

        // WHEN
        SupplierDto supplierDto = supplierMapper.mapToDto(supplierEntity);
        SupplierDto supplierDtoTwo = supplierMapper.mapToDto(supplierEntityTwo);

        // THEN
        assertThat(supplierDto).isNotNull();
        assertThat(supplierDtoTwo).isNotNull();

        assertThat(supplierDto.getId()).isEqualTo(supplierEntity.getId());
        assertThat(supplierDtoTwo.getId()).isEqualTo(supplierEntityTwo.getId());

        assertThat(supplierDto.getRegistrationNumber()).isEqualTo(supplierEntity.getRegistrationNumber());
        assertThat(supplierDtoTwo.getRegistrationNumber()).isEqualTo(supplierEntityTwo.getRegistrationNumber());

        assertThat(supplierDtoTwo.getSupplierProductDto()).isNull();
        assertThat(supplierDtoTwo.getSupplierAddressDto()).isNull();

        assertThat(supplierDto.getSupplierProductDto().getId()).isEqualTo(supplierEntity.getProduct().getId());
        assertThat(supplierDto.getSupplierProductDto().getUrl()).isEqualTo(supplierEntity.getProduct().getUrl());
        assertThat(supplierDto.getSupplierAddressDto().getNumber()).isEqualTo(supplierEntity.getAddress().getNumber());
    }

    @Test
    @DisplayName("Map supplier entity list to supplier dto List")
    void mapToDtoList() {
        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Product 1");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(10);
        productEntity.setUrl("url 1");


        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");
        supplierEntity.setAddress(addressEntity);
        supplierEntity.setProduct(productEntity);

        SupplierEntity supplierEntityTwo = new SupplierEntity();
        supplierEntityTwo.setId(2);
        supplierEntityTwo.setRegistrationNumber("654321");

        // WHEN
        List<SupplierEntity> supplierEntityList = List.of(supplierEntity, supplierEntityTwo);
        List<SupplierDto> supplierDtoList = supplierMapper.mapToDtoList(supplierEntityList);

        assertThat(supplierDtoList).hasSize(2);
        assertThat(supplierDtoList).isNotNull();
    }

    @Test
    @DisplayName("Map supplier dto to supplier entity")
    void mapToEntity() {

        // GIVEN
        SupplierProductDto supplierProductDto = new SupplierProductDto();
        supplierProductDto.setId(1);
        supplierProductDto.setUrl("url 1");
        supplierProductDto.setName("Product 1");
        supplierProductDto.setPrice(100.0);
        supplierProductDto.setQuantity(10);


        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("123456");

        SupplierDto supplierDtoTwo = new SupplierDto();
        supplierDtoTwo.setId(2);
        supplierDtoTwo.setRegistrationNumber("654321");
        supplierDtoTwo.setSupplierProductDto(supplierProductDto);

        // WHEN
        SupplierEntity supplierEntity = supplierMapper.mapToEntity(supplierDto);
        SupplierEntity supplierEntityTwo = supplierMapper.mapToEntity(supplierDtoTwo);

        assertThat(supplierEntity).isNotNull();
        assertThat(supplierEntityTwo).isNotNull();

        assertThat(supplierEntity.getId()).isEqualTo(supplierDto.getId());
        assertThat(supplierEntityTwo.getId()).isEqualTo(supplierDtoTwo.getId());

        assertThat(supplierEntity.getRegistrationNumber()).isEqualTo(supplierDto.getRegistrationNumber());
        assertThat(supplierEntityTwo.getRegistrationNumber()).isEqualTo(supplierDtoTwo.getRegistrationNumber());

        assertThat(supplierEntityTwo.getProduct()).isNotNull();
    }
}
