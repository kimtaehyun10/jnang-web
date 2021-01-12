package com.dwict.jfmc.client.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.main.mapper.MainMapper;
import com.dwict.jfmc.client.main.model.Banner;
import com.dwict.jfmc.client.main.model.PopupWindow;
import com.dwict.jfmc.client.main.model.PopupZone;
import com.dwict.jfmc.client.main.service.MainService;

@Service("mainService")
public class MainServiceImpl implements MainService {

	@Resource(name = "mainMapper")
	private MainMapper mapper;

	@Override
	public List<HashMap<String, ?>> getMenuList() {
		return mapper.getMenuList();
	}

	@Override
	public String getCmsNm(String cmsCd) {
		return mapper.getCmsNm(cmsCd);
	}

	@Override
	public List<PopupZone> popupZone() {
		return mapper.popupZone();
	}
	
	@Override
	public List<PopupWindow> popupWindow(){
		return mapper.popupWindow();
		
	}
	

	

	@Override
	public List<Map<String, Object>> cmsBoard(Map<String, Object> requestMap) {		
		return mapper.cmsBoard(requestMap);
	}

	@Override
	public List<Banner> banner() {
		return mapper.banner();
	}

}
