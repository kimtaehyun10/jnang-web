<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/lockerStatus.js"></script>

<div id="sub_my_status" class=' '>
	<div id="notes" class="border_box_a_gray_1a margin_b50">
		<h4 class="margin_b20">유의사항</h4>
		<ul class="ul_hyphen1a13">
			<li>유료 사물함 임대 현황 입니다.(사물함은 시설이용고객만 임대하실 수 있습니다.)</li>
			<li>임대보증금은 방문 납입하셔야 합니다.</li>
			<li>온라인 결제취소는 평일 업무시간(09시~18시)내에만 가능합니다.</li>
		</ul>
	</div>
	<table class="stbl_l1a">
		<colgroup>
			<col width="15%">
			<col width="15%">
			<col width="15%">
			<col width="15%">
			<col width="15%">
			<col width="15%">
		</colgroup>
		<thead>
			<tr>
				<th>락카위치</th>
				<th>락카번호</th>
				<th>이용기간</th>
				<th>이용개월</th>
				<th>금액</th>
				<th>상태</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="6">임대하신 락카가 없습니다.</td>
			</tr>
		</tbody>
	</table>
</div>