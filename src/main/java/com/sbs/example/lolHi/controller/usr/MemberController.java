package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.util.Util;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(@RequestParam Map<String, Object> param) {
		String loginId = Util.getAsStr(param.get("loginId"), "");

		if(loginId.length() == 0) {
			return String.format("<script> alert('로그인 아이디를 입력해주세요'); history.back(); </script>");
		}
		
		boolean isJoinAvailableLoginId = memberService.isJoinAvailableLoginId(loginId);
		
		if(isJoinAvailableLoginId == false) {
			return String.format("<script> alert('%s(은)는 이미 사용중인 아이디 입니다.'); history.back(); </script>", loginId); 
		}
		
		int num = memberService.join(param);

		return String.format("<script> alert('%d번 님의 회원가입을 환영합니다'); location.replace('/usr/article/list'); </script>",
				num);
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(@RequestParam Map<String, Object> param) {
		String loginId = Util.getAsStr(param.get("loginId"), "");
		String loginPw = Util.getAsStr(param.get("loginPw"), "");
		
		if(loginId.length() == 0) {
			return String.format("<script> alert('로그인 아이디를 입력해주세요'); history.back(); </script>");
		}
		
		if(loginPw.length() == 0) {
			return String.format("<script> alert('로그인 패스워드를 입력해주세요'); history.back(); </script>");
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			return String.format("<script> alert('존재하지 않는 아이디 입니다.'); history.back(); </script>");
		}
		
		if(member.getLoginPw().equals(loginPw) == false) {
			return String.format("<script> alert('아이디와 패스워드가 일치하지 않습니다.'); history.back(); </script>");
		}
		return String.format("<script> alert('%s 님의 로그인을 환영합니다'); location.replace('/usr/article/list'); </script>",
				loginId);
	}
}