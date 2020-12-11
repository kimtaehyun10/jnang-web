package com.dwict.jfmc.client.mem.service;

import javax.servlet.http.HttpServletRequest;

import com.dwict.jfmc.client.mem.model.Member;

public interface MemberService {

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
	//회원가입 여부 조회
	String isMember(HttpServletRequest request);

}
