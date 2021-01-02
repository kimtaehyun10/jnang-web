/**
* @projectDescription dwict.board.const.js
*
* @author RGJ
* @version 1.2
*/
'use strict'
const faqHtml = ''
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
	+'<div id="faqData" class="faq_data01a clbx"></div>'
	+'<div id="paging" class="paging_01a">'
	+'</div>'
	+'</div>'
;

const ntcHtml = ''
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
	+'<table id="ntcData" class="stbl_l3a con_wid">'	
	+'</table>'
	+'</div>'
	+'<div id="paging" class="paging_01a">'
	+'</div>'
	+'</div>'
;

const ntcDtailPageHtml = ''
	+'<div class="sub_bbs_v01">'
	+'<table class="stbl_w3b" id="ntcDetailData">'
	+'</table>'
	+'<div class="bx_btns_01a" id="ntcDetailBtn">'	
	+'</div>'
	+'</div>'
;

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

const brdDtailPageHtml = ''
	+'<div class="sub_bbs_v01">'
	+'<table class="stbl_w3b" id="brdDetailData1">'
	+'</table>'
	+'<div id="brdDetailData2"></div>'
	+'<div class="bx_btns_01a" id="brdDetailBtn">'	
	+'</div>'
	+'</div>'
;

const brdWritePageHtml = ''
	+'<div class="sub_bbs_v01">'
	+'<table class="stbl_w3b" id="brdWriteData">'
	+'</table>'
	+'<div class="bx_btns_01a" id="brdWriteBtn">'	
	+'</div>'
	+'</div>'
;

const brdUpdatePageHtml = ''
	+'<div class="sub_bbs_v01">'
	+'<table class="stbl_w3b" id="brdUpdateData">'
	+'</table>'
	+'<div class="bx_btns_01a" id="brdUpdateBtn">'	
	+'</div>'
	+'</div>'
;

const addUIEvnet = {
	navi: function(){
		/**
		 * @Summary 서브
		 * @Discription 서브 > 상단2 > 네비메뉴1__onoff
		 */
		$('.sub_navi1 .navi1 .mbx1 > a').on('click',function(){
			if($('.sub_navi1 .bg1').css('display')==='none'){
				var idx_n=$(this).parent().parent().index();
				$('.sub_navi1 .navi1 .dp1').each(function(){
					if(idx_n!=$(this).index()){
						$(this).find('ul').slideUp(100);
					}
				});
				$(this).next().slideToggle(150);
			}else{
				$(this).next().slideToggle(150);
			}
		});
	},
	faqToggle: function(){
		/**
		 * @Summary 서브
		 * @Discription 서브 > 본문html > 자주묻는질문__토글
		 */
		var faq_data01a__o_toggle_idx=null;
		$('.faq_data01a .cel1 > dd > a').on('click',function(){
			var idx=$(this).parent().parent().parent().parent().index();
			if(faq_data01a__o_toggle_idx!=idx){
				if(faq_data01a__o_toggle_idx!=null){
					$('.faq_data01a > dl:eq('+faq_data01a__o_toggle_idx+') > dd').removeClass('on');
				}
				$('.faq_data01a > dl:eq('+idx+') > dd').addClass('on');
				faq_data01a__o_toggle_idx=idx;
			}else{
				$('.faq_data01a > dl:eq('+idx+') > dd').removeClass('on');
				faq_data01a__o_toggle_idx=null;
			}
		});
	}
};

const searchFaq = function(){
	const param = {
		cmsCd:$('#h_cmsCd').val(),
		pageIndex:$('#h_pageIndex').val(),
		pageSize:$('#h_pageSize').val(),
		startRow:$('#h_startRow').val(),
		searchKey:$('#searchKey').val(),
		searchValue:$('#searchValue').val()
	};	
	$.get('/data/faq/'+$('#h_cmsCd').val(), param, function(data){
		let cont = '';
		let size = data.resultList.length;
		if(size!=0){
			for(var i=0; i<size; i++){
				cont += '<dl>';
				cont += '<dt>';
				cont += '<dl class="cel1">';
				cont += '<dt><span>Q</span></dt>';
				cont += '<dd><a href="javascript:;">'+data.resultList[i].title+'</a></dd>';
				cont += '</dl>';
				cont += '</dt>';
				cont += '<dd>';
				cont += '<dl class="cel1">';
				cont += '<dt><span>A</span></dt>';
				cont += '<dd>';
				cont += '<pre style="font-family:NanumSquare,Noto Sans KR,tahoma,sans-serif,dotum,gulim !important">';
				cont += data.resultList[i].cont;
				cont += '</pre>';
				cont += '</dd>';
				cont += '</dl>';
				cont += '</dd>';
				cont += '</dl>';
			}
		}else{
			cont += '<dl>';
			cont += '<dt>';
			cont += '<dl>';
			cont += '<dd style="text-align:center;">등록된 자료가 없습니다.</dd>';
			cont += '</dl>';
			cont += '</dl>';
			cont += '</dd>';
			cont += '</dl>';
		}
		$('#faqData').empty().append(cont);
	}).done(function(data){
		paging(data, 'searchFaq()');   
	}).always(function(data){		
		addUIEvnet.faqToggle();
	});
};

