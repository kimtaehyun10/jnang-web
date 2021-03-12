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
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/cart.js"></script>
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
	
	String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
	
	String Moid = "Moid"; 
	final String DEV_PAY_ACTION_URL = "https://tpay.smilepay.co.kr/interfaceURL.jsp";	//개발테스트
	final String PRD_PAY_ACTION_URL = "https://pay.smilepay.co.kr/interfaceURL.jsp";	//운영
	String actionUrl = DEV_PAY_ACTION_URL;
	String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String MID = ""; //"SMTPAY001m";
	//String MID = "jungnan01m"; //위에 URl 변경 DEV_PAY_ACTION_URL ==> PRD_PAY_ACTION_URL
	String DivideInfo = ""; //서브몰 결제 정보 //request.getParameter("DivideInfo");
	
	String ReturnURL = URL +"/smartPay/returnPay"; //Vos.getRtnPayURL(); //"http://localhost:8080/smartPay/returnPay"; //"https://tpay.smilepay.co.kr/returnPay.jsp"; //리턴url
	String EncodingType = "utf8"; //euckr/utf8
	final String strUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
 	// 상점서명키 (꼭 해당 상점키로 바꿔주세요)
 	//String merchantKey = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";
 	//String merchantKey = "KiS8NWHjZ49FzG91HMI9hVXOSxYrvFBKzl2bYpr2ac7lg369iZxy0xhCJfg4juCuVH27mO/TQ4kG2qnjEr5Z4Q==";
 	
 	//VoPay test1 = new VoPay();
 	//VoPay Vos = (VoPay)request.getAttribute("VoPay");

 	// 상점 ID MID

	Member member 	= (Member) session.getAttribute("member");
	String MEM_ID	= member.getId();
	String MEM_NM 	= member.getMemNm();
	String MEM_MAIL = member.getEmail();
	String MEM_HP 	= member.getHp();
	//out.println("MEM_ID:"+ MEM_ID +"<BR>");
	//out.println("MEM_NM:"+ MEM_NM +"<BR>");
	//out.println("MEM_MAIL:"+ MEM_MAIL +"<BR>");
	

	//String EncryptData = encodeMD5HexBase64(ediDate + MID + goodsAmt + merchantKey);

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

	//결제 버튼
	function goBtn(seq, goodsAmt, GoodsName, COMCD) {
		
		<%
		if (strUrl.contains("localhost") || strUrl.contains("14.36.179.143") || MEM_ID.equals("powerjyc")) {
		} else  {
		%>
		alert("현재 결제 서비스는 점검중입니다.");
		return false;
		<%
		}
		%>
		
		//alert(seq +", "+ goodsAmt +", "+ GoodsName +", "+ COMCD);
		var BuyerName = encodeURI("<%=MEM_NM%>");
		
		//debugger;
		$.get("/data/getOdEncryptData/"+ <%=ediDate%> +"/"+ goodsAmt +"/"+ COMCD +"?GoodsName="+ encodeURI(GoodsName) +"&BuyerName="+ BuyerName, function(data){
		//try {
			console.log(data);
			var dataList = "";
			if(data.length != 0){				
				$("#EncryptData").val(data.EncryptData);
				$("#merchantKey").val(data.KEY);
				$("#MID").val(data.MID);
				$("#Amt").val(goodsAmt);
				$("#GoodsName").val(data.enGoodsName);
				$("#BuyerName").val(data.enBuyerName);
				actionUrl = data.URL;
				$("#ReturnURL").val("<%=ReturnURL%>?q=${otherData.MEM_ID}/"+ seq +"///");
			goPay();	
			} 
		/*} catch (exception) {
			alert("할인적용 오류 : 잠시후 다시 시도하여 주세요..");
			window.location.reload();
			return;
		}*/
		});
			
		
	}
	

	var actionUrl = "";
	function goPay() 
	{		
		console.log("actionUrl:"+ actionUrl);
		var form = document.tranMgr;
		form.action = actionUrl;
		
		if (form.GoodsCnt.value == "0") {
			alert("수강 신청 내역이 없습니다.");
			return false;
		}
		
		console.log("actionUrl:"+ form.GoodsName.value);
		console.log("actionUrl:"+ form.Amt.value);
		console.log("actionUrl:"+ form.GoodsCnt.value);
		console.log("actionUrl:"+ form.ReturnURL.value);
		
		if (form.GoodsName.value == "" || form.Amt.value == "" || form.GoodsCnt.value == "" ||form.ReturnURL.value == "")
		{
			alert("필수 입력값 오류!!");
			return false;
		}
		
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
	
	//즉시감면 데이터 세팅
	function saleGo(){
		
		//데이터 세팅
    	var param = "imreName=이수현&" //넘기지 않는 경우 이름 input 확성화
	          + "imreBirth=870802&" //넘기지 않는 경우 이름 주민등록번호 앞자리 확성화
	          + "imreDisCode=";   //넘기지 않는경우 할인항목 전체 노출
    	window.open( "/imre/imReView",param, 600, 400 );
    //	openPopLayer( "/imre/imReView", encodeURI(param), "", 800, 500, null ,null );
	}
	


	
	
</script>

<div id="sub_cart" class=' '>
	<form name="frm" method="post">
	<table class="stbl_l1a">
		<colgroup>
			<col width="5%">
			<col width="5%">
			<col width="15%">
			<col width="10%">
			<col width="*">
			<col width="15%">
			<col width="10%">
			<col width="10%">
		</colgroup>
		<thead>
			<tr>
				<th>No.</th>
				<th><input type="checkbox" name="" id="" onclick="ac_cbox(this.form, 'cbox', this.checked);" style='width:30px; height:30px;'></th>				
				<th>센터명</th>
				<th>종목</th>
				<th>강좌명</th>
				<th>대상</th>
				<th>수강료</th>
				<th>결제</th>
			</tr>
		</thead>
		<tbody id="dataList">
		</tbody>

	</table>
	</form>

<div class="btnarea margin_t80">
	  <!-- <a href="#none" onclick="saleGo();" id=" " class="blue">할인적용</a> -->
	</div>
    <div class="btnarea margin_t80">
        <a href="#none" onClick="data.list('del');" id=" " class="gray2">선택삭제</a>
    </div>
</div>





<form name="tranMgr" method="post" action="">
	<table class="type">
	    <tbody>
		    <input type="hidden" name="PayMethod" maxlength="2" value="CARD">
		    <input type="hidden" name="PayType" maxlength="2" value="">
			<!-- 수량 -->
		    <input type="hidden" id="GoodsCnt" name="GoodsCnt" maxlength="2" value="1">
			<!--<div>상품명:</div>-->
		    <input type="hidden" id="GoodsName" name="GoodsName" maxlength="2" value="">
			
			<!-- <div>상품금액:</div> -->
		    <input type="hidden" id="Amt" name="Amt" maxlength="2" value="<%//=goodsAmt%>">
			
			<!-- <div>주문번호:</div> -->
		    <input type="hidden" name="Moid" maxlength="2" value="Moid">
	    
		    <input type="hidden" id="MID" name="MID" maxlength="2" value="<%=MID%>">
			
		    <input type="hidden" id="ReturnURL" name="ReturnURL" maxlength="2" value="">
			
		    <input type="hidden" name="ReceiptType" maxlength="2" value="0">
			
		    <input type="hidden" name="RetryURL" maxlength="2" value="<%=ReturnURL%>?q=${otherData.MEM_ID}/">
			<!-- 
		    <tr>
		        <th scope="row">mallUserID</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="mallUserID" maxlength="2" value=""> -->
			<!-- <div>구매자:</div> -->
		    <input type="hidden" id="BuyerName" name="BuyerName" maxlength="2" value="BuyerName">
			<!-- <div>연락처:</div> -->
		    <input type="hidden" id="BuyerTel" name="BuyerTel" maxlength="2" value="<%=MEM_HP%>">
			<!-- <div>이메일:</div> -->
		    <input type="hidden" id="BuyerEmail" name="BuyerEmail" maxlength="2" value="<%=MEM_MAIL%>">
			
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
	
	    </tbody>
	
	</table>
	</form>
	
	<%-- iframe --%>
	<iframe src="blank.html" name="payFrame" frameborder="no" width="0" height="0" scrolling="yes" align="center"></iframe>
	
	<!-- /smartPay/mainCancelPay -->
	
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