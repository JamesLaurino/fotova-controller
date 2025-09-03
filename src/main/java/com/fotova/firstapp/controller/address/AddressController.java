package com.fotova.firstapp.controller.address;

import com.fotova.dto.address.AddressDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.address.AddressService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@OpenAPIDefinition(info = @Info
        (title = "Fotova creation - Definition", version = "1.0",
                description = "Operations that help manage fotova-creation" ))
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Operation(summary = "Retrieve all the client address")
    @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully",
    content = {
        @Content(mediaType = "application/json",
        schema = @Schema(implementation = AddressDto.class))
    })
    @GetMapping("auth/address")
    public ResponseEntity<Object> getAllAddresses() {
        Response<List<AddressDto>> response = Response.<List<AddressDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Addresses retrieved successfully")
                .data(addressService.getAllAddresses())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve the client address with the id of the address",
    responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Address retrieved successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = AddressDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Address not found for the given id",
                content = @Content()
        )
    })
    @GetMapping("auth/address/{addressId}")
    public ResponseEntity<Object> getAddressById(
            @Parameter(description = "Address identifier", required = true, example = "1")
            @PathVariable Integer addressId) {
        Response<AddressDto> response = Response.<AddressDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Address retrieved successfully")
                .data(addressService.getAddressById(addressId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve the client address with the id of the address",
    responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Address deleted successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = String.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Address not found for the given id deletion impossible",
                content = @Content()
        )
    })
    @DeleteMapping("auth/address/{addressId}/delete")
    public ResponseEntity<Object> deleteAddress(
            @Parameter(description = "Address identifier", required = true, example = "1")
            @PathVariable Integer addressId) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Address deleted successfully")
                .data(addressService.deleteAddressById(addressId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new address")
    @ApiResponse(responseCode = "200", description = "Address added successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = AddressDto.class))
            })
    @ApiResponse(responseCode = "409", description = "Address already exists",
            content = @Content)
    @PostMapping("auth/address/add")
    public ResponseEntity<Object> addAddress(@RequestBody AddressDto addressDto) {
        Response<AddressDto> response = Response.<AddressDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Address added successfully")
                .data(addressService.addAddress(addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an address")
    @ApiResponse(responseCode = "200", description = "Address updated successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = AddressDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Address not found",
            content = @Content)
    @PutMapping("auth/address/update")
    public ResponseEntity<Object> updateAddress(@RequestBody AddressDto addressDto) {
        Response<AddressDto> response = Response.<AddressDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Address updated successfully")
                .data(addressService.updateAddress(addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
