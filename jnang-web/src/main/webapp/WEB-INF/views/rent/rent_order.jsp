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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/common.js"></script>
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
******************************************************************************
*/
	
//InetAddress inet = InetAddress.getLocalHost(); // 서버 IP 가져오기
Timestamp toDay = new Timestamp((new Date()).getTime()); // 현재날짜
Timestamp nxDay = getTimestampWithSpan(toDay, 1); // 가상계좌 입금만료일  1일후 가져오기
String VbankExpDate = nxDay.toString();
VbankExpDate = VbankExpDate.substring(0, 10); 
VbankExpDate = VbankExpDate.replaceAll("-", "");
String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
String Moid = "Moid"; 
final String strUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
// 상점서명키 (꼭 해당 상점키로 바꿔주세요)
//String merchantKey = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";
//String merchantKey = "KiS8NWHjZ49FzG91HMI9hVXOSxYrvFBKzl2bYpr2ac7lg369iZxy0xhCJfg4juCuVH27mO/TQ4kG2qnjEr5Z4Q==";
//String MID = "SMTPAY001m";		// 상점 ID MID

//String returnUrl = "https://tpay.smilepay.co.kr/returnPay.jsp"; // 결제결과를 수신할 가맹점 returnURL 설정
//String retryUrl = "https://tpay.smilepay.co.kr/inform.jsp"; // 가맹점 retryURL 설정

String DivideInfo = "";
String storeMID 	= (String) request.getAttribute("MID");
//String payURL 		= "https://tpay.smilepay.co.kr/interfaceURL.jsp";

String merchantKey  = (String) request.getAttribute("KEY");
String MID 			= storeMID; //(String) request.getAttribute("MID");
String payURL 		= (String) request.getAttribute("URL");
String PWD 			= (String) request.getAttribute("PWD");
//out.print("merchantKey =" + merchantKey + "<BR>");
//out.print("MID =" + MID);

//if (strUrl.contains("localhost")) {
//	out.println("LOCAL ==> merchantKey:"+ merchantKey +"<BR>");
//	out.println("LOCAL ==> storeMID:"+ MID +"<BR>");
//	out.println("LOCAL ==> payURL:"+ payURL +"<BR>");
//	out.println("LOCAL ==> PWD:"+ PWD +"<BR>");
//}

//final String DEV_PAY_ACTION_URL = "https://tpay.smilepay.co.kr/interfaceURL.jsp";	//개발테스트
//final String PRD_PAY_ACTION_URL = "https://pay.smilepay.co.kr/interfaceURL.jsp";	//운영
String actionUrl = payURL; // 개발 서버 URL
String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String ReturnURL = URL +"/smartPay/rentPay"; //Vos.getfPayURL(); //"http://localhost:8080/smartPay/returnPay"; //"https://tpay.smilepay.co.kr/returnPay.jsp"; //리턴url
String RetryURL = URL +"/smartPay/rentPay";
String EncodingType = "utf8"; //euckr/utf8



Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
String today = transFormat.format(from);


//String MEM_NM 		= (String) pageContext.getAttribute("MEM_NM");
Member member 	= (Member) session.getAttribute("member");
String MEM_ID	= member.getId();
String MEM_NM 	= member.getMemNm();
String MEM_MAIL = member.getEmail();

MEM_MAIL = (MEM_MAIL == null) ? "" : " | &nbsp; "+ MEM_MAIL;
%>



<%
int PRICE = 0;
String commaTot = "";
String commaRent = "";
String saleTot = ""; 
String saleRent = "";
String commaLight = "";
String commaNum1 = "";
%>
<c:set var="PLACE_GROUP" value="${rentCfg.PLACE_GROUP}" />
<c:set var="RENT_AMT" value="${rentCfg.RENT_AMT}" />
<c:set var="COMNM" value="${rentCfg.COMNM}" />
<c:set var="otherCfg" value="${rentCfg.other_cfg}" />
<c:set var="TM_TYPE" value="${myTeamList.get(0).TM_TYPE }" />
<c:set var="yoil" value="${dataList.yoil }" />

<%
int PLACE_GROUP = (int) pageContext.getAttribute("PLACE_GROUP");
//out.println("PLACE_GROUP:"+ PLACE_GROUP +"<BR>");

String TM_TYPE = (String)pageContext.getAttribute("TM_TYPE");
//out.print(TM_TYPE);

String yoil = (String)pageContext.getAttribute("yoil");
//out.print(yoil);

//테니스장 기본가격(주간,평일)
long RENT_AMT = (long) pageContext.getAttribute("RENT_AMT");
//out.println("RENT_AMT:"+ RENT_AMT +"<BR>");

//대관장소명
String COMNM = (String) pageContext.getAttribute("COMNM");
//out.println("COMNM:"+ COMNM +"<BR>");

//기타 환경설정값
///코트/8/토/0/휴/6500/ / / /
String otherCfg = (String)pageContext.getAttribute("otherCfg") ;
String [] arryCfg = otherCfg.split("\\/");

//공휴일가격
String holiday_price = arryCfg[6];

