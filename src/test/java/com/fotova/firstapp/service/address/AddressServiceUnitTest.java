package com.fotova.firstapp.service.address;

import com.fotova.dto.address.AddressDto;
import com.fotova.entity.AddressEntity;
import com.fotova.repository.address.AddressRepositoryImpl;
import com.fotova.service.address.AddressMapper;
import com.fotova.service.address.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class AddressServiceUnitTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private AddressRepositoryImpl addressRepositoryImpl;

    @BeforeEach
    public void init() {
    }

    @Test
    @DisplayName("Get all addresses")
    public void getAllAddresses() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("street 1");
        addressEntity.setCity("city 1");
        addressEntity.setCountry("country 1");
        addressEntity.setNumber("number 1");

        AddressEntity addressEntityTwo = new AddressEntity();
        addressEntityTwo.setId(2);
        addressEntityTwo.setStreet("street 2");
        addressEntityTwo.setCity("city 2");
        addressEntityTwo.setCountry("country 2");
        addressEntityTwo.setNumber("number 2");

        List<AddressEntity> addressEntityList = List.of(addressEntity, addressEntityTwo);

        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("street dto 1");
        addressDto.setCity("city dto 1");
        addressDto.setCountry("country dto 1");
        addressDto.setNumber("number dto 1");

        AddressDto addressDtoTwo = new AddressDto();
        addressDtoTwo.setId(2);
        addressDtoTwo.setStreet("street dto 2");
        addressDtoTwo.setCity("city dto 2");
        addressDtoTwo.setCountry("country dto 2");
        addressDtoTwo.setNumber("number dto 2");

        List<AddressDto> addressDtoList = List.of(addressDto, addressDtoTwo);

        // WHEN
        BDDMockito.given(addressRepositoryImpl.findAll()).willReturn(addressEntityList);
        BDDMockito.given(addressMapper.mpaToAddressDtoList(addressEntityList)).willReturn(addressDtoList);
        List<AddressDto> result = addressService.getAllAddresses();

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getStreet()).isEqualTo("street dto 1");
        assertThat(result.get(1).getCountry()).isEqualTo("country dto 2");
        verify(addressRepositoryImpl, times(1)).findAll();
        verify(addressMapper,times(1)).mpaToAddressDtoList(addressEntityList);

    }

    @Test
    @DisplayName("Get address by id")
    public void getAddressById() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("street 1");
        addressEntity.setCity("city 1");
        addressEntity.setCountry("country 1");
        addressEntity.setNumber("number 1");

        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("street dto 1");
        addressDto.setCity("city dto 1");
        addressDto.setCountry("country dto 1");
        addressDto.setNumber("number dto 1");

        // WHEN
        BDDMockito.given(addressRepositoryImpl.findById(addressEntity.getId())).willReturn(addressEntity);
        BDDMockito.given(addressMapper.mpaToAddressDto(addressEntity)).willReturn(addressDto);
        AddressDto result = addressService.getAddressById(addressEntity.getId());

        // THEN
        verify(addressRepositoryImpl, times(1)).findById(addressEntity.getId());
        verify(addressMapper,times(1)).mpaToAddressDto(addressEntity);
        assertThat(result.getStreet()).isEqualTo("street dto 1");
        assertThat(result.getCity()).isEqualTo("city dto 1");
        assertThat(result.getCountry()).isEqualTo("country dto 1");
        assertThat(result.getNumber()).isEqualTo("number dto 1");
    }

    @Test
    @DisplayName("Update address client")
    public void updateAddressClientId() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("street 1");
        addressEntity.setCity("city 1");
        addressEntity.setCountry("country 1");

        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("street dto 1");
        addressDto.setCity("city dto 1");
        addressDto.setCountry("country dto 1");
        addressDto.setNumber("number dto 1");

        // WHEN
        BDDMockito.given(addressMapper.mapToAddressEntity(addressDto)).willReturn(addressEntity);
        BDDMockito.given(addressRepositoryImpl.save(addressEntity)).willReturn(addressEntity);
        BDDMockito.given(addressMapper.mpaToAddressDto(addressEntity)).willReturn(addressDto);
        addressService.addAddress(addressDto);

        // THEN
        verify(addressMapper,times(1)).mapToAddressEntity(addressDto);
        verify(addressRepositoryImpl, times(1)).save(addressEntity);
        verify(addressMapper,times(1)).mpaToAddressDto(addressEntity);
        assertThat(addressDto.getStreet()).isEqualTo("street dto 1");
        assertThat(addressDto.getCity()).isEqualTo("city dto 1");
        assertThat(addressDto.getCountry()).isEqualTo("country dto 1");
        assertThat(addressDto.getNumber()).isEqualTo("number dto 1");

    }
}