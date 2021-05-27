package com.dwict.jfmc.client.mypage.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface MypageMapper {

	//장바구니 비우기, 선택 삭제
	int basketClear(Map<String, Object> requestMap);

	//장바구니 리스트
	List<Map<String, Object>> basketList(Map<String, Object> maps);

	//내정보
	Map<String, Object> memberInfo(String id);
	
	//my 대관
	List<Map<String, Object>> myRentDataList(String id);
	
	List<Map<String, ?>> myBoard(Map<String, Object> param);
	
	int myBoardCnt(Map<String, Object> param);

	//내  수강신청 현황
	List<Map<String, Object>> getMyClssList(String userId);
	
	//수강신청 당일 취소처리
	int classCancelPay(Map<String, Object> requestMap);
	
	void modifyUpdate(Map<String, Object> param);
	
	void modifyDelete(Map<String, Object> param);

	List<Map<String, Object>> lockerStatusList(Map<String, Object> param);

	//myPage 대관 선택  취소
	int rentSelectCancel(Map<String, Object> maps);

	Map<String, Object> reLocker(Map<String, Object> param);

	List<Map<String, Object>> forOrderCancel(String tID);

	List<Map<String, Object>> myRentDataList1(String userId);

	List<Map<String, Object>> myRentDataList2(Map<String, Object> map);

	List<Map<String, Object>> getMyRentIdxList(Map<String, Object> map);
	
}
