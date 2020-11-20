/**
* @projectDescription dwict.board.price.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	$('#boardCon').append(priceHtml);
	searchPrice();
});

const priceHtml = ''
	+'<div class="sub_board_l01">'
	+'<div class="sch_box_01">'
	+'<ul>'
	+'<li>'
	+'<select id="searchKey" name="searchKey" class="selbox_01">'
	+'<option value="ALL" selected>통합검색</option>'
	+'<option value="TITLE">제목</option>'
	+'<option value="CONT">내용</option>'
	+'</select>'
	+'</li>'
	+'<li><input type="text" id="searchValue" name="searchValue" maxlength="10" onkeyup="searchBoard()"></li>'
	+'<li><input type="submit" value="검색하기" onclick="searchBoard()"></li>'
	+'</ul>'
	+'</div>'
	+'<div class="autoscroll_x1 margin_top_1dot0">'
	+'<table id="priceData" class="stbl_l3a con_wid">'	
	+'</table>'
	+'</div>'
	+'<div id="paging" class="paging_01a">'
	+'</div>'
	+'<div id="brdBtn" style="text-align:right;">'
	+'</div>'
	+'</div>'
;

const searchPrice = function(){
	const param = {
		cmsCd:$('#h_cmsCd').val(),
		pageIndex:$('#h_pageIndex').val(),
		pageSize:$('#h_pageSize').val(),
		startRow:$('#h_startRow').val(),
		searchKey:$('#searchKey').val(),
		searchValue:$('#searchValue').val()
	};		
	$.get('/data/board/price/'+$('#h_cmsCd').val(), param, function(data){		
		let cont1 = '';
		let cont2 = '';
		let size = data.resultList.length;	
		if(size!=0){
			for(var i=0; i<size; i++){
				if(i==0){
					cont1 += '<colgroup><col width="10%"/><col width="60%"/><col width="10%"/><col width="10%"/><col width="10%"/></colgroup>';
					cont1 += '<tr><th>번호</th><th>건명</th><th>발주부서</th><th>작성일</th><th>결과</th></tr>';
				}
				cont1 += '<tr class="">';
				cont1 += '<td class="no01">'+data.resultList[i].brdNo+'</td>';
				cont1 += '<td class="tit01 ali_l"><a onclick="priceDetailPage(\''+ data.resultList[i].cmsCd +'\','+data.resultList[i].brdNo+');">'+data.resultList[i].title+'</a></td>';
				cont1 += '<td>'+data.resultList[i].comName+'</td>';												
				cont1 += '<td>'+data.resultList[i].regDt.year+'-'+lpad(data.resultList[i].regDt.monthValue,2,"0")+'-'+lpad(data.resultList[0].regDt.dayOfMonth,2,"0")+'</td>';				
				if(data.resultList[i].price1 === 0 && data.resultList[i].price2 === 0 && data.resultList[i].price3 === 0){
					cont1 += '<td><span class="stat1 stat_n">시담대기</span></td>';
				}else if(data.resultList[i].expectedPrice >= data.resultList[i].price1 && (data.resultList[i].price2 === 0 && data.resultList[i].price3 === 0)){					
					cont1 += '<td><span class="stat1 stat_y">시담완료</span></td>';
				}else if(data.resultList[i].expectedPrice < data.resultList[i].price1 && (data.resultList[i].price2 === 0 && data.resultList[i].price3 === 0)){
					cont1 += '<td><span class="stat1 stat_n">시담대기</span></td>';
				}else if(data.resultList[i].expectedPrice < data.resultList[i].price1 && (data.resultList[i].expectedPrice >= data.resultList[i].price2 && data.resultList[i].price3 === 0)){
					cont1 += '<td><span class="stat1 stat_y">시담완료</span></td>';
				}else if(data.resultList[i].expectedPrice < data.resultList[i].price1 && (data.resultList[i].expectedPrice < data.resultList[i].price2 && data.resultList[i].price3 === 0)){
					cont1 += '<td><span class="stat1 stat_n">시담대기</span></td>';
				}else if(data.resultList[i].expectedPrice < data.resultList[i].price1 && (data.resultList[i].expectedPrice < data.resultList[i].price2 && data.resultList[i].expectedPrice >= data.resultList[i].price3)){
					cont1 += '<td><span class="stat1 stat_y">시담완료</span></td>';
				}else if(data.resultList[i].expectedPrice < data.resultList[i].price1 && (data.resultList[i].expectedPrice < data.resultList[i].price2 && data.resultList[i].expectedPrice < data.resultList[i].price3)){
					cont1 += '<td><span class="stat1 stat_n">시담대기</span></td>';
				}				    
				cont1 += '</tr>';								
			}
		}else{
			cont1 += '<tr class="noti">';
			cont1 += '<td colspan="5">등록된 자료가 없습니다.</td>';
			cont1 += '</tr>';
		}
		$('#priceData').empty().append(cont1);		
	}).done(function(data){
		paging(data, 'searchPrice()');
	});
};

const searchBoard = function(){		
	if(window.event.keyCode == 13 || !window.event.keyCode){						
		$('#h_pageIndex').val('1');
		searchPrice();
	}	
}

const priceDetailPage = function(cmsCd, brdNo){						
	window.location.href='/price/'+cmsCd+'/'+brdNo;	
};