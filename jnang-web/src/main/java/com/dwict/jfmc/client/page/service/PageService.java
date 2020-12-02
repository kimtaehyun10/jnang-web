package com.dwict.jfmc.client.page.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dwict.jfmc.client.page.model.Cont;
import com.dwict.jfmc.client.page.model.Menu;

public interface PageService {

	Cont htmlPage(String cmsCd);
	Cont empPage(String cmsCd);
	Menu boardPage(String cmsCd);

	//달력
	Map<String, Integer> today_info(Map<String, Object> dateMap);
	
	List<Map <String,Object>> dateList(Map<String, Integer> requestMap);
	
	//myData
	List<Map<String, Object>> getRentList(Map<String, Object> requestMap,HttpServletRequest request);
	
	List<Map<String, Object>> getRentCalendar(Map<String, Object> requestMap, HttpServletRequest request);
	
	//대관 일정 리스트
	List<Map<String, Object>> getPlaceTimeTabList(Map<String, Object> requestMap);
	
	//달력 탭별 예약현황
	List<Map<String, Object>> getPlaceCalendarTab(Map<String, Object> requestMap);
	
	
	//할인 변경시 금액 변경으로  EncryptData값 받기
	String getOdEncryptData(Map<String, Object> param);
	
}
