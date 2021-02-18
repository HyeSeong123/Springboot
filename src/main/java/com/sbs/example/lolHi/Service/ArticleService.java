package com.sbs.example.lolHi.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.ArticleDao;
import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.util.Util;

@Service
public class ArticleService {
	@Autowired
	private ArticleDao articleDao;
	
	public List<Article> getArticles(Map<String, Object> param) {
		int page = Util.getAsInt(param.get("page"),0);
		
		if(page < 0 || page == 0) {
			page = 1;
		}
		
		int itemsInAPage = 20;
		
		int limitFrom = (page -1) * itemsInAPage;
		int limitTake = itemsInAPage;
		
		param.put("limitFrom", limitFrom);
		param.put("limitTake", limitTake);
		
		return articleDao.getArticles(param);
	}

	public Article getArticleByNum(int num) {
		return articleDao.getArticleByNum(num);
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

}
