package com.fotova.firstapp.controller.client;

import com.fotova.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("auth/clients")
    public ResponseEntity<Object> getAllClient() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("auth/client/{clientId}")
    public ResponseEntity<Object> getClientById(@PathVariable Integer clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }
}
