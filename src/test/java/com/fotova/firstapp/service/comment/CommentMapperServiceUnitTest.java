package com.fotova.firstapp.service.comment;

import com.fotova.dto.comment.CommentClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.entity.ClientEntity;
import com.fotova.entity.CommentEntity;
import com.fotova.service.comment.CommentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentMapperServiceUnitTest {

    private CommentMapper commentMapper = new CommentMapper();

    @Test
    @DisplayName("Map comment dto to comment entity")
    void mapToCommentEntity() {
        // GIVEN
        CommentDto commentDtoOne = new CommentDto();
        commentDtoOne.setId(1);
        commentDtoOne.setBody("Body dto 1");
        commentDtoOne.setHeader("Header dto 1");
        commentDtoOne.setCreateAt(Instant.now());
        commentDtoOne.setUpdateAt(Instant.now());
        commentDtoOne.setClientCommentDto(new CommentClientDto());

        // WHEN
        CommentEntity commentEntity = commentMapper.mapToCommentEntity(commentDtoOne);

        // THEN
        assertThat(commentEntity).isNotNull();
        assertThat(commentEntity.getId()).isEqualTo(commentDtoOne.getId());
        assertThat(commentEntity.getBody()).isEqualTo(commentDtoOne.getBody());
        assertThat(commentEntity.getHeader()).isEqualTo(commentDtoOne.getHeader());
        assertThat(commentEntity.getUpdateAt()).isEqualTo(commentDtoOne.getUpdateAt());
        assertThat(commentEntity.getCreateAt()).isEqualTo(commentDtoOne.getCreateAt());
    }


    @Test
    @DisplayName("Map comment entity to comment dto")
    public void mapToCommentDto() {
        //GIVEN
        CommentEntity commentEntityOne = new CommentEntity();
        commentEntityOne.setId(1);
        commentEntityOne.setBody("Body entity 1");
        commentEntityOne.setHeader("Header entity 1");
        commentEntityOne.setCreateAt(Instant.now());
        commentEntityOne.setUpdateAt(Instant.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setEmail("<EMAIL>");
        clientEntity.setUsername("thomas");
        clientEntity.setPassword("<PASSWORD>");

        CommentEntity commentEntityTwo = new CommentEntity();
        commentEntityTwo.setId(1);
        commentEntityTwo.setBody("Body entity 2");
        commentEntityTwo.setHeader("Header entity 2");
        commentEntityTwo.setCreateAt(Instant.now());
        commentEntityTwo.setUpdateAt(Instant.now());
        commentEntityTwo.setClientEntity(clientEntity);

        // WHEN
        CommentDto commentDto = commentMapper.mapToCommentDto(commentEntityOne);
        CommentDto commentDtoTwo = commentMapper.mapToCommentDto(commentEntityTwo);

        // THEM
        assertThat(commentDto).isNotNull();
        assertThat(commentDto.getId()).isEqualTo(commentEntityOne.getId());
        assertThat(commentDto.getBody()).isEqualTo(commentEntityOne.getBody());
        assertThat(commentDto.getHeader()).isEqualTo(commentEntityOne.getHeader());
        assertThat(commentDto.getUpdateAt()).isEqualTo(commentEntityOne.getUpdateAt());
        assertThat(commentDto.getCreateAt()).isEqualTo(commentEntityOne.getCreateAt());
        assertThat(commentDto.getClientCommentDto()).isNull();
        assertThat(commentDtoTwo.getClientCommentDto()).isNotNull();
        assertThat(commentDtoTwo.getClientCommentDto().getUsername()).isEqualTo(clientEntity.getUsername());
        assertThat(commentDtoTwo.getClientCommentDto().getEmail()).isEqualTo(clientEntity.getEmail());
    }


    @Test
    @DisplayName("Map comment entity list to comment dto list")
    public void mapToCommentDtoList() {
        // GIVEN
        CommentEntity commentEntityOne = new CommentEntity();
        commentEntityOne.setId(1);
        commentEntityOne.setBody("Body entity 1");
        commentEntityOne.setHeader("Header entity 1");
        commentEntityOne.setCreateAt(Instant.now());
        commentEntityOne.setUpdateAt(Instant.now());
        commentEntityOne.setClientEntity(new ClientEntity());

        CommentEntity commentEntityTwo = new CommentEntity();
        commentEntityTwo.setId(2);
        commentEntityTwo.setBody("Body entity 2");
        commentEntityTwo.setHeader("Header entity 2");
        commentEntityTwo.setCreateAt(Instant.now());
        commentEntityTwo.setUpdateAt(Instant.now());
        commentEntityTwo.setClientEntity(new ClientEntity());

        List<CommentEntity> commentEntityList = List.of(commentEntityOne, commentEntityTwo);

        // WHEN
        List<CommentDto> commentDtoListRes = commentMapper.mapToCommentDtoList(commentEntityList);

        // THEN
        assertThat(commentDtoListRes).isNotEmpty();
        assertThat(commentDtoListRes).hasSize(2);
        assertThat(commentDtoListRes).isNotNull();
        assertThat(commentDtoListRes.get(0).getBody()).isEqualTo(commentEntityOne.getBody());
        assertThat(commentDtoListRes.get(1).getHeader()).isEqualTo(commentEntityTwo.getHeader());
    }

}
