<%@page import="java.util.Date"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="java.util.Enumeration"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.dwict.jfmc.client.mem.model.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/rent.js"></script>
<%
/******************************************************************************
*
*	@ SYSTEM NAME		: 결제요청페이지
*	@ PROGRAM NAME		: mainPayConfirm.jsp
*	@ MAKER				: sajang
*	@ MAKE DATE			: 2017.12.01
*	@ PROGRAM CONTENTS	: 결제요청페이지
*
************************** 변 경 이 력 *****************************************
* 번호	작업자		작업일				변경내용
*	1	스마트로	2017.12.01		결제요청페이지
*******************************************************************************/
	
	//InetAddress inet = InetAddress.getLocalHost(); // 서버 IP 가져오기
	//Timestamp toDay = new Timestamp((new Date()).getTime()); // 현재날짜
	//Timestamp nxDay = getTimestampWithSpan(toDay, 1); // 가상계좌 입금만료일  1일후 가져오기
	//String VbankExpDate = nxDay.toString();
	//VbankExpDate = VbankExpDate.substring(0, 10); 
	//VbankExpDate = VbankExpDate.replaceAll("-", "");
	String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
	String goodsAmt = "10";
	String GoodsName = "";
	//상품주문번호	
	
	String merchantKey  = "";//(String) request.getAttribute("merchantKey");
	String storeMID 	= "";//(String) request.getAttribute("storeMID");
	String payURL 		= "";//(String) request.getAttribute("URL");
	//out.println("payURL:"+ payURL +"<BR>");
	//out.println("storeMID:"+ storeMID +"<BR>");
	//out.println("merchantKey:"+ merchantKey +"<BR>");
	
	String Moid = "Moid"; 
	final String DEV_PAY_ACTION_URL = "https://tpay.smilepay.co.kr/interfaceURL.jsp";	//개발테스트
	final String PRD_PAY_ACTION_URL = "https://pay.smilepay.co.kr/interfaceURL.jsp";	//운영
	String actionUrl = payURL;
	String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String MID = storeMID;//"SMTPAY001m";
	//String MID = "jungnan01m"; //위에 URl 변경 DEV_PAY_ACTION_URL ==> PRD_PAY_ACTION_URL
	String DivideInfo = ""; //서브몰 결제 정보 //request.getParameter("DivideInfo");
	
	String ReturnURL = URL +"/smartPay/rentPay"; //Vos.getRtnPayURL(); //"http://localhost:8080/smartPay/returnPay"; //"https://tpay.smilepay.co.kr/returnPay.jsp"; //리턴url
	String EncodingType = "utf8"; //euckr/utf8
	
 	// 상점서명키 (꼭 해당 상점키로 바꿔주세요)
 	//merchantKey = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";
 	//String merchantKey = "KiS8NWHjZ49FzG91HMI9hVXOSxYrvFBKzl2bYpr2ac7lg369iZxy0xhCJfg4juCuVH27mO/TQ4kG2qnjEr5Z4Q==";
 	
 	//VoPay test1 = new VoPay();
 	//VoPay Vos = (VoPay)request.getAttribute("VoPay");

 	// 상점 ID MID


	/*
	if(StringUtils.isNotEmpty(DivideInfo) ) // Base64 인코딩(utf-8 인코딩)
	{
		String temp = DivideInfo.replaceAll("&#39;","\"");
		Charset euckrCharset = Charset.forName("utf-8");
		ByteBuffer byteBuffer = euckrCharset.encode(temp);
		byte[] euckrStringBuffer = new byte[byteBuffer.remaining()];
		byteBuffer.get(euckrStringBuffer);
								
		String b64Enc = new String(Base64.encodeBase64(euckrStringBuffer));
		DivideInfo = b64Enc;
	} */

	//String EncryptData = encodeMD5HexBase64(ediDate + MID + goodsAmt + merchantKey);


	/*
	String encodeString = "";
	String encodedString = URLEncoder.encode(encodeString, "EUC-KR");
	System.out.println(encodedString); 
	encodedString = "%EC%B9%B4%EB%93%9C+%EA%B2%B0%EC%A0%9C+%EC%84%B1%EA%B3%B5";
	String decodedString = URLDecoder.decode(encodedString, "UTF-8");    
	System.out.println(decodedString); // 자바킹75! *-blog.me+=_~ 
	*/
	Member member 	= (Member) session.getAttribute("member");
	String MEM_ID	= member.getId();
	String MEM_NM 	= member.getMemNm();
	String MEM_MAIL = member.getEmail();
	
	
