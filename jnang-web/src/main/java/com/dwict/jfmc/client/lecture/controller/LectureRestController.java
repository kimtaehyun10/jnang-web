package com.dwict.jfmc.client.lecture.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.com.service.CommonService;
import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;
import com.dwict.jfmc.client.lecture.model.TrainClass;
import com.dwict.jfmc.client.lecture.service.LectureService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
@RequestMapping("/data")
public class LectureRestController {

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "lectureService")
	private LectureService service;

	@GetMapping(value = "/lecture/sb1")
	public List<ComInfo> conditionSb1(HttpServletRequest request) {
		return service.conditionSb1();
	}

	@GetMapping(value = "/lecture/sb2/{comcd}")
	public List<Grpcd> conditionSb2(HttpServletRequest request, Grpcd grpcd, @PathVariable String comcd) {
		grpcd.setComcd(comcd);
		return service.conditionSb2(grpcd);
	}

	@GetMapping(value = "/lecture/sb3/{comcd}/{item1}")
	public List<Grpcd> conditionSb3(HttpServletRequest request, Grpcd grpcd, @PathVariable String comcd, @PathVariable String item1) {
		grpcd.setComcd(comcd);
		grpcd.setItem1(item1);
		return service.conditionSb3(grpcd);
	}

	@GetMapping(value = "/lecture/lectureList")
	public Paging lectureList(TrainClass trainClass, 
			@RequestParam int pageIndex, @RequestParam int pageSize, @RequestParam int startRow) {
		final Map<String, Object> param = new HashMap<>();
		param.put("pageIndex", pageIndex);
		param.put("pageSize", pageSize);
		param.put("startRow", startRow);
		param.put("comcd", trainClass.getComcd());
		param.put("sportsCd", trainClass.getSportsCd());
		param.put("msportsCd", trainClass.getMsportsCd());		
		
		return service.lectureList(param);
	}

	@GetMapping(value = "/lecture/program")
	public ProgramItem getProgramInfo(@RequestParam String secureText, ProgramItem programItem) {
		final String jsonString = commonService.decodeText(secureText);
		final JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
		programItem.setComcd(jsonObject.get("comcd").getAsString());
		programItem.setClassCd(jsonObject.get("classCd").getAsString());
		programItem.setItemCd(jsonObject.get("itemCd").getAsString());
		return service.getProgramInfo(programItem);
	}
	
	//과목 => 장바구니 담기, 잔여수량 첵크
	@GetMapping(value = "/lecture/basketIn")
	public Map<String, Object> setBasket(@RequestParam Map<String, Object> requestMap,ProgramItem programItem) {
		final String sDate = (String) requestMap.get("oderData[sDate]");
		final String eDate = (String) requestMap.get("oderData[eDate]");
		final String step = (String) requestMap.get("oderData[step]");
		final String secureText = (String) requestMap.get("secureText");
		final String jsonString = commonService.decodeText(secureText);
		final JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
		programItem.setComcd(jsonObject.get("comcd").getAsString());
		programItem.setClassCd(jsonObject.get("classCd").getAsString());
		programItem.setItemCd(jsonObject.get("itemCd").getAsString());
		programItem.setTcSdate(sDate); //수강 시작일
		programItem.setTcEdate(eDate); //수강 종료일
		programItem.setStep(step); //진행단계 1:신청리스트단계, 2:결제페이지단계
		
		
		SimpleDateFormat format1 = new SimpleDateFormat ("HH:mm:ss");
		Calendar time = Calendar.getInstance();
		String format_time1 = (format1.format(time.getTime()).substring(0,2));
		
		
		/*
		//시간첵크
		get
		if (userId.equals("anonymousUser") || member == null)
		{
			requestMap.put("code", -9);
			return requestMap;
		}
		*/
		
		return service.setBasket(programItem);
	
	}
	

	//이사장 전용 종목별 검색 종목 출력
	@PostMapping(value = "/lecture/classGroup")
	public List<Map<String, Object>> rentGroupList(HttpServletRequest request) {
		return service.classGroupList(request);
	}
	//이사장 전용 종목별 검색 종목 출력
	@PostMapping(value = "/lecture/classList")
	public List<Map<String, Object>> lecClassList(HttpServletRequest request) {
		return service.lecClassList(request);
	}
	
	
}