package com.dwict.jfmc.client.etc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface EtcService {

	HashMap<String, String> getWeatherInfo();

	//공휴일 정보
	List<Map<String, Object>> getHoliday(HttpServletRequest request);
	//sso API
	Map<String, Object> scc(Map<String, Object> requestMap, HttpServletRequest request);

	//kiosk 정수 API
	String apiKioskDayItemList(Map<String, Object> requestMap, HttpServletRequest request);

	//kiosk 정수 API
	String kioskDayInsert(Map<String, Object> requestMap, HttpServletRequest request);

	//kiosk 결제취소
	String kioskDayCancel(Map<String, Object> requestMap, HttpServletRequest request);


}
