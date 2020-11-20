/**
* @projectDescription myBoard.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '마이페이지', '마이게시판', true);
	$('#boardCon').append(brdHtml);
	searchBrd();
});

const brdHtml = ''
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
	+'<table id="brdData" class="stbl_l3a con_wid">'	
	+'</table>'
	+'</div>'
	+'<div id="paging" class="paging_01a">'
	+'</div>'
	+'<div id="brdBtn" style="text-align:right;">'
	+'</div>'
	+'</div>'
;

const searchBrd = function(){
	const param = {
		userId:$('#h_userId').val(),
		pageIndex:$('#h_pageIndex').val(),
		pageSize:$('#h_pageSize').val(),
		startRow:$('#h_startRow').val(),
		searchKey:$('#searchKey').val(),
		searchValue:$('#searchValue').val()
	};		
	$.get('/data/mypage/myBoard/'+$('#h_userId').val(), param, function(data){		
		let cont1 = '';
		let cont2 = '';
		let size = data.resultList.length;	
		if(size!=0){
			for(var i=0; i<size; i++){
				if(i==0){
					cont1 += '<colgroup><col width="70px"/><col width="*"/><col width="150px"/><col width="150px"/><col width="110px"/><col width="110px"/><col width="70px"/></colgroup>';
					cont1 += '<tr><th>번호</th><th>제목</th><th>작성자</th><th>답변여부</th><th>공개여부</th><th>작성일</th><th>조회</th></tr>';
				}
				cont1 += '<tr class="">';
				cont1 += '<td class="no01">'+data.resultList[i].brdNo+'</td>';
				cont1 += '<td class="tit01 ali_l"><a onclick="brdDetailPage(\''+ data.resultList[i].cmsCd +'\','+data.resultList[i].brdNo+',\''+ data.resultList[i].regId +'\',\''+ data.resultList[i].secretYn +'\');">'+data.resultList[i].title+'</a></td>';
				cont1 += '<td>'+data.resultList[i].regNm+'</td>';
				if(data.resultList[i].repYn === 'Y'){
					cont1 += '<td><span class="stat1 stat_y">답변완료</span></td>';
				}else{
					cont1 += '<td><span class="stat1 stat_n">답변처리중</span></td>';
				}
				if(data.resultList[i].secretYn === 'Y'){
					cont1 += '<td>공개</td>';
				}else{
					cont1 += '<td>비공개</td>';
				}				
				cont1 += '<td>'+data.resultList[i].regDt.year+'-'+lpad(data.resultList[i].regDt.monthValue,2,"0")+'-'+lpad(data.resultList[0].regDt.dayOfMonth,2,"0")+'</td>';				
				cont1 += '<td>'+data.resultList[i].hit+'</td>';
				cont1 += '</tr>';								
			}
		}else{
			cont1 += '<tr class="noti">';
			cont1 += '<td colspan="5">등록된 자료가 없습니다.</td>';
			cont1 += '</tr>';
		}
		$('#brdData').empty().append(cont1);
		if(!$('#emptyDep1').text()) cont2 += '<a class="size_m2 btn_gray2" href="/board/write/'+$('#h_cmsCd').val()+'">글쓰기</a>';		
		$('#brdBtn').empty().append(cont2);
	}).done(function(data){
		paging(data, 'searchBrd()');
	});
};

const searchBoard = function(){		
	if(window.event.keyCode == 13 || !window.event.keyCode){						
		$('#h_pageIndex').val('1');
		searchBrd();
	}	
}

const brdDetailPage = function(cmsCd,brdNo,regId,secretYn){
	if(regId != $("#h_userId").val() && secretYn == 'N'){
		alert('비공개 글 입니다.');		
	}else{		
		const param = {
			cmsCd:cmsCd,
			brdNo:brdNo,
			mType:'BRD'
		};		
		$.patch('/data/board/hit/'+cmsCd+'/'+brdNo, param, function(data){		
			if(data>0){
				window.location.href='/mypage/myBoard/'+cmsCd+'/'+brdNo;
			}		
		});
	}			
};