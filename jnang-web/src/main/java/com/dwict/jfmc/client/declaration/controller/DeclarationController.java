package com.dwict.jfmc.client.declaration.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.main.service.MainService;

@RestController
public class DeclarationController {
	
	@Resource(name = "mainService")
	private MainService mainService;
	
	@GetMapping(value = "/declaration/{cmsCd}")
	public ModelAndView declarationPage(ModelAndView modelAndView, @PathVariable String cmsCd, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("board/declaration/declarationPage");
		return modelAndView;
	}
	
	@GetMapping(value = "/decComment/{cmsCd}")
	public ModelAndView decCommentPage(ModelAndView modelAndView, @PathVariable String cmsCd, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("board/declaration/decCommentPage");
		return modelAndView;
	}
}
