package com.dwict.jfmc.client.rent.mapper;

import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;
import com.dwict.jfmc.client.lecture.model.TrainClass;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rentMapper")
public interface RentMapper {

	List<ComInfo> conditionSb1();
	List<Grpcd> conditionSb2(Grpcd grpcd);
	List<Grpcd> conditionSb3(Grpcd grpcd);
	List<TrainClass> lectureList(Map<String, Object> param);
	int lectureListCnt(Map<String, Object> param);
	List<ProgramItem> lectureProgramList(TrainClass param);
	ProgramItem getProgramInfo(ProgramItem programItem);

	
	//종목별 대관 센터  정보 가져오기
	List<Map<String, Object>> rentGroupList(Map<String, Object> param);
	
	//종목별 대관 센터 리스트 가져오기
	List<Map<String, Object>> rentList(Map<String, Object> param);
	
	//대관 환경설정값
	Map<String, Object> rentConfig(Map<String, Object> requestMap);
	
	
	//팀 신청 저장
	int teamSave(Map<String, Object> requestMap);
	int teamMemSave(Map<String, Object> requestMap);
	
	
	//대관예약 센터 리스트
	List<Map<String, Object>> getCenterList();
	List<Map<String, Object>> getCenterPlaceList();

	//예약 신청 저장
	int rentSave(Map<String, Object> requestMap);

	//체육관별 대관 list
	List<Map<String, Object>> getRentList(Map<String, Object> requestMap);



}