String GoodsName = COMNM;
//out.print("GoodsName = " + GoodsName);

//축구장/야구장 (기본(평일주말)) 단가표
int [][] rentPrice = new int [20][20];

%>
<c:forEach items="${rentPriceList}" var="result" varStatus="status">

	<c:set var="days" value="${result.days}" />
	<c:set var="playtime" value="${result.playtime}" />
	<c:set var="price" value="${result.price}" />
	<c:set var="inArea" value="${result.in_area }" />
	<c:set var="person" value="${result.person }" />
	<%
	//1 평일, 0 주말
	//boolean days = (boolean)pageContext.getAttribute("days") ;
	//int int_days = (days) ? 1 : 0;
	//1 비장애, 0 장애
	//boolean person = (boolean)pageContext.getAttribute("person");
	//int int_person = (person) ? 1 : 0;
	//1 관내, 0 관외
	//boolean inArea = (boolean)pageContext.getAttribute("inArea");
	//int int_inArea = (inArea) ? 1 : 0;
	
	//out.print("days 1평일 or 0주말" + days + "<BR>");
	//out.print("person 1비장애 or 0장애=" + person + "<BR>");
	//out.print("inArea 1관내 or 0관외=" + inArea + "<BR><BR>");
	
	//사용시간
	//int playtime = (int)pageContext.getAttribute("playtime") ;
	//이용금액
	//int price = (int)pageContext.getAttribute("price") ;
	
	//rentPrice[int_days][playtime] = price;
	
	//out.println("["+ int_days +"]"+"["+ playtime +"] = "+ price +"<BR>" );
	%>

</c:forEach>

<script type="text/javascript">
$(function(){
	
	setMenuTitle('bg_99', '체육시설', '<%=COMNM%> > 예약/결제', true);
	$('.sub_navi1 .navi1 .mbx1 > a').on('click',function(){
		if($('.sub_navi1 .bg1').css('display')==='none'){
			var idx_n=$(this).parent().parent().index();
			$('.sub_navi1 .navi1 .dp1').each(function(){
				if(idx_n!=$(this).index()){
					$(this).find('ul').slideUp(100);
				}
			});
			$(this).next().slideToggle(150);
		}else{
			$(this).next().slideToggle(150);
		}
	});
	
	//$(".sdate").datepicker();
});

/* function getPerson(){
	var personCheck = $('input:radio[name="person"]:checked').val();
	
	$.ajax({
		url: 'rent/rentOrder',
		dataType:'json',
		type:'post',
		data:{
			'personCheck'
		}
	
	})
	
} */

function sale(){
	
	var commaTot = $("#commaTot").val();
	var commaRent =	$("#commaRent").val();
	var commaLight = $("#commaLight").val();
	var saleRent = $("#saleRent").val();
	var saleTot = $("#saleTot").val();
	var COMCD =	$("#refComCd").val();
	
	
	var html1='';
	var html2='';
	<%-- <span id="AmtDP">  <%=commaTot %></span>원 &nbsp; (대관료 : <%=commaRent %>	 / 조명료 : <%=commaLight %>)</td> --%>

	if($('input[name=person]:checked').val() == '0'){
		html1 += "<span id='AmtDP'>"
		html1 += commaTot + "</span>원"
		html1 += "&nbsp;"
		html1 += "(대관료 : " + commaRent + "원/"
		html1 += " 조명료 : " + commaLight + "원) </td>"
		$('#saleResult').html(html1);
		
	} else {
		html1 += "<span id='AmtDP'>"
		html1 += saleTot + "</span>원"
		html1 += "&nbsp;" 
		html1 += "(대관료 : " + saleRent + "원/"
		html1 += " 조명료 : " + commaLight + "원)</td>"
		$('#saleResult').html(html1);
	}
	
	if($('input[name=person]:checked').val() == '0'){
		html2 += "<a href='#none' onClick=send('"
		html2 += COMCD
		html2 += "');"
		html2 += " id='' class='green'>결제</a>"
		html2 += "<a href='#none' onClick='history.back(-1);' id='' class='gray2'>취소</a>"
		console.log(html2);
		$('.margin_t80').html(html2);
	} else {
		html2 += "<a href='#none' onClick='rentSave()' id='' class='green'>예약저장</a>"
		html2 += "<a href='#none' onClick='history.back(-1);' id='' class='gray2'>취소</a>"
			console.log(html2);
		$('.margin_t80').html(html2);
	}
}

