package com.dwict.jfmc.client.park.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("parkMapper")
public interface ParkMapper {

	void pubParkApplyWrite(Map<String, Object> param);

	int pubParkCheck(Map<String, Object> requestMap);

	List<Map<String, Object>> pubParkComCd(Map<String, Object> requestMap);

}
