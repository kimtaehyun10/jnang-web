package com.dwict.jfmc.client.etc.mapper;

import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.com.model.AtchFile;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("etcMapper")
public interface EtcMapper {

	//공휴일정보 가져오기
	List <Map<String, Object>> getHoliday(Map<String, Object> maps);
	
	//sso API
	Map<String, Object> scc(Map<String, Object> requestMap);

	//키오스크
	List <Map <String,Object>> apiKioskDayItemList(Map<String, Object> maps);

	//키오스크 취소
	Map<String, Object> kioskCancelData(Map<String, Object> maps);
}
