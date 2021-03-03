package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sbs.example.lolHi.Service.MemberService;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.util.Util;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/usr/member/join")
	public String showJoin(Model model, HttpServletRequest req) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/article/main");
			return "common/redirect";
		}

		return "usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	public String doJoin(@RequestParam Map<String, Object> param, Model model, HttpServletRequest req) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/article/main");
			return "common/redirect";
		}

		String loginId = Util.getAsStr(param.get("loginId"), "");
		String email = Util.getAsStr(param.get("email"), "");

		if (loginId.length() == 0) {
			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack");
			return "common/redirect";
		}

		int isJoinAvailableLoginIdAndEmail = memberService.isJoinAvailableLoginIdAndEmail(loginId,email);

		if (isJoinAvailableLoginIdAndEmail == -1) {
			model.addAttribute("msg", loginId + "(은)는 이미 사용중인 아이디 입니다.");
			model.addAttribute("replaceUri", "/usr/member/join");
			
			return "common/redirect";
		}
		
		if (isJoinAvailableLoginIdAndEmail == -2) {
			model.addAttribute("msg", email + "(은)는 이미 사용중인 이메일 입니다.");
			model.addAttribute("replaceUri", "/usr/member/join");
			
			return "common/redirect";
		}
		
		int num = memberService.join(param);

		model.addAttribute("msg", loginId + "님의 회원가입을 환영합니다");
		model.addAttribute("replaceUri", "/usr/home/main");

		return "common/redirect";
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpServletRequest req, Model model,String listUrl) {
		if(listUrl == null) {
			listUrl = "/usr/home/main";
		}
		
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		
		if(listUrl != null) {
			String encodedListUrl = Util.getUriEncoded(listUrl);
		}
		
		req.setAttribute("encodedPreUrl", listUrl);
		
		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "../.." + listUrl);
			return "common/redirect";
		}

		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	public String doLogin(String loginId, String loginPw, HttpSession session , Model model, HttpServletRequest req, String listUrl) {
		if(listUrl == null) {
			listUrl = "/home/main";
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
			model.addAttribute("replaceUri", "./login" + listUrl);

			return "common/redirect";
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			model.addAttribute("msg", "아이디와 패스워드가 일치하지 않습니다.");
			model.addAttribute("replaceUri", "./login" + listUrl);

			return "common/redirect";
		}

		session.setAttribute("loginedMemberNum", member.getNum());
		
		model.addAttribute("msg", member.getLoginId() + "님의 로그인을 환영합니다.");
		model.addAttribute("replaceUri", "../.." + listUrl);

		return "common/redirect";
	}
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId(HttpServletRequest req, Model model) {
		
		return "usr/member/findLoginId";
	}
	@RequestMapping("/usr/member/doFindLoginId")
	public String DoFindLoginId(Model model,@RequestParam Map<String,Object> param) {
		
		Member member = memberService.getMemberByNameAndEmail(param);

		if ( member == null) {
			model.addAttribute("msg", "일치하는 아이디가 없습니다.");
			model.addAttribute("replaceUri", "./findLoginId");
			
			return "common/redirect";
		}
		
		model.addAttribute("msg", "회원님의 아이디는" + member.getLoginId() + "입니다.");
		model.addAttribute("replaceUri", "./login");
		
		return "common/redirect";
	}	
	@RequestMapping("/usr/member/findPassword")
	public String showFindPassword(HttpServletRequest req, Model model) {
		
		return "usr/member/findPassword";
	}
	@RequestMapping("/usr/member/doFindPassword")
	public String DoFindPassword(Model model,String loginId, String email) {
		
		int canFind = memberService.canMemberByLoginIdAndEmail(loginId,email);

		System.out.println("loginId= " + loginId);
		System.out.println("email= " + email);
		
		if ( canFind == -1) {
			model.addAttribute("msg", "일치하는 아이디가 없습니다.");
			model.addAttribute("replaceUri", "./findPassword");
			
			return "common/redirect";
		}
		
		if ( canFind == -2) {
			model.addAttribute("msg", "일치하는 이메일이 없습니다.");
			model.addAttribute("replaceUri", "./findPassword");
			
			return "common/redirect";
		}
		
		if ( canFind == -3) {
			model.addAttribute("msg", "아이디와 이메일이 일치하지 않습니다.");
			model.addAttribute("replaceUri", "./findPassword");
			
			return "common/redirect";
		}
		
		Member member = memberService.getMemberByLoginIdAndEmail(loginId,email);
		
		memberService.setTempPasswordAndNotify(member);
		
		model.addAttribute("msg", "임시비밀번호를 이메일로 발송 하였습니다." + member.getLoginPw() + "입니다.");
		model.addAttribute("replaceUri", "./login");
		
		return "common/redirect";
	}
	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession session, Model model,String listUrl) {
		session.setAttribute("loginedMemberNum", 0);

		model.addAttribute("msg", "로그아웃 되었습니다.");
		model.addAttribute("replaceUri", listUrl);

		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/showInforMe")
	public String showInforMe(HttpSession session, Model model) {
		
		return "usr/member/showInforMe";
	}
	
	@RequestMapping("/usr/member/modify")
	public String showModify(HttpSession session, Model model) {
		
		return "usr/member/modify";
	}
	
	@RequestMapping("/usr/member/doModify")
	public String doModify(HttpSession session, Model model, @RequestParam Map<String, Object> param, HttpServletRequest req) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");
		
		param.put("num", loginedMemberNum);
		
		param.remove("loginId");
		
		memberService.doModify(param);
		
		model.addAttribute("msg", "수정되었습니다.");
		model.addAttribute("replaceUri", "/usr/home/main");

		return "common/redirect";
	}
}