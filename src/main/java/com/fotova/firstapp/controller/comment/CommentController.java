package com.fotova.firstapp.controller.comment;

import com.fotova.dto.client.ClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("auth/comments")
    public ResponseEntity<Object> getAllComments() {
        Response<List<CommentDto>> response = Response.<List<CommentDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comments retrieve successfully")
                .data(commentService.getAllComments())
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("auth/comment/{commentId}")
    public ResponseEntity<Object> getCommentById(@PathVariable("commentId") Integer commentId) {
        Response<CommentDto> response = Response.<CommentDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment retrieved successfully")
                .data(commentService.getCommentById(commentId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("auth/comment/update")
    public ResponseEntity<Object> updateComment(@RequestBody CommentDto commentDto) {
        Response<CommentDto> response = Response.<CommentDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment updated successfully")
                .data(commentService.updateComment(commentDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/comment/add")
    public ResponseEntity<Object> addComment(@RequestBody CommentDto commentDto) {
        Response<CommentDto> response = Response.<CommentDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment added successfully")
                .data(commentService.saveComment(commentDto))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("auth/comment/{commentId}/delete")
    public ResponseEntity<Object> deleteComment(@PathVariable("commentId") Integer commentId) {
        commentService.deleteCommentById(commentId);
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment deleted successfully")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

}
