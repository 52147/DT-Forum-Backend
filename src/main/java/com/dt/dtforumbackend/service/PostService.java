package com.dt.dtforumbackend.service;

import com.dt.dtforumbackend.mapper.PostMapper;
import com.dt.dtforumbackend.model.Post;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    private final PostMapper postMapper;

    public PostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    public List<Post> getAllPosts() {
        return postMapper.findAll();
    }
}