function selectDC(){
	
	var commaTot = $("#commaTot").val();
	var commaRent =	$("#commaRent").val();
	var commaLight = $("#commaLight").val();
	var rentSum = $("#rentSum").val();
	var lightSum = $("#lightSum").val();
	var saleRent = rentSum / 2; // 할인된 가격 (인트형)
	var commaSaleRent = AmountCommas(saleRent); // 할인된 가격에 콤마를 찍어준 값 (문자형)
	var saleTotalSum = Number(lightSum) + Number(saleRent);
	var commaTotalSum = AmountCommas(saleTotalSum);
	
	
	var html1='';
	if($('input[name=person]:checked').val() == '0'){
		html1 += "<span id='AmtDP'>"
		html1 += commaTot + "</span>원"
		html1 += "&nbsp;"
		html1 += "(대관료 : " + commaRent + "원/"
		html1 += " 조명료 : " + commaLight + "원) </td>"
		$('#saleResult').html(html1);
		
	} else {
		html1 += "<span id='AmtDP'>"
		html1 += commaTotalSum + "</span>원"
		html1 += "&nbsp;" 
		html1 += "(대관료 : " + commaSaleRent + "원/"
		html1 += " 조명료 : " + commaLight + "원)</td>"
		$('#saleResult').html(html1);
	}
}

function AmountCommas(val){
	return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g,",");
}



var data = {
	
	selectDC: function(dcPer, COMCD){
		
	


	var dePer = "";	
	var because = "";
	
	var test = document.getElementById("disCount").value.split(",");
	
	for(var idx=0; idx < test.length; idx ++){
		var objElement = test[idx];
		if(idx == 0) {
			var dcPer = objElement;
		}else if(idx == 1) {
			var because = objElement;
		}
	}

	//히든값에 있는 아이디를 찾아 넣어줌
	$('#because').val(because);
	
	//뒤에 할인값이 계속 연달아 붙어서 function호출할 때 마다 초기화 된 ReturnURL값을 계속 넣어준다.
	var ReturnUrl1 = $('#ReturnURL1').val();
	$('#ReturnURL').val(ReturnUrl1); 
	
	$('#ReturnURL').val($('#ReturnURL').val()+'/'+because);
	if(because > 0 ) {
		alert("이용자의 50%가 할인대상자여야 합니다.\n(현장에서 확인예정)")
	}
	 
	var saleRentPrice = "";
	var realSaleRentPrice = "";
	var fileValue = $("input[name='saleRentPrice']").length;
    var fileData = new Array(fileValue);
    for(var i=0; i<fileValue; i++){                          
         fileData[i] = $("input[name='saleRentPrice']")[i].value;
         //saleRentPrice = fileData[i].replaceAll(",", "");
         //saleRentPrice.replace(/\,/g,"");
         saleRentPrice = fileData[i].replace(",","");
         
        
         saleRentPrice = saleRentPrice - (saleRentPrice * (dcPer/100));
         
         if(i == 0) {
        	 realSaleRentPrice += saleRentPrice + "," ; 
         } else if (i == (fileValue -1)) {
        	 realSaleRentPrice += saleRentPrice;
         } else {
        	 realSaleRentPrice += saleRentPrice + "," ; 
         }
    }
    
	var rentSum = Number($("#rentSum").val());
	var dataListLength = Number($("#dataListLength").val());
	var saleRent = rentSum - (rentSum * (dcPer/100));
	var lightSum = Number($("#lightSum").val());
	var goodsAmt = Number(lightSum) + Number(saleRent);
	var commaTot = AmountCommas(goodsAmt);
	var commaRent =	AmountCommas(saleRent);
	var commaLight = AmountCommas(lightSum);
	var goodsName = String($("#GoodsName").val());
	
	/* if(returnUrlSplit <= 9) {
		returnUrlValue = $('#ReturnURL').val();	
	} else {
		
	}
	console.log($('#ReturnURL').val().substring($('#ReturnURL').val().lastIndexOf('/'),$('#ReturnURL').val().length));
	//$('#ReturnURL').val().substring($('#ReturnURL').val().lastIndexOf('/'),$('#ReturnURL').val().length);
	 */
	$('#ReturnURL').val($('#ReturnURL').val()+'/'+realSaleRentPrice);
	
	var html1='';
	html1 += "<span id='AmtDP'>"
	html1 += commaTot + "</span>원"
	html1 += "&nbsp;"
	html1 += "(대관료 : " + commaRent + "원/"
	html1 += " 조명료 : " + commaLight + "원) </td>"
	$('#saleResult').html(html1);
	
	$("#AmtDP").text(goodsAmt );
	$("#Amt").val(goodsAmt);
	$("#goodsAmt").val(goodsAmt);
	debugger;
	
	$.get("/data/getOdEncryptData/<%=ediDate%>/"+ goodsAmt +"/"+ COMCD, function(data){
		try {
			var dataList = "";
			if(data.length != 0){
				$("#EncryptData").val(data.EncryptData);
				$("#merchantKey").val(data.KEY);
				$("#MID").val(data.MID);
				$("#Amt").val(goodsAmt);
				$("#GoodsName").val(goodsName);
				
				
			} 
		} catch (exception) {
			alert("할인적용 오류 : 잠시후 다시 시도하여 주세요..");
			window.location.reload();
			return;
		}
	});
	
	}
};

