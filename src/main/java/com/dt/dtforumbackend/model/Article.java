package com.dt.dtforumbackend.model;

import com.dt.dtforumbackend.model.Keyword;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String preview;

    @Column(length = 255)
    private String author;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "messageCounts")
    private Integer messageCounts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "article_keywords",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private Set<Keyword> keywords;

    @Column(length = 255)
    private String topic;

    @Transient // This annotation marks the field as not persistent
    private Set<String> keywordNames = new HashSet<>();

    // Lombok will generate the necessary getters and setters
}
