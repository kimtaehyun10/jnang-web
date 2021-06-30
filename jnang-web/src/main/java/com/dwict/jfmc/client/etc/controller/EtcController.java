package com.dwict.jfmc.client.etc.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.etc.service.EtcService;
import com.dwict.jfmc.client.mem.service.MemberService;

@RestController
public class EtcController {
	
	@Resource(name = "etcService")
	private EtcService etcService;
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@GetMapping(value = "/etc/sitemap")
	public ModelAndView sitemap(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/sitemap");
		return modelAndView;
	}

	@GetMapping(value = "/robots.txt")
	@ResponseBody
	public String robots() {
		return "User-agent: *\nDisallow: /\n";
	}
	
	
	@GetMapping(value = "/etc/guide1")
	public ModelAndView guide1(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/guide1");
		return modelAndView;
	}

	@GetMapping(value = "/etc/guide2")
	public ModelAndView guide2(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/guide2");
		return modelAndView;
	}

	@GetMapping(value = "/etc/guide3")
	public ModelAndView guide3(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/guide3");
		return modelAndView;
	}

	@GetMapping(value = "/etc/guide4")
	public ModelAndView guide4(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/guide4");
		return modelAndView;
	}
	
	@GetMapping(value = "/etc/guide5")
	public ModelAndView guide5(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/guide5");
		return modelAndView;
	}
	
	@GetMapping(value = "/etc/tmq")
	public ModelAndView tmq(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/tmq");
		return modelAndView;
	}
	
	@GetMapping(value = "/sso/scc")
	public ModelAndView scc(ModelAndView modelAndView, @RequestParam Map<String, Object> requestMap, HttpServletRequest request) {
		Map<String, Object> resultMap = etcService.scc(requestMap, request);
		modelAndView.addObject("msg",resultMap.get("msg"));
		modelAndView.addObject("location",resultMap.get("location"));
		modelAndView.setViewName("etc/scc.none");
		return modelAndView;
	}
	
	

	/* 
	 * 혁산 API ########################################################
	 *
	 */
	//혁산 api 회원가입 여부 조회
	@GetMapping(value = "/member/isMember.do")
	public ModelAndView isMember(HttpServletRequest request, ModelAndView modelAndView) {
		String rtnMSG = memberService.apiIsMember(request);
		modelAndView.addObject("rtnMSG",rtnMSG);
		modelAndView.setViewName("etc/sccAPI.none");
		return modelAndView;
	}
	
	//혁산 api 회원가입
	@RequestMapping(value = "/member/joinMember")
	public ModelAndView joinMember(@RequestParam Map<String, Object> requestMap,HttpServletRequest request, ModelAndView modelAndView) {
		String rtnMSG = memberService.apiJoinMember(requestMap,request);
		modelAndView.addObject("rtnMSG",rtnMSG);
		modelAndView.setViewName("etc/sccAPI.none");
		return modelAndView;
	}
	
	@GetMapping(value = "/member/etcTest.do")
	public ModelAndView etcTest(@RequestParam Map<String, Object> requestMap,HttpServletRequest request, ModelAndView modelAndView) {
		modelAndView.setViewName("etc/apiTest.none");
		return modelAndView;
	}
	
	//혁산 api 회원아이디 중복 조회
	@GetMapping(value = "/member/checkId.do")
	public ModelAndView checkId(HttpServletRequest request, ModelAndView modelAndView) {
		String rtnMSG = memberService.apiCheckId(request);
		modelAndView.addObject("rtnMSG",rtnMSG);
		modelAndView.setViewName("etc/sccAPI.none");
		return modelAndView;
	}
	
	
	//혁산 api 정회원 전환(카드발급)
	/*
	@GetMapping(value = "/member/getMembership.do")
	public ModelAndView getMembership(HttpServletRequest request, ModelAndView modelAndView) {
		String rtnMSG = memberService.getMembership(request);
		modelAndView.addObject("rtnMSG",rtnMSG);
		modelAndView.setViewName("etc/sccAPI.none");
		return modelAndView;
	}
	*/	
	
	
	/* 
	 * 정수클로벌 키오스크 제공 API ########################################################
	 *
	 */
	//일일권 종류/가격 정보를 제공하는 API를 요청합니다
	@GetMapping(value = "/kiosk/dayItemList.do")
	public ModelAndView kioskDayItemList(HttpServletRequest request, ModelAndView modelAndView) {
		String rtnMSG = etcService.apiKioskDayItemList(request);
		modelAndView.addObject("rtnMSG",rtnMSG);
		modelAndView.setViewName("etc/sccAPI.none");
		return modelAndView;
	}
	
	//일일권 매출 발생시 귀사의 DB에 등록할 수 있도록 매출 정보를 전송할 API가 필요합니
	@GetMapping(value = "/kiosk/dayInsert.do")
	public ModelAndView kioskDayInsert(Map<String, Object> requestMap, HttpServletRequest request, ModelAndView modelAndView) {
		String rtnMSG = etcService.kioskDayInsert(requestMap, request);
		modelAndView.addObject("rtnMSG",rtnMSG);
		modelAndView.setViewName("etc/sccAPI.none");
		return modelAndView;
	}
	
	
	
	
}
