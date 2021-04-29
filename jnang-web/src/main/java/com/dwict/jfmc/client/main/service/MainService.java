package com.dwict.jfmc.client.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dwict.jfmc.client.main.model.Banner;
import com.dwict.jfmc.client.main.model.PopupWindow;
import com.dwict.jfmc.client.main.model.PopupZone;

public interface MainService {

	List<HashMap<String, ?>> getMenuList();

	String getCmsNm(String cmsCd);

	List<PopupZone> popupZone();

	List<PopupWindow> popupWindow();
	
	List<Map<String, Object>> cmsBoard(Map<String, Object> requestMap);

	List<Banner> banner();

	List<Map<String, Object>> popupInfo(Map<String, Object> requestMap);

	void updateMainCnt(HttpServletRequest request, Map<String, Object> requestMap);

}


