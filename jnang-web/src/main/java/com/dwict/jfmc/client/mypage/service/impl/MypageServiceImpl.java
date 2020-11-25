package com.dwict.jfmc.client.mypage.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.mem.mapper.MemberMapper;
import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mypage.mapper.MypageMapper;
import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.security.model.Account;

@Service("mypageService")
public class MypageServiceImpl implements MypageService {

	@Value("#{appConfig['smpay.merchant.key']}")
	private String merchantKey;

	@Resource(name="mypageMapper")
	private MypageMapper mapper;

	@Resource(name = "memberMapper")
	private MemberMapper memberMapper;

	//오래된 장바구니 비우기
	@Override
	public int basketClear(Map<String, Object> requestMap ) {
		//System.out.println(requestMap);
		return mapper.basketClear(requestMap);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> basketList(Map<String, Object> requestMap , HttpServletRequest request) {

		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		final String MEM_NO = account.getMemNo();
		requestMap.put("MEM_ID", MEM_ID);
		requestMap.put("MEM_NO", MEM_NO);

		//오래된 장바구니 비우기
		//System.out.println(requestMap);
		basketClear(requestMap);

		/*
		 * ########################################################################
		 *결제 페이지 값 전송 ############################################################# 
		 * ########################################################################
		 */
		//상품제목
		String goodsNames = "";
		String goodsNameEncode = "";
		String memNmEncode = "";
		//String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		final String MID = "SMTPAY001m";
		// 상점서명키 (꼭 해당 상점키로 바꿔주세요)
		//String merchantKey = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";
		//String merchantKey = "KiS8NWHjZ49FzG91HMI9hVXOSxYrvFBKzl2bYpr2ac7lg369iZxy0xhCJfg4juCuVH27mO/TQ4kG2qnjEr5Z4Q==";
		final String ediDate =  FormatUtil.getDefaultDate(3, "",""); // getyyyyMMddHHmmss(); // 전문생성일시

		//기타 회원정보 불러오기
		final Member member = memberMapper.findById(MEM_ID);
		final String MEM_NM = member.getMemNm();
		String HP 	= member.getHp();
		final String ETC_NO = member.getEtcNo();
		final String EMAIL = member.getEmail();
		HP = (HP == null || HP == "") ? ETC_NO : HP ;

		//장바구니 불러오기
		int dataCnt = 0;
		int goodsAmt = 0;
		final List<Map<String, Object>> dataList = mapper.basketList(MEM_NO);
		//list 값 정보 가져오기
		for (int ii=0; ii < dataList.size(); ii++) {

			final String ITEM_NM = dataList.get(ii).get("ITEM_NM").toString();	
			final String SALE_AMT = dataList.get(ii).get("SALE_AMT").toString();
			goodsAmt += Integer.parseInt(SALE_AMT);
			if (ii==0) {
				goodsNames = ITEM_NM;
			}

			dataCnt++;
		}
		final String strUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		goodsAmt = (strUrl.contains("localhost") || strUrl.contains("14.36.179.143")) ? 10 : goodsAmt;

		//리턴 값 저장  
		goodsNames = (dataCnt > 1) ? goodsNames +" 외"+ (dataCnt-1) + "건" : goodsNames;
		final Map<String, Object> addMap = new HashMap<>();

		try {
			goodsNameEncode = URLEncoder.encode(goodsNames, "EUC-KR");
			memNmEncode		= URLEncoder.encode(MEM_NM, "EUC-KR");
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String EncryptData = FormatUtil.encodeMD5HexBase64(ediDate + MID + goodsAmt + merchantKey);

		addMap.put("goodsCnt", dataCnt);
		addMap.put("goodsAmt", goodsAmt);
		addMap.put("goodsName", goodsNames +"");
		addMap.put("goodsNameEn", goodsNameEncode);

		addMap.put("buyerName", MEM_NM);
		addMap.put("buyerNameEn", memNmEncode);

		addMap.put("buyerEmail", EMAIL);
		addMap.put("byerTel", HP);
		addMap.put("EncryptData", EncryptData);
		addMap.put("ediDate", ediDate);
		addMap.put("merchantKey", merchantKey);
		dataList.add(addMap);		

		return dataList;
	}

	
	//내 정보
	@Override
	public Map<String, Object> myInfo(String id) {

		if (id == null || id =="") {
			return null;
		}
		return mapper.memberInfo(id);
	}

	//내  수강신청 현황
	@Override
	public List<Map<String,Object>> getMyClssList(String userId) {

		return mapper.getMyClssList(userId);
	}

	//수강신청 당일 취소처리
	@Override
	public int classCancelPay(Map<String, Object> requestMap, HttpServletRequest request){

		//final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//final String userId = auth.getName();

		final HttpSession session = request.getSession(false);
		final Member members = (Member) session.getAttribute("member");
		System.out.println("세션=========================>"+ members.getId());		
		requestMap.put("userId", members.getId());

		return mapper.classCancelPay(requestMap);
	}

	//내 대관정보
	@Override
	public List<Map<String,Object>> getMyRentList(String userId) {

		return mapper.myRentDataList(userId);
	}

	@Override
	public Paging myBoard(Map<String, Object> param) {
		final Paging obj = new Paging();
		obj.handlePaging(Integer.valueOf((String) param.get("pageIndex")), Integer.valueOf((String) param.get("pageSize")));
		param.put("startRow", obj.getStartRow());
		param.put("pageSize", obj.getPageSize());
		obj.handlePagingList(mapper.myBoard(param), mapper.myBoardCnt(param));
		return obj;
	}

	@Override
	public void modifyUpdate(Map<String, Object> param) {
		mapper.modifyUpdate(param);
	}

	@Override
	public void modifyDelete(Map<String, Object> param) {
		mapper.modifyDelete(param);
	}
}
