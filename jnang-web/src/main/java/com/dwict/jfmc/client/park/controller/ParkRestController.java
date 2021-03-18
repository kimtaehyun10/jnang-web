package com.dwict.jfmc.client.park.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dwict.jfmc.client.park.service.ParkService;

@RestController
@RequestMapping("/data")
public class ParkRestController {

	@Resource(name = "parkService")
	private ParkService parkService;
	
	@PostMapping(value = "/pubParkApply/{cmsCd}")
	public Map<String, Object> pubParkApplyWrite(HttpServletRequest request, @PathVariable String cmsCd, MultipartHttpServletRequest files) throws Exception {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		final List<MultipartFile> fileList = files.getFiles("files");
		param.put("name", request.getParameter("name"));
		param.put("phone", request.getParameter("phone"));
		param.put("car_number", request.getParameter("car_number"));
		param.put("car_model", request.getParameter("car_model"));		
		param.put("pub_park_name", request.getParameter("pub_park_name"));
		param.put("use_time", request.getParameter("use_time"));
		param.put("discount", request.getParameter("discount"));		
		param.put("fileList", fileList);				
		parkService.pubParkApplyWrite(param);
		resultMap.put("result", "저장되었습니다.");					
		
		return resultMap;
	}
	//공영주차 신청 중복 체크
	@GetMapping(value = "/pubParkApply/pubParkCheck.json")
	public Map<String, Object> pubParkCheck(@RequestParam Map<String, Object> requestMap, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		int checkNum = parkService.pubParkCheck(requestMap);
		if(checkNum == 0) {
			resultMap.put("msg", "OK");
		}else {
			resultMap.put("msg", "NO");
		}
		
		return resultMap;
	}
	
}
