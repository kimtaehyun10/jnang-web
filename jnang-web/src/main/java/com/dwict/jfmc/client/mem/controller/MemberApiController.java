package com.dwict.jfmc.client.mem.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.mem.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberApiController {

	@Resource(name = "memberService")
	private MemberService memberService;

	/* ########################################################
	 * 혁산 API
	 *#########################################################
	 */
	//혁산 api 회원가입 여부 조회
	@GetMapping(value = "/isMember.do")
	public String isMember(HttpServletRequest request) {
		return memberService.apiIsMember(request);
	}
	
	//혁산 api 회원가입
	@PostMapping(value = "/joinMember.do")
	public String joinMember(@RequestParam Map<String, Object> requestMap, HttpServletRequest request) {
		return memberService.apiJoinMember(requestMap,request);
	}
	
	//혁산 api 회원아이디 중복 조회
	@GetMapping(value = "/checkId.do")
	public String checkId(HttpServletRequest request) {
		return memberService.apiCheckId(request);
	}
	
	//혁산 api 정회원 전환(카드발급)
	@GetMapping(value = "/getMembership.do")
	public String getMembership(HttpServletRequest request) {
		return memberService.apiMembership(request);
	}		
}
