package com.fotova.firstapp.controller.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.address.AddressDto;
import com.fotova.service.address.AddressService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all addresses")
    public void getAllAddresses() throws Exception {
        // GIVEN
        AddressDto addressDtoOne = new AddressDto();
        addressDtoOne.setId(1);
        addressDtoOne.setCity("city 1");
        addressDtoOne.setCountry("country 1");
        addressDtoOne.setNumber("number 1");
        addressDtoOne.setStreet("street 1");

        AddressDto addressDtoTwo = new AddressDto();
        addressDtoTwo.setId(2);
        addressDtoTwo.setCity("city 2");
        addressDtoTwo.setCountry("country 2");
        addressDtoTwo.setNumber("number 2");
        addressDtoTwo.setStreet("street 2");

        AddressDto addressDtoThree = new AddressDto();
        addressDtoThree.setId(3);
        addressDtoThree.setCity("city 3");
        addressDtoThree.setCountry("country 3");
        addressDtoThree.setNumber("number 3");
        addressDtoThree.setStreet("street 3");

        List<AddressDto> addressDtoList = List.of(addressDtoOne, addressDtoTwo, addressDtoThree);

        // WHEN
        BDDMockito.given(addressService.getAllAddresses()).willReturn(addressDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/address"));

        // THEN
        verify(addressService, times(1)).getAllAddresses();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()",
                        CoreMatchers.is(addressDtoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Addresses retrieved successfully")));

    }

    @Test
    @DisplayName("Get address by id")
    public void getAddressById() throws Exception {
        // GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setCity("city 1");
        addressDto.setCountry("country 1");
        addressDto.setNumber("number 1");
        addressDto.setStreet("street 1");

        // WHEN
        BDDMockito.given(addressService.getAddressById(addressDto.getId())).willReturn(addressDto);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/address/{addressId}",
                addressDto.getId()));

        verify(addressService, times(1)).getAddressById(addressDto.getId());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(addressDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.city",
                    CoreMatchers.is(addressDto.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.country",
                    CoreMatchers.is(addressDto.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.number",
                        CoreMatchers.is(addressDto.getNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.street",
                        CoreMatchers.is(addressDto.getStreet())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Address retrieved successfully")));

    }

    @Test
    @DisplayName("Delete address by id")
    public void deleteAddressById() throws Exception {
        // GIVEN
        Integer addressId = 1;

        // WHEN
        BDDMockito.given(addressService.deleteAddressById(addressId))
                .willReturn("Address has been deleted successfully for id : " + addressId);
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/auth/address/{addressId}/delete",
                addressId));

        // THEN
        verify(addressService, times(1)).deleteAddressById(addressId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Address deleted successfully")));
    }

    @Test
    @DisplayName("Add address")
    public void addAddress() throws Exception {
        // GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setCity("city 1");
        addressDto.setCountry("country 1");
        addressDto.setNumber("number 1");
        addressDto.setStreet("street 1");

        // WHEN
        BDDMockito.given(addressService.addAddress(any(AddressDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/address/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressDto)));

        // THEN
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.city",
                        CoreMatchers.is(addressDto.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.country",
                        CoreMatchers.is(addressDto.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.number",
                        CoreMatchers.is(addressDto.getNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.street",
                        CoreMatchers.is(addressDto.getStreet())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Address added successfully")));
    }

    @Test
    @DisplayName("Update Address")
    public void updateAddress() throws Exception {
        // GIVEN
        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setCity("city 1");
        addressDto.setCountry("country 1");
        addressDto.setNumber("number 1");
        addressDto.setStreet("street 1");

        // GIVEN
        BDDMockito.given(addressService.updateAddress(any(AddressDto.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions resultActions = mockMvc.perform(put("/api/v1/auth/address/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressDto)));

        // THEN
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.city",
                        CoreMatchers.is(addressDto.getCity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.country",
                        CoreMatchers.is(addressDto.getCountry())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.number",
                        CoreMatchers.is(addressDto.getNumber())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.street",
                        CoreMatchers.is(addressDto.getStreet())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Address updated successfully")));
    }
}
