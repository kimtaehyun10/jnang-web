package com.dwict.jfmc.client.mem.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.LogintalkService;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.security.service.ErrorService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
public class MemberApiController {

	@Resource(name = "memberService")
	private MemberService memberService;

	/* ########################################################
	 * 혁산 API
	 *#########################################################
	 */
	//회원가입 여부 조회
	@GetMapping(value = "/member/isMember.do")
	public String isMember(HttpServletRequest request) {
		return memberService.isMember(request);
	}
	
	
}
