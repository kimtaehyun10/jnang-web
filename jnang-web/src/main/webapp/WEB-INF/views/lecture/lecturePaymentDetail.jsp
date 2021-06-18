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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/lecture/lecture.payment.detail.js"></script>
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
	//final String DEV_PAY_ACTION_URL = "https://tpay.smilepay.co.kr/interfaceURL.jsp";	//개발테스트
	//final String PRD_PAY_ACTION_URL = "https://pay.smilepay.co.kr/interfaceURL.jsp";	//운영
	
	String actionUrl = (String) request.getAttribute("URL");
	String URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
	String MID = (String) request.getAttribute("MID"); //"SMTPAY001m";
	String merchantKey  = (String) request.getAttribute("KEY");
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

 	String REFERER = request.getHeader("REFERER");

 	// 상점 ID MID

	Member member 	= (Member) session.getAttribute("member");
	String MEM_ID	= member.getId();
	String MEM_NM 	= member.getMemNm();
	String MEM_MAIL = member.getEmail();
	String MEM_HP 	= member.getHp();
	String MEM_BIRTH = member.getSecBirthDate();
	String GENDER = member.getGender();
	//out.println("MEM_ID:"+ MEM_ID +"<BR>");
	//out.println("MEM_NM:"+ MEM_NM +"<BR>");
	//out.println("MEM_MAIL:"+ MEM_MAIL +"<BR>");
	

	//String EncryptData = encodeMD5HexBase64(ediDate + MID + goodsAmt + merchantKey);

