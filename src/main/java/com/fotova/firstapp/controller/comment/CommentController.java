package com.fotova.firstapp.controller.comment;

import com.fotova.dto.comment.CommentDto;
import com.fotova.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("auth/comments")
    public ResponseEntity<Object> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @GetMapping("auth/comment/{commentId}")
    public ResponseEntity<Object> getCommentById(@PathVariable("commentId") Integer commentId) {
        return ResponseEntity.ok(commentService.getCommentById(commentId));
    }

    @PutMapping("auth/comment/update")
    public ResponseEntity<Object> updateComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(commentDto));
    }

    @PostMapping("auth/comment/add")
    public ResponseEntity<Object> addComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.saveComment(commentDto));
    }

}
