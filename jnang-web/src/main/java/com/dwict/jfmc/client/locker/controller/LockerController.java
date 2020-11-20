package com.dwict.jfmc.client.locker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LockerController {

	@GetMapping(value = "/locker/list")
	public ModelAndView lectureList(ModelAndView modelAndView) {
		modelAndView.setViewName("/locker/lockerList");
		return modelAndView;
	}

	@GetMapping(value = "/locker/list/{comcd}")
	public ModelAndView lectureList(ModelAndView modelAndView, @PathVariable String comcd) {
		modelAndView.addObject("comcd", comcd);
		modelAndView.setViewName("/locker/lockerList");
		return modelAndView;
	}

}
