package com.sbs.example.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.example.lolHi.Service.ArticleService;
import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.util.Util;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam Map<String, Object> param) {
		List<Article> articles = articleService.getArticles(param);
		int totalCount = articleService.totalCount();
		int itemsCountInAPage = 20;
		int page = Util.getAsInt(param.get("page"), 1);
		int totalPage = (int) Math.ceil(totalCount/ (double)itemsCountInAPage);
		int pageMenuArmSize = 10;
		int pageMenuStart = page -  pageMenuArmSize;

		if(pageMenuStart < 1) {
			pageMenuStart = 1;
		}
		int pageMenuEnd = page + pageMenuArmSize;
		
		if(pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;
		}
		
		param.put("itemsCountInAPage", itemsCountInAPage);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageMenuStart", pageMenuStart);
		model.addAttribute("pageMenuEnd", pageMenuEnd);
		model.addAttribute("articles", articles);
		
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int num) {
		Article article = articleService.getArticleByNum(num);
		
		model.addAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int num) {
		
		articleService.doDeleteArticleByNum(num);

		
		return String.format("<script> alert('%d번 글을 삭제하였습니다'); location.replace('/usr/article/list'); </script>", num);
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int num) {
		
		Article article = articleService.getArticleByNum(num);
		
		model.addAttribute("article", article);
		
		return String.format("usr/article/modify");
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int num, String title, String body,HttpSession session, Model model) {
		
		int loginedMemberNum = 0 ;
		
		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int)session.getAttribute("loginedMemberNum");
		}
		
		if(loginedMemberNum == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}
		
		model.addAttribute("msg", num + "번 글을 수정하였습니다");
		model.addAttribute("replaceUri", "/usr/article/list");
		
		articleService.doModifyArticleByNum(num, title, body);
		
		
		return String.format("<script> alert('%d번'); location.replace('/usr/article/detail?num=%d'); </script>", num, num);
	}
	
	@RequestMapping("/usr/article/write")
	public String showWrite(HttpSession session, Model model) {
		int loginedMemberNum = 0 ;
		
		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int)session.getAttribute("loginedMemberNum");
		}
		
		if(loginedMemberNum == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}
		
		return String.format("usr/article/write");
	}
	
	@RequestMapping("usr/article/doWrite")
	public String doWrite(@RequestParam Map<String,Object> param, Model model, HttpSession session) {
		
		int num = articleService.doWriteArticleByNum(param);
		
		int loginedMemberNum = 0 ;
		
		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int)session.getAttribute("loginedMemberNum");
		}
		
		if(loginedMemberNum == 0) {
			model.addAttribute("msg", "로그인 후 이용해주세요");
			model.addAttribute("replaceUri", "/usr/member/login");
			return "common/redirect";
		}
		
		model.addAttribute("msg", num + "번 글을 작성하였습니다");
		model.addAttribute("replaceUri", "/usr/article/list");
		
		return "common/redirect";
	}
}