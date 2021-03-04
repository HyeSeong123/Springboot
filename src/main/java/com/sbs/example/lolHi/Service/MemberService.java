package com.sbs.example.lolHi.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

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

	@Value("${custom.siteUri}")
	private String siteUrl;
	
	@Value("${custom.siteLoginUri}")
	private String siteLoginUri;

	@Autowired
	private MailService mailService;

	@Autowired
	MemberDao memberDao;

	@Autowired
	AttrService attrService;

	public int join(Map<String, Object> param) {
		memberDao.join(param);

		int num = Util.getAsInt(param.get("num"), 0);

		String email = Util.getAsStr(param.get("email"), null);

		String authCode = genEmailAuthCode(num);
		
		sendJoinCompleteMail(num, email, authCode);

		return num;
	}

	private String genEmailAuthCode(int actorNum) {
		
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + actorNum + "__extra__emailAuthCode", authCode);

		return authCode;
	}

	private void sendJoinCompleteMail(int actorNum, String email, String authCode) {
		String mailTitle = String.format("[%s] 가입이 완료되었습니다. 이메일 인증을 진행해주세요", siteName);

		StringBuilder mailBodySb = new StringBuilder();
		String doAuthMainUrl = siteUrl + "/usr/member/doAuthEmail?authCode=" + authCode + "&email=" + email + "&actorNum=" + actorNum;
		
		mailBodySb.append("<h1>가입이 완료되었습니다.<h1>");
		mailBodySb.append("<h1>아래 인증코드를 클릭하여 이메일 인증을 해주세요.<h1>");
		mailBodySb.append(String.format("<p><a href=\"%s\" target=\"_blank\">인증하기</a></p>", doAuthMainUrl));

		mailService.send(email, mailTitle, mailBodySb.toString());
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
		return memberDao.getMemberByLoginIdAndEmail(loginId, email);
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

	public String genCheckLoginPwAuthCode(int actorNum) {
		String authCode = UUID.randomUUID().toString();
		attrService.setValue("member__" + actorNum + "__extra__modifyPrivateAuthCode", authCode,
				Util.getDateStrLater(60 * 60));

		return authCode;
	}

	public ResultData checkValidCheckLoginPwAuthCode(int actorNum, String checkLoginPwAuthCode) {
		System.out.println("checkLoginPwAuthCode= " + checkLoginPwAuthCode);
		if (attrService.getValue("member__" + actorNum + "__extra__modifyPrivateAuthCode")
				.equals(checkLoginPwAuthCode)) {
			return new ResultData("S-1", "유효한 키 입니다.");
		}

		return new ResultData("F-1", "유효하지 않은 키 입니다.");
	}

	public String getEmailAuthCode(int actorNum) {
		return attrService.getValue("member__" + actorNum + "__extra__emailAuthCode");
		
	}

	public void saveAuthedEmail(int actorNum, String email) {
		attrService.setValue("member__" + actorNum + "__extra__authedEmail", email);
	}

	public String getAuthedEmail(int actorNum) {
		return attrService.getValue("member__" + actorNum + "__extra__authedEmail");
	}
}