%> 

<script type="text/javascript">


var encodingType = "EUC-KR";//EUC-KR

function setAcceptCharset(form) 
{
	var browser = getVersionOfIE();
    if(browser != 'N/A')
    	document.charset = encodingType;//ie
    else
    	form.charset = encodingType;//else
}

function getVersionOfIE() 
{ 
	 var word; 
	 var version = "N/A"; 

	 var agent = navigator.userAgent.toLowerCase(); 
	 var name = navigator.appName; 

	 // IE old version ( IE 10 or Lower ) 
	 if ( name == "Microsoft Internet Explorer" ) 
	 {
		 word = "msie "; 
	 }
	 else 
	 { 
		 // IE 11 
		 if ( agent.search("trident") > -1 ) word = "trident/.*rv:"; 

		 // IE 12  ( Microsoft Edge ) 
		 else if ( agent.search("edge/") > -1 ) word = "edge/"; 
	 } 

	 var reg = new RegExp( word + "([0-9]{1,})(\\.{0,}[0-9]{0,1})" ); 

	 if ( reg.exec( agent ) != null  ) 
		 version = RegExp.$1 + RegExp.$2; 

	 return version; 
}




function goPay() 
{
	
	var form = document.tranMgr;
	var actinURL = form.actionUrl.value;


	
	/* if (form.GoodsCnt.value == "0") {
		alert("수강 신청 내역이 없습니다.");
		return false;
	} */
		
	
	/* if (GoodsName.value == "" || Amt.value == "" ||ReturnURL.value == "")
	{
		alert("필수 입력값 오류!!");
		return false;
	} */
	
	
	
	//form.GoodsName.value = GoodsName;
	//form.Amt.value = AMT;
	//form.ReturnURL.value = ReturnURL;
	
	form.action = actinURL;
	
	//form.GoodsName.value = encodeURI("<%//=GoodsName%>");
	//form.BuyerName.value = "<%//=BuyerName%>";
	//form.BuyerAddr.value = "<%//=BuyerAddr%>";
	//form.EncryptData.value = "<%//=EncryptData%>";
	//form.DivideInfo.value = "<%//=DivideInfo%>";
			
	if(form.FORWARD.value == 'Y') // 화면처리방식 Y(권장):상점페이지 팝업호출
	{
		var popupX = ((window.screen.width) / 2) - (545 / 2);
		var popupY = ((window.screen.height) /2) - (573 / 2);
					
		var winopts= "width=545,height=573,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no,left="+ popupX + ", top="+ popupY + ", screenX="+ popupX + ", screenY= "+ popupY;
		var win =  window.open("", "payWindow", winopts);
		
		try{
		    if(win == null || win.closed || typeof win.closed == 'undefined' || win.screenLeft == 0) { 
		    	alert('브라우저 팝업이 차단으로 설정되었습니다.\n 팝업 차단 해제를 설정해 주시기 바랍니다.');
		    	//return false;
		    }
		}catch(e){}
	    
		form.target = "payWindow";//payWindow  고정
		form.submit();
	}
	else // 화면처리방식 N:결제모듈내부 팝업호출
	{
		form.target = "payFrame";//payFrame  고정
		form.submit();
	}
	
	return false;
}

function rentDetail(comNm, rentIdx, writeDh) {
	
	var userId = '<%=MEM_ID%>';
	
		window.location.href='/mypage/rentDetail?rentIdx='+rentIdx+'&writeDh=' + writeDh + '&userId=' + userId;
	}


