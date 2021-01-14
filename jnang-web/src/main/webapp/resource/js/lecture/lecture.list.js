/**
* @projectDescription lecture.list.js
*
* @author RGJ
* @version 1.22
*/

'use strict'
$(function(){
	setMenuTitle('bg_01', '시설 통합예약', '수강신청', true);
	setCondition();
	addOnChangeEvent('sb1');
	addOnChangeEvent('sb2');
	addOnChangeEvent('sb3');
});

var checkComCd = function(val){
	var isExist = false;
	var comcd;
	$('#sb1 option').each(function(){
		if(this.value === val) isExist = true;
	});
	if(isExist){
		$('#sb1').val(val);
		comcd = val;
	}else{
		$('#sb1').val('all');
		comcd = 'all';
	};
	return comcd;
};

var setCondition = function(type){
	switch(type) {
	case 'sb1':
		$.get('/data/lecture/sb2/'+$('#sb1').val(), {}, function(data2){
			writeCondition('sb2', data2);
		}).done(function(data2){
			$.get('/data/lecture/sb3/'+$('#sb1').val()+'/'+$('#sb2').val(), {}, function(data3){
				writeCondition('sb3', data3);
			}).done(function(){
				searchLectureList();
			});
		});
		break;
	case 'sb2':
		$.get('/data/lecture/sb3/'+$('#sb1').val()+'/'+$('#sb2').val(), {}, function(data3){
			writeCondition('sb3', data3);
		}).done(function(){
			searchLectureList();
		});
		break;
	case 'sb3':
		searchLectureList();
		break;
	default:
		$.get('/data/lecture/sb1', {}, function(data1){
			writeCondition('sb1', data1);
		}).done(function(data1){
			var comcd = checkComCd($('#h_sb1').val()!=''?$('#h_sb1').val():'all');
			$.get('/data/lecture/sb2/'+comcd, {}, function(data2){
				writeCondition('sb2', data2);
			}).done(function(data2){
				var cd = $('#sb2').val();
				$.get('/data/lecture/sb3/'+comcd+'/'+cd, {}, function(data3){
					writeCondition('sb3', data3);
				}).done(function(){
					searchLectureList();
				});
			});
		});
		break;
	}
};

var writeCondition = function(type, data){
	var commonHtml = '<option value=\'all\'>--- 전체 ---</option>';
	switch(type) {
	case 'sb1':
		var sb1Html = commonHtml;
		for(var i=0; i<data.length; i++){
	
			sb1Html += '<option value='+ data[i].comcd +'>'+data[i].comnm+'</option>';	
	
		}
		$('#sb1').empty().append(sb1Html);
		break;
	case 'sb2':
		var sb2Html = commonHtml;
		for(var i=0; i<data.length; i++){
	
			sb2Html += '<option value='+ data[i].cd +'>'+data[i].cdNm+'</option>';
			
		};
		$('#sb2').empty().append(sb2Html).val('all');
		break;
	case 'sb3':
		var sb3Html = commonHtml;
    
		for(var i=0; i<data.length; i++){

				sb3Html += '<option value=' + data[i].cd + '>' +data[i].cdNm+'</option>';
		
		}
		
		$('#sb3').empty().append(sb3Html).val('all');
		break;
	default:
		alert('예기치 않은 오류 발생');
		return;
	}
};

var addOnChangeEvent = function(id){
	document.getElementById(id).addEventListener('change',function(e){
		$('#h_pageIndex').val('1');
		setCondition(id);
	});
};

