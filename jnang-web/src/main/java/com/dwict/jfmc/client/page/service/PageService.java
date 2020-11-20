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
	
}
