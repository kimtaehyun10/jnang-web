<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/park/dwict.board.pubParkApply.js"></script>


<script type="text/javascript">
$(function(){		
	/**
	 * @Summary 서브
	 * @Discription 서브 > 본문html > 탭메뉴2__onoff
	 */
	$('.tab_ty2_selbox_1a > div > a').on('click', function(){
		$(this).next().slideToggle(150);
	});
	/**
	 * @Summary 서브
	 * @Discription 서브 > 본문html > 탭클릭에따른콘텐츠onoff
	 */
	$('.jsc_tab1a_con').css('display','none');
	$('.jsc_tab1a_con').eq(0).css('display','');
	$('.jsc_tab1a_btn a').on('click',function(){
		if(!$(this).parent().hasClass('on')){
			var idx=$(this).parent().index();
			$('.jsc_tab1a_btn li').removeClass('on');
			$('.jsc_tab1a_con').css('display','none');
			$('.jsc_tab1a_btn li').eq(idx).addClass('on');
			$('.jsc_tab1a_con').eq(idx).css('display','');
		}
	});
});
</script>

<!-- <div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>오시는 길</div>
<div class='sub_sisul_photo01 clbx margin_top_1dot0'>
	<ul>
		<li>
			<div class='bx_sub_sisul_photo01 clbx'>
				<div id="map" style="width:730px;height:400px;"></div>
				<script>
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
					mapOption = { 
						center: new daum.maps.LatLng(37.5879442, 127.0809088), // 지도의 중심좌표
						level: 3 // 지도의 확대 레벨
					};

				var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

				var iwContent = '<div style="padding:5px;"><strong>면목2동체육관</strong><br />서울시 중랑구 겸재로23길 27 공영주차장 4층(면목동)</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
					iwPosition = new daum.maps.LatLng(37.5879442, 127.0809088), //인포윈도우 표시 위치입니다
					iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

				// 인포윈도우를 생성하고 지도에 표시합니다
				var infowindow = new daum.maps.InfoWindow({
					map: map, // 인포윈도우가 표시될 지도
					position : iwPosition, 
					content : iwContent,
					removable : iwRemoveable
				});
					  
				// 아래 코드는 인포윈도우를 지도에서 제거합니다
				// infowindow.close();        
				</script>
			</div>
		</li>
		<li>
			<div class='cname1'>면목2동체육관</div>
			<div class='cment1'>
				면목2동체육관은 헬스 및 생활체육의 보급과 활성화를 통하여 주민 모두 건강하고 여유로운 삶을 영위케 하여 지역사회 발전에 이바지고자 합니다.

			</div>
			<div class='hline1'></div>			
		</li>
	</ul>
</div>

<br>
<br>
<br>

<div class='autoscroll_x1 margin_top_1dot0'>
	<table class='stbl_l1a con_tbl1' summary='이 표는 구분/내용 정보로 구성되어 있습니다.'>
	<caption>위치, 전화, 팩스</caption>
	<colgroup>
		<col width="15%">			
	</colgroup>
	<tr>
		<th>위치</th>
		<td class='ali_l'>서울시 중랑구 겸재로23길 27 공영주차장 4층(면목동)</td>
	</tr>
	<tr>
		<th>전화</th>
		<td class='ali_l'>02-495-5200</td>
	</tr>
	<tr>
		<th>팩스</th>
		<td class='ali_l'>02-495-5206</td>
	</tr>
	</table>
</div>
<br>
<br>
<br>
<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>교통편</div>
<div class='autoscroll_x1 margin_top_1dot0'>
	<table class='stbl_l1a con_tbl1' summary='이 표는 구분/내용 정보로 구성되어 있습니다.'>
	<caption>교통편</caption>
	<colgroup>
		<col width="15%">			
	</colgroup>
	<tr>
		<th>지하철</th>
		<td class='ali_l'>7호선(면목역) 3번출구, 동2로 방향 800m</td>
	</tr>
	<tr>
		<th>시내버스</th>
		<td class='ali_l'>간선 : 146 지선 : 2227 마을버스 : 02</td>
	</tr>	
	</table>
</div> -->


