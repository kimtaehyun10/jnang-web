/**
* @projectDescription dwict.board.decCommentPage.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '고객광장', '처리결과 확인');
});

const decCommentPageHtml = ''
	+'<div class="sub_bbs_v01">'
	+'<table class="stbl_w3b" id="decCommentData">'
	+'</table>'
	+'<div id="decCommentData2"></div>'
	+'<div class="bx_btns_01a" id="decCommentBtn">'	
	+'</div>'
	+'</div>'
;

const searchDecCommentPage = function(){	
	$.get('/data/declaration/'+$("#dCode").val()+'/'+$("#dPass").val(), {}, function(data){		
		if(!data){
			alert('해당하는 신고서가 없습니다.');
			return;
		}else{
			if(data.ddel === 'Y'){
				alert('삭제된 신고서입니다.');
				return;
			}
			alert('확인되었습니다.');
			$('#boardCon').empty().append(decCommentPageHtml);
			let cont1 = '';
			cont1 += '<colgroup><col width="150px"/><col width="*"/></colgroup>';
			cont1 += '<tr>';
			cont1 += '<th>신고유형</th>';
			if(data.dtype === 1)cont1 += '<td>갑질 피해 신고센터</td>';			
			if(data.dtype === 2)cont1 += '<td>직원의 부닥이득 수수행위</td>';			
			if(data.dtype === 3)cont1 += '<td>직무수행 저해행위</td>';			
			if(data.dtype === 4)cont1 += '<td>성범죄 발생 신고</td>';			
			if(data.dtype === 5)cont1 += '<td>인권침해 발생 신고</td>';			
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>제목</th>';
			cont1 += '<td>'+data.dtitle+'</td>';
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>누가</th>';
			cont1 += '<td>'+data.dwho+'</td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>언제</th>';
			cont1 += '<td>'+data.dwhen+'</td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>어디서</th>';
			cont1 += '<td>'+data.dwhere+'</td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>내용</th>';
			cont1 += '<td><textarea id="cont" class="inputbox_01a wid_100p" readonly>'+data.dcontents+'</textarea></td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>이 문제를 아는사람</th>';
			cont1 += '<td><textarea id="cont" class="inputbox_01a wid_100p" readonly>'+data.dacquaintance+'</textarea></td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>이 문제의 확인 방법</th>';
			cont1 += '<td><textarea id="cont" class="inputbox_01a wid_100p" readonly>'+data.dconfirm+'</textarea></td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>이 문제의 파악 경위</th>';
			if(data.dinspector === '1')cont1 += '<td>내게 일어난 일이라서</td>';
			if(data.dinspector === '2')cont1 += '<td>내가 직접 보거나 들은 일이라서</td>';
			if(data.dinspector === '3')cont1 += '<td>직장 동료에게 들었음</td>';
			if(data.dinspector === '4')cont1 += '<td>외부인에게 들었음</td>';
			if(data.dinspector === '5')cont1 += '<td>소문으로 들었음</td>';
			if(data.dinspector === '6')cont1 += '<td>우연히 문서나 파일을 보다가 알게되었음</td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>이 문제의 지속 기간</th>';
			if(data.dtime === 1)cont1 += '<td>한번</td>';
			if(data.dtime === 2)cont1 += '<td>일주일</td>';
			if(data.dtime === 3)cont1 += '<td>1-3 개월</td>';
			if(data.dtime === 4)cont1 += '<td>3개월에서 1년</td>';
			if(data.dtime === 5)cont1 += '<td>1년 이상</td>';
			cont1 += '</tr>';		
			cont1 += '<tr>';
			cont1 += '<th>첨부파일</th>';		
			cont1 += '<td id="attach"></td>'
			cont1 += '</tr>';
			cont1 += '<tr>';
			cont1 += '<th>처리결과</th>';		
			cont1 += '<td id="decResult"></td>'
			cont1 += '</tr>';
			$('#decCommentData').empty().append(cont1);		
			let cont2 = '';		
			cont2 += '<a class="size_m2 btn_gray2" href="/decComment/'+$('#h_cmsCd').val()+'">뒤로가기</a>';		
			$('#decCommentBtn').empty().append(cont2);
		}			
	}).done(function(data){				
		$.get('/data/ntc/'+data.cmsCd+'/'+data.dno+'/'+data.dfile, {}, function(data){			
			let cont3 = '';
			for(var i=0;i<data.length;i++){								
				cont3 += '<a onclick="fileDownload(\''+data[i].attachId+'\','+data[i].attachNo+');">'+data[i].fileNm+'</a><br>';
				$("#attach").html(cont3);								
			}
		});
		$.get('/data/decComment/'+$('#h_cmsCd').val()+'/'+data.dno, {}, function(data){			
			let cont4 = '';
			if(!data){
				cont4 += '처리진행중';
			}else{
				cont4 += '처리완료';
				let cont5 = '';
				cont5 += '<div class="bbs_v_rep1a">';
				cont5 += '<div class="data">';		
				cont5 += '<div class="info">';		
				cont5 += '답변내역<br>담당자 : '+data.nm+'<br>담당부서 : '+data.operId+'<br>전화번호 : '+data.tel+'<br>답변일 : '+data.regdt+'';		
				cont5 += '</div>';
				cont5 += '<div class="cont1">';
				cont5 += data.title+'<br>';
				cont5 += data.conts;
				cont5 += '</div>';
				cont5 += '</div>';
				cont5 += '</div>';				
				$("#decCommentData2").html(cont5);
			}
			$("#decResult").html(cont4);
		});
	});
};

const fileDownload = function(attachId, attachNo){
	window.location.href = '/data/file/board/'+attachId+'/'+attachNo;
}

var find = {	
	proc: function(){
		if(this.validate()){
			this.checkOk();
		}
	},
	validate: function(){		
		if($("#dCode").val() === ''){
			alert('고유번호를 입력해주세요.');			
			$("#dCode").focus();
			return false;
		};				
		if($("#dPass").val() === ''){
			alert('비밀번호를 입력해주세요.');
			$("#dPass").focus();
			return false;
		};
		return true;
	},
	checkOk: function(){		
		searchDecCommentPage();		
	}
}