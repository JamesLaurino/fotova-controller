package com.fotova.firstapp.controller.comment;

import com.fotova.dto.client.ClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Retrieve all comments")
    @ApiResponse(responseCode = "200", description = "Comments retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CommentDto.class))
            })
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

    @Operation(summary = "Retrieve a comment by its id")
    @ApiResponse(responseCode = "200", description = "Comment retrieved successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CommentDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Comment not found",
            content = @Content)
    @GetMapping("auth/comment/{commentId}")
    public ResponseEntity<Object> getCommentById(
            @Parameter(description = "Comment identifier - id", required = true, example = "1")
            @PathVariable("commentId") Integer commentId) {
        Response<CommentDto> response = Response.<CommentDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment retrieved successfully")
                .data(commentService.getCommentById(commentId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a comment")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CommentDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Comment not found",
            content = @Content)
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

    @Operation(summary = "Add a new comment")
    @ApiResponse(responseCode = "200", description = "Comment added successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CommentDto.class))
            })
    @ApiResponse(responseCode = "409", description = "Comment already exists",
            content = @Content)
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

    @Operation(summary = "Delete a comment by its id")
    @ApiResponse(responseCode = "200", description = "Comment deleted successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = String.class))
            })
    @ApiResponse(responseCode = "404", description = "Comment not found",
            content = @Content)
    @DeleteMapping("auth/comment/{commentId}/delete")
    public ResponseEntity<Object> deleteComment(
            @Parameter(description = "Comment identifier - id", required = true, example = "1")
            @PathVariable("commentId") Integer commentId) {
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment deleted successfully")
                .data(commentService.deleteCommentById(commentId))
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

}
