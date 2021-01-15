<!--@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"-->
<%@ page import="java.io.UnsupportedEncodingException"%>
<%@ page import="java.net.URLDecoder"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Enumeration"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<%@ page import="org.apache.commons.codec.digest.DigestUtils"%>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.io.OutputStreamWriter"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.io.IOException"%>
<%@ page import="java.io.InputStream"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.nio.ByteBuffer"%>
<%@page import="java.nio.charset.Charset"%>
<%@ page import="java.util.Iterator" %>
<%@page contentType="text/html; charset=utf-8"%>
<%--@page import="kr.co.smartro.pg.document.Box"--%>
<%--@page import="kr.co.smartro.pg.common.biz.CommonBiz"--%>
<%
/******************************************************************************
*
*	@ SYSTEM NAME		: SmilePay Url Call 방식 결제 취소 테스트 페이지
*	@ PROGRAM NAME		: CancelPayUrlCallSample.jsp
*	@ MAKER				: sajang
*	@ MAKE DATE			: 2018.03.19
*	@ PROGRAM CONTENTS	: SmilePay Url Call 방식 결제 취소 테스트 페이지
*						  운영 MID/상점키는 스마트로 영업담당자 통하여 
*						  받으실 수 있습니다.
************************** 변 경 이 력 *****************************************
* 번호	작업자		작업일				변경내용
*	1	sajang		2018.03.19	SmilePay Url Call 방식 결제 취소 테스트 페이지
*******************************************************************************/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String payURL = (String) request.getAttribute("payURL");
String MERCHANT_KEY = (String) request.getAttribute("merchantKey");
String Cancelpw = ""; //request.getParameter("Cancelpw");
//String MERCHANT_KEY = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";// MID(SMTPAY001m)의 상점키 설정 - 결제 요청한 상점ID의 상점키를 입력
//out.println("merchantKey:"+ merchantKey +"<BR>");
//out.println("storeMID:"+ MERCHANT_KEY +"<BR>");
out.println("payURL:"+ payURL +"<BR>");
//
String PRD_CANCEL_ACTION_URL = "";
//실제 테스트
if (payURL.contains("https://pay.sm")) {
	Cancelpw = "098033";
	PRD_CANCEL_ACTION_URL = "https://pay.smilepay.co.kr/cancel/payCancelNVProcess.jsp";
} else {
	Cancelpw = "123456";
	PRD_CANCEL_ACTION_URL = "https://tpay.smilepay.co.kr/cancel/payCancelNVProcess.jsp";
}
out.println("Cancelpw:"+ Cancelpw +"<BR>");
//final String DEV_CANCEL_ACTION_URL = "https://tpay.smilepay.co.kr/cancel/payCancelNVProcess.jsp";//개발
//final String PRD_CANCEL_ACTION_URL = "https://pay.smilepay.co.kr/cancel/payCancelNVProcess.jsp";//운영
final String SUCCESS_CANCEL = "2001";//취소 성공 코드
final String SUCCESS_REFUND = "2211";//환불 성공 코드(계좌이체, 가상계좌)
final String CHAR_SET = "urf-8";
HashMap<String,String> cancelRequest = new HashMap<String,String>();
HashMap<String,String> result;
String rTemp = "";

// 결제 취소 결과
String PayMethod = "";
String PayName = "";
String MID = "";
String TID = "";
String CancelAmt = "";
String CancelMSG = "";
String ResultCode = "";
String ResultMsg = "";
String CancelDate = "";
String CancelTime = "";
String CancelNum = "";
String Moid = "";
String DivideInfo = "";

String sendMID = "";
String sendTID = request.getParameter("TID");

//취소 요청 데이터 설정
cancelRequest.put("TID", sendTID==null?"":sendTID);									//1.취소할 거래 TID [필수]
cancelRequest.put("CancelAmt", request.getParameter("CancelAmt")==null?"":request.getParameter("CancelAmt"));						//2.취소 금액	[필수]
cancelRequest.put("Cancelpw",  Cancelpw ==null ? "": Cancelpw);						//3.취소 패스워드	[필수]
//cancelRequest.put("Cancelpw", request.getParameter("Cancelpw")==null?"":request.getParameter("Cancelpw"));						//3.취소 패스워드	[필수]
cancelRequest.put("CancelMSG", request.getParameter("CancelMSG")==null?"":urlEncodeEuckr(request.getParameter("CancelMSG")));		//4.취소 사유 메세지 (euc-kr urlencoding)
cancelRequest.put("PartialCancelCode", request.getParameter("PartialCancelCode")==null?"":request.getParameter("PartialCancelCode"));		//5.전체취소 0, 부분취소 1 [전체취소 Default]
cancelRequest.put("MerchantMode", request.getParameter("MerchantMode")==null?"":request.getParameter("MerchantMode"));				//6.상점모드 - 서브몰정산 가맹점 전용 (MerchantMode : "T")

