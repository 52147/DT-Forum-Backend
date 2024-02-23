package com.dt.dtforumbackend.service;

import com.dt.dtforumbackend.model.Keyword;

import com.dt.dtforumbackend.mapper.ArticleMapper;
import com.dt.dtforumbackend.mapper.KeywordMapper;
import com.dt.dtforumbackend.model.Article;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
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
            List<String> keywordNames = keywordMapper.findByArticleId(article.getId()).stream().map(Keyword::getName).collect(Collectors.toList());
            article.setKeywordNames(new HashSet<>(keywordNames)); // Assuming a setter for keyword names
        });
        return articles;
    }

    //  Method to get an article by ID
    public Article getArticleById(Integer id) {
        Article article = articleMapper.findById(id); // Assuming this method is defined in ArticleMapper
        if (article != null) {
            List<String> keywordNames = keywordMapper.findByArticleId(article.getId()).stream().map(Keyword::getName).collect(Collectors.toList());
            article.setKeywordNames(new HashSet<>(keywordNames)); // Assuming Article has a setter for keywordNames
        }
        return article;
    }

    // 1. Find articles with the same topic and at least one shared keyword.
    // 2. If no articles share a keyword, include all articles with the same topic.
    // 3. Exclude the article with the given articleId from the results.
    // 4. Fallback to including all articles if no related articles are found based on the topic.
    public List<Article> findRelatedArticlesOrFallback(Integer articleId) {
        // Fetch the target article to determine its topic and keywords
        Article targetArticle = articleMapper.findById(articleId);
        if (targetArticle == null) {
            throw new IllegalArgumentException("Article not found with ID: " + articleId);
        }
        List<Keyword> targetKeywords = keywordMapper.findByArticleId(articleId);
        Set<String> targetKeywordNames = targetKeywords.stream()
                .map(Keyword::getName)
                .collect(Collectors.toSet());

        // Fetch all articles except the target article
        List<Article> allArticles = articleMapper.findAll().stream()
                .filter(article -> !article.getId().equals(articleId))
                .collect(Collectors.toList());

        // Filter articles by the same topic
        List<Article> sameTopicArticles = allArticles.stream()
                .filter(article -> article.getTopic().equals(targetArticle.getTopic()))
                .collect(Collectors.toList());

        // Find articles with at least one shared keyword
        List<Article> articlesWithSharedKeyword = sameTopicArticles.stream()
                .filter(article -> {
                    List<Keyword> articleKeywords = keywordMapper.findByArticleId(article.getId());
                    Set<String> articleKeywordNames = articleKeywords.stream()
                            .map(Keyword::getName)
                            .collect(Collectors.toSet());
                    // Check for intersection in keywords
                    return !Collections.disjoint(targetKeywordNames, articleKeywordNames);
                })
                .collect(Collectors.toList());

        // Determine final list of related articles
        List<Article> relatedArticles;
        if (!articlesWithSharedKeyword.isEmpty()) {
            relatedArticles = articlesWithSharedKeyword;
        } else if (!sameTopicArticles.isEmpty()) {
            relatedArticles = sameTopicArticles;
        } else {
            relatedArticles = allArticles;
        }

        return relatedArticles;
    }

    // Method to get the topic of the article with the given ID
    private String getArticleTopic(Integer articleId) {
        Article article = articleMapper.findById(articleId);
        return (article != null) ? article.getTopic() : null;
    }

    public List<Article> getArticlesByKeywordNames(List<String> keywordNames) {
        return articleMapper.findArticlesByKeywordNames(keywordNames);
    }
    public void incrementLikesCount(Integer articleId) {
        articleMapper.incrementLikesCount(articleId);
    }

}
