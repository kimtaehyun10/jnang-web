package com.dwict.jfmc.client.mypage.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import com.dwict.jfmc.client.smpay.service.PayService;

@Service("mypageService")
public class MypageServiceImpl implements MypageService {

	@Resource(name="mypageMapper")
	private MypageMapper mapper;

	@Resource(name = "memberMapper")
	private MemberMapper memberMapper;

	@Resource(name = "payService")
	private PayService payService;
	
	//오래된 장바구니 비우기,  선택 삭제
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
		
		//사업장별 PG결제 키값 정보불러오기
		/*
		장바구니에서 어디 사업장을 불러와야 할지 모르기 때문에 적용안함  
		Map<String, Object> maps = new HashMap<>();
		maps = payService.payKeyInfo(requestMap);
		String  merchantKey = (String) maps.get("KEY");
		String  storeMID = (String) maps.get("MID");
		*/

		Map <String,Object> maps = new HashMap<>();
    	maps.put("MEM_NO", MEM_NO);
    	maps.put("SEQ", "");
    	
		//ClassNm 값 구
		final List<Map<String, Object>> dataList = mapper.basketList(maps);
		//list 값 정보 가져오기
		/*
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
		goodsAmt = (strUrl.contains("localhost") || MEM_ID.equals("powerjyc")) ? 10 : goodsAmt;

		//리턴 값 저장  
		//goodsNames = (dataCnt > 1) ? goodsNames +" 외"+ (dataCnt-1) + "건" : goodsNames;
		final Map<String, Object> addMap = new HashMap<>();

		try {
			goodsNameEncode = URLEncoder.encode(goodsNames, "EUC-KR");
			memNmEncode		= URLEncoder.encode(MEM_NM, "EUC-KR");
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//final String EncryptData = FormatUtil.encodeMD5HexBase64(ediDate + storeMID + goodsAmt + merchantKey);

		
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
		addMap.put("storeMID", storeMID);
		dataList.add(addMap);		
		*/
		
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

	
	//myPage 대관 선택  취소
	@Override
	public int rentSelectCancel(HttpServletRequest request) {

		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		final String MEM_NO = account.getMemNo();
		
		final Map<String, Object> maps = new HashMap<>();
		maps.put("MEM_NO", MEM_NO);
		maps.put("SEQ", request.getParameter("SEQ"));
		
		return mapper.rentSelectCancel(maps);
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

	@Override
	public List<Map<String, Object>> lockerStatusList(Map<String, Object> param) {
		return mapper.lockerStatusList(param);
	}
	
	@Override
	public Map<String, Object> reLocker(Map<String, Object> param) {
		return mapper.reLocker(param);
	}


	@Override
	public Map<String, Object> lockerPayDetail(HttpServletRequest request, Map<String, Object> param) {
		final Map<String, Object> requestMap = new HashMap<>();
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		final String MEM_NO = account.getMemNo();
		requestMap.put("MEM_ID", MEM_ID);
		requestMap.put("MEM_NO", MEM_NO);

		/*
		 * ########################################################################
		 *결제 페이지 값 전송 ############################################################# 
		 * ########################################################################
		 */
		//상품제목
		String goodsNames = param.get("COMNM")+"/"+param.get("PLACE_CD")+"/"+param.get("LOCKER_CD");
		String goodsNameEncode = "";
		String memNmEncode = "";
		//String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
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

		//사업장별 PG결제 키값 정보불러오기 
		Map<String, Object> maps = new HashMap<>();
		maps = payService.payKeyInfo(param);
		String  merchantKey = (String) maps.get("KEY");
		String  storeMID = (String) maps.get("MID");
		String  URL = (String) maps.get("URL");
		

		//리턴 값 저장  
		final Map<String, Object> addMap = new HashMap<>();
		
		try {
			goodsNameEncode = URLEncoder.encode(goodsNames, "EUC-KR");
			memNmEncode		= URLEncoder.encode(MEM_NM, "EUC-KR");
		} catch (final UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final String EncryptData = FormatUtil.encodeMD5HexBase64(ediDate + storeMID + param.get("RENT_AMT") + merchantKey);

		addMap.put("goodsName", goodsNames +"");
		addMap.put("goodsNameEn", goodsNameEncode);

		addMap.put("buyerName", MEM_NM);
		addMap.put("buyerNameEn", memNmEncode);

		addMap.put("buyerEmail", EMAIL);
		addMap.put("byerTel", HP);
		addMap.put("EncryptData", EncryptData);
		addMap.put("ediDate", ediDate);
		addMap.put("merchantKey", merchantKey);
		addMap.put("storeMID", storeMID);				
		addMap.put("URL", URL);

		return addMap;
	}


	@Override
	public List<Map<String, Object>> forOrderCancel(String tID) {
		return mapper.forOrderCancel(tID);
	}
	
	@Override
	public List<Map<String,Object>> getMyRentList1(String userId) {

		return mapper.myRentDataList1(userId);
	}


	@Override
	public List<Map<String, Object>> getMyRentList2(Map<String, Object> map) {
		
		return mapper.myRentDataList2(map);
	}


	@Override
	public List<Map<String, Object>> getMyRentIdxList(Map<String, Object> map) {
		return mapper.getMyRentIdxList(map);
	}


	@Override
	public Map<String, Object> getMyClss(Map<String, Object> requestMap) {

		return mapper.getMyClss(requestMap);
	}


	@Override
	public Paging rentPage(Map<String, Object> param) {
		final Paging obj = new Paging();
		obj.handlePaging(Integer.valueOf((String) param.get("pageIndex")), Integer.valueOf((String) param.get("pageSize")));
		param.put("startRow", obj.getStartRow());
		param.put("pageSize", obj.getPageSize());
		int rentPageCnt = mapper.rentPageCnt(param);
		param.put("rentPageCnt",rentPageCnt+1);
		obj.handlePagingList(mapper.rentPage(param), mapper.rentPageCnt(param));
		return obj;	
	}
}
