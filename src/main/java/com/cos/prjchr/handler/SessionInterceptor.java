package com.cos.prjchr.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.cos.prjchr.domain.user.User;

public class SessionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle 실행됨");

		HttpSession session = request.getSession();

		User principal = (User) session.getAttribute("principal");
		if(principal == null) {
			response.sendRedirect("/loginForm");
		}

		return true;
	}
}
