package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String showJoin(Model model, HttpSession session) {
		int loginedMemberNum = 0;

		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int) session.getAttribute("loginedMemberNum");
		}

		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/article/list");
			return "common/redirect";
		}

		return "usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	public String doJoin(@RequestParam Map<String, Object> param, Model model, HttpSession session) {
		int loginedMemberNum = 0;

		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int) session.getAttribute("loginedMemberNum");
		}

		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/article/list");
			return "common/redirect";
		}

		String loginId = Util.getAsStr(param.get("loginId"), "");

		if (loginId.length() == 0) {
			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack");
			return "common/redirect";
		}

		boolean isJoinAvailableLoginId = memberService.isJoinAvailableLoginId(loginId);

		if (isJoinAvailableLoginId == false) {
			model.addAttribute("msg", loginId + "(은)는 이미 사용중인 아이디 입니다.");
			model.addAttribute("historyBack");
			return "common/redirect";
		}

		int num = memberService.join(param);

		model.addAttribute("msg", loginId + "님의 회원가입을 환영합니다");
		model.addAttribute("replaceUri", "/usr/article/list");

		return "common/redirect";
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession session, Model model) {
		int loginedMemberNum = 0;

		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int) session.getAttribute("loginedMemberNum");
		}

		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/article/list");
			return "common/redirect";
		}

		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	public String doLogin(String loginId, String loginPw, HttpSession session, Model model) {
		int loginedMemberNum = 0;

		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int) session.getAttribute("loginedMemberNum");
		}

		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/article/list");
			return "common/redirect";
		}
		
		if (loginId.length() == 0) {
			model.addAttribute("msg", "로그인 아이디를 입력해주세요");
			model.addAttribute("historyBack");

			return "common/redirect";
		}

		if (loginPw.length() == 0) {
			model.addAttribute("msg", "로그인 패스워드를 입력해주세요");
			model.addAttribute("historyBack");

			return "common/redirect";
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			model.addAttribute("msg", "존재하지 않는 아이디 입니다.");
			model.addAttribute("replaceUri", "/usr/member/login");

			return "common/redirect";
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			model.addAttribute("msg", "아이디와 패스워드가 일치하지 않습니다.");
			model.addAttribute("replaceUri", "/usr/member/login");

			return "common/redirect";
		}

		session.setAttribute("loginedMemberNum", member.getNum());

		model.addAttribute("msg", member.getLoginId() + "님의 로그인을 환영합니다.");
		model.addAttribute("replaceUri", "/usr/article/list");

		return "common/redirect";
	}

	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession session, Model model) {
		session.setAttribute("loginedMemberNum", 0);

		model.addAttribute("msg", "로그아웃 되었습니다.");
		model.addAttribute("replaceUri", "/usr/article/list");

		return "common/redirect";
	}
}