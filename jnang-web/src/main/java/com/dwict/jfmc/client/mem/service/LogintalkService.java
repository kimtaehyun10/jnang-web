package com.dwict.jfmc.client.mem.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

public interface LogintalkService {

	Object sendToken(String token);
	ModelAndView login(HttpServletRequest request, JsonObject response);
	ModelAndView verifyForJoin(HttpServletRequest request, JsonObject response);
	ModelAndView verifyForLogin(HttpServletRequest request, JsonObject response);
	ModelAndView verifyForChangePwd(HttpServletRequest request, Object response);

}
