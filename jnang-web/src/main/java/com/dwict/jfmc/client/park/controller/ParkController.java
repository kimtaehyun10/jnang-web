package com.dwict.jfmc.client.park.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ParkController {

	@GetMapping(value = "/pubParkApply/{cmsCd}")
	public ModelAndView pubParkApply(ModelAndView modelAndView, @PathVariable String cmsCd) {
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("board/park/pubParkApply");
		return modelAndView;
	}
}
