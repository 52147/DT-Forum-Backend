package com.dt.dtforumbackend.controller;

import com.dt.dtforumbackend.model.Article;
import com.dt.dtforumbackend.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    // New endpoint to get an article by ID
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Integer id) {
        Article article = articleService.getArticleById(id);
        if (article != null) {
            return new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/related")
    public ResponseEntity<List<Article>> findRelatedArticles(@PathVariable Integer id) {
        List<Article> relatedArticles = articleService.findRelatedArticlesOrFallback(id);
        if (relatedArticles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(relatedArticles);
    }
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> incrementLike(@PathVariable("id") Integer id) {
        articleService.incrementLikesCount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

