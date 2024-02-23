package com.dt.dtforumbackend.controller;

import com.dt.dtforumbackend.model.Comment;
import com.dt.dtforumbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/article/{articleId}")
    public List<Comment> getCommentsByArticleId(@PathVariable Integer articleId) {
        return commentService.getCommentsByArticleId(articleId);
    }

}
