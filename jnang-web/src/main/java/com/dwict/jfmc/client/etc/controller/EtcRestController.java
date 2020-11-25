package com.dwict.jfmc.client.etc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.etc.service.EtcService;

@RestController
@RequestMapping("/data")
public class EtcRestController {

	@Resource(name = "etcService")
	private EtcService etcService;

	@GetMapping(value = "/weather")
	protected HashMap<String, String> getWeatherInfo(HttpServletRequest request) {
		return etcService.getWeatherInfo();
	}
	
	//공휴일 정보 (월별/다중 일별)
	@RequestMapping(value = "/holiday")
	protected List<Map<String, Object>> getHoliday(HttpServletRequest request) {
		return etcService.getHoliday(request);
	}
	

}
