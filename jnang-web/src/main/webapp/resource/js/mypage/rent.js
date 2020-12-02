/**
* @projectDescription cart.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '마이페이지', '대관신청현황', true);
	
	//data.list('');
	
});


var data = {
	list: function(mode){
		
		var param = {"SEQ":"0"};
		var seq = "";		
		var arrayParam = new Array();
		$("input:checkbox[name='cbox[]']:checked").each(function(){
			arrayParam.push ($(this).val());
		});
		if (mode =="del") {
			if (arrayParam.length == 0) {
				alert("데이타를 1건이상 선택하세요.");
				return false;
			}
			seq = arrayParam.toString();
			param = {"SEQ" : seq};
		}
		if (confirm("\n 대관 신청을 취소  하시겠습니까?\n ")) { 			
		} else {
			return false;	
		}		
		$.get('/data/mypage/rentSelectCancel', param, function(data){
			try {
				if(data.length != 0){
					window.location.reload();
					alert("취소 완료");
				}
			
			} catch (exception) {
				alert("예약내역 출력오류 : 잠시후 다시 시도하여 주세요..");
				return;
			}
		});
	},
	other: function(){
	},	
	//var param = { "seq" : seq };

};