%> 
<script type="text/javascript">

	<%
 	if (REFERER == null) {
 	%>
 		alert("정상적인 접근방법이 아닙니다.");
 		history.back(-1);
	</script>
 	<%
 	return;
 	}
	%>

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
		//가격 위변조값 체크
		goodsAmt = $('#costAmt').val();
		<%-- <%
		if (strUrl.contains("localhost") || strUrl.contains("14.36.179.143") || MEM_ID.equals("powerjyc")) {
		} else  {
		%>
		alert("현재 결제 서비스는 점검중입니다.");
		return false;
		<%
		}
		%> --%>
		var oderData = { }; //신청할 강좌 시작종료값 임시 저장
		oderData.sDate = "<%=ediDate%>".substring(0,8);
		oderData.eDate = "<%=ediDate%>".substring(0,8);
		oderData.step  = 2;

		//주문전 수량 첵크
		$.get('/data/lecture/basketIn', {secureText:localStorage.getItem('lecture'), "oderData" : oderData }, function(data){
			//console.log(data);
			if (data.code == "0"){
				alert("신청하신 강좌가 마감되었습니다.");
				window.location.href='/lecture/list/'+ data.COMCD;
				return false;
			}
		});
		
		//alert(seq +", "+ goodsAmt +", "+ GoodsName +", "+ COMCD);
		var BuyerName = encodeURI("<%=MEM_NM%>");
		
		//debugger;
		$.get("/data/getOdEncryptData/"+ <%=ediDate%> +"/"+ goodsAmt +"/"+ COMCD +"?GoodsName="+ encodeURI(GoodsName) +"&BuyerName="+ BuyerName, function(data){
		//try {
			//console.log(data);
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
			return ;
		}
		
		console.log("actionUrl:"+ form.GoodsName.value);
		console.log("actionUrl:"+ form.Amt.value);
		console.log("actionUrl:"+ form.GoodsCnt.value);
		console.log("actionUrl:"+ form.ReturnURL.value);
		
		if (form.GoodsName.value == "" || form.Amt.value == "" || form.GoodsCnt.value == "" ||form.ReturnURL.value == "")
		{
			alert("필수 입력값 오류!!");
			return ;
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
				
		return ;
	}
	
	//즉시감면 데이터 세팅
    function popImre(){
	var memName = $('#memName').val();
	var memBirth = $('#memBirth').val().substring(2,8); // 19881205
	
	//데이터 세팅
	   var param = "imreName="+memName //넘기지 않는 경우 이름 input 확성화
	   + "&imreBirth="+memBirth  //넘기지 않는 경우 이름 주민등록번호 앞자리 확성화
	   + "&imreDisCode=";   //넘기지 않는경우 할인항목 전체 노출
	   
	   window.open( "/imre/imReView.p?"+encodeURI(param)+"","", "width=608, height=645, resizable=no, scrollbars=yes, status=no" );	      
     }
            
    //즉시감면 콜백 함수(함수명 변경불가)
    function returnPopImre(imreDisCode){
    //axf.alert("즉시감면 대상 코드 반환 : " + imreDisCode);
    	var seq = $('#SEQ').val();
    	var classNm = $('#classNm').val();
    	var comcd = $('#comcd').val();    	
    	
    	var html1 = '';
    	var html2 = '';
    	var html3 = '';
    	var beforeCost = '';
    	var afterCost = '';
    	var resultCost = '';    	    	    					
    	
    	if(imreDisCode == '29'){
    		html1 = '2자녀이상(20%)';
    		
    		beforeCost = $('#lectureCost').val();
    		afterCost = Math.round((beforeCost*0.2)/100)*100;
    		resultCost = beforeCost - afterCost;
    		html2 += '수강료 : '+comma_str_y(resultCost)+'원(할인가격 : '+comma_str_y(afterCost)+'원)';   		
    	}
    	if(imreDisCode == '21'){
    		html1 = '3자녀이상(50%)';
    		
    		beforeCost = $('#lectureCost').val();
    		afterCost = Math.round((beforeCost*0.5)/100)*100;
    		resultCost = beforeCost - afterCost;
    		html2 += '수강료 : '+comma_str_y(resultCost)+'원(할인가격 : '+comma_str_y(afterCost)+'원)';
    		
    	}
    	if(imreDisCode == '02'){
    		html1 = '장애인 본인(50%)';
    		
    		beforeCost = $('#lectureCost').val();
    		afterCost = Math.round((beforeCost*0.5)/100)*100;
    		resultCost = beforeCost - afterCost;
    		html2 += '수강료 : '+comma_str_y(resultCost)+'원(할인가격 : '+comma_str_y(afterCost)+'원)';
    		    		
    	}
    	if(imreDisCode == '01'){
    		html1 = '국가유공자(50%)';
    		
    		beforeCost = $('#lectureCost').val();
    		afterCost = Math.round((beforeCost*0.5)/100)*100;
    		resultCost = beforeCost - afterCost;
    		html2 += '수강료 : '+comma_str_y(resultCost)+'원(할인가격 : '+comma_str_y(afterCost)+'원)';
    		
    	}
    	if(imreDisCode == '05'){
    		html1 = '기초생활수급자(50%)';
    		
    		beforeCost = $('#lectureCost').val();
    		afterCost = Math.round((beforeCost*0.5)/100)*100;
    		resultCost = beforeCost - afterCost;
    		html2 += '수강료 : '+comma_str_y(resultCost)+'원(할인가격 : '+comma_str_y(afterCost)+'원)';
    		
    	}
    	if(imreDisCode == '31'){
    		html1 = '병역명문가증 소지자(30%)';
    		
    		beforeCost = $('#lectureCost').val();
    		afterCost = Math.round((beforeCost*0.3)/100)*100;
    		resultCost = beforeCost - afterCost;
    		html2 += '수강료 : '+comma_str_y(resultCost)+'원(할인가격 : '+comma_str_y(afterCost)+'원)';
    		
    	}
    	
    	html3 += '<a class="size_m2 btn_gray2" href="/lecture/list">목록</a>';
    	<!-- html3 += '<a class="size_m2 btn_red2" onclick=\"goBtn('"+ seq +"','"+ resultCost +"','"+ classNm +"','"+ comcd +"');\">결제</a>'; -->
		html3 += "<a class='size_m2 btn_red2' onclick=\"goBtn('"+ seq +"','"+ resultCost +"','"+ classNm +"','"+ comcd +"' );\">결제</a>";										  		
		
		$('#costAmt').val(resultCost);				
    	$("#DCREASON_CD").html(html1);		
		$("#costHtml").html(html2);
		$('#insertLecture').empty().append(html3);
		
    }
	
	//결제방법 selectBox
	function changeBox(){
		var searchKey = $('#searchKey').val();
		
		if(searchKey == 'S'){
			$('#PayMethod').val('CARD');
		}else if(searchKey == 'G'){
			$('#PayMethod').val('BANK');
		}
	}

	
	
</script>
<div class='sub_lecture_v01'>
	<input type="hidden" id="SEQ" value="${SEQ}">	
	<input type="hidden" id="classNm" value="">
	<input type="hidden" id="comcd" value="">
	<input type="hidden" id="memBirth" value="<%=MEM_BIRTH%>">
	<input type="hidden" id="memName" value="<%=MEM_NM%>">
	<input type="hidden" id="memGender" value="<%=GENDER%>">
	<input type="hidden" id="GUBUN" value="${GUBUN}">
	<input type="hidden" id="costAmt" value="">
	<table id='programTable' class='stbl_w3b' summary='이 표는 강좌명/대상/요일/시간/정원/수강료 등의 정보로 구성되어 있습니다.'></table>
	<table style="margin-top:50px;" id='programDetailTable' class='stbl_w3b' summary='이 표는 강좌소개/세부내용/기타 등의 정보로 구성되어 있습니다.'></table>

	<div style="margin-top:50px;" class='bx_btns_01a' id="insertLecture">
		<!-- <a class='size_m2 btn_gray2' href='/lecture/list'>목록</a>
		<a class='size_m2 btn_gray2' href='#'>결제</a> -->
		<!-- onClick=\"goBtn('"+ data[i].SEQ +"','"+ 10 +"','"+ GoodsName +"','"+ data[i].COMCD +"', );\" -->
		
 <!-- 	<sec:authorize access="isAnonymous()" >
	<input id="user" type='submit' class='size_m2 btn_gray2' value='신청' onclick="needAuthorize.user();">
	   <input type='submit' class='size_m2 btn_gray2' value='신청' onclick="stop();"> 
		</sec:authorize>
		
		<sec:authorize access="isAuthenticated()" >
		 	<sec:authorize access="!hasAnyRole('ROLE_CARD')" >
				<input type='submit' class='size_m2 btn_gray2' value='신청' onclick="needAuthorize.card();">
			</sec:authorize> 
			<sec:authorize access="hasAnyRole('ROLE_CARD')" >
	 			<input type='submit' class='size_m2 btn_green1' value='신청' onclick="addBasket();">
			</sec:authorize>
		</sec:authorize> -->
	</div>
</div>


<form name="tranMgr" method="post" action=""> <!-- method="post" action="" -->
	<table class="type">
	    <tbody>
		    <input type="hidden" id="PayMethod" name="PayMethod" maxlength="2" value="CARD">
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

