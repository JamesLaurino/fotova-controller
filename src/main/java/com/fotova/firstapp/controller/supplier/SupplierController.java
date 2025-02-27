package com.fotova.firstapp.controller.supplier;

import com.fotova.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("auth/suppliers")
    public ResponseEntity<Object> getAllSuppliers(){
        return ResponseEntity.ok(supplierService.findAll());
    }
}