function rentSave(){
<%
	if(PLACE_GROUP == 2 || PLACE_GROUP == 3){
%>	
	/* alert("할인예약은 현장에서 확인 후 할인된 금액으로 결제합니다. \n예약 후 현장결제 해주세요."); */
	alert("현재 이용하실 수 없습니다.");
	return false;
<%
	} else if(PLACE_GROUP == 4) {
%>	
	alert("테니스장은 현장에서 결제를 진행합니다.");
<%
	}
%>
	var frm1 = document.frm1;
	if(frm1.agree1.checked == false) {
		alert('주의 사항 및 환불 안내를 확인하고 선택하여 주세요.');
		frm1.agree1.focus();
		return false;
	}
	var priceTimeSeq1 = $("#val1").val();
	var priceTimeSeq2 = $("#val2").val();
	var reserveDate = $("#val3").val();
	
	console.log(priceTimeSeq1, priceTimeSeq2, reserveDate);
	
	$.post('/rent/save2', $("#frm1").serialize() , function(data){
		window.location.href = "/mypage/rent";
	}).done(function(data){
		
	},"json");
	return false;

}

function send(COMCD){
	
	
	<%
	if(PLACE_GROUP == 4 ) {
	%>
		var because = String($("#because").val());
		
		if(because == null || because == ""){
			alert("할인여부를 선택해주세요.");
			return false;
		}
	<%
	}
	%>
	
	var goodsAmt = Number($("#goodsAmt").val());
	var goodsName = String($("#GoodsName").val());
	
	$("#AmtDP").text(goodsAmt );
	$("#Amt").val(goodsAmt);
	$("#GoodsName").val(goodsName);
	
	$.get("/data/getOdEncryptData/<%=ediDate%>/"+ goodsAmt +"/"+ COMCD, function(data){
		try {
			var dataList = "";
			if(data.length != 0){
				
				$("#EncryptData").val(data.EncryptData);
				$("#merchantKey").val(data.KEY);
				$("#MID").val(data.MID);
				$("#Amt").val(goodsAmt);
				$("#GoodsName").val(goodsName);
				
				
				
				var html1=$("#returnURL").val();
				
				
			goPay();	
			} 
		} catch (exception) {
			alert("잠시후 다시 시도하여 주세요..");
			window.location.reload();
			return;
		}
	});
	

}
</script>

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
		
		<%-- var MEM_ID = '<%=MEM_ID%>';
		if(MEM_ID != "vos1") {
			alert("현재 이용하실 수 없습니다. 관리자에게 문의해주세요.");
			return false;
		} --%>
		
		var frm1 = document.frm1;
		if(frm1.agree1.checked == false) {
			alert('주의 사항 및 환불 안내를 확인하고 선택하여 주세요.');
			frm1.agree1.focus();
			return false;
		}
		
		
		var form = document.tranMgr;
		form.action = '<%=actionUrl%>';
		
		/* if (form.GoodsCnt.value == "0") {
			alert("수강 신청 내역이 없습니다.");
			return false;
		} */
		
		if (form.GoodsName.value == "" || form.Amt.value == "" || form.GoodsCnt.value == "" ||form.ReturnURL.value == "")
		{
			alert("필수 입력값 오류!!");
			return false;
		}

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
	
	
</script>
<div class="sub_cont1">
<div class="con_bx">
	

<div class="sub_cleanreport">

	
<form name="frm1" id="frm1" method="post"> 
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
	<div class="border_top_3_green"></div>
	
