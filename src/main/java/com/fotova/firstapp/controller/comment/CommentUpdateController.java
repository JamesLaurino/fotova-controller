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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1")
public class CommentUpdateController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AuthService authService;

    @Operation(summary = "Update a comment. An admin can update any comment, "
            + "a connected user can only update his own comment")
    @ApiResponse(responseCode = "200", description = "Comment updated successfully",
            content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CommentDto.class))
            })
    @ApiResponse(responseCode = "404", description = "Comment not found",
            content = @Content)
    @PutMapping("auth/comment/{commentId}/update")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Object> updateComment(
            @Parameter(description = "Comment identifier - id", required = true, example = "1")
            @PathVariable("commentId") Integer commentId,
            @RequestBody @Valid CommentDto commentDto) {

        commentDto.setId(commentId);

        if (!authService.isAdmin()) {
            ClientDto principal = authService.getPrincipal();
            CommentDto existing = commentService.getCommentById(commentId);
            if (existing.getClientCommentDto() == null
                    || !principal.getId().equals(existing.getClientCommentDto().getClientId())) {
                throw new NotFoundException("Comment not found for id: " + commentId);
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
}
