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
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery-ui.css?v=1" />

<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/rent/rent.write.js"></script> --%>
<%-- <%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
String today = transFormat.format(from);
String param 	= request.getParameter("q");
String [] arrParam = param.split("\\/"); 
String PLACE_CD = arrParam[0];
String YMD 		= request.getParameter("ymd");
YMD = (YMD == null || YMD == "") ? today : YMD;
%> --%>
<script type="text/javascript">
$(function(){
	setMenuTitle('bg_99', '마이페이지', '대관신청 상세페이지', true);
	
});


</script>
<div class="con_bx1">
	
<div class="sub_cleanreport">
	
 <form name="frm1" id="frm1" method="post" onsubmit="return send();" enctype="multipart/form-data"> 

	<div class="border_top_3_green"></div>
	
<table class="stbl_w3b border_top_0" summary="이 표는 제목/내용 등의 정보로 구성된 팀등록/수정 폼입니다.">
	<caption>
	대관문의
	</caption>
	<colgroup>
		<col width="200px">
		<col width="*">
	</colgroup>
	<tbody>
	<tr>
		<th>예약자 성명</th>
		<td>
			<input type="text" id="MEM_NM" name="MEM_NM" value="<c:out value='${rentList[0].WRITER}'/>" maxlength="20" class="inputbox_01a" readonly="readonly">
		</td>
	</tr>
	<tr>
		<th>연락처</th>
		<td>
			<input type="text" id="TEL" name="TEL" value="<c:out value='${rentList[0].TEL}'/>" maxlength="20" class="inputbox_01a" readonly="readonly">
		</td>
	</tr>
	<tr>
		<th>대관 예약 이름</th>
		<td>
			<input type="text" id="COMNM" name="COMNM" value="<c:out value='${rentList[0].COMNM}'/>" maxlength="20" class="inputbox_01a" readonly="readonly" >
		</td>
	</tr>
	<tr>
		<th>예약한 대관 상태</th>
		<td>
			<input type="text" id="APP_TYPE_NM" name="APP_TYPE_NM" value="<c:out value='${rentList[0].APP_TYPE_NM}'/>" maxlength="20" class="inputbox_01a" readonly="readonly">
		</td>
	</tr>
	<c:if test="${rentList[0].PLACE_GROUP eq '1' }">
		<tr>
			<th>예약한 시간</th>
			<td>
				시작시간 : <span style="color:red;">${rentList[0].START_DATE}</span><br>
				종료시간 : <span style="color:red;">${rentList[0].END_DATE}</span>
			</td>
		</tr>
	</c:if>
	<c:if test="${rentList[0].PLACE_GROUP ne '1' }">
		<tr>
			<th>예약한 시간</th>
			<td>
				
				<c:forEach items="${rentList}" var="result" varStatus="status">
					날짜 : <span style="color:red;">${result.RESERVE_DATE}</span> 시간 : <span style="color:red;">${result.item }</span><br>
				</c:forEach>
			</td>
		</tr>
	</c:if>
	<tr>
		<th>결제예정 또는 결제 완료 금액</th>
		<td>
			<input type="text" name="PAY_AMT" id="PAY_AMT" value="<c:out value='${rentList[0].PAY_AMT}'/>" readonly="readonly" class="inputbox_01a"/>
		</td>
	</tr>
	<c:if test="${not empty rentList[0].PAY_DATE}">
		<tr>
			<th>결제 완료 날짜</th>
			<td>
				<input type="text" id="PAY_DATE" name="PAY_DATE" value="<c:out value='${rentList[0].PAY_DATE}'/>" maxlength="20" class="inputbox_01a" readonly="readonly">
			</td>
		</tr>
	</c:if>
	<tr>
		<th>예약 시간</th>
		<td>
			<input type="text" name="WRITE_DH" id="WRITE_DH" value="<c:out value='${rentList[0].FORMAT_WRITE_DH}'/>" class="inputbox_01a" readonly="readonly"/>
		</td>
	</tr>
	<c:if test="${rentList[0].PLACE_GROUP eq '1' }">
		<tr>
			<th>행사명</th>
			<td>
				<input type="text" id="OBJECT" name="OBJECT" value="<c:out value='${rentList[0].OBJECT}'/>" class="inputbox_01a" readonly="readonly">
			</td>
		</tr>
	</c:if>
	<c:if test="${rentList[0].PLACE_GROUP eq '1' }">	
	<tr>
	
		<th>문의내용</th>
		<td>
			<textarea name="CONCEPT" id="CONCEPT" maxlength="1000" style="height:200px;width:80%;" class="inputbox_01a" readonly>
<c:out value='${rentList[0].CONCEPT}'/>
			</textarea>
		</td>
	</tr>
	</c:if>
    </tbody>
</table>
※ 작성한 내용과 다른 부분이 있다면 관리에게 연락바랍니다.
<br>
<br>
		<div class="bx_btns_01a ali_c">
			<a href="/mypage/rent" id=" " class="size_m2 btn_green1" class="gray2">목록으로</a>
		</div>

</form>


		<!-- } 서브본문영역1내부 -->
		</div>
	</div>