package com.dwict.jfmc.client.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.main.model.Banner;
import com.dwict.jfmc.client.main.model.PopupZone;

public interface MainService {

	List<HashMap<String, ?>> getMenuList();

	String getCmsNm(String cmsCd);

	List<PopupZone> popupZone();

	List<Map<String, Object>> cmsBoard(Map<String, Object> requestMap);

	List<Banner> banner();

}
