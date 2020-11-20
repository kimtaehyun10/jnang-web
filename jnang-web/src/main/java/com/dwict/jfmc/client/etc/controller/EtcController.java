package com.dwict.jfmc.client.etc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class EtcController {

	@GetMapping(value = "/etc/sitemap")
	public ModelAndView sitemap(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/sitemap");
		return modelAndView;
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

}
