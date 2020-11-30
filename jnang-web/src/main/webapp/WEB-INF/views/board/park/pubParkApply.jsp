<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/park/dwict.board.pubParkApply.js"></script>

<div class='sub_cleanreport'>	
	<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>공영주차장 이용신청 안내문</div>
	<div class='padding_left_1dot0 fontsize_1dot15 margin_top_1dot0 lineheight_1dot8'>
		<ul class='ul_hyphen1a13'>
			<li><span class='fc_red1'>요금감면을 받기 위해서는 아래 구비서류가 필요합니다.</span></li>
			<li>일반 : 자동차등록증</li>
			<li>국가유공자 : 복지카드(유공자카드)</li>
			<li>장애인 : 장애인등록증(복지카드), 장애인주차 표지판</li>
			<li>경차·저공해·친환경·전기차 : 자동차등록증(저공해필증, 친환경차량 필증, 전기차 확인증)</li>
			<li>다자녀 : 다둥이 행복카드 ※ 기타 할인증빙 관련 요청서류(할인증빙 서류는 정보확인 후 즉시파기)</li>
		</ul>
	</div>	
	<br>
	<br>				
	<div class='border_top_3_green'></div>
	<table class='stbl_w3b border_top_0' summary=''>
	<caption>공영주차장 이용신청</caption>
	<colgroup>
		<col width='100px'/>
		<col width='*'/>
	</colgroup>		
	<tr>
		<th>성명</th>
		<td>
			<input type='text' id='name' name='name' value='' maxlength='' required class='inputbox_01a inputbox_01_s3' placeholder="자동차등록증에 명시 된 소유자명">
		</td>
	</tr>
	<tr>
		<th>연락처</th>
		<td><input type='text' id='phone' name='phone' value='' maxlength='' required class='inputbox_01a inputbox_01_s3'></td>
	</tr>
	<tr>
		<th>차량번호</th>
		<td>
			<input type='text' id='car_number' name='car_number' value='' maxlength='' required class='inputbox_01a inputbox_01_s3'>
		</td>
	</tr>
	<tr>
		<th>차종(모델명)</th>
		<td>
			<input type='text' id='car_model' name='car_model' value='' maxlength='' required class='inputbox_01a inputbox_01_s3'>
		</td>
	</tr>
	<tr>
		<th>주차장명</th>
		<td>
			<select id='pub_park_name' name='pub_park_name' class='inputbox_01a inputbox_01_s1' required>
				<option value=''>-- 선택하세요 --</option>					
				<option value='1'>상봉2동 공영주차장</option>
				<option value='2'>까치공원 공영주차장</option>
				<option value='3'>면목5동 공영주차장</option>
				<option value='4'>은행나무 공영주차장</option>
				<option value='5'>늘봄 공영주차장</option>
				<option value='6'>중화1동 공영주차장</option>
				<option value='7'>중곡초등학교 공영주차장</option>
				<option value='8'>봉수대공원 공영주차장</option>
				<option value='9'>용마폭포공원 공영주차장</option>
				<option value='10'>면목2동 공영주차장</option>
				<option value='11'>중랑초등학교 공영주차장</option>
				<option value='12'>면일초등학교 공영주차장</option>
				<option value='13'>동원시장 주차장</option>
				<option value='14'>금란 주차장</option>
				<option value='15'>면목시장 주차장</option>
				<option value='16'>망우본동 공영주차장</option>
				<option value='17'>사가정공원 공영주차장</option>
				<option value='18'>중화 제1~4 공영주차장</option>
				<option value='19'>중랑역 제1~2 공영주차장</option>
				<option value='20'>망우3동 공영주차장</option>
				<option value='21'>면목유수지 공영주차장</option>
			</select>
		</td>
	</tr>		
	<tr>
		<th>이용시간</th>
		<td>
			<select id='use_time' name='use_time' class='inputbox_01a inputbox_01_s1' required>
				<option value=''>-- 선택하세요 --</option>					
				<option value='1'>전일</option>
				<option value='2'>주간</option>
				<option value='3'>야간</option>				
			</select>							
		</td>
	</tr>
	<tr>
		<th>요금감면</th>
		<td>
			<select id='discount' name='discount' class='inputbox_01a inputbox_01_s1' required>
				<option value='0'>-- 없음 --</option>					
				<option value='1'>국가유공(80%)</option>
				<option value='2'>장애인(80%)</option>
				<option value='3'>경차(50%)</option>				
				<option value='4'>저공해(50%)</option>				
				<option value='5'>친환경(50%)</option>				
				<option value='6'>전기(50%)</option>				
				<option value='7'>2자녀(30%)</option>				
				<option value='8'>3자녀(50%)</option>								
			</select>			
		</td>
	</tr>				
	</table>							
	<br>
	<br>	
	<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>공영주차장 이용기본규약</div>
	<div class='inputbox_01a wid_100p margin_top_1dot0'>
		□ 신청자격<br>

		1. 본인 소유의 차량을 등록하여 이용을 원하는 자 중 아래 결격사유가 없는 차량<br><br>			

		□ 신청제외대상<br>
		1. 1톤을 초과하는 화물차량(탑차, 기타 구조물 설치차량)<br>
	    2. 차량 및 기타장비(캐리어 등) 포함 높이가 주차장 제한높이를 초과하는 차량<br>
	    3. 차량 치수(전장, 전폭, 전고, 축거)가 주차장 이용에 부적합한 경우(차량의 구조상 주차가 불가능하거나 곤란한 경우)<br>
	    4. 압류차량, 미등록차량(책임보험 미가입 등), 기타 행정의무를 이행하지 않거나 위반한 차량<br> 
	    5. 이륜자동차(4륜바이크 포함)<br><br>

		□ 사용조건<br>
		1. 주차장사업 이용내규 준수<br>
    		2. 위 구비서류를 제출하고 사용 승인을 받은 차량<br>
    		※ 요금할인 중 장애할인 대상자는 적합여부(변동사항 발생여부) 확인을 위해 매년1회 주차사업팀(공영주차장 담당)으로 관련 증빙서류를 제출하여야 합니다.<br>
    		3. 주차요금은 매월(1개월) 출구에서 카드결제 하여야 합니다.(연장기한 내 본인 결제)<br>
    		4. 지정된 기간에 기간 연장하지 않을 경우 배정이 취소되며, 익월 1일 00시부터 주차요금(시간주차 요금)이 부과됩니다.<br><br>

		□ 기타 주차장 이용동의<br>
		1. 아래의 사유가 발생 할 경우 배정취소 할 수 있으며, 공단의 취소 결정에 이의를 제기하지 않겠습니다.<br> 
        - 주차시설을 훼손하거나 인근 주민에게 피해를 유발시키는 행위(쓰레기 무단투기, 기타 민원성 행위 등)<br>
        - 배정받은 차량의 주차 외 용도사용 시<br>
        - 기간연장 기한 내 연장처리가 안될 경우<br>
        - 기타 규정에 위반 되거나 주차장 이용취소가 불가피한 경우	        						
	</div>	
	<br>	
	<div>
		<label><input type='checkbox' id='agree1' name='agree1' value='Y'> 본 신청에 작성된 내용이 사실과 다를 경우, 주차장 사용에 불이익을 받을 수 있다는 것에 동의하며, 이용신청서를 제출합니다.</label>
	</div>
					
	<br>
	<br>	
	<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>개인정보 수집･이용 및 제공, 조회 등에 관한 동의서</div>
	<div class='inputbox_01a wid_100p margin_top_1dot0'>				
		▢ 개인정보 수집․이용 내역<br>			
		1. 항목 : 성명, 생년월일, 휴대폰 연락처, 차량번호, 차종, 차량할인정보(국가유공, 경차·저공해·친환경·전기 차량, 다자녀 유무)<br>
	    2. 수집․이용 목적 : 주차장 운영에 따른 이용자 이력관리 및 이용 안내문자(SMS) 발송<br>
	    3. 보유기간 : 3년<br><br>			
		
		▢ 민감정보 처리 내역<br>
		1. 항목 : 장애유무<br>
    		2. 수집목적 : 요금할인 적용<br>     		
    		3. 보유기간 3년<br>
    		4. 지정된 기간에 기간 연장하지 않을 경우 배정이 취소되며, 익월 1일 00시부터 주차요금(시간주차 요금)이 부과됩니다.<br>     			        						
	</div>	
	<br>	
	<div>
		<label><input type='checkbox' id='agree2' name='agree2' value='Y'> 위와 같이 민감정보를 수집․이용하는데 동의합니다.</label>
	</div>
	
		
	<div class='bx_btns_01a ali_c'>
		<input type="button" class='size_m2 btn_green1' value='작성완료' onclick="pubParkfind.proc()">
	</div>		
</div>