<table class="stbl_w3b border_top_0" summary="이 표는 제목/내용 등의 정보로 구성된 팀등록/수정 폼입니다.">
	<caption>
		신청정보
	</caption>
	<colgroup>
		<col width="200px">
		<col width="*">
	</colgroup>
	<tbody>
	<tr>
    	<td colspan="2"><div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">신청정보</div></td>
    </tr>
	<tr>
		<th>신청일시</th>
		<td>
			<c:out value='${dataList.dataList[0].WRITE_DH}'/> [<c:out value='${dataList.yoil}'/>] 요일
		 </td>
	  </tr>
	  <tr>
		<th>신청자명</th>
		<td><%=MEM_NM%></td>
	  </tr>
	  <tr>	
	  <th>신청자정보</th>
	  <td><c:out value='${memData.HP}'/> &nbsp; <%=MEM_MAIL%></td>
	  </tr>
	  <tr>
		<th> 시설명 </th>
		<td><c:out value='${rentCfg.COMNM}'/></td>
	  </tr>	  
	  <tr>
	    <th>대관시간</th>
	    <td>
	   
	    	<%
	    	String holiday_yn ="N";
	    	String priceMsg ="";
	    	//축구장,야구장 사용 시간계산
	    	int rent_timeCnt = 0;
	    	//테니스 주간 가격
	    	int rent_t_timePrice = 0; //(int) RENT_AMT;
	    	//테니스 야간, 공휴일 가격
	    	//int rent_t_night_holiday_price = Integer.parseInt(holiday_price);
	    	//대여 총금액
	    	int rentSum = 0;
	    	%>
			<c:forEach items="${dataList.dataList}" var="result" varStatus="status">
				<!-- 공휴일 확인 -->
				<c:set var="holiday_yn" value="${result.holiday_yn}" />
				<c:set var="item" value="${result.item}" />
				<%
				priceMsg = "";
				holiday_yn = (String)pageContext.getAttribute("holiday_yn") ;
				//out.println("holiday_yn:"+ holiday_yn +"  <BR>");
				
				//주간 야간(18시이후) 구분용
				String item = (String)pageContext.getAttribute("item");
				int int_item_time = Integer.parseInt(item.substring(0,2));
				//out.println("item:"+ int_item_time +"<BR>");
				%>
				
				
				<!--  예약성공 계산 -->
				<c:if test="${result.orderYN eq 'Y'}"> 
				<c:choose>
					<c:when test="${dataList.yoil eq '토' || dataList.yoil eq '일'}">
						<%holiday_yn = "Y"; %>
					</c:when>
					<c:otherwise>
						<%holiday_yn = "N"; %>
					</c:otherwise>
				</c:choose>
					[예약 성공]
					<% 
					rent_timeCnt ++;
					//요금 계산
						//축구장/야구장 #################################################################
						if (PLACE_GROUP == 2 || PLACE_GROUP == 3) {
							
							//아래에 총 사용 시간으로 계산
							
						//테니스장  #################################################################
						} else if (PLACE_GROUP == 4) {
							
							//out.println(result.item}
							
							//테니스 공휴일 , 야간 경우 요금변경
							if (holiday_yn.equals("Y") || int_item_time >= 18) {
								rent_t_timePrice = Integer.parseInt(holiday_price);
								priceMsg = (holiday_yn.equals("Y")) ? "공휴일요금":" 야간요금 ";
							} else {
								rent_t_timePrice = (int) RENT_AMT;
								
							}
							rentSum = rentSum + rent_t_timePrice;
							
						}
					%>
				</c:if>
				<c:if test="${result.orderYN eq 'N'}"> 
					<span class='btn_red1'>[예약 실패]</span>
				</c:if>
			
				<%
				if (PLACE_GROUP == 2 || PLACE_GROUP == 3) {
				%> 
						
					&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=rent_timeCnt %> 시간<BR>
					 					
				<%
				} else if (PLACE_GROUP == 4) {
				%> 
					&nbsp; ${result.place_tab} 코트
				
					<%
					priceMsg = (priceMsg.equals("")) ? "" : " ["+ priceMsg +"]";
					
					commaNum1 = NumberFormat.getInstance().format(rent_t_timePrice);
					%>
	
					&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum1 %> <%=priceMsg %><BR>
					<input type="hidden" name="saleRentPrice" value="<%=commaNum1 %>" />
				<%
				}
				%>

				
			</c:forEach>
	    </td>
    </tr>
	  <tr>
	    <th>조명료</th>
	    <td>
<%
	    	//1시간 조명료
	    	int lightPrice =0;
	    	int lightSum = 0;
	    	int totalSum = 0;
	    	
	    	if (PLACE_GROUP == 2 || PLACE_GROUP == 3) {
	    		//축구장/야구장
	    		lightPrice = 11000;
	    	} else if (PLACE_GROUP == 4) {
	    		//테니스장
	    		lightPrice = 3000;
	    		//rent_daytimePrice = 
	    	}
	    	
	    	%>
			<c:forEach items="${dataList.dataList}" var="result" varStatus="status">
				<c:set var="item" value="${result.item}" />
				<c:set var="SORT" value="${result.SORT }" />
				<c:set var="RESERVE_DATE" value="${fn:substring(result.RESERVE_DATE,5,7)}" />
				<%-- ${fn:substring(result.RESERVE_DATE,5,7)} --%>
				<%
				//주간 야간(18시이후) 구분용
				String item = (String)pageContext.getAttribute("item");
				int int_item_time = Integer.parseInt(item.substring(0,2));
				String RESERVE_DATE = (String)pageContext.getAttribute("RESERVE_DATE");
				int SORT = (int)pageContext.getAttribute("SORT") ;
				//out.println("SORT:"+ SORT +"<BR>");
				%>
				
				
				<!--  예약성공 계산 -->
				<c:if test="${result.orderYN eq 'Y'}"> 
					
					<!-- 조명사용 -->
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '01'}">
							<c:if test="${result.sort eq '1' or result.sort eq '2' or result.sort eq '12' or result.sort eq '13' or result.sort eq '14' or result.sort eq '15' or result.sort eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '02'}">
							<c:if test="${result.sort eq '1' or result.sort eq '2' or result.sort eq '12' or result.sort eq '13' or result.sort eq '14' or result.sort eq '15' or result.sort eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '03'}">
							<c:if test="${result.sort eq '1' or result.sort eq '13' or result.sort eq '14' or result.sort eq '15' or result.sort eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '04'}">
							<c:if test="${result.SORT eq '13' or result.SORT eq '14' or result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '05'}">
							<c:if test="${result.SORT eq '14' or result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '06'}">
							<c:if test="${result.SORT eq '14' or result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '07'}">
							<c:if test="${result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '08'}">
							<c:if test="${result.SORT eq '14' or result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '09'}">
							<c:if test="${result.SORT eq '14' or result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '10'}">
							<c:if test="${result.SORT eq '1' or result.SORT eq '13' or result.SORT eq '14' or result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '11'}">
							<c:if test="${result.SORT eq '1' or result.SORT eq '13' or result.SORT eq '14' or result.SORT eq '15' or result.SORT eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						<c:if test="${fn:substring(result.RESERVE_DATE,5,7) eq '12'}">
							<c:if test="${result.sort eq '1' or result.sort eq '2' or result.sort eq '12' or result.sort eq '13' or result.sort eq '14' or result.sort eq '15' or result.sort eq '16'}">
								[예약 성공]
								<!-- 조명사용 , ${result.add_light} -->
								<%
								lightSum = lightSum + lightPrice;
								%>
								<%
								if (PLACE_GROUP == 4) {
								%> 
									&nbsp; ${result.place_tab} 코트
								<%
								}
								String commaNum = NumberFormat.getInstance().format(lightPrice);
								%>
				
								&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>
							</c:if>
						</c:if>
						
						<%-- [예약 성공]
						<!-- 조명사용 , ${result.add_light} -->
						<%
						lightSum = lightSum + lightPrice;
						%>
						<%
						if (PLACE_GROUP == 4) {
						%> 
							&nbsp; ${result.place_tab} 코트
						<%
						}
						String commaNum = NumberFormat.getInstance().format(lightPrice);
						%>
		
						&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} &nbsp; <%=commaNum %><BR>  --%>
										
				</c:if>

				
			</c:forEach>
				    
		</td>
    </tr>
    <c:if test="${rentCfg.PLACE_GROUP eq '4'}">
    	<!-- 우선 4번 그룹만 할인적용 --> 
	  <tr>
	    <th>할인여부</th>
	    <td>
	    	<select name="" id="disCount" class="inputbox_01a" onchange="data.selectDC(this.value, '${rentCfg.COMCD }');">
		    	<option value="" selected>==할인 선택==</option>
		    	<option value="0,0">일반</option>
		    	<option value="10,1">중랑구민(10%)</option>
		    	<!-- <option value="10">관내팀(10%)</option> -->
		    	<option value="30,2">경로우대(30%)</option>
		    	<!-- <option value="50">초/중/고생(50%)</option> -->
		    	<option value="50,3">장애인(50%)</option>
		    	<option value="50,4">기초수급자(50%)</option>
		    	<option value="50,5">국가유공자(50%)</option>
	    	</select>
	    	<!-- <input type="radio" name="person" value="0" onClick="selectDC()" checked> 일반
            <input type="radio" name="person" value="1" onClick="selectDC()" > 할인 50% (장애, 국가유공자, 초중고생) -->
		</td>
    	</tr>
    </c:if>
    
    <c:if test="${rentCfg.PLACE_GROUP eq '3' || rentCfg.PLACE_GROUP eq '2' }">
	  <tr>
	    <th>할인여부</th>
	    <td>
	    	<%-- <select name="" class="inputbox_01a" onchange="data.selectDC(this.value, '${rentCfg.COMCD }');">
		    	<option value="">==할인 선택==</option>
		    	<option value="0" selected>일반</option>
		    	<!-- <option value="10">중랑구민(10%)</option>
		    	<option value="10">관내팀(10%)</option>
		    	<option value="30">경로우대(30%)</option> -->
		    	<option value="50">초/중/고생(50%)</option>
		    	<option value="50">장애인(50%)</option>
		    	<option value="50">기초수급자(50%)</option>
		    	<option value="50">국가유공자(50%)</option>
	    	</select> --%>
	    	
    
			<input type="radio" name="person" value="0" onClick="sale()" checked> 일반
           	<input type="radio" name="person" value="1" onClick="sale()" > 할인 50% (장애, 국가유공자, 초중고생)
		</td>
    	</tr>
    </c:if>
	  <tr>
	    <th>합계</th> 
	   		<c:set var="PRICE" value="${rentPriceList[0].PRICE}" />
	   		<c:set var="PRICE1" value="${rentPriceList[1].PRICE}" />
	    <td>
	    <%
	    PRICE = (int) pageContext.getAttribute("PRICE");
	    int PRICE1 = (int) pageContext.getAttribute("PRICE1");
	  	//축구장/야구장 #################################################################
	  	
		if (PLACE_GROUP == 2 || PLACE_GROUP == 3) {
			
				if (holiday_yn.equals("Y")) {
					rentSum = rentPrice[0][rent_timeCnt];
				} else {
					rentSum = rentPrice[1][rent_timeCnt];
				}  
	    
		    totalSum = PRICE + lightSum;
		    int totalSaleSum = PRICE1 + lightSum;
		    commaTot = NumberFormat.getInstance().format(totalSum);
		    commaRent = NumberFormat.getInstance().format(PRICE);
		    commaLight = NumberFormat.getInstance().format(lightSum);
		    saleTot = NumberFormat.getInstance().format(totalSaleSum);
		    saleRent = NumberFormat.getInstance().format(PRICE1);
		} else if (PLACE_GROUP == 4) {
			totalSum = rentSum + lightSum;
			commaTot = NumberFormat.getInstance().format(totalSum);
			commaRent = NumberFormat.getInstance().format(rentSum);
			commaLight = NumberFormat.getInstance().format(lightSum);
		}
	    %>
	    <div id = "saleResult">
	    	<span id="AmtDP">  <%=commaTot %></span>원 (대관료 : <%=commaRent %>원/ 조명료 : <%=commaLight %>원)</td>
	    </div>
	    <input type="hidden" id="commaTot" value="<%=commaTot %>" />
	    <input type="hidden" id="saleTot" value="<%=saleTot %>" />
	    <input type="hidden" id="commaRent" value="<%=commaRent %>" />
	    <input type="hidden" id="commaLight" value="<%=commaLight %>" />
	    <input type="hidden" id="saleRent" value="<%=saleRent %>" />
	    <input type="hidden" id="dataListLength" value="${fn:length(dataList.dataList) }" />
	    <input type="hidden" id="refComCd" value="${rentCfg.COMCD}" />
	    <input type="hidden" id="rentSum" value="<%=rentSum%>" />
	    <input type="hidden" id="lightSum" value="<%=lightSum%>" />
	    <input type="hidden" id="val1" name="val1" value="${dataList.dataList[0].PLACE_TIME_SEQ }">
		<input type="hidden" id="val2" name="val2" value="${dataList.dataList[1].PLACE_TIME_SEQ }">
		<input type="hidden" id="val3" name="val3" value="${dataList.dataList[0].RESERVE_DATE }">
	    
	    <%-- <input type="hidden" id="sale1" value="${rentPriceList[1].price}"> --%>
    </tr>
	<tr>
    	<td colspan="2"><div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">주의사항 및 환불안내</div></td>
    </tr>    
	<tr>
    	<td colspan="2">
