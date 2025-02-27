package com.fotova.firstapp.controller.supplier;

import com.fotova.dto.supplier.SupplierDto;
import com.fotova.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("auth/suppliers")
    public ResponseEntity<Object> getAllSuppliers(){
        return ResponseEntity.ok(supplierService.findAll());
    }

    @GetMapping("auth/supplier/{supplierId}")
    public ResponseEntity<Object> getSupplierById(@PathVariable("supplierId") Integer supplierId){
        return ResponseEntity.ok(supplierService.findById(supplierId));
    }

    @PostMapping("auth/supplier/add")
    public ResponseEntity<Object> addSupplier(@RequestBody SupplierDto supplierDto){
        return ResponseEntity.ok(supplierService.save(supplierDto));
    }

    @PutMapping("auth/supplier/update")
    public ResponseEntity<Object> updateSupplier(@RequestBody SupplierDto supplierDto){
        return ResponseEntity.ok(supplierService.update(supplierDto));
    }

}
