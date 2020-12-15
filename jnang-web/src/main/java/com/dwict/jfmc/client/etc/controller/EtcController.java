package com.dwict.jfmc.client.etc.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.etc.service.EtcService;

@RestController
public class EtcController {
	
	@Resource(name = "etcService")
	private EtcService etcService;

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
	
	@GetMapping(value = "/etc/tmq")
	public ModelAndView tmq(ModelAndView modelAndView) {
		modelAndView.setViewName("etc/tmq");
		return modelAndView;
	}
	
	@GetMapping(value = "/sso/scc")
	public ModelAndView scc(ModelAndView modelAndView, @RequestParam Map<String, Object> requestMap) {
		Map<String, Object> resultMap = etcService.scc(requestMap);
		modelAndView.addObject("msg",resultMap.get("msg"));
		modelAndView.addObject("location",resultMap.get("location"));
		modelAndView.setViewName("etc/scc.none");
		return modelAndView;
	}
}