</script>

<div id="sub_cart" class=' '>
	<form name="frm" method="post">
	<table class="stbl_l1a">
		<colgroup>
			<col width="5%"><col width="5%"><col width="*"><col width="120"><col width="100"><col width="100"><col width="100">
		</colgroup>
		<thead>
			<tr>
				<th>No.</th>
				<th>선택</th>
				<th>대관장소</th>
				<th>대관료</th>
				<th>대관일</th>
				<th>확정여부</th>
				<th>접수일</th>
				<th>결제하기</th>
			</tr>
		</thead>
		<tbody id="dataList">
			<c:forEach items="${rentList}" var="result" varStatus="status">
			<!-- 체육관 대관만 현재 확인 가능 -->
			<%-- <c:if test="${result.PLACE_NM ne '축구장'  && result.PLACE_NM ne '야구장' && result.PALCE_NM ne '테니스장' && result.PLACE_NM ne '잔디운동장'}"> --%>
			<%-- <c:if test="${result.PLACE_GROUP == '1' }"> --%>
			<tr>
				<td>${status.count}</td>
				<td>
					<c:if test="${result.APP_TYPE == '10' or result.APP_TYPE == '5'}">	
					<input type="checkbox" id="cbox" name="cbox[]" value="${result.RENT_IDX}" style='width:30px; height:30px;'></td>
					</c:if>
				<td onclick="rentDetail('${result.COMNM}', '${result.RENT_IDX }', '${result.WRITE_DH }');" style="cursor: pointer;">${result.COMNM}  &nbsp; > &nbsp;  ${result.PLACE_NM} 
					<c:if test="${result.OBJECT ne null}">
						&nbsp; ( ${result.OBJECT} )
						${result.SDATE }~${result.EDATE}
					</c:if>
					<c:if test="${result.place_tab ne 0}">
						&nbsp; / ( ${result.place_tab} ) 코트
					</c:if>
					<c:if test="${result.add_light ne 0}">
						&nbsp; / 조명 사용
					</c:if>
				</td>
				<td>
					<%-- <c:if test="${result.PLACE_NM == '축구장' }">
						
					</c:if> --%>
					<%-- <c:if test="${result.PLACE_NM == '생활체육실' }"> --%>
						<fmt:formatNumber value="${result.PAY_AMT}" pattern="#,###"/>원
					<%-- </c:if> --%>
				</td>
				<td>${result.RESERVE_DATE}</td>
				<td>${result.APP_TYPE_NM}</td>
				<td>${result.CHNG_DH}</td>
				<td>
				 <c:set var="test" value="${result.COMNM } > ${result.PLACE_NM }" />
				 <%
				    String test = (String)pageContext.getAttribute("test") ;
				 	test = URLEncoder.encode(test, "EUC-KR");
				 %>
				
					<c:if test="${result.APP_TYPE == '15' }">
						<input type="button" class="size_m2 btn_green1" value="결제" onClick="test('<%=ediDate%>', '${result.PAY_AMT}','<%=test%>', '${result.RESERVE_DATE}', ${result.PLACE_CD}, '${result.COMCD }',${result.RENT_IDX },'${otherData.MEM_ID }','<%=actionUrl%>' );">
					</c:if>
					<c:if test="${result.APP_TYPE == '30' }">
						<input type="button" class="size_m2 btn_green1" value="취소" onClick="cancelPay('${result.TID}', '${result.SLIP_NO}', '${result.PAY_AMT}','${result.PAY_DATE }','${result.COMCD}' );">
					</c:if> 
				</td>
			</tr>
			<%-- </c:if> --%>
			</c:forEach>
		</tbody>
	</table>
	</form>
    <div class="btnarea margin_t80">
        <a href="#none" onClick="data.list('del');" id=" " class="gray2">선택 대관 취소</a>
    </div>
</div>

<%

String EncryptData = encodeMD5HexBase64(ediDate + MID + goodsAmt + merchantKey);


