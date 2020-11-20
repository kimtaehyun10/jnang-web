/**
* @projectDescription dwict.board.priceDetailPage.js
*
* @author KTH
* @version 1.0
*/
'use strict'
const priceDetailHtml = ''
	+'<div class="sub_bbs_v01">'
	+'<table class="stbl_w3b" id="priceDetailData">'
	+'</table>'
	+'<div id="brdDetailData2"></div>'
	+'<div class="bx_btns_01a" id="priceDetailBtn">'	
	+'</div>'
	+'</div>'
;

const searchPriceDetail = function(){	
	$.get('/data/board/price/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val(), {}, function(data){						
		let cont1 = '';
		let cont2 = '';
		cont1 += '<colgroup><col width="170px"/><col width="*"/></colgroup>';
		cont1 += '<tr>';
		cont1 += '<th>기관명</th>';
		cont1 += '<td>중랑구시설관리공단</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>발주부서</th>';
		cont1 += '<td>'+data.orderDepart+'</td>';
		cont1 += '</tr>';		
		cont1 += '<tr>';
		cont1 += '<th>작성자</th>';
		cont1 += '<td>'+data.regNm+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>전화번호</th>';
		cont1 += '<td>'+data.regOffmTelno+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>건명</th>';
		cont1 += '<td>'+data.title+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>대표자</th>';
		cont1 += '<td>'+data.ceo+'</td>';
		cont1 += '</tr>';						
		cont1 += '<tr>';
		cont1 += '<th>업체명</th>';
		cont1 += '<td>'+data.comName+'</td>';
		cont1 += '</tr>';						
		cont1 += '<tr>';
		cont1 += '<th>사업자 등록번호</th>';
		cont1 += '<td>'+data.busiNumber+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>등록일</th>';
		cont1 += '<td>'+data.regDt.year+'-'+lpad(data.regDt.monthValue,2,"0")+'-'+lpad(data.regDt.dayOfMonth,2,"0")+'</td>';
		cont1 += '</tr>';
		if(data.price1 === 0 && data.price2 === 0 && data.price3 === 0){
			cont1 += '<tr>';
			cont1 += '<th>제 1회 투찰금액(단위:원)</th>';
			cont1 += '<td><input type="text" id="PRICE_1" name="price" class="inputbox_01a wid_100p" placeholder="숫자만 입력하세요"></td>';
			cont1 += '</tr>';
			cont2 += '<a class="size_m2 btn_green2" onclick="priceUpdate();">저장</a>';
		}else if(data.expectedPrice >= data.price1 && (data.price2 === 0 && data.price3 === 0)){					
			cont1 += '<tr>';
			cont1 += '<th>낙찰가격</th>';
			cont1 += '<td>'+addComma(data.price1)+'원</td>';
			cont1 += '</tr>';
		}else if(data.expectedPrice < data.price1 && (data.price2 === 0 && data.price3 === 0)){
			cont1 += '<tr>';
			cont1 += '<th>제 1회 투찰금액(단위:원)</th>';
			cont1 += '<td>'+addComma(data.price1)+'원</td>';
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>제 2회 투찰금액(단위:원)</th>';
			cont1 += '<td><input type="text" id="PRICE_2" name="price" class="inputbox_01a wid_100p" placeholder="숫자만 입력하세요"></td>';
			cont1 += '</tr>';
			cont2 += '<a class="size_m2 btn_green2" onclick="priceUpdate();">저장</a>';
		}else if(data.expectedPrice < data.price1 && (data.expectedPrice >= data.price2 && data.price3 === 0)){
			cont1 += '<tr>';
			cont1 += '<th>낙찰가격</th>';
			cont1 += '<td>'+addComma(data.price2)+'원</td>';
			cont1 += '</tr>';
		}else if(data.expectedPrice < data.price1 && (data.expectedPrice < data.price2 && data.price3 === 0)){
			cont1 += '<tr>';
			cont1 += '<th>제 1회 투찰금액(단위:원)</th>';
			cont1 += '<td>'+addComma(data.price1)+'원</td>';
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>제 2회 투찰금액(단위:원)</th>';
			cont1 += '<td>'+addComma(data.price2)+'원</td>';
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>제 3회 투찰금액(단위:원)</th>';
			cont1 += '<td><input type="text" id="PRICE_3" name="price" class="inputbox_01a wid_100p" placeholder="숫자만 입력하세요"></td>';
			cont1 += '</tr>';
			cont2 += '<a class="size_m2 btn_green2" onclick="priceUpdate();">저장</a>';
		}else if(data.expectedPrice < data.price1 && (data.expectedPrice < data.price2 && data.expectedPrice >= data.price3)){
			cont1 += '<tr>';
			cont1 += '<th>낙찰가격</th>';
			cont1 += '<td>'+addComma(data.price3)+'원</td>';
			cont1 += '</tr>';
		}else if(data.expectedPrice < data.price1 && (data.expectedPrice < data.price2 && data.expectedPrice < data.price3)){
			cont1 += '<tr>';
			cont1 += '<th>제 1회 투찰금액(단위:원)</th>';
			cont1 += '<td>'+addComma(data.price1)+'원</td>';
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>제 2회 투찰금액(단위:원)</th>';
			cont1 += '<td>'+addComma(data.price2)+'원</td>';
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>제 3회 투찰금액(단위:원)</th>';
			cont1 += '<td>'+addComma(data.price3)+'원</td>';
			cont1 += '</tr>';
		}			
		$('#priceDetailData').empty().append(cont1);						
		cont2 += '<a class="size_m2 btn_gray2" href="/price/'+data.cmsCd+'">목록</a>';		
		$('#priceDetailBtn').empty().append(cont2);		
	});
};

var find = {	
	proc: function(){
		if(this.validate()){
			this.checkOk();
		}
	},
	validate: function(){		
		if($("#ceo").val() === ''){
			alert('대표자를 입력해주세요.');
			$("#ceo").focus();
			return false;
		};				
		if($("#busiNumber").val() === ''){
			alert('사업자등록번호를 입력해주세요.');
			$("#busiNumber").focus();
			return false;
		};
		if($("#brdPwd").val() === ''){
			alert('비밀번호를 입력해주세요.');
			$("#brdPwd").focus();
			return false;
		};
		return true;
	},
	checkOk: function(){				
		if($('#h_ceo').val() === $('#ceo').val() && $('#h_busiNumber').val() === $('#busiNumber').val() && $('#h_brdPwd').val() === $('#brdPwd').val()){
			alert('확인되었습니다.');
			$('#boardCon').empty().append(priceDetailHtml);
			searchPriceDetail();
		}else{
			alert('입력하신 정보가 일치하지 않습니다.');
		}
	}
}

const addComma = function(num){
	var regexp = /\B(?=(\d{3})+(?!\d))/g;
	return num.toString().replace(regexp, ',');
}

const enterKey = function(){		
	if(window.event.keyCode == 13 || !window.event.keyCode){		
		find.proc();
	}	
}

const priceUpdate = function(){
	if(!/^[0-9]+$/.test($('input[name=price]').val())){
		alert('투찰금액은 숫자만 입력해주세요.');
		$('input[name=price]').focus();
		return;
	};
	var columnValue = $('[name="price"]').attr('id');
	var price = $('input[name=price]').val();
	var param = {
		columnValue:columnValue,
		price:price
	};
	$.patch('/data/board/price/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val(), param, function(data){
		alert(data.result);
		window.location.href='/price/'+$('#h_cmsCd').val()+'';				
	});
}