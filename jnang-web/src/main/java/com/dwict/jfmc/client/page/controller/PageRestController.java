package com.dwict.jfmc.client.page.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.page.model.Cont;
import com.dwict.jfmc.client.page.model.Menu;
import com.dwict.jfmc.client.page.service.PageService;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;

@RestController
@RequestMapping("/data")
public class PageRestController {

	@Resource(name = "memberService")
	private MemberService memberService;
	
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
	@GetMapping (value = "/rentGroup")
	public List<Map<String, Object>> rentGroupList(HttpServletRequest request) {
		return rtnService.rentGroupList(request);
	}

	//종목별 대관 센터  리스트 가져오기 (VIEW)
	@GetMapping(value = "/rentList")
	public List<Map<String, Object>> rentList(HttpServletRequest request) {
		return rtnService.rentList(request);
	}

	
	//월,일별  예약현황 불러오기
	@GetMapping(value = "/getRentList")
	public List<Map<String, Object>> getRentList(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
		return service.getRentList(requestMap, request);
	}
	
	//대관 일정 리스트
	@GetMapping(value = "/getPlaceTimeTabList")
	public List<Map<String, Object>> getPlaceTab(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
		return service.getPlaceTimeTabList(requestMap);
	}	
	
	//달력 탭별 예약현황
	@GetMapping(value = "/getPlaceCalendarTab")
	public List<Map<String, Object>> getCalendarTab(@RequestParam  Map<String, Object> requestMap, HttpServletRequest request) {
		return service.getPlaceCalendarTab(requestMap);
	}		
	
	
	
	//할인 변경시 금액 변경으로  EncryptData값 받기 
	///rent/rentOrder 사용
	@RequestMapping(value = "/getOdEncryptData/{ediDate}/{goodsAmt}/{COMCD}")
	public Map <String, Object> getOdEncryptData(HttpServletRequest request, @PathVariable String ediDate, @PathVariable String goodsAmt, @PathVariable String COMCD) {
		
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		//결제전 세션 저장
		memberService.memSession(request, MEM_ID);
				
		final Map<String, Object> param = new HashMap<>();
		param.put("ediDate", ediDate);				
		param.put("goodsAmt", goodsAmt);
		param.put("COMCD", COMCD);
		
		return service.getOdEncryptData(param);
	}
	
}
