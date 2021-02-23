package com.dwict.jfmc.client.rent.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dwict.jfmc.client.com.service.CommonService;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;


@RestController
@RequestMapping("/rent")
public class RentRestController {

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "rentService")
	RentService rtnService;

	//댸관 예약 저장
	@PostMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> rtnSave(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
		System.out.println("requestMap = " + requestMap);
		System.out.println("request = " + request);
		
		Enumeration params = request.getParameterNames();
		while(params.hasMoreElements()) {
		  String name = (String) params.nextElement();
		  System.out.print(name + " : " + request.getParameter(name) + "     "); 
		}
		System.out.println();


		//예약 저장
		Map<String, Object> rtn = rtnService.rentSave(requestMap, request);
		return rtn;
	}
	
		//댸관 예약 저장
		@PostMapping(value = "/save2")
		@ResponseBody
		public Map<String, Object> rtnSave2(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
			System.out.println("requestMap = " + requestMap);
			System.out.println("request = " + request);
			
			Enumeration params = request.getParameterNames();
			while(params.hasMoreElements()) {
			  String name = (String) params.nextElement();
			  System.out.print(name + " : " + request.getParameter(name) + "     "); 
			}
			System.out.println();


			//예약 저장
			Map<String, Object> rtn = rtnService.rentSave2(requestMap, request);
			return rtn;
			
		}

	
	//댸관 문의 저장
	@PostMapping(value = "/writeSave")
	@ResponseBody
	public int writeSave(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request, MultipartHttpServletRequest files) throws Exception {
		
		
		
		final Map<String, Object> param = new HashMap<>();
		final List<MultipartFile> fileList = files.getFiles("files");
		
		param.put("fileList", fileList);
		
		rtnService.pubRentApplyWrite(param);
		
		String ATTACH_ID = (String) param.get("attach_id");
		//예약 저장
		
		System.out.println(requestMap);
		System.out.println(param);
		
		requestMap.put("ATTACH_ID", ATTACH_ID);
		
		int rtn = rtnService.writeSave(requestMap);
		return rtn;
	}
	
	//팀신청 저장
	@PostMapping(value = "/teamSave")
	@ResponseBody
	//public int teamSave(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
	public int teamSave(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request, MultipartHttpServletRequest files) throws Exception {
		final Map<String, Object> param = new HashMap<>();
		final List<MultipartFile> fileList = files.getFiles("files");
		
		param.put("fileList", fileList);
		
		rtnService.pubRentApplyWrite(param);
		
		String ATTACH_ID = (String) param.get("attach_id");
		//예약 저장
		
		System.out.println(requestMap);
		System.out.println(param);
		
		requestMap.put("ATTACH_ID", ATTACH_ID);
		
		//예약 저장
		final int rtn = rtnService.teamSave(requestMap, request);

		return rtn;
	}
	
	
}