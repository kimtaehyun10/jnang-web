package com.dwict.jfmc.client.park.service;

import java.util.List;
import java.util.Map;

public interface ParkService {

	void pubParkApplyWrite(Map<String, Object> param) throws Exception;

	int pubParkCheck(Map<String, Object> requestMap);

	List<Map<String, Object>> pubParkComCd(Map<String, Object> requestMap);

}
