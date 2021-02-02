package com.dwict.jfmc.client.rent.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.page.service.PageService;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;
import com.dwict.jfmc.client.smpay.service.PayService;
import com.dwict.jfmc.client.com.util.*;
import com.dwict.jfmc.client.etc.service.EtcService;
import com.dwict.jfmc.client.mem.service.MemberService;

@RestController
public class RentController {

	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "etcService")
	EtcService etcService;
	
	@Resource(name = "mypageService")
	private MypageService mypgService;

	@Resource(name = "rentService")
	RentService rentService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name = "payService")
	private PayService payService;
	
	
	//대관 게시판 문의
	@GetMapping(value = "/rent/write")
	public ModelAndView rentWriteForm(ModelAndView modelAndView, Map<String, Object> requestMap, HttpServletRequest request) {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();

		//공휴일
		List<Map <String,Object>> holidayList= etcService.getHoliday(request);
		modelAndView.addObject("holidayList", holidayList);
		
		//셋팅 값
		final Map <String,Object> rentCfg = rentService.rentConfig(request);
		if (rentCfg == null) {
			modelAndView.setViewName("/rent/rentlist");
			return modelAndView;
		}
		modelAndView.addObject("rentCfg", rentCfg);
		
		//센터별 대관 그룹 가져오기(대강당 ,소강당, 강의실)
		List<Map <String,Object>> centerGroup = pageService.getCenterGroup(requestMap, request);
		modelAndView.addObject("centerGroup", centerGroup);
		
		//회원정보 가져오기
		final Map <String,Object> myData = mypgService.myInfo(MEM_ID);
		modelAndView.addObject("myData", myData);
		
		//체육관별 대관 정보 가져오기
		List<Map <String,Object>> rentList = pageService.getRentCalendar(requestMap, request);
		modelAndView.addObject("rentList", rentList);
		
		modelAndView.setViewName("/rent/rent_write");
		return modelAndView;
	}
	
	
	//대관 날짜 선택 신청 폼
	@GetMapping(value = "/rent/date")
	public ModelAndView rentForm(ModelAndView modelAndView, Map<String, Object> requestMap, HttpServletRequest request) {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();

		//공휴일
		List<Map <String,Object>> holidayList= etcService.getHoliday(request);
		modelAndView.addObject("holidayList", holidayList);
		
		//셋팅 값
		final Map <String,Object> rentCfg = rentService.rentConfig(request);
		if (rentCfg == null) {
			modelAndView.setViewName("/rent/rentlist");
			return modelAndView;
		}
		//팀 회원수
		int teamCount =  rentService.teamCount(MEM_ID, rentCfg);
		rentCfg.put("teamMemCnt",teamCount );
		modelAndView.addObject("rentCfg", rentCfg);

		//센터별 대관 그룹 가져오기(대강당 ,소강당, 강의실)
		List<Map <String,Object>> centerGroup = pageService.getCenterGroup(requestMap, request);
		modelAndView.addObject("centerGroup", centerGroup);
		
		//체육관별 대관 정보 가져오기
		List<Map <String,Object>> rentList = pageService.getRentCalendar(requestMap, request);
		modelAndView.addObject("rentList", rentList);
		
		//대관대상 리스트 정보
		//final List<Map <String,Object>> rentList = rentService.getCenterPlaceList();

		modelAndView.setViewName("/rent/rent_date");
		return modelAndView;
	}
	
	
	//예약확인
	@PostMapping(value = "/rent/rentOrder")
	public ModelAndView rentOrder(ModelAndView modelAndView, Map<String, Object> requestMap, HttpServletRequest request) {
		
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		//결제전 세션 저장
		memberService.memSession(request, MEM_ID);
		
		System.out.println("requestMap:"+ requestMap);
		System.out.println("request:"+ request);
		//회원정보 가져오기
		final Map <String,Object> myData = mypgService.myInfo(MEM_ID);
		String MEM_NO = (String) myData.get("MEM_NO");
		modelAndView.addObject("memData", myData);
		
		//셋팅 값
		final Map <String,Object> rentCfg = rentService.rentConfig(request);
		if (rentCfg == null) {
			modelAndView.setViewName("/rent/rentlist");
			return modelAndView;
		}
		modelAndView.addObject("rentCfg", rentCfg);
		
		//축구장 단가표
		List <Map <String, Object>> rentPriceList= rentService.rentPriceList(MEM_NO, request);
		modelAndView.addObject("rentPriceList", rentPriceList);
		
		//주문확인
		Map <String, Object> rentOrderList= rentService.rentOrder(MEM_NO, request);
		modelAndView.addObject("dataList", rentOrderList);
		
		//결제키
		Map<String, Object> maps = new HashMap<>();
		maps = payService.payKeyInfo(rentCfg);
		modelAndView.addAllObjects(maps);
		
		modelAndView.setViewName("/rent/rent_order");
		return modelAndView;
	}
	
	
	//팀 등록
	@GetMapping(value = "/rent/team")
	public ModelAndView rentTeam(ModelAndView modelAndView, HttpServletRequest request) {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		//결제전 세션 저장
		memberService.memSession(request, MEM_ID);
		
		String SEQ = request.getParameter("seq");
				
		//회원정보 가져오기
		final Map <String,Object> myData = mypgService.myInfo(MEM_ID);
		modelAndView.addObject("myData", myData);

		//생성팀 리스트
		final List<Map<String, Object>> teamList = rentService.getTeamList();
		modelAndView.addObject("teamList", teamList);		//팀 정보 
		final Map <String,Object> team = rentService.getTeam(SEQ);
		modelAndView.addObject("teamData", team);

		//팀 멤버 리스트 
		final List<Map <String,Object>> teamMemList = rentService.getTeamMemberList(SEQ);
		modelAndView.addObject("teamMemList", teamMemList);
		
		
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
