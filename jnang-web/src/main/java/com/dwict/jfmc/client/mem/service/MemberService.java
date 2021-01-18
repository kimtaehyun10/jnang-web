package com.dwict.jfmc.client.mem.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dwict.jfmc.client.mem.model.Member;

public interface MemberService {

	Map<String, Object> memCard(String CardNo);
	
	boolean isExistingMember(Member param);
	Member findExistingMember(Member param);
	boolean isDuplicatedId(Member param);
	int insert(Member param);
	int update(Member param);
	int insertForLessThan14(Member param);
	int password(Member param);
	void memSession(HttpServletRequest request, String userId);
	
	/**########################################################
	 * 혁산 API
	 *#########################################################
	 */
	//혁산 api 회원가입 여부 조회
	String apiIsMember(HttpServletRequest request);
	//혁산 api 회원가입
	String apiJoinMember(Map<String, Object> requestMap, HttpServletRequest request);
	//혁산 api 회원아이디 중복조회
	String apiCheckId(HttpServletRequest request);
	String apiMembership(HttpServletRequest request);
	
	


}
