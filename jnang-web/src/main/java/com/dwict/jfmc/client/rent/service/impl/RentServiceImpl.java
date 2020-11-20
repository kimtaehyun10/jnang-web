package com.dwict.jfmc.client.rent.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;
import com.dwict.jfmc.client.lecture.model.TrainClass;
import com.dwict.jfmc.client.rent.mapper.RentMapper;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;

@Service("rentService")
public class RentServiceImpl implements RentService {

	@Resource(name = "rentMapper")
	private RentMapper mapper;

	@Override
	public List<ComInfo> conditionSb1() {
		return mapper.conditionSb1();
	}

	@Override
	public List<Grpcd> conditionSb2(Grpcd grpcd) {
		return mapper.conditionSb2(grpcd);
	}

	@Override
	public List<Grpcd> conditionSb3(Grpcd grpcd) {
		return mapper.conditionSb3(grpcd);
	}

	@Override
	public Paging lectureList(Map<String, Object> param) {
		final Paging obj = new Paging();
		obj.handlePaging((Integer) param.get("pageIndex"), (Integer) param.get("pageSize"));
		param.put("startRow", obj.getStartRow());
		param.put("pageSize", obj.getPageSize());
		final List<TrainClass> resultList = mapper.lectureList(param);
		for(int i=0; i<resultList.size(); i++) {
			resultList.get(i).setProgramItem(mapper.lectureProgramList(resultList.get(i)));
		}
		obj.handlePagingList(resultList, mapper.lectureListCnt(param));
		return obj;
	}

	@Override
	public ProgramItem getProgramInfo(ProgramItem programItem) {
		return mapper.getProgramInfo(programItem);
	}

	//종목별 대관 센터  정보 가져오기
	@Override
	public List<Map<String, Object>> rentGroupList(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		param.put("PLACE_GROUP", request.getParameter("sp_type"));
		return mapper.rentGroupList(param);
	}
	
	//종목별 대관 센터  리스트 가져오기
	@Override
	public List<Map<String, Object>> rentList(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<>();
		param.put("PLACE_GROUP", request.getParameter("ct1"));
		param.put("COMCD", request.getParameter("ct2"));
		return mapper.rentList(param);
	}
	
	
	//팀  신청 저장
	@Override
	public int teamSave(Map<String, Object> requestMap, HttpServletRequest request) {

		System.out.println(requestMap);

		try {

			//팀정보 저장
			mapper.teamSave(requestMap);
			
			String strData =  (String) requestMap.get("arryData");//주문상품 정보
		  	System.out.println("sssssssssss:"+ strData);
		  	String [] arrayData = strData.split("//");
		  	int xxx = arrayData.length;
		  	
	  		for (int ii = 0; ii < xxx; ii++) {
	  			Map <String, Object> strMap = new HashMap<>();
	  			String arStr =  arrayData[ii] +" | | | ";
	  			String[] tmpStr = arStr.split("\\|");
	  			
	  			strMap.put("mem_id", requestMap.get("mem_id"));
	  			strMap.put("mem_nm", tmpStr[0]);
	  			strMap.put("mem_birth", tmpStr[1]);
	  			strMap.put("mem_addr", tmpStr[2]);
	  			strMap.put("mem_addr2", tmpStr[3]);
				System.out.println("sssssssssss:"+ strMap);
				mapper.teamMemSave(strMap);
			}
			
			
		} catch (final Exception ex)
		{
			return 0;
		}
		
		return 1;
	}
	

	//대관 환경설정값
	@Override
	public Map<String, Object> rentConfig(HttpServletRequest request) {
		
		String param = request.getParameter("q");
		param = (param == null) ? "0/0/0/" : param;
		String [] arrParam = param.split("\\/"); 
		
		Map <String,Object> maps= new HashMap<>();
		maps.put("PLACE_CD", arrParam[0]);
		maps.put("PLACE_TAB", arrParam[1]);

		Map<String, Object> obj = mapper.rentConfig(maps);
		if (obj == null) 
		{
			return null;
		}
		obj.put("param", param);
	
		
		return obj;
	}	
	

	///대관신청 체육관 정보
	@Override
	public List<Map<String,Object>> getCenterList() {
		return mapper.getCenterList();
	}

	///대관신청 체육관 정보
	@Override
	public List<Map<String,Object>> getCenterPlaceList() {
		return mapper.getCenterPlaceList();
	}	


	//대관 신청 저장
	@Override
	public int rentSave(Map<String, Object> requestMap, HttpServletRequest request) {

		try {
			final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final String MEM_ID = account.getUsername();
			final String MEM_NO = account.getMemNo();
			final String MEM_NM = account.getMemNm();

			requestMap.put("MEM_NM", MEM_NM);
			requestMap.put("MEM_NO", MEM_NO);			
		} catch (final Exception ex)
		{
			return -999;
		}

		try {

			//체육센터/신청code 분리저장 위해서
			final String rentData = (String) requestMap.get("RENT_LIST");
			final String [] arryRent = (rentData +"/////").split("/");
			requestMap.put("COMCD", arryRent[0]);
			requestMap.put("PLACE_CD", arryRent[1]);
			requestMap.put("PART_CD", arryRent[2]);
			requestMap.put("SALE_AMT", arryRent[3]);

		} catch (final Exception ex)
		{
			return 0;
		}
		return mapper.rentSave(requestMap);
	}


}
