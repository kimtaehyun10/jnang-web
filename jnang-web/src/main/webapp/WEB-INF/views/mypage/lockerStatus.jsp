<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.dwict.jfmc.client.mem.model.Member"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/lockerStatus.js"></script>

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
	//String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
	//상품주문번호	
	
	String payURL = (String) request.getAttribute("payURL");
	String storeMID = (String) request.getAttribute("storeMID");
	
	//out.println("payURL:"+ payURL +"<BR>");
	//out.println("storeMID:"+ storeMID +"<BR>");
	String Moid = "Moid"; 
	final String DEV_PAY_ACTION_URL = "https://tpay.smilepay.co.kr/interfaceURL.jsp";	//개발테스트
	final String PRD_PAY_ACTION_URL = "https://pay.smilepay.co.kr/interfaceURL.jsp";	//운영
	String actionUrl = payURL; // 개발 서버 URL
	String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String MID = storeMID; //"SMTPAY001m";
	String DivideInfo = ""; //서브몰 결제 정보 //request.getParameter("DivideInfo");
	
	String ReturnURL = URL +"/smartPay/lockerPay"; //Vos.getRtnPayURL(); //"http://localhost:8080/smartPay/returnPay"; //"https://tpay.smilepay.co.kr/returnPay.jsp"; //리턴url
	//String ReturnURL = "https://tpay.smilepay.co.kr/returnPay.jsp";
	String EncodingType = "utf8"; //euckr/utf8
	
	Member member = (Member) session.getAttribute("member");
	String COMCD = "";
	String MEM_NO="";
	String RENT_NO="";
 	// 상점서명키 (꼭 해당 상점키로 바꿔주세요)
 	// String merchantKey = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";
 	// String merchantKey = "KiS8NWHjZ49FzG91HMI9hVXOSxYrvFBKzl2bYpr2ac7lg369iZxy0xhCJfg4juCuVH27mO/TQ4kG2qnjEr5Z4Q==";
 	
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
		form.action = '<%=actionUrl%>';
		
		if (form.GoodsCnt.value == "0") {
			alert("사물함 신청 내역이 없습니다.");
			return false;
		}
			
		console.log(form);
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
	
	function reLocker(COMNM, PLACE_CD, LOCKER_CD, RENT_AMT, COMCD, MEM_NO, RENT_NO){
		var param = {
			COMNM:COMNM,
			PLACE_CD:PLACE_CD,
			LOCKER_CD:LOCKER_CD,
			RENT_AMT:10	
		};
		$('#ReturnURL').val('<%=ReturnURL%>?q='+COMCD+'/'+MEM_NO+'/'+RENT_NO+'/<%=member.getId()%>');
		$('#RetryURL').val('<%=ReturnURL%>?q='+COMCD+'/'+MEM_NO+'/'+RENT_NO+'/<%=member.getId()%>');		
		$.get('/data/mypage/lockerPayDetail', param, function(data){
					
			$("#GoodsCnt").val("1");
			$("#Amt").val(10);		
			$("#GoodsName").val(data.goodsNameEn);
			$("#BuyerName").val(data.buyerNameEn);
			$("#BuyerTel").val(data.byerTel);
			$("#BuyerEmail").val(data.buyerEmail);
			$("#EncryptData").val(data.EncryptData);
			$("#ediDate").val(data.ediDate);
			$("#merchantKey").val(data.merchantKey);
			$("#MID").val(data.storeMID);
			
			goPay();
		}).done(function(data){
			
		});
		/*var param = {
			COMCD:COMCD,
			RENT_NO:RENT_NO
		};
		$.post('/data/mypage/lockerStatus/'+MEM_NO, param, function(data){
			alert(data.result);
			//window.location.href='/price/'+$('#h_cmsCd').val()+'';				
		});*/
}
</script>

<div id="subCon">		
	<input type="hidden" id="h_memNo" name="h_memNo" value="${member.memNo}" />
