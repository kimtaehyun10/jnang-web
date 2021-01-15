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
	frm.action = "/smartPay/mainCancelPay";
	frm.submit();
	
}