const searchNtc = function(){	
	const param = {
		cmsCd:$('#h_cmsCd').val(),
		pageIndex:$('#h_pageIndex').val(),
		pageSize:$('#h_pageSize').val(),
		startRow:$('#h_startRow').val(),
		searchKey:$('#searchKey').val(),
		searchValue:$('#searchValue').val()
	};
	$.get('/data/ntc/'+$('#h_cmsCd').val(), param, function(data){		
		let cont = '';
		let size = data.resultList.length;	
		if(size!=0){
			for(var i=0; i<size; i++){
				if(i==0){
					cont += '<colgroup><col width="70px"/><col width="*"/><col width="150px"/><col width="110px"/><col width="70px"/></colgroup>';
					cont += '<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회</th></tr>';
				}				
				if(data.resultList[i].ntcYn === 'Y'){
					cont += '<tr class="noti">';
					cont += '<td class="no01">공지</td>';
				}else{
					cont += '<tr class="">';
					if($('#h_cmsCd').val() === '05061201'){
						cont += '<td class="no01">'+data.resultList[i].rownum+'</td>';
					}else{
						cont += '<td class="no01">'+data.resultList[i].brdNo+'</td>';
					}										
				}
				cont += '<td class="tit01 ali_l"><a onclick="ntcDetailPage(\''+ data.resultList[i].cmsCd +'\','+data.resultList[i].brdNo+');">'+data.resultList[i].title+'</a></td>';
				cont += '<td>'+data.resultList[i].regNm+'</td>';
				cont += '<td>'+data.resultList[i].regDt.year+'-'+lpad(data.resultList[i].regDt.monthValue,2,"0")+'-'+lpad(data.resultList[0].regDt.dayOfMonth,2,"0")+'</td>';				
				cont += '<td>'+data.resultList[i].hit+'</td>';
				cont += '</tr>';								
			}
		}else{
			cont += '<tr class="noti">';
			cont += '<td colspan="5">등록된 자료가 없습니다.</td>';
			cont += '</tr>';
		}		
		$('#ntcData').empty().append(cont);
	}).done(function(data){
		paging(data, 'searchNtc()');
	});
};

const ntcDetailPage = function(cmsCd,brdNo){
	const param = {
		cmsCd:cmsCd,
		brdNo:brdNo,
		mType:$('#h_mType').val()
	};	
	$.post('/data/board/hit/'+cmsCd+'/'+brdNo, param, function(data){		
		if(data>0){
			window.location.href='/board/'+cmsCd+'/'+brdNo;
		}		
	});		
};

const searchNtcDetailPage = function(){
	const param = {
		cmsCd:$('#h_cmsCd').val(),
		brdNo:$('#h_brdNo').val()
	};
	$.get('/data/ntc/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val(), param, function(data){						
		let cont1 = '';
		cont1 += '<colgroup><col width="100px"/><col width="*"/></colgroup>';
		cont1 += '<tr>';
		cont1 += '<th>제목</th>';
		cont1 += '<td>'+data.title+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>작성자</th>';
		cont1 += '<td>'+data.regNm+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>작성일</th>';
		cont1 += '<td>'+data.regDt.year+'-'+lpad(data.regDt.monthValue,2,"0")+'-'+lpad(data.regDt.dayOfMonth,2,"0")+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>조회수</th>';
		cont1 += '<td>'+data.hit+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>첨부파일</th>';		
		cont1 += '<td id="attach"></td>'
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<td colspan="2">';
		cont1 += '<div class="bbs_v_cont1a">'+data.cont+'</div>';
		cont1 += '</td>';
		cont1 += '</tr>';
		$('#ntcDetailData').empty().append(cont1);		
		let cont2 = '';		
		if(data.cmsCd.substr(0, 6) === '050612'){
			cont2 += '<a class="size_m2 btn_gray2" href="/emp/'+data.cmsCd+'">목록</a>';
		}else{
			cont2 += '<a class="size_m2 btn_gray2" href="/board/'+data.cmsCd+'">목록</a>';
		}		
		$('#ntcDetailBtn').empty().append(cont2);
		param.attachId = data.attachId;		
		$.get('/data/ntc/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val()+'/'+data.attachId, param, function(data){			
			let cont3 = '';
			for(var i=0;i<data.length;i++){								
				cont3 += '<a onclick="fileDownload(\''+data[i].attachId+'\','+data[i].attachNo+');">'+data[i].fileNm+'</a><br>';
				$("#attach").html(cont3);								
			}
		});
	});	
};

