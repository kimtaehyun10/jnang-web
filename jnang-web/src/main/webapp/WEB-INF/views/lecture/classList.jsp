<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/lecture/class.list.js"></script>
<script type="text/javascript">
$(function(){
	setMenuTitle('bg_01', '시설 통합예약', '수강신청', true);


	$('.sub_navi1 .navi1 .mbx1 > a').on('click',function(){
		if($('.sub_navi1 .bg1').css('display')==='none'){
			var idx_n=$(this).parent().parent().index();
			$('.sub_navi1 .navi1 .dp1').each(function(){
				if(idx_n!=$(this).index()){
					$(this).find('ul').slideUp(100);
				}
			});
			$(this).next().slideToggle(150);
		}else{
			$(this).next().slideToggle(150);
		}
	});
	
	selectSport('${class_seq}');

});

</script>
<div class='sub_lecture_l01'>
	<div class="bx_sch_01a">
	<ul>
		<li>
			<div class='bd1'>
				<div class='tit1'>종목</div>
				<select id="ct1" name="ct1" class="selbox_multi_01" size="4" onchange="selectSport(1);">
					<option value='' selected>--- 전체 ---</option>
					<option value='1'>수영</option>
					<option value='2' selected>헬스</option>
					<option value='3'>체육ㆍ문화</option>
					<option value='3'>체육ㆍ문화</option>
				</select>
			</div>
		</li>
		<li>
			<div class='bd1'>
				<div class='tit1'>체육센터</div>
				<select id="ct2" name="ct2" class="selbox_multi_01" size="4" onchange="selectSport(1);">
					<option value=''>--- 전체 ---</option>
					<option value='' selected>구민체육센터</option>
					<option value=''>중랑문화체육관</option>
					<option value=''>면목2동체육관</option>
				</select>
			</div>
		</li>
	</ul>
	
	</div>
	<br>
	<div class="autoscroll_x1 margin_top_1dot0">
			<table id="lectureTable" class="stbl_l1a con_wid" summary="이 표는 강좌명/대상/요일/시간/정원/수강료 정보로 구성되어 있습니다."><caption>수강신청</caption><colgroup><col width="*"><col width="100px"><col width="140px"><col width="140px"><col width="150px"><col width="100px"><col width="100px"></colgroup><tbody><tr><th>강좌명</th><th>대상</th><th>요일</th><th>시간</th><th colspan="2">수강료(원)</th><th>상세보기</th></tr><tr><td rowspan="2" class="ali_l">조기수영A-월수금 06:00~06:50</td><td rowspan="2">성인</td><td rowspan="2">월,수,금</td><td rowspan="2" class="border_r1">06:00~06:50</td><td>조기수영A-월수금06:00~06:50</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01001', 'I000001');">상세보기</a></td></tr><tr><td>중고생(주3)-월수금</td><td>33,000</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01001', 'I000018');">상세보기</a></td></tr><tr><td rowspan="2" class="ali_l">조기수영B-화목토 06:00~06:50</td><td rowspan="2">성인</td><td rowspan="2">화,목,토</td><td rowspan="2" class="border_r1">06:00~06:50</td><td>조기수영B-화목토06:00~06:50</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01002', 'I000002');">상세보기</a></td></tr><tr><td>중고생(주3)-월수금</td><td>33,000</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01002', 'I000018');">상세보기</a></td></tr><tr><td rowspan="2" class="ali_l">조기수영C-월수금 07:00~07:50</td><td rowspan="2">성인</td><td rowspan="2">월,수,금</td><td rowspan="2" class="border_r1">07:00~07:50</td><td>조기수영C-월수금07:00~07:50</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01003', 'I000003');">상세보기</a></td></tr><tr><td>중고생(주3)-월수금</td><td>33,000</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01003', 'I000018');">상세보기</a></td></tr><tr><td rowspan="2" class="ali_l">조기수영D-화목토 07:00~07:50</td><td rowspan="2">성인</td><td rowspan="2">화,목,토</td><td rowspan="2" class="border_r1">07:00~07:50</td><td>조기수영D-화목토</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01004', 'I000004');">상세보기</a></td></tr><tr><td>중고생(주3)-월수금</td><td>33,000</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01004', 'I000018');">상세보기</a></td></tr><tr><td class="ali_l">조기수영E-월수금 08:00~08:50</td><td>성인</td><td>월,수,금</td><td>08:00~08:50</td><td>조기수영E-월수금</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01005', 'I000005');">상세보기</a></td></tr><tr><td class="ali_l">조기수영F:강습-화목,자유-토 08:00~08:50</td><td>성인</td><td>화,목,토</td><td>08:00~08:50</td><td>조기수영F-화목토</td><td>42,900</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01006', 'I000006');">상세보기</a></td></tr><tr><td class="ali_l">오전수영A-월수금 09:00~09:50</td><td>성인</td><td>월,수,금</td><td>09:00~09:50</td><td>오전수영A-월수금</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01007', 'I000009');">상세보기</a></td></tr><tr><td class="ali_l">오전수영B:강습-화목,자유-토  09:00~09:50</td><td>성인</td><td>화,목,토</td><td>09:00~09:50</td><td>오전수영B:강습-화목,자유-토</td><td>42,900</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01008', 'I000010');">상세보기</a></td></tr><tr><td class="ali_l">오전수영C-월수금 10:00~10:50</td><td>성인</td><td>월,수,금</td><td>10:00~10:50</td><td>오전수영C-월수금</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01009', 'I000011');">상세보기</a></td></tr><tr><td class="ali_l">오전수영D-화목토 10:00~10:50</td><td>성인</td><td>화,목,토</td><td>10:00~10:50</td><td>오전수영D-화목토</td><td>44,800</td><td><a class="size_s2 btn_blue2" onclick="searchLectureDetail('JUNGNANG01', '01010', 'I000012');">상세보기</a></td></tr></tbody></table>
	</div>
	
 	<div id="paging" class='paging_01a'></div>
	<div id="reception" class='margin_top_0dot5'></div>
		
	
		
</div>
