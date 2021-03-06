package com.dwict.jfmc.client.rent.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.print.attribute.HashPrintJobAttributeSet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dwict.jfmc.client.board.mapper.BoardMapper;
import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;
import com.dwict.jfmc.client.lecture.model.TrainClass;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.park.mapper.ParkMapper;
import com.dwict.jfmc.client.rent.mapper.RentMapper;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;
import com.dwict.jfmc.client.smpay.service.PayService;

@Service("rentService")
public class RentServiceImpl implements RentService {

	@Resource(name = "payService")
	private PayService payService;
	
	@Resource(name = "rentMapper")
	private RentMapper mapper;
	
	@Resource(name = "rentService")
	RentService rentService;
	
	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "mypageService")
	private MypageService mypgService;
	
	@Resource(name = "boardMapper")
	private BoardMapper boardMapper;
	
	@Resource(name="parkMapper")
	private ParkMapper parkMapper;
	
	@Value("#{appConfig['smpayPG.mode']}")
	private String PG_MODE; //1:실제, 0:테스트
	
	
	@Value("#{appConfig['Globals.fileUploadStorePath']}")
	private String storePath;
	
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
			
			String teamSEQ = (String) requestMap.get("teamSEQ");
			if (teamSEQ.equals("") || teamSEQ == null) {
			int teamCnt = mapper.teamCnt(requestMap);
				if(teamCnt > 0) {
					return 3;
				}else {
					//팀정보 저장
					mapper.teamSave(requestMap);
				}
			} else {
				//팀정보 저장
				mapper.teamUpdate(requestMap);
			}
			
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
	  			strMap.put("hp", tmpStr[2]);
	  			strMap.put("mem_addr", tmpStr[3]);
	  			strMap.put("mem_addr2", tmpStr[4]);
	  			strMap.put("teamSEQ", teamSEQ);
				System.out.println("sssssssssss:"+ strMap);
				mapper.teamMemSave(strMap);
			}
			
			
		} catch (final Exception ex)
		{
			return 0;
		}
		
		return 1;
	}
	
	/*
	//팀  신청 수정
	@Override
	public int teamUpdate(Map<String, Object> requestMap, HttpServletRequest request) {

		System.out.println(requestMap);

		try {

			String teamSEQ = (String) requestMap.get("teamSEQ");
			if (teamSEQ == "") {
				//팀정보 저장
				mapper.teamSave(requestMap);
			} else {
				//팀정보 저장
				mapper.teamUpdate(requestMap);
			}
			
			

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
	*/
		
	//대관 환경설정값
	@Override
	public Map<String, Object> rentConfig(HttpServletRequest request) {
		
		String val6 = (String) request.getAttribute("val6");
		String param = "";
		String PLACE_CD = (String)request.getAttribute("val4");
		String[] arrParam = new String[3];
		Map <String,Object> maps= new HashMap<>();
		if(StringUtils.isNotBlank(val6)) {
			maps.put("PLACE_CD",PLACE_CD);
		}else {
			param = request.getParameter("q");
			param = (param == null) ? "0/0/0/" : param;
			param = ((param.contains("/"))) ? param : param +"/0" ;
			
			arrParam = param.split("\\/"); 
			
			
			maps.put("PLACE_CD", arrParam[0]);
			maps.put("PLACE_TAB", arrParam[1]);
		}
		

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
				rentYN += ",Y"; //선착순 예약완료 출력용 
			} else {
				rentYN += ",N"; //이미 예약됨 출력용
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
		
		String RENT_DATE = (String) requestMap.get("RENT_DATE");
		String STIME = requestMap.get("STIME").toString();
		STIME = (Integer.parseInt(STIME) > 9) ? STIME : "0"+STIME;
		String ETIME = (String) requestMap.get("ETIME");
		ETIME = (Integer.parseInt(ETIME) > 9) ? ETIME : "0"+ETIME;
		
		String INWON = (String) requestMap.get("INWON");
 		String START_DATE = RENT_DATE + " " + STIME + ":00";
		String END_DATE = RENT_DATE + " " + ETIME + ":00";
		
		RENT_DATE = RENT_DATE.replace("-", "");
		
		requestMap.put("PART_CD", "00");
		requestMap.put("COM_NM", "");
		requestMap.put("TIME_SEQ", 0);
		requestMap.put("INWON", INWON + "명");
		requestMap.put("RESERVE_DATE", RENT_DATE);
		requestMap.put("SDATE", STIME + ":00");
		requestMap.put("START_DATE", START_DATE);
		requestMap.put("EDATE", ETIME + ":00");
		requestMap.put("END_DATE", END_DATE);
		requestMap.put("APP_TYPE", "10");
		
		//return mapper.rentWriteSave(requestMap);
 		return mapper.rentSave(requestMap);
	}
	
	//대관 예약 확인
	@Override
	public Map<String, Object> rentOrder(String MEM_NO, HttpServletRequest request) {
		
		
		Map <String , Object > maps = new HashMap<>();
		String val6 = (String) request.getAttribute("val6");
		String val1 = "";
		String val2 = "";
		String RESERVE_DATE = "";
		String PLACE_CD = "";
		String COMCD = "";
		int reserveDate = 0;
		if(StringUtils.isNotBlank(val6)) {
			val1 = (String)request.getAttribute("val1");
			val1 = val1.substring(1,val1.length());
			val2 = (String)request.getAttribute("val2");
			val2 = val2.substring(1,val2.length());
			reserveDate = (int) request.getAttribute("val3");
			RESERVE_DATE = Integer.toString(reserveDate);
			PLACE_CD = (String)request.getAttribute("val4");
			COMCD = (String)request.getAttribute("val5");
			
		}else { 
			val1 = request.getParameter("val1");
			val1 = val1.substring(1,val1.length());
			val2 = request.getParameter("val2");
			val2 = val2.substring(1,val2.length());
			RESERVE_DATE = request.getParameter("val3");
			PLACE_CD = request.getParameter("val4");
			COMCD = request.getParameter("val5");
		}
		
		
		String yoil ="";
		try {
			yoil = FormatUtil.getDateYoil(RESERVE_DATE, "yyyyMMdd");
			maps.put("yoil", yoil); //해당일자 요일
		} catch (Exception e) {
			yoil = "";
		}
		
		//사업장별 PG결제 키값 정보불러오기 
		Map<String, Object> maps2 = new HashMap<>();
		maps.put("COMCD", COMCD);
		maps2 = payService.payKeyInfo(maps);
		String  merchantKey = (String) maps2.get("KEY");
		String  storeMID = (String) maps2.get("MID");
		
		if(StringUtils.isNotBlank(val6)) {
			final String[] brdNoArr = val1.split(",");
			maps.put("brdNoList", brdNoArr);
		}else {
			final String[] brdNoArr =  request.getParameter("val1").split(",");
			maps.put("brdNoList", brdNoArr);
		}
		
		
		
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
		

		/*
		//다시 
		for (int ii = 0 ; ii < rtnMap.size(); ii++) {
			
			
			
			
		}
		*/
		

		maps.put("dataList", rtnMap);
		return maps;
	}

	//축구장 야구장 단가표
	@Override
	public List <Map <String, Object>> rentPriceList(String MEM_NO, HttpServletRequest request) {

		String val6 = (String) request.getAttribute("val6");
		String val1 = "";
		String val2 = "";
		String RESERVE_DATE = "";
		String PLACE_CD = "";
		String COMCD = "";
		int reserveDate = 0;
		if(StringUtils.isNotBlank(val6)) {
			val1 = (String)request.getAttribute("val1");
			val1 = val1.substring(1,val1.length());
			val2 = (String)request.getAttribute("val2");
			val2 = val2.substring(1,val2.length());
			reserveDate = (int) request.getAttribute("val3");
			RESERVE_DATE = Integer.toString(reserveDate);
			PLACE_CD = (String)request.getAttribute("val4");
			COMCD = (String)request.getAttribute("val5");
			
		}else { 
			val1 = request.getParameter("val1");
			val1 = val1.substring(1,val1.length());
			val2 = request.getParameter("val2");
			val2 = val2.substring(1,val2.length());
			RESERVE_DATE = request.getParameter("val3");
			PLACE_CD = request.getParameter("val4");
			COMCD = request.getParameter("val5");
		}
		
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		//결제전 세션 저장
		memberService.memSession(request, MEM_ID);
		//셋팅 값
		final Map <String,Object> rentCfg = rentService.rentConfig(request);
		
		//마이 팀 리스트에서 관내 관외 가져와서 관외면 in_area = 1 삽입 관내면 
		List<Map <String,Object>> myTeamList = rentService.getMyTeamList(MEM_ID, rentCfg);
		
		String inArea = "";
		if(myTeamList.get(0).get("TM_TYPE").equals("1")) {
			inArea = "1";
		} else if (myTeamList.get(0).get("TM_TYPE").equals("2")) {
			inArea = "0";
		}
		
		Map <String , Object > maps = new HashMap<>();
		
		String yoil ="";
		try {
			yoil = FormatUtil.getDateYoil(RESERVE_DATE, "yyyyMMdd");
			maps.put("yoil", yoil); //해당일자 요일
		} catch (Exception e) {
			yoil = "";
		}
		
		if(yoil.equals("토") || yoil.equals("일")) {
			maps.put("days" , "0");
		} else {
			maps.put("days" , "1");
		}
		
		if(StringUtils.isNotBlank(val6)) {
			final String[] brdNoArr = val1.split(",");
			maps.put("brdNoList", brdNoArr);
		}else {
			final String[] brdNoArr =  request.getParameter("val1").split(",");
			maps.put("brdNoList", brdNoArr);
		}
		
		maps.put("in_area", inArea); //1:관내거주, 0:관외/타지역
		//maps.put("person", "1"); //1:일반인 , 0:장애인
		maps.put("PLACE_CD", PLACE_CD);
		maps.put("MEM_NO", MEM_NO);
		maps.put("rtn_idx", val1); //예약 idx
		maps.put("rtn_YN", val2); 	//예약결과 
		maps.put("RESERVE_DATE", RESERVE_DATE);
		
		List <Map <String , Object>> rtnMap = mapper.rentOdList(maps);
		
		int playTime = 0;
		
		if (rtnMap.size() != 0) {
			playTime = rtnMap.size();
			maps.put("playtime", playTime);
		}
		
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
/*	@Override
	public Map <String, Object> getTeam() {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
				
		Map <String , Object > maps = new HashMap<>();
		maps.put("MEM_ID", MEM_ID);
		return mapper.getTeamData(maps);
	}*/
	
	//팀 정보 가져오기
	@Override
	public List<Map<String, Object>>  getTeamList() {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
				
		Map <String , Object > maps = new HashMap<>();
		maps.put("MEM_ID", MEM_ID);
		return mapper.getTeamList(maps);
	}
	
	//선택 팀 정보 가져오기
	@Override
	public Map <String, Object> getTeam(String SEQ) {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
				
		Map <String , Object > maps = new HashMap<>();
		maps.put("MEM_ID", MEM_ID);
		maps.put("SEQ", SEQ);
		return mapper.getTeamData(maps);
	}
	
	//팀 회원 정보
	@Override
	public List <Map <String, Object>> getTeamMemberList(String SEQ) {

		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
				
		Map <String , Object > maps = new HashMap<>();
		maps.put("MEM_ID", MEM_ID);
		maps.put("SEQ", SEQ);
		return  mapper.getTeamMemberList(maps);
	}
	
	//팀별 회원수
	@Override
	public int teamCount(String MEM_ID, Map <String,Object> maps) {
		maps.put("MEM_ID", MEM_ID);
		return mapper.teamCount(maps);
	}

	@Override
	public void pubRentApplyWrite(Map<String, Object> param) throws Exception {
		final List<MultipartFile> fileList = (List<MultipartFile>) param.get("fileList");
		parkMapper.pubParkApplyWrite(param);
		int index = 1;		
		if (!fileList.isEmpty()) {
			final File saveFolder = new File(storePath);
			if (!saveFolder.exists() || saveFolder.isFile()) {
				saveFolder.mkdirs();
			}								
			for(final MultipartFile mf : fileList) {
				final String originFileName = mf.getOriginalFilename();
				final String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());				
				final String reFileName = "PPK" + "_" + UUID.randomUUID().toString().replaceAll("-", "") + extName;
				final String saveFile = storePath + reFileName;
				mf.transferTo(new File(saveFile));
				param.put("attachNo", index);
				param.put("attachId", param.get("attach_id"));
				param.put("fileNm", originFileName);
				param.put("virFileNm", reFileName);
				param.put("fileExtsn", extName.replace(".", ""));
				
				boardMapper.saveAttach(param);
				index++;
			}
		}
 	}

	@Override
	public List<Map<String, Object>> rentSportsRentList(HttpServletRequest request) {
		return mapper.rentSportsRentList();
	}

	@Override
	public List<Map<String, Object>> getMyTeamList(String mEM_ID, Map<String, Object> maps) {
		maps.put("MEM_ID", mEM_ID);
		return mapper.getMyTeamList(maps);
	}

	@Override
	public void updateRtnAppType() {
		
		mapper.updateRtnAppType();
		
		return;
		
	}

	@Override
	public Map<String, Object> rentSave2(Map<String, Object> requestMap, HttpServletRequest request) {
		
		String val1 = request.getParameter("val1");
		String val2 = request.getParameter("val2");
		String val3 = request.getParameter("val3");
		
		val3 = val3.replace("-", "");
		String[] brdNoArr =  {val1, val2};
		
		Map <String , Object > maps = new HashMap<>();
		maps.put("reserveDate", val3);
		maps.put("brdNoList", brdNoArr);
		
		mapper.updateRentApp(maps);
		
		return maps;
	}

	@Override
	public Map<String, Object> rentCancelSave(Map<String, Object> requestMap) {	
		mapper.rentCancelSave(requestMap);
		
		return requestMap;
		
	}

	@Override
	public void cancelAppType(Map<String, Object> requestMap) {
		mapper.cancelAppType(requestMap);
	}

	@Override
	public void updateTennisAppType() {
		
		mapper.updateTennisAppType();
		
		return;
		
	}

	@Override
	public String nextAttachId(Map<String, Object> param) throws Exception {
		final List<MultipartFile> fileList = (List<MultipartFile>) param.get("fileList");
		
		Map<String, Object> list = mapper.nextAttachId(param);
		String attach_id = list.get("attach_id").toString();
		
		int index = 1;		
		if (!fileList.isEmpty()) {
			final File saveFolder = new File(storePath);
			if (!saveFolder.exists() || saveFolder.isFile()) {
				saveFolder.mkdirs();
			}								
			for(final MultipartFile mf : fileList) {
				final String originFileName = mf.getOriginalFilename();
				final String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());				
				final String reFileName = "PPK" + "_" + UUID.randomUUID().toString().replaceAll("-", "") + extName;
				final String saveFile = storePath + reFileName;
				mf.transferTo(new File(saveFile));
				param.put("attachNo", index);
				param.put("attachId", attach_id);
				param.put("fileNm", originFileName);
				param.put("virFileNm", reFileName);
				param.put("fileExtsn", extName.replace(".", ""));
				
				boardMapper.saveAttach(param);
				index++;
			}
		}
		return attach_id;
	}
	

}
