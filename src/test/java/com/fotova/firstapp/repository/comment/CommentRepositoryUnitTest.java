package com.fotova.firstapp.repository.comment;

import com.fotova.entity.ClientEntity;
import com.fotova.entity.CommentEntity;
import com.fotova.repository.client.ClientRepositoryJpa;
import com.fotova.repository.comment.CommentRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CommentRepositoryUnitTest {

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    private  CommentEntity commentEntityTwo;

    private ClientEntity clientEntityOne;

    @BeforeEach
    public void init() {
        commentRepositoryJpa.deleteAll();
        clientRepositoryJpa.deleteAll();

        clientEntityOne = new ClientEntity();
        clientEntityOne.setUsername("James");
        clientEntityOne.setPassword("<PASSWORD 1>");
        clientEntityOne.setEmail("<EMAIL 1>");
        clientEntityOne.setCreatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));
        clientEntityOne.setUpdatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));

        ClientEntity clientEntityTwo = new ClientEntity();
        clientEntityTwo.setUsername("Thomas");
        clientEntityTwo.setPassword("<PASSWORD 2>");
        clientEntityTwo.setEmail("<EMAIL 2>");
        clientEntityTwo.setCreatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));
        clientEntityTwo.setUpdatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));

        ClientEntity clientEntityThree = new ClientEntity();
        clientEntityThree.setUsername("Thomas");
        clientEntityThree.setPassword("<PASSWORD 3>");
        clientEntityThree.setEmail("<EMAIL 3>");
        clientEntityThree.setCreatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));
        clientEntityThree.setUpdatedAt(Timestamp.valueOf("2024-01-01 00:00:00"));

        clientRepositoryJpa.saveAll(List.of(clientEntityOne, clientEntityTwo, clientEntityThree));

        CommentEntity commentEntityOne = new CommentEntity();
        commentEntityOne.setBody("comment body 1");
        commentEntityOne.setHeader("comment header 1");
        commentEntityOne.setCreateAt(Instant.now());
        commentEntityOne.setClientEntity(clientEntityOne);

        commentEntityTwo = new CommentEntity();
        commentEntityTwo.setBody("comment body 2");
        commentEntityTwo.setHeader("comment header 2");
        commentEntityTwo.setCreateAt(Instant.now());
        commentEntityTwo.setClientEntity(clientEntityTwo);

        CommentEntity commentEntityThree = new CommentEntity();
        commentEntityThree.setBody("comment body 3");
        commentEntityThree.setHeader("comment header 3");
        commentEntityThree.setCreateAt(Instant.now());
        commentEntityThree.setClientEntity(clientEntityThree);

        commentRepositoryJpa.saveAll(List.of(commentEntityOne, commentEntityTwo, commentEntityThree));

    }

    @DisplayName("should set client to null for a given comment id")
    @Test
    public void shouldSetClientToNullForGivenCommentId() {

        // WHEN
        commentRepositoryJpa.updateCommentFromClient(commentEntityTwo.getId());
        List<CommentEntity> commentEntityList = commentRepositoryJpa.findAll();

        // THEN
        assertThat(commentEntityList).isNotEmpty();
        assertThat(commentEntityList).hasSize(3);
        assertThat(commentEntityList).isNotNull();

        commentEntityList.forEach((comment) -> {
            if(comment.getClientEntity() != null)
                assertThat(comment.getId()).isNotEqualTo(commentEntityTwo.getId());

            if(comment.getId().equals(commentEntityTwo.getId()))
                assertThat(comment.getClientEntity()).isNull();

            if(comment.getClientEntity() == null)
                assertThat(comment.getId()).isEqualTo(commentEntityTwo.getId());
        });

    }

    @DisplayName("should update comment's client by comment id and client id")
    @Test
    public void shouldUpdateClientOfCommentByIds() {

        // GIVEN
        CommentEntity commentEntityFourth = new CommentEntity();
        commentEntityFourth.setBody("comment body 4");
        commentEntityFourth.setHeader("comment header 4");
        commentEntityFourth.setCreateAt(Instant.now());
        commentRepositoryJpa.save(commentEntityFourth);

        // WHEN
        commentRepositoryJpa.setCommentClientId(clientEntityOne.getId(),commentEntityFourth.getId());
        CommentEntity commentEntityUpdated = commentRepositoryJpa.findById(commentEntityFourth.getId()).orElse(null);
        List<ClientEntity> clientEntityList = clientRepositoryJpa.findAll();
        List<CommentEntity> commentEntityList = commentRepositoryJpa.findAll();

        // THEN
        assertThat(clientEntityList).isNotEmpty();
        assertThat(clientEntityList).hasSize(3);
        assertThat(clientEntityList).isNotNull();

        assertThat(commentEntityList).isNotEmpty();
        assertThat(commentEntityList).hasSize(4);
        assertThat(commentEntityList).isNotNull();

        assertThat(commentEntityUpdated).isNotNull();
        assertThat(commentEntityUpdated.getClientEntity()).isNotNull();
        assertThat(commentEntityUpdated.getClientEntity().getId()).isEqualTo(clientEntityOne.getId());
    }
}
