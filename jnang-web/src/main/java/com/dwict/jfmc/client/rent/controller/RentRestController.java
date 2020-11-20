package com.dwict.jfmc.client.rent.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.com.service.CommonService;
import com.dwict.jfmc.client.rent.service.RentService;


@RestController
@RequestMapping("/rent")
public class RentRestController {

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "rentService")
	RentService rtnService;

	//댸관 예약 저장
	@PostMapping(value = "/save")
	public int rtnSave(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
	
		//예약 저장
		final int rtn = rtnService.rentSave(requestMap, request);

		return rtn;
	}

	
	//팀신청 저장
	@PostMapping(value = "/teamSave")
	//public int teamSave(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
	public int teamSave(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
		
		//예약 저장
		final int rtn = rtnService.teamSave(requestMap, request);

		return rtn;
	}
	
	
}