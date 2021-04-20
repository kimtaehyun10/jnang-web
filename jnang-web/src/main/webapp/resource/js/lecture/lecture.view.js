/**
* @projectDescription lecture.view.js
*
* @author RGJ
* @version 1.23
*/
'use strict'
$(function(){
	setMenuTitle('bg_01', '시설 통합예약', '상세보기', true);
	initPage();
});

var lectDate = { }; //신청할 강좌 시작종료값 임시 저장
var initPage = function(){
	$.get('/data/lecture/program', {secureText:localStorage.getItem('lecture')}, function(data){
		var date = new Date(); 
		var year = date.getFullYear(); 
		var month = new String(date.getMonth()+1); 
		var day = new String(date.getDate()); 

		// 한자리수일 경우 0을 채워준다. 
		if(month.length == 1){ 
		  month = "0" + month; 
		} 
		if(day.length == 1){ 
		  day = "0" + day; 
		}
		
		var programStartDate = dateUtil.getProgramStartDate(data.grpcd.startdate, data.comcd, day);
		var programEndDate = dateUtil.getProgramEndDate(programStartDate, data.monthCnt);
		var tableDesc = '', tableHead = '', tableBody = '', capaTableHead = '', capaTableBody = '', tableDetail = '';
		
		var costAmt = data.costAmt.toLocaleString(undefined, {maximumFractionDigits: 5});
		//신청할 강좌 시작종료값 임시 저장
		lectDate.sDate =  programStartDate;
		lectDate.eDate =  programEndDate;
		
		//tableDesc
		tableDesc += '<caption>강좌 상세보기</caption>';
		tableDesc += '<colgroup>';
		tableDesc += '<col width="100px"/>';
		tableDesc += '<col width="*"/>';
		tableDesc += '</colgroup>';
		tableHead += '<tr>';
		tableHead += '<th>강좌명</th>';
		tableHead += '<td>'+data.classNm+'</td>';
		tableHead += '</tr>';
		tableHead += '<tr>';
		tableHead += '<tr>';
		tableHead += '<th>시간/요일</th>';
		tableHead += '<td>'+data.trainTimeNm+' / '+data.trainDayNm+'</td>';
		tableHead += '</tr>';
		tableHead += '<th>수강기간</th>';
		tableHead += '<td>'+dateUtil.addPoint(programStartDate)+' ~ '+dateUtil.addPoint(programEndDate)+'</td>';
		tableHead += '</tr>';
		tableHead += '</tr>';
		tableHead += '<th>대상</th>';
		tableHead += '<td>'+data.classObj+'</td>';
		tableHead += '</tr>';
		tableHead += '<tr>';
		tableHead += '<th>센터명</th>';		
		tableHead += '<td >'+data.comnm+'</td>';
		tableHead += '</tr>';
		tableHead += '<th>접수방식</th>';
		tableHead += '<td>'+'선착순 접수'+'</td>';
		tableHead += '</tr>';
		tableHead += '<tr>';
		tableHead += '<th>수강료</th>';	
		tableHead += '<td>'+data.itemNm+'<br>'+comma_str_y(data.costAmt)+'원'+'</td>';
		tableHead += '</tr>';
		
		tableDesc += '<caption>강좌 상세보기</caption>';
		tableDesc += '<colgroup>';
		tableDesc += '<col width="100px"/>';
		tableDesc += '<col width="*"/>';
		tableDesc += '</colgroup>';
		
		tableDetail += '<tr>';
		tableDetail += '<th>강좌소개</th>';
		tableDetail += '<td>'+data.classDesc+'</td>';
		tableDetail += '</tr>';
		
		tableDetail += '<tr>';
		tableDetail += '<th>강좌세부내용</th>';
		tableDetail += '<td>'+data.classNote+'</td>';
		tableDetail += '</tr>';
		
		tableDetail += '<tr>';
		tableDetail += '<th>기타</th>';
		tableDetail += '<td>'+data.bigo+'</td>';
		tableDetail += '</tr>';

		tableDetail += '<br>';

		$('#programDetailTable').empty().append(tableDesc+tableDetail);
		
		/*if(data.sportsCd === '01' && data.comcd === 'JUNGNANG01' || data.sportsCd === '01' && data.comcd === 'JUNGNANG02' ){
		tableDetail += '<tr>';
		tableDetail += '<th>강좌소개</th>';
		tableDetail += '<td>'+'수업내용: 트레이닝을 통한 건강증진 및 기술 강화 훈련'+'<br>'
		+'- 기초반 : Orientation 및 물적응, 기본 다리젓기, 자유형, 배영'+'<br>'
		+'- 초급반 : 자유형, 배영, 평영 다리젓기, 평영'+'<br>'
		+'- 중급반 : 자유형, 배영, 평영, 접영의 숙달 및 지구력강화'+'<br>'
		+'- 상급반 : 자유형, 배영, 평영, 접영의 숙달 및 지구력강화 각 영법별 교정 및 start, turn의 교습 숙달'+'<br>'
		+'</td>';

		tableDetail += '</tr>';

		tableDetail += '<tr>';
		tableDetail += '<th>세부내용</th>';
			tableDetail += '<td>'+'수업내용: 트레이닝을 통한 건강증진 및 기술 강화 훈련'+'<br>'
		+'1. 수영복을 입기 전 샤워를 한 후, 수영복과 모자를 착용합니다.'+'<br>'
		+'2. 강습시작 전 후 준비운동과 정리운동을 하시기 바랍니다.'+'<br>'
		+'3. 안전사고 예방을 위하여 반드시 강사의 지시를 이행하여 주시기 바랍니다.'+'<br>'
		+'4. 눈병, 피부병, 기타 공공위생에 영향을 미치는 환자 및 음주를 하신 분은 이용하실 수 없습니다.'+'<br>'
		+'</td>';
		tableDetail += '</tr>';
		
		tableDetail += '<tr>';
		tableDetail += '<th>기타</th>';
		tableDetail += '<td>'+data.bigo+'</td>';
		tableDetail += '</tr>';

		$('#programDetailTable').empty().append(tableDesc+tableDetail);
		}else{
			
		tableDesc += '<caption>강좌 상세보기</caption>';
		tableDesc += '<colgroup>';
		tableDesc += '<col width="100px"/>';
		tableDesc += '<col width="*"/>';
		tableDesc += '</colgroup>';
		
		tableDetail += '<tr>';
		tableDetail += '<th>강좌소개</th>';
		tableDetail += '<td>'+data.classNote+'</td>';
		tableDetail += '</tr>';

		
		tableDetail += '<tr>';
		tableDetail += '<th>기타</th>';
		tableDetail += '<td>'+data.bigo+'</td>';
		tableDetail += '</tr>';

		tableDetail += '<br>';

		$('#programDetailTable').empty().append(tableDesc+tableDetail);
		}*/
		

		$('#programTable').empty().append(tableDesc+tableHead+tableBody);
		capaTableHead += '<caption>정원</caption>';
		capaTableHead += '<colgroup>';
		capaTableHead += '<col width="33.33333%"/>';
		capaTableHead += '<col width="33.33333%"/>';
		capaTableHead += '<col width="33.33334%"/>';
		capaTableHead += '</colgroup>';
		capaTableBody += '<tr>';
		capaTableBody += '<th>총 정원</th>';
		capaTableBody += '<th>온라인 정원</th>';
		capaTableBody += '<th>온라인 잔여정원</th>';
		capaTableBody += '</tr>';
		capaTableBody += '<tr>';
		capaTableBody += '<td class="ali_c">'+data.capa+' 명</td>';
		capaTableBody += '<td class="ali_c">'+data.webCapa+' 명</td>';
		capaTableBody += '<td class="ali_c">'+(data.webCapa-data.saleCount)+' 명</td>';
		capaTableBody += '</tr>';
		$('#capaTable').empty().append(capaTableHead+capaTableBody);
	});
};

var needAuthorize = {
	user: function(){
		alert('로그인 후 이용해 주세요.');
		window.location.href ='/lecture/list/JUNGNANG02';
	},
	card: function(){
		alert('회원카드를 발급받은 회원만 신청이 가능합니다.');
		//return;
	}
};


//장바구니 저장
var addBasket = function(){
	$.get('/data/lecture/basketIn', {secureText:localStorage.getItem('lecture'), "lectDate" : lectDate }, function(data){
		if (data == "1" || data == "2"){
			window.location.href='/mypage/cart';
		} else if (data == "-9"){
			alert("세션이 종료되었거나 로그인 회원이 아닙니다.\n\n로그인 페이지로 이동합니다.");
			window.location.href='/mem/login';
		} else {
			alert("접수오류 \n\n다시 시도 후 관리자에게 문의 하세요.");
			window.location.reload();
		}
	});

};

/* 개발 참고 : 
 *  강좌 신청 조건 Validation
 *  
 *  alert "금액이 미설정된 강좌입니다.", ""
 *  alert "대상 정보가 올바르지 않습니다.", ""
 *  alert "기간 정보가 올바르지 않습니다.", ""
 *  
 *  alert(mds + '일 보다 과거 일자는 선택할 수 없습니다.');
 *  
 *  die(serr&"강좌 정보가 누락되었습니다.")
 *  die(serr&"이용 개월 정보가 누락되었습니다.")
 *  die(serr&"시작일 정보가 누락되었습니다.")
 *  die(serr&"존재하지 않는 강좌입니다.")
 *  die(serr&"강습 시작일을 직접 선택할 수 없는 강좌입니다.")
 *  die(serr&"동일한 강좌가 이미 접수된 상태입니다.<br><br>수강기간 : " & err_vdate_s & " ~ " & err_vdate_e)
 *  die(serr&"회원 로그인 후 이용 가능합니다.")
 *  die(serr&"이미 선택하신 강좌입니다.<br><br>장바구니를 확인해주세요.")
 *  die(serr&"장바구니 담기에 실패하였습니다.")
 *  
 *  err_str	= "센터 접수설정이 필요합니다."
 *  err_str	= "센터 휴관일로 신청할 수 없습니다."
 *  err_str	= "회원카드를 발급받은 회원만 신청 가능합니다."
 *  err_str	= "회원정보의 성별 정보가 올바르지 않습니다."
 *  err_str	= "회원정보의 생년월일 정보가 올바르지 않습니다."
 *  err_str	= "회원님의 나이 확인에 실패하였습니다."
 *  
 *  err_str	= "정원이 등록된 강좌가 아닙니다."
 *  err_str	= "정원 마감되어 신청할 수 없습니다."
 *  
 *  err_str	= "평일은 접수가 불가합니다."
 *  err_str	= "토요일은 접수가 불가합니다."
 *  err_str	= "일요일은 접수가 불가합니다."
 *  err_str	= "접수 가능한 시간이 아닙니다."
 *  
 *  err_str	= "신규회원 접수일이 아닙니다."
 */

