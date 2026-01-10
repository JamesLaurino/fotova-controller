package com.fotova.firstapp.service.client;

import com.fotova.dto.address.AddressDto;
import com.fotova.dto.client.ClientCommentDto;
import com.fotova.dto.client.ClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.entity.AddressEntity;
import com.fotova.entity.ClientEntity;
import com.fotova.entity.CommentEntity;
import com.fotova.repository.address.AddressRepositoryImpl;
import com.fotova.repository.client.ClientRepositoryImpl;
import com.fotova.repository.comment.CommentRepositoryImpl;
import com.fotova.service.address.AddressMapper;
import com.fotova.service.client.ClientMapper;
import com.fotova.service.client.ClientService;
import com.fotova.service.comment.CommentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class ClientServiceUnitTest {
    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepositoryImpl clientRepositoryImpl;

    @Mock
    private AddressRepositoryImpl addressRepositoryImpl;

    @Mock
    private CommentRepositoryImpl commentRepositoryImpl;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private AddressMapper addressMapper;

    @Mock
    private CommentMapper commentMapper;

    @Test
    @DisplayName("getAllClients")
    public void getAllClients() {
        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1);
        commentEntity.setBody("Body entity 1");
        commentEntity.setHeader("Header entity 1");
        commentEntity.setCreateAt(Instant.now());
        commentEntity.setUpdateAt(Instant.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL> 1");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD> 1");
        clientEntity.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntity.setCreatedAt(Timestamp.from(Instant.now()));
        clientEntity.setAddress(addressEntity);
        clientEntity.setCommentEntities(List.of(commentEntity));

        ClientEntity clientEntityTwo = new ClientEntity();
        clientEntityTwo.setId(2);
        clientEntityTwo.setEmail("<EMAIL> 2");
        clientEntityTwo.setUsername("thomas 2");
        clientEntityTwo.setPassword("<PASSWORD> 2");
        clientEntityTwo.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntityTwo.setCreatedAt(Timestamp.from(Instant.now()));

        ClientDto clientDtoOne = new ClientDto();
        clientDtoOne.setId(1);
        clientDtoOne.setEmail("<EMAIL> 1");
        clientDtoOne.setUsername("thomas");
        clientDtoOne.setIsActive(true);
        clientDtoOne.setCommentEntities(List.of((new ClientCommentDto())));

        ClientDto clientDtoTwo = new ClientDto();
        clientDtoTwo.setId(2);
        clientDtoTwo.setEmail("<EMAIL> 2");
        clientDtoTwo.setUsername("thomas 2");
        clientDtoTwo.setIsActive(true);
        clientDtoTwo.setCommentEntities(List.of((new ClientCommentDto())));

        List<ClientDto> clientDtoList = List.of(clientDtoOne, clientDtoTwo);

        // WHEN
        BDDMockito.given(clientRepositoryImpl.findAll()).willReturn(List.of(clientEntity, clientEntityTwo));
        BDDMockito.given(clientMapper.maptoClientDtoList(List.of(clientEntity, clientEntityTwo))).willReturn(clientDtoList);
        List<ClientDto> clientDtoListRes = clientService.getAllClients();

        // THEN
        verify(clientRepositoryImpl, times(1)).findAll();
        verify(clientMapper, times(1)).maptoClientDtoList(List.of(clientEntity, clientEntityTwo));

        assertThat(clientDtoListRes).isNotEmpty();
        assertThat(clientDtoListRes).isNotNull();
        assertThat(clientDtoListRes).hasSize(2);
        assertThat(clientDtoListRes.get(0).getEmail()).isEqualTo(clientDtoOne.getEmail());
        assertThat(clientDtoListRes.get(1).getEmail()).isEqualTo(clientDtoTwo.getEmail());
        assertThat(clientDtoListRes.get(0).getCommentEntities()).hasSize(1);
    }

    @Test
    @DisplayName("getClientById")
    public void getClientById() {
        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1);
        commentEntity.setBody("Body entity 1");
        commentEntity.setHeader("Header entity 1");
        commentEntity.setCreateAt(Instant.now());
        commentEntity.setUpdateAt(Instant.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL> 1");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD> 1");
        clientEntity.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntity.setCreatedAt(Timestamp.from(Instant.now()));
        clientEntity.setAddress(addressEntity);
        clientEntity.setCommentEntities(List.of(commentEntity));

        ClientDto clientDtoOne = new ClientDto();
        clientDtoOne.setId(1);
        clientDtoOne.setEmail("<EMAIL> 1");
        clientDtoOne.setUsername("thomas");
        clientDtoOne.setIsActive(true);
        clientDtoOne.setCommentEntities(List.of((new ClientCommentDto())));

        // WHEN
        BDDMockito.given(clientRepositoryImpl.findById(1)).willReturn(clientEntity);
        BDDMockito.given(clientMapper.mapClientToClientDto(clientEntity)).willReturn(clientDtoOne);
        ClientDto clientDtoRes = clientService.getClientById(1);

        // THEN
        verify(clientRepositoryImpl, times(1)).findById(1);
        verify(clientMapper, times(1)).mapClientToClientDto(clientEntity);

        assertThat(clientDtoRes).isNotNull();
        assertThat(clientDtoRes.getId()).isEqualTo(clientDtoOne.getId());
        assertThat(clientDtoRes.getEmail()).isEqualTo(clientDtoOne.getEmail());
        assertThat(clientDtoRes.getUsername()).isEqualTo(clientDtoOne.getUsername());
        assertThat(clientDtoRes.getCommentEntities()).hasSize(1);
    }

    @Test
    @DisplayName("updateAddressClient")
    public void updateAddressClient() {
        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1);
        commentEntity.setBody("Body entity 1");
        commentEntity.setHeader("Header entity 1");
        commentEntity.setCreateAt(Instant.now());
        commentEntity.setUpdateAt(Instant.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL> 1");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD> 1");
        clientEntity.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntity.setCreatedAt(Timestamp.from(Instant.now()));
        clientEntity.setAddress(addressEntity);
        clientEntity.setCommentEntities(List.of(commentEntity));

        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("Street dto 1");
        addressDto.setCity("City dto 1");
        addressDto.setCountry("Country dto 1");
        addressDto.setNumber("Number dto 1");


        ClientDto clientDtoOne = new ClientDto();
        clientDtoOne.setId(1);
        clientDtoOne.setEmail("<EMAIL> 1");
        clientDtoOne.setUsername("thomas");
        clientDtoOne.setIsActive(true);
        clientDtoOne.setCommentEntities(List.of((new ClientCommentDto())));

        // WHEN
        BDDMockito.given(clientRepositoryImpl.findById(clientEntity.getId())).willReturn(clientEntity);
        BDDMockito.given(clientMapper.mapAddressToAddressEntity(addressDto))
                .willReturn(addressEntity);
        BDDMockito.given(addressRepositoryImpl.save(addressEntity)).willReturn(addressEntity);
        BDDMockito.given(clientRepositoryImpl.findById(clientEntity.getId())).willReturn(clientEntity);
        BDDMockito.given(clientMapper.mapClientToClientDto(clientEntity)).willReturn(clientDtoOne);
        ClientDto clientDtoRes = clientService.updateAddressClient(clientEntity.getId(), addressDto);

        // THEN
        verify(clientRepositoryImpl, times(2)).findById(clientEntity.getId());
        verify(clientMapper, times(1)).mapAddressToAddressEntity(addressDto);
        verify(addressRepositoryImpl, times(1)).save(addressEntity);
        verify(clientMapper, times(1)).mapClientToClientDto(clientEntity);

        assertThat(clientDtoRes).isNotNull();
        assertThat(clientDtoRes.getId()).isEqualTo(clientDtoOne.getId());
        assertThat(clientDtoRes.getEmail()).isEqualTo(clientDtoOne.getEmail());
        assertThat(clientDtoRes.getUsername()).isEqualTo(clientDtoOne.getUsername());
    }

    @Test
    @DisplayName("addAddressClient")
    public void addAddressClient() {
        // GIVEN
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1);
        addressEntity.setStreet("Street 1");
        addressEntity.setCity("City 1");
        addressEntity.setCountry("Country 1");
        addressEntity.setNumber("Number 1");

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1);
        commentEntity.setBody("Body entity 1");
        commentEntity.setHeader("Header entity 1");
        commentEntity.setCreateAt(Instant.now());
        commentEntity.setUpdateAt(Instant.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL> 1");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD> 1");
        clientEntity.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntity.setCreatedAt(Timestamp.from(Instant.now()));
        clientEntity.setAddress(addressEntity);
        clientEntity.setCommentEntities(List.of(commentEntity));

        AddressDto addressDto = new AddressDto();
        addressDto.setId(1);
        addressDto.setStreet("Street dto 1");
        addressDto.setCity("City dto 1");
        addressDto.setCountry("Country dto 1");
        addressDto.setNumber("Number dto 1");

        ClientDto clientDtoOne = new ClientDto();
        clientDtoOne.setId(1);
        clientDtoOne.setEmail("<EMAIL> 1");
        clientDtoOne.setUsername("thomas");
        clientDtoOne.setIsActive(true);
        clientDtoOne.setCommentEntities(List.of((new ClientCommentDto())));

        // WHEN
        BDDMockito.given(addressMapper.mapToAddressEntity(addressDto)).willReturn(addressEntity);
        BDDMockito.given(addressRepositoryImpl.save(addressEntity)).willReturn(addressEntity);
        BDDMockito.given(clientRepositoryImpl.findById(clientEntity.getId())).willReturn(clientEntity);
        BDDMockito.given(clientRepositoryImpl.save(clientEntity)).willReturn(clientEntity);
        BDDMockito.given(clientMapper.mapClientToClientDto(clientEntity)).willReturn(clientDtoOne);
        ClientDto clientDtoRes = clientService.addAddressClient(clientEntity.getId(), addressDto);

        // THEN
        verify(addressMapper, times(1)).mapToAddressEntity(addressDto);
        verify(addressRepositoryImpl, times(1)).save(addressEntity);
        verify(clientRepositoryImpl, times(1)).save(clientEntity);
        verify(clientRepositoryImpl, times(2)).findById(clientEntity.getId());
        verify(clientMapper, times(1)).mapClientToClientDto(clientEntity);

        assertThat(clientDtoRes).isNotNull();
        assertThat(clientDtoRes.getId()).isEqualTo(clientDtoOne.getId());
        assertThat(clientDtoRes.getEmail()).isEqualTo(clientDtoOne.getEmail());
        assertThat(clientDtoRes.getUsername()).isEqualTo(clientDtoOne.getUsername());
        assertThat(clientDtoRes.getCommentEntities()).hasSize(1);
        assertThat(clientDtoRes.getCommentEntities().get(0).getHeader())
                .isEqualTo(clientDtoOne.getCommentEntities().get(0).getHeader());
    }

    @Test
    @DisplayName("addCommentClient")
    public void addCommentClient() {
        // GIVEN
        CommentDto commentDto = new CommentDto();
        commentDto.setHeader("Header dto 1");
        commentDto.setBody("Body dto 1");
        commentDto.setId(1);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(1);
        commentEntity.setBody("Body entity 1");
        commentEntity.setHeader("Header entity 1");
        commentEntity.setCreateAt(Instant.now());
        commentEntity.setUpdateAt(Instant.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL> 1");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD> 1");
        clientEntity.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntity.setCreatedAt(Timestamp.from(Instant.now()));

        // WHEN
        BDDMockito.given(commentMapper.mapToCommentEntity(commentDto)).willReturn(commentEntity);
        BDDMockito.given(commentRepositoryImpl.save(commentEntity)).willReturn(commentEntity);
        BDDMockito.willDoNothing().given(commentRepositoryImpl).setCommentClientId(clientEntity.getId(), commentEntity.getId());
        String res = clientService.addCommentClient(clientEntity.getId(), commentDto);

        // THEN
        verify(commentMapper, times(1)).mapToCommentEntity(commentDto);
        verify(commentRepositoryImpl, times(1)).save(commentEntity);
        verify(commentRepositoryImpl, times(1)).setCommentClientId(clientEntity.getId(), commentEntity.getId());

        assertThat(res).isEqualTo("comment added successfully to client : " +  clientEntity.getId());
    }

    @Test
    @DisplayName("deleteClientById")
    public void deleteClientById() {
        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL> 1");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD> 1");
        clientEntity.setUpdatedAt(Timestamp.from(Instant.now()));
        clientEntity.setCreatedAt(Timestamp.from(Instant.now()));

        // GIVEN
        BDDMockito.given(clientRepositoryImpl.clientAnonymization(clientEntity.getId())).willReturn(clientEntity);
        BDDMockito.given(clientMapper.anonymization(clientEntity)).willReturn(clientEntity);
        BDDMockito.given(clientRepositoryImpl.save(clientEntity)).willReturn(clientEntity);
        BDDMockito.willDoNothing().given(clientRepositoryImpl).updateClientAddress(clientEntity.getId());
        String res = clientService.deleteClientById(clientEntity.getId());

        // THEN
        verify(clientRepositoryImpl, times(1)).clientAnonymization(clientEntity.getId());
        verify(clientMapper, times(1)).anonymization(clientEntity);
        verify(clientRepositoryImpl, times(1)).save(clientEntity);
        verify(clientRepositoryImpl, times(1)).updateClientAddress(clientEntity.getId());
        assertThat(res).isEqualTo("client deleted successfully : " +  clientEntity.getId());
    }
}