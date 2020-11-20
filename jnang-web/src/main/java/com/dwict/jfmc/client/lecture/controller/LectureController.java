package com.dwict.jfmc.client.lecture.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LectureController {

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

}
