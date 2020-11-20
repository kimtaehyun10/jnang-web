package com.dwict.jfmc.client.security.service;

import org.springframework.web.servlet.ModelAndView;

public interface ErrorService {

	ModelAndView error(String error_code);

}
