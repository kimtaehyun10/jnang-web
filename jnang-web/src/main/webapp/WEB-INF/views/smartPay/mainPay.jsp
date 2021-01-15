<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.nio.ByteBuffer"%>
<%@page import="java.nio.charset.Charset"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%//@page import="com.dwict.jfmc.client.smpay.model.VoPay"%>
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
	
	InetAddress inet = InetAddress.getLocalHost(); // 서버 IP 가져오기
	Timestamp toDay = new Timestamp((new Date()).getTime()); // 현재날짜
	Timestamp nxDay = getTimestampWithSpan(toDay, 1); // 가상계좌 입금만료일  1일후 가져오기
	String VbankExpDate = nxDay.toString();
	VbankExpDate = VbankExpDate.substring(0, 10); 
	VbankExpDate = VbankExpDate.replaceAll("-", "");
	String ediDate = getyyyyMMddHHmmss(); // 전문생성일시
	//상품주문번호	
	String Moid = "Moid"; 
	final String DEV_PAY_ACTION_URL = "https://tpay.smilepay.co.kr/interfaceURL.jsp";	//개발
	final String PRD_PAY_ACTION_URL = "https://pay.smilepay.co.kr/interfaceURL.jsp";	//운영
	String actionUrl = DEV_PAY_ACTION_URL; // 개발 서버 URL
	
	
	
 	// 상점서명키 (꼭 해당 상점키로 바꿔주세요)
 	String merchantKey = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";
 	//String merchantKey = "IYaOCqVRvikKOKFyZj2orqsphGQX4g48eF17sYMVoYV8GJApVgovlns+tdRXSFEr+q6kobAHgu9aSE5H/qKQ8g==";
 	//VoPay test1 = new VoPay();
    
 	// 상점 ID MID
	String MID = "SMTPAY001m";
 	//String MID = "jungnan01m"; //위에 URl 변경 DEV_PAY_ACTION_URL ==> PRD_PAY_ACTION_URL
 	
	String GoodsCnt = "1"; //상품수량
	String GoodsNameDp = "상품명";
	String GoodsName = "상품명"; //request.getParameter("GoodsName");
	String goodsAmt =  "10"; //request.getParameter("goodsAmt"); //결제금액
	String BuyerNameDp = "구매자";
	String BuyerName = request.getParameter("BuyerName");
	String MemberNo = "111111";
	String BuyerAddr = request.getParameter("BuyerAddr");
	BuyerAddr = (BuyerAddr == null) ? "" : BuyerAddr;
	String DivideInfo = ""; //서브몰 결제 정보 //request.getParameter("DivideInfo");
	
	String BuyerTel = "010--";
	BuyerTel = (BuyerTel == null) ? "" : BuyerTel;
	String BuyerEmail = "";
	BuyerEmail = (BuyerEmail == null) ? "" : BuyerEmail;
	
	String ReturnURL = "http://localhost:8080/smartPay/returnPay"; //"https://tpay.smilepay.co.kr/returnPay.jsp"; //리턴url
	
	if (GoodsName == null || goodsAmt == null || GoodsCnt == null || ReturnURL == null) {
		%>
		<script type="text/javascript">
			alert("필수 입력값 오류");
			history.back(-1);
		</script>
		<%
		out.close();
	}
	
	String EncodingType = "utf8"; //euckr/utf8
	// 가맹점 utf-8 방식 사용시, euc-kr로 urlencoding 필요
	GoodsName = URLEncoder.encode(GoodsName, "EUC-KR");
	BuyerName = URLEncoder.encode(BuyerName, "euc-kr");
	BuyerAddr = URLEncoder.encode(BuyerAddr, "euc-kr");

	if(StringUtils.isNotEmpty(DivideInfo) ) // Base64 인코딩(utf-8 인코딩)
	{
		String temp = DivideInfo.replaceAll("&#39;","\"");
		Charset euckrCharset = Charset.forName("utf-8");
		ByteBuffer byteBuffer = euckrCharset.encode(temp);
		byte[] euckrStringBuffer = new byte[byteBuffer.remaining()];
		byteBuffer.get(euckrStringBuffer);
								
		String b64Enc = new String(Base64.encodeBase64(euckrStringBuffer));
		DivideInfo = b64Enc;
	}

	String EncryptData = encodeMD5HexBase64(ediDate + MID + goodsAmt + merchantKey);


 	String encodeString = "";
	String encodedString = URLEncoder.encode(encodeString, "EUC-KR");
	System.out.println(encodedString); 
	encodedString = "%EC%B9%B4%EB%93%9C+%EA%B2%B0%EC%A0%9C+%EC%84%B1%EA%B3%B5";
	String decodedString = URLDecoder.decode(encodedString, "UTF-8");    
	System.out.println(decodedString); // 자바킹75! *-blog.me+=_~
%> 
decodedString : <%=decodedString%>
<style type="text/css">
<!--
table.type {
    border-collapse: collapse;
    text-align: left;
    line-height: 1.5;
}
table.type thead th {
    padding: 8px;
    font-weight: bold;
    vertical-align: top;
    color: #369;
    border-bottom: 3px solid #036;
    text-align:center;
}
table.type tbody th {
    width: 150px;
    padding: 8px;
    font-weight: bold;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
    background: #f3f6f7;
    text-align:center;
}
table.type td {
    width: 250px;
    padding: 8px;
    vertical-align: top;
    border-bottom: 1px solid #ccc;
    text-align:center;
}

