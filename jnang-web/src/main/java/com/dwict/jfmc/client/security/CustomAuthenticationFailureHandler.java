package com.dwict.jfmc.client.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		final String url = "/mem/login";
		//request.getSession().setAttribute("failCount", "-1");

		if (exception instanceof UsernameNotFoundException) {
			final String errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
			request.getSession().setAttribute("status", "01");
			request.getSession().setAttribute("errorMsg", errorMsg);
		} else if (exception instanceof BadCredentialsException) {
			String errorMsg = null;
			switch (exception.getMessage()) {
			case "01":
				errorMsg = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
				request.getSession().setAttribute("status", "01");
				request.getSession().setAttribute("errorMsg", errorMsg);
				break;
			case "99":
				errorMsg = "비밀번호 변경 대상입니다.";
				request.getSession().setAttribute("status", "99");
				request.getSession().setAttribute("errorMsg", errorMsg);
				break;
			default:
				break;
			}
		} else if (exception instanceof DisabledException) {
			//계정 잠김
			final String message = exception.getMessage();
			if (message.equals("locked")) {
				//url = "/?loginError=03";
			}
		}

		/*
		if (exception.getClass().isAssignableFrom(DisabledException.class)) {
			//계정 잠김
			final String message = exception.getMessage();
			if (message.equals("locked")) {
				url = "/?loginError=03";
			}
		} else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			url += "02";
			final String errorMsg = exception.getMessage();
			//request.getSession().setAttribute("failCount", message);
			request.setAttribute("errorMsg", errorMsg);
		}*/

		setDefaultFailureUrl(url);
		super.onAuthenticationFailure(request, response, exception);
	}
}