package com.dt.dtforumbackend.mapper;


import com.dt.dtforumbackend.model.Article;
import com.dt.dtforumbackend.model.Keyword;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("SELECT * FROM articles")
    @Results({@Result(property = "id", column = "id"), @Result(property = "keywords", column = "id", many = @Many(select = "selectKeywordsForArticle"))})
    List<Article> findAll();

    @Select("SELECT k.* FROM keywords k " + "JOIN article_keywords ak ON k.id = ak.keyword_id " + "WHERE ak.article_id = #{articleId}")
    List<Keyword> selectKeywordsForArticle(Integer articleId);

    @Select("SELECT * FROM articles WHERE id = #{id}")
    @Results({@Result(property = "id", column = "id"), @Result(property = "keywords", column = "id", many = @Many(select = "selectKeywordsForArticle"))})
    Article findById(Integer id);


    List<Article> findArticlesByKeywordNames(List<String> keywordNames);

    @Select("SELECT * FROM articles WHERE topic = #{articleTopic}")
    List<Article> findArticlesByTopic(String articleTopic);

    @Update("UPDATE articles SET likes_count = likes_count + 1 WHERE id = #{id}")
    void incrementLikesCount(Integer id);
}