input {
    width: 100%;
    padding: 6px 10px;
    margin: 2px 0;
    box-sizing: border-box;
}

.btnblue {
    background-color: #6DA2D9;
    box-shadow: 0 0 0 1px #6698cb inset,
                0 0 0 2px rgba(255,255,255,0.15) inset,
                0 4px 0 0 rgba(110, 164, 219, .7),
                0 4px 0 1px rgba(0,0,0,.4),
                0 4px 4px 1px rgba(0,0,0,0.5);
}

-->
</style>

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
		
		//setAcceptCharset(form);
		
		//form.GoodsName.value = encodeURI("<%=GoodsName%>");
		//form.BuyerName.value = "<%=BuyerName%>";
		//form.BuyerAddr.value = "<%=BuyerAddr%>";
		form.EncryptData.value = "<%=EncryptData%>";
		//form.DivideInfo.value = "<%=DivideInfo%>";
				
		if(form.FORWARD.value == 'Y') // 화면처리방식 Y(권장):상점페이지 팝업호출
		{
			var popupX = ((window.screen.width) / 2) - (545 / 2);
			var popupY = ((window.screen.height) /2) - (573 / 2);
						
			var winopts= "width=545,height=573,toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=no,left="+ popupX + ", top="+ popupY + ", screenX="+ popupX + ", screenY= "+ popupY;
			var win =  window.open("", "payWindow", winopts);
			
			try{
			    if(win == null || win.closed || typeof win.closed == 'undefined' || win.screenLeft == 0) { 
			    	alert('브라우저 팝업이 차단으로 설정되었습니다.\n 팝업 차단 해제를 설정해 주시기 바랍니다.');
			    	return false;
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

<form name="tranMgr" method="post" action="">
	<table class="type">
	    <tbody>
		    <input type="hidden" name="PayMethod" maxlength="2" value="CARD">
		    <input type="hidden" name="PayType" maxlength="2" value="">
		    <div>주문수량:<%=GoodsCnt%></div>
		    <input type="hidden" name="GoodsCnt" maxlength="2" value="<%=GoodsCnt%>">
			
			<div>상품명:<%=GoodsNameDp%></div>
		    <input type="hiddenx" name="GoodsName" maxlength="2" value="<%=GoodsName%>">
			
			<div>상품금액:<%=goodsAmt%></div>
		    <input type="hidden" name="Amt" maxlength="2" value="<%=goodsAmt%>">
			
			<div>주문번호:<%=Moid%></div>
		    <input type="hidden" name="Moid" maxlength="2" value="<%=Moid%>">
	    
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
			<div>구매자:<%=BuyerNameDp%></div>
		    <input type="hiddenx" name="BuyerName" maxlength="2" value="<%=BuyerName%>">
			<div>연락처:<%=BuyerTel%></div>
		    <input type="hidden" name="BuyerTel" maxlength="2" value="<%=BuyerTel%>">
			<div>이메일:<%=BuyerEmail%></div>
		    <input type="hidden" name="BuyerEmail" maxlength="2" value="<%=BuyerEmail%>">
			
			<!-- 
		    <tr>
		        <th scope="row">ParentEmail</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="ParentEmail" maxlength="2" value="">
			 -->
		    <input type="hidden" name="BuyerAddr" maxlength="2" value="<%=BuyerAddr%>">
			<!-- 
		    <tr>
		        <th scope="row">BuyerPostNo</th>
		        <td></td>
		    </tr>		    
		    <input type="hidden" name="BuyerPostNo" maxlength="2" value="">
			 -->
		    <input type="hiddenx" name="UserIP" maxlength="2" value="<%//=InetAddress[0]%>">
  
		    <input type="hiddenx" name="MallIP" maxlength="2" value="<%//=InetAddress%>">
		    
		    <input type="hidden" name="VbankExpDate" maxlength="2" value="<%=VbankExpDate%>">
			
		    <input type="hidden" name="EncryptData" maxlength="2" value="">
    
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
			-->
		    <input type="hidden" name="SspMallID" maxlength="2" value="<%=MemberNo%>">
			<!-- 
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
			
		    <input type="hidden" name="ediDate" maxlength="2" value="<%=ediDate%>">
			
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
		        <td><%=merchantKey%> %></td>
		    </tr>	
		    -->	    
		    <input type="hiddenx" name="merchantKey" maxlength="2" value="<%=merchantKey%>">
		    <input type="hiddenx" name="MemberNo" maxlength="2" value="<%=MemberNo%>">



	 	    <tr>
		        <td class="btnblue" colspan="4" onclick="goPay();">결제하기</td>
		    </tr>
	
	    </tbody>
	
	</table>
	</form>
	
	<%-- iframe --%>
	<iframe src="about:blank" name="payFrame" frameborder="no" width="0"
		height="0" scrolling="yes" align="center"></iframe>
	
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
