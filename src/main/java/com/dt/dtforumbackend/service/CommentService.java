package com.dt.dtforumbackend.service;

import com.dt.dtforumbackend.mapper.CommentMapper;
import com.dt.dtforumbackend.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public List<Comment> getCommentsByArticleId(Integer articleId) {
        // Fetch top-level comments (where reply is null)
        List<Comment> topLevelComments = commentMapper.findByArticleIdAndReplyIsNull(articleId);
        // Recursively load replies for each top-level comment
        topLevelComments.forEach(this::loadReplies);
        return topLevelComments;
    }

    private void loadReplies(Comment comment) {
        List<Comment> replies = commentMapper.findRepliesByCommentId(comment.getId());
        comment.setReplies(replies);
        replies.forEach(this::loadReplies); // Recurse to load further nested replies
    }

    private void setReplies(Comment comment) {
        List<Comment> replies = findReplies(comment.getId());
        replies.forEach(this::setReplies); // Recursively set replies for nested comments
        comment.setReplies(replies);
    }

    private List<Comment> findReplies(Integer commentId) {
        return commentMapper.findRepliesByCommentId(commentId);
    }
}
