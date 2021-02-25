package com.sbs.example.lolHi.controller.usr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.Service.ArticleService;
import com.sbs.example.lolHi.Service.LikeService;
import com.sbs.example.lolHi.Service.ReplyService;
import com.sbs.example.lolHi.controller.dto.Board;
import com.sbs.example.lolHi.dto.Article;
import com.sbs.example.lolHi.dto.Like;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.Reply;
import com.sbs.example.lolHi.util.Util;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private LikeService likeService;
	
	@RequestMapping("/usr/article/main")
	public String showMain() {

		return "usr/article/main";
	}

	@RequestMapping("/usr/article-{boardCode}/list")
	public String showList(Model model, @RequestParam Map<String, Object> param, HttpServletRequest req, String boardCode) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		List<Article> articles = articleService.getArticles(loginedMember, param);
		List<Reply> replies = replyService.getRepliesNum("article", loginedMember);
		
		Board board = articleService.getBoardByCode(boardCode);
		
		System.out.println("boardCode : " + boardCode);
		
		int totalCount = articleService.totalCount(param);
		int itemsCountInAPage = 20;
		int page = Util.getAsInt(param.get("page"), 1);
		int totalPage = (int) Math.ceil(totalCount / (double) itemsCountInAPage);
		int pageMenuArmSize = 10;
		int pageMenuStart = page - pageMenuArmSize;

		if (pageMenuStart < 1) {
			pageMenuStart = 1;
		}
		int pageMenuEnd = page + pageMenuArmSize;

		if (pageMenuEnd > totalPage) {
			pageMenuEnd = totalPage;
		}

		param.put("itemsCountInAPage", itemsCountInAPage);

		model.addAttribute("board", board);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageMenuStart", pageMenuStart);
		model.addAttribute("pageMenuEnd", pageMenuEnd);
		model.addAttribute("articles", articles);
		model.addAttribute("replies", replies);
		
		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int num, String listUrl, HttpServletRequest req) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		boolean availAbleLike = false;
		
		List<Reply> replies = replyService.getReplies(num, "article", loginedMember);
		
		Like like = likeService.getLikeByParam(num, loginedMemberNum);
		
		System.out.println("like= " + like);
		System.out.println("num= " + num);
		System.out.println("loginedMemberNum= " + loginedMemberNum);
		
		if(like == null) {
			availAbleLike = true;
		}
		
		int replyNum = replies.size();
		
		String saveUrl = req.getParameter("listUrl");
		
		saveUrl = Util.getUriEncoded(saveUrl);
		
		Article article = null;

		article = articleService.getArticleByNum(loginedMember, num);

		if (listUrl == null) {
			listUrl = "article/ust/list";
		}

		model.addAttribute("availAbleLike", availAbleLike);
		model.addAttribute("article", article);
		model.addAttribute("replies", replies);
		model.addAttribute("listUrl", listUrl);
		model.addAttribute("saveUrl", saveUrl);

		return "usr/article/detail";
	}

	@RequestMapping("/usr/article/doDelete")
	public String doDelete(int num, HttpServletRequest req, Model model, String listUrl) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		Article article = articleService.getArticleByNum(loginedMember, loginedMemberNum);

		if ((boolean) article.getExtra().get("actorCanDelete") == false) {
			model.addAttribute("msg", "삭제 권한이 없습니다.");
			model.addAttribute("replaceUri", "/usr/article/detail?num=" + num + "&listUrl=" + listUrl);
			return "common/redirect";
		}

		articleService.doDeleteArticleByNum(num);

		model.addAttribute("msg", num + "번 글을 삭제하였습니다.");
		model.addAttribute("replaceUri", listUrl);

		return "common/redirect";
	}

	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int num, HttpServletRequest req, String listUrl) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");

		Article article = articleService.getArticleByNum(loginedMember, num);

		model.addAttribute("article", article);

		listUrl = Util.getUriEncoded(listUrl);

		model.addAttribute("listUrl", listUrl);
		
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		if ((boolean) article.getExtra().get("actorCanModify") == false) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("replaceUri", "/usr/article/detail?num=" + num + "&listUrl=" + listUrl);
			return "common/redirect";
		}
		
		return String.format("usr/article/modify");
	}

	@RequestMapping("/usr/article/doModify")
	public String doModify(int num, String title, String body, HttpServletRequest req, Model model, String listUrl) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		
		Article article = articleService.getArticleByNum(loginedMember, num);

		if ((boolean) article.getExtra().get("actorCanModify") == false) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("replaceUri", "/usr/article/detail?num=" + num + "&listUrl=" + listUrl);
			return "common/redirect";
		}
		
       //	listUrl = Util.getUriEncoded(listUrl);
		
		articleService.doModifyArticleByNum(num, title, body);

		model.addAttribute("msg", num + "번 글을 수정하였습니다");
		model.addAttribute("replaceUri", "/usr/article/detail?num=" + num + "&listUrl=" + listUrl);

		return "common/redirect";
	}

	@RequestMapping("/usr/article/write")
	public String showWrite(HttpServletRequest req, Model model) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		return String.format("usr/article/write");
	}

	@RequestMapping("usr/article/doWrite")
	public String doWrite(@RequestParam Map<String, Object> param, Model model, HttpServletRequest req) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		param.put("memberNum", loginedMemberNum);
		int num = articleService.doWriteArticleByNum(param);

		model.addAttribute("msg", num + "번 글을 작성하였습니다");
		model.addAttribute("replaceUri", "/usr/article/list");

		return "common/redirect";
	}
	
}