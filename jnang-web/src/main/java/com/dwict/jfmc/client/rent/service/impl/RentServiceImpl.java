package com.dwict.jfmc.client.rent.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.print.attribute.HashPrintJobAttributeSet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;
import com.dwict.jfmc.client.lecture.model.TrainClass;
import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.rent.mapper.RentMapper;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;

@Service("rentService")
public class RentServiceImpl implements RentService {

	@Resource(name = "rentMapper")
	private RentMapper mapper;

	@Resource(name = "mypageService")
	private MypageService mypgService;
	
	@Value("#{appConfig['smpay.merchant.key']}")
	private String merchantKey;
	
	@Value("#{appConfig['smpay.mid.key']}")
	private String storeMID;
	
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
			
			

		  	//삭제 팀회원 삭제
			final String[] brdNoArr = ((String) requestMap.get("arryDel")).split(",");
			requestMap.put("brdNoList", brdNoArr);
		  	mapper.teamMemDel(requestMap);
		  	
			String strData =  (String) requestMap.get("arryData");//주문상품 정보
		  	System.out.println("sssssssssss:"+ strData);
		  	String [] arrayData = strData.split("//");
		  	int xxx = arrayData.length;
		  	
	  	
		  	//팀 회원 등록
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
		param = ((param.contains("/"))) ? param : param +"/0" ;
		
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
		obj.put("PLACE_TAB", arrParam[1]);
	
		
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


	//대관 신청 저장  ###################################################################################
	@Override
	@Transactional
	public Map<String, Object> rentSave(Map<String, Object> requestMap, HttpServletRequest request) {

		//예약일
		String RESERVE_DATE = request.getParameter("ymd");
		//선택된 배열 저장값
		String [] arrayTmp = request.getParameterValues("tseq"); 

		//대관 값 가져오기
		Map<String, Object> maps = rentConfig(request);
		
		
		try {
			final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			final String MEM_ID = account.getUsername();
			final String MEM_NO = account.getMemNo();
			final String MEM_NM = account.getMemNm();

			maps.put("MEM_NM", MEM_NM);
			maps.put("MEM_NO", MEM_NO);	
			
		} catch (final Exception ex)
		{
			return null ;
		}
		
		
		maps.put("COM_NM", "");
		maps.put("TEL", "");
		maps.put("RESERVE_DATE", RESERVE_DATE); //예약일

		int rtn = 1;
		Map <String, Object> rtnMap = new HashMap<String, Object>();
		String rentIdx="", rentYN = "";
		for (int ii = 0 ; ii < arrayTmp.length; ii++) {
			
			maps.put("TIME_SEQ", arrayTmp[ii]);
			rentIdx += ","+arrayTmp[ii];
			
			//중복저장 첵크
			int dbl_idx = mapper.rentDblchk(maps);
			//System.out.println("dbl_idx:"+ dbl_idx);
			if (dbl_idx ==0) {
				//저장
				rtn =  mapper.rentSave(maps);
				rentYN += ",Y";
			} else {
				rentYN += ",N";
			}
			
		}
	
		rtnMap.put("rentIdx", rentIdx);
		rtnMap.put("rentYN", rentYN);
		return rtnMap;
	}

	
	//대관 문의 저장 ###################################################################################
	@Override
	public int writeSave(Map<String, Object> requestMap) {
		String param = (String) requestMap.get("q");
		param = (param == null) ? "0/0/0/" : param;
		param = ((param.contains("/"))) ? param : param +"/0" ;
		
		String [] arrParam = param.split("\\/"); 
		
		requestMap.put("PLACE_CD", arrParam[0]);
		requestMap.put("PLACE_TAB", arrParam[1]);
		requestMap.put("COMCD", arrParam[2]);
		
		String RENT_DATE = (String) requestMap.get("RENT_DATE");
		RENT_DATE = RENT_DATE.replace("-", "");
		String STIME = requestMap.get("STIME").toString();
		STIME = (Integer.parseInt(STIME) > 9) ? STIME : "0"+STIME;
		String ETIME = (String) requestMap.get("ETIME");
		ETIME = (Integer.parseInt(ETIME) > 9) ? ETIME : "0"+ETIME;
		
		
		
		requestMap.put("PART_CD", "00");
		requestMap.put("COM_NM", "");
		requestMap.put("TIME_SEQ", 0);
		requestMap.put("RESERVE_DATE", RENT_DATE);
		requestMap.put("SDATE", STIME + ":00");
		requestMap.put("EDATE", ETIME + ":00");
		requestMap.put("APP_TYPE", "10");
		
		
		//return mapper.rentWriteSave(requestMap);
		return mapper.rentSave(requestMap);
	}
	
