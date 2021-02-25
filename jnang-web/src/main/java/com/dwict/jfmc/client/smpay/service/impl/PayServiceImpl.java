package com.dwict.jfmc.client.smpay.service.impl;




import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dwict.jfmc.client.smpay.mapper.PayMapper;
import com.dwict.jfmc.client.smpay.service.PayService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodec;
import com.dwict.jfmc.client.com.util.FormatUtil;
import com.dwict.jfmc.client.mem.mapper.MemberMapper;
import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.mypage.mapper.MypageMapper;
import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.security.model.Account;

@Service("payService")
public class PayServiceImpl implements PayService {

	@Value("#{appConfig['smpayPG.mode']}")
	private String PG_MODE; //1:실제, 0:테스트
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@Resource(name = "payMapper")
	private PayMapper mapper;

	@Resource(name="mypageMapper")
	private MypageMapper mypageMapper;

	@Resource(name = "mypageService")
	private MypageService mypgService;
	
	@Resource(name = "memberMapper")
	private MemberMapper memberMapper;
	
	@Override
	public int testInsert(String dirPath, HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		mapper.testSlectx(request);
		return 1;
		
	}
	
	@Override
	//사업장(comcd)별 결제코드 키 값 불러오기 
	public Map <String,Object> payKeyInfo(Map<String, Object> maps) {
		
		
		//0 테스트 1 실제
		  if (PG_MODE.equals("0")) {
			  maps.put("COMCD", "TEST");
		  
		  }
		 
		return mapper.payKeyInfo(maps);
	}
	
	@Override
	//사업장(comcd) 캔슬 
	public Map <String,Object> payKeyInfoCancel(Map<String, Object> maps) {
		
		if(PG_MODE.equals("1")) {
			
			if(maps.get("SLIP_NO") != null && maps.get("COMCD") != null && maps.get("TID") != null) {
				String comCd = maps.get("COMCD").toString();
				String slipNo = maps.get("SLIP_NO").toString();
				String tId = maps.get("TID").toString();
				String payAmt = maps.get("PAY_AMT").toString();
				String MEM_NO = maps.get("MEM_NO").toString();
				String NEXT_RECEIPT_NO = mapper.getNextReceiptNo();
				
				int cancelPay = Integer.parseInt(payAmt);
				
				mapper.updateCancelRentApp(maps);
				
				/*
				 * //일단 테스트용 if (PG_MODE.equals("0"))//1:실제, 0:테스트 { maps.put("COMCD", "TEST");
				 * }
				 */
				return mapper.payKeyInfo(maps);
				
			} else if(maps.get("COMCD").toString() != null ) {
					maps.put("COMCD", "TEST");
					
			}
			
		}
		
		//0 테스트 1 실제
		/*
		 * if (PG_MODE.equals("1")) { if(maps.get("COMCD").toString() != null) {
		 * maps.put("COMCD", "TEST");
		 * 
		 * }
		 * 
		 * }
		 */
		return mapper.payKeyInfo(maps);
	}
	
	
	