<%
//약관 축구장/야구장 #################################################################
if (PLACE_GROUP == 2 || PLACE_GROUP == 3) {
%>
예약후 결제 시 3시간 이내에 결제하지 않으면 자동으로 취소됩니다. <br>
<span style="color:rgb(255, 0, 0);">결제시간이 3시간 지난 후 입금하셔도 예약이 되지 않습니다.</span><br>
개별 결제만 가능합니다. <br>
환불규정을 꼭 숙지해 주시기바랍니다 <br>
서울특별시 도시공원조례의 환불규정에 의거하여 취소신청일 기준 해당하는 비율을 공제 후 환불 <br>
이용예정일 10일전 또는 예약당일 취소의 경우 전액환불 <br>
이용예정일 9~7일전 취소의 경우 사전 납부된 금액의 10% 공제 후 환불 <br>
이용예정일 6~5일전 취소의 경우 사전 납부된 금액의 30% 공제 후 환불 <br>
이용예정일 4~2일전 취소의 경우 사전 납부된 금액의 50% 공제 후 환불 <br>
이용예정일 1일전 취소의 경우 사전 납부된 금액의 80% 공제 후 환불 <br>
이용 당일의 경우 환불불가 <br>	
<%
//약관 테니스장  #################################################################
} else if (PLACE_GROUP == 4) {
%>

예약후 결제 시 1시간 이내 결제하지 않으면 자동으로 취소됩니다.<br>
<span style="color:rgb(255, 0, 0);">결제시간이 1시간 지난 후 입금하셔도 예약이 되지 않습니다.</span><br>
대관시간 결정은 행사준비 시간, 반출, 청소시간을 포함하여 신청합니다.<br>
신청이외의 출입시 추가비용을 별도로 내야 합니다.<br>
계약된 대관시간중 안전사고에 대하여 주의하여야 합니다.<br>
시설등을 파손하였을 때는 사용자의 과실로 배상해야 합니다.<br>
강습행위는 절대 할 수 없습니다.(볼 바구니, 카트 사용금지)<br>
대관 무단양도, 전매 등 부정행위 시 사용승인 정지 및 취소가 됩니다.<br>
기타 사항은 테니스장의 제반 규정 및 이용약관 에 따릅니다.<br>
소비자피해보상규정(재정경제부 고시 제2006-36호)의거하여 환불은 이용예정일 1일전까지 취소가능하며, 납부된 금액의 10%공제 후 환불	<br>			
<%
}
%>    	
<br>
<label><input type="checkbox" id="agree1" name="agree1" required> 위 주의사항 및 환불안내에 대한 내용을 확인하였습니다.</label> <br>    	
		</td>
    </tr>
    	
    </tbody>
