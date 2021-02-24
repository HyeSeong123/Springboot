package com.sbs.example.lolHi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbs.example.lolHi.dto.Article;

@Mapper
public interface ArticleDao {

	List<Article> getArticles(Map<String, Object> param);

	Article getArticleByNum(@Param("num") int num);

	void doDeleteArticleByNum(@Param("num") int num);

	void doModifyArticleByNum(@Param("num") int num, @Param("title") String title, @Param("body") String body);

	void doWriteArticleByNum(Map<String, Object> param);

	int totalCount(Map<String, Object> param);

	Article getArticleByNumForReply(@Param("num") int num, @Param("replyNum") int replyNum);
}