</div>

<div id="boardCon">
	<div class='sub_lecture_l01' style="display: none;">		
		<div class='ali_c'>
		<img src='${pageContext.request.contextPath}/resource/images/sub/icon_lock_info_01.jpg' alt=''>
		<span class='fc_green2 fontsize_1dot80 margin_l15'>신규(처음)신청은 방문 접수, 수납가능 합니다.</span>
	</div>
	<br>
	<br>
	<br>
	<br>
	<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>사물함 이용 안내문</div>
		<div class='border_box_a_gray_1a margin_top_1dot0 lineheight_1dot8 fontsize_1dot20'>
			<div class='number_a_1a'>
				<dl>
					<dt><span>1</span></dt>
					<dd>
						사물함 내 보관물의 도난, 분실, 파손 등으로 인한 손실에 대한 책임은 전적으로 사용자 본인에게 있습니다.<br>
						(보관물의 <span class='fc_red1'>습기를 제거하고 보관</span>하시어 사물함 내 습기로 인해 <span class='fc_red1'>곰팡이가 생기지 않도록 주의</span> 바랍니다.)<br>
					</dd>
				</dl>
				<dl>
					<dt><span>2</span></dt>
					<dd>
						<span class='fc_red1'>사용기간이 만료</span>(또는 강좌의 환불)되면 반드시 <span class='fc_red1'>열쇠를 반납</span>하셔야 <span class='fc_red1'>보증금을 환불</span>받을 수 있습니다.<br>
					</dd>
				</dl>
				<dl>
					<dt><span>3</span></dt>
					<dd>
						사용기간 <span class='fc_red1'>만료 한 달 이상 열쇠를 반납하지 않을 경우 보증금 10,000원중 3,000원은 연체료, 7,000원은 키교체 비용으로 처리</span>되며 <span class='fc_red1'>보관물은 별도의 장소</span>에 <span class='fc_red1'>보관</span>됩니다.<br>
					</dd>
				</dl>
				<dl>
					<dt><span>4</span></dt>
					<dd>
						<span class='fc_red1'>사물함 열쇠를 분실</span>하였을 경우에는 <span class='fc_red1'>즉시 신고</span>하여야 하며 <span class='fc_red1'>열쇠를 재발급</span> 받으려면 <span class='fc_red1'>키 분실 요금 10,000원</span>을 <span class='fc_red1'>납부</span>하셔야 합니다.<br>
					</dd>
				</dl>
			</div>
		</div>		
	</div>
</div>

<form name="tranMgr" method="post" action="">
	<table class="type">
	    <tbody>
		    <input type="hidden" name="PayMethod" maxlength="2" value="CARD">
		    <input type="hidden" name="PayType" maxlength="2" value="">
			<!-- 수량 -->
		    <input type="hidden" id="GoodsCnt" name="GoodsCnt" maxlength="2" value="<%//=GoodsCnt%>">
			<!--<div>상품명:</div>-->
		    <input type="hidden" id="GoodsName" name="GoodsName" maxlength="2" value="<%//=GoodsName%>xxx">
			
			<!-- <div>상품금액:</div> -->
		    <input type="hidden" id="Amt" name="Amt" maxlength="2" value="<%//=goodsAmt%>">
			
			<!-- <div>주문번호:</div> -->
		    <input type="hidden" name="Moid" maxlength="2" value="Moid">
	    
		    <input type="hidden" id="MID" name="MID" maxlength="2" value="<%=MID%>">
			
		    <input type="hidden" id="ReturnURL" name="ReturnURL" maxlength="2" value="">
			
		    <input type="hidden" name="ReceiptType" maxlength="2" value="0">
			
		    <input type="hidden" id="RetryURL" name="RetryURL" maxlength="2" value="">
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
			
		    <input type="hidden" id="ediDate" name="ediDate" maxlength="2" value="ediDate">
			
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