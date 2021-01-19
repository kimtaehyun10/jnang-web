package com.dwict.jfmc.client.main.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.main.model.Banner;
import com.dwict.jfmc.client.main.model.PopupWindow;
import com.dwict.jfmc.client.main.model.PopupZone;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("mainMapper")
public interface MainMapper {

	List<HashMap<String, ?>> getMenuList();

	String getCmsNm(String cmsCd);

	List<PopupZone> popupZone();
	
	List<PopupWindow> popupWindow();

	List<Map<String, Object>> cmsBoard(Map<String, Object> requestMap);

	List<Banner> banner();

	List<Map<String, Object>> popupInfo(Map<String, Object> requestMap);

}
