package com.dwict.jfmc.client.mypage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dwict.jfmc.client.com.model.Paging;

public interface MypageService {

	//오래된 장바구니 지우기
	int basketClear(Map<String, Object> requestMap);
	
	List<Map<String, Object>> basketList(Map<String, Object> requestMap, HttpServletRequest request);

	//회원정보 조회 , 대관신청사용
	Map<String, Object> myInfo(String id);

	//my대관
	List<Map<String, Object>> getMyRentList(String userId);

	Paging myBoard(Map<String, Object> param);

	//my 수강신청 현황
	List<Map<String, Object>> getMyClssList(String userId);

	//수강신청 당일 취소처리
	int classCancelPay(Map<String, Object> requestMap, HttpServletRequest request);
	
	void modifyUpdate(Map<String, Object> param);
	
	void modifyDelete(Map<String, Object> param);

	List<Map<String, Object>> lockerStatusList(Map<String, Object> param);

	//myPage 대관 선택  취소
	int rentSelectCancel(HttpServletRequest request);

	Map<String, Object> reLocker(Map<String, Object> param);

	Map<String, Object> lockerPayDetail(HttpServletRequest request, Map<String, Object> param);

	List<Map<String, Object>> forOrderCancel(String tID);

	List<Map<String, Object>> getMyRentList1(String userId);

	List<Map<String, Object>> getMyRentList2(Map<String, Object> map);

	List<Map<String, Object>> getMyRentIdxList(Map<String, Object> map);
	
	//결제상세보기
	Map<String, Object> getMyClss(Map<String, Object> requestMap);
}
