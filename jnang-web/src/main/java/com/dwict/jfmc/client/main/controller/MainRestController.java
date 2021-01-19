package com.dwict.jfmc.client.main.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.main.model.Banner;
import com.dwict.jfmc.client.main.model.PopupWindow;
import com.dwict.jfmc.client.main.model.PopupZone;
import com.dwict.jfmc.client.main.service.MainService;

@RestController
@RequestMapping("/data")
public class MainRestController {

	@Resource(name = "mainService")
	private MainService service;

	@GetMapping(value = "/popupZone")
	public List<PopupZone> popupZone(HttpServletRequest request) {
		return service.popupZone();
	}
	
	@GetMapping(value = "/popupWindow")
	public List<PopupWindow> popupWindow(HttpServletRequest request) {
		return service.popupWindow();
	}
	
	//공지사항/보도자료/입찰공고/채용공고 ..
	@GetMapping(value = "/cmsBoard")
	public List<Map<String, Object>> cmsBoard(HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		List<Map<String, Object>> rtn = service.cmsBoard(requestMap);
		return rtn;
	}
	
	@GetMapping(value = "/banner")
	public List<Banner> banner(HttpServletRequest request) {
		return service.banner();
	}
	
	//팝업창 관리
	@GetMapping(value = "/popupInfo")
	public List<Map<String, Object>> popupInfo(HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		return service.popupInfo(requestMap);
	}
}
