package com.dwict.jfmc.client.mem.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mem.model.Member;

public interface IpinService {

	ModelAndView result(HttpServletRequest request, ModelAndView modelAndView);
	ModelAndView verifyForJoin(HttpServletRequest request, Member member);

}
