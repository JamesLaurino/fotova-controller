package com.fotova.firstapp.controller.supplier;

import com.fotova.dto.address.AddressDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.dto.supplier.SupplierDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.supplier.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Operation(summary = "Retrieve all the client orders")
    @ApiResponse(responseCode = "200", description = "Suppliers retrieved successfully",
    content = {
            @Content(mediaType = "application/json", schema =
            @Schema(implementation = SupplierDto.class))
    })
    @GetMapping("auth/suppliers")
    public ResponseEntity<Object> getAllSuppliers(){
        Response<List<SupplierDto>> response = Response.<List<SupplierDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Suppliers retrieved successfully")
                .data(supplierService.findAll())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve the supplier with his id",
    responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Supplier retrieved successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = SupplierDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Supplier not found for the given id",
                content = @Content()
        )
    })
    @GetMapping("auth/supplier/{supplierId}")
    public ResponseEntity<Object> getSupplierById(
            @Parameter(description = "Supplier identifier - id", required = true, example = "1")
            @PathVariable("supplierId") Integer supplierId){
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier retrieved successfully")
                .data(supplierService.findById(supplierId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Add a new supplier in the database",
    responses = {
        @ApiResponse(
                responseCode = "200",
                description = "Supplier added successfully",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = SupplierDto.class)
                )
        ),
        @ApiResponse(
                responseCode = "409",
                description = "Supplier already exist for the given id",
                content = @Content()
        )
    })
    @PostMapping("auth/supplier/add")
    public ResponseEntity<Object> addSupplier(@RequestBody @Valid SupplierDto supplierDto){
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier added successfully")
                .data(supplierService.save(supplierDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing supplier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Supplier updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SupplierDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Supplier not found for the given id",
                            content = @Content()
                    )
            })
    @PutMapping("auth/supplier/update")
    public ResponseEntity<Object> updateSupplier(@RequestBody @Valid SupplierDto supplierDto){
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier updated successfully")
                .data(supplierService.update(supplierDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a supplier by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Supplier deleted successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Supplier not found for the given id",
                            content = @Content()
                    )
            })
    @DeleteMapping("auth/supplier/{supplierId}/delete")
    public ResponseEntity<Object> deleteSupplier(
            @Parameter(description = "Supplier identifier - id", required = true, example = "1")
            @PathVariable("supplierId") Integer supplierId){
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier has been deleted successfully")
                .data(supplierService.delete(supplierId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add an address to a supplier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Address added to supplier successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SupplierDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Supplier not found for the given id",
                            content = @Content()
                    )
            })
    @PutMapping("auth/supplier/{supplierId}/address")
    public ResponseEntity<Object> addSupplierAddress(
            @Parameter(description = "Supplier identifier - id", required = true, example = "1")
            @PathVariable("supplierId") Integer supplierId, @RequestBody @Valid AddressDto addressDto)
    {
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Address has been added  to the supplier successfully")
                .data(supplierService.addSupplierAddress(supplierId, addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a product to a supplier",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Product added to supplier successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SupplierDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Supplier not found for the given id",
                            content = @Content()
                    )
            })
    @PutMapping("auth/supplier/{supplierId}/product")
    public ResponseEntity<Object> addSupplierProduct(
            @Parameter(description = "Supplier identifier - id", required = true, example = "1")
            @PathVariable("supplierId") Integer supplierId, @RequestBody @Valid ProductDtoBack productDto)
    {
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Product has been added  to the supplier successfully")
                .data(supplierService.addSupplierProduct(supplierId, productDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}