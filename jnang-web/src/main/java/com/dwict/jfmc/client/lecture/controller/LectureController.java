package com.dwict.jfmc.client.lecture.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.lecture.service.LectureService;
import com.dwict.jfmc.client.mem.mapper.MemberMapper;
import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.MemberService;

@RestController
public class LectureController {
	
	@Resource(name = "memberMapper")
	private MemberMapper memMapper;
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "lectureService")
	private LectureService lectureService;
	
	@GetMapping(value = "/lecture/list")
	public ModelAndView lectureList(ModelAndView modelAndView) {
		modelAndView.setViewName("/lecture/lectureList");
		return modelAndView;
	}

	//종목별로 출력
	@GetMapping(value = "/lecture/class/{seq}")
	public ModelAndView classList(ModelAndView modelAndView, @PathVariable String seq) {
		modelAndView.addObject("class_seq", seq);
		modelAndView.setViewName("/lecture/classList");
		return modelAndView;
	}
	
	
	@GetMapping(value = "/lecture/list/{comcd}")
	public ModelAndView lectureList(ModelAndView modelAndView, @PathVariable String comcd) {		
		modelAndView.addObject("comcd", comcd);
		modelAndView.setViewName("/lecture/lectureList");
		return modelAndView;
	}

	@GetMapping(value = "/lecture/view")
	public ModelAndView lectureView(ModelAndView modelAndView, HttpServletRequest request) {
		modelAndView.setViewName("/lecture/lectureView");
		modelAndView.addObject("paymentYN",request.getParameter("paymentYN"));
		return modelAndView;
	}				

	@GetMapping(value = "/lecture/lecturePaymentDetail")
	public ModelAndView lecturePaymentDetail(ModelAndView modelAndView, HttpServletRequest request) {
		
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		//세션저장
		memberService.memSession(request, userId);		

		final HttpSession session = request.getSession(false);
		final Member members = (Member) session.getAttribute("member");
		System.out.println("세션=========================>"+ members.getId());
		
		
		Map<String, Object> maps = new HashMap<>();
		//사업장 별로 PG결제코드로 변경 maps = payService.payKeyInfo(request);
		//modelAndView.addAllObjects(maps);
		
		maps.put("MEM_ID", members.getId());
		maps.put("MEM_NO", members.getMemNo());
		modelAndView.addObject("otherData", maps);		
		
		modelAndView.addObject("SEQ",request.getParameter("SEQ"));
		modelAndView.setViewName("/lecture/lecturePaymentDetail");		
		return modelAndView;
	}
}
