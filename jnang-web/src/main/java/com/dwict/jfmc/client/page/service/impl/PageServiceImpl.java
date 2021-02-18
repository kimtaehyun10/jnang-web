package com.dwict.jfmc.client.page.service.impl;

import java.util.List;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.page.mapper.PageMapper;
import com.dwict.jfmc.client.page.model.Cont;
import com.dwict.jfmc.client.page.model.Menu;
import com.dwict.jfmc.client.page.service.PageService;
import com.dwict.jfmc.client.rent.mapper.RentMapper;
import com.dwict.jfmc.client.smpay.service.PayService;

@Service("pageService")
public class PageServiceImpl implements PageService {

	@Resource(name = "payService")
	private PayService payService;
		
	@Resource(name = "pageMapper")
	private PageMapper mapper;

	@Resource(name = "rentMapper")
	private RentMapper rentMapper;


	@Override
	public Cont htmlPage(String cmsCd) {
		return mapper.htmlPage(cmsCd);
	}

	@Override
	public Cont empPage(String cmsCd) {
		final Cont vo = mapper.empPage(cmsCd);
		if(vo.getChild() != 0) vo.setChilds(mapper.empPageChilds(cmsCd));
		return vo;
	}

	@Override
	public Menu boardPage(String cmsCd) {
		return mapper.boardPage(cmsCd);
	}


	@Override
	// 날짜에 관련된 달력정보를 가지는 메서드
	public Map<String, Integer> today_info(Map<String, Object> dateMap) {
		// 날짜 캘린더 함수에 삽입.
		Map<String, Integer> today_Data = new HashMap<String, Integer>();
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt((String) dateMap.get("yy")), Integer.parseInt((String) dateMap.get("mm")), 1);

		int startDay = cal.getMinimum(java.util.Calendar.DATE);
		int endDay = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		int start = cal.get(java.util.Calendar.DAY_OF_WEEK);
		
		Calendar todayCal = Calendar.getInstance();
		SimpleDateFormat ysdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat msdf = new SimpleDateFormat("M");

		int today_year = Integer.parseInt(ysdf.format(todayCal.getTime()));
		int today_month = Integer.parseInt(msdf.format(todayCal.getTime()));

		int search_year = Integer.parseInt((String) dateMap.get("yy"));
		int search_month = Integer.parseInt((String) dateMap.get("mm")) + 1;

		int today = -1;
		if (today_year == search_year && today_month == search_month) {
			SimpleDateFormat dsdf = new SimpleDateFormat("dd");
			today = Integer.parseInt(dsdf.format(todayCal.getTime()));
		}
		
		search_month = search_month-1; 
		
		Map<String, Integer> before_after_calendar = before_after_calendar(search_year,search_month);
		
		//날짜 관련
		System.out.println("search_month : " + search_month);
		
