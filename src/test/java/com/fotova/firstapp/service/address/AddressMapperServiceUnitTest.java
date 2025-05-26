package com.fotova.firstapp.service.address;

import com.fotova.dto.address.AddressDto;
import com.fotova.entity.AddressEntity;
import com.fotova.service.address.AddressMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AddressMapperServiceUnitTest {
    private AddressMapper addressMapper = new AddressMapper();

    @Test
    @DisplayName("Map to address dto")
    public void mapToAddressDto() {

        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setStreet("street 1");
        addressEntity.setCity("city 1");
        addressEntity.setCountry("country 1");
        addressEntity.setNumber("number 1");

        // WHEN
        AddressDto addressDto = addressMapper.mpaToAddressDto(addressEntity);

        // THEN
        assertThat(addressDto.getStreet()).isEqualTo("street 1");
        assertThat(addressDto.getCity()).isEqualTo("city 1");
        assertThat(addressDto.getCountry()).isEqualTo("country 1");
        assertThat(addressDto.getNumber()).isEqualTo("number 1");
    }

    @Test
    @DisplayName("Map to address entity")
    public void mapToAddressEntity() {

        // GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("street 1");
        addressDto.setCity("city 1");
        addressDto.setCountry("country 1");
        addressDto.setNumber("number 1");

        // WHEN
        AddressEntity addressEntity = addressMapper.mapToAddressEntity(addressDto);

        // THEN
        assertThat(addressEntity.getStreet()).isEqualTo("street 1");
        assertThat(addressEntity.getCity()).isEqualTo("city 1");
        assertThat(addressEntity.getCountry()).isEqualTo("country 1");
        assertThat(addressEntity.getNumber()).isEqualTo("number 1");
        assertThat(addressEntity.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Map to address dto list")
    public void mpaToAddressDtoList() {
        // GIVEN
        AddressEntity addressEntityOne = new AddressEntity();
        addressEntityOne.setId(1);
        addressEntityOne.setStreet("street 1");
        addressEntityOne.setCity("city 1");
        addressEntityOne.setCountry("country 1");
        addressEntityOne.setNumber("number 1");

        AddressEntity addressEntityTwo = new AddressEntity();
        addressEntityTwo.setId(2);
        addressEntityTwo.setStreet("street 2");
        addressEntityTwo.setCity("city 2");
        addressEntityTwo.setCountry("country 2");
        addressEntityTwo.setNumber("number 2");

        AddressEntity addressEntityThree = new AddressEntity();
        addressEntityThree.setId(3);
        addressEntityThree.setStreet("street 3");
        addressEntityThree.setCity("city 3");
        addressEntityThree.setCountry("country 3");
        addressEntityThree.setNumber("number 3");

        List<AddressEntity> addressEntityList = List.of(addressEntityOne, addressEntityTwo, addressEntityThree);

        // WHEN
        List<AddressDto> addressDtoList = addressMapper.mpaToAddressDtoList(addressEntityList);

        // THEN
        addressDtoList.forEach((addressDto) -> {
            if(addressDto.getId() == 1)
            {
                assertThat(addressDto.getStreet()).isEqualTo("street 1");
                assertThat(addressDto.getCity()).isEqualTo("city 1");
                assertThat(addressDto.getNumber()).isEqualTo("number 1");
            }

            if(addressDto.getId() == 2)
            {
                assertThat(addressDto.getStreet()).isEqualTo("street 2");
                assertThat(addressDto.getCity()).isEqualTo("city 2");
                assertThat(addressDto.getNumber()).isEqualTo("number 2");
            }

            if(addressDto.getId() == 3)
            {
                assertThat(addressDto.getStreet()).isEqualTo("street 3");
                assertThat(addressDto.getCity()).isEqualTo("city 3");
                assertThat(addressDto.getNumber()).isEqualTo("number 3");
            }

        });
        Assertions.assertThat(addressDtoList).isNotEmpty();
        Assertions.assertThat(addressDtoList).hasSize(3);
        Assertions.assertThat(addressDtoList).isNotNull();
    }
}
