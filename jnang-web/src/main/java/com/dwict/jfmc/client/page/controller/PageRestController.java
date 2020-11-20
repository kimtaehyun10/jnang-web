package com.dwict.jfmc.client.page.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.page.model.Cont;
import com.dwict.jfmc.client.page.model.Menu;
import com.dwict.jfmc.client.page.service.PageService;
import com.dwict.jfmc.client.rent.service.RentService;

@RestController
@RequestMapping("/data")
public class PageRestController {

	@Resource(name = "pageService")
	private PageService service;
	
	@Resource(name = "rentService")
	RentService rtnService;

	@GetMapping(value = "/htmlPage/{cmsCd}")
	public Cont htmlPage(HttpServletRequest request, @PathVariable String cmsCd) {
		return service.htmlPage(cmsCd);
	}

	@GetMapping(value = "/empPage/{cmsCd}")
	public Cont empPage(HttpServletRequest request, @PathVariable String cmsCd) {
		return service.empPage(cmsCd);
	}

	@GetMapping(value = "/boardPage/{cmsCd}")
	public Menu faqPage(HttpServletRequest request, @PathVariable String cmsCd) {
		return service.boardPage(cmsCd);
	}

	//종목별 대관 센터  정보 가져오기 (종목 센터 선택)
	@PostMapping(value = "/rentGroup")
	public List<Map<String, Object>> rentGroupList(HttpServletRequest request) {
		return rtnService.rentGroupList(request);
	}

	//종목별 대관 센터  리스트 가져오기 (VIEW)
	@PostMapping(value = "/rentList")
	public List<Map<String, Object>> rentList(HttpServletRequest request) {
		return rtnService.rentList(request);
	}

	
	//월,일별  예약현황 불러오기
	@GetMapping(value = "/getRentList")
	public List<Map<String, Object>> getRentList(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
		return service.getRentList(requestMap, request);
	}
	
}
