package com.sbs.example.lolHi.controller.usr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.example.lolHi.Service.ArticleService;

@Controller
public class HomeController {
	@Autowired
	private ArticleService articleService;
	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}
}
