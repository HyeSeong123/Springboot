package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.Service.ReplyService;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.Reply;

@Controller
public class replyController {
	@Autowired
	private ReplyService replyService;

	@RequestMapping("/usr/reply/doWrite")
	public String doWrite(Model model, @RequestParam Map<String, Object> param, int num, String replaceUrl) {
		int replyNum = replyService.doWrite(param);
		
		if(replaceUrl == null || replaceUrl.length() == 0) {
			replaceUrl = String.format("/usr/article/detail?num=%d", num);
		}
		
		model.addAttribute("msg", "댓글이 작성되었습니다.");
		model.addAttribute("replaceUri", replaceUrl);
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/reply/doDelete")
	public String doDelete(Model model, int num, HttpServletRequest req, String replaceUrl) {
		Member loginedMember = (Member) req.getAttribute("loginedMember");
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		
		Reply reply = replyService.getArticleForNum(loginedMember, num);
		
		if(replaceUrl == null || replaceUrl.length() == 0) {
			replaceUrl = String.format("/usr/article/detail?num=" + reply.getRelNum());
		}
		String listUrl = req.getParameter("replaceUrl");
		
		if (reply.getMemberNum() != loginedMemberNum) {
			model.addAttribute("msg", "삭제 권한이 없습니다.");
			model.addAttribute("replaceUri", "/usr/article/detail?num=" + reply.getRelNum() + "&" + listUrl);
			return "common/redirect";
		}
		
		replyService.doDelete(num);
		
		model.addAttribute("msg", "댓글이 삭제되었습니다.");
		model.addAttribute("replaceUri", replaceUrl);
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/reply/doModify")
	public String doModify(Model model, @RequestParam Map<String, Object> param, int replyNum, int num, HttpServletRequest req,String replaceUrl) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		
		Reply reply = replyService.getArticleForNum(replyNum);
		
		if(replaceUrl == null || replaceUrl.length() == 0) {
			replaceUrl = String.format("/usr/article/detail?num=" + reply.getRelNum());
		}
		String listUrl = req.getParameter("replaceUrl");
				
		if (reply.getMemberNum() != loginedMemberNum) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("replaceUri", "/usr/article/detail?num=" + reply.getRelNum() + "&" + listUrl);
			return "common/redirect";
		}
		
		replyService.doModify(param);
		
		model.addAttribute("msg", "댓글이 수정되었습니다.");
		model.addAttribute("replaceUri", replaceUrl);
		
		return "common/redirect";
	}
}