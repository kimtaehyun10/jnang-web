package com.dwict.jfmc.client.lecture.mapper;

import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;
import com.dwict.jfmc.client.lecture.model.TrainClass;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("lectureMapper")
public interface LectureMapper {
	

	
	
	List<ComInfo> conditionSb1();
	List<Grpcd> conditionSb2(Grpcd grpcd);
	List<Grpcd> conditionSb3(Grpcd grpcd);
	List<TrainClass> lectureList(Map<String, Object> param);
	int lectureListCnt(Map<String, Object> param);
	List<ProgramItem> lectureProgramList(TrainClass param);
	Grpcd lectureGrpcd(Grpcd grpcd);
	ProgramItem getProgramInfo(ProgramItem programItem);

	//장바구니 중복 저장 첵크
	int setBasketChk(Map<String, Object> basketMap);
	//장바구니 담기
	int setBasket(Map<String, Object> maps);
	
	
	List<Map<String, Object>> classGroupList(Map<String, Object> param);
	List<Map<String, Object>> lecClassList(Map<String, Object> param);
	
	
}
