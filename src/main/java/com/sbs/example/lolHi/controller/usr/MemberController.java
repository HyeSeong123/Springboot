package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sbs.example.lolHi.Service.MemberService;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.ResultData;
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
			model.addAttribute("replaceUri", "/usr/home/main");
			return "common/redirect";
		}

		return "usr/member/join";
	}

	@RequestMapping("/usr/member/doJoin")
	public String doJoin(@RequestParam Map<String, Object> param, Model model, HttpServletRequest req) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		if (loginedMemberNum > 0) {
			model.addAttribute("msg", "로그아웃 후 이용해주세요.");
			model.addAttribute("replaceUri", "/usr/home/main");
			return "common/redirect";
		}

		String loginId = Util.getAsStr(param.get("loginId"), "");
		String email = Util.getAsStr(param.get("email"), "");

		if (loginId.length() == 0) {
			model.addAttribute("msg", "로그인 아이디를 입력해주세요.");
			model.addAttribute("historyBack");
			return "common/redirect";
		}

		int isJoinAvailableLoginIdAndEmail = memberService.isJoinAvailableLoginIdAndEmail(loginId, email);

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
	public String showLogin(HttpServletRequest req, Model model, String listUrl) {
		if (listUrl == null) {
			listUrl = "/usr/home/main";
		}

		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		if (listUrl != null) {
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
	public String doLogin(String loginId, String loginPw, HttpSession session, Model model, HttpServletRequest req,
			String listUrl) {
		if (listUrl == null) {
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
			model.addAttribute("replaceUri", "./login");

			return "common/redirect";
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			model.addAttribute("msg", "아이디와 패스워드가 일치하지 않습니다.");
			model.addAttribute("replaceUri", "./login");

			return "common/redirect";
		}

		String authedEmail = memberService.getAuthedEmail(member.getNum());
		
		if(authedEmail.equals(member.getEmail()) == false){
			model.addAttribute("msg", "아메일 인증 후 시도해주세요");
			model.addAttribute("historyBack", true);

			return "common/redirect";
		}
		
		session.setAttribute("loginedMemberNum", member.getNum());

		model.addAttribute("msg", member.getLoginId() + "님의 로그인을 환영합니다.");
		model.addAttribute("replaceUri", "../.." + listUrl);

		return "common/redirect";
	}

	@RequestMapping("/usr/member/authKey")
	@ResponseBody
	public ResultData showAuthKey(String loginId, String loginPw) {
		
		if(loginId == null) {
			return new ResultData("F-1", "loginId를 입력해주세요");
		}
		
		Member existingMember = memberService.getMemberByLoginId(loginId);
		
		if(existingMember == null) {
			return new ResultData("F-2", "존재하지 않는 아이디 입니다.", "loginId", loginId);
		}
		
		if(loginPw == null) {
			return new ResultData("F-1", "패스워드를 입력해주세요.");
		}
		
		if(existingMember.getLoginPw().equals(loginPw) == false) {
			return new ResultData("F-3", "비밀번호가 일치하지 않습니다.");
		}
		
		return new ResultData("S-1", String.format("%s님 환영합니다,", existingMember.getNickname()), "authKey", existingMember.getAuthKey(), "num", existingMember.getNum(), "name", existingMember.getName(), "nickname", existingMember.getNickname());
	}

	@RequestMapping("/usr/member/memberByAuthKey")
	@ResponseBody
	public ResultData showMemberByAuthKey(String authKey) {
		if (authKey == null) {
			return new ResultData("F-1", "authKey를 입력해주세요.");
		}
		
		Member existingMember = memberService.getMemberByAuthKey(authKey);
		
		return new ResultData("S-1", String.format("유효한 회원입니다."), "member" , existingMember);
		
	}

	
	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId(HttpServletRequest req, Model model) {

		return "usr/member/findLoginId";
	}

	@RequestMapping("/usr/member/doFindLoginId")
	public String DoFindLoginId(Model model, @RequestParam Map<String, Object> param) {

		Member member = memberService.getMemberByNameAndEmail(param);

		if (member == null) {
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
	public String DoFindPassword(Model model, String loginId, String email) {

		int canFind = memberService.canMemberByLoginIdAndEmail(loginId, email);

		System.out.println("loginId= " + loginId);
		System.out.println("email= " + email);

		if (canFind == -1) {
			model.addAttribute("msg", "일치하는 아이디가 없습니다.");
			model.addAttribute("replaceUri", "./findPassword");

			return "common/redirect";
		}

		if (canFind == -2) {
			model.addAttribute("msg", "일치하는 이메일이 없습니다.");
			model.addAttribute("replaceUri", "./findPassword");

			return "common/redirect";
		}

		if (canFind == -3) {
			model.addAttribute("msg", "아이디와 이메일이 일치하지 않습니다.");
			model.addAttribute("replaceUri", "./findPassword");

			return "common/redirect";
		}

		Member member = memberService.getMemberByLoginIdAndEmail(loginId, email);

		System.out.println("member=" + member);

		ResultData setTempPasswordAndNotifyRsData = memberService.setTempPasswordAndNotify(member);

		model.addAttribute("msg", String.format(setTempPasswordAndNotifyRsData.getMsg()));
		model.addAttribute("historyBack", true);

		return "common/redirect";
	}

	@RequestMapping("/usr/member/doLogout")
	public String doLogout(HttpSession session, Model model, String listUrl) {
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
	public String showModify(HttpServletRequest req, Model model, String checkLoginPwAuthCode) {

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberNum, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack" , true);
			model.addAttribute("msg" , checkValidCheckPasswordAuthCodeResultData.getMsg());
			
			return "common/redirect";
		}
		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	public String doModify(HttpSession session, Model model, @RequestParam Map<String, Object> param,
			HttpServletRequest req, String checkLoginPwAuthCode) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		if (checkLoginPwAuthCode == null || checkLoginPwAuthCode.length() == 0) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "비밀번호 체크 인증코드가 없습니다.");
			return "common/redirect";
		}

		ResultData checkValidCheckPasswordAuthCodeResultData = memberService
				.checkValidCheckLoginPwAuthCode(loginedMemberNum, checkLoginPwAuthCode);

		if (checkValidCheckPasswordAuthCodeResultData.isFail()) {
			model.addAttribute("historyBack" , true);
			model.addAttribute("msg" , checkValidCheckPasswordAuthCodeResultData.getMsg());
			
			return "common/redirect";
		}
		
		param.put("num", loginedMemberNum);

		param.remove("loginId");

		memberService.doModify(param);

		model.addAttribute("msg", "수정되었습니다.");
		model.addAttribute("replaceUri", "/usr/home/main");

		return "common/redirect";
	}

	@RequestMapping("/usr/member/passwordCheck")
	public String showpasswordCheck(HttpSession session, Model model) {

		return "usr/member/passwordCheck";
	}

	@RequestMapping("/usr/member/doPasswordCheck")
	public String doPasswordCheck(Model model, HttpServletRequest req, String loginPw, String redirectUri) {
		int loginedMemberNum = (int) req.getAttribute("loginedMemberNum");

		Member member = memberService.getMemberByNum(loginedMemberNum);

		String authCode = memberService.genCheckLoginPwAuthCode(member.getNum());

		if (member.getLoginPw().equals(loginPw) == false) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "패스워드가 일치하지 않습니다.");

			return "common/redirect";
		}

		if (redirectUri == null || redirectUri.length() == 0) {
			redirectUri = "/usr/home/main";
		}
		System.out.println("authCode= " + authCode);
		System.out.println("redirectUri= " + redirectUri);
		redirectUri = Util.getNewUri(redirectUri, "checkLoginPwAuthCode", authCode);

		model.addAttribute("replaceUri", redirectUri);
		
		return "common/redirect";
	}
	
	@RequestMapping("/usr/member/doAuthEmail")
	public String doAuthEmail(int actorNum ,String email, String authCode, Model model) {
		Member member = memberService.getMemberByNum(actorNum);
		
		if(member == null) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "존재하지 않는 회원입니다.");
			
			return "common/redirect";
		}
		
		if(member.getEmail().equals(email) == false) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "일치하지 않는 이메일 입니다.");
			
			return "common/redirect";
		}
		
		String emailAuthCodeOnDB = memberService.getEmailAuthCode(actorNum);
		
		if ( authCode.equals(emailAuthCodeOnDB) == false) {
			model.addAttribute("historyBack", true);
			model.addAttribute("msg", "인증코드가 일치하지 않습니다.");
			
			return "common/redirect";
		}
		
		memberService.saveAuthedEmail(actorNum, email);
		
		model.addAttribute("msg", "이메일 인증에 성공하였습니다.");
		model.addAttribute("replaceUri", "/home/main");
		
		return "common/redirect";
	}
	
}