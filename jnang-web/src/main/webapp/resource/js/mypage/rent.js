/**
* @projectDescription cart.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '마이페이지', '대관신청현황', true);
	
});


function test(ediDate, goodsAmt, GoodsName, RESERVE_DATE, PLACE_CD, COMCD, RTN_IDX, MEM_ID){
		
		//GoodsName = encodeURI(GoodsName);

		
		debugger;
		$.get("/data/getOdEncryptData/"+ ediDate +"/"+ goodsAmt +"/"+ COMCD, function(data){
		//try {
			//console.log(data);
			//debugger;
			var dataList = "";
			if(data.length != 0){				
				$("#EncryptData").val(data.EncryptData);
				$("#merchantKey").val(data.KEY);
				$("#MID").val(data.MID);
				$("#actionUrl").val(data.URL);
				$("#Amt").val(goodsAmt);
				$("#GoodsName").val(GoodsName);
				
				var html1=$("#returnURL").val();
				var html2='';
				html2+=html1 + "?q=";
				html2+= RESERVE_DATE+"/";
				html2+= PLACE_CD + "/";
				html2+= COMCD + "/";
				html2+= RTN_IDX + "/";
				html2+= MEM_ID;
				
				$("#returnURL").val(html2);
			goPay();	
			} 
		/*} catch (exception) {
			alert("할인적용 오류 : 잠시후 다시 시도하여 주세요..");
			window.location.reload();
			return;
		}*/
	});
	
}

function cancelPay( TID, slipNo, payAmt, payDt, comCd){
	
	debugger;
	var sysDate = new Date();
	var year = sysDate.getFullYear();
	var month = sysDate.getMonth() + 1;
	var date = sysDate.getDate();
	if(month < 10){
		 month = '0' + month;	
	}
	if(date < 10){
		date = '0' + date;
	}
	var today = year+month+date;
	
	var payYear = payDt.substring(0,4);
	var payMonth = payDt.substring(5,7);
	var payDay = payDt.substring(8,10);
	
	payDt = payYear+payMonth+payDay;
	
	if(payDt != today) {
		if (confirm("\n 결제 취소는 당일에만 가능합니다.\n당일이 아닌 경우 환불신청서를 작성하셔야 합니다.\n환불신청서를 작성하시겠습니까?")) {
		var param = {"TID" : TID, "SLIP_NO" : slipNo};
		var frm = document.frmCancel;
		
			frm.p1.value = TID;
			frm.p2.value = payAmt;
			frm.p3.value = slipNo;
			frm.p4.value = comCd;
			//frm.action="/mypage/orderCancel";
			window.location.href='/mypage/orderCancel?TID='+TID+'&payAmt='+payAmt+'&comCd=' + comCd;
			//frm.submit();
			
			
		} else {
			alert("환불신청서 작성을 취소하셨습니다.");
			return false;
		}
	} else if (payDt == today) {
		if (confirm("\n 당일 대여 결제를 취소  하시겠습니까?\n\n취소시 복구 되지 않습니다.")) {
			payAmt = payAmt.replace(/,/g,"");
			var param = {"TID" : TID, "SLIP_NO" : slipNo};
			var frm = document.frmCancel;
			frm.p1.value = TID;
			frm.p2.value = payAmt;
			frm.p3.value = slipNo;
			frm.p4.value = comCd;
			frm.action="/smartPay/mainCancelPay"
			
			frm.submit();
			
		} else {
			return false;	
		}	
	}
	
}

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
				alert("데이타를 1건이상 선택하세요.\n\n[확정]된 예약은 현장에서 취소 가능합니다. ");
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

