package com.dt.dtforumbackend.mapper;

import com.dt.dtforumbackend.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface PostMapper {
    @Select("SELECT * FROM posts")
    List<Post> findAll();
}