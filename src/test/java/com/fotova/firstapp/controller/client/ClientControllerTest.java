package com.fotova.firstapp.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.address.AddressDto;
import com.fotova.dto.client.ClientCommentDto;
import com.fotova.dto.client.ClientDto;
import com.fotova.dto.comment.CommentClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.firstapp.security.service.AuthService;
import com.fotova.service.client.ClientService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientService clientService;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all clients")
    public void getAllClient() throws Exception {
        // GIVEN
        List<ClientCommentDto> clientCommentDtoList =
                List.of(new ClientCommentDto(), new ClientCommentDto(), new ClientCommentDto());

        ClientDto clientDtoOne = new ClientDto();
        clientDtoOne.setId(1);
        clientDtoOne.setEmail("email 1");
        clientDtoOne.setUsername("username 1");
        clientDtoOne.setIsActive(true);
        clientDtoOne.setCommentEntities(clientCommentDtoList);

        ClientDto clientDtoTwo = new ClientDto();
        clientDtoTwo.setId(2);
        clientDtoTwo.setEmail("email 2");
        clientDtoTwo.setUsername("username 2");
        clientDtoTwo.setIsActive(true);
        clientDtoTwo.setCommentEntities(clientCommentDtoList);

        ClientDto clientDtoThree = new ClientDto();
        clientDtoThree.setId(3);
        clientDtoThree.setEmail("email 3");
        clientDtoThree.setUsername("username 2");
        clientDtoThree.setIsActive(true);
        clientDtoThree.setCommentEntities(clientCommentDtoList);

        List<ClientDto> clientDtoList = List.of(clientDtoOne, clientDtoTwo, clientDtoThree);

        // WHEN
        BDDMockito.given(clientService.getAllClients()).willReturn(clientDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/clients"));

        // THEN
        verify(clientService, times(1)).getAllClients();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()",
                        CoreMatchers.is(clientDtoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Clients retrieved successfully")));
    }


    @Test
    @DisplayName("Get client by id")
    public void getClientById() throws Exception {
        // GIVEN
        List<ClientCommentDto> clientCommentDtoList =
                List.of(new ClientCommentDto(), new ClientCommentDto(), new ClientCommentDto());

        ClientDto clientDtoOne = new ClientDto();
        clientDtoOne.setId(1);
        clientDtoOne.setEmail("email 1");
        clientDtoOne.setUsername("username 1");
        clientDtoOne.setIsActive(true);
        clientDtoOne.setCommentEntities(clientCommentDtoList);

        // WHEN
        BDDMockito.given(clientService.getClientById(clientDtoOne.getId())).willReturn(clientDtoOne);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/client/{clientId}",
                clientDtoOne.getId()));

        // THEN
        verify(clientService, times(1)).getClientById(clientDtoOne.getId());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(clientDtoOne.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.email",
                        CoreMatchers.is(clientDtoOne.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username",
                        CoreMatchers.is(clientDtoOne.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Client retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.commentEntities", Matchers.hasSize(3)));
    }

    @Test
    @DisplayName("Delete client by id")
    public void deleteById() throws Exception {
        // GIVEN
        Integer clientId = 1;

        // WHEN
        BDDMockito.given(clientService.deleteClientById(clientId)).willReturn(null);
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/auth/client/{clientId}/delete",
                clientId));

        // THEN
        verify(clientService, times(1)).deleteClientById(clientId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Client deleted successfully")));
    }

    @Test
    @DisplayName("Post a comment to a client")
    public void postCommentClient() throws Exception {
        // GIVEN
        List<ClientCommentDto> clientCommentDtoList =
                List.of(new ClientCommentDto(), new ClientCommentDto(), new ClientCommentDto());

        ClientDto clientDtoOne = new ClientDto();
        clientDtoOne.setId(1);
        clientDtoOne.setEmail("email 1");
        clientDtoOne.setUsername("username 1");
        clientDtoOne.setIsActive(true);
        clientDtoOne.setCommentEntities(clientCommentDtoList);

        CommentClientDto clientCommentDto = new CommentClientDto();
        clientCommentDto.setClientId(1);
        clientCommentDto.setUsername("username 1");
        clientCommentDto.setEmail("email 1");

        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setBody("body 1");
        commentDto.setUpdateAt(Instant.now());
        commentDto.setCreateAt(Instant.now());
        commentDto.setHeader("header 1");
        commentDto.setClientCommentDto(clientCommentDto);


        // WHEN
        BDDMockito.given(authService.getPrincipal()).willReturn(clientDtoOne);
        BDDMockito.given(clientService.addCommentClient(eq(1), any(CommentDto.class)))
                .willReturn("comment added successfully to client : " + clientDtoOne.getId());


        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/client/comment")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDto)));

        // THEN
        verify(clientService, times(1)).addCommentClient(eq(1), any(CommentDto.class));
        verify(authService, times(1)).getPrincipal();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Client comment added successfully")));
    }

    @Test
    @DisplayName("Post an address to a client")
    public void postAddressClient() throws Exception {
        // GIVEN
        ClientDto clientDto = new ClientDto();
        clientDto.setId(1);
        clientDto.setEmail("email 1");
        clientDto.setUsername("username 1");
        clientDto.setIsActive(true);
        clientDto.setCommentEntities(List.of(new ClientCommentDto()));

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("street 1");
        addressDto.setNumber("number 1");
        addressDto.setCountry("country 1");
        addressDto.setCity("city 1");


        // WHEN
        BDDMockito.given(authService.getPrincipal()).willReturn(clientDto);
        BDDMockito.given(clientService.addAddressClient(eq(1), any(AddressDto.class)))
                .willReturn(clientDto);


        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/client/address")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addressDto)));

        verify(clientService, times(1)).addAddressClient(eq(1), any(AddressDto.class));
        verify(authService, times(1)).getPrincipal();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Client address added successfully")));

    }
}
