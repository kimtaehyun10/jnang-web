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
	int teamUpdate(Map<String, Object> requestMap);
	int teamMemSave(Map<String, Object> requestMap);
	
	
	//대관예약 센터 리스트
	List<Map<String, Object>> getCenterList();
	List<Map<String, Object>> getCenterPlaceList();

	//대관문의 저장
	int rentWriteSave(Map<String, Object> requestMap);
	
	//예약 신청 중복첵크
	int rentDblchk(Map<String, Object> requestMap);

	//예약 신청 저장
	int rentSave(Map<String, Object> requestMap);
	//주문 확인
	List <Map<String, Object>> rentOdList(Map<String, Object> maps);
	//주문결제
	Map<String, Object> rentOdPay(Map<String, Object> maps);

	
	//3시간내 미결제 대관 삭제
	int rentNoPayClear(Map<String, Object> requestMap);
	//체육관별 대관 list
	List<Map<String, Object>> getRentList(Map<String, Object> requestMap);
	List<Map<String, Object>> getRentCalendar(Map<String, Object> requestMap);
	
	//대관 일정 리스트
	List<Map<String, Object>> getPlaceTimeTabList(Map<String, Object> requestMap);
	
	//달력 탭별 예약현황
	List<Map<String, Object>> getPlaceCalendarTab(Map<String, Object> requestMap);
	
	//축구장 야구장 단가표
	List<Map<String, Object>> rentPriceList(Map<String, Object> maps);

	//팀리스트
	List<Map<String, Object>> getTeamList(Map<String, Object> maps);

	//선택 팀 정보
	Map<String, Object> getTeamData(Map<String, Object> maps);
	
	//팀 멤버 리스트
	List<Map<String, Object>> getTeamMemberList(Map<String, Object> maps);
	

	int teamCount(Map<String, Object> param);
	
	void teamMemDel(Map<String, Object> requestMap);
	
	//센터별 대관 그룹 가져오기(대강당 ,소강당, 강의실)
	List<Map<String, Object>> getCenterGroup(Map<String, Object> requestMap);
	List<Map<String, Object>> rentSportsRentList();
	List<Map<String, Object>> getMyTeamList(Map<String, Object> maps);
	int teamCnt(Map<String, Object> requestMap);
	void updateRtnAppType();
	void updateRentApp(Map<String, Object> maps);
	
}
