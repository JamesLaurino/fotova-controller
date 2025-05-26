package com.fotova.firstapp.controller.supplier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.address.AddressDto;
import com.fotova.dto.product.CategoryInnerProductDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.dto.supplier.SupplierAddressDto;
import com.fotova.dto.supplier.SupplierDto;
import com.fotova.dto.supplier.SupplierProductDto;
import com.fotova.service.supplier.SupplierService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class SupplierControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SupplierService supplierService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all suppliers")
    public void getAllSuppliersTest() throws Exception {
        // GIVEN
        List<SupplierDto> supplierDtoList = List.of(
                new SupplierDto(),
                new SupplierDto(),
                new SupplierDto()
        );

        // WHEN
        BDDMockito.given(supplierService.findAll()).willReturn(supplierDtoList);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/suppliers")
                .contentType("application/json"));

        // THEN
        verify(supplierService, times(1)).findAll();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", CoreMatchers.is("Suppliers retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()", CoreMatchers.is(supplierDtoList.size())));
    }

    @Test
    @DisplayName("Get supplier by id")
    public void getSupplierByIdTest() throws Exception {
        // GIVEN
        Integer supplierId = 1;
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(supplierId);
        supplierDto.setRegistrationNumber("1234");

        // WHEN
        BDDMockito.given(supplierService.findById(supplierId)).willReturn(supplierDto);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/supplier/{supplierId}", supplierId)
                .contentType("application/json"));

        // THEN
        verify(supplierService, times(1)).findById(supplierId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", CoreMatchers.is("Supplier retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(supplierDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.registrationNumber",
                        CoreMatchers.is(supplierDto.getRegistrationNumber())));
    }

    @Test
    @DisplayName("Add a supplier")
    public void addSupplierTest() throws Exception {
        // GIVEN
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("1234");
        supplierDto.setSupplierAddressDto(new SupplierAddressDto()); // vide comme convenu
        supplierDto.setSupplierProductDto(new SupplierProductDto()); // vide comme convenu

        // WHEN
        BDDMockito.given(supplierService.save(any(SupplierDto.class))).willReturn(supplierDto);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/supplier/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(supplierDto)));

        // THEN
        verify(supplierService, times(1)).save(any(SupplierDto.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", CoreMatchers.is("Supplier added successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(supplierDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.registrationNumber", CoreMatchers.is(supplierDto.getRegistrationNumber())));
    }

    @Test
    @DisplayName("Update a supplier")
    public void updateSupplierTest() throws Exception {
        // GIVEN
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(1);
        supplierDto.setRegistrationNumber("5678");
        supplierDto.setSupplierAddressDto(new SupplierAddressDto());
        supplierDto.setSupplierProductDto(new SupplierProductDto());

        // WHEN
        BDDMockito.given(supplierService.update(any(SupplierDto.class))).willReturn(supplierDto);

        ResultActions resultActions = mockMvc.perform(put("/api/v1/auth/supplier/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(supplierDto)));

        // THEN
        verify(supplierService, times(1)).update(any(SupplierDto.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage", CoreMatchers.is("Supplier updated successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", CoreMatchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", CoreMatchers.is(supplierDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.registrationNumber", CoreMatchers.is(supplierDto.getRegistrationNumber())));
    }

    @Test
    @DisplayName("Delete a supplier")
    public void deleteSupplierTest() throws Exception {
        // GIVEN
        Integer supplierId = 1;
        String deleteMessage = "Supplier has been deleted successfully";

        // WHEN
        BDDMockito.given(supplierService.delete(supplierId)).willReturn(deleteMessage);

        ResultActions resultActions = mockMvc.perform(delete("/api/v1/auth/supplier/{supplierId}/delete", supplierId)
                .contentType("application/json"));

        // THEN
        verify(supplierService, times(1)).delete(supplierId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Supplier has been deleted successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        CoreMatchers.is(deleteMessage)));
    }

    @Test
    @DisplayName("Add supplier address")
    public void addSupplierAddressTest() throws Exception {
        // GIVEN
        Integer supplierId = 1;
        AddressDto addressDto = new AddressDto();
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(supplierId);
        supplierDto.setRegistrationNumber("12345");

        // WHEN
        BDDMockito.given(supplierService.addSupplierAddress(eq(supplierId), any(AddressDto.class))).willReturn(supplierDto);

        ResultActions resultActions = mockMvc.perform(put("/api/v1/auth/supplier/{supplierId}/address", supplierId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressDto)));

        // THEN
        verify(supplierService, times(1)).addSupplierAddress(eq(supplierId), any(AddressDto.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Address has been added  to the supplier successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(supplierDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.registrationNumber",
                        CoreMatchers.is(supplierDto.getRegistrationNumber())));
    }

    @Test
    @DisplayName("Add supplier product")
    public void addSupplierProductTest() throws Exception {
        // GIVEN
        Integer supplierId = 1;
        ProductDtoBack productDto = new ProductDtoBack();
        productDto.setId(10);
        productDto.setName("Product Test");
        productDto.setQuantity(5);
        productDto.setPrice(99.99);
        productDto.setCategoryInnerProductDto(new CategoryInnerProductDto());
        productDto.setUrl("http://example.com/product.jpg");

        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setId(supplierId);
        supplierDto.setRegistrationNumber("REG12345");

        // WHEN
        BDDMockito.given(supplierService.addSupplierProduct(eq(supplierId), any(ProductDtoBack.class))).willReturn(supplierDto);

        ResultActions resultActions = mockMvc.perform(put("/api/v1/auth/supplier/{supplierId}/product", supplierId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(productDto)));

        // THEN
        verify(supplierService, times(1)).addSupplierProduct(eq(supplierId), any(ProductDtoBack.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode", CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Product has been added  to the supplier successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(supplierDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.registrationNumber",
                        CoreMatchers.is(supplierDto.getRegistrationNumber())));
    }



}