const searchBrd = function(){
	const param = {
		cmsCd:$('#h_cmsCd').val(),
		pageIndex:$('#h_pageIndex').val(),
		pageSize:$('#h_pageSize').val(),
		startRow:$('#h_startRow').val(),
		searchKey:$('#searchKey').val(),
		searchValue:$('#searchValue').val()
	};
	if($('#emptyDep1').text()) param.secYn = 'YN';	
	$.get('/data/brd/'+$('#h_cmsCd').val(), param, function(data){		
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

const brdDetailPage = function(cmsCd,brdNo,regId,secretYn){
	if(regId != $("#h_userId").val() && secretYn == 'N'){
		alert('비공개 글 입니다.');		
	}else{		
		const param = {
			cmsCd:cmsCd,
			brdNo:brdNo,
			mType:$('#h_mType').val()
		};		
		$.patch('/data/board/hit/'+cmsCd+'/'+brdNo, param, function(data){		
			if(data>0){
				window.location.href='/board/'+cmsCd+'/'+brdNo;
			}		
		});
	}			
};

const searchBrdDetailPage = function(){
	const param = {
		cmsCd:$('#h_cmsCd').val(),
		brdNo:$('#h_brdNo').val()
	};
	$.get('/data/brd/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val(), param, function(data){		
		let cont1 = '';
		cont1 += '<colgroup><col width="100px"/><col width="*"/></colgroup>';
		cont1 += '<tr>';
		cont1 += '<th>제목</th>';
		cont1 += '<td>'+data.title+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>작성자</th>';
		cont1 += '<td>'+data.regNm+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>작성일</th>';
		cont1 += '<td>'+data.regDt.year+'-'+lpad(data.regDt.monthValue,2,"0")+'-'+lpad(data.regDt.dayOfMonth,2,"0")+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>조회수</th>';
		cont1 += '<td>'+data.hit+'</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>첨부파일</th>';		
		cont1 += '<td id="attach"></td>'
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<td colspan="2">';
		cont1 += '<div class="bbs_v_cont1a">'+data.cont+'</div>';
		cont1 += '</td>';
		cont1 += '</tr>';
		$('#brdDetailData1').empty().append(cont1);
		param.upBrdNo = data.brdNo;
		$.get('/data/brd/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val(), param, function(data){			
			let cont2 = '';
			if(data){				
				cont2 += '<div class="bbs_v_rep1a">';
				cont2 += '<div class="data">';		
				cont2 += '<div class="info">';		
				cont2 += '답변내역<br>담당자 : '+data.regNm+'<br>';				
				cont2 += '부서 : '+data.regOffNm+'<br>';				
				cont2 += '전화번호 : '+data.regOffmTelno+'<br>';				
				cont2 += '답변일 : '+data.regDt.year+'-'+lpad(data.regDt.monthValue,2,"0")+'-'+lpad(data.regDt.dayOfMonth,2,"0")+'';				
				cont2 += '</div>';
				cont2 += '<div class="cont1">';
				cont2 += data.title+'<br>';
				cont2 += data.cont;
				cont2 += '</div>';
				cont2 += '</div>';
				cont2 += '</div>';				
			}
			$('#brdDetailData2').empty().append(cont2);
		});								
		let cont3 = '';		
		if(data.regId == $('#h_userId').val() && data.repYn == 'N'){
			cont3 += '<a class="size_m2 btn_red1" onclick="boardDelete(\''+data.cmsCd+'\','+data.brdNo+',\''+$("#h_mType").val()+'\');">삭제</a>';
			cont3 += '<a class="size_m2 btn_gray1" href="/board/update/'+data.cmsCd+'/'+data.brdNo+'">수정</a>';
		}
		if($('#h_pType').val() === 'MYB'){
			cont3 += '<a class="size_m2 btn_gray2" href="/mypage/myBoard">목록</a>';
		}else{
			cont3 += '<a class="size_m2 btn_gray2" href="/board/'+data.cmsCd+'">목록</a>';
		}
		
		$('#brdDetailBtn').empty().append(cont3);				
	}).done(function(data){
		const param = {
			cmsCd:$('#h_cmsCd').val(),
			brdNo:$('#h_brdNo').val(),
			attachId:data.attachId
		};				
		$.get('/data/ntc/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val()+'/'+data.attachId, param, function(data){			
			let cont4 = '';
			for(var i=0;i<data.length;i++){								
				cont4 += '<a onclick="fileDownload(\''+data[i].attachId+'\','+data[i].attachNo+');">'+data[i].fileNm+'</a>&nbsp&nbsp';												
			}			
			$("#attach").html(cont4);
		});
	});	
};

