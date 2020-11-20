package com.dwict.jfmc.client.security.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.security.service.ErrorService;

@Controller
public class ErrorController {

	@Resource(name = "errorService")
	private ErrorService service;

	@RequestMapping(value="/error/{error_code}")
	public ModelAndView error(@PathVariable String error_code) {
		return service.error(error_code);
	}

}
