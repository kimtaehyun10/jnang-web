<%
/******************************************************************************
*
*	@ SYSTEM NAME		: URL Noti Test 페이지
*	@ PROGRAM NAME		: inform.jsp
*	@ MAKER				: sajang
*	@ MAKE DATE			: 2017.12.01
*	@ PROGRAM CONTENTS	: URL Noti Test 페이지
*
************************** 변 경 이 력 *****************************************
* 번호	작업자		작업일				변경내용
*	1	스마트로	2017.12.01		URL Noti 결과 페이지
*	2   스마트로   2018.12.28       결제 통보 결과 응답
*******************************************************************************/
%>
<%@ page import="java.util.*" %>
<%
	boolean result = false; 
	String PayMethod		= request.getParameter("PayMethod");
	String MID				= request.getParameter("MID");
	String mallUserID		= request.getParameter("mallUserID");
	String Amt				= request.getParameter("Amt");
	String name				= request.getParameter("name");
	String GoodsName		= request.getParameter("GoodsName");
	String TID				= request.getParameter("TID");
	String OTID				= request.getParameter("OTID");
	String OID				= request.getParameter("OID");
	String AuthDate			= request.getParameter("AuthDate");
	String AuthCode			= request.getParameter("AuthCode");
	String ResultCode		= request.getParameter("ResultCode");
	String ResultMsg		= request.getParameter("ResultMsg");
	String state_cd			= request.getParameter("state_cd");
	String FnCd				= request.getParameter("FnCd");
	String FnName			= request.getParameter("FnName");
	String pinNo			= request.getParameter("pinNo");
	String CardQuota		= request.getParameter("CardQuota");
	String BuyerEmail		= request.getParameter("BuyerEmail");
	String BuyerTel			= request.getParameter("BuyerTel");
	String BuyerAuthNum		= request.getParameter("BuyerAuthNum");
	String VbankNum			= request.getParameter("VbankNum");
	String VbankName		= request.getParameter("VbankName");
	String ReceiptType		= request.getParameter("ReceiptType");
	String RcptAppNo		= request.getParameter("RcptAppNo");
	String RcptCcNo			= request.getParameter("RcptCcNo");
	String CardUsePoint		= request.getParameter("CardUsePoint");
	String SignValue		= request.getParameter("SignValue");

	System.out.print("지불수단(PayMethod):[" + PayMethod + "]<br>");
	System.out.print("상점ID(MID):[" + MID + "]<br>");
	System.out.print("고객ID(mallUserID):[" + mallUserID + "]<br>");
	System.out.print("금액(Amt):[" + Amt + "]<br>");		
	System.out.print("구매자명(name):[" + name + "]<br>");
	System.out.print("상품명(GoodsName):[" + GoodsName + "]<br>");
	System.out.print("거래번호(TID):[" + TID + "]<br>");
    System.out.print("원거래 거래번호(OTID):[" + OTID + "]<br>");
    System.out.print("주문번호(OID):[" + OID + "]<br>");
	System.out.print("승인 및 취소일시(AuthDate):[" + AuthDate + "]<br>");
	System.out.print("승인번호(AuthCode):[" + AuthCode + "]<br>");
	System.out.print("결과코드(ResultCode):[" + ResultCode + "]<br>");
	System.out.print("결과메시지(ResultMsg):[" + ResultMsg + "]<br>");
    System.out.print("승인취소구분(state_cd):[" + state_cd + "]<br>");
    System.out.print("결제카드사코드(FnCd):[" + FnCd + "]<br>");
    System.out.print("결제카드사명(FnName):[" + FnName + "]<br>");
    System.out.print("카드번호(pinNo):[" + pinNo + "]<br>");
    System.out.print("할부개월수(CardQuota):[" + CardQuota + "]<br>");
    System.out.print("구매자이메일주소(BuyerEmail):[" + BuyerEmail + "]<br>");
    System.out.print("구매자전화번호(BuyerTel):[" + BuyerTel + "]<br>");
    System.out.print("구매자주민번호(BuyerAuthNum):[" + BuyerAuthNum + "]<br>");
    System.out.print("가상계좌번호(VbankNum):[" + VbankNum + "]<br>");
    System.out.print("가상계좌은행명(VbankName):[" + VbankName + "]<br>");
    System.out.print("현금영수증유형(ReceiptType):[" + ReceiptType + "]<br>");
    System.out.print("사용포인트 국민카드 전용(CardUsePoint):[" + CardUsePoint + "]<br>");
    System.out.print("위변조 사인값(SignValue):[" + SignValue + "]<br>");
	
    if(ResultCode != null ) {
    	
  	  if("3001".equals(ResultCode)){ //CARD
 	     // 결제 성공시 DB처리 하세요.
 	     // TID 결제 취소한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
 	     //if(DB 처리 및 내부처리 로직 성공시) {
 	      result = true;
 	     //}
 	  }

 	  if("4000".equals(ResultCode)){ //BANK
 	     // 결제 성공시 DB처리 하세요.
 	     // TID 결제 취소한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
 	     //if(DB 처리 및 내부처리 로직 성공시) {
 	      result = true;
 	     //}
 	  }
 	  if("4100".equals(ResultCode)){ //VBANK 체번완료
 	     // 결제 성공시 DB처리 하세요.
 	     // TID 결제 취소한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
 	     //if(DB 처리 및 내부처리 로직 성공시) {
 	      result = true;
 	     //}
 	  }
 	  if("4110".equals(ResultCode)){ //VBANK 입금완료
 	     // 결제 성공시 DB처리 하세요.
 	     // TID 결제 취소한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
 	     //if(DB 처리 및 내부처리 로직 성공시) {
 	      result = true;
 	     //}
 	  }
 	  if("A000".equals(ResultCode)){ //cellphone
 	     // 결제 성공시 DB처리 하세요.
 	     // TID 결제 취소한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
 	     //if(DB 처리 및 내부처리 로직 성공시) {
 	      result = true;
 	     //}
 	  }
 	  if("7001".equals(ResultCode)){ //현금영수증
 	     // 결제 성공시 DB처리 하세요.
 	     // TID 결제 취소한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
 	     //if(DB 처리 및 내부처리 로직 성공시) {
 	      result = true;
 	     //}
 	  }
 	  // 결제 취소
 	  if("2001".equals(ResultCode)){
 	     // 취소 성공시 DB처리 하세요.
 	     //TID 결제 취소한 데이터 존재시 UPDATE, 존재하지 않을 경우 INSERT
 	     //if(DB 처리 및 내부처리 로직 성공시) {
 	      result = true;
 	     //}
 	  }
 	  if("2211".equals(ResultCode)){
 	     // 환불
 	  }
 	  if("2013".equals(ResultCode)){
  	     // 이미(기) 취소 거래임
  	  }
	}
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
</head>
	<body>
		<%if(result) { %>
		OK
		<% } %> 
	</body>
</html>