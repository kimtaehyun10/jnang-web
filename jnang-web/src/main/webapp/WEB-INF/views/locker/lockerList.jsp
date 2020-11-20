<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/locker/locker.list.js"></script>
<div id="subCon">
	<input type="hidden" id="h_sb1" name="h_sb1" value="${comcd}" />
	<input type="hidden" id="h_sb2" name="h_sb2" value="all" />
	<input type="hidden" id="h_sb3" name="h_sb3" value="all" />
	<input type="hidden" id="h_pageIndex" name="h_pageIndex" value="1"/>
	<input type="hidden" id="h_pageSize" name="h_pageSize" value="10"/>
	<input type="hidden" id="h_startRow" name="h_startRow" value="0"/>
</div>
<div class='sub_lecture_l01'>
	<div class='lect_sch_box_01'>
		<div class='bx1 clbx'>
			<ul>
				<li>
					<div class='bd1'>
						<div class='tit1'>체육센터</div>
						<select id="sb1" name='sb1' class='selbox_multi_01' size='4'>
							<option value="all">--- 전체 ---</option>
							<option value="JUNGNANG01">중랑구민체육센터</option>
							<option value="JUNGNANG02">중랑문화체육관</option>
						</select>
					</div>
				</li>
				<li>
					<div class='bd1'>
						<div class='tit1'>종목</div>
						<select id="sb2" name='sb2' class='selbox_multi_01' size='4'>
							<option value="all">--- 전체 ---</option>
						</select>
					</div>
				</li>
				<li>
					<div class='bd1'>
						<div class='tit1'>분류</div>
						<select id="sb3" name='sb3' class='selbox_multi_01' size='4'>
							<option value="all">--- 전체 ---</option>
						</select>
					</div>
				</li>
			</ul>
		</div>
		<!-- <div class='ali_c margin_top_1dot0'>
			<input type='submit' onclick="searchLectureList();" value='검색'>
		</div> -->
	</div>
	<br>
	<div class='autoscroll_x1 margin_top_1dot0'>
		<table id="lectureTable" class='stbl_l1a con_wid' summary='이 표는 강좌명/대상/요일/시간/정원/수강료 정보로 구성되어 있습니다.'></table>
	</div>
 	<div id="paging" class='paging_01a'></div>
	<div id="reception" class='margin_top_0dot5'></div>
	
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
