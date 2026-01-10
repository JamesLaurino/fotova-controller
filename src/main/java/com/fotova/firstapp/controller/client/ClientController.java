package com.fotova.firstapp.controller.client;

import com.fotova.dto.address.AddressDto;
import com.fotova.dto.client.ClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.firstapp.security.service.AuthService;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.client.ClientService;
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
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Retrieve all clients")
    @ApiResponse(responseCode = "200", description = "Clients retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ClientDto.class))
            })
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

    @Operation(summary = "Retrieve a client by its id")
    @ApiResponse(responseCode = "200", description = "Client retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ClientDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Client not found",
            content = @Content)
    @GetMapping("auth/client/{clientId}")
    public ResponseEntity<Object> getClientById(
            @Parameter(description = "Client identifier - id", required = true, example = "1")
            @PathVariable Integer clientId) {
        Response<ClientDto> response = Response.<ClientDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client retrieved successfully")
                .data(clientService.getClientById(clientId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a client's address")
    @ApiResponse(responseCode = "200", description = "Client address updated successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ClientDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Client not found",
            content = @Content)
    @PutMapping("auth/client/update")
    public ResponseEntity<Object> updateAddressClient(@RequestBody @Valid AddressDto addressDto) {
        ClientDto clientDto = authService.getPrincipal();
        Response<ClientDto> response = Response.<ClientDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client address updated successfully")
                .data(clientService.updateAddressClient(clientDto.getId(),addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add an address to a client")
    @ApiResponse(responseCode = "200", description = "Client address added successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ClientDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Client not found",
            content = @Content)
    @PostMapping("auth/client/address")
    public ResponseEntity<Object> postAddressClient(@RequestBody @Valid AddressDto addressDto) {
        ClientDto clientDto = authService.getPrincipal();
        Response<ClientDto> response = Response.<ClientDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client address added successfully")
                .data(clientService.addAddressClient(clientDto.getId(),addressDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a comment to a client")
    @ApiResponse(responseCode = "200", description = "Client comment added successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            })
    @ApiResponse(responseCode = "404", description = "Client not found",
            content = @Content)
    @PostMapping("auth/client/comment")
    public ResponseEntity<Object> postCommentClient(@RequestBody @Valid CommentDto commentDto) {
        ClientDto clientDto = authService.getPrincipal();
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client comment added successfully")
                .data(clientService.addCommentClient(clientDto.getId(),commentDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a client by its id")
    @ApiResponse(responseCode = "200", description = "Client deleted successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            })
    @ApiResponse(responseCode = "404", description = "Client not found",
            content = @Content)
    @DeleteMapping("auth/client/{clientId}/delete")
    public ResponseEntity<Object> deleteClientById(
            @Parameter(description = "Client identifier - id", required = true, example = "1")
            @PathVariable Integer clientId) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Client deleted successfully")
                .data(clientService.deleteClientById(clientId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}