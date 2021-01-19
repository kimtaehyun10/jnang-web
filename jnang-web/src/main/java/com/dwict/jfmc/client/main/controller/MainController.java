package com.dwict.jfmc.client.main.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.main.service.MainService;

@RestController
public class MainController {

	@Resource(name = "mainService")
	private MainService mainService;
	
	@RequestMapping(value = "/")
	public ModelAndView mainRootPage(ModelAndView modelAndView) {
		modelAndView.setViewName("main.nomenu");
		return modelAndView;
	}

	@GetMapping(value = "/popup/popup20.p")
	public ModelAndView popup(ModelAndView modelAndView, @RequestParam Map<String, Object> requestMap) {
		List<Map<String, Object>> popupInfo = mainService.popupInfo(requestMap);
		modelAndView.addObject("popupInfo", popupInfo.get(0));
		modelAndView.setViewName("popup/popup20.none");
		return modelAndView;
	}
	
}
