package com.dwict.jfmc.client.rent.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;

public interface RentService {

	List<ComInfo> conditionSb1();
	List<Grpcd> conditionSb2(Grpcd grpcd);
	List<Grpcd> conditionSb3(Grpcd grpcd);
	Paging lectureList(Map<String, Object> param);
	ProgramItem getProgramInfo(ProgramItem programItem);

	//종목별 대관 센터 정보 가져오기
	List<Map<String, Object>> rentGroupList(HttpServletRequest request);
	
	//종목별 대관 센터 리스트 가져오기
	List<Map<String, Object>> rentList(HttpServletRequest request);	
	
	//대관 센터 리스트
	List<Map<String, Object>> getCenterList();
	List<Map<String, Object>> getCenterPlaceList();
	
	//대관 환경설정값
	Map<String, Object> rentConfig(HttpServletRequest request);
	
	//예약접수
	Map<String, Object> rentSave(Map<String, Object> requestMap, HttpServletRequest request);

	//대관 문의 저장(게시판)
	int writeSave(Map<String, Object> requestMap);
	
	//접수확인
	Map<String, Object> rentOrder(String MEM_NO, HttpServletRequest request);
	//결제
	Map<String, Object> rentOdPay(HttpServletRequest request);
	
	//팀명 저장
	int teamSave(Map<String, Object> requestMap, HttpServletRequest request);
	//int teamUpdate(Map<String, Object> requestMap, HttpServletRequest request);
	
	//축구장 단가리스트
	List<Map<String, Object>> rentPriceList(String mEM_NO, HttpServletRequest request);

	//팀 멤버 리스트
	List<Map<String, Object>> getTeamMemberList(String SEQ);
	
	//팀명 리스트
	List<Map<String, Object>>  getTeamList();
	Map<String, Object> getTeam(String SEQ);
	
	//팀 회원수
	int teamCount(String mEM_ID, Map<String, Object> maps);
	
	void pubRentApplyWrite(Map<String, Object> param) throws Exception;
	List<Map<String, Object>> rentSportsRentList(HttpServletRequest request);
	List<Map<String, Object>> getMyTeamList(String mEM_ID, Map<String, Object> maps);
	
	
	


}