const searchBrdWritePage = function(){	
	let cont1 = '';
	cont1 += '<colgroup><col width="100px"/><col width="*"/></colgroup>';
	cont1 += '<tr>';
	cont1 += '<th>제목</th>';
	cont1 += '<td><input type="text" id="title" class="inputbox_01a wid_100p"></td>';
	cont1 += '</tr>';
	cont1 += '<tr>';
	cont1 += '<th>작성자</th>';
	cont1 += '<td>'+$("#h_userNm").val()+'<input type="hidden" id="regId" value="'+$("#h_userId").val()+'"></td>';
	cont1 += '</tr>';
	cont1 += '<tr>';
	cont1 += '<th>공개여부</th>';
	cont1 += '<td>';
	cont1 += '<input type="radio" name="secretYn" value="Y" checked> <label class="margin_r10">공개</label>';
	cont1 += '<input type="radio" name="secretYn" value="N"> <label class="margin_r10">비공개</label>';
	cont1 += '</td>';
	cont1 += '</tr>';
	cont1 += '<tr>';
	cont1 += '<th>첨부파일</th>';		
	cont1 += '<td><input type="file" id="file" name="file" multiple></td>'
	cont1 += '</tr>';
	cont1 += '<tr>';
	cont1 += '<td colspan="2">';
	cont1 += '<textarea id="cont" class="inputbox_01a wid_100p"></textarea>';
	cont1 += '</td>';
	cont1 += '</tr>';
	$('#brdWriteData').empty().append(cont1);
	$('body').append($('<script>CKEDITOR.replace("cont",{filebrowserUploadUrl:"/data/image/cheditorImgUpload",height:400});</script>'));
	let cont2 = '';
	cont2 += '<a class="size_m2 btn_gray1" href="/board/'+$('#h_cmsCd').val()+'">목록</a>';
	cont2 += '<a class="size_m2 btn_gray2" onclick="brdSaveBtn();">저장</a>';
	$('#brdWriteBtn').empty().append(cont2);	
};

const brdSaveBtn = function(){	
	var formData=new FormData();
	var inputFile=$("#file");
	var files=inputFile[0].files;						
	formData.append("cmsCd",$('#h_cmsCd').val());		
	formData.append("title",$("#title").val());
	formData.append("cont",CKEDITOR.instances['cont'].getData());
	formData.append("secretYn",$('input[name="secretYn"]:checked').val());
	formData.append("regId",$("#regId").val());							
	formData.append("mType",$("#h_mType").val());							
	for(var i=0;i<files.length;i++){
		formData.append("files",files[i]);
	}
	$.ajax({
        type: "post",
        enctype: 'multipart/form-data',
        url:'/data/brd/'+$('#h_cmsCd').val()+'',
        data: formData,
        processData: false,
        contentType: false,       
        success: function (data) {
        	alert(data.result);
        	window.location.href = '/board/'+$('#h_cmsCd').val()+'';
        },        
        error: function (jqXHR,textStatus,errorThrown) { 
        	console.log(jqXHR,textStatus,errorThrown);
        }
    });
}

