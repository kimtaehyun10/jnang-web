package com.dwict.jfmc.client.etc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public interface EtcService {

	HashMap<String, String> getWeatherInfo();

	//공휴일 정보
	List<Map<String, Object>> getHoliday(HttpServletRequest request);

}
