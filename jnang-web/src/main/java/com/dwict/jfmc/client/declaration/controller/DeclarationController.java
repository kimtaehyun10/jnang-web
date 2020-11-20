package com.dwict.jfmc.client.declaration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DeclarationController {

	@GetMapping(value = "/declaration/{cmsCd}")
	public ModelAndView declarationPage(ModelAndView modelAndView, @PathVariable String cmsCd) {
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("board/declaration/declarationPage");
		return modelAndView;
	}
	
	@GetMapping(value = "/decComment/{cmsCd}")
	public ModelAndView decCommentPage(ModelAndView modelAndView, @PathVariable String cmsCd) {
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("board/declaration/decCommentPage");
		return modelAndView;
	}
}
