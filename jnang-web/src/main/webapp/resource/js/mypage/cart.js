/**
* @projectDescription cart.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '마이페이지', '장바구니', true);
	
	data.list('');
	
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
			
			if (confirm("\n 삭제후 데이타 복구불가능합니다 \n 정말로 삭제 하시겠습니까?\n ")) return true;
			return false;
			
			seq = arrayParam.toString();
			param = {"SEQ" : seq};
		}
		$.get('/data/mypage/basketList', param, function(data){
			try {
				var dataList = "";
				if(data.length != 0){
					for(var i=0; i<data.length; i++){
						//debugger;
						if (data[i].ITEM_CD != undefined) {
							var saleAmt = data[i].SALE_AMT;
							var GoodsName = data[i].ITEM_NM
							GoodsName = (data[i].CLASS_NM != "") ? GoodsName +" "+ data[i].CLASS_NM : "";
							GoodsName = GoodsName.replace("/","");
							dataList += "<tr>"
								+ "<td>"+ (i+1) +"</td> "
								+ "<td><input type='checkbox' id='cbox' name='cbox[]' value='"+ data[i].SEQ +"' style='width:30px; height:30px;'></td> "
								+ "<td>"+ data[i].COMNM +"</td> "
								+ "<td>"+ data[i].SPORTS_NM +"</td> "
								+ "<td>"+ data[i].CLASS_NM +"</td> "
								+ "<td>"+ data[i].ITEM_NM +"</td> "
								+ "<td>"+ comma_str_y(saleAmt) +"</td>"
								+ "<td><input type='button' class='size_m2 btn_green1' value='결제' onClick=\"goBtn('"+ data[i].SEQ +"','"+ 10 +"','"+ GoodsName +"','"+ data[i].COMCD +"', );\"></td>"
								+ "</tr>";
						}
					}
					var dataCnt = data.length-1;
					
					/*
					$("#BuyerName").val(data[dataCnt].buyerNameEn);
					$("#BuyerTel").val(data[dataCnt].byerTel);
					$("#BuyerEmail").val(data[dataCnt].buyerEmail);
					$("#EncryptData").val(data[dataCnt].EncryptData);
					$("#ediDate").val(data[dataCnt].ediDate);
					$("#merchantKey").val(data[dataCnt].merchantKey);
					$("#MID").val(data[dataCnt].storeMID);
 					*/					
					$("#dataList").html(dataList);
					
					
				}
				else
				{
					$("#dataList").html("<tr><td colspan='10'>강좌 내역이 없습니다.</td></tr>");
				}
			
			} catch (exception) {
				alert("주문 내역 출력오류 : 잠시후 다시 시도하여 주세요..");
				return;
			}
		});
	},
	other: function(){
	},	
	
	//var param = { "seq" : seq };

};
