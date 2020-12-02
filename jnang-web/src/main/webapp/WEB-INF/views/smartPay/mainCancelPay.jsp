<%
/******************************************************************************
*
*	@ SYSTEM NAME		: 결제취소 요청 페이지
*	@ PROGRAM NAME		: mainCancelPay_URLCall.jsp
*	@ MAKER				: sajang
*	@ MAKE DATE			: 2017.12.01
*	@ PROGRAM CONTENTS	: 결제취소 요청 페이지
*
************************** 변 경 이 력 *****************************************
* 번호	작업자		작업일				변경내용
*	1	스마트로	2017.12.01		결제취소 요청 페이지
*******************************************************************************/
%>
<%@page contentType="text/html; charset=utf-8"%>
<%
	String actionUrl = "/smartPay/CancelPayAct";
	String TID			= request.getParameter("TID");
	String MID			= request.getParameter("MID");
	String CancelAmt    = request.getParameter("CancelAmt");
	String CancelMSG	= "테스트 취소"; //request.getParameter("CancelMSG");
	
	TID = TID == null ? "" : TID.trim();
	MID = MID == null ? "" : MID.trim();
	CancelMSG = CancelMSG == null ? "" : CancelMSG;

	/*	서브몰정산 가맹점 전용 */
	String MerchantMode = "T"; // 상점모드
	String DivideInfo = "{'DivideInfo':[{'Amt':'502','MID':'SMTSUB002m','GoodsName':'상품1'},{'Amt':'502','MID':'SMTSUB003m','GoodsName':'상품2'}]}"; // 서브몰 정보

	/*
	DivideInfo (서브몰 정보) 설정 예시 -  JSON Object 형식으로 설정
	{
		'DivideInfo':
		[
			{'Amt':'502','MID':'SMTSUB002m','GoodsName':'상품1'}, // 서브몰 1
			{'Amt':'502','MID':'SMTSUB003m','GoodsName':'상품2'} 	// 서브몰 2
		]
	}
	*/	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<title>스마트로::인터넷결제</title>

<script language="javascript">
<!--
function goCancelCard() {
	var formNm = document.tranMgr;
	
	// TID validation
	if(formNm.TID.value == "") {
		alert("TID를 확인하세요.");
		return false;
	} else if(formNm.TID.value.length > 30 || formNm.TID.value.length < 30) {
		alert("TID 길이를 확인하세요.");
		return false;
	}
	// 취소금액
	if(formNm.CancelAmt.value == "") {
		alert("금액을 입력하세요.");
		return false;
	} else if(formNm.CancelAmt.value.length > 12 ) {
		alert("금액 입력 길이 초과.");
		return false;
	}
	var PartialValue = "";
	// 부분취소여부 체크 - 신용카드, 계좌이체 부분취소 가능
	for(var idx = 0 ; idx < formNm.PartialCancelCode.length ; idx++){
		if(formNm.PartialCancelCode[idx].checked){
			PartialValue = formNm.PartialCancelCode[idx].value;
			break;
		}
	}
	
	if(PartialValue == '1'){
		if(formNm.TID.value.substring(10,12) != '01' &&  formNm.TID.value.substring(10,12) != '02' &&  formNm.TID.value.substring(10,12) != '03'){
			alert("신용카드결제, 계좌이체, 가상계좌만 부분취소/부분환불이 가능합니다");
			return false;
		}
	}
	
	formNm.submit();
	return true;
}

-->
<%
if (TID != "" && TID != null && CancelAmt != "" && CancelAmt != null) {
%>
	setTimeout("goCancelCard();",500);
<%
}
%>
</script>
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
	text-align: center;
}

table.type tbody th {
	width: 150px;
	padding: 8px;
	font-weight: bold;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
	background: #f3f6f7;
	text-align: center;
}

table.type td {
	width: 250px;
	padding: 8px;
	vertical-align: top;
	border-bottom: 1px solid #ccc;
	text-align: center;
}

input {
	width: 100%;
	padding: 6px 10px;
	margin: 2px 0;
	box-sizing: border-box;
}

.btnblue {
	background-color: #6DA2D9;
	box-shadow: 0 0 0 1px #6698cb inset, 0 0 0 2px rgba(255, 255, 255, 0.15)
		inset, 0 4px 0 0 rgba(110, 164, 219, .7), 0 4px 0 1px
		rgba(0, 0, 0, .4), 0 4px 4px 1px rgba(0, 0, 0, 0.5);
}
-->
</style>
</head>

<body ondragstart='' onselectstart='' style="overflow: scroll">
	<form name="tranMgr" method="post" action="<%=actionUrl%>">
		<table class="type">

			<thead>
				<tr>
					<th colspan="3">[결제 취소 요청]</th>
				</tr>
				<tr>
					<th scope="cols">항목명</th>
					<th scope="cols">파라미터</th>
					<th scope="cols">입력</th>
				</tr>
			</thead>

			<tbody>

				<tr>
					<th scope="row">거래ID</th>
					<td>TID</td>
					<td><input name="TID" type="text" class="input" id="TID" value="<%=TID%>" size="30" maxlength="30" /></td>
				</tr>

				<tr>
					<th scope="row">취소 패스워드</th>
					<td>Cancelpw</td>
					<td><strong><input name="Cancelpw" type="password" class="input" id="Cancelpw" value="123456"/>* 데모시 "123456" 입력</strong></td>
				</tr>

				<tr>
					<th scope="row">취소금액</th>
					<td>CancelAmt</td>
					<td><input name="CancelAmt" type="text" class="input" id="CancelAmt" value="<%=CancelAmt == null ? "" : CancelAmt%>" /></td>
				</tr>

				<tr>
					<th scope="row">취소사유</th>
					<td>CancelMSG</td>
					<td><input name="CancelMSG" type="text" class="input" id="CancelMSG" value="<%=CancelMSG%>" size="30" maxlength="30" />
					</td>
				</tr>

				<tr>
					<th scope="row">부분취소 여부</th>
					<td>PartialCancelCode</td>
					<td height="30" valign="middle">
						<strong> <input type="radio" name="PartialCancelCode" id="PartialCancelCode" value="0" checked="checked" /> 전체취소 </strong>
						<strong> <input type="radio" name="PartialCancelCode" id="PartialCancelCode" value="1" /> 부분취소 </strong>
					</td>
				</tr>
				
				<tr>
					<th scope="row">상점모드</th>
					<td>MerchantMode</td>
					<td><input name="MerchantMode" type="text" class="input" id="MerchantMode" value="<%=MerchantMode%>" size="1" maxlength="30" />
					</td>
				</tr>

				<tr>
					<th scope="row">서브몰정보</th>
					<td>DivideInfo</td>
					<td><input name="DivideInfo" type="text" class="input" id="DivideInfo" value="<%=DivideInfo%>" maxlength="30" />
					</td>
				</tr>

				<tr>
					<th>&nbsp;</th>
					<td class="btnblue" onclick="return goCancelCard();">확인</td>
					<td class="btnblue" onclick="javascript:document.tranMgr.reset();">입력초기화</td>
				</tr>
			</tbody>
		</table>

		<input type="hidden" name="cc_ip" size="20" value="<%=request.getRemoteAddr()%>" />
		<input type="hidden" name="EncodingType" value="euckr" /> <!--utf8 or euckr-->
		<input type="hidden" name="FORWARD" value="Y" />
	</form>
</body>
</html>