	//기본주문
	@Override
	@Transactional
	public Map<String, Object> lecOrderInsert(HttpServletRequest request) {
		// TODO Auto-generated method stub

		

		
		String ResultCode		= request.getParameter("ResultCode")==null?"":request.getParameter("ResultCode"); // 결과코드
		String PayMethod		= request.getParameter("PayMethod")==null?"":request.getParameter("PayMethod"); // 지불수단
		String MID				= request.getParameter("MID")==null?"":request.getParameter("MID"); // 상점 ID
		String Amt				= request.getParameter("Amt")==null?"":request.getParameter("Amt"); // 금액
		String BuyerName		= request.getParameter("BuyerName")==null?"":request.getParameter("BuyerName"); // 결제자명
		BuyerName				= rtnDecode(BuyerName);
		String GoodsName		= request.getParameter("GoodsName")==null?"":request.getParameter("GoodsName"); // 상품명
		GoodsName				= rtnDecode(GoodsName);
		//String mallUserID       = request.getParameter("mallUserID")==null?"":request.getParameter("mallUserID"); // 고객사회원ID
		String TID              = request.getParameter("TID")==null?"":request.getParameter("TID"); // 거래번호
		String OID				= request.getParameter("OID")==null?"":request.getParameter("OID"); // 주문번호
		String AuthDate			= request.getParameter("AuthDate")==null?"":request.getParameter("AuthDate"); // 승인일자
		String AuthCode			= request.getParameter("AuthCode")==null?"":request.getParameter("AuthCode"); // 승인번호
		String ResultMsg		= request.getParameter("ResultMsg")==null?"":request.getParameter("ResultMsg"); // 결과메시지
		ResultMsg				= rtnDecode(ResultMsg);
		String VbankNum			= request.getParameter("VbankNum")==null?"":request.getParameter("VbankNum"); // 가상계좌번호
		String VbankName			= request.getParameter("VbankName")==null?"":request.getParameter("VbankName"); // 가상계좌은행명
		
		String fn_cd			= request.getParameter("fn_cd")==null?"":request.getParameter("fn_cd"); // 결제카드사코드
		String fn_name			= request.getParameter("fn_name")==null?"":request.getParameter("fn_name"); // 결제카드사명
		fn_name					= rtnDecode(fn_name);
		String CardQuota			= request.getParameter("CardQuota")==null?"":request.getParameter("CardQuota"); // 할부개월수
		String BuyerTel			= request.getParameter("BuyerTel")==null?"":request.getParameter("BuyerTel"); // 구매자 전화번호
		String BuyerEmail			= request.getParameter("BuyerEmail")==null?"":request.getParameter("BuyerEmail"); // 구매자이메일주소
		String BuyerAuthNum			= request.getParameter("BuyerAuthNum")==null?"":request.getParameter("BuyerAuthNum"); // 구매자주민번호
		String ReceiptType			= request.getParameter("ReceiptType")==null?"":request.getParameter("ReceiptType"); // 현금영수증유형
		String SignValue			= request.getParameter("SignValue")==null?"":request.getParameter("SignValue"); // 위변조 사인값
		
		String TaxCD			= request.getParameter("TaxCD")==null?"":request.getParameter("TaxCD"); // TAX 코드
		String SvcAmt			= request.getParameter("SvcAmt")==null?"":request.getParameter("SvcAmt"); // 봉사료
		String Tax			= request.getParameter("Tax")==null?"":request.getParameter("Tax"); // 부가세
		String AcquCardCode			= request.getParameter("AcquCardCode")==null?"":request.getParameter("AcquCardCode"); // 매입사코드 

		String DivideInfo = request.getParameter("DivideInfo")==null?"":request.getParameter("DivideInfo"); // 서브몰 정보 
		String rtnUrl 		= request.getParameter("ReturnURL")==null?"":request.getParameter("ReturnURL"); // 서브몰 정보
		rtnUrl				= (rtnUrl == null) ? "" : rtnUrl;
		
		String SspMallID			= request.getParameter("SspMallID")==null?"":request.getParameter("SspMallID"); // 매입사코드
		String MemberNo			= request.getParameter("MemberNo")==null?"":request.getParameter("MemberNo"); // 매입사코드

		String userParam		= request.getParameter("q")==null? "//" : request.getParameter("q"); // 사용자 파람
								String [] arrayTmp 	= userParam.split("\\/"); //20201225/8
		String MEM_ID			= arrayTmp[0]; //예약일

		final Member member = memberMapper.findById(MEM_ID);
		String MEM_NO		= member.getMemNo();
		String MEM_NM		= member.getMemNm();
		
		
		/*
		final HttpSession session = request.getSession(false);
		//Member members = null;
		//try {
		Member	members = (Member) session.getAttribute("member");
			System.out.println("세션=========================>"+ members.getId());
		//} catch (Exception e) {
		//	System.out.println("세션XXXXXXXXXXXXXXXXXXXXXXXXXXXX=>"+ e.toString());
		//}
		System.out.println(members.getId());
		String MEM_ID = members.getId();
    	String MEM_NO = members.getMemNo();
    	String MEM_NM = members.getMemNm();
    	//String MEM_ID = "powerjyc1"; //members.getId();
    	//String MEM_NO = "00135079"; //members.getMemNo();
    	//String MEM_NM = "정연철"; //members.getMemNm();
    	*/
		
		/*
		//일단 작동안되서 세션으로 적용
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String MEM_ID = auth.getName();
		Map <String,Object> member = mypgService.myInfo(MEM_ID);
		
		String MEM_NO = (String) member.get("MEM_NO");
		String MEM_NM = (String) member.get("MEM_NM");
    	*/
    	
		// 웹 링크 버전일 경우에 실제 스마트로 서버의 승인 값을 검증 하기 위해서 아래의 값을 비교 합니다..
	    if (ResultCode.equals("3001")) {// CARD
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    	
//	    	try {
	    	
	    			
		    	int goodsAmt = 0;
		    	String goodsNames = "";
		    	int dataCnt = 0;
		    	
		    	//장바구니 정보 가져오기
				List<Map<String, Object>> aPrgList = mypageMapper.basketList(MEM_NO);
				String comCd =  aPrgList.get(0).get("COMCD").toString();
				
		  		//등록강습반 및 프로그램 저장
		  		//String prgList = List; //request.getParameter("PRG");//array 강습반 및 프로그램 정보
		  		//String payList = request.getParameter("PAY"); //(String)requestMap.get("PAY");//array 주문상품 정보
		  		//JSONArray aPrgList = JSONArray.fromObject(prgList); 
		  		//JSONArray aPayList = JSONArray.fromObject(payList);
		  		
		  		String userId = MEM_ID;//(String)requestMap.get("userId");
		  		String userNm = ""; //(String)requestMap.get("userNm");
	
		  		//ACT_MODE : "Change" 강좌 변경
		  		String ACT_MODE =""; // (String)requestMap.get("ACT_MODE");
				//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
		  		
		  		int CASH_AMT_SUM = 0;//현금결제금액
		  		int CARD_AMT_SUM = Integer.parseInt(Amt);//카드결제금액
		  		int RECEIVE_AMT = 0;
		  		int RETURN_AMT = 0;
		  		
		  		int iDEPOSIT_AMT = 0; //보증금
		  		
		  		
		  			  	
//		  		//현금,카드 합계
//		  		for (int i=0;i<aPayList.size();i++ ) {
//		  			String P_TYPE = aPayList.getJSONObject(i).getString("P_TYPE").toString();
//		  			if ("CASH".equals(P_TYPE)) { //현금
//		  	  			CASH_AMT_SUM = CASH_AMT_SUM + aPayList.getJSONObject(i).getInt("PAY_AMT");
//		  	  			RECEIVE_AMT = RECEIVE_AMT + aPayList.getJSONObject(i).getInt("RECEIVE_AMT");
//		  	  			RETURN_AMT = RETURN_AMT + aPayList.getJSONObject(i).getInt("PAY_AMT");
//		  			} else { //카드
//		  	  			CARD_AMT_SUM = CARD_AMT_SUM + aPayList.getJSONObject(i).getInt("PAY_AMT");
//		  			}
//		  		}
		  		
		  		
		  		
		  		
	//	  		//결제금액에서 보증금을 빼기 위한
	//	  		for (int i=0;i<aPrgList.size();i++ ) {
	//	  			String GUBUN = aPrgList.getJSONObject(i).getString("ITM_TYPE").toString();
	//	  			if ("war".equals(ITM_TYPE)){
	//	  				int tmpDEPOSIT_AMT = aPrgList.getJSONObject(i).getInt("DEPOSIT_AMT"); // 보증금	  			
	//		  			iDEPOSIT_AMT =  iDEPOSIT_AMT + tmpDEPOSIT_AMT;
	//	  			}
	//	  		}  		
		  			  		
		  		//select * from PAY_CHANGE_INFO
		   		String NEXT_RECEIPT_NO = mapper.getNextReceiptNo();
		   		//다음정산번호 가져오기 select * from PAY_LIST
		   		String NEXT_SLIP_NO = mapper.getNextSlipNo();
		   		
		   		//현금영수번호 가 없을경우 보조추가용
		   		String NEXT_APP_NO = "";
		
		   		
				//주문_결제정보(CALC_MASTER) 저장  
		   		//select * from CALC_MASTER order by  WRITE_DH desc
				Map<String, Object> requestMapCalcMaster = new HashMap<String, Object>();
				requestMapCalcMaster.put("COMCD", comCd);
			  	requestMapCalcMaster.put("USER_ID", MEM_NO);
			  	requestMapCalcMaster.put("RECEIPT_NO", NEXT_RECEIPT_NO);	  	
				requestMapCalcMaster.put("SLIP_NO", NEXT_SLIP_NO);
				requestMapCalcMaster.put("MEM_NO", MEM_NO);
				requestMapCalcMaster.put("PAY_AMT", CASH_AMT_SUM + CARD_AMT_SUM);//결제금액
				requestMapCalcMaster.put("CASH_AMT",CASH_AMT_SUM);
				requestMapCalcMaster.put("CARD_AMT",CARD_AMT_SUM);
				
				
				//주문_결제정보(CALC_MASTER) 저장
		  		mapper.setCalcMaster(requestMapCalcMaster);
		  		
		    	String ymdhis = FormatUtil.getDefaultDate(1, "-","");
	
				
				
	  			String P_COMCD =  fn_cd; // aPayList.getJSONObject(ii).getString("P_COMCD").toString(); //결제업체코드
	  			String P_TYPE =  "CARD"; //aPayList.getJSONObject(ii).getString("P_TYPE").toString(); //결제수단구분(CARD,CASH)
	  			
	  			//if (P_TYPE.equals("CARD")) {
	  				
  			
	  	  		int PAY_AMT = Integer.parseInt(Amt); //aPayList.getJSONObject(ii).getInt("PAY_AMT"); //결제금액
	  			
	  			//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
	  			String METHOD_CD = "01";// aPayList.getJSONObject(ii).getString("METHOD_CD").toString(); //결제수단코드
	       		String APP_DATE = AuthDate; //aPayList.getJSONObject(ii).getString("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
	       		String APP_NO = AuthCode; //aPayList.getJSONObject(ii).getString("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
	       		String APP_TIME = ""; //aPayList.getJSONObject(ii).getString("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
	       		
	       		
	       		if ("".equals(APP_DATE) || APP_DATE.equals(null)) {
	  				APP_DATE = FormatUtil.getDefaultDate(3, "",""); 
	  			}
	       		
	       		if (APP_NO.equals("") || APP_NO == null ) {
	       			APP_NO = NEXT_APP_NO;
	       		}
	
	       		String APP_CARD_NO = ""; //aPayList.getJSONObject(ii).getString("APP_CARD_NO").toString(); //카드 번호
	       		String SEC_CARD_NO1 = "";
	       		String SEC_CARD_NO2 = "";
	       		String SEC_CARD_NO3 = "";
	       		String SEC_CARD_NO4 = "";       		
	       		if (APP_CARD_NO.length() > 10)
	       		{
	       			SEC_CARD_NO1 = APP_CARD_NO.substring(4);
	       			SEC_CARD_NO4 = APP_CARD_NO.substring(APP_CARD_NO.length()-4, APP_CARD_NO.length());
	       		}

	       		
	       		String APP_CARD_CD = fn_cd; //aPayList.getJSONObject(ii).getString("APP_CARD_CD").toString(); //카드사 코드
	       		String APP_CARD_HALBU = CardQuota; //aPayList.getJSONObject(ii).getString("APP_CARD_HALBU").toString(); //카드사 할부       		
	       		String APP_CASH_INFO = "";//aPayList.getJSONObject(ii).getString("APP_CASH_INFO").toString(); //현금영수 입력정보
	       		
	       		
	       		//승인구분__1_카드결제승인__2_카드승인취소__3_현금영수증승인__4_현금영수증승인취소
	       		String APP_GBN	= (P_TYPE.equals("CARD")) ? "1" : "3";
	       		
	  			Map<String, Object> requestMapPayList = new HashMap<String, Object>();
	  			
		  		requestMapPayList.put("COMCD", comCd);
		  		requestMapPayList.put("USER_ID", userId);
		  		requestMapPayList.put("MEM_NO", MEM_NO);
		  		requestMapPayList.put("SLIP_NO", NEXT_SLIP_NO);  	
		  		requestMapPayList.put("RECEIPT_NO", NEXT_RECEIPT_NO);
		  		requestMapPayList.put("PAY_AMT", PAY_AMT - iDEPOSIT_AMT);//결제금액(보증금을 뺌)
		  		requestMapPayList.put("APP_DATE", APP_DATE);//승인일시__van또는pg또는현금영수증
		  		requestMapPayList.put("APP_NO", APP_NO);//승인번호__van또는pg또는현금영수증
		  		requestMapPayList.put("APP_GBN", APP_GBN);//승인구분
		  		requestMapPayList.put("APP_TIME", APP_TIME);//승인시분Hi__van또는pg또는현금영수증  		  		
		  		requestMapPayList.put("P_COMCD", P_COMCD);//결제업체
		  		requestMapPayList.put("P_TYPE", P_TYPE);//지불수단
		    	requestMapPayList.put("METHOD_CD", METHOD_CD);//지불수단코드(카드:02,현금:00)
		    	requestMapPayList.put("CHANGE_YN", "N");//변경여부
		    	requestMapPayList.put("CANCEL_YN", "N");//승인취소여부YN
		    	requestMapPayList.put("PAY_SEQ", 1);//결제 순번(카드,현금 2건이상경우 순번) 
		    	
		    	requestMapPayList.put("SEC_CARD_NO1", SEC_CARD_NO1);//카드번호1
		    	requestMapPayList.put("SEC_CARD_NO2", SEC_CARD_NO2);//카드번호1
		    	requestMapPayList.put("SEC_CARD_NO3", SEC_CARD_NO3);//카드번호1
		    	requestMapPayList.put("SEC_CARD_NO4", SEC_CARD_NO4);//카드번호1
		    	
		    	requestMapPayList.put("CARD_SEC", APP_CARD_CD);//카드사 코드
		    	requestMapPayList.put("CARD_SEC2", "");//
		    	requestMapPayList.put("HALBU_CNT", APP_CARD_HALBU);//카드사 할부
		    	
		    	requestMapPayList.put("TID", TID);//승인금액 정보
		    	
		    	requestMapPayList.put("APP_AMT", PAY_AMT);//승인금액 정보
		    	requestMapPayList.put("CASH_USER_INFO", APP_CASH_INFO);//현금영수 정보
		    	
		    	requestMapPayList.put("STORE_NO", "");//???????????
		    	requestMapPayList.put("PAY_LIST_YN", "");//
		    	requestMapPayList.put("WRITER", userNm);//운영자

		    	requestMapPayList.put("WRITE_DH", ymdhis);//
	    	
		    	
		    	//
		    	
		    	// select * from PAY_LIST  order by  WRITE_DH desc
		    	// select * from card_app_hist_damo  order by  WRITE_DH desc
		    	
		    	//결제정보 저장
				mapper.setPayList(requestMapPayList);
				//일마감관리 > 일마감관리 > 카드결제처리현황
				mapper.setPayList2(requestMapPayList);
				

				//장바구니 SEQ
				String arrSEQ = "";
				
				//장바구니 list 값 정보 가져오기
				for (int ii=0; ii < aPrgList.size(); ii++) {
					//장바구니 SEQ 삭제시 필요
					String SEQ = aPrgList.get(ii).get("SEQ").toString();
					arrSEQ += ","+ SEQ;
					String ITEM_NM = aPrgList.get(ii).get("ITEM_NM").toString();	
					String SALE_AMT = aPrgList.get(ii).get("SALE_AMT").toString();
					goodsAmt += Integer.parseInt(SALE_AMT);
					if (ii==0) {
						goodsNames = ITEM_NM;
					}
					
					dataCnt++;
				}
				//리턴 값 저장  
				goodsNames = (dataCnt > 1) ? goodsNames +" 외"+ (dataCnt-1) + "건" : goodsNames;
			    		
				
				for (int ii=0; ii < aPrgList.size(); ii++) {		
		  			//프로그램/사물함구분 PRG:프로그램, LOCKER:사물함, DEPOSIT:사물함보증금
		  			String GUBUN = aPrgList.get(ii).get("ITM_TYPE").toString(); //aPrgList.getJSONObject(jj).getString("ITM_TYPE").toString();
					String CLASS_CD = aPrgList.get(ii).get("CLASS_CD").toString(); //aPrgList.getJSONObject(jj).getString("CLASS_CD").toString();
					String PART_CD = aPrgList.get(ii).get("PART_CD").toString(); //aPrgList.getJSONObject(jj).getString("PART_CD").toString();
					String SPORTS_CD = aPrgList.get(ii).get("SPORTS_CD").toString(); //aPrgList.getJSONObject(jj).getString("SPORTS_CD").toString();
					String ITEM_CD = aPrgList.get(ii).get("ITEM_CD").toString(); //aPrgList.getJSONObject(jj).getString("ITEM_CD").toString();
					String ITEM_SDATE = aPrgList.get(ii).get("ITEM_SDATE").toString(); //aPrgList.getJSONObject(jj).getString("ITEM_SDATE").toString();
					String ITEM_EDATE = aPrgList.get(ii).get("ITEM_EDATE").toString(); //aPrgList.getJSONObject(jj).getString("ITEM_EDATE").toString();
					String DCREASON_CD = aPrgList.get(ii).get("DC_CD1").toString(); //aPrgList.getJSONObject(jj).getString("DCREASON_CD").toString();
					String DCREASON_CD2 = aPrgList.get(ii).get("DC_CD2").toString(); //aPrgList.getJSONObject(jj).getString("DCREASON_CD2").toString();
					String USER_NO = ""; //aPrgList.getJSONObject(jj).getString("USER_NO").toString();
					
					String tmp_Amp = aPrgList.get(ii).get("SALE_AMT").toString();
					int COST_AMT = Integer.parseInt(tmp_Amp); //aPrgList.getJSONObject(jj).getInt("COST_AMT"); //판매원가
					int UNIT_AMT = Integer.parseInt(tmp_Amp); //aPrgList.getJSONObject(jj).getInt("UNIT_AMT"); //판매단가
					int SALE_AMT = Integer.parseInt(tmp_Amp); //aPrgList.getJSONObject(jj).getInt("SALE_AMT"); //판매금액
					
					String SALE_NUM = "1"; //aPrgList.getJSONObject(jj).getString("SALE_NUM").toString(); //판매수량
					int USE_CNT = 0; //aPrgList.getJSONObject(jj).getInt("USE_CNT"); //이용기간내횟수 = 0; //이용횟수
					int DC_AMT = 0; //aPrgList.getJSONObject(jj).getInt("DC_AMT"); //할인금액
					String VAT_YN = aPrgList.get(ii).get("VAT_YN").toString(); //aPrgList.getJSONObject(jj).getString("VAT_YN").toString(); //부가세여부YN
					
					//부가세 계산

					int VAT_AMT = 0; //aPrgList.getJSONObject(jj).getInt("VAT_AMT"); //부가세금액
					String CALMEM_NO = MEM_NO; //aPrgList.getJSONObject(jj).getString("CALMEM_NO").toString(); //이용회원번호
					int DEPOSIT_AMT = 0; //aPrgList.getJSONObject(jj).getInt("DEPOSIT_AMT"); // 보증금
					
					if ("".equals(CALMEM_NO)) {
						CALMEM_NO = MEM_NO;
					}
					
			  		//////////////////////
			  		//회원가입프로그램
			  		//////////////////////
					if ("lec".equals(GUBUN)){
						Map<String, Object> requestMapMemSale = new HashMap<String, Object>();
						requestMapMemSale.put("USER_ID", userId);
						requestMapMemSale.put("COMCD", comCd);
						requestMapMemSale.put("ACT_MODE", "I"); //추가(I), 수정(E) 여부
						requestMapMemSale.put("MEM_NO", MEM_NO);//	회원번호
						requestMapMemSale.put("PART_CD", PART_CD);//업장코드
						requestMapMemSale.put("SPORTS_CD", SPORTS_CD);//종목코드(CM_SPORTS_CD)
						requestMapMemSale.put("ITEM_CD", ITEM_CD);//프로그램코드
						requestMapMemSale.put("SLIP_NO", NEXT_SLIP_NO);//정산번호
						requestMapMemSale.put("MEM_NO", MEM_NO);//	회원번호
						requestMapMemSale.put("ITEM_SDATE", ITEM_SDATE);//	이용시작일Ymd
						requestMapMemSale.put("ITEM_EDATE", ITEM_EDATE);//	이용종료일Ymd
						requestMapMemSale.put("DCREASON_CD2", DCREASON_CD2);//	감면대상할인__공통코드참조
						requestMapMemSale.put("DCREASON_CD", DCREASON_CD);//	감면대상할인__공통코드참조
						requestMapMemSale.put("COST_AMT", COST_AMT);//	판매원가
						requestMapMemSale.put("UNIT_AMT", UNIT_AMT);//	판매단가
						requestMapMemSale.put("SALE_NUM", SALE_NUM);//	판매수량
						requestMapMemSale.put("USE_CNT", USE_CNT);//	이용기간내횟수
						requestMapMemSale.put("DC_AMT", DC_AMT);//	할인금액
						requestMapMemSale.put("SALE_AMT", SALE_AMT);//	판매금액
						requestMapMemSale.put("VAT_YN", VAT_YN);//	부가세여부YN
						requestMapMemSale.put("VAT_AMT", VAT_AMT);//	부가세금액
						requestMapMemSale.put("WEB_TYPE", "ONLINE");//	온오프라인구분
						requestMapMemSale.put("CALMEM_NO", CALMEM_NO);//	이용회원번호??
						requestMapMemSale.put("TRANSFER_GBN", "N");//	미사용인듯__전부N임
						requestMapMemSale.put("MIDCANCEL_YN", "N");//	중도해약여부YN  		  	
						requestMapMemSale.put("CHANGE_YN", "N");//	변경상태__Y_변경후강습__B_변경전강습__N_변경없음
						requestMapMemSale.put("RETURN_YN", "N");//	미사용인듯__전부N임
						requestMapMemSale.put("CANCEL_YN", "N");//	결제당일취소여부YN
						requestMapMemSale.put("PROMOTION_YN", "N");//	미사용인듯__전부N임
						requestMapMemSale.put("OLD_PROMOTION_YN", "N");//	미사용인듯__전부N임
						requestMapMemSale.put("SALE_SEC", "01"); //매출구분(SM_SALE_SEC)-01:정상
						
				  		//등록강습반 및 프로그램 저장
						mapper.setMemSale(requestMapMemSale);  
		
				  		//회원_강습신청내역 저장
				  		String NEXT_SALE_SEQ = requestMapMemSale.get("NEXT_SALE_SEQ").toString();
				  		
				  		Map<String, Object> requestMapTrainHist = new HashMap<String, Object>();
		
				  		requestMapTrainHist.put("USER_ID", userId);
			    	  	requestMapTrainHist.put("COMCD", comCd);
			    	  	requestMapTrainHist.put("ACT_MODE", "I"); //추가(I), 수정(E) 여부
			    	  	requestMapTrainHist.put("MEM_NO", MEM_NO);//	회원번호
			    	  	requestMapTrainHist.put("SALE_SEQ", NEXT_SALE_SEQ);//가입프로그램신청순번
		
			    	  	requestMapTrainHist.put("CLASS_CD", CLASS_CD);//강습반__TRAIN_CLASS__CLASS_CD
			    		requestMapTrainHist.put("TRAIN_SDATE", ITEM_SDATE);//이용시작일_Ymd
			    		requestMapTrainHist.put("TRAIN_EDATE", ITEM_EDATE);//이용종료일_Ymd
			    		requestMapTrainHist.put("USER_NO", USER_NO);//강사ID
			    	  	
			    	  	requestMapTrainHist.put("CHNG_DATE", "");//강습반변경일_Ymd
			    	  	requestMapTrainHist.put("CHANGE_YN", "N");//	강습반변경여부YN
			    	  	
			    	  	if ("".equals(USER_NO)) {
			    	  		requestMapTrainHist.put("ASSIGN_YN", "N");//강사여부
			    	  	} else {
			    	  		requestMapTrainHist.put("ASSIGN_YN", "Y");//강사여부
			    	  	}
			    	
			  		mapper.setTrainHist(requestMapTrainHist);
	
					} else 
			  		//////////////////////
			  		//사물함임대료
			  		//////////////////////	
					if ("LOCKER".equals(GUBUN)){
					
						
					} else 
			  		//////////////////////
			  		//사물함보증금
			  		//////////////////////				
					if ("DEPOSIT".equals(GUBUN)){
	
					}
					
					
		  		}//end for


		//장바구니 삭제
		Map<String, Object> maps = new HashMap<>();
		maps.put("MEM_NO", MEM_NO);
		arrSEQ = arrSEQ.substring(1);
		maps.put("SEQ", arrSEQ);
		mypageMapper.basketClear(maps);

//	    } catch (Exception ex) {
//	  		
//	  		
//	  	}
//	    	
	    	
	    	
		}
	    else if (ResultCode.equals("4000")) {// BANK
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else if (ResultCode.equals("4100")) {// VBANK
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else if (ResultCode.equals("A000")) {// cellphone
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else if (ResultCode.equals("B000")) {// CLGIFT
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else
	    {
	    	
	    
	    }
	    
	    
	    
		//mapper.testSlectx(request);
		Map<String, Object>  rtnData = new HashMap<String, Object>();
		rtnData = FormatUtil.formatMapRequest(request);
		rtnData.put("GoodsNameDe", GoodsName);
		rtnData.put("BuyerNameDe", BuyerName);
		rtnData.put("ResultMsgDe", ResultMsg);
		rtnData.put("rtnEndUrl", rtnUrl); //완료후 이동
		
		return rtnData;
		
	}

	
	
	
	

	//대관 결제 #################################################################################################################################
	@Override
	@Transactional
	public Map<String, Object> rentOrderInsert(HttpServletRequest request) {
		// TODO Auto-generated method stub

		//final HttpSession session = request.getSession(false);
		//Member members = null;
		//try {
		//Member	members = (Member) session.getAttribute("member");

		
		String ResultCode		= request.getParameter("ResultCode")==null?"":request.getParameter("ResultCode"); // 결과코드
		String PayMethod		= request.getParameter("PayMethod")==null?"":request.getParameter("PayMethod"); // 지불수단
		String MID				= request.getParameter("MID")==null?"":request.getParameter("MID"); // 상점 ID
		String Amt				= request.getParameter("Amt")==null?"":request.getParameter("Amt"); // 금액
		String BuyerName		= request.getParameter("BuyerName")==null?"":request.getParameter("BuyerName"); // 결제자명
		BuyerName				= rtnDecode(BuyerName);
		String GoodsName		= request.getParameter("GoodsName")==null?"":request.getParameter("GoodsName"); // 상품명
		GoodsName				= rtnDecode(GoodsName);
		//String mallUserID       = request.getParameter("mallUserID")==null?"":request.getParameter("mallUserID"); // 고객사회원ID
		String TID              = request.getParameter("TID")==null?"":request.getParameter("TID"); // 거래번호
		String OID				= request.getParameter("OID")==null?"":request.getParameter("OID"); // 주문번호
		String AuthDate			= request.getParameter("AuthDate")==null?"":request.getParameter("AuthDate"); // 승인일자
		String AuthCode			= request.getParameter("AuthCode")==null?"":request.getParameter("AuthCode"); // 승인번호
		String ResultMsg		= request.getParameter("ResultMsg")==null?"":request.getParameter("ResultMsg"); // 결과메시지
		ResultMsg				= rtnDecode(ResultMsg);
		String VbankNum			= request.getParameter("VbankNum")==null?"":request.getParameter("VbankNum"); // 가상계좌번호
		String VbankName			= request.getParameter("VbankName")==null?"":request.getParameter("VbankName"); // 가상계좌은행명
		
		String fn_cd			= request.getParameter("fn_cd")==null?"":request.getParameter("fn_cd"); // 결제카드사코드
		String fn_name			= request.getParameter("fn_name")==null?"":request.getParameter("fn_name"); // 결제카드사명
		fn_name					= rtnDecode(fn_name);
		String CardQuota			= request.getParameter("CardQuota")==null?"":request.getParameter("CardQuota"); // 할부개월수
		String BuyerTel			= request.getParameter("BuyerTel")==null?"":request.getParameter("BuyerTel"); // 구매자 전화번호
		String BuyerEmail			= request.getParameter("BuyerEmail")==null?"":request.getParameter("BuyerEmail"); // 구매자이메일주소
		String BuyerAuthNum			= request.getParameter("BuyerAuthNum")==null?"":request.getParameter("BuyerAuthNum"); // 구매자주민번호
		String ReceiptType			= request.getParameter("ReceiptType")==null?"":request.getParameter("ReceiptType"); // 현금영수증유형
		String SignValue			= request.getParameter("SignValue")==null?"":request.getParameter("SignValue"); // 위변조 사인값
		
		String TaxCD			= request.getParameter("TaxCD")==null?"":request.getParameter("TaxCD"); // TAX 코드
		String SvcAmt			= request.getParameter("SvcAmt")==null?"":request.getParameter("SvcAmt"); // 봉사료
		String Tax			= request.getParameter("Tax")==null?"":request.getParameter("Tax"); // 부가세
		String AcquCardCode			= request.getParameter("AcquCardCode")==null?"":request.getParameter("AcquCardCode"); // 매입사코드 

		String DivideInfo = request.getParameter("DivideInfo")==null?"":request.getParameter("DivideInfo"); // 서브몰 정보 
		String rtnUrl 		= request.getParameter("ReturnURL")==null?"":request.getParameter("ReturnURL"); // 서브몰 정보
		rtnUrl				= (rtnUrl == null) ? "" : rtnUrl;
		
		String SspMallID			= request.getParameter("SspMallID")==null?"":request.getParameter("SspMallID"); // 매입사코드
		String MemberNo			= request.getParameter("MemberNo")==null?"":request.getParameter("MemberNo"); // 매입사코드
		String userParam		= request.getParameter("q")==null? "//" : request.getParameter("q"); // 사용자 파람
								String [] arrayTmp 	= userParam.split("\\/"); //20201225/8
		String RESERVE_DATE1	= arrayTmp[0]; //예약일
		String PLACE_CD			= arrayTmp[1]; //대관장소값
		String COMCD			= arrayTmp[2]; //COMCD
		String rtn_idx			= arrayTmp[3]; //대관 idx
		String MEM_ID			= arrayTmp[4]; //memID
		
		int RESERVE_DATE = Integer.parseInt(RESERVE_DATE1.replaceAll("-", ""));
		
		final Member member = memberMapper.findById(MEM_ID);
		String MEM_NO		= member.getMemNo();
		String MEM_NM		= member.getMemNm();
		
		
		// 웹 링크 버전일 경우에 실제 스마트로 서버의 승인 값을 검증 하기 위해서 아래의 값을 비교 합니다..
	    if (ResultCode.equals("3001")) {// CARD
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    	
//	    	try {
	    	
	    			
		    	int goodsAmt = 0;
		    	String goodsNames = "";
		    	int dataCnt = 0;
		    	
			
		    	
		  		//등록강습반 및 프로그램 저장
		  		//String prgList = List; //request.getParameter("PRG");//array 강습반 및 프로그램 정보
		  		//String payList = request.getParameter("PAY"); //(String)requestMap.get("PAY");//array 주문상품 정보
		  		//JSONArray aPrgList = JSONArray.fromObject(prgList); 
		  		//JSONArray aPayList = JSONArray.fromObject(payList);
		  		
		  		String userId = MEM_ID;//(String)requestMap.get("userId");
		  		String userNm = ""; //(String)requestMap.get("userNm");
	
		  		//ACT_MODE : "Change" 강좌 변경
		  		String ACT_MODE =""; // (String)requestMap.get("ACT_MODE");
				//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
		  		
		  		int CASH_AMT_SUM = 0;//현금결제금액
		  		int CARD_AMT_SUM = Integer.parseInt(Amt);//카드결제금액
		  		int RECEIVE_AMT = 0;
		  		int RETURN_AMT = 0;
		  		
		  		int iDEPOSIT_AMT = 0; //보증금
		  		
		  		
		  			  		
		  		//select * from PAY_CHANGE_INFO
		   		//String NEXT_RECEIPT_NO = mapper.getNextReceiptNo();
		   		//다음정산번호 가져오기 select * from PAY_LIST
		   		String NEXT_SLIP_NO = mapper.getNextSlipNo();
		   		
		   		//현금영수번호 가 없을경우 보조추가용
		   		String NEXT_APP_NO = "";
		
		   		
		  		
		    	String ymdhis = FormatUtil.getDefaultDate(1, "-","");
	
				
				
	  			String P_COMCD =  fn_cd; // aPayList.getJSONObject(ii).getString("P_COMCD").toString(); //결제업체코드
	  			String P_TYPE =  "CARD"; //aPayList.getJSONObject(ii).getString("P_TYPE").toString(); //결제수단구분(CARD,CASH)
	  			
	  			//if (P_TYPE.equals("CARD")) {
	  				
  			
	  	  		int PAY_AMT = Integer.parseInt(Amt); //aPayList.getJSONObject(ii).getInt("PAY_AMT"); //결제금액
	  			
	  			//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
	  			String METHOD_CD = "01";// aPayList.getJSONObject(ii).getString("METHOD_CD").toString(); //결제수단코드
	       		String APP_DATE = AuthDate; //aPayList.getJSONObject(ii).getString("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
	       		String APP_NO = AuthCode; //aPayList.getJSONObject(ii).getString("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
	       		String APP_TIME = ""; //aPayList.getJSONObject(ii).getString("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
	       		
	       		
	       		if ("".equals(APP_DATE) || APP_DATE.equals(null)) {
	  				APP_DATE = FormatUtil.getDefaultDate(3, "",""); 
	  			}
	       		
	       		if (APP_NO.equals("") || APP_NO == null ) {
	       			APP_NO = NEXT_APP_NO;
	       		}
	
	       		String APP_CARD_NO = ""; //aPayList.getJSONObject(ii).getString("APP_CARD_NO").toString(); //카드 번호
	       		String SEC_CARD_NO1 = "";
	       		String SEC_CARD_NO2 = "****";
	       		String SEC_CARD_NO3 = "****";
	       		String SEC_CARD_NO4 = "";       		
	       		if (APP_CARD_NO.length() > 10)
	       		{
	       			SEC_CARD_NO1 = APP_CARD_NO.substring(4);
	       			SEC_CARD_NO4 = APP_CARD_NO.substring(APP_CARD_NO.length()-4, APP_CARD_NO.length());
	       		}

	       		
	       		String APP_CARD_CD = fn_cd; //aPayList.getJSONObject(ii).getString("APP_CARD_CD").toString(); //카드사 코드
	       		String APP_CARD_HALBU = CardQuota; //aPayList.getJSONObject(ii).getString("APP_CARD_HALBU").toString(); //카드사 할부       		
	       		String APP_CASH_INFO = "";//aPayList.getJSONObject(ii).getString("APP_CASH_INFO").toString(); //현금영수 입력정보
	       		
	       		Map <String , Object > maps;
	       		
		    	//결제 SEQ
		    	maps = new HashMap<>();
		    	maps.put("COMCD", COMCD);
		    	maps.put("PAY_AMT", Amt);
		    	maps.put("PG_CD", fn_cd);
		    	maps.put("PG_NM", fn_name);
		    	maps.put("APP_TIME", AuthDate);
		    	maps.put("APP_NO", APP_NO);
		    	maps.put("TID", TID);
		    	maps.put("PG_QUTOA", APP_CARD_HALBU);
		    	maps.put("CARD_NO", SEC_CARD_NO1 +"-"+ SEC_CARD_NO2 +"-"+ SEC_CARD_NO3+"-"+ SEC_CARD_NO4);
		    	maps.put("WDATE", ymdhis);
		    	maps.put("SEQ", "");
		    	
		    	//실서버에서 3번 중복저장됨 그래서 중복첵크용 나는 초보자니 이렇게 해도됨 
		    	dataCnt = mapper.rentDblChk(maps);
		    	if (dataCnt ==0) {
			    	mapper.rentOrderSEQ(maps);
			    	//int od_seq = (int) maps.get("SEQ");
	
					// 대간 결제 정보 저장
			    	//선택된 대관 idx 값들 배열화 //146,147,11,333,444
			    	final String[] brdNoArr =  rtn_idx.split(",");
			    	maps.put("ORDER_SEQ", maps.get("SEQ"));
					maps.put("brdNoList", brdNoArr);
					maps.put("SLIP_NO", NEXT_SLIP_NO);
					maps.put("MEM_NO", MEM_NO);
					maps.put("PLACE_CD", PLACE_CD);
					maps.put("RESERVE_DATE", RESERVE_DATE);
					
					maps.put("PAY_AMT", 0);
					
					//대관 결제 정보 저장
					mapper.rentOrderSave(maps);
					
					String APP_GBN	= (P_TYPE.equals("CARD")) ? "1" : "3";
					
					maps.put("APP_DATE", APP_DATE);
					maps.put("APP_GBN", APP_GBN);
					maps.put("SEQ", rtn_idx);
					
					mapper.setPayList2(maps);
					
		    	}
				

//	    } catch (Exception ex) {
//	  		
//	  		
//	  	}
//	    	
	    	
	    	
		}
	    else if (ResultCode.equals("4000")) {// BANK
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else if (ResultCode.equals("4100")) {// VBANK
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else if (ResultCode.equals("A000")) {// cellphone
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else if (ResultCode.equals("B000")) {// CLGIFT
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    }
	    else if (ResultCode.equals("TS10")) {// 토스
	    	// 승인 성공 시 DB 처리 하세요.
			// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
	    	
	    	int goodsAmt = 0;
	    	String goodsNames = "";
	    	int dataCnt = 0;
	    	
		
	    	
	  		//등록강습반 및 프로그램 저장
	  		//String prgList = List; //request.getParameter("PRG");//array 강습반 및 프로그램 정보
	  		//String payList = request.getParameter("PAY"); //(String)requestMap.get("PAY");//array 주문상품 정보
	  		//JSONArray aPrgList = JSONArray.fromObject(prgList); 
	  		//JSONArray aPayList = JSONArray.fromObject(payList);
	  		
	  		String userId = MEM_ID;//(String)requestMap.get("userId");
	  		String userNm = ""; //(String)requestMap.get("userNm");

	  		//ACT_MODE : "Change" 강좌 변경
	  		String ACT_MODE =""; // (String)requestMap.get("ACT_MODE");
			//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
	  		
	  		int CASH_AMT_SUM = 0;//현금결제금액
	  		int CARD_AMT_SUM = Integer.parseInt(Amt);//카드결제금액
	  		int RECEIVE_AMT = 0;
	  		int RETURN_AMT = 0;
	  		
	  		int iDEPOSIT_AMT = 0; //보증금
	  		
	  		
	  			  		
	  		//select * from PAY_CHANGE_INFO
	   		//String NEXT_RECEIPT_NO = mapper.getNextReceiptNo();
	   		//다음정산번호 가져오기 select * from PAY_LIST
	   		//String NEXT_SLIP_NO = mapper.getNextSlipNo();
	   		
	   		//현금영수번호 가 없을경우 보조추가용
	   		String NEXT_APP_NO = "";
	
	   		
	  		
	    	String ymdhis = FormatUtil.getDefaultDate(1, "-","");

			
			
  			String P_COMCD =  fn_cd; // aPayList.getJSONObject(ii).getString("P_COMCD").toString(); //결제업체코드
  			String P_TYPE =  "TOSS"; //aPayList.getJSONObject(ii).getString("P_TYPE").toString(); //결제수단구분(CARD,CASH)
  			
  			//if (P_TYPE.equals("CARD")) {
  				
			
  	  		int PAY_AMT = Integer.parseInt(Amt); //aPayList.getJSONObject(ii).getInt("PAY_AMT"); //결제금액
  			
  			//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
  			String METHOD_CD = "01";// aPayList.getJSONObject(ii).getString("METHOD_CD").toString(); //결제수단코드
       		String APP_DATE = AuthDate; //aPayList.getJSONObject(ii).getString("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
       		String APP_NO = AuthCode; //aPayList.getJSONObject(ii).getString("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
       		String APP_TIME = ""; //aPayList.getJSONObject(ii).getString("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
       		
       		
       		if ("".equals(APP_DATE) || APP_DATE.equals(null)) {
  				APP_DATE = FormatUtil.getDefaultDate(3, "",""); 
  			}
       		
       		if (APP_NO.equals("") || APP_NO == null ) {
       			APP_NO = NEXT_APP_NO;
       		}

       		String APP_CARD_NO = ""; //aPayList.getJSONObject(ii).getString("APP_CARD_NO").toString(); //카드 번호
       		String SEC_CARD_NO1 = "";
       		String SEC_CARD_NO2 = "****";
       		String SEC_CARD_NO3 = "****";
       		String SEC_CARD_NO4 = "";       		
       		if (APP_CARD_NO.length() > 10)
       		{
       			SEC_CARD_NO1 = APP_CARD_NO.substring(4);
       			SEC_CARD_NO4 = APP_CARD_NO.substring(APP_CARD_NO.length()-4, APP_CARD_NO.length());
       		}

       		
       		String APP_CARD_CD = fn_cd; //aPayList.getJSONObject(ii).getString("APP_CARD_CD").toString(); //카드사 코드
       		String APP_CARD_HALBU = CardQuota; //aPayList.getJSONObject(ii).getString("APP_CARD_HALBU").toString(); //카드사 할부       		
       		String APP_CASH_INFO = "";//aPayList.getJSONObject(ii).getString("APP_CASH_INFO").toString(); //현금영수 입력정보
       		
       		Map <String , Object > maps;
       		
	    	//결제 SEQ
	    	maps = new HashMap<>();
	    	maps.put("COMCD", COMCD);
	    	maps.put("PAY_AMT", Amt);
	    	maps.put("PG_CD", fn_cd);
	    	maps.put("PG_NM", fn_name);
	    	maps.put("APP_TIME", AuthDate);
	    	maps.put("APP_NO", APP_NO);
	    	maps.put("TID", TID);
	    	maps.put("PG_QUTOA", APP_CARD_HALBU);
	    	maps.put("CARD_NO", SEC_CARD_NO1 +"-"+ SEC_CARD_NO2 +"-"+ SEC_CARD_NO3+"-"+ SEC_CARD_NO4);
	    	maps.put("WDATE", ymdhis);
	    	maps.put("SEQ", "");
	    	
	    	//실서버에서 3번 중복저장됨 그래서 중복첵크용 나는 초보자니 이렇게 해도됨 
	    	dataCnt = mapper.rentDblChk(maps);
	    	if (dataCnt ==0) {
		    	mapper.rentOrderSEQ(maps);
		    	//int od_seq = (int) maps.get("SEQ");

				// 대간 결제 정보 저장
		    	//선택된 대관 idx 값들 배열화 //146,147,11,333,444
		    	final String[] brdNoArr =  rtn_idx.split(",");
		    	maps.put("ORDER_SEQ", maps.get("SEQ"));
				maps.put("brdNoList", brdNoArr);
				maps.put("MEM_NO", MEM_NO);
				maps.put("PLACE_CD", PLACE_CD);
				maps.put("RESERVE_DATE", RESERVE_DATE);
				maps.put("PAY_AMT", 0);
				
				//대관 결제 정보 저장
				mapper.rentOrderSave(maps);
				
	    	}
			
	    	
	    }
	    else
	    {
	    	
	    
	    }
	    
	    
	    
		//mapper.testSlectx(request);
		Map<String, Object>  rtnData = new HashMap<String, Object>();
		
		
		/*		rtnData.put("ResultCode", ResultCode);
		rtnData.put("PayMethod", PayMethod);
		rtnData.put("MID", MID);
		rtnData.put("Amt", Amt);
		rtnData.put("BuyerName", BuyerName);
		rtnData.put("GoodsName", GoodsName);
		rtnData.put("TID", TID);
		rtnData.put("OID", OID);
		rtnData.put("AuthDate", AuthDate);
		rtnData.put("ResultMsg", ResultMsg);
		rtnData.put("fn_cd", fn_cd);
		rtnData.put("fn_name", fn_name);
		rtnData.put("BuyerTel", BuyerTel);
		rtnData.put("BuyerEmail", BuyerEmail);		
		rtnData.put("PayMethod", PayMethod);
		rtnData.put("PayMethod", PayMethod);
		rtnData.put("PayMethod", PayMethod);
		rtnData.put("PayMethod", PayMethod);
		rtnData.put("PayMethod", PayMethod);
		rtnData.put("PayMethod", PayMethod);
		*/
		rtnData = FormatUtil.formatMapRequest(request);
		rtnData.put("GoodsNameDe", GoodsName);
		rtnData.put("BuyerNameDe", BuyerName);
		rtnData.put("ResultMsgDe", ResultMsg);
		rtnData.put("rtnEndUrl", rtnUrl); //완료후 이동
		
		return rtnData;
		
	}

	
	
	//다음정산번호 가져오기
	//@Override
//	public String getNextSlipNo() {
//		return mapper.getNextSlipNo();
//	}

	
	//다음영수번호 가져오기
	//@Override
//	public String getNextReceiptNo() {
//		return mapper.getNextReceiptNo();
//	}

	private String rtnDecode(String strs) {
		try {
			System.out.println("strs1===>:"+ strs);
			strs = URLDecoder.decode(strs, "UTF-8");
			System.out.println("strs2:"+ strs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("strs2:"+ e.toString());
		}    
		return strs;
	}
	
	@Override
	public Map<String, Object> lockerOrderInsert(HttpServletRequest request) {
		// TODO Auto-generated method stub
				
				String ResultCode		= request.getParameter("ResultCode")==null?"":request.getParameter("ResultCode"); // 결과코드
				String PayMethod		= request.getParameter("PayMethod")==null?"":request.getParameter("PayMethod"); // 지불수단
				String MID				= request.getParameter("MID")==null?"":request.getParameter("MID"); // 상점 ID
				String Amt				= request.getParameter("Amt")==null?"":request.getParameter("Amt"); // 금액
				String BuyerName		= request.getParameter("BuyerName")==null?"":request.getParameter("BuyerName"); // 결제자명
				BuyerName				= rtnDecode(BuyerName);
				String GoodsName		= request.getParameter("GoodsName")==null?"":request.getParameter("GoodsName"); // 상품명
				GoodsName				= rtnDecode(GoodsName);
				//String mallUserID       = request.getParameter("mallUserID")==null?"":request.getParameter("mallUserID"); // 고객사회원ID
				String TID              = request.getParameter("TID")==null?"":request.getParameter("TID"); // 거래번호
				String OID				= request.getParameter("OID")==null?"":request.getParameter("OID"); // 주문번호
				String AuthDate			= request.getParameter("AuthDate")==null?"":request.getParameter("AuthDate"); // 승인일자
				String AuthCode			= request.getParameter("AuthCode")==null?"":request.getParameter("AuthCode"); // 승인번호
				String ResultMsg		= request.getParameter("ResultMsg")==null?"":request.getParameter("ResultMsg"); // 결과메시지
				ResultMsg				= rtnDecode(ResultMsg);
				String VbankNum			= request.getParameter("VbankNum")==null?"":request.getParameter("VbankNum"); // 가상계좌번호
				String VbankName			= request.getParameter("VbankName")==null?"":request.getParameter("VbankName"); // 가상계좌은행명
				
				String fn_cd			= request.getParameter("fn_cd")==null?"":request.getParameter("fn_cd"); // 결제카드사코드
				String fn_name			= request.getParameter("fn_name")==null?"":request.getParameter("fn_name"); // 결제카드사명
				fn_name					= rtnDecode(fn_name);
				String CardQuota			= request.getParameter("CardQuota")==null?"":request.getParameter("CardQuota"); // 할부개월수
				String BuyerTel			= request.getParameter("BuyerTel")==null?"":request.getParameter("BuyerTel"); // 구매자 전화번호
				String BuyerEmail			= request.getParameter("BuyerEmail")==null?"":request.getParameter("BuyerEmail"); // 구매자이메일주소
				String BuyerAuthNum			= request.getParameter("BuyerAuthNum")==null?"":request.getParameter("BuyerAuthNum"); // 구매자주민번호
				String ReceiptType			= request.getParameter("ReceiptType")==null?"":request.getParameter("ReceiptType"); // 현금영수증유형
				String SignValue			= request.getParameter("SignValue")==null?"":request.getParameter("SignValue"); // 위변조 사인값
				
				String TaxCD			= request.getParameter("TaxCD")==null?"":request.getParameter("TaxCD"); // TAX 코드
				String SvcAmt			= request.getParameter("SvcAmt")==null?"":request.getParameter("SvcAmt"); // 봉사료
				String Tax			= request.getParameter("Tax")==null?"":request.getParameter("Tax"); // 부가세
				String AcquCardCode			= request.getParameter("AcquCardCode")==null?"":request.getParameter("AcquCardCode"); // 매입사코드 

				String DivideInfo = request.getParameter("DivideInfo")==null?"":request.getParameter("DivideInfo"); // 서브몰 정보 
				String rtnUrl 		= request.getParameter("ReturnURL")==null?"":request.getParameter("ReturnURL"); // 서브몰 정보
				rtnUrl				= (rtnUrl == null) ? "" : rtnUrl;
				
				String SspMallID			= request.getParameter("SspMallID")==null?"":request.getParameter("SspMallID"); // 매입사코드
				String MemberNo			= request.getParameter("MemberNo")==null?"":request.getParameter("MemberNo"); // 매입사코드
				String userParam		= request.getParameter("q")==null? "//" : request.getParameter("q"); // 사용자 파람
										String [] arrayTmp 	= userParam.split("\\/"); //20201225/8
				String COMCD		= arrayTmp[0]; //장소
				String MEM_NO			= arrayTmp[1]; //회원번호
				String RENT_NO			= arrayTmp[2]; //락커 고유번호				
				String MEM_ID			= arrayTmp[3]; //memID

				final Member member = memberMapper.findById(MEM_ID);
				//String MEM_NO		= member.getMemNo();
				String MEM_NM		= member.getMemNm();
		    	//String MEM_ID = "powerjyc1"; //members.getId();
		    	//String MEM_NO = "00135079"; //members.getMemNo();
		    	//String MEM_NM = "정연철"; //members.getMemNm();
		    	
				/*
				//일단 작동안되서 세션으로 적용
				final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				final String MEM_ID = auth.getName();
				Map <String,Object> member = mypgService.myInfo(MEM_ID);
				
				String MEM_NO = (String) member.get("MEM_NO");
				String MEM_NM = (String) member.get("MEM_NM");
		    	*/
		    	
				// 웹 링크 버전일 경우에 실제 스마트로 서버의 승인 값을 검증 하기 위해서 아래의 값을 비교 합니다..
			    if (ResultCode.equals("3001")) {// CARD
			    	// 승인 성공 시 DB 처리 하세요.
					// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
			    	
//			    	try {
			    	
			    			
				    	int goodsAmt = 0;
				    	String goodsNames = "";
				    	int dataCnt = 0;
				    	
					
				    	
				  		//등록강습반 및 프로그램 저장
				  		//String prgList = List; //request.getParameter("PRG");//array 강습반 및 프로그램 정보
				  		//String payList = request.getParameter("PAY"); //(String)requestMap.get("PAY");//array 주문상품 정보
				  		//JSONArray aPrgList = JSONArray.fromObject(prgList); 
				  		//JSONArray aPayList = JSONArray.fromObject(payList);
				  		
				  		String userId = MEM_ID;//(String)requestMap.get("userId");
				  		String userNm = ""; //(String)requestMap.get("userNm");
			
				  		//ACT_MODE : "Change" 강좌 변경
				  		String ACT_MODE =""; // (String)requestMap.get("ACT_MODE");
						//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
				  		
				  		int CASH_AMT_SUM = 0;//현금결제금액
				  		int CARD_AMT_SUM = Integer.parseInt(Amt);//카드결제금액
				  		int RECEIVE_AMT = 0;
				  		int RETURN_AMT = 0;
				  		
				  		int iDEPOSIT_AMT = 0; //보증금
				  		
				  		
				  		
			//	  		//결제금액에서 보증금을 빼기 위한
			//	  		for (int i=0;i<aPrgList.size();i++ ) {
			//	  			String GUBUN = aPrgList.getJSONObject(i).getString("ITM_TYPE").toString();
			//	  			if ("war".equals(ITM_TYPE)){
			//	  				int tmpDEPOSIT_AMT = aPrgList.getJSONObject(i).getInt("DEPOSIT_AMT"); // 보증금	  			
			//		  			iDEPOSIT_AMT =  iDEPOSIT_AMT + tmpDEPOSIT_AMT;
			//	  			}
			//	  		}  		
				  			  		
				  		//select * from PAY_CHANGE_INFO
				   		String NEXT_RECEIPT_NO = mapper.getNextReceiptNo();
				   		//다음정산번호 가져오기 select * from PAY_LIST
				   		String NEXT_SLIP_NO = mapper.getNextSlipNo();
				   		
				   		//현금영수번호 가 없을경우 보조추가용
				   		String NEXT_APP_NO = "";
				
				   		
						//주문_결제정보(CALC_MASTER) 저장  
				   		//select * from CALC_MASTER order by  WRITE_DH desc
						Map<String, Object> requestMapCalcMaster = new HashMap<String, Object>();
						requestMapCalcMaster.put("COMCD", COMCD);
					  	requestMapCalcMaster.put("USER_ID", MEM_NO);
					  	requestMapCalcMaster.put("RECEIPT_NO", NEXT_RECEIPT_NO);	  	
						requestMapCalcMaster.put("SLIP_NO", NEXT_SLIP_NO);
						requestMapCalcMaster.put("MEM_NO", MEM_NO);
						requestMapCalcMaster.put("PAY_AMT", CASH_AMT_SUM + CARD_AMT_SUM);//결제금액
						requestMapCalcMaster.put("CASH_AMT",CASH_AMT_SUM);
						requestMapCalcMaster.put("CARD_AMT",CARD_AMT_SUM);
						
						
						//주문_결제정보(CALC_MASTER) 저장
				  		mapper.setCalcMaster(requestMapCalcMaster);
				  		
				    	String ymdhis = FormatUtil.getDefaultDate(1, "-","");
			
						
						
			  			String P_COMCD =  fn_cd; // aPayList.getJSONObject(ii).getString("P_COMCD").toString(); //결제업체코드
			  			String P_TYPE =  "CARD"; //aPayList.getJSONObject(ii).getString("P_TYPE").toString(); //결제수단구분(CARD,CASH)
			  			
			  			//if (P_TYPE.equals("CARD")) {
			  				
		  			
			  	  		int PAY_AMT = Integer.parseInt(Amt); //aPayList.getJSONObject(ii).getInt("PAY_AMT"); //결제금액
			  			
			  			//METHOD_CD:결제수단코드(00:현금, 99:현금영수증, 01:비씨카드.....)
			  			String METHOD_CD = "01";// aPayList.getJSONObject(ii).getString("METHOD_CD").toString(); //결제수단코드
			       		String APP_DATE = AuthDate; //aPayList.getJSONObject(ii).getString("APP_DATE").toString(); //카드_승인일시__van또는pg또는현금영수증
			       		String APP_NO = AuthCode; //aPayList.getJSONObject(ii).getString("APP_NO").toString(); //카드_승인번호__van또는pg또는현금영수증
			       		String APP_TIME = ""; //aPayList.getJSONObject(ii).getString("APP_TIME").toString(); //카드_승인시분Hi__van또는pg또는현금영수증
			       		
			       		
			       		if ("".equals(APP_DATE) || APP_DATE.equals(null)) {
			  				APP_DATE = FormatUtil.getDefaultDate(3, "",""); 
			  			}
			       		
			       		if (APP_NO.equals("") || APP_NO == null ) {
			       			APP_NO = NEXT_APP_NO;
			       		}
			
			       		String APP_CARD_NO = ""; //aPayList.getJSONObject(ii).getString("APP_CARD_NO").toString(); //카드 번호
			       		String SEC_CARD_NO1 = "";
			       		String SEC_CARD_NO2 = "****";
			       		String SEC_CARD_NO3 = "****";
			       		String SEC_CARD_NO4 = "";       		
			       		if (APP_CARD_NO.length() > 10)
			       		{
			       			SEC_CARD_NO1 = APP_CARD_NO.substring(4);
			       			SEC_CARD_NO4 = APP_CARD_NO.substring(APP_CARD_NO.length()-4, APP_CARD_NO.length());
			       		}

			       		
			       		String APP_CARD_CD = fn_cd; //aPayList.getJSONObject(ii).getString("APP_CARD_CD").toString(); //카드사 코드
			       		String APP_CARD_HALBU = CardQuota; //aPayList.getJSONObject(ii).getString("APP_CARD_HALBU").toString(); //카드사 할부       		
			       		String APP_CASH_INFO = "";//aPayList.getJSONObject(ii).getString("APP_CASH_INFO").toString(); //현금영수 입력정보
			       		
			       		
			       		//승인구분__1_카드결제승인__2_카드승인취소__3_현금영수증승인__4_현금영수증승인취소
			       		String APP_GBN	= (P_TYPE.equals("CARD")) ? "1" : "3";
			       		
			  			Map<String, Object> requestMapPayList = new HashMap<String, Object>();
			  			
				  		requestMapPayList.put("COMCD", COMCD);
				  		requestMapPayList.put("USER_ID", userId);
				  		requestMapPayList.put("MEM_NO", MEM_NO);
				  		requestMapPayList.put("SLIP_NO", NEXT_SLIP_NO);  	
				  		requestMapPayList.put("RECEIPT_NO", NEXT_RECEIPT_NO);
				  		requestMapPayList.put("PAY_AMT", PAY_AMT - iDEPOSIT_AMT);//결제금액(보증금을 뺌)
				  		requestMapPayList.put("APP_DATE", APP_DATE);//승인일시__van또는pg또는현금영수증
				  		requestMapPayList.put("APP_NO", APP_NO);//승인번호__van또는pg또는현금영수증
				  		requestMapPayList.put("APP_GBN", APP_GBN);//승인구분
				  		requestMapPayList.put("APP_TIME", APP_TIME);//승인시분Hi__van또는pg또는현금영수증  		  		
				  		requestMapPayList.put("P_COMCD", P_COMCD);//결제업체
				  		requestMapPayList.put("P_TYPE", P_TYPE);//지불수단
				    	requestMapPayList.put("METHOD_CD", METHOD_CD);//지불수단코드(카드:02,현금:00)
				    	requestMapPayList.put("CHANGE_YN", "N");//변경여부
				    	requestMapPayList.put("CANCEL_YN", "N");//승인취소여부YN
				    	requestMapPayList.put("PAY_SEQ", 1);//결제 순번(카드,현금 2건이상경우 순번) 
				    	
				    	requestMapPayList.put("SEC_CARD_NO1", SEC_CARD_NO1);//카드번호1
				    	requestMapPayList.put("SEC_CARD_NO2", SEC_CARD_NO2);//카드번호1
				    	requestMapPayList.put("SEC_CARD_NO3", SEC_CARD_NO3);//카드번호1
				    	requestMapPayList.put("SEC_CARD_NO4", SEC_CARD_NO4);//카드번호1
				    	
				    	requestMapPayList.put("CARD_SEC", APP_CARD_CD);//카드사 코드
				    	requestMapPayList.put("CARD_SEC2", "");//
				    	requestMapPayList.put("HALBU_CNT", APP_CARD_HALBU);//카드사 할부
				    	
				    	requestMapPayList.put("TID", TID);//승인금액 정보
				    	
				    	requestMapPayList.put("APP_AMT", PAY_AMT);//승인금액 정보
				    	requestMapPayList.put("CASH_USER_INFO", APP_CASH_INFO);//현금영수 정보
				    	
				    	requestMapPayList.put("STORE_NO", "");//???????????
				    	requestMapPayList.put("PAY_LIST_YN", "");//
				    	requestMapPayList.put("WRITER", userNm);//운영자

				    	requestMapPayList.put("WRITE_DH", ymdhis);//
			    	
				    	
				    	//
				    	
				    	// select * from PAY_LIST  order by  WRITE_DH desc
				    	// select * from card_app_hist_damo  order by  WRITE_DH desc
				    	
				    	//결제정보 저장
						mapper.setPayList(requestMapPayList);
						//일마감관리 > 일마감관리 > 카드결제처리현황
						mapper.setPayList2(requestMapPayList);
						

						// 사물함 결제정보 저장
				    	Map <String , Object > maps = new HashMap<>();				    		
						maps.put("COMCD", COMCD);
						maps.put("MEM_NO", MEM_NO);
						maps.put("RENT_NO", RENT_NO);
						Map<String, Object> relocker = mypageMapper.reLocker(maps);
						relocker.put("SLIP_NO", NEXT_SLIP_NO);
						relocker.put("MEM_ID", MEM_ID);
						mapper.lockerOrderSave(relocker);
						
						
						

//			    } catch (Exception ex) {
//			  		
//			  		
//			  	}
//			    	
			    	
			    	
				}
			    else if (ResultCode.equals("4000")) {// BANK
			    	// 승인 성공 시 DB 처리 하세요.
					// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
			    }
			    else if (ResultCode.equals("4100")) {// VBANK
			    	// 승인 성공 시 DB 처리 하세요.
					// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
			    }
			    else if (ResultCode.equals("A000")) {// cellphone
			    	// 승인 성공 시 DB 처리 하세요.
					// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
			    }
			    else if (ResultCode.equals("B000")) {// CLGIFT
			    	// 승인 성공 시 DB 처리 하세요.
					// TID 결제 성공한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
			    }
			    else
			    {
			    	
			    
			    }
			    
			    
			    
				//mapper.testSlectx(request);
				Map<String, Object>  rtnData = new HashMap<String, Object>();
				
				
				/*		rtnData.put("ResultCode", ResultCode);
				rtnData.put("PayMethod", PayMethod);
				rtnData.put("MID", MID);
				rtnData.put("Amt", Amt);
				rtnData.put("BuyerName", BuyerName);
				rtnData.put("GoodsName", GoodsName);
				rtnData.put("TID", TID);
				rtnData.put("OID", OID);
				rtnData.put("AuthDate", AuthDate);
				rtnData.put("ResultMsg", ResultMsg);
				rtnData.put("fn_cd", fn_cd);
				rtnData.put("fn_name", fn_name);
				rtnData.put("BuyerTel", BuyerTel);
				rtnData.put("BuyerEmail", BuyerEmail);		
				rtnData.put("PayMethod", PayMethod);
				rtnData.put("PayMethod", PayMethod);
				rtnData.put("PayMethod", PayMethod);
				rtnData.put("PayMethod", PayMethod);
				rtnData.put("PayMethod", PayMethod);
				rtnData.put("PayMethod", PayMethod);
				*/
				rtnData = FormatUtil.formatMapRequest(request);
				rtnData.put("GoodsNameDe", GoodsName);
				rtnData.put("BuyerNameDe", BuyerName);
				rtnData.put("ResultMsgDe", ResultMsg);
				rtnData.put("rtnEndUrl", rtnUrl); //완료후 이동
				
				return rtnData;
	}

}
