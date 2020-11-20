package com.dwict.jfmc.client.rent.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.page.service.PageService;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;

@RestController
public class RentController {

	@Resource(name = "mypageService")
	private MypageService mypgService;

	@Resource(name = "rentService")
	RentService rtnService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	//접수폼
	@GetMapping(value = "/rent/date")
	public ModelAndView rentForm(ModelAndView modelAndView, Map<String, Object> requestMap, HttpServletRequest request) {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();

		//셋팅 값
		final Map <String,Object> rentCfg = rtnService.rentConfig(request);
		if (rentCfg == null) {
			modelAndView.setViewName("/rent/rentlist");
			return modelAndView;
		}
		modelAndView.setViewName("/rent/rent_date");
		modelAndView.addObject("rentCfg", rentCfg);
		
		//회원정보 가져오기
		//final Map <String,Object> myData = mypgService.myInfo(MEM_ID);
		//modelAndView.addObject("myData", myData);
		

		//체육관별 대관 정보 가져오기
		List<Map <String,Object>> rentList = pageService.getRentList(requestMap, request);
		modelAndView.addObject("rentList", rentList);
		
		//대관대상 리스트 정보
		//final List<Map <String,Object>> rentList = rtnService.getCenterPlaceList();

		modelAndView.setViewName("/rent/rent_date");
		return modelAndView;
	}

	
	//팀 등록
	@GetMapping(value = "/rent/team")
	public ModelAndView rentTeam(ModelAndView modelAndView, HttpServletRequest request) {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();

		
		//셋팅 값
		//final List< Map <String,Object> > rentCfg = rtnService.rentConfig(MEM_ID);
		//modelAndView.addObject("rentCfg", rentCfg);
				
		//회원정보 가져오기
		final Map <String,Object> myData = mypgService.myInfo(MEM_ID);
		modelAndView.addObject("myData", myData);

		//대관대상 리스트 정보
		//final List<Map <String,Object>> rentList = rtnService.getCenterPlaceList();

		//modelAndView.addObject("rentList", rentList);
		modelAndView.setViewName("/rent/team");
		return modelAndView;
	}
	/*
	@GetMapping(value = "/rent/list/{comcd}")
	public ModelAndView lectureList(ModelAndView modelAndView, @PathVariable String comcd) {
		modelAndView.addObject("comcd", comcd);
		modelAndView.setViewName("/rent/lectureList");
		return modelAndView;
	}

	@GetMapping(value = "/rent/view")
	public ModelAndView lectureView(ModelAndView modelAndView, HttpServletRequest request) {
		modelAndView.setViewName("/rent/lectureView");
		return modelAndView;
	}
	 */

}
