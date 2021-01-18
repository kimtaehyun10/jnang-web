package com.dwict.jfmc.client.mem.mapper;

import java.util.Map;

import com.dwict.jfmc.client.mem.model.Member;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("memberMapper")
public interface MemberMapper {

	boolean isExistingMember(Member param);
	Member findExistingMember(Member param);
	boolean isDuplicatedId(Member param);
	int insert(Member param);
	int update(Member param);
	int insertForLessThan14(Member param);
	int password(Member param);

	//회원검색 ID로
	Member findById(String param);
	

	
	
	/**########################################################
	 * 혁산 API 시작 
	 *#########################################################
	 */
	Map<String, String> isMember(Map<String, String> maps);
	
	

}