DivideInfo = request.getParameter("DivideInfo"); //7.서브몰 정보

if(StringUtils.isNotEmpty(DivideInfo)) // Base64 인코딩(utf-8 인코딩)
{
	String temp = DivideInfo.replaceAll("&#39;","\"");
	Charset euckrCharset = Charset.forName("utf-8");
	ByteBuffer byteBuffer = euckrCharset.encode(temp);
	byte[] euckrStringBuffer = new byte[byteBuffer.remaining()];
	byteBuffer.get(euckrStringBuffer);
							
	String b64Enc = new String(Base64.encodeBase64(euckrStringBuffer));
	DivideInfo = b64Enc;

	cancelRequest.put("DivideInfo", DivideInfo);
}

//TID + 상점키 + 취소 금액 + 취소타입
String plainHashData = cancelRequest.get("TID") + MERCHANT_KEY  +  cancelRequest.get("CancelAmt") + cancelRequest.get("PartialCancelCode");
String hashData = encodeMD5HexBase64(plainHashData);

System.out.println("CancelUrlPayCallSample plainHashData : " + plainHashData);	
System.out.println("CancelUrlPayCallSample hashData : " + hashData);

cancelRequest.put("hashData", hashData);//6.HASH 설정 [필수]

try {
//http 통신
rTemp = sendByPost(cancelRequest, PRD_CANCEL_ACTION_URL).trim();
} catch (Exception e) {
e.printStackTrace();
}

result = parseMessage(rTemp, "&", "=");
System.out.println("CancelUrlPayCallSample result : " + result);	

PayMethod = result.get("PayMethod");
PayName = urlDecodeEuckr(result.get("PayName"));
MID = result.get("MID");
TID = result.get("TID");
CancelAmt = result.get("CancelAmt");
CancelMSG = urlDecodeEuckr(result.get("CancelMSG"));
ResultCode = result.get("ResultCode");
ResultMsg = urlDecodeEuckr(result.get("ResultMsg"));
CancelDate = result.get("CancelDate");
CancelTime = result.get("CancelTime");
CancelNum = result.get("CancelNum");
Moid = result.get("Moid");

if(SUCCESS_CANCEL.equals(result.get("ResultCode")) || SUCCESS_REFUND.equals(result.get("ResultCode"))) {
// 취소 및 환불 성공에 따른 가맹점 비지니스 로직 구현 필요

%>
<!-- 당일취소처리 -->
<script type="text/java">
	$.get('/data/cancelPay', $("#frm2").serialize() , function(data){
		if (data != null && data != "null") {
			$("#val1").val(data.rentIdx);
			$("#val2").val(data.rentYN);			
			frm.submit();
		} else if (data == "-9") {
			alert("사용자 동의후 접수 가능합니다.");
		} else {
			alert('접수 오류 !  다시 신청 하십시요!~');
			top.location.reload();
		}
	}).done(function(data){
		
	},"json");
</script>
<%
} else {
// 취소 및 환불 실패에 따른 가맹점 비지니스 로직 구현 필요
}
%>
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

.selectbox {
position: relative;
width: auto;
border: 1px solid #999;
z-index: 1;
padding: 4px;
color: #369;
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

<div class="sub_cont1">
		<div class="con_bx">

<table class="type" align="center" style="margin:none;">

<thead>
	<tr>
		<th colspan="4" style="text-align:right;color: #000;">결제취소 결과페이지</th>
	</tr>
</thead>

<thead>
	<tr>
		<th scope="cols">항목명</th>
		<th scope="cols" style="width:80px;">value</th>           
	</tr>
</thead>

<tbody>
	
	<tr>
		<th scope="row">지불수단명</th>
		<td><%=PayName%></td>
	</tr>

	   <tr>
		<th scope="row">취소금액</th>
		<td><%=CancelAmt%></td>
	</tr>

	   <tr>
		<th scope="row">취소메세지</th>
		<td><%=CancelMSG%></td>
	</tr>

	   <tr>
		<th scope="row">결과코드</th>
		<td><%=ResultCode%></td>
	</tr>

	   <tr>
		<th scope="row">결과메시지</th>
		<td><%=ResultMsg%></td>
	</tr>

	   <tr>
		<th scope="row">취소일자</th>
		<td><%=CancelDate%></td>
	</tr>

	   <tr>
		<th scope="row">취소시간</th>
		<td><%=CancelTime%></td>
	</tr>

	   <tr>
		<th scope="row">취소번호</th>
		<td><%=CancelNum%></td>
	</tr>

</tbody>

</table>

	<a href="/mypage/classStatus" class="size_m2 btn_green1 fontsize_1dot50" >취소처리 확인</a>

<%!
public final String encodeMD5HexBase64(String pw){
return new String(Base64.encodeBase64(DigestUtils.md5Hex(pw).getBytes()));
}

/**
* 응답메세지 파싱
* @param plainText
* @param delim
* @param delim2
* @return
*/
public HashMap<String,String> parseMessage(String plainText, String delim, String delim2)
{
HashMap<String,String> retData = new HashMap<String,String>();
ArrayList<String> tokened_array = tokenizerWithBlanks(plainText, delim);
String temp = "";
for (int i = 0; i < tokened_array.size(); i++) {
	temp = tokened_array.get(i);
	if (StringUtils.isNotEmpty(temp)) {
		retData.put( temp.substring(0,temp.indexOf(delim2)),temp.substring(temp.indexOf(delim2)+1).trim() );
	}
}
return retData;
}

public String urlEncodeEuckr(String str)
{
try {
	str =  URLEncoder.encode(str, "euc-kr");
} catch (UnsupportedEncodingException e) {
	e.printStackTrace();
}
return str;
}

public String urlDecodeEuckr(String str)
{
try {
	str =  URLDecoder.decode(str, "euc-kr");
} catch (UnsupportedEncodingException e) {
	e.printStackTrace();
}
return str;
}

/**
* 입력받은 스트링을 공백과 함께 delimiter로 자른다.
*
* @param input 파싱할 문자열
* @param delimiter 구분자
*/
ArrayList<String> tokenizerWithBlanks(String input, String delimiter)
{
ArrayList<String> array = new ArrayList<String>();
String token;
int pos;
int delimiterSize = delimiter.length();
do{
	pos = input.indexOf(delimiter);
	if (pos >= 0)
	{
		token = input.substring(0, pos);
		input = input.substring(pos + delimiterSize);
	}
	else
	{
		token = input;
		input = "";
	}
	array.add(token);
} while (pos >= 0);
return array;
}

/**
 * Post Method 방식으로 통신
 * 
 * @param request		요청할 데이터
 * @param actionURL		요청할 URL
 * @return
 * @throws Exception
 */

 public String sendByPost(HashMap<String,String> requestMap, String requestURL) throws Exception
{
	try 
	{
		StringBuffer buffer = new StringBuffer();
		Iterator<String> keys = requestMap.keySet().iterator();
		int size = requestMap.size();
		buffer.append("?");
		int i = 0;
		while (keys.hasNext()) {
			String key = keys.next();
			buffer.append(key).append("=").append(urlEncodeEuckr(requestMap.get(key)));
			if (true == keys.hasNext()) {
				buffer.append("&"); 
			}
		}
		
		System.out.println("requestURL [ " + requestURL + buffer.toString() + " ]");
		
		URL url = new URL(requestURL + buffer.toString());

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), "euc-kr"); // 서버는  "EUC-KR" 입니다 변경하지 마세요..
		//wr.write(buffer.toString());
		wr.flush();

		BufferedReader rd = new BufferedReader(new InputStreamReader( connection.getInputStream()));  // 서버는  "EUC-KR" 입니다 필요시 UTF-8 형으로 변경하세요.
		String result = "";
		String line;
		while ((line = rd.readLine()) != null)
		{
			result += line;
		}
		wr.close();
		rd.close();

		return result;
	} 
	catch (Exception e) 
	{
		System.out.println(" UrlCall Exception :"+e.toString());
		return "1";
	}
}
%>