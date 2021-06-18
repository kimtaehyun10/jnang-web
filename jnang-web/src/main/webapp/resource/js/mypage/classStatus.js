/**
* @projectDescription classStatus.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '마이페이지', '수강신청현황', true);	
});



function cancelPay(tid, s_no, pay, comcd, seq) {	
	
	if (confirm("\n 당일 상품 결제를 취소  하시겠습니까?\n\n취소시 복구 되지 않습니다.")) {
		
	} else {
		return false;	
	}
	
	pay= pay.replace(/,/g,"");
	var param = {"TID" : tid, "SLIP_NO" : s_no};
	var frm = document.frmCancel;
	frm.p1.value = tid;
	frm.p2.value = pay;
	frm.p3.value = s_no;
	frm.p4.value = comcd;
	frm.otherParam.value = seq;
	frm.action = "/smartPay/mainCancelPay";
	frm.submit();
	
}




var lectDate = { }; //신청할 강좌 시작종료값 임시 저장
var addBasket1 = function(comcd, classCd, itemCd){
	
	// 재등록 작업 해야한다 (수강신청기간 구분 해야한다 - 신규등록 참고)
	
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
	
	var lecture = { comcd:comcd, classCd:classCd, itemCd:itemCd };
	
	$.get('/data/encode/text', {text:JSON.stringify(lecture)}, function(data){
		localStorage.setItem('lecture', data);
		
	}).done(function(){
		$.get('/data/lecture/program', {secureText:localStorage.getItem('lecture')}, function(data){
			var programStartDate = dateUtil.getProgramStartDate(data.grpcd.startdate, comcd, day); //수정작업해야함
			var programEndDate = dateUtil.getProgramEndDate(programStartDate, data.monthCnt);			
			//신청할 강좌 시작종료값 임시 저장
			lectDate.sDate =  programStartDate;
			lectDate.eDate =  programEndDate;
						
		}).done(function(){
			$.get('/data/lecture/basketIn', {secureText:localStorage.getItem('lecture'), "lectDate" : lectDate }, function(data){
				
				if (data.SEQ != undefined && data.SEQ != "" && data != null){					
					window.location.href='/lecture/lecturePaymentDetail?SEQ='+data.SEQ;
				} else if (data.code == "-9"){
					alert("세션이 종료되었거나 로그인 회원이 아닙니다.\n\n로그인 페이지로 이동합니다.");
					window.location.href='/mem/login';
				} else if(data.code == "-10"){
					/*alert("회원카드를 발급받은 회원만 신청이 가능합니다. 센터에 방문하셔서 발급 받으세요.");
					window.location.reload();*/
				} else {
					alert("접수오류 \n\n다시 시도 후 관리자에게 문의 하세요.");
					window.location.reload();
				}
				
			});
		});
	});
};

