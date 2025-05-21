package com.fotova.firstapp.service.supplier;

import com.fotova.dto.address.AddressDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.dto.supplier.SupplierDto;
import com.fotova.dto.supplier.SupplierProductDto;
import com.fotova.entity.AddressEntity;
import com.fotova.entity.ProductEntity;
import com.fotova.entity.SupplierEntity;
import com.fotova.repository.address.AddressRepositoryImpl;
import com.fotova.repository.product.ProductRepositoryImpl;
import com.fotova.repository.supplier.SupplierRepositoryImpl;
import com.fotova.service.address.AddressMapper;
import com.fotova.service.product.ProductMapper;
import com.fotova.service.supplier.SupplierMapper;
import com.fotova.service.supplier.SupplierService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class SupplierServiceUnitTest {

    @InjectMocks
    private SupplierService supplierService;

    @Mock
    private SupplierRepositoryImpl supplierRepositoryImpl;

    @Mock
    private AddressRepositoryImpl addressRepositoryImpl;

    @Mock
    private ProductRepositoryImpl productRepositoryImpl;

    @Mock
    private SupplierMapper supplierMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private ProductMapper productMapper;

    @Test
    @DisplayName("findAll")
    public void findAll() {
        //GIVEN
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

        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");

        SupplierEntity supplierEntityTwo = new SupplierEntity();
        supplierEntityTwo.setId(2);
        supplierEntityTwo.setRegistrationNumber("654321");

        // WHEN
        BDDMockito.given(supplierRepositoryImpl.findAll()).willReturn(List.of(supplierEntity, supplierEntityTwo));
        BDDMockito.given(supplierMapper.mapToDtoList(List.of(supplierEntity, supplierEntityTwo)))
                .willReturn(List.of(supplierDto, supplierDtoTwo));
        List<SupplierDto> supplierDtoList = supplierService.findAll();

        // THEN
        verify(supplierRepositoryImpl, times(1)).findAll();
        verify(supplierMapper, times(1)).mapToDtoList(List.of(supplierEntity, supplierEntityTwo));

        assertThat(supplierDtoList).isNotNull();
        assertThat(supplierDtoList).hasSize(2);
        assertThat(supplierDtoList).contains(supplierDto, supplierDtoTwo);
    }

    @Test
    @DisplayName("findById")
    public void findById() {
        // GIVEN
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("123456");

        // WHEN
        BDDMockito.given(supplierRepositoryImpl.findById(supplierEntity.getId())).willReturn(supplierEntity);
        BDDMockito.given(supplierMapper.mapToDto(supplierEntity)).willReturn(supplierDto);
        SupplierDto supplierDtoResult = supplierService.findById(supplierEntity.getId());

        // THEN
        verify(supplierRepositoryImpl, times(1)).findById(supplierEntity.getId());
        verify(supplierMapper, times(1)).mapToDto(supplierEntity);

        assertThat(supplierDtoResult).isNotNull();
        assertThat(supplierDtoResult).isEqualTo(supplierDto);
        assertThat(supplierDtoResult.getId()).isEqualTo(supplierEntity.getId());
        assertThat(supplierDtoResult.getRegistrationNumber()).isEqualTo(supplierEntity.getRegistrationNumber());
    }

    @Test
    @DisplayName("save")
    public void save() {
        // GIVEN
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("123456");

        // WHEN
        BDDMockito.given(supplierMapper.mapToEntity(supplierDto)).willReturn(supplierEntity);
        BDDMockito.given(supplierRepositoryImpl.save(supplierEntity)).willReturn(supplierEntity);
        SupplierDto supplierDtoResult = supplierService.save(supplierDto);

        // THEN
        verify(supplierMapper, times(1)).mapToEntity(supplierDto);
        verify(supplierRepositoryImpl, times(1)).save(supplierEntity);

        assertThat(supplierDtoResult).isNotNull();
        assertThat(supplierDtoResult.getId()).isEqualTo(supplierEntity.getId());
        assertThat(supplierDtoResult.getRegistrationNumber()).isEqualTo(supplierEntity.getRegistrationNumber());
        assertThat(supplierDtoResult.getSupplierProductDto()).isNull();
        assertThat(supplierDtoResult.getSupplierAddressDto()).isNull();

    }

    @Test
    @DisplayName("update")
    public void update() {
        // GIVEN
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("123456");

        // WHEN
        BDDMockito.given(supplierMapper.mapToEntity(supplierDto)).willReturn(supplierEntity);
        BDDMockito.given(supplierRepositoryImpl.save(supplierEntity)).willReturn(supplierEntity);
        BDDMockito.given(supplierMapper.mapToDto(supplierEntity)).willReturn(supplierDto);
        SupplierDto supplierDtoResult = supplierService.update(supplierDto);

        // THEN
        verify(supplierMapper, times(1)).mapToEntity(supplierDto);
        verify(supplierRepositoryImpl,times(1)).save(supplierEntity);

        assertThat(supplierDtoResult).isNotNull();
        assertThat(supplierDtoResult.getRegistrationNumber()).isEqualTo(supplierDto.getRegistrationNumber());

    }

    @Test
    @DisplayName("delete")
    public void delete() {
        // WHEN
        BDDMockito.willDoNothing().given(supplierRepositoryImpl).updateSupplierAddressId(1);
        BDDMockito.willDoNothing().given(supplierRepositoryImpl).updateSupplierProductId(1);
        BDDMockito.willDoNothing().given(supplierRepositoryImpl).deleteById(1);
        supplierService.delete(1);

        //THEN
        verify(supplierRepositoryImpl, times(1)).updateSupplierAddressId(1);
        verify(supplierRepositoryImpl, times(1)).updateSupplierProductId(1);
        verify(supplierRepositoryImpl, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("addSupplierAddress")
    public void addSupplierAddress() {
        // GIVEN
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        SupplierProductDto supplierProductDto = new SupplierProductDto();
        supplierProductDto.setId(1);
        supplierProductDto.setUrl("url 1");
        supplierProductDto.setName("Product 1");
        supplierProductDto.setPrice(100.0);
        supplierProductDto.setQuantity(10);

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("123456");

        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("Street 1");
        addressDto.setCity("City 1");
        addressDto.setCountry("Country 1");
        addressDto.setNumber("Number 1");


        //WHEN
        BDDMockito.given(supplierRepositoryImpl.findById(supplierEntity.getId())).willReturn(supplierEntity);
        BDDMockito.given(addressMapper.mapToAddressEntity(addressDto)).willReturn(addressEntity);
        BDDMockito.given(addressRepositoryImpl.save(addressEntity)).willReturn(addressEntity);
        BDDMockito.given(supplierRepositoryImpl.save(supplierEntity)).willReturn(supplierEntity);
        BDDMockito.given(supplierMapper.mapToDto(supplierEntity)).willReturn(supplierDto);
        supplierService.addSupplierAddress(supplierEntity.getId(), addressDto);

        // THEN
        verify(supplierRepositoryImpl, times(1)).findById(supplierEntity.getId());
        verify(supplierRepositoryImpl, times(1)).save(supplierEntity);
        verify(supplierMapper, times(1)).mapToDto(supplierEntity);
        verify(addressMapper, times(1)).mapToAddressEntity(addressDto);
        verify(addressRepositoryImpl, times(1)).save(addressEntity);
        assertThat(supplierEntity).isNotNull();
        assertThat(supplierEntity.getAddress()).isNotNull();
        assertThat(supplierEntity.getAddress().getId()).isEqualTo(addressEntity.getId());
    }

    @Test
    @DisplayName("addSupplierProduct")
    public void addSupplierProduct() {
        //GIVEN
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(1);
        supplierEntity.setRegistrationNumber("123456");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(1);
        productEntity.setName("Product 1");
        productEntity.setPrice(100.0);
        productEntity.setQuantity(10);
        productEntity.setUrl("url 1");

        ProductDtoBack productDtoBack = new ProductDtoBack();
        productDtoBack.setId(1);
        productDtoBack.setName("Product 1");
        productDtoBack.setPrice(100.0);
        productDtoBack.setQuantity(10);
        productDtoBack.setUrl("url 1");

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("123456");


        // WHEN
        BDDMockito.given(supplierRepositoryImpl.findById(supplierEntity.getId())).willReturn(supplierEntity);
        BDDMockito.given(productMapper.mapToProductEntity(productDtoBack)).willReturn(productEntity);
        BDDMockito.given(productRepositoryImpl.save(productEntity)).willReturn(productEntity);
        BDDMockito.given(supplierRepositoryImpl.save(supplierEntity)).willReturn(supplierEntity);
        BDDMockito.given(supplierMapper.mapToDto(supplierEntity)).willReturn(supplierDto);
        supplierService.addSupplierProduct(supplierEntity.getId(), productDtoBack);

        // THEN
        verify(supplierRepositoryImpl, times(1)).findById(supplierEntity.getId());
        verify(supplierRepositoryImpl, times(1)).save(supplierEntity);
        verify(supplierMapper, times(1)).mapToDto(supplierEntity);
        verify(productMapper, times(1)).mapToProductEntity(productDtoBack);
        verify(productRepositoryImpl, times(1)).save(productEntity);
        assertThat(supplierEntity).isNotNull();
        assertThat(supplierEntity.getProduct()).isNotNull();
        assertThat(supplierEntity.getProduct().getId()).isEqualTo(productEntity.getId());
        assertThat(supplierEntity.getProduct().getUrl()).isEqualTo(productEntity.getUrl());

    }
}
