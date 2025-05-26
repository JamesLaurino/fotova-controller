package com.fotova.firstapp.controller.supplier;

import com.fotova.dto.address.AddressDto;
import com.fotova.dto.product.ProductDtoBack;
import com.fotova.dto.supplier.SupplierDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.supplier.SupplierService;
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

    @GetMapping("auth/supplier/{supplierId}")
    public ResponseEntity<Object> getSupplierById(@PathVariable("supplierId") Integer supplierId){
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier retrieved successfully")
                .data(supplierService.findById(supplierId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/supplier/add")
    public ResponseEntity<Object> addSupplier(@RequestBody SupplierDto supplierDto){
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier added successfully")
                .data(supplierService.save(supplierDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("auth/supplier/update")
    public ResponseEntity<Object> updateSupplier(@RequestBody SupplierDto supplierDto){
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier updated successfully")
                .data(supplierService.update(supplierDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("auth/supplier/{supplierId}/delete")
    public ResponseEntity<Object> deleteSupplier(@PathVariable("supplierId") Integer supplierId){
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Supplier has been deleted successfully")
                .data(supplierService.delete(supplierId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("auth/supplier/{supplierId}/address")
    public ResponseEntity<Object> addSupplierAddress(
            @PathVariable("supplierId") Integer supplierId, @RequestBody AddressDto addressDto)
    {
        Response<SupplierDto> response = Response.<SupplierDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Address has been added  to the supplier successfully")
                .data(supplierService.addSupplierAddress(supplierId, addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("auth/supplier/{supplierId}/product")
    public ResponseEntity<Object> addSupplierProduct(
            @PathVariable("supplierId") Integer supplierId, @RequestBody ProductDtoBack productDto)
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