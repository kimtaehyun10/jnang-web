package com.dwict.jfmc.client.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

	@Override
	public List<Map<String, Object>> popupInfo(Map<String, Object> requestMap) {
		
		return mapper.popupInfo(requestMap);
	}

	@Override
	public void updateMainCnt(HttpServletRequest request, Map<String, Object> requestMap) {
		
		// 접속자 IP
		String ip = request.getHeader("X-FORWARDED-FOR");
		if (ip == null || ip.length() == 0) {
		   ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0) {
		   ip = request.getHeader("WL-Proxy-Client-IP");  // 웹로직
		}
		if (ip == null || ip.length() == 0) {
		   ip = request.getRemoteAddr() ;
		}
		
		// 에이전트
		String agent = request.getHeader("User-Agent");
		
		System.out.println(agent);
		
		// 브라우져 구분
		String brower = "";

		if (agent != null) {
		   if (agent.indexOf("Trident") > -1) {
		      brower = "MS_IEXPLORER";
		   } else if (agent.indexOf("Edg") > -1) {
		      brower = "MS_EDGE";
		   } else if (agent.indexOf("Whale") > -1) {
		      brower = "Naver_Whale";
		   } else if (agent.indexOf("Chrome") > -1) {
		      brower = "Google_Chrome";
		   } else if (agent.indexOf("Safari") > -1) {
			  brower = "Apple_Safari";
		   } else if (agent.indexOf("Opera") > -1) {
		      brower = "Opera";
		   } else if (agent.indexOf("iPhone") > -1 && agent.indexOf("Mobile") > -1) {
		      brower = "iPhone";
		   } else if (agent.indexOf("Android") > -1 && agent.indexOf("Mobile") > -1) {
		      brower = "Android";
		   }
		}
		
		String[] mobileOs = {"iphone", "ipod", "Android"};
		if(agent != null && !agent.equals("")) {
			for(int i = 0; i < mobileOs.length; i++) {
				if (agent.indexOf(mobileOs[i]) > -1) {
					System.out.println(mobileOs[i]);
				}
			}
		}
		
		// OS 구분
		String os = "";
		 

		if(agent.indexOf("NT 6.0") != -1) os = "Windows";
		else if(agent.indexOf("NT 5.2") != -1) os = "Windows";
		else if(agent.indexOf("NT 5.1") != -1) os = "Windows";
		else if(agent.indexOf("NT 5.0") != -1) os = "Windows";
		else if(agent.indexOf("NT") != -1) os = "Windows";
		else if(agent.indexOf("9x 4.90") != -1) os = "Windows";
		else if(agent.indexOf("98") != -1) os = "Windows";
		else if(agent.indexOf("10") != -1) os = "Windows";
		else if(agent.indexOf("7") != -1) os = "Windows";
		else if(agent.indexOf("8") != -1) os = "Windows";
		else if(agent.indexOf("95") != -1) os = "Windows";
		else if(agent.indexOf("Win16") != -1) os = "Windows";
		else if(agent.indexOf("Windows") != -1) os = "Windows";
		else if(agent.indexOf("Linux") != -1) os = "Linux";
		else if(agent.indexOf("Macintosh") != -1) os = "Macintosh";
		else if(agent.indexOf("Mac") != -1) os = "Mac";
		else os = ""; 
		
		if(brower.equals("Android") || brower.equals("iPhone")) {
			os = "Mobile";
		}else if(brower.equals("Apple_Safari")){
			os = "Mac";
		}
		
		
		
		requestMap.put("ip",ip);
		requestMap.put("os",os);
		requestMap.put("browser",brower);
		mapper.insertMenuCnt(requestMap);
	}


}