		// 캘린더 함수 end
		//today_Data.put("yy", (Integer) dateMap.get("yy"));
		//today_Data.put("mm", (Integer) dateMap.get("mm"));
		today_Data.put("start", start);
		today_Data.put("startDay", startDay);
		today_Data.put("endDay", endDay);
		today_Data.put("today", today);
		today_Data.put("search_year", search_year);
		today_Data.put("search_month", search_month+1);
		today_Data.put("before_year", before_after_calendar.get("before_year"));
		today_Data.put("before_month", before_after_calendar.get("before_month"));
		today_Data.put("after_year", before_after_calendar.get("after_year"));
		today_Data.put("after_month", before_after_calendar.get("after_month"));
		return today_Data;
	}
	
	
	//이전달 다음달 및 이전년도 다음년도
	private Map<String, Integer> before_after_calendar(int search_year, int search_month){
		Map<String, Integer> before_after_data = new HashMap<String, Integer>();
		int before_year = search_year;
		int before_month = search_month-1;
		int after_year = search_year;
		int after_month = search_month+1;

		if(before_month<0){
			before_month=11;
			before_year=search_year-1;
		}
		
		if(after_month>11){
			after_month=0;
			after_year=search_year+1;
		}
		
		before_after_data.put("before_year", before_year);
		before_after_data.put("before_month", before_month);
		before_after_data.put("after_year", after_year);
		before_after_data.put("after_month", after_month);
		
		return before_after_data;
	}
	
	@Override
	// 날짜에 관련된 달력정보를 가지는 메서드
	public List<Map <String,Object>> dateList(Map<String, Integer> today_info) {
		

		System.out.println(today_info);
		//{yy=1, mm=2, after_year=1, before_year=1, startDay=1, before_month=1, endDay=31, today=-1, after_month=3, start=3, search_year=1, search_month=3}
		//List<Map<String, Object>> dateList = new ArrayList<>();
		List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();
		//실질적인 달력 데이터 리스트에 데이터 삽입 시작.
		//일단 시작 인덱스까지 아무것도 없는 데이터 삽입
		Map<String, Object> addMap;
		/*
		 for(int i=1; i< today_info.get("start"); i++){
			addMap = new HashMap<String, Object>();
			addMap.put("year", null);
			addMap.put("month", null);
			addMap.put("date", null);
			addMap.put("value", null);
			addMap.put("schedule", null);
			addMap.put("schedule_detail", null);
			dateList.add(addMap);
		}
		*/
		
		//날짜 삽입
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {
			if(i== today_info.get("today")){
				addMap = new HashMap<String, Object>();
				addMap.put("year", String.valueOf(today_info.get("yy")));
				addMap.put("month",  String.valueOf(today_info.get("mm")));
				addMap.put("date",  String.valueOf(i));
				addMap.put("value", "today");
				//addMap.put("schedule", null);
				//addMap.put("schedule_detail", null);
			}else{

				addMap = new HashMap<String, Object>();
				addMap.put("year", String.valueOf(today_info.get("yy")));
				addMap.put("month",  String.valueOf(today_info.get("mm")));
				addMap.put("date",  String.valueOf(i));
				addMap.put("value", "normal_date");
				//addMap.put("schedule", null);
				//addMap.put("schedule_detail", null);
			}
			dateList.add(addMap);
			System.out.println(dateList);
		}
		
		System.out.println("sssssssssssssssss:"+dateList);
		

		//달력 빈곳 빈 데이터로 삽입
		int index = 7-dateList.size()%7;
		
		if(dateList.size()%7!=0){
			
			for (int i = 0; i < index; i++) {
				addMap = new HashMap<String, Object>();
				addMap.put("year", null);
				addMap.put("month", null);
				addMap.put("date", null);
				addMap.put("value", null);
				addMap.put("schedule", null);
				addMap.put("schedule_detail", null);
				dateList.add(i,addMap);
				System.out.println(addMap);
			}
			
		}
		
		
		return dateList;
	}
	
	//rent list
	@Override
	public List<Map<String, Object>> getRentList(Map<String, Object> requestMap,HttpServletRequest request) {
		
		
		String yy = (String) request.getParameter("yy");
		String mm = (String) request.getParameter("mm");
		
		
		//접수시 사용
		String ymd = (String) request.getParameter("ymd"); //예약 선택일
		ymd = (ymd == null) ? "":ymd;
		String param = request.getParameter("q");	//PLACE_CD / PLACE_TAB
		param = (param == null) ? "0/0/0/" : param;
		param = ((param.contains("/"))) ? param : param +"/0" ;
		
		String [] arrParam = param.split("\\/");
		
		if (yy == null || mm == null) {
			Date from = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMM");
			String today = transFormat.format(from);
			yy = today.substring(0,4);
			mm = today.substring(4);
		}
		mm = (mm.length() == 1) ? "0"+mm : mm;
		
		if (ymd.length() == 8) { 
			requestMap.put("YYMMDD", ymd); //현재달 전체 출력용
		} else {
			requestMap.put("YYMM", yy + mm); //현재달 전체 출력용
		}

		requestMap.put("PLACE_CD", arrParam[0]);
		requestMap.put("PLACE_TAB", arrParam[1]);
		
		return rentMapper.getRentList(requestMap);
	}
	
	
	@Override
	public List<Map<String, Object>> getRentCalendar(Map<String, Object> requestMap,HttpServletRequest request) {
		
		
		String yy = (String) request.getParameter("yy");
		String mm = (String) request.getParameter("mm");
		
		
		//접수시 사용
		String ymd = (String) request.getParameter("ymd"); //예약 선택일
		ymd = (ymd == null) ? "":ymd;
		String param = request.getParameter("q");	//PLACE_CD / PLACE_TAB
		param = (param == null) ? "0/0/0/" : param;
		param = ((param.contains("/"))) ? param : param +"/0" ;		
		String [] arrParam = param.split("\\/");
		
		if (yy == null || mm == null) {
			Date from = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMM");
			String today = transFormat.format(from);
			yy = today.substring(0,4);
			mm = today.substring(4);
		}
		mm = (mm.length() == 1) ? "0"+mm : mm;
		
		if (ymd.length() == 8) { 
			requestMap.put("YYMMDD", ymd); //현재달 전체 출력용
		} else {
			requestMap.put("YYMM", yy + mm); //현재달 전체 출력용
		}

		requestMap.put("PLACE_CD", arrParam[0]);
		requestMap.put("PLACE_TAB", arrParam[1]);
		//지난 시간 삭제
		rentMapper.rentNoPayClear(requestMap);
		
		return rentMapper.getRentCalendar(requestMap);
	}
	
	//센터별 대관 그룹 가져오기(대강당 ,소강당, 강의실)
	@Override
	public List<Map<String, Object>> getCenterGroup(Map<String, Object> requestMap, HttpServletRequest request) {
		
		String param = request.getParameter("q");	//PLACE_CD / PLACE_TAB
		param = (param == null) ? "0/0/0/" : param;
		param = ((param.contains("/"))) ? param : param +"/0" ;
		String [] arrParam = param.split("\\/");
		
		requestMap.put("PLACE_CD", arrParam[0]);
		return rentMapper.getCenterGroup(requestMap);
	}
	
	//대관 일정 리스트
	@Override
	public List<Map<String, Object>> getPlaceTimeTabList(Map<String, Object> requestMap) {	
		return rentMapper.getPlaceTimeTabList(requestMap);
	}
	
	//대관 일정 리스트
	@Override
	public List<Map<String, Object>> getPlaceCalendarTab(Map<String, Object> requestMap) {	
		return rentMapper.getPlaceCalendarTab(requestMap);
	}
	
	
	//할인 변경시 금액 변경으로  EncryptData값 받기
	@Override
	public Map <String, Object> getOdEncryptData(Map<String, Object> requestMap ) {
		
		String ediDate = (String) requestMap.get("ediDate");
		String goodsAmt = (String) requestMap.get("goodsAmt");
		String COMCD = (String) requestMap.get("COMCD");
		String enGoodsName = "";
		String enBuyerName = "";
		try {
			String GoodsName = (String) requestMap.get("GoodsName");
			if (GoodsName != null &&  GoodsName != "") {
				enGoodsName = URLEncoder.encode(GoodsName, "EUC-KR");
			}
			
			String BuyerName = (String) requestMap.get("BuyerName");
			if (BuyerName != null &&  BuyerName != "") {
				enBuyerName = URLEncoder.encode(BuyerName, "EUC-KR");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//사업장별 PG결제 키값 정보불러오기 
		Map<String, Object> maps = new HashMap<>();
		maps = payService.payKeyInfo(requestMap); 
		String  merchantKey = (String) maps.get("KEY");
		String  storeMID = (String) maps.get("MID");
		
		
		final String EncryptData = FormatUtil.encodeMD5HexBase64(ediDate + storeMID + goodsAmt + merchantKey);
		maps.put("EncryptData",EncryptData);
		maps.put("enGoodsName",enGoodsName);
		maps.put("enBuyerName",enBuyerName);
		
		return maps;
	}
	

}
