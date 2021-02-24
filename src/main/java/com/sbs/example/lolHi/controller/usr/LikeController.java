package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.Service.LikeService;
import com.sbs.example.lolHi.Service.ReplyService;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.Reply;

@Controller
public class LikeController {
	@Autowired
	private LikeService likeService;

	@RequestMapping("/usr/like/doLike")
	public String doLike(Model model, @RequestParam Map<String, Object> param, int num, String replaceUrl) {
		
		likeService.doLike(param);
		System.out.println("replaceUrl=" + replaceUrl);
		if(replaceUrl == null || replaceUrl.length() == 0) {
			replaceUrl = String.format("/usr/article/detail?num=%d", num);
		}
		replaceUrl = String.format("/usr/article/detail?num=%d", num);
		model.addAttribute("msg", "좋아요 처리 되엇습니다.");
		model.addAttribute("replaceUri", replaceUrl);
		
		return "common/redirect";
	}
}