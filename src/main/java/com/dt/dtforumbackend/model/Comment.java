package com.dt.dtforumbackend.model;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "messages")
public class Comment {
    private Integer id;
    private Integer articleId;
    private String author;
    private String content;
    private Date timestamp;
    private Integer likes;
    private Integer unlikes;
    private Integer reply;
    private List<Comment> replies = new ArrayList<>(); // To hold nested replies

    // Getters and setters
}
