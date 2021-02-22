package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.Service.replyService;

@Controller
public class replyController {
	@Autowired
	private replyService replyService;

	@RequestMapping("/usr/reply/doWrite")
	public String doWrite(Model model, @RequestParam Map<String, Object> param) {
		
		int replyNum = replyService.doWrite(param);
		
		return "usr/article/detail";
	}
}