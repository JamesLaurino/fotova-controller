package com.fotova.firstapp.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fotova.dto.comment.CommentDto;
import com.fotova.service.comment.CommentService;
import org.hamcrest.CoreMatchers;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get all comments")
    public void getAllComments() throws Exception {
        // GIVEN
        List<CommentDto> commentDtoList = List.of(new CommentDto(), new CommentDto(), new CommentDto());

        // WHEN
        BDDMockito.given(commentService.getAllComments()).willReturn(commentDtoList);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/comments"));

        // THEN
        verify(commentService, times(1)).getAllComments();
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()",
                        CoreMatchers.is(commentDtoList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Comments retrieve successfully")));
    }

    @Test
    @DisplayName("Get comment by id")
    public void getCommentById() throws Exception {
        // GIVEN
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setHeader("header 1");
        commentDto.setBody("Body 1");
        commentDto.setCreateAt(Instant.now());
        commentDto.setUpdateAt(Instant.now());

        // WHEN
        BDDMockito.given(commentService.getCommentById(commentDto.getId())).willReturn(commentDto);
        ResultActions resultActions = mockMvc.perform(get("/api/v1/auth/comment/{commentId}", commentDto.getId()));

        // THEN
        verify(commentService, times(1)).getCommentById(commentDto.getId());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(commentDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.header",
                        CoreMatchers.is(commentDto.getHeader())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.body",
                        CoreMatchers.is(commentDto.getBody())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Comment retrieved successfully")));
    }

    @Test
    @DisplayName("Update a comment")
    public void updateComment() throws Exception {
        // GIVEN
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setHeader("header 1");
        commentDto.setBody("Body 1");
        commentDto.setCreateAt(Instant.now());
        commentDto.setUpdateAt(Instant.now());

        // WHEN
        BDDMockito.given(commentService.updateComment(commentDto)).willReturn(any(CommentDto.class));
        ResultActions resultActions = mockMvc.perform(put("/api/v1/auth/comment/update")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDto)));

        // THEN
        verify(commentService, times(1)).updateComment(any(CommentDto.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Comment updated successfully")));
    }

    @Test
    @DisplayName("Add a comment")
    public void addComment() throws Exception {
        // GIVEN
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setHeader("header 1");
        commentDto.setBody("Body 1");
        commentDto.setCreateAt(Instant.now());
        commentDto.setUpdateAt(Instant.now());

        // WHEN
        BDDMockito.given(commentService.saveComment(any(CommentDto.class)))
                .willReturn(commentDto);
        ResultActions resultActions = mockMvc.perform(post("/api/v1/auth/comment/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(commentDto)));

        // THEN
        verify(commentService, times(1)).saveComment(any(CommentDto.class));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id",
                        CoreMatchers.is(commentDto.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.header",
                        CoreMatchers.is(commentDto.getHeader())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.body",
                        CoreMatchers.is(commentDto.getBody())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Comment added successfully")));
    }

    @Test
    @DisplayName("Deletion of a comment")
    public void deleteComment() throws Exception {
        // GIVEN
        Integer commentId = 1;

        // WHEN
        BDDMockito.given(commentService.deleteCommentById(commentId)).willReturn("Comment has been deleted successfully for id : " + commentId);
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/auth/comment/{commentId}/delete",
                commentId));

        // THEN
        verify(commentService, times(1)).deleteCommentById(commentId);
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success",
                        CoreMatchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode",
                        CoreMatchers.is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data",
                        CoreMatchers.is("Comment has been deleted successfully for id : " + commentId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseMessage",
                        CoreMatchers.is("Comment deleted successfully")));

    }
}
