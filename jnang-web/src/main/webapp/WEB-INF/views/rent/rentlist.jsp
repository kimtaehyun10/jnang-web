<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
String today = transFormat.format(from);

String comcd = request.getParameter("cm");
comcd = (comcd == null) ? "":comcd;

//out.println("comcd:"+ comcd);

String ss = "";
if (comcd.equals("JUNGNANG01") ) {
	ss = "";
	
} else if (comcd.equals("JUNGNANG02") ) {
	ss = "";
	
} else if (comcd.equals("JUNGNANG03") ) {
	ss = "";
	
} else { 
	ss = "";
}

%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/rent/rent.list.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery-ui.min.js"></script>
<script type="text/javaScript">
var saveRtn = "${saveRtn}";
	if (saveRtn == "-9") {
		alert("사용자 동의후 접수 가능합니다.");
	}
	if (saveRtn == "0") {
		alert("대관 접수 오류 다시 접수 하여 주세요.");
	}	
	//로그인 첵크
	if (saveRtn == "-999") {
		top.location.href ='/mem/login';
	}		
</script>

<div class="bx_sch_01a">
		<!-- <div class="bx1 clbx"> -->
			<ul>
				<li>
					<div class="bd1">
						<div class="tit1">구분</div>
						<select id="ct1" name="ct1" class="selbox_multi_01" size="4" onchange="selectSport(1);">
						<option value="all" selected>--- 전체 ---</option>
						<option value="1">체육관</option>
						<option value="2">축구장</option>
						<option value="3">야구장</option>
						<option value="4">테니스장</option>
						</select>
					</div>
				</li>
				<li>
					<div class="bd1">
						<div class="tit1">센터</div>
						<select id="ct2" name="ct2" class="selbox_multi_01" size="4" onchange="selectSport(2);">
						<option value="all" selected>--- 전체 ---</option>
						</select>
					</div>
				</li>
			</ul>
		</div>
		<!-- <div class='ali_c margin_top_1dot0'>
			<input type='submit' onclick="searchLectureList();" value='검색'>
		</div> -->
	<!-- </div> -->
<br>
<div id="sub_cart" class=' '>
	<form name="frm" method="post">
		<table class="stbl_l1a">
			<colgroup>
				<col width="*">
				<col width="20%">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<thead>
				<tr>
					<th>센터</th>
					<th>시설명</th>
					<th>비용</th>
					<th>시설안내</th>
					<th>현황</th>
					<th>신청</th>					
				</tr>
			</thead>
			<tbody id="dataList">
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
