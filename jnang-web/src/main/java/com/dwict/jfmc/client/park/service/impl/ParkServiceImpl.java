package com.dwict.jfmc.client.park.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.park.mapper.ParkMapper;
import com.dwict.jfmc.client.park.service.ParkService;

@Service("parkService")
public class ParkServiceImpl implements ParkService {

	@Resource(name="parkMapper")
	private ParkMapper parkMapper;
	
	@Override
	public void pubParkApplyWrite(Map<String, Object> param) {		
		parkMapper.pubParkApplyWrite(param);
	}

}