	//대관 예약 확인
	@Override
	public Map<String, Object> rentOrder(String MEM_NO, HttpServletRequest request) {
		
		
		Map <String , Object > maps = new HashMap<>();
		
		String val1 = request.getParameter("val1");
		val1 = val1.substring(1,val1.length());
		String val2 = request.getParameter("val2");
		val2 = val2.substring(1,val2.length());
		String RESERVE_DATE = request.getParameter("val3");
		String PLACE_CD = request.getParameter("val4");
		String yoil ="";
		try {
			yoil = FormatUtil.getDateYoil(RESERVE_DATE, "yyyyMMdd");
			maps.put("yoil", yoil); //해당일자 요일
		} catch (Exception e) {
			yoil = "";
		}
		
		
		final String[] brdNoArr =  request.getParameter("val1").split(",");
		maps.put("brdNoList", brdNoArr);
		
		maps.put("rtn_idx", val1); //예약 idx
		maps.put("rtn_YN", val2); 	//예약결과 
		maps.put("RESERVE_DATE", RESERVE_DATE);
		maps.put("PLACE_CD", PLACE_CD);
		maps.put("MEM_NO", MEM_NO);
		maps.put("merchantKey", merchantKey);
		maps.put("storeMID", storeMID);
		
		//주문정보 가져오기
		List <Map <String , Object>> rtnMap = mapper.rentOdList(maps);
		//rtnMap.add(maps);
		

		
		//다시 
		for (int ii = 0 ; ii < rtnMap.size(); ii++) {
			
			
			
			
		}
		
		

		maps.put("dataList", rtnMap);
		return maps;
	}

	//축구장 야구장 단가표
	@Override
	public List <Map <String, Object>> rentPriceList(String MEM_NO, HttpServletRequest request) {

		String val1 = request.getParameter("val1");
		String val2 = request.getParameter("val2");
		
		
		Map <String , Object > maps = new HashMap<>();
		maps.put("in_area", "1"); //1:관내거주, 0:관외/타지역
		maps.put("person", "1"); //1:일반인 , 0:장애인
		return mapper.rentPriceList(maps);
	}
	
	//대관 신청 저장
	@Override
	public Map<String, Object> rentOdPay(HttpServletRequest request) {
		
		String val1 = request.getParameter("val1");
		String val2 = request.getParameter("val2");
		
		Map <String , Object > maps = new HashMap<>();
		maps = mapper.rentOdPay(maps);
		
		return null;
	}

	
	//팀 정보 가져오기
	@Override
	public Map <String, Object> getTeam() {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
				
		Map <String , Object > maps = new HashMap<>();
		maps.put("MEM_ID", MEM_ID);
		return mapper.getTeamData(maps);
	}
	//팀 회원 정보
	@Override
	public List <Map <String, Object>> getTeamMemberList() {

		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
				
		Map <String , Object > maps = new HashMap<>();
		maps.put("MEM_ID", MEM_ID);
		return  mapper.getTeamMemberList(maps);
	}
	
	//팀별 회원수
	@Override
	public int teamCount(String MEM_ID) {
		Map<String, Object> param = new HashMap<>();
		param.put("MEM_ID", MEM_ID);
		return mapper.teamCount(param);
	}
	

}
