package com.dt.dtforumbackend.service;
import com.dt.dtforumbackend.model.Keyword;

import com.dt.dtforumbackend.mapper.ArticleMapper;
import com.dt.dtforumbackend.mapper.KeywordMapper;
import com.dt.dtforumbackend.model.Article;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleMapper articleMapper;
    private final KeywordMapper keywordMapper;

    public ArticleService(ArticleMapper articleMapper, KeywordMapper keywordMapper) {
        this.articleMapper = articleMapper;
        this.keywordMapper = keywordMapper;
    }

    public List<Article> getAllArticles() {
        List<Article> articles = articleMapper.findAll();
        articles.forEach(article -> {
            List<String> keywordNames = keywordMapper.findByArticleId(article.getId())
                    .stream()
                    .map(Keyword::getName)
                    .collect(Collectors.toList());
            article.setKeywordNames(new HashSet<>(keywordNames)); // Assuming a setter for keyword names
        });
        return articles;
    }
    // New method to get an article by ID
    public Article getArticleById(Integer id) {
        Article article = articleMapper.findById(id); // Assuming this method is defined in ArticleMapper
        if (article != null) {
            List<String> keywordNames = keywordMapper.findByArticleId(article.getId())
                    .stream()
                    .map(Keyword::getName)
                    .collect(Collectors.toList());
            article.setKeywordNames(new HashSet<>(keywordNames)); // Assuming Article has a setter for keywordNames
        }
        return article;
    }
}
