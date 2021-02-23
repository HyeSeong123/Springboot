package com.sbs.example.lolHi.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.ArticleDao;
import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public List<Article> getArticles(Member actorMember, Map<String, Object> param) {
		int page = Util.getAsInt(param.get("page"), 0);

		if (page < 0 || page == 0) {
			page = 1;
		}

		int itemsInAPage = 20;

		int limitFrom = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;

		param.put("limitFrom", limitFrom);
		param.put("limitTake", limitTake);

		List<Article> articles = articleDao.getArticles(param);

		for (Article article : articles) {
			if (article.getExtra() == null) {
				article.setExtra(new HashMap<>());
			}

			boolean actorCanDelete = false;
			
			if(actorMember != null) {
				actorCanDelete = actorMember.getNum() == article.getMemberNum();
			}
			
			boolean actorCanModify = actorCanDelete;
			
			article.getExtra().put("actorCanDelete", actorCanDelete);
			article.getExtra().put("actorCanModify", actorCanModify);
		}

		return articles;
	}

	public Article getArticleByNum(Member actorMember, int num) {
		Article article = articleDao.getArticleByNum(num);
		
		if(article.getExtra() == null) {
			article.setExtra(new HashMap<>());
		}
		
		boolean actorCanDelete = false;
		
		if(actorMember != null) {
			actorCanDelete = actorMember.getNum() == article.getMemberNum();
		}
		
		boolean actorCanModify = actorCanDelete;
		
		article.getExtra().put("actorCanDelete", actorCanDelete);
		article.getExtra().put("actorCanModify", actorCanModify);
		
		return article;
	}

	public void doDeleteArticleByNum(int num) {
		articleDao.doDeleteArticleByNum(num);
	}

	public void doModifyArticleByNum(int num, String title, String body) {
		articleDao.doModifyArticleByNum(num, title, body);

	}

	public int doWriteArticleByNum(Map<String, Object> param) {
		articleDao.doWriteArticleByNum(param);

		int num = Util.getAsInt(param.get("num"), 0);

		return num;
	}

	public int totalCount(Map<String, Object> param) {
		return articleDao.totalCount(param);
	}

}
