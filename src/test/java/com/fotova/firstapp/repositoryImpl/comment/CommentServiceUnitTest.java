package com.fotova.firstapp.repositoryImpl.comment;

import com.fotova.entity.ClientEntity;
import com.fotova.entity.CommentEntity;
import com.fotova.entity.ERole;
import com.fotova.entity.RoleEntity;
import com.fotova.repository.comment.CommentRepositoryImpl;
import com.fotova.repository.comment.CommentRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CommentServiceUnitTest {

    @Mock
    private CommentRepositoryJpa commentRepositoryJpa;

    @InjectMocks
    private CommentRepositoryImpl commentRepositoryImpl;

    private CommentEntity commentEntityOne = new CommentEntity();
    private CommentEntity commentEntityTwo = new CommentEntity();

    @BeforeEach
    public void init() {

        // GIVEN
        commentEntityOne.setId(1);
        commentEntityOne.setBody("Hello there");
        commentEntityOne.setHeader("Congratulation");
        commentEntityOne.setUpdateAt(Instant.now());
        commentEntityOne.setCreateAt(Instant.now());

        commentEntityTwo.setId(2);
        commentEntityTwo.setBody("Hello there 2");
        commentEntityTwo.setHeader("Congratulation 2");
        commentEntityTwo.setUpdateAt(Instant.now());
        commentEntityTwo.setCreateAt(Instant.now());
    }

    @Test
    @DisplayName("Find all comments")
    @Order(1)
    public void givenCommentRepository_whenFindAll_thenReturnList() {

        // GIVEN
        BDDMockito.given(commentRepositoryJpa.findAll()).willReturn(List.of(commentEntityOne, commentEntityTwo));

        //WHEN
        List<CommentEntity> commentEntityList = commentRepositoryImpl.findAll();

        // THEN
        assertThat(commentEntityList).isNotEmpty();
        assertThat(commentEntityList).hasSize(2);
        assertThat(commentEntityList).isNotNull();
    }

    @Test
    @DisplayName("Find comment by id")
    @Order(2)
    public void givenCommentRepository_whenFindById_thenReturnComment() {

        // GIVEN
        BDDMockito.given(commentRepositoryJpa.findById(1)).willReturn(Optional.ofNullable(commentEntityOne));

        // WHEN
        CommentEntity commentEntity = commentRepositoryImpl.findById(1);

        // THEN
        assertThat(commentEntity).isNotNull();
        assertThat(commentEntity.getId()).isEqualTo(1);
        assertThat(commentEntity.getBody()).isEqualTo("Hello there");
        assertThat(commentEntity.getHeader()).isEqualTo("Congratulation");
        assertThat(commentEntity.getCreateAt()).isNotNull();
        assertThat(commentEntity.getUpdateAt()).isNotNull();

    }

    @Test
    @DisplayName("Delete comment by id")
    @Order(3)
    public void givenDeleteAllComment_whenDeleteById_thenSuccess() {

        // WHEN
        BDDMockito.willDoNothing().given(commentRepositoryJpa).deleteById(1);
        commentRepositoryImpl.deleteById(1);

        // THEN : vérifier que la méthode est utilisée avec le bon paramètre
        verify(commentRepositoryJpa, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Update comment")
    @Order(4)
    public void givenUpdateComment_whenUpdateById_thenSuccess() {

        // GIVEN
        CommentEntity updatedComment = new CommentEntity();
        updatedComment.setId(1);
        updatedComment.setBody("Updated");
        updatedComment.setHeader("Header");
        updatedComment.setCreateAt(Instant.now());
        updatedComment.setUpdateAt(Instant.now());

        Mockito.when(commentRepositoryJpa.save(commentEntityOne)).thenReturn(updatedComment);

        // WHEN
        CommentEntity result = commentRepositoryImpl.update(commentEntityOne);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getBody()).isEqualTo("Updated");
        verify(commentRepositoryJpa, times(1)).save(commentEntityOne);
    }

    @Test
    @DisplayName("set comment client Id")
    @Order(5)
    public void givenCommentAndClient_whenSetCommentIdClientId_thenCommentClientIdIsSetForComment() {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setUsername("Thomas");
        clientEntity.setPassword("<PASSWORD>");
        clientEntity.setEmail("<EMAIL>");
        clientEntity.setRoles(Set.of(new RoleEntity(ERole.ROLE_ADMIN)));

        // WHEN
        BDDMockito.willDoNothing().given(commentRepositoryJpa)
                .setCommentClientId(clientEntity.getId(),commentEntityOne.getId());

        commentRepositoryImpl.setCommentClientId(clientEntity.getId(),commentEntityOne.getId());


        // THEN
        verify(commentRepositoryJpa, times(1))
                .setCommentClientId(clientEntity.getId(),commentEntityOne.getId());
    }

    @Test
    @DisplayName("update comment client id")
    @Order(6)
    public void given_whenCommentId_thenClientIsNull() {

        // WHEN
        BDDMockito.willDoNothing().given(commentRepositoryJpa)
                .updateCommentFromClient(commentEntityOne.getId());

        commentRepositoryImpl.updateCommentClientId(commentEntityOne.getId());


        // THEN
        verify(commentRepositoryJpa, times(1))
                .updateCommentFromClient(commentEntityOne.getId());
    }
}
