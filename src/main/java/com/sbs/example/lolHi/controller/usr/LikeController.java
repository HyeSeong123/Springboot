package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.Service.ArticleService;
import com.sbs.example.lolHi.Service.LikeService;
import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.dto.Like;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.util.Util;

@Controller
public class LikeController {
	@Autowired
	private LikeService likeService;

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/usr/like/doLike")
	public String doLike(HttpServletRequest req, Model model,@RequestParam Map<String, Object> param,String listUrl) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		
		int num = Util.getAsInt(param.get("num"), 0);
		
		listUrl = Util.getUriEncoded(listUrl);
		
		Article article = articleService.getArticleByNum(loginedMember, num);
		
		Like like = likeService.getLikeByParam(param); 
		
		if(like != null) {
			if(article.getMemberNum() == like.getMemberNum()) {
				model.addAttribute("msg", "중복 추천은 불가합니다.");
				model.addAttribute("replaceUri", "/usr/article/detail?num=" + article.getNum() + "&listUrl=" + listUrl);
				return "common/redirect";
			}
		}

		likeService.doLike(param);
		
		model.addAttribute("msg", "좋아요 처리 되엇습니다.");
		model.addAttribute("replaceUri", "/usr/article/detail?num=" + article.getNum() + "&listUrl=" + listUrl);
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/like/doDislike")
	public String doDislike(HttpServletRequest req, Model model,@RequestParam Map<String, Object> param,String listUrl) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		int num = Util.getAsInt(param.get("num"), 0);
		
		listUrl = Util.getUriEncoded(listUrl);
		
		Article article = articleService.getArticleByNum(loginedMember, num);
		
		Like like = likeService.getLikeByParam(param);
		
		System.out.println("like= " + like);
		System.out.println("article= " + article);
		
		if(like != null) {
			if(loginedMember.getNum() != like.getMemberNum()) {
				model.addAttribute("msg", "좋아요를 누른 뒤 사용 가능합니다.");
				model.addAttribute("replaceUri", "/usr/article/detail?num=" + article.getNum() + "&listUrl=" + listUrl);
				return "common/redirect";
			}
		}

		if(like == null) {
			model.addAttribute("msg", "좋아요를 누른 뒤 사용 가능합니다.");
			model.addAttribute("replaceUri", "/usr/article/detail?num=" + article.getNum() + "&listUrl=" + listUrl);
			return "common/redirect";
		}
		
		likeService.doDelete(param);
		
		model.addAttribute("msg", "좋아요 취소 되엇습니다.");
		model.addAttribute("replaceUri", "/usr/article/detail?num=" + article.getNum() + "&listUrl=" + listUrl);
		
		return "common/redirect";
	}
	
}