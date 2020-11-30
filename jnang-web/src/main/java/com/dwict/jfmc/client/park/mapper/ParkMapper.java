package com.dwict.jfmc.client.park.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("parkMapper")
public interface ParkMapper {

	void pubParkApplyWrite(Map<String, Object> param);

}
