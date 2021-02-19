package com.sbs.example.lolHi.controller.usr;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.example.lolHi.dao.MemberDao;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.util.Util;

@Service
public class MemberService {
	@Autowired
	MemberDao memberDao;

	public int join(Map<String, Object> param) {
		memberDao.join(param);

		int num = Util.getAsInt(param.get("num"), 0);

		return num;
	}

	public boolean isJoinAvailableLoginId(String loginId) {
		Member member = getMemberByLoginId(loginId);

		if (member == null) {
			return true;
		}

		return false;
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

}
