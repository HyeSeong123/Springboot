package com.sbs.example.lolHi.controller.usr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	@RequestMapping("/usr/article/list")
	public String showList() {
		
		List<Article> articles = articleService.getArticle();
		
		return "usr/article/list";
	}
}