<%@page import="javax.persistence.Convert"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
String today = transFormat.format(from);
String sSEQ = request.getParameter("seq");
sSEQ = (sSEQ == null || sSEQ == "") ? "0" : sSEQ;
%>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/jquery-ui.css?v=1" />
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/rent/rent.team.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/com/daumPostcode.min.js"></script>

<c:set var="sp_type" value="${teamData.sp_type}" />
<c:set var="tm_type" value="${teamData.tm_type}" />
<%
int sp_type = 0;
if (pageContext.getAttribute("sp_type") != null) {
	sp_type = (int) pageContext.getAttribute("sp_type");
	//out.println("sp_type:"+ sp_type +"<BR>");
}

String tm_type = "";
if (pageContext.getAttribute("tm_type") != null) {
	tm_type = (String) pageContext.getAttribute("tm_type");
	//out.println("tm_type:"+ tm_type +"<BR>");
}

%>
<script type="text/javascript">

function teamSelect(val1) {
	var frm = document.frm2;
	frm.seq.value = val1;
	frm.action="/rent/team";
	frm.submit();
}


</script>
<div class="sub_cont1">
<div class="con_bx">
	

<div class="sub_cleanreport">

	<!-- 
	<div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">센터별 대관안내 사항</div>
	<div class="padding_left_1dot0 fontsize_1dot15 margin_top_1dot0 lineheight_1dot8">
		<div class="catebox intro02">
			<ul>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0557&amp;pnum=7&amp;cnum=13" class="on">중랑구민체육센터</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0558&amp;pnum=7&amp;cnum=13">중랑문화체육관</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0559&amp;pnum=7&amp;cnum=13">중랑구립잔디운동장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0560&amp;pnum=7&amp;cnum=13">용마폭포공원축구장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0583&amp;pnum=7&amp;cnum=13">신내차량기지축구장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0619&amp;pnum=7&amp;cnum=13">중랑구립테니스장</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0561&amp;pnum=7&amp;cnum=13">중랑구립정보도서관</a></li>
				<li><a href="/content.do?sgrp=S01&amp;siteCmsCd=CM0001&amp;topCmsCd=CM0003&amp;cmsCd=CM0562&amp;pnum=7&amp;cnum=13">중랑구민회관</a></li>
			</ul>
		</div>
	</div>
	<br>
	<br>
	-->


<form name="frm2" id="frm2" method="get">
	<input type="hidden" name="seq" value="">
</form>
<form name="frm1" id="frm1" method="post" onsubmit="return send();"> 
<div class="border_top_3_green"></div>

<table class="stbl_w3b border_top_0" summary="이 표는 제목/내용 등의 정보로 구성된 팀등록/수정 폼입니다.">
	<caption>
	팀 등록신청
	</caption>
	<colgroup>
		<col width="200px">
		<col width="*">
	</colgroup>
	<tbody>
	<tr>
    	<td colspan="4"><div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">팀 정보</div></td>
    </tr>
    <tr>
    	<th>팀리스트</th>
    <td colspan="2">
	    <select id="teamSEQ" name="teamSEQ" class="inputbox_01a" onchange="teamSelect(this.value);">
		    <option value=''>신규팀생성</option>
			<c:forEach items="${teamList}" var="result" varStatus="status">
				<c:set var="intSeq" value="${result.seq}" />
				<c:set var="tm_nm" value="${result.tm_nm}" />
				<c:set var="sp_typeNm" value="${result.sp_typeNm}" />
				<%
			 	int intSeq = (int)pageContext.getAttribute("intSeq") ;
				String  tm_nm = (String) pageContext.getAttribute("tm_nm") ;
				String  sp_typeNm = (String) pageContext.getAttribute("sp_typeNm") ;
				//out.print("RESERVE_DATE==>:"+ RESERVE_DATE +"<BR>");
				if (intSeq == Integer.parseInt(sSEQ)) {
					out.println("<option value='"+ intSeq +"' SELECTED >["+ sp_typeNm +"] "+ tm_nm +"</option>");
				} else {
					out.println("<option value='"+ intSeq +"' >["+ sp_typeNm +"] "+ tm_nm +"</option>");
				}
			 	%>				
			</c:forEach>
		</select>
	</td>
	</tr>
	<tr>
		<th>종목선택</th>
		<td>
			<select id="sp_type" name="sp_type" class="inputbox_01a" required>
			  <option value='' /> == 종목 == </option>
			  <!-- <option value='1'/> 체육관 </option> -->
			  <option value='2' <% if (sp_type == 2 ) { out.print(" selected "); } %> />축구장</option>
			  <option value='3' <% if (sp_type == 3 ) { out.print(" selected "); } %> />야구장 </option>
			  <option value='4' <% if (sp_type == 4) { out.print(" selected "); } %>/> 테니스장 </option>
		    </select>
		 </td>
		<th>신청구분</th>
		<td><label><input type="radio" id="tm_type" name="tm_type" value="1" required
		<% if (tm_type.equals("1") ) { out.print(" checked "); }%>
		> 관내팀 </label> &nbsp; &nbsp; <label><input type="radio" id="tm_type" name="tm_type" value="2"
		<% if (tm_type.equals("2") ) { out.print(" checked "); }%>
		>관외팀</label></td>
	</tr>
	<tr>
		<th>단체명</th>
		<td><input type="text" id="tm_nm" name="tm_nm" value="<c:out value="${teamData.tm_nm}"/>" maxlength="" required class="inputbox_01a inputbox_01_s3"></td>
		<th>회원수</th>
		<td><div id="temMemCnt"> <c:out value="${fn:length(teamMemList)}"/>명 </div></td>
	</tr>
	<tr>
    	<td colspan="4">
    		<div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">대표자 정보</div> 
    		<a href='/mypage/modify' class="size_m2 btn_green1">회원정보 변경 바로가기</a>
    		</td>
    </tr>	
	  <th>대표자명</th>
	  <td><c:out value='${myData.MEM_NM}'/></td>
	  <th>아이디</th>
	  <td><c:out value='${myData.ID}'/></td>
	  </tr>
	<tr>
	  <th>연락처</th>
	  <td><c:out value='${myData.HOME_TEL}'/></td>
	  <th>휴대폰번호</th>
	  <td><c:out value='${myData.HP}'/></td>
	  </tr>
	<tr>
    	<td colspan="4">
    		<div class="bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5">회원 명단</div>
    		<a href='#none' onclick="mem_add();" class="size_m2 btn_green1">+ 추가</a>
    	</td>
    </tr>	
	<tr>
	  <td colspan="4" align="left">
	  	<input type="hidden" id="zip">
	  	<div id="mem_list">
			<c:forEach items="${teamMemList}" var="result" varStatus="status">
				<div id='m_list${status.count}' style='margin:5px;'>
				${status.count}. 성명 : <span style='margin:0px 5px 0 5px;display:inline-block; width:100px; border: none;'><input type='text' id='uname' name='uname' value='${result.mem_nm}' style='width:100%;'></span>
				생년월일 : <span style='margin:0px 5px 0 5px;display:inline-block; width:100px; border: none;' class='ali_c'><input type='text' class='sdate' value='${result.mem_birth}' name='ubrth' style='width:100%;'></span>
				주소 : <span style='margin:0px 5px 0 5px;display:inline-block; width:400px; border: none;' class='ali_c'><input type='text' id='addr${status.count}' value='${result.mem_addr}' name='uaddr' style='width:100%;' onclick='execDaumPostcode(document.getElementById("zip"), document.getElementById("addr${status.count}"));'></span>
				상세주소 : <span style='margin:0px 5px 0 5px;display:inline-block; width:200px; border: none;' class='ali_c'><input type='text' name='uaddr2' value='${result.mem_addr2}' style='width:100%;'></span>
				<a onclick='del("${status.count}",${result.seq});'> - ${status.count}. 삭제</a></div>
			</c:forEach>
	  	
	  	</div>
	  </td>
	</tr>
    </tbody>
</table>
<br>
<br>
	<div class="bx_btns_01a ali_c">
		<input type="hidden" id="mem_id" name="mem_id" value="<c:out value='${myData.ID}'/>">
		<input type="hidden" id="arryData" name="arryData">
		<input type="hidden" id="arryDel" name="arryDel">
		<input type="submit" class="size_m2 btn_green1" value="<%=(sSEQ =="0") ? "팀 신청" : "팀 수정" %>">
		<input type="button" class="size_m2 btn_green1" value="대관신청" onClick="top.location.href='/rentlist';">
	</div>

</form>


		<!-- } 서브본문영역1내부 -->
		</div>
	</div>
