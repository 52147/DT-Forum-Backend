package com.dt.dtforumbackend.mapper;

import com.dt.dtforumbackend.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {

    @Select("SELECT * FROM messages WHERE articleId = #{articleId}")
    List<Comment> findByArticleId(Integer articleId);

    @Select("SELECT * FROM messages WHERE reply = #{commentId}")
    List<Comment> findRepliesByCommentId(Integer commentId);

    @Select("SELECT * FROM messages WHERE articleId = #{articleId} AND reply IS NULL")
    List<Comment> findByArticleIdAndReplyIsNull(Integer articleId);
}
