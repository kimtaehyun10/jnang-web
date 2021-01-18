package com.dwict.jfmc.client.lecture.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.lecture.service.LectureService;
import com.dwict.jfmc.client.mem.mapper.MemberMapper;
import com.dwict.jfmc.client.mem.model.Member;

@RestController
public class LectureController {
	
	@Resource(name = "memberMapper")
	private MemberMapper memMapper;
	
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
		return modelAndView;
	}
	
	@GetMapping(value = "/lecture/view1")
	public ModelAndView lectureView2(ModelAndView modelAndView, HttpServletRequest request) {
	final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		final String userId = auth.getName();
		
		Member member = memMapper.findById(userId);
		
		if(member == null) {
			modelAndView.setViewName("/lecture/lectureView3");
			return modelAndView;
		}


		String memc = member.getCardNo();
	 if(memc == null){
			modelAndView.setViewName("/lecture/lectureView2");
		}else {
			modelAndView.setViewName("/lecture/lectureView1");
		}
	
		return modelAndView;
	}
	
	@GetMapping(value = "/lecture/view3")
	public ModelAndView lectureView3(ModelAndView modelAndView, HttpServletRequest request) {
		modelAndView.setViewName("/lecture/lectureView3");
		return modelAndView;
		
	}
	
	

}
