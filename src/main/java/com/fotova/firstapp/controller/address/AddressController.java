package com.fotova.firstapp.controller.address;

import com.fotova.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("auth/address")
    public ResponseEntity<Object> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("auth/address/{addressId}")
    public ResponseEntity<Object> getAddressById(@PathVariable Integer addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }
}