<div class='sub_sisul_photo01 clbx'>
	<ul>
		<li>
			<div class='bx_sub_sisul_photo01 clbx'>
				<ul class='sub_sisul_photo01__rolling1'>
					<!-- <li><img src='/resource/images/sub/parking/parking_01_02_01.jpg' alt='면목2동'></li>
					<li><img src='/resource/images/sub/parking/parking_01_02_02.jpg' alt='용마'></li>
					<li><img src='/resource/images/sub/parking/parking_01_02_03.jpg' alt='중화1동'></li> -->
					<li><img src='/resource/images/sub/parking/parking_01_02_04.jpg' alt='까치공원'></li>
					<li><img src='/resource/images/sub/parking/parking_01_02_05.jpg' alt='까치공원2'></li>
					<li><img src='/resource/images/sub/parking/parking_01_02_06.jpg' alt='까치공원3'></li>
					<li><img src='/resource/images/sub/parking/parking_01_02_07.jpg' alt='면목5동'></li>
					<li><img src='/resource/images/sub/parking/parking_01_02_08.jpg' alt='용마폭포'></li>
					<li><img src='/resource/images/sub/parking/parking_01_02_09.jpg' alt='중곡초교'></li>
				</ul>
			</div>
		</li>
		<li>
			<div class='cname1'>공영주차장</div>
			<div class='cment1'>
				신청안내<br>
				1. 방문 : 중랑구 겸재로 23길 27(2층사무실)<br>
				2. 팩스 : 신청서작성 후 등록증과 함께 발송
			</div>
			<div class='hline1'></div>
			<div class='info1'>
				<div class='cment1'>
				※ 기본서류<br>
				- 이용신청서, 자동차등록증, 감면(항목별) 증빙서류
				</div>
			</div>
		</li>
	</ul>
</div>

<br>
<br>
<br>

<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>신청접수안내</div>
<div class='margin_top_1dot0 lineheight_1dot8'>
	공영주차장 신청접수는 방문신청 및 담당자와 상담을 통해 가능하오니, 궁금하시거나 문의하실 사항이 있으실 경우 주차사업팀으로 연락주시면 성심성의껏 답변 드리겠습니다.<br>

	<strong class='fc_red1'>중랑구시설관리공단 주차사업팀 연락처 : 공영주차장 담당 02)1577-3325 → 3번</strong><br>

	※ 온라인을 통한 인터넷 접수는 프로그램 준비 중에 있습니다.<br>
</div>

<br>
<br>
<br>

<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>공영주차장 요금표</div>
<div class='autoscroll_x1 margin_top_1dot0'>
	<table class='stbl_l1a con_tbl1' summary='이 표는 (노상주차장, 노외주차장 - 1회요금, 1일요금, 월정기요금) 정보로 구성되어 있습니다.'>
	<caption>공영주차장 요금표</caption>
	<tr>
		<th rowspan='3' class='border_r1'>구분</th>
		<th colspan='4'>노상주차장</th>
		<th colspan='3'>노외주차장</th>
	</tr>
	<tr>
		<th rowspan='2'>1회 주차시(5분당)</th>
		<th rowspan='2' class='border_r1'>1일 주차권(야간에한함)</th>
		<th colspan='2'>월정기권</th>
		<th rowspan='2'>1회 주차시(5분당)</th>
		<th colspan='2'>월정기권</th>
	</tr>
	<tr>
		<th>주간</th>
		<th>야간</th>
		<th>주간</th>
		<th>야간</th>
	</tr>
	<tr>
		<th>1급지</th>
		<td>500</td>
		<td>5,000</td>
		<td>-</td>
		<td>-</td>
		<td>400</td>
		<td>250,000</td>
		<td>100,000</td>
	</tr>
	<tr>
		<th>2급지</th>
		<td>250</td>
		<td>4,000</td>
		<td>-</td>
		<td>-</td>
		<td>250</td>
		<td>180,000</td>
		<td>60,000</td>
	</tr>
	<tr>
		<th>3급지</th>
		<td>150</td>
		<td>3,000</td>
		<td>-</td>
		<td>-</td>
		<td>150</td>
		<td>100,000</td>
		<td>40,000</td>
	</tr>
	<tr>
		<th>4급지</th>
		<td>100</td>
		<td>2,000</td>
		<td>50,000</td>
		<td>-</td>
		<td>100</td>
		<td>환승목적주차시: 40,000<br>기타 : 50,000</td>
		<td>20,000</td>
	</tr>
	<tr>
		<th>5급지</th>
		<td>50</td>
		<td>1,000</td>
		<td>30,000</td>
		<td>20,000</td>
		<td>50</td>
		<td>30,000</td>
		<td>20,000</td>
	</tr>
	</table>
</div>

<br>
<br>
<br>

<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>요금할인</div>

<div class='margin_top_0dot5 lineheight_1dot8'>
	<ul class='ul_hyphen1a10'>
		<li>긴급 자동차 및 민방위 훈련대피 자동차 (전액면제)</li>
		<li>국가유공 자동차 (80% 할인)</li>
		<li>장애인 자동차 (80% 할인)</li>
		<li>1000cc이하 자동차(50% 할인)</li>
		<li>저공해 스티커부착 자동차(50% 할인)</li>
		<li>다둥이카드 소지자(두자녀 (30% 할인), 세자녀이상 (50% 할인)</li>
		<li>장기기증등록자 및 기증자(50% 할인)</li>
	</ul>
</div>

<br>
<br>
<br>