</table>
<br>
<%if(PLACE_GROUP == 2 || PLACE_GROUP == 3) {%>
    <div class="btnarea margin_t80">
		<a href="#none" onclick="send('${rentCfg.COMCD}');" id=" " class="green">결제</a>
		<!-- <a href="#none" onclick="javascript:alert('현재 결제하실 수 없습니다. 관리자에게 문의해주세요');" id=" " class="green">결제</a> -->
		
        <a href="#none" onClick="history.back(-1);" id=" " class="gray2">취소</a>
    </div>
<%} else if(PLACE_GROUP == 4){ %>
	<div class="btnarea margin_t80">
		<a href="#none" onclick="send('${rentCfg.COMCD}');" id=" " class="green">결제</a>
		<!-- <a href="#none" onclick="javascript:alert('현재 결제하실 수 없습니다. 관리자에게 문의해주세요');" id=" " class="green">결제</a> -->
		<a href="#none" onClick="history.back(-1);" id=" " class="gray2">취소</a>
	</div>
<%} %>	
</form>
<%
totalSum = (strUrl.contains("localhost") || MEM_ID.equals("powerjyc")) ? 10 : totalSum;

String EncryptData = encodeMD5HexBase64(ediDate + MID + totalSum + merchantKey);


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
<%
//약관 축구장/야구장 #################################################################
if (PLACE_GROUP == 2 || PLACE_GROUP == 3) {
%>	    
		    <input type="hidden" name="PayMethod" maxlength="2" value="VBANK">
<%
} else if(PLACE_GROUP == 4) {		
%>
		    <input type="hidden" name="PayMethod" maxlength="2" value="CARD">
<%
}
%>
	    	
		    <input type="hidden" name="PayType" maxlength="2" value="">
		    
		    <input type="hidden" id="dividedSaleRent" name="dividedSaleRent" value="">
			<!-- 수량 -->
		    <input type="hidden" id="GoodsCnt" name="GoodsCnt" maxlength="2" value="1">
			<!--<div>상품명:</div>-->
		    <input type="hidden" id="GoodsName" name="GoodsName" maxlength="2" value="<%=GoodsName%>">
		
			<!-- <div>상품금액:</div> -->
			
		    <input type="hidden" id="goodsAmt" maxlength="2" value="<%=totalSum%>">
		    
		    <input type="hidden" id="Amt" name="Amt" maxlength="2" value="<%=totalSum%>">
			
			<!-- <div>주문번호:</div> -->
		    <input type="hidden" name="Moid" maxlength="2" value="Moid">
	    
		    <input type="hidden" name="MID" maxlength="2" value="<%=MID%>">
			
		    <input type="hidden" name="ReturnURL" id="ReturnURL" maxlength="2" value="<%=ReturnURL%>?q=${dataList.RESERVE_DATE}/${dataList.PLACE_CD}/${rentCfg.COMCD}/${dataList.rtn_idx}/<%=MEM_ID%>">
		    <input type="hidden" name="ReturnURL1" id="ReturnURL1" maxlength="2" value="<%=ReturnURL%>?q=${dataList.RESERVE_DATE}/${dataList.PLACE_CD}/${rentCfg.COMCD}/${dataList.rtn_idx}/<%=MEM_ID%>">
		    <%-- <input type="hidden" id="returnURL" name="ReturnURL" maxlength="2" value="<%=ReturnURL%>"> --%>
			
		    <input type="hidden" name="ReceiptType" maxlength="2" value="0">
			
		    <input type="hidden" name="RetryURL" maxlength="2" value="http://14.36.179.143:80/smartPay/vbankPay">
			<!-- "http://14.36.179.143:9470/smartPay/rentpay";
		    <tr>
		        <th scope="row">mallUserID</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="mallUserID" maxlength="2" value=""> -->
			<!-- <div>구매자:</div> -->
		    <input type="hidden" id="BuyerName" name="BuyerName" maxlength="2" value="<%=MEM_NM%>">
			<!-- <div>연락처:</div> -->
		    <input type="hidden" id="BuyerTel" name="BuyerTel" maxlength="2" value="<c:out value='${memData.HP}'/>">
			<!-- <div>이메일:</div> -->
		    <input type="hidden" id="BuyerEmail" name="BuyerEmail" maxlength="2" value="<c:out value='${memData.EMAIL}'/>">
			
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
		    
		    <input type="hidden" name="VbankExpDate" maxlength="2" value="<%=VbankExpDate%>">
			
		    <input type="hidden" id="EncryptData" name="EncryptData" maxlength="2" value="EncryptData">
    
		    <input type="hidden" name="FORWARD" maxlength="2" value="Y">
			
		    <input type="hidden" name="MallResultFWD" maxlength="2" value="">
			
		    <input type="hidden" name="TransType" maxlength="2" value="0">
			
		    <input type="hidden" name="EncodingType" maxlength="2" value="<%=EncodingType%>">
			
		    <input type="hidden" name="OpenType" maxlength="2" value="KR">
			   
		    <input type="hidden" name="because" id="because" maxlength="2" value="">
	    
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
		    <input type="hidden" id="merchantKey" name="merchantKey" maxlength="2" value="<%=merchantKey%>">
	
	    </tbody>
	
	</table>
	</form>
	
	<%-- iframe --%>
	<iframe src="" name="payFrame" frameborder="no" width="0" height="0" scrolling="yes" align="center"></iframe>
	
	<!-- /smartPay/mainCancelPay -->
	
	
		<!-- } 서브본문영역1내부 -->
		</div>
	</div>

	
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