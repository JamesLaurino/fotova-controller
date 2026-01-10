package com.fotova.firstapp.service.comment;

import com.fotova.dto.comment.CommentClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.entity.ClientEntity;
import com.fotova.entity.CommentEntity;
import com.fotova.repository.comment.CommentRepositoryImpl;
import com.fotova.service.comment.CommentMapper;
import com.fotova.service.comment.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.time.Instant;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class CommentServiceUnitTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepositoryImpl commentRepositoryImpl;

    @Mock
    private CommentMapper commentMapper;

    @Test
    @DisplayName("Get all comment")
    public void getAllComments() {
        // GIVEN
        CommentDto commentDtoOne = new CommentDto();
        commentDtoOne.setId(1);
        commentDtoOne.setBody("Body dto 1");
        commentDtoOne.setHeader("Header dto 1");
        commentDtoOne.setCreateAt(Instant.now());
        commentDtoOne.setUpdateAt(Instant.now());
        commentDtoOne.setClientCommentDto(new CommentClientDto());

        CommentDto commentDtoTwo = new CommentDto();
        commentDtoTwo.setId(2);
        commentDtoTwo.setBody("Body dto 2");
        commentDtoTwo.setHeader("Header dto 2");
        commentDtoTwo.setCreateAt(Instant.now());
        commentDtoTwo.setUpdateAt(Instant.now());
        commentDtoTwo.setClientCommentDto(new CommentClientDto());

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

        List<CommentDto> commentDtoList = List.of(commentDtoOne, commentDtoTwo);
        List<CommentEntity> commentEntityList = List.of(commentEntityOne, commentEntityTwo);

        // WHEN
        BDDMockito.given(commentRepositoryImpl.findAll()).willReturn(commentEntityList);
        BDDMockito.given(commentMapper.mapToCommentDtoList(commentEntityList)).willReturn(commentDtoList);
        List<CommentDto> commentDtoListRes = commentService.getAllComments();

        // THEN
        verify(commentRepositoryImpl, times(1)).findAll();
        verify(commentMapper, times(1)).mapToCommentDtoList(commentEntityList);
        assertThat(commentDtoListRes).isNotEmpty();
        assertThat(commentDtoListRes).hasSize(2);
        assertThat(commentDtoListRes).isNotNull();
        assertThat(commentDtoListRes.get(0).getBody()).isEqualTo(commentDtoOne.getBody());
        assertThat(commentDtoListRes.get(1).getHeader()).isEqualTo(commentDtoTwo.getHeader());
    }

    @Test
    @DisplayName("Save a comment from a comment dto")
    public void saveComment() {
        // GIVEN
        CommentDto commentDtoOne = new CommentDto();
        commentDtoOne.setId(1);
        commentDtoOne.setBody("Body dto 1");
        commentDtoOne.setHeader("Header dto 1");
        commentDtoOne.setCreateAt(Instant.now());
        commentDtoOne.setUpdateAt(Instant.now());
        commentDtoOne.setClientCommentDto(new CommentClientDto());

        CommentEntity commentEntityOne = new CommentEntity();
        commentEntityOne.setId(1);
        commentEntityOne.setBody("Body entity 1");
        commentEntityOne.setHeader("Header entity 1");
        commentEntityOne.setCreateAt(Instant.now());
        commentEntityOne.setUpdateAt(Instant.now());
        commentEntityOne.setClientEntity(new ClientEntity());

        // WHEN
        BDDMockito.given(commentMapper.mapToCommentEntity(commentDtoOne)).willReturn(commentEntityOne);
        BDDMockito.given(commentRepositoryImpl.save(commentEntityOne)).willReturn(commentEntityOne);
        BDDMockito.given(commentMapper.mapToCommentDto(commentEntityOne)).willReturn(commentDtoOne);
        CommentDto commentDtoRes = commentService.saveComment(commentDtoOne);

        // THEN
        verify(commentMapper, times(1)).mapToCommentEntity(commentDtoOne);
        verify(commentRepositoryImpl, times(1)).save(commentEntityOne);
        verify(commentMapper, times(1)).mapToCommentDto(commentEntityOne);
        assertThat(commentDtoRes).isNotNull();
        assertThat(commentDtoRes.getBody()).isEqualTo(commentDtoOne.getBody());
        assertThat(commentDtoRes.getHeader()).isEqualTo(commentDtoOne.getHeader());
    }

    @Test
    @DisplayName("Delete a comment base on his id")
    public void deleteCommentById() {
        // GIVEN
        Integer commentId = 1;

        // WHEN
        BDDMockito.willDoNothing().given(commentRepositoryImpl).updateCommentClientId(commentId);
        BDDMockito.willDoNothing().given(commentRepositoryImpl).deleteById(commentId);
        commentService.deleteCommentById(commentId);

        // THEN
        verify(commentRepositoryImpl, times(1)).updateCommentClientId(commentId);
        verify(commentRepositoryImpl, times(1)).deleteById(commentId);
    }

    @Test
    @DisplayName("Update a comment from a comment dto")
    public void updateComment() {
        // GIVEN
        CommentDto commentDtoOne = new CommentDto();
        commentDtoOne.setId(1);
        commentDtoOne.setBody("Body dto 1");
        commentDtoOne.setHeader("Header dto 1");
        commentDtoOne.setCreateAt(Instant.now());
        commentDtoOne.setUpdateAt(Instant.now());
        commentDtoOne.setClientCommentDto(new CommentClientDto());

        CommentEntity commentEntityOne = new CommentEntity();
        commentEntityOne.setId(1);
        commentEntityOne.setBody("Body entity 1");
        commentEntityOne.setHeader("Header entity 1");
        commentEntityOne.setCreateAt(Instant.now());
        commentEntityOne.setUpdateAt(Instant.now());
        commentEntityOne.setClientEntity(new ClientEntity());

        // WHEN
        BDDMockito.given(commentMapper.mapToCommentEntity(commentDtoOne)).willReturn(commentEntityOne);
        BDDMockito.given(commentRepositoryImpl.update(commentEntityOne)).willReturn(commentEntityOne);
        BDDMockito.given(commentMapper.mapToCommentDto(commentEntityOne)).willReturn(commentDtoOne);
        CommentDto commentDtoRes = commentService.updateComment(commentDtoOne);

        // THEN
        verify(commentMapper, times(1)).mapToCommentEntity(commentDtoOne);
        verify(commentRepositoryImpl, times(1)).update(commentEntityOne);
        verify(commentMapper, times(1)).mapToCommentDto(commentEntityOne);
        assertThat(commentDtoRes).isNotNull();
        assertThat(commentDtoRes.getBody()).isEqualTo(commentDtoOne.getBody());
        assertThat(commentDtoRes.getHeader()).isEqualTo(commentDtoOne.getHeader());
    }

    @Test
    @DisplayName("Get a comment base on his id")
    public void getCommentById() {
        // GIVEN
        CommentDto commentDtoOne = new CommentDto();
        commentDtoOne.setId(1);
        commentDtoOne.setBody("Body dto 1");
        commentDtoOne.setHeader("Header dto 1");
        commentDtoOne.setCreateAt(Instant.now());
        commentDtoOne.setUpdateAt(Instant.now());
        commentDtoOne.setClientCommentDto(new CommentClientDto());

        CommentEntity commentEntityOne = new CommentEntity();
        commentEntityOne.setId(1);
        commentEntityOne.setBody("Body entity 1");
        commentEntityOne.setHeader("Header entity 1");
        commentEntityOne.setCreateAt(Instant.now());
        commentEntityOne.setUpdateAt(Instant.now());
        commentEntityOne.setClientEntity(new ClientEntity());

        // WHEN
        BDDMockito.given(commentRepositoryImpl.findById(commentEntityOne.getId())).willReturn(commentEntityOne);
        BDDMockito.given(commentMapper.mapToCommentDto(commentEntityOne)).willReturn(commentDtoOne);
        CommentDto commentDtoRes = commentService.getCommentById(commentEntityOne.getId());

        // THEN
        verify(commentRepositoryImpl, times(1)).findById(commentEntityOne.getId());
        verify(commentMapper, times(1)).mapToCommentDto(commentEntityOne);
        assertThat(commentDtoRes).isNotNull();
        assertThat(commentDtoRes.getBody()).isEqualTo(commentDtoOne.getBody());
        assertThat(commentDtoRes.getHeader()).isEqualTo(commentDtoOne.getHeader());
    }
}