const searchBrdUpdatePage = function(){		
	const param = {
		cmsCd:$('#h_cmsCd').val(),
		brdNo:$('#h_brdNo').val()
	};
	$.get('/data/brd/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val(), param, function(data){		
		let cont1 = '';
		cont1 += '<colgroup><col width="100px"/><col width="*"/></colgroup>';
		cont1 += '<tr>';
		cont1 += '<th>제목</th>';
		cont1 += '<td><input type="text" id="title" value="'+data.title+'" class="inputbox_01a wid_100p"></td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>작성자</th>';
		cont1 += '<td>'+data.regNm+'<input type="hidden" id="regId" value="'+data.regId+'"></td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>공개여부</th>';
		cont1 += '<td>';
		if(data.secretYn == 'Y'){
			cont1 += '<input type="radio" name="secretYn" value="Y" checked> <label class="margin_r10">공개</label>';
			cont1 += '<input type="radio" name="secretYn" value="N"> <label class="margin_r10">비공개</label>';
		}else{
			cont1 += '<input type="radio" name="secretYn" value="Y"> <label class="margin_r10">공개</label>';
			cont1 += '<input type="radio" name="secretYn" value="N" checked> <label class="margin_r10">비공개</label>';
		}			
		cont1 += '</td>';
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<th>첨부파일</th>';		
		cont1 += '<td><input type="file" id="file" name="file" multiple><div id="attach"></div><input type="hidden" id="attachId" value="'+data.attachId+'"></td>';						
		cont1 += '</tr>';
		cont1 += '<tr>';
		cont1 += '<td colspan="2">';
		cont1 += '<textarea id="cont" class="inputbox_01a wid_100p">'+data.cont+'</textarea>';
		cont1 += '</td>';
		cont1 += '</tr>';
		$('#brdUpdateData').empty().append(cont1);
		$('body').append($('<script>CKEDITOR.replace("cont",{filebrowserUploadUrl:"/data/image/cheditorImgUpload",height:400});</script>'));
		let cont2 = '';
		cont2 += '<a class="size_m2 btn_gray1" href="/board/'+$('#h_cmsCd').val()+'">목록</a>';
		cont2 += '<a class="size_m2 btn_gray2" onclick="brdUpdateBtn();">저장</a>';
		$('#brdUpdateBtn').empty().append(cont2);		
	}).done(function(data){
		$.get('/data/ntc/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val()+'/'+data.attachId, param, function(data){			
			let cont3 = '';
			for(var i=0;i<data.length;i++){								
				cont3 += '<a onclick="fileDownload(\''+data[i].attachId+'\','+data[i].attachNo+');">'+data[i].fileNm+'</a><br>';																
			}
			if(data.length>0){
				cont3 += '<input type="checkbox" id="checkboxYn" value="Y"> 파일삭제';
			}
			$("#attach").html(cont3);
		});
	});	
};

const brdUpdateBtn = function(){	
	var formData=new FormData();
	var inputFile=$("#file");
	var files=inputFile[0].files;						
	formData.append("cmsCd",$('#h_cmsCd').val());		
	formData.append("brdNo",$('#h_brdNo').val());		
	formData.append("mType",$('#h_mType').val());		
	formData.append("regId",$('#regId').val());		
	formData.append("title",$("#title").val());
	formData.append("cont",CKEDITOR.instances['cont'].getData());
	formData.append("secretYn",$('input[name="secretYn"]:checked').val());																
	formData.append("checkboxYn",$('#checkboxYn:checked').val());	
	formData.append("attachId",$('#attachId').val());	
	for(var i=0;i<files.length;i++){
		formData.append("files",files[i]);
	}	
	$.ajax({
        type: "post",
        enctype: 'multipart/form-data',
        url:'/data/brd/'+$('#h_cmsCd').val()+'/'+$('#h_brdNo').val()+'',
        data: formData,
        processData: false,
        contentType: false,               
        success: function (data) {
        	alert(data.result);
        	window.location.href = '/board/'+$('#h_cmsCd').val()+'';
        },        
        error: function (jqXHR,textStatus,errorThrown) { 
        	console.log(jqXHR,textStatus,errorThrown);
        }
    });
}

