<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/lockerStatus.js"></script>

<div id="subCon">		
	<input type="hidden" id="h_memNo" name="h_memNo" value="${member.memNo}" />
</div>

<div id="boardCon">
	<div class='sub_lecture_l01'>		
		<div class='ali_c'>
		<img src='${pageContext.request.contextPath}/resource/images/sub/icon_lock_info_01.jpg' alt=''>
		<span class='fc_green2 fontsize_1dot80 margin_l15'>신규(처음)신청은 방문 접수, 수납가능 합니다.</span>
	</div>
	<br>
	<br>
	<br>
	<br>
	<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>사물함 이용 안내문</div>
		<div class='border_box_a_gray_1a margin_top_1dot0 lineheight_1dot8 fontsize_1dot20'>
			<div class='number_a_1a'>
				<dl>
					<dt><span>1</span></dt>
					<dd>
						사물함 내 보관물의 도난, 분실, 파손 등으로 인한 손실에 대한 책임은 전적으로 사용자 본인에게 있습니다.<br>
						(보관물의 <span class='fc_red1'>습기를 제거하고 보관</span>하시어 사물함 내 습기로 인해 <span class='fc_red1'>곰팡이가 생기지 않도록 주의</span> 바랍니다.)<br>
					</dd>
				</dl>
				<dl>
					<dt><span>2</span></dt>
					<dd>
						<span class='fc_red1'>사용기간이 만료</span>(또는 강좌의 환불)되면 반드시 <span class='fc_red1'>열쇠를 반납</span>하셔야 <span class='fc_red1'>보증금을 환불</span>받을 수 있습니다.<br>
					</dd>
				</dl>
				<dl>
					<dt><span>3</span></dt>
					<dd>
						사용기간 <span class='fc_red1'>만료 한 달 이상 열쇠를 반납하지 않을 경우 보증금 10,000원중 3,000원은 연체료, 7,000원은 키교체 비용으로 처리</span>되며 <span class='fc_red1'>보관물은 별도의 장소</span>에 <span class='fc_red1'>보관</span>됩니다.<br>
					</dd>
				</dl>
				<dl>
					<dt><span>4</span></dt>
					<dd>
						<span class='fc_red1'>사물함 열쇠를 분실</span>하였을 경우에는 <span class='fc_red1'>즉시 신고</span>하여야 하며 <span class='fc_red1'>열쇠를 재발급</span> 받으려면 <span class='fc_red1'>키 분실 요금 10,000원</span>을 <span class='fc_red1'>납부</span>하셔야 합니다.<br>
					</dd>
				</dl>
			</div>
		</div>		
	</div>
</div>