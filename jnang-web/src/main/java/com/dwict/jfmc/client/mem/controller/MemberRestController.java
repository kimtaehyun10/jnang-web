package com.dwict.jfmc.client.mem.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.LogintalkService;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.security.service.ErrorService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/data")
public class MemberRestController {

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "logintalkService")
	private LogintalkService logintalk;

	@Resource(name = "errorService")
	private ErrorService errorService;

	@GetMapping(value = "/mem/join/logintalk/token")
	public ModelAndView getUserInfoByLogintalkTokenForJoin(HttpServletRequest request, ModelAndView modelAndView, @RequestParam String token) {
		final Object response = logintalk.sendToken(token);
		final JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
		modelAndView = logintalk.verifyForJoin(request, jsonObject);
		return modelAndView;
	}

	@GetMapping(value = "/mem/login/logintalk/token")
	public ModelAndView getUserInfoByLogintalkTokenForLogin(HttpServletRequest request, ModelAndView modelAndView, @RequestParam String token) {
		final Object response = logintalk.sendToken(token);
		final JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
		switch (jsonObject.get("service").getAsString()) {
		case "11":
			modelAndView = logintalk.login(request, jsonObject);
			break;
		case "13":
			modelAndView = logintalk.verifyForLogin(request, jsonObject);
			break;
		}
		return modelAndView;
	}

	@GetMapping(value = "/mem/join/id/isDuplicate")
	public boolean isDuplicate(Member member) {
		return memberService.isDuplicatedId(member);
	}

	@PostMapping(value = "/mem/join")
	public int insert(HttpServletRequest request, Member member) {
		final HttpSession session = request.getSession(false);
		int result = 0;
		if(session != null && session.getAttribute("join.adult") != null 
				&& session.getAttribute("join.exist") != null && session.getAttribute("join.member") != null) {
			final boolean adultYn = (boolean) session.getAttribute("join.adult");
			final boolean isExist = (boolean) session.getAttribute("join.exist");
			if(adultYn == true) {
				final Member sessionMember = (Member) session.getAttribute("join.member");
				member.setMemNm(sessionMember.getMemNm());
				member.setSecBirthDate(sessionMember.getSecBirthDate());
				member.setGender(sessionMember.getGender());
				member.setDpstrNm(request.getParameter("dpstrNm"));
				member.setBankNm(request.getParameter("bankNm"));
				member.setAcountNum(request.getParameter("acountNum"));
				if(sessionMember.getHp() != null) member.setHp(sessionMember.getHp()); //ipin 인증은 hp를 리턴하지 않음
				if(isExist) {
					result = memberService.update(member);
				} else {
					result = memberService.insert(member);
				}
			} else {
				result = memberService.insertForLessThan14(member);
			}
		} else {
			throw new RuntimeException();
		}
		return result;
	}

	@PostMapping(value = "/mem/password")
	public int password(HttpServletRequest request, Member member) {
		final HttpSession session = request.getSession(false);
		int result = 0;
		if(session != null && session.getAttribute("find.exist") != null) {
			final Member sessionMember = (Member) session.getAttribute("member");
			member.setMemNm(sessionMember.getMemNm());
			member.setSecBirthDate(sessionMember.getSecBirthDate());
			member.setGender(sessionMember.getGender());
			member.setHp(sessionMember.getHp());
			result = memberService.password(member);
		} else {
			throw new RuntimeException();
		}
		return result;
	}

	@PostMapping(value = "/mem/ipin/result")
	public RedirectView ipinResult(HttpServletRequest request, @RequestParam String enc_data) {
		final HttpSession session = request.getSession(false);
		session.setAttribute("enc_data", enc_data);
		return new RedirectView("/mem/ipinResult");
	}

}
