package com.sbs.example.lolHi.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sbs.example.lolHi.Service.ArticleService;
import com.sbs.example.lolHi.Service.MemberService;
import com.sbs.example.lolHi.dto.Board;
import com.sbs.example.lolHi.dto.Member;
import com.sbs.example.lolHi.util.Util;

@Component("beforeActionInterceptor")
public class BeforeActionInterceptor implements HandlerInterceptor {
	@Autowired
	private MemberService memberService;

	@Autowired
	private ArticleService articleService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		int loginedMemberNum = 0;
		Member loginedMember = null;
		
		String authKey = request.getParameter("authKey");
		if(authKey != null && authKey.length() > 0) {
			loginedMember = memberService.getMemberByAuthKey(authKey);
			
			if(loginedMember == null) {
				request.setAttribute("authKeyStatus", "invalid");
			}
			else {
				request.setAttribute("authKeyStatus", "valid");
				loginedMemberNum = loginedMember.getNum();
			}
		}
		else {
			HttpSession session = request.getSession();
			request.setAttribute("authKeyStatus", "none");
			
			if(session.getAttribute("loginedMemberNum") != null) {
				loginedMemberNum = (int)session.getAttribute("loginedMemberNum");
				loginedMember = memberService.getMemberByNum(loginedMemberNum);
			}
		}
		
		boolean isAjax = false;
		boolean isLogined = false;

		if(loginedMember != null) {
			if (loginedMember != null) {
				isLogined = true;
				loginedMember = memberService.getMemberByNum(loginedMemberNum);
			}
			HttpSession session = request.getSession();
			
			if (session.getAttribute("listUrl") != null) {
				String listUrl = request.getParameter("listUrl");
	
				request.setAttribute("listUrl", listUrl);
			}
		}
		
		List<Board> boards = articleService.getBoards();
		
		request.setAttribute("isAjax", isAjax);
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberNum", loginedMemberNum);
		request.setAttribute("loginedMember", loginedMember);
		request.setAttribute("boards", boards);
		
		String currentUri = request.getRequestURI();

		if (request.getQueryString() != null) {
			currentUri += "?" + request.getQueryString();
		}

		String encodedCurrentUri = Util.getUriEncoded(currentUri);

		request.setAttribute("currentUri", currentUri);
		request.setAttribute("encodedCurrentUri", encodedCurrentUri);

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
}
