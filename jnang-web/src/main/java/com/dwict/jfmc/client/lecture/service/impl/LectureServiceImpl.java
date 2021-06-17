package com.dwict.jfmc.client.lecture.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Convert;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.lecture.mapper.LectureMapper;
import com.dwict.jfmc.client.lecture.model.ComInfo;
import com.dwict.jfmc.client.lecture.model.Grpcd;
import com.dwict.jfmc.client.lecture.model.ProgramItem;
import com.dwict.jfmc.client.lecture.model.TrainClass;
import com.dwict.jfmc.client.lecture.service.LectureService;
import com.dwict.jfmc.client.mem.mapper.MemberMapper;
import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mypage.mapper.MypageMapper;

@Service("lectureService")
public class LectureServiceImpl implements LectureService {


	
	@Resource(name = "lectureMapper")
	private LectureMapper mapper;

	@Resource(name = "memberMapper")
	private MemberMapper memMapper;

	@Resource(name="mypageMapper")
	private MypageMapper mypageMapper;
	
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
		Map<String, Object> maps = new HashMap<>();
		
		//장바구니 오래된 주문 비우기
		mypageMapper.basketClear(maps);
		
		List<TrainClass> resultList = mapper.lectureList(param);
		//final List<TrainClass> resultList = mapper.lectureList(param);
		
		System.out.println(resultList);
		
		for(int i=0; i<resultList.size(); i++) {
			resultList.get(i).setProgramItem(mapper.lectureProgramList(resultList.get(i)));
			resultList.get(i).setGrpcd(mapper.lectureGrpcd(new Grpcd(resultList.get(i).getComcd(), resultList.get(i).getSportsCd())));
		}
		obj.handlePagingList(resultList, mapper.lectureListCnt(param));
		return obj;
	}

	@Override
	public ProgramItem getProgramInfo(ProgramItem programItem) {
		final ProgramItem result = mapper.getProgramInfo(programItem);
		result.setGrpcd(mapper.lectureGrpcd(new Grpcd(result.getComcd(), result.getSportsCd())));
		return result;
	}

	//이사장 전용 종목별 검색 종목 출력
	@Override
	public List<Map<String, Object>> classGroupList(HttpServletRequest request) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("ct1", request.getParameter("ct1"));
		return mapper.classGroupList(param);

	}
	
	//이사장 전용 종목별 검색 종목 출력
	@Override
	public List<Map<String, Object>> lecClassList(HttpServletRequest request) {
		
		Map<String, Object> param = new HashMap<>();
		param.put("ct1", request.getParameter("ct1"));
		return mapper.lecClassList(param);

	}
	
	/*
	 * 장바구니 담기
 	 */
	@Override
	public Map<String, Object> setBasket(ProgramItem programItem) {
		Map<String, Object> resultMap = new HashMap<>();
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		String ITEM_CD 		= programItem.getItemCd();
		String ITEM_SDATE	= programItem.getTcSdate();
		String ITEM_EDATE 	= programItem.getTcEdate();
		String ORDER_STEP	= programItem.getStep(); //주문단계
		
		//회원정보
		Member member = memMapper.findById(userId);

		//없는 회원정보 ,로그인 첵크
		if (userId.equals("anonymousUser") || member == null)
		{
			resultMap.put("code", -9);
			return resultMap;
		}

		//회원카드 없는 회원 체크
		/*if (member.getCardNo() == null || member.getCardNo().equals(""))
		{
			resultMap.put("code", -10);
			return resultMap;
		}*/

		//상품정보 오류
		if (ITEM_CD.length() < 5 || ITEM_SDATE.length() != 8 || ITEM_EDATE.length() != 8)
		{
			resultMap.put("code", 0);
			return resultMap;
		}

		//프로그램 정보 불러오기
		final ProgramItem result = mapper.getProgramInfo(programItem);
		Map<String, Object> basketMap = new HashMap<>();
		basketMap.put("COMCD", result.getComcd());
		basketMap.put("ITEM_NM", result.getItemNm());
		basketMap.put("ITM_TYPE", "lec");
		basketMap.put("CLASS_CD", result.getClassCd());
		basketMap.put("PART_CD", result.getPartCd());
		basketMap.put("SPORTS_CD", result.getSportsCd());
		basketMap.put("ITEM_CD", ITEM_CD);
		basketMap.put("MEM_NO", member.getMemNo());
		basketMap.put("ITEM_SDATE", ITEM_SDATE);
		basketMap.put("ITEM_EDATE", ITEM_EDATE);
		basketMap.put("DC_CD1", "");
		basketMap.put("DC_CD2", "");
		basketMap.put("SALE_AMT", result.getCostAmt());
		basketMap.put("VAT_YN", result.getVatYn());
		basketMap.put("ADD_YMD", FormatUtil.getDefaultDate(2, "",""));
		basketMap.put("WRITE_DH", FormatUtil.getDefaultDate(1, "",""));

		//강좌 잔여 수량 첵크(잔여수량=총할당수량 -주문수량+장바구니수량)
		Map<String, Object> orderMap = mapper.limitCnt(basketMap);
		int limitCnt = 0;
		//결제 단계(STEP 2)에서 수량첵크
		if (ORDER_STEP.equals("2")) {
			//결제 2단계에서 수량첵크(할당수 - 실제결제수)
			int web_capa = Integer.parseInt(String.valueOf(orderMap.get("WEB_CAPA"))); //웹 할당수량
			int order_Cnt = Integer.parseInt(String.valueOf(orderMap.get("orderCnt"))); //결제 완료수량
			limitCnt = web_capa - order_Cnt;			
		} else {
			//신청 1단계에서 수량첵크 (할당수 - (실제결제수 + 장바구니수)
			limitCnt = Integer.parseInt(String.valueOf(orderMap.get("limitCnt")));
		}
		if (limitCnt <= 0) {
			basketMap.put("code", 0);
			return basketMap;
		}
		
		//장바구니 중복 저장 첵크 ###########
		int dataCnt = mapper.setBasketChk(basketMap);
		if (dataCnt == 0) {
			//장바구니 저장
			mapper.setBasket(basketMap);
			return basketMap;
		} else {
			//중복일 경우 해당 강좌 seq값 가져오기
			int SEQ = mapper.getBasketSeq(basketMap);
			basketMap.put("SEQ", SEQ);
			return basketMap;
		}
		
	}

}
