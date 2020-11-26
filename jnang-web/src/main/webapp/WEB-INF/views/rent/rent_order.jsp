<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
*******************************************************************************/
	
	//InetAddress inet = InetAddress.getLocalHost(); // 서버 IP 가져오기
	//Timestamp toDay = new Timestamp((new Date()).getTime()); // 현재날짜
	//Timestamp nxDay = getTimestampWithSpan(toDay, 1); // 가상계좌 입금만료일  1일후 가져오기
	//String VbankExpDate = nxDay.toString();
	//VbankExpDate = VbankExpDate.substring(0, 10); 
	//VbankExpDate = VbankExpDate.replaceAll("-", "");
	//String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
	//상품주문번호	
	String Moid = "Moid"; 
	final String DEV_PAY_ACTION_URL = "https://tpay.smilepay.co.kr/interfaceURL.jsp";	//개발테스트
	final String PRD_PAY_ACTION_URL = "https://pay.smilepay.co.kr/interfaceURL.jsp";	//운영
	String actionUrl = DEV_PAY_ACTION_URL; // 개발 서버 URL
	String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String MID = "SMTPAY001m";
	String DivideInfo = ""; //서브몰 결제 정보 //request.getParameter("DivideInfo");
	
	String ReturnURL = URL +"/smartPay/returnPay"; //Vos.getRtnPayURL(); //"http://localhost:8080/smartPay/returnPay"; //"https://tpay.smilepay.co.kr/returnPay.jsp"; //리턴url
	String EncodingType = "utf8"; //euckr/utf8

%> 


<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
String today = transFormat.format(from);

%>
<script type="text/javascript">

$(function(){
	setMenuTitle('bg_99', '체육시설', '예약/결제', true);
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


function send(){

	
	goPay();

	return false;

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
		
		var frm1 = document.frm1;
		if(frm1.agree1.checked == false) {
			alert('주의 사항 및 환불 안내를 확인하고 선택하여 주세요.');
			frm1.agree1.focus();
			return false;
		}
		
		
		var form = document.tranMgr;
		form.action = '<%=actionUrl%>';
		
		if (form.GoodsCnt.value == "0") {
			alert("수강 신청 내역이 없습니다.");
			return false;
		}
			
		
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
		<td><c:out value='${memData.MEM_NM}'/></td>
	  </tr>
	  <tr>	
	  <th>신청자정보</th>
	  <td><c:out value='${memData.HP}'/> &nbsp; | &nbsp; <c:out value='${memData.EMAIL}'/></td>
	  </tr>
	  <tr>
		<th> 시설명 </th>
		<td><c:out value='${rentCfg.COMNM}'/></td>
	  </tr>	  
	  <tr>
	    <th>대관비용</th>
	    <td>
			<c:forEach items="${dataList.dataList}" var="result" varStatus="status">
			
				
				<c:if test="${result.orderYN eq 'Y'}"> 
					[예약 성공]
					<c:set var="otherCfg" value="${rentCfg.other_cfg}" />
				</c:if>
				<c:if test="${result.orderYN eq 'N'}"> 
					<span class='btn_red1'>[예약 실패]</span>
				</c:if>
			
				<c:if test="${result.place_tab ne '0'}"> 
					&nbsp; ${result.place_tab} 코트
				</c:if>
				&nbsp; ${result.RESERVE_DATE} &nbsp; ${result.item} <BR> 
				
			</c:forEach>		
	    </td>
    </tr>
	  <tr>
	    <th>조명료</th>
	    <td><c:out value='${myData.HOME_TEL}'/></td>
    </tr>
    <c:if test="${rentCfg.PLACE_GROUP eq '4'}">
    	<!-- 우선 4번 그룹만 할인적용 --> 
	  <tr>
	    <th>할인</th>
	    <td>
	    	<select name="" class="inputbox_01a" >
	    	<option value="">==할인 선택==</option>
	    	<option value="01">중랑구민(10%)</option>
	    	<option value="01">경로우대(30%)</option>
	    	<option value="01">장애인(%0%)</option>
	    	<option value="01">기초수급자(%0%)</option>
	    	<option value="01">국가유공자(10%)</option>
	    	</select>
	    	(단, 코드당 이용자의 50% 이상이 할인 대상자일 경우)
		</td>
    	</tr>
    </c:if>
	  <tr>
	    <th>합계</th>
	    <td><c:out value='${myData.HOME_TEL}'/></td>
    </tr>
	<tr>
    	<td colspan="2"><div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">주의사항 및 환불안내</div></td>
    </tr>    
	<tr>
    	<td colspan="2">
예약후 결제 시 3시간 이내에 결제하지 않으면 자동으로 취소됩니다. <br>
개별 결제만 가능합니다. <br>
환불규정을 꼭 숙지해 주시기바랍니다 <br>
서울특별시 도시공원조례의 환불규정에 의거하여 취소신청일 기준 해당하는 비율을 공제 후 환불 <br>
이용예정일 10일전 또는 예약당일 취소의 경우 전액환불 <br>
이용예정일 9~7일전 취소의 경우 사전 납부된 금액의 10% 공제 후 환불 <br>
이용예정일 6~5일전 취소의 경우 사전 납부된 금액의 30% 공제 후 환불 <br>
이용예정일 4~2일전 취소의 경우 사전 납부된 금액의 50% 공제 후 환불 <br>
이용예정일 1일전 취소의 경우 사전 납부된 금액의 80% 공제 후 환불 <br>
이용 당일의 경우 환불불가 <br>
 <br>
<label><input type="checkbox" id="agree1" name="agree1" required> 위 주의사항 및 환불안내에 대한 내용을 확인하였습니다.</label> <br>    	
		</td>
    </tr>
    	
    </tbody>
</table>
<br>

    <div class="btnarea margin_t80">
		<a href="#none" onclick="goPay();" id=" " class="green">결제</a>
        <a href="#none" onClick="data.list('del');" id=" " class="gray2">취소</a>
    </div>

</form>






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
	    
		    <input type="hidden" name="MID" maxlength="2" value="<%=MID%>">
			
		    <input type="hidden" name="ReturnURL" maxlength="2" value="<%=ReturnURL%>">
			
		    <input type="hidden" name="ReceiptType" maxlength="2" value="0">
			
		    <input type="hidden" name="RetryURL" maxlength="2" value="<%=ReturnURL%>">
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
	<iframe src="" name="payFrame" frameborder="no" width="0" height="0" scrolling="yes" align="center"></iframe>
	
	<!-- /smartPay/mainCancelPay -->
	
	
		<!-- } 서브본문영역1내부 -->
		</div>
	</div>
