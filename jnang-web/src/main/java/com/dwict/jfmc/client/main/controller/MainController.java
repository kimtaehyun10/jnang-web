package com.dwict.jfmc.client.main.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

	@RequestMapping(value = "/")
	public ModelAndView mainRootPage(ModelAndView modelAndView) {
		modelAndView.setViewName("main.nomenu");
		return modelAndView;
	}

	@GetMapping(value = "/popup/{fileName}")
	public ModelAndView popup(ModelAndView modelAndView, @PathVariable String fileName) {
		modelAndView.setViewName("/popup/"+fileName+".none");
		return modelAndView;
	}

}