const boardDelete = function(cmsCd,brdNo,mType){
	if(confirm("정말삭제하시겠습니까?")){		
		const param = {
			cmsCd:cmsCd,
			brdNo:brdNo,
			mType:$('#h_mType').val()
		};				
		$.ajax({
	        type: "delete", 	        
	        url:'/data/board/'+cmsCd+'/'+brdNo+'',
	        data: param,	                       
	        success: function (data) {
	        	alert(data.result);
	        	window.location.href = '/board/'+$('#h_cmsCd').val()+'';
	        },        
	        error: function (jqXHR,textStatus,errorThrown) { 
	        	console.log(jqXHR,textStatus,errorThrown);
	        }
	    });
	}else{
	    return;
	}		
};
const fileDownload = function(attachId, attachNo){
	window.location.href = '/data/file/board/'+attachId+'/'+attachNo;
}
const searchKeyword = function(){			
	$.get('/data/board/search/'+$('#searchValue').val(), {}, function(data){
		let cont = '';
		let count = 0;
		if(data.length>0){			
			cont += '<div class="clbx">';
			cont += '<ul class="tab_ty1_cnt_4a">';
			for(let i=0;i<data.length;i++){
				if(data[i].mtype === 'FAQ'){
					count += data[i].count;
				}else{
					if(i === 0){
						cont+='<li id="'+data[i].cmsCd+'" class="on"><a onclick="searchMenu(\''+data[i].mtype+'\',\''+data[i].cmsCd+'\')">'+data[i].cmsNm+'('+data[i].count+')</a></li>';
					}else{
						cont+='<li id="'+data[i].cmsCd+'" class=""><a onclick="searchMenu(\''+data[i].mtype+'\',\''+data[i].cmsCd+'\')">'+data[i].cmsNm+'('+data[i].count+')</a></li>';
					}				
				}
			}		
			if(count>0) cont+='<li id="FAQ" class=""><a onclick="searchMenu(\'FAQ\',\'ALL\')">FAQ('+count+')</a></li>';
			cont += '</ul>';
			cont += '</div>';
			$('#subCon').append(cont);
			if(data[0].mtype === 'FAQ'){
				$('#h_cmsCd').val('ALL');
				$('#h_mType').val('FAQ');
				$('#FAQ').addClass('on');
			}else{
				$('#h_cmsCd').val(data[0].cmsCd);
				$('#h_mType').val(data[0].mtype);
			}
		}else{			
			cont += '<div class="sub_greeting01">';
			cont += '<div class="ment1">';
			cont += '<div class="bx">';
			cont += '<div class="fontsize_2dot00 lineheight_1dot8 t1">';
			cont += '<div class="sub_greeting01">';
			cont += '검색된 내용이 없습니다.';
			cont += '</div>';
			cont += '</div>';
			cont += '</div>';
			cont += '</br>';
			cont += '</br>';
			cont += '</div>';
			$('#subCon').append(cont);						
		}						
	}).done(function(data){		
		if(data){
			if(data[0].mtype === 'NTC'){
				$('#boardCon').append(ntcHtml);
				$('.sch_box_01').remove();
				searchNtc();
			}else if(data[0].mtype === 'BRD'){
				$('#boardCon').append(brdHtml);
				$('.sch_box_01').remove();						
				searchBrd();			
			}else if(data[0].mtype === 'FAQ'){
				$('#boardCon').append(faqHtml);
				$('.sch_box_01').remove();				
				searchFaq();
			}
		}		
	});		
}

const searchMenu = function(mtype,cmsCd){	
	$('#h_cmsCd').val(cmsCd);
	$('#h_mType').val(mtype);
	$('#h_pageIndex').val('1');
	$('.sub_board_l01').remove();
	$('.tab_ty1_cnt_4a>li').removeClass('on');
	$('#'+cmsCd).addClass('on');	
	if(mtype === 'NTC'){						
		$('#boardCon').append(ntcHtml);
		$('.sch_box_01').remove();				
		searchNtc();		
	}else if(mtype === 'BRD'){		
		$('#boardCon').append(brdHtml);
		$('.sch_box_01').remove();				
		searchBrd();			
	}else if(mtype === 'FAQ'){				
		$('#boardCon').append(faqHtml);
		$('.sch_box_01').remove();				
		searchFaq();
	}
}

const searchBoard = function(){		
	if(window.event.keyCode == 13 || !window.event.keyCode){		
		let mtype = $('#h_mType').val();		
		$('#h_pageIndex').val('1');
		if(mtype === 'NTC'){												
			searchNtc();		
		}else if(mtype === 'BRD'){							
			searchBrd();			
		}else if(mtype === 'FAQ'){										
			searchFaq();
		}
	}	
}

const priceDetailPage = function(){		
		
}
