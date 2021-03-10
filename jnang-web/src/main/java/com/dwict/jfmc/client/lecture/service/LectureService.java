package com.dwict.jfmc.client.lecture.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;

public interface LectureService {

	List<ComInfo> conditionSb1();
	List<Grpcd> conditionSb2(Grpcd grpcd);
	List<Grpcd> conditionSb3(Grpcd grpcd);
	Paging lectureList(Map<String, Object> param);
	ProgramItem getProgramInfo(ProgramItem programItem);
	Map<String, Object> setBasket(ProgramItem programItem);
	
	

	
	//종목 그룹 가져오기
	List<Map<String, Object>> classGroupList(HttpServletRequest request);
	//이사장 전용 종목 강좌 리스트
	List<Map<String, Object>> lecClassList(HttpServletRequest request);
	

}
