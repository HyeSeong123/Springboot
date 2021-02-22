package com.sbs.example.lolHi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sbs.example.lolHi.controller.usr.MemberService;
import com.sbs.example.lolHi.dto.Member;

@Component("beforeActionInterceptor")
public class BeforeActionInterceptor implements HandlerInterceptor {
	@Autowired
	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();

		boolean isAjax = false;
		boolean isLogined = false;
		int loginedMemberNum = 0;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberNum") != null) {
			loginedMemberNum = (int) session.getAttribute("loginedMemberNum");

			if (loginedMemberNum > 0) {
				isLogined = true;
			}
			loginedMember = memberService.getMemberByNum(loginedMemberNum);
		}

		request.setAttribute("isAjax", isAjax);
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberNum", loginedMemberNum);
		request.setAttribute("loginedMember", loginedMember);

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
