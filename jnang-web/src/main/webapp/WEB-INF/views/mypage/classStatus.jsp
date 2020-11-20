<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/classStatus.js"></script>

<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
String today = transFormat.format(from);
%>

<div id="sub_my_status" class=''>
	<div id="notes" class="border_box_a_gray_1a margin_b50">
		<h4 class="margin_b20">유의사항</h4>
		<ul class="ul_hyphen1a13">
			<li>수강취소는 당일에만 가능하며, 이후에는 환불규정에 따라 처리됩니다.</li>
			<li>반 변경은 방문접수만 가능합니다.</li>
			<li>반 변경은 개강 후 7일 이전까지만 가능합니다.<span class="cap_green" style=""> (7일 이전 1회만 가능)</span></li>
			<li>카드결제 취소는 당일만 가능합니다.</li>
			<!-- <li>환불은 자세한 환불규정을 확인하시기 바랍니다. <a href="#" class="size_s2 btn_line_green margin_l5">환불규정</a></li> -->
		</ul>
	</div>
	<!-- 
	<div class='sch_box_01 noslt'>
		<form name='frm_sch01' method='get' action=''>
			<ul>
				<li><input type='text' name='' maxlength='10'></li>
				<li><input type='submit' value='검색하기'></li>
			</ul>
		</form>
	</div>
	 -->	
	<table class="stbl_l1a">
		<colgroup>
			<col width="5%"><col width="35%"><col width="20%"><col width="20%"><col width="20%">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>강좌명</th>
				<th>수강료</th>
				<th>결제상태</th>
				<th>수강기간</th>
			</tr>
		</thead>
		<tbody>
			<c:set var = "TODAY" value="<%=today%>"/>
			<c:forEach items="${rentList}" var="result" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${result.ITEM_NM}</td>
				<td>${result.COST_AMT}원</td>
				<td>
					<span id="" class="cap_blue">${result.MIDCANCEL_YN_NM}</span>
					
					<!-- 당일취소 -->
					<c:if test="${result.WRITE_YMD eq TODAY}">
						<br><a href="#none" onclick="cancelPay('${result.TID}', '${result.SLIP_NO}', '${result.COST_AMT}');" class="btn_green1">당일취소</a>
					</c:if>
				</td>
				<td>
					<div id="" class="">${result.MONTH_CNT}개월</div>
					<div>${result.ITEM_SDATE_DT} ~ ${result.ITEM_EDATE_DT}</div>
					<!-- <div><a class="size_s2 btn_blue2" onclick="">재등록</a></div>-->
				</td>
			</tr>
			</c:forEach>
			<!-- 					
			<tr>
				<td>1</td>
				<td>즐거운 꼬마요리터A</td>
				<td>90,000원</td>
				<td><span id="" class="cap_red">전액환불</span></td>
				<td>
					<div id="" class="">1개월</div>
					<div>2020.03.01 ~ 2020.04.01</div>
					<div><a class="size_s2 btn_blue2" onclick="">재등록</a></div>
				</td>
			</tr>
			 -->
		</tbody>
	</table>
</div>