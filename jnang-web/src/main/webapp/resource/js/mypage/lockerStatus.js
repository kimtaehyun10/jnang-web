/**
* @projectDescription lockerStatus.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '마이페이지', '사물함신청현황', true);
	searchLocker();
});

const lockerHtml = ''
	+'<div id="sub_my_status">'
	+'<div id="notes" class="border_box_a_gray_1a margin_b50">'
	+'<h4 class="margin_b20">유의사항</h4>'
	+'<ul class="ul_hyphen1a13">'
	+'<li>유료 사물함 임대 현황 입니다.(사물함은 시설이용고객만 임대하실 수 있습니다.)</li>'
	+'<li>임대보증금은 방문 납입하셔야 합니다.</li>'
	+'<li>온라인 결제취소는 평일 업무시간(09시~18시)내에만 가능합니다.</li>'
	+'</ul>'
	+'</div>'
	+'<table class="stbl_l1a">'
	+'<thead><tr><th>장소</th><th>락카위치</th><th>락카번호</th><th>이용기간</th><th>이용개월</th><th>금액</th><th>보증금</th><th>상태</th><th>신청</th></tr></thead>'
	+'<tbody id="lockerData" class="stbl_l3a con_wid">'
	+'</tbody>'
	+'</table>'
	+'</div>'	
;

const searchLocker = function(){
			
	$.get('/data/mypage/lockerStatus/'+$('#h_memNo').val(), {}, function(data){
		if(data.length === 0){
			$('.sub_lecture_l01').css('display','block');
		}else{
			$('#boardCon').html(lockerHtml);			
			var cont1 = '';
			for(var i=0;i<data.length;i++){
				var date1 = new Date(data[i].RENT_EDATE);
				var date2 = new Date(data[i].TODAY);
				cont1 += '<tr>';
				cont1 += '<td>';
				cont1 += data[i].COMNM
				cont1 += '</td>';
				cont1 += '<td>';
				cont1 += data[i].PLACE_CD
				cont1 += '</td>';						
				cont1 += '<td>';
				cont1 += data[i].LOCKER_CD
				cont1 += '</td>';						
				cont1 += '<td>';
				cont1 += data[i].RENT_SDATE+' ~ '+data[i].RENT_EDATE;
				cont1 += '</td>';						
				cont1 += '<td>';
				cont1 += data[i].RENT_MON+'개월';
				cont1 += '</td>';			
				cont1 += '<td>';
				cont1 += data[i].RENT_AMT+'원';				
				cont1 += '</td>';
				cont1 += '<td>';
				cont1 += data[i].DEPOSIT_AMT+'원';
				cont1 += '</td>';
				cont1 += '<td>';
				if(date1>date2){
					cont1 += '사용중';
				}else{
					cont1 += '사용만료';
				}				 
				cont1 += '</td>';
				cont1 += '<td>';
				if(data[i].RENT_NO === data.length && date1>date2){
					cont1 += '<span class="stat1 stat_y" style="cursor:pointer;" onclick="reLocker(\''+data[i].COMNM+'\',\''+data[i].PLACE_CD+'\',\''+data[i].LOCKER_CD+'\','+data[i].RENT_AMT+',\''+data[i].COMCD+'\',\''+data[i].MEM_NO+'\','+data[i].RENT_NO+')">연장하기</span>';
				}
				cont1 += '</td>';
				cont1 += '</tr>';
				//       reLocker(\''+data[i].COMCD+'\',\''+data[i].MEM_NO+'\','+data[i].RENT_NO+');
			}
			$('#lockerData').html(cont1);
		}
	});
};
	
