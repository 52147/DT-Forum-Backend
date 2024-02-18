package com.dt.dtforumbackend.mapper;

import com.dt.dtforumbackend.model.Keyword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KeywordMapper {

    @Select("SELECT * FROM keywords")
    List<Keyword> findAll();

    @Select("SELECT k.id, k.name FROM keywords k " + "JOIN article_keywords ak ON k.id = ak.keyword_id " + "WHERE ak.article_id = #{articleId}")
    List<Keyword> findByArticleId(@Param("articleId") Integer articleId);

    @Select("SELECT k.* FROM keywords k " + "INNER JOIN article_keywords ak ON k.id = ak.keyword_id " + "WHERE ak.article_id = #{articleId}")
    List<Keyword> findKeywordsByArticleId(@Param("articleId") Integer articleId);


}
