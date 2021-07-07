<%@ page import="com.dwict.jfmc.client.mem.model.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//테스트용 아이디
	Member member 	= (Member) session.getAttribute("member");
	String MEM_ID = "";
	String MEM_BIRTH = "";
	if(member != null){
		MEM_ID	= member.getId();
		MEM_BIRTH = member.getSecBirthDate();
	}
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/lecture/lecture.list.js"></script>

<div id="subCon">
	<input type="hidden" id="h_sb1" name="h_sb1" value="${comcd}" />
	<input type="hidden" id="h_sb2" name="h_sb2" value="all" />
	<input type="hidden" id="h_sb3" name="h_sb3" value="all" />
	<input type="hidden" id="h_pageIndex" name="h_pageIndex" value="1"/>
	<input type="hidden" id="h_pageSize" name="h_pageSize" value="10"/>
	<input type="hidden" id="h_startRow" name="h_startRow" value="0"/>
	<input type="hidden" id="memId" value="<%=MEM_ID%>">
	<input type="hidden" id="memBirth" value="<%=MEM_BIRTH%>">
	<div style="font-size:18px; margin-bottom:20px;">수강신청을 진행할 센터와 분류를 선택하세요.</div>
</div>

<div class="sub_board_l01">
	<div class="sch_box_01">
	<ul>
	<li>
	<select id="searchKey" name="searchKey" class="selbox_01">
	<option value="all" selected>강좌명</option>	
	</select>
	</li>
	<li><input type="text" id="searchValue" name="searchValue" maxlength="10" onkeyup="enterkey()"></li>
	<li><input type="submit" value="검색하기" onclick="searchGo();"></li>
	</ul>
	</div>	
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
<script>
	if($('#h_sb1').val() == 'JUNGNANG01' ){
		//alert('코로나19에 따른 시설물 제한적 운영으로 \n정규강좌는 수강신청이 불가 합니다.\n정규강좌 운영시 공지사항에 게재합니다. \n자세한 문의는 아래로 연락 바랍니다. \n연락처 : 02-3423-1070');
	}else if($('#h_sb1').val() == 'JUNGNANG02'){
		alert('코로나19에 따른 시설물 제한적 운영으로 \n정규강좌는 수강신청이 불가 합니다.\n정규강좌 운영시 공지사항에 게재합니다. \n자세한 문의는 아래로 연락 바랍니다. \n연락처 : 02-436-9200');
	}else if($('#h_sb1').val() == 'JUNGNANG03'){
		alert('코로나19에 따른 시설물 제한적 운영으로 \n정규강좌는 수강신청이 불가 합니다.\n정규강좌 운영시 공지사항에 게재합니다. \n자세한 문의는 아래로 연락 바랍니다. \n연락처 : 02-495-5200');
	}
</script>
<script>
	function searchGo(){
		$('#sb1').val('all');
		$('#sb2').val('all');
		$('#sb3').val('all');
		$('#h_pageIndex').val('1');
		searchLectureList();
	}
	function enterkey() { 
		if (window.event.keyCode == 13) { 
			$('#sb1').val('all');
			$('#sb2').val('all');
			$('#sb3').val('all');
			$('#h_pageIndex').val('1');
			searchLectureList();	
		} 
	}
</script>