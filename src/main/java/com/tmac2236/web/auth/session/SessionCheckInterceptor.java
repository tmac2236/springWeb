package com.tmac2236.web.auth.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tmac2236.web.auth.entity.User;

public class SessionCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("User");

		if (user != null) {
			return true;
		} else {
		    //導回首頁
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return false;
		}
	}

}