var searchLectureList = function(){
	var param = {
		comcd:$('#sb1').val(), sportsCd:$('#sb2').val(), msportsCd:$('#sb3').val(),
		pageIndex:$('#h_pageIndex').val(), pageSize:$('#h_pageSize').val(), startRow:$('#h_startRow').val()
	};
	$.get('/data/lecture/lectureList/', param, function(data){
		console.log(data.resultList.length);
		var tableDesc = '', tableHead = '', tableBody = '', reception = '', lectureBtn = '';
		//tableDesc
		tableDesc = '<caption>수강신청</caption>';
		tableDesc += '<colgroup>';
		tableDesc += '<col width="170px"/>';
		tableDesc += '<col width="170px"/>';
		tableDesc += '<col width="130px"/>';
		tableDesc += '<col width="140px"/>';
		tableDesc += '<col width="150px"/>';
		tableDesc += '<col width="140px"/>';
		tableDesc += '<col width="140px"/>';
		tableDesc += '<col width="140px"/>';
		tableDesc += '<col width="140px"/>';
		tableDesc += '<col width="140px"/>';
		tableDesc += '</colgroup>';
		//tableHead
		tableHead += '<tr>';
		tableHead += '<th>센터명</th>';
		tableHead += '<th>종목</th>';
		tableHead += '<th>강좌명</th>';
		tableHead += '<th>시간/요일</th>';
		tableHead += '<th>대 상</th>';
	//	tableHead += '<th colspan=\'2\'>수강료(원)</th>';
		tableHead += '<th>수강료(원)</th>'
		tableHead += '<th>잔여인원</th>';
		tableHead += '<th>신 청</th>';
		tableHead += '<th>상세보기</th>';
		tableHead += '</tr>';
		//tableBody
		
	
			
		
		if(data.resultList.length > 0){
			var prgTot = 0;
			
           

		
	
			for(var i=0; i<data.resultList.length; i++){
				if(data.resultList[i].prgCount != 0) prgTot++;
				
			};
			if(prgTot != 0){
				for(var i=0; i<data.resultList.length; i++){
					if(data.resultList[i].prgCount === 1){	
		
						
						tableBody += '<tr>';
				
							
							
								if(data.resultList[i].comcd === 'JUNGNANG01'){
							   tableBody += '<td >'+"중랑구민체육센터"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG02'){
							   tableBody += '<td >'+"중랑문화체육관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG03'){
							   tableBody += '<td >'+"면목2동체육관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG05'){
							   tableBody += '<td >'+"묵동다목적체육관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG06'){
							   tableBody += '<td >'+"중랑구립정보도서관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG07'){
							   tableBody += '<td >'+"중랑구립테니스장"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG11'){
							   tableBody += '<td >'+"용마폭포공원테니스장"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG12'){
							   tableBody += '<td >'+"서울중랑워터파크"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG04'){
							   tableBody += '<td>'+"신내다목적체육관"+'</td>';
						      }


		  if( data.resultList[i].sportsCd === '01' && data.resultList[i].comcd === 'JUNGNANG01' ){			
							 tableBody += '<td >'+"수영"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '01' && data.resultList[i].comcd === 'JUNGNANG02' ){			
							 tableBody += '<td >'+"수영"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '02' ){				
							 tableBody += '<td >'+"헬스"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '03' && data.resultList[i].comcd === 'JUNGNANG02'  ){				
							 tableBody += '<td  >'+"생활체육"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '01' && data.resultList[i].comcd === 'JUNGNANG03'  ){				
							 tableBody += '<td >'+"생활체육"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '05' && data.resultList[i].comcd === 'JUNGNANG03'  ){				
							 tableBody += '<td >'+"패키지(헬스+생활체육)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '03' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td  >'+"(1개월)체육/문화/영어"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '06' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td  >'+"패키지반"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '07' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td  >'+"방학특강"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '11' && data.resultList[i].comcd === 'JUNGNANG02'  ){				
							 tableBody += '<td  >'+"패키지"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '17' && data.resultList[i].comcd === 'JUNGNANG02'  ){				
							 tableBody += '<td  >'+"피아노"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '11' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td  >'+"강습반(묵동)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '08' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td>'+"강습반(신내)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '09' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td>'+"봉화산클럽(목동)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '17' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td >'+"방학특강(꿈나무)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '10' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td>'+"월정기권(묵동)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '07' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td >'+"봉화산클럽(신내)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '05' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td >'+"아기스포츠단"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '18' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td >'+"피아노교실"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '04' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td >'+"(3개월)체육/문화/수영"+'</td>';	
								}
					
						tableBody += '<td >'+data.resultList[i].classNm+'</td>';
					
		
					    
		
		
						if(data.resultList[i].comcd === 'JUNGNANG01' && data.resultList[i].sportsCd === '02'){
							//중랑구민체육센터 헬스 시간 변경
							tableBody += '<td>'+data.resultList[i].trainTimeNm+'<br>(토 06:00~20:50)</td>';
						}else if(data.resultList[i].comcd === 'JUNGNANG03' && data.resultList[i].sportsCd === '02'){
							tableBody += '<td>'+data.resultList[i].trainTimeNm+'<br>(토 06:00~20:50)</td>';
						}else{
								tableBody += '<td>'+data.resultList[i].trainTimeNm+'<br>'+data.resultList[i].trainDayNm+'</td>';
						}
										tableBody += '<td>'+data.resultList[i].classObj+'</td>';
						tableBody += '<td>'+data.resultList[i].programItem[0].costAmt.toLocaleString(undefined, {maximumFractionDigits: 5})+'</td>';
						
				        tableBody += '<td >'+data.resultList[i].remainCapa+' 명</td>';
						
                    		if(data.resultList[i].remainCapa === 0){
									tableBody += '<td><a class="size_s2 btn_pink_redWrite">접수마감</a></td>';
							}else{
														tableBody += '<td ><a class="size_s3 btn_pink_blueWrite" onclick="addBasket1(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[0].itemCd+'\');">수강신청</a></td>';
							}
							
							
						tableBody += '<td ><a class="size_s2 btn_blue2" onclick="searchLectureDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[0].itemCd+'\');">상세보기</a></td>';
						tableBody += '</tr>';
				
						
					
					}else{
						var prgCount = data.resultList[i].prgCount;
						
					
						for(var j=0; j<prgCount; j++){
					     
							if(j===0){
							
								tableBody += '<tr>';
							
								
								
								if(data.resultList[i].comcd === 'JUNGNANG01'){
							   tableBody += '<td rowspan='+prgCount+' >'+"중랑구민체육센터"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG02'){
							   tableBody += '<td rowspan='+prgCount+' >'+"중랑문화체육관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG03'){
							   tableBody += '<td rowspan='+prgCount+' >'+"면목2동체육관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG05'){
							   tableBody += '<td rowspan='+prgCount+' >'+"묵동다목적체육관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG06'){
							   tableBody += '<td rowspan='+prgCount+' >'+"중랑구립정보도서관"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG07'){
							   tableBody += '<td rowspan='+prgCount+' >'+"중랑구립테니스장"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG11'){
							   tableBody += '<td rowspan='+prgCount+' >'+"용마폭포공원테니스장"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG12'){
							   tableBody += '<td rowspan='+prgCount+' >'+"서울중랑워터파크"+'</td>';
						      }else if(data.resultList[i].comcd === 'JUNGNANG04'){
							   tableBody += '<td  rowspan='+prgCount+'>'+"신내다목적체육관"+'</td>';
						      }


			  if( data.resultList[i].sportsCd === '01' && data.resultList[i].comcd === 'JUNGNANG01' ){			
							 tableBody += '<td rowspan='+prgCount+' >'+"수영"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '01' && data.resultList[i].comcd === 'JUNGNANG02' ){			
							 tableBody += '<td rowspan='+prgCount+' >'+"수영"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '02' ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"헬스"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '03' && data.resultList[i].comcd === 'JUNGNANG02'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"생활체육"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '01' && data.resultList[i].comcd === 'JUNGNANG03'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"생활체육"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '05' && data.resultList[i].comcd === 'JUNGNANG03'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"패키지(헬스+생활체육)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '03' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"(1개월)체육/문화/영어"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '06' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"패키지반"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '07' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"방학특강"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '11' && data.resultList[i].comcd === 'JUNGNANG02'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"패키지"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '17' && data.resultList[i].comcd === 'JUNGNANG02'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"피아노"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '11' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td rowspan='+prgCount+'>'+"강습반(묵동)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '08' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"강습반(신내)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '09' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"봉화산클럽(목동)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '17' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"방학특강(꿈나무)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '10' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"월정기권(묵동)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '07' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"봉화산클럽(신내)"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '06' && data.resultList[i].comcd === 'JUNGNANG04'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"전체"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '05' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"아기스포츠단"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '04' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"(3개월)체육/문화/수영"+'</td>';	
								}else  if( data.resultList[i].sportsCd === '18' && data.resultList[i].comcd === 'JUNGNANG01'  ){				
							 tableBody += '<td rowspan='+prgCount+' >'+"피아노교실"+'</td>';	
								}
								
					
								tableBody += '<td rowspan='+prgCount+' >'+data.resultList[i].classNm+'</td>';
						     
								tableBody += '<td rowspan='+prgCount+'>'+data.resultList[i].trainTimeNm+'<br>'+data.resultList[i].trainDayNm+'</td>'
								
					        			tableBody += '<td rowspan='+prgCount+'>'+data.resultList[i].classObj+'</td>';
				
								tableBody += '<td>'+data.resultList[i].programItem[j].costAmt.toLocaleString(undefined, {maximumFractionDigits: 5})+'</td>';
								
								tableBody += '<td rowspan='+prgCount+'>'+data.resultList[i].remainCapa+' 명</td>';
							if(data.resultList[i].remainCapa === 0){
									tableBody += '<td><a class="size_s2 btn_pink_redWrite">접수마감</a></td>';
							}else{
								tableBody += '<td><a class="size_s2 btn_pink_blueWrite" onclick="addBasket1(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[j].itemCd+'\');">수강신청</a></td>';
							}
			
					
								tableBody += '<td><a class="size_s2 btn_blue2" onclick="searchLectureDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[0].itemCd+'\');">상세보기</a></td>';
								
								
								tableBody += '</tr>';
							}else{
								tableBody += '<tr>';   
				                
								tableBody += '<td>'+data.resultList[i].programItem[j].costAmt.toLocaleString(undefined, {maximumFractionDigits: 5})+'</td>';
								
								
						if(data.resultList[i].remainCapa === 0){
									tableBody += '<td><a class="size_s2 btn_pink_redWrite">접수마감</a></td>';
							}else{
								tableBody += '<td><a class="size_s2 btn_pink_blueWrite" onclick="addBasket1(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[j].itemCd+'\');">수강신청</a></td>';
							}		
								tableBody += '<td><a class="size_s2 btn_blue2" onclick="searchLectureDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[j].itemCd+'\');">상세보기</a></td>';
						
							}
									tableBody += '</tr>';
						}
					}
				}
			}else{
				tableBody += '<tr>';
				tableBody += '<td colspan="7">프로그램이 없습니다.</td>';
				tableBody += '</tr>';
			}
		}else{
			tableBody += '<tr>';
			tableBody += '<td colspan="7">프로그램이 없습니다.</td>';
			tableBody += '</tr>';
		}
	
		$('#lectureTable').empty().append(tableDesc+tableHead+tableBody);
		if($('#sb2').val() === 'all' || data.resultList.length === 0){
			reception += '<ul class="ul_hyphen1a10">';
			/*reception += '<li><span class="fc_red1">수강료 할인은 웹상에서 적용이 되지 않습니다.</span></li>';*/
			reception += '<li>개강 이후의 접수는 마감되지 않은 강좌에 한합니다. (10일 이전에 신청한 강좌는 당월강좌로 접수됩니다.)</li>';
			reception += '<li>개강 이후 접수한 건에 대한 환불은 개강일 기준으로 정산됩니다.</li>';
			reception += '</ul>';
		}else{
			reception += '<ul class="ul_hyphen1a10">';
			reception += '<li><span class="fc_red1">재수강 신청 기간은 '+dateUtil.addHyphen(data.resultList[0].grpcd.repSdate)+' 부터 '+dateUtil.addHyphen(data.resultList[0].grpcd.repEdate)+' 까지 입니다.(마이페이지에서 재수강 신청하세요)</span></li>';
			reception += '<li><span class="fc_blue1">신규 접수기간은 '+dateUtil.addHyphen(data.resultList[0].grpcd.recSdate)+' 부터 '+dateUtil.addHyphen(data.resultList[0].grpcd.recEdate)+' 까지 입니다.</span></li>';
			/*reception += '<li><span class="fc_red1">수강료 할인은 웹상에서 적용이 되지 않습니다.</span></li>';*/
			reception += '<li>개강 이후의 접수는 마감되지 않은 강좌에 한합니다. (10일 이전에 신청한 강좌는 당월강좌로 접수됩니다.)</li>';
			reception += '<li>개강 이후 접수한 건에 대한 환불은 개강일 기준으로 정산됩니다.</li>';
			reception += '</ul>';
		}
		$('#reception').empty().append(reception);
	}).done(function(data){
		paging(data, 'searchLectureList()');
	});
};

var searchLectureDetail = function(comcd, classCd, itemCd){
	var lecture = { comcd:comcd, classCd:classCd, itemCd:itemCd };
	$.get('/data/encode/text', {text:JSON.stringify(lecture)}, function(data){
		localStorage.setItem('lecture', data);
	}).done(function(){
		window.location.href='/lecture/view';
	});
};


var needAuthorize = {
	user: function(){
		alert('로그인 후 이용해 주세요.');
		return;
	},
	card: function(){
		alert('회원카드를 발급받은 회원만 신청이 가능합니다.');
		//return;
	}
};


var addBasket1 = function(comcd, classCd, itemCd){
	var lecture = { comcd:comcd, classCd:classCd, itemCd:itemCd };
	$.get('/data/encode/text', {text:JSON.stringify(lecture)}, function(data){
		localStorage.setItem('lecture', data);
	}).done(function(){
		window.location.href='/lecture/view1';
	});
};





