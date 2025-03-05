package com.fotova.firstapp.controller.client;

import com.fotova.dto.comment.CommentDto;
import com.fotova.dto.address.AddressDto;
import com.fotova.dto.client.ClientDto;
import com.fotova.firstapp.security.service.AuthService;
import com.fotova.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthService authService;

    @GetMapping("auth/clients")
    public ResponseEntity<Object> getAllClient() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("auth/client/{clientId}")
    public ResponseEntity<Object> getClientById(@PathVariable Integer clientId) {
        return ResponseEntity.ok(clientService.getClientById(clientId));
    }

    @PutMapping("client/update")
    public ResponseEntity<Object> updateAddressClient(@RequestBody AddressDto addressDto) {
        ClientDto clientDto = authService.getPrincipal();
        return ResponseEntity.ok(clientService.updateAddressClient(clientDto.getId(),addressDto));
    }

    @PostMapping("/client/address")
    public ResponseEntity<Object> postAddressClient(@RequestBody AddressDto addressDto) {
        ClientDto clientDto = authService.getPrincipal();
        return ResponseEntity.ok(clientService.addAddressClient(clientDto.getId(),addressDto));
    }

    @PostMapping("client/comment")
    public ResponseEntity<String> postCommentClient(@RequestBody CommentDto commentDto) {
        ClientDto clientDto = authService.getPrincipal();
        return ResponseEntity.ok(clientService.addCommentClient(clientDto.getId(),commentDto));
    }

    @DeleteMapping("auth/client/{clientId}/delete")
    public ResponseEntity<String> deleteClientById(@PathVariable Integer clientId) {
        return ResponseEntity.ok(clientService.deleteClientById(clientId));
    }
}