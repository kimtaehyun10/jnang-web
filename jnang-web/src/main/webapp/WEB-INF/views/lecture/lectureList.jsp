<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/lecture/lecture.list.js"></script>
<div id="subCon">
	<input type="hidden" id="h_sb1" name="h_sb1" value="${comcd}" />
	<input type="hidden" id="h_sb2" name="h_sb2" value="all" />
	<input type="hidden" id="h_sb3" name="h_sb3" value="all" />
	<input type="hidden" id="h_pageIndex" name="h_pageIndex" value="1"/>
	<input type="hidden" id="h_pageSize" name="h_pageSize" value="10"/>
	<input type="hidden" id="h_startRow" name="h_startRow" value="0"/>
	<div style="font-size:18px; margin-bottom:20px;">수강신청을 진행할 센터와 분류를 선택하세요.</div>
</div>
<div class='sub_lecture_l01'>
	<div class='lect_sch_box_01'>
		<div class='bx1 clbx'>
			<ul>
				<li>
					<div class='bd1'>
						<div class='tit1'>체육시설</div>
						<select id="sb1" name='sb1' class='selbox_multi_01' size='4'></select>
					</div>
				</li>
				<li>
					<div class='bd1'>
						<div class='tit1'>종목</div>
						<select id="sb2" name='sb2' class='selbox_multi_01' size='4'></select>
					</div>
				</li>
				<li>
					<div class='bd1'>
						<div class='tit1'>분류</div>
						<select id="sb3" name='sb3' class='selbox_multi_01' size='4'></select>
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
</div>
