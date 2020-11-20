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



function cancelPay(tid, s_no, pay) {
	
	//alert(tid);
	
	if (confirm("\n 당일 상품 결제를 취소  하시겠습니까?\n\n취소시 복구 되지 않습니다.")) {
		
	} else {
		return false;	
	}
	
	pay= pay.replace(/,/g,"");
	var param = {"TID" : tid, "SLIP_NO" : s_no};
	
	top.location.href='/smartPay/mainCancelPay?TID='+ tid +'&CancelAmt='+ pay;
	
	$.get('/data/mypage/classCancelPay', param, function(data){
		try {
			
		//	alert(data);
			
		top.location.href='/smartPay/mainCancelPay?TID='+ tid;
		
			
		} catch (exception) {
			alert("취소 처리 오류 : 잠시후 다시 시도하여 주세요..");
			return;
		}

	});
	
	
	
}
