package com.dwict.jfmc.client.park.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.park.service.ParkService;

@RestController
@RequestMapping("/data")
public class ParkRestController {

	@Resource(name = "parkService")
	private ParkService parkService;
	
	@PostMapping(value = "/pubParkApply/{cmsCd}")
	public Map<String, Object> pubParkApplyWrite(HttpServletRequest request, @PathVariable String cmsCd) throws Exception {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();		
		param.put("name", request.getParameter("name"));
		param.put("phone", request.getParameter("phone"));
		param.put("car_number", request.getParameter("car_number"));
		param.put("car_model", request.getParameter("car_model"));		
		param.put("pub_park_name", request.getParameter("pub_park_name"));
		param.put("use_time", request.getParameter("use_time"));
		param.put("discount", request.getParameter("discount"));
		parkService.pubParkApplyWrite(param);
		resultMap.put("result", "저장되었습니다.");
		return resultMap;
	}
}