try {
	GoodsName = URLEncoder.encode(GoodsName, "EUC-KR");
	MEM_NM = URLEncoder.encode(MEM_NM, "EUC-KR");
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>

<form name="tranMgr" method="post" action="">
	<table class="type">
	    <tbody>
		    <input type="hidden" name="PayMethod" maxlength="2" value="CARD">
		    <input type="hidden" name="PayType" maxlength="2" value="">
			<!-- 수량 -->
		    <input type="hidden" id="GoodsCnt" name="GoodsCnt" maxlength="2" value="1">
			<!--<div>상품명:</div>-->
		    <input type="hidden" id="GoodsName" name="GoodsName" maxlength="2" value="<%=GoodsName%>">
			
			<!-- <div>상품금액:</div> -->
		    <input type="hidden" id="Amt" name="Amt" maxlength="2" value="<%=goodsAmt%>">
			
			<!-- <div>주문번호:</div> -->
		    <input type="hidden" name="Moid" maxlength="2" value="Moid">
	    
		    <input type="hidden" id="MID" name="MID" maxlength="2" value="<%=MID%>">
			
		    <%-- <input type="hidden" name="ReturnURL" maxlength="2" value="<%=ReturnURL%>?q=${otherData.MEM_ID}//"> --%>
			<input type="hidden" id="returnURL" name="ReturnURL" maxlength="2" value="<%=ReturnURL%>">
		    <input type="hidden" name="ReceiptType" maxlength="2" value="0">
			
		    <input type="hidden" name="RetryURL" maxlength="2" value="<%=ReturnURL%>?q=${otherData.MEM_ID}//">
			<!-- 
		    <tr>
		        <th scope="row">mallUserID</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="mallUserID" maxlength="2" value=""> -->
			<!-- <div>구매자:</div> -->
		    <input type="hidden" id="BuyerName" name="BuyerName" maxlength="2" value="BuyerName">
			<!-- <div>연락처:</div> -->
		    <input type="hidden" id="BuyerTel" name="BuyerTel" maxlength="2" value="BuyerTel">
			<!-- <div>이메일:</div> -->
		    <input type="hidden" id="BuyerEmail" name="BuyerEmail" maxlength="2" value="BuyerEmail">
			
			<!-- 
		    <tr>
		        <th scope="row">ParentEmail</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="ParentEmail" maxlength="2" value="">
			 -->
		    <input type="hidden" id="BuyerAddr" name="BuyerAddr" maxlength="2" value="BuyerAddr">
			<!-- 
		    <tr>
		        <th scope="row">BuyerPostNo</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="BuyerPostNo" maxlength="2" value="">
			 -->
		    <input type="hidden" name="UserIP" maxlength="2" value="<%//=InetAddress[0]%>">
  
		    <input type="hidden" name="MallIP" maxlength="2" value="<%//=InetAddress%>">
		    
		    <input type="hidden" name="VbankExpDate" maxlength="2" value="<%//=VbankExpDate%>">
			
		    <input type="hidden" id="EncryptData" name="EncryptData" maxlength="2" value="EncryptData">
    
		    <input type="hidden" name="FORWARD" maxlength="2" value="Y">
			
		    <input type="hidden" name="MallResultFWD" maxlength="2" value="">
			
		    <input type="hidden" name="TransType" maxlength="2" value="0">
			
		    <input type="hidden" name="EncodingType" maxlength="2" value="<%=EncodingType%>">
			
		    <input type="hidden" name="OpenType" maxlength="2" value="KR">
	    
		    <!-- <input type="hidden" name="GoodsCl" maxlength="2" value="">-->
			<!-- 
		    <tr>
		        <th scope="row">OfferPeriod</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="OfferPeriod" maxlength="2" value="">
			
		    <tr>
		        <th scope="row">fn_cd</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="fn_cd" maxlength="2" value="">
			 
		    <tr>
		        <th scope="row">CardQuota</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="CardQuota" maxlength="2" value="">
			
		    <tr>
		        <th scope="row">SocketYN</th>
		        <td>N</td>
		    </tr>		    
		    <input type="hidden" name="SocketYN" maxlength="2" value="N">
			
		    <tr>
		        <th scope="row">SspMallID</th>
		        <td></td>
		    </tr>
		    <input type="hidden" id="SspMallID" name="SspMallID" maxlength="2" value="">
		    <tr>
		        <th scope="row">SkinColor</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="SkinColor" maxlength="2" value="">
			
		    <tr>
		        <th scope="row">TaxCD</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="TaxCD" maxlength="2" value="">
			
		    <tr>
		        <th scope="row">SvcAmt</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="SvcAmt" maxlength="2" value="">
			
		    <tr>
		        <th scope="row">Tax</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="Tax" maxlength="2" value="">
			-->
	    
		    <input type="hidden" name="CardPoint" maxlength="2" value="0">
			
		    <input type="hidden" id="ediDate" name="ediDate" maxlength="2" value="<%=ediDate%>">
			
		    <input type="hidden" name="UrlEncode" maxlength="2" value="Y">
			<!-- 
		    <tr>
		        <th scope="row">Productinfo</th>
		        <td>padj</td>
		    </tr>
		     -->		    
		    <input type="hidden" name="Productinfo" maxlength="2" value="padj">
			
			<!--
			<tr>
		        <th scope="row">DivideInfo</th>
		        <td>{'DivideInfo':[{'Amt':'502','MID':'SMTSUB002m','GoodsName':'상품1'},{'Amt':'502','MID':'SMTSUB003m','GoodsName':'상품2'}]}</td>
		    </tr>		    
		    <input type="hidden" name="DivideInfo" maxlength="2" value="{'DivideInfo':[{'Amt':'502','MID':'SMTSUB002m','GoodsName':'상품1'},{'Amt':'502','MID':'SMTSUB003m','GoodsName':'상품2'}]}">
			 
		    <tr>
		        <th scope="row">merchantKey</th>
		        <td></td>
		    </tr>	
		    -->	    
		    <input type="hidden" id="merchantKey" name="merchantKey" maxlength="2" value="merchantKey">
			<input type="hidden" id="actionUrl" name="actionUrl" maxlength="2" value="">	
	    </tbody>
	
	</table>
	</form>
	<%-- iframe --%>
	<iframe src="" name="payFrame" frameborder="no" width="0" height="0" scrolling="yes" align="center"></iframe>
	
	
	<form name='frmCancel' method='post' action=''>
		<input type='hidden' name='p1' maxlength='2'>
		<input type='hidden' name='p2' maxlength='2'>
		<input type='hidden' name='p3' maxlength='2'>
		<input type='hidden' name='p4' maxlength='2'>
		<input type='hidden' name='p5' maxlength='2' value="rent">
		<input type='hidden' name='otherParam' maxlength='2'>		
	</form>
	
	
	<%!
/**
 * 기준날짜에서 몇일 전,후의 날짜를 구한다.
 * @param	sourceTS	기준날짜
 * @param	day			변경할 일수
 * @return	기준날짜에서 입력한 일수를 계산한 날짜
 */
public static Timestamp getTimestampWithSpan(Timestamp sourceTS, long day) throws Exception {
	Timestamp targetTS = null;
	
	if (sourceTS != null) {
		targetTS = new Timestamp(sourceTS.getTime() + (day * 1000 * 60 * 60 * 24));
	}

	return targetTS;
}

/**
 * 현재날짜를 YYYYMMDDHHMMSS로 리턴
 */
public final synchronized String getyyyyMMddHHmmss(){
	/** yyyyMMddHHmmss Date Format */
	SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	
	return yyyyMMddHHmmss.format(new Date());
}

/**
 * <pre>
 * MD5+Base64
 * </pre>
 * @param pw
 * @return String
 */
public static final String encodeMD5Base64(String str){
	return new String(Base64.encodeBase64(DigestUtils.md5(str)));
}
 
public static final String encodeMD5HexBase64(String pw){
	return new String(Base64.encodeBase64(DigestUtils.md5Hex(pw).getBytes()));
}
%>