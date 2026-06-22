package com.fotova.firstapp.controller.comment;

import com.fotova.dto.client.ClientDto;
import com.fotova.dto.comment.CommentDto;
import com.fotova.exception.NotFoundException;
import com.fotova.firstapp.security.service.AuthService;
import com.fotova.firstapp.security.utils.Response;
import com.fotova.service.comment.CommentService;
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
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AuthService authService;

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
    public ResponseEntity<Object> updateComment(@RequestBody @Valid CommentDto commentDto) {
        if (!authService.isAdmin()) {
            ClientDto principal = authService.getPrincipal();
            CommentDto existing = commentDto.getId() == null ? null : commentService.getCommentById(commentDto.getId());
            if (existing == null || existing.getClientCommentDto() == null
                    || !principal.getId().equals(existing.getClientCommentDto().getClientId())) {
                throw new NotFoundException("Comment not found");
            }
        }
        Response<CommentDto> response = Response.<CommentDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment updated successfully")
                .data(commentService.updateComment(commentDto))
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
        String result;
        if (authService.isAdmin()) {
            result = commentService.deleteCommentById(commentId);
        } else {
            ClientDto clientDto = authService.getPrincipal();
            result = commentService.deleteCommentByIdWithClientId(commentId, clientDto.getId());
        }
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Comment deleted successfully")
                .data(result)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

}
