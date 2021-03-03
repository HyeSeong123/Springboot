package com.sbs.example.lolHi.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.MemberDao;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.dto.ResultData;
import com.sbs.example.lolHi.util.Util;


@Service
public class MemberService {
	@Value("${custom.siteName}")
	private String siteName;
	
	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	
	@Value("${custom.siteLoginUri}")
	private String siteLoginUri;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	MemberDao memberDao;

	public int join(Map<String, Object> param) {
		memberDao.join(param);

		int num = Util.getAsInt(param.get("num"), 0);

		String email = Util.getAsStr(param.get("email"), null);
		
		sendJoinCompleteMail(email);
		
		return num;
	}
	private void sendJoinCompleteMail(String email) {
		String mailTitle = String.format("[%s] 가입이 완료되었습니다.", siteName);
		
		StringBuilder mailBodySb = new StringBuilder();
		mailBodySb.append("<h1>가입이 완료되었습니다.<h1>");
		mailBodySb.append(String.format("<p><a href=\"%s\" target=\"_blank\">%s</a>로 이동</p>", siteMainUri, siteName));
		
		mailService.send(email,mailTitle,mailBodySb.toString());
	}
	
	public int isJoinAvailableLoginIdAndEmail(String loginId, String email) {
		Member loginMember = getMemberByLoginId(loginId);

		Member emailMember = getMemberByEmail(email);
		
		if (loginMember != null) {
			return -1;
		}

		if (emailMember != null) {
			return -2;
		}
		
		return 1;
	}

	public int canMemberByLoginIdAndEmail(String loginId, String email) {
		Member loginMember = getMemberByLoginId(loginId);

		Member emailMember = getMemberByEmail(email);
		
		if (loginMember == null) {
			return -1;
		}

		if (emailMember == null) {
			return -2;
		}
		
		if (loginMember.getEmail().equals(emailMember.getEmail()) == false) {
			return -3;
		}
		
		return 1;
	}
	
	private Member getMemberByEmail(String email) {
		return memberDao.getMemberByEmail(email);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberByNum(int loginedMemberNum) {
		return memberDao.getMemberByNum(loginedMemberNum);
	}

	public int doModify(Map<String, Object> param) {
		return memberDao.doModify(param);
	}

	public Member getMemberByNameAndEmail(Map<String, Object> param) {
		return memberDao.getMemberByNameAndEmail(param);
	}

	public Member getMemberByLoginIdAndEmail(String loginId, String email) {
		return memberDao.getMemberByLoginIdAndEmail(loginId,email);
	}

	public ResultData setTempPasswordAndNotify(Member member) {
		Random r = new Random();
		String tempLoginPw = (10000 + r.nextInt(90000)) + ""; 
		
		String mailTitle = String.format("[%s] 임시 패스워드가 발송 되었습니다.", siteName);
		String mailBody = "";
		
		mailBody += String.format("로그인 아이디 : %s<br>", member.getLoginId());
		mailBody += String.format("임시 비밀번호 : %s", tempLoginPw);
		mailBody += "<br>";
		mailBody += String.format("<a href=\"%s\" target=\"_blank\">로그인 하러가기</a>", siteLoginUri);
		
		ResultData sendResultData = mailService.send(member.getEmail(), mailTitle, mailBody);
		
		if (sendResultData.isFail()) {
			return new ResultData("F-1", "메일발송에 실패했습니다.");
		}
		
		Map<String, Object> modifyParam = new HashMap<>();
		
		modifyParam.put("loginPw", Util.sha256(tempLoginPw));
		modifyParam.put("loginId", member.getLoginId());
		modifyParam.put("num", member.getNum());
		
		memberDao.doModify(modifyParam);
		
		return new ResultData("S-1", "임시 패스워드를 메일로 발송하였습니다.");
	}

}
