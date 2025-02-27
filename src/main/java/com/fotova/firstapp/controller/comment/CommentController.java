package com.fotova.firstapp.controller.comment;

import com.fotova.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("auth/comments")
    public ResponseEntity<Object> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

}
