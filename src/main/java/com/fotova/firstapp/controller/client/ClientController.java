package com.fotova.firstapp.controller.client;

import com.fotova.dto.category.CategoryDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.dto.address.AddressDto;
import com.fotova.dto.client.ClientDto;
import com.fotova.firstapp.security.service.AuthService;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthService authService;

    @GetMapping("auth/clients")
    public ResponseEntity<Object> getAllClient() {
        Response<List<ClientDto>> response = Response.<List<ClientDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Clients retrieved successfully")
                .data(clientService.getAllClients())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/client/{clientId}")
    public ResponseEntity<Object> getClientById(@PathVariable Integer clientId) {
        Response<ClientDto> response = Response.<ClientDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client retrieved successfully")
                .data(clientService.getClientById(clientId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("client/update")
    public ResponseEntity<Object> updateAddressClient(@RequestBody AddressDto addressDto) {
        ClientDto clientDto = authService.getPrincipal();
        Response<ClientDto> response = Response.<ClientDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client updated successfully")
                .data(clientService.updateAddressClient(clientDto.getId(),addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/client/address")
    public ResponseEntity<Object> postAddressClient(@RequestBody AddressDto addressDto) {
        ClientDto clientDto = authService.getPrincipal();
        Response<ClientDto> response = Response.<ClientDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Clients address added successfully")
                .data(clientService.addAddressClient(clientDto.getId(),addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("client/comment")
    public ResponseEntity<Object> postCommentClient(@RequestBody CommentDto commentDto) {
        ClientDto clientDto = authService.getPrincipal();
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client comment retrieved successfully")
                .data(clientService.addCommentClient(clientDto.getId(),commentDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("auth/client/{clientId}/delete")
    public ResponseEntity<Object> deleteClientById(@PathVariable Integer clientId) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client deleted successfully")
                .data(clientService.deleteClientById(clientId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}