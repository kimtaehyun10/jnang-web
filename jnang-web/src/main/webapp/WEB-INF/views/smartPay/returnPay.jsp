<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.dwict.jfmc.client.mem.model.Member"%>

<%
	String VbankName = request.getParameter("VbankName")==null?"":request.getParameter("VbankName"); // 가상계좌은행명
		
	VbankName = URLDecoder.decode(VbankName, "utf-8");
	
	Member member 	= (Member) session.getAttribute("member");
	String MEM_ID = "";
	String MEM_BIRTH = "";
	String MEM_HP = "";
	String MEM_MAIL = "";
	String MEM_NM = "";
	if(member != null){
		MEM_ID	= member.getId();
		MEM_NM = member.getMemNm();
		MEM_BIRTH = member.getSecBirthDate();
		MEM_HP = member.getHp();
		MEM_MAIL = member.getEmail();
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

<c:set var="goUrl" value="${rtnMap.goURL}" />
<%
String goUrl = (String)pageContext.getAttribute("goUrl") ;
%>
<script type="text/javascript">
var goURL = "${rtnMap.goURL}";
<%
////정원초과 취소처리
if ( "close1".equals(goUrl) || "close2".equals(goUrl) ) {
%>	
	if(goURL == 'close1'){
		alert("정원이 마감 되어 취소처리 됩니다.")
	}else{
		alert("결제오류로 인하여 취소처리 됩니다.\n다시 결제를 진행해주세요.")
	}
	//window.opener.cancelPay('${rtnMap.TID}','','${rtnMap.Amt}','${rtnMap.COMCD}',0);
	opener.location.href='/smartPay/mainCancelPay?p1=${rtnMap.TID}&p2=${rtnMap.Amt}&p3=spno&p4=com&p5=payCancel';
	self.close();
<%
} else {
	%>
	function gtnUrl(val1) {
		
		//opener.location.href='/mypage/classStatus';		
		if (goURL != "") {
			try {
			opener.location.href='${rtnMap.goURL}';
			} catch (ex) {
	
			}
		}
		self.close();
	}
		
	if (goURL != "") {
		try {			
			opener.location.href='${rtnMap.goURL}';
		} catch (ex) {

		}
	}
	
<%
} //정원초과 취소처리
%>


</script>
	<table class="type">
		<thead>
			<tr>
				<th colspan="3">[결제 결과]</th>
			</tr>
			<tr>
				<th scope="cols">항목명</th>
				<th scope="cols">입력</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th scope="row">지불수단</th>
				<td><strong><c:out value="${rtnMap.PayMethod}"/><%//=PayMethod%></strong></td>
			</tr>				
			<tr>
				<th scope="row">금액
					</td>
					<td><strong><c:out value="${rtnMap.Amt}"/><%//=Amt%>원</strong></td>
			</tr>
			<tr>
				<th scope="row">결제자명
					</td>
					<td><c:out value="<%=MEM_NM %>"/><%//=BuyerName%></strong></td>
			</tr>
			<c:if test="${rtnMap.CHANGE_YN ne 'B'}">
				<tr>
					<th scope="row">상품명
						</td>
						<td><c:out value="${rtnMap.GoodsNameDe}"/><strong><%//=GoodsName%></strong></td>
				</tr>
			</c:if>			
			<tr>
				<th scope="row">카드번호
					</td>
					<td><strong><c:out value="${rtnMap.CardNum}"/><%//=OID%></strong></td>
			</tr>
			<tr>
				<th scope="row">승인일자
					</td>
					<td><strong><c:out value="${rtnMap.AuthDate}"/><%//=AuthDate%></strong></td>
			</tr>
			<tr>
				<th scope="row">승인번호 (AuthCode)
					</td>
					<td><c:out value="${rtnMap.AuthCode}"/><%//=AuthCode%></td>
			</tr>			
			<tr>
				<th scope="row">결과메시지
					</td>
					<td><strong><c:out value="${rtnMap.ResultMsgDe}"/><%//=ResultMsg%></strong></td>
			</tr>
			<tr>
				<th scope="row">결제카드사명
					</td>
					<td><strong><c:out value="${rtnMap.AcquCardName}"/><%//=fn_name%></strong></td>
			</tr>
			<%-- <tr>
				<th scope="row">결제카드사코드
					</td>
					<td><strong><c:out value="${rtnMap.fn_cd}"/><%//=fn_name%></strong></td>
			</tr> --%>
			<tr>
				<th scope="row">할부개월수
					</td>
					<td><strong><c:out value="${rtnMap.CardQuota}"/><%//=CardQuota%></strong></td>
			</tr>
			<tr>
				<th scope="row">구매자전화번호
					</td>
					<td><strong><c:out value="<%=MEM_HP%>"/><%//=BuyerTel%></strong></td>
			</tr>
			<tr>
				<th scope="row">구매자이메일주소
					</td>
					<td><strong><c:out value="<%=MEM_MAIL %>"/><%//=BuyerEmail%></strong></td>
			</tr>
			<tr>
				<td class="btnblue" colspan="3" onclick="gtnUrl();">닫기</td>
			</tr>

		</tbody>

	</table>
</body>
</html>
