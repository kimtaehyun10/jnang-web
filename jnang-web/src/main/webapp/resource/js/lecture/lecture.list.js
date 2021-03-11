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
			console.log(data[i].comcd, data[i].comnm); 
			if(data[i].comcd == "JUNGNANG06"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG08"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG18"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG10"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG14"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG15"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG16"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG17"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG13"){
				sb1Html += '';
			}else if(data[i].comcd == "JUNGNANG09"){
				sb1Html += '';
			}else{
			sb1Html += '<option value='+ data[i].comcd +'>'+data[i].comnm+'</option>';	
			}						
	
		}
		$('#sb1').empty().append(sb1Html);
		break;
	case 'sb2':
		var sb2Html = commonHtml;
		for(var i=0; i<data.length; i++){
			if(data[i].item2 === 'Y'){ //웹표시 여부
				sb2Html += '<option value='+ data[i].cd +'>'+data[i].cdNm+'</option>';
			}						
		};
		$('#sb2').empty().append(sb2Html).val('all');
		break;
	case 'sb3':
		var sb3Html = commonHtml;
    
		for(var i=0; i<data.length; i++){
			if(data[i].item2 === 'Y'){ //웹표시 여부
				sb3Html += '<option value=' + data[i].cd + '>' +data[i].cdNm+'</option>';
			}					
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
		
		var tableDesc = '', tableHead = '', tableBody = '', reception = '', lectureBtn = '';
		//tableDesc
		tableDesc = '<caption>수강신청</caption>';
		tableDesc += '<colgroup>';
		tableDesc += '<col width="170px"/>';
		tableDesc += '<col width="110px"/>';
		tableDesc += '<col width="130px"/>';
		tableDesc += '<col width="130px"/>';
		tableDesc += '<col width="130px"/>';
		tableDesc += '<col width="120px"/>';
		tableDesc += '<col width="130px"/>';
		tableDesc += '<col width="140px"/>';
		tableDesc += '</colgroup>';
		//tableHead
		tableHead += '<tr>';
		tableHead += '<th>센터명</th>';
		tableHead += '<th>종목</th>';
		tableHead += '<th>강좌명</th>';
		tableHead += '<th>시간/요일</th>';
		tableHead += '<th colspan=\'2\'>대상/수강료(원)</th>';
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
						tableBody += '<td >'+data.resultList[i].comnm+'</td>';		  
						tableBody += '<td>'+data.resultList[i].sportsCdNm+'</td>'
						tableBody += '<td >'+data.resultList[i].classNm+'</td>';						
						//토요일 시간 변경 (임시로 기타항목에 들어감 추후에 개발해야함)
						tableBody += '<td>'+data.resultList[i].trainTimeNm+'<br>'+data.resultList[i].trainDayNm+'</td>';
						tableBody += '<td>'+data.resultList[i].programItem[0].itemNm+'</td>';
										
						tableBody += '<td>'+data.resultList[i].programItem[0].costAmt.toLocaleString(undefined, {maximumFractionDigits: 5})+'</td>';
						
						
				       	if(data.resultList[i].webCapa-data.resultList[i].webUser === 0 || data.resultList[i].webCapa-data.resultList[i].webUser < 0){
									tableBody += '<td>'+0+' 명</td>';
								}else{
									tableBody += '<td>'+(data.resultList[i].webCapa-data.resultList[i].webUser)+' 명</td>';
								}
						
                    		if(data.resultList[i].webCapa-data.resultList[i].webUser === 0 || data.resultList[i].webCapa-data.resultList[i].webUser < 0){
									tableBody += '<td><a class="size_s2 btn_pink_redWrite">접수마감</a></td>';                    				
							}else{
									tableBody += '<td ><a class="size_s3 btn_pink_blueWrite" onclick="lecturePaymentDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[0].itemCd+'\');">수강신청</a></td>';
							}
							
							
						tableBody += '<td ><a class="size_s2 btn_blue2" onclick="searchLectureDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[0].itemCd+'\');">상세보기</a></td>';
						tableBody += '</tr>';
				
						
					
					}else{
						var prgCount = data.resultList[i].prgCount;
						
					
						for(var j=0; j<prgCount; j++){
					     
							if(j===0){
							
								tableBody += '<tr>';															
								tableBody += '<td rowspan='+prgCount+'>'+data.resultList[i].comnm+'</td>';							
								tableBody += '<td rowspan='+prgCount+'>'+data.resultList[i].sportsCdNm+'</td>'			  					
								tableBody += '<td rowspan='+prgCount+' >'+data.resultList[i].classNm+'</td>';		
								
								tableBody += '<td rowspan='+prgCount+'>'+data.resultList[i].trainTimeNm+'<br>'+data.resultList[i].trainDayNm+'</td>'
								
					        	tableBody += '<td>'+data.resultList[i].programItem[j].itemNm+'</td>';				
								tableBody += '<td>'+data.resultList[i].programItem[j].costAmt.toLocaleString(undefined, {maximumFractionDigits: 5})+'</td>';
								
								if(data.resultList[i].webCapa-data.resultList[i].webUser === 0 || data.resultList[i].webCapa-data.resultList[i].webUser < 0){
									tableBody += '<td rowspan='+prgCount+'>'+0+' 명</td>';
								}else{
									tableBody += '<td rowspan='+prgCount+'>'+(data.resultList[i].webCapa-data.resultList[i].webUser)+' 명</td>';
								}
								
							if(data.resultList[i].webCapa-data.resultList[i].webUser === 0 || data.resultList[i].webCapa-data.resultList[i].webUser < 0){
									tableBody += '<td><a class="size_s2 btn_pink_redWrite">접수마감</a></td>';
							}else{
								tableBody += '<td><a class="size_s2 btn_pink_blueWrite" onclick="lecturePaymentDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[j].itemCd+'\');">수강신청</a></td>';
							}
					
								tableBody += '<td><a class="size_s2 btn_blue2" onclick="searchLectureDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[0].itemCd+'\');">상세보기</a></td>';
								tableBody += '</tr>';
							}else{
								tableBody += '<tr>';   
				                tableBody += '<td>'+data.resultList[i].programItem[j].itemNm+'</td>';
								tableBody += '<td>'+data.resultList[i].programItem[j].costAmt.toLocaleString(undefined, {maximumFractionDigits: 5})+'</td>';
								
								
						if(data.resultList[i].webCapa-data.resultList[i].webUser === 0 || data.resultList[i].webCapa-data.resultList[i].webUser < 0){
									tableBody += '<td><a class="size_s2 btn_pink_redWrite">접수마감</a></td>';
							}else{
								tableBody += '<td><a class="size_s2 btn_pink_blueWrite" onclick="lecturePaymentDetail(\''+data.resultList[i].comcd+'\'\, \''+data.resultList[i].classCd+'\'\, \''+data.resultList[i].programItem[j].itemCd+'\');">수강신청</a></td>';
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


var lectDate = { }; //신청할 강좌 시작종료값 임시 저장
var lecturePaymentDetail = function(comcd, classCd, itemCd){
	
	/*var lecture = { comcd:comcd, classCd:classCd, itemCd:itemCd };	
	$.get('/data/encode/text', {text:JSON.stringify(lecture)}, function(data){
		localStorage.setItem('lecture', data);
	}).done(function(){
		window.location.href='/lecture/lecturePaymentDetail';
	});*/
	
	/*
	 	홈페이지  강좌 신청 접수기간
		메인 체육센터3군데 , 재등록 11~23일 , 신규 : 24~다음달10일
		신대 다목적,  재등록 18~24일 , 신규 : 25~다음달07일
	 */
	
	var date = new Date(); 
	var year = date.getFullYear(); 
	var month = new String(date.getMonth()+1); 
	var day = new String(date.getDate()); 

	// 한자리수일 경우 0을 채워준다. 
	if(month.length == 1){ 
	  month = "0" + month; 
	} 
	if(day.length == 1){ 
	  day = "0" + day; 
	} 
	console.log(year, month, day);		
	

	if(!(day >='24' || day <='10') && comcd == 'JUNGNANG01' || comcd == 'JUNGNANG02' || comcd == 'JUNGNANG03'){
		if($('#testId').val() == 'tom881205'){
			alert('테스트아이디 입니다.');
		}else{
			alert('신규수강신청 기한은 24일 ~ 다음달 10일 까지 입니다.');
			return;
		}
	}
	
	if(!(day >='25' || day <='07') && comcd == 'JUNGNANG04' || comcd == 'JUNGNANG05'){		
		if($('#testId').val() == 'tom881205'){
			alert('테스트아이디 입니다.');
		}else{
			alert('신규수강신청 기한은 25일 ~ 다음달 07일 까지 입니다.');
			return;
		}
	}
	
	var lecture = { comcd:comcd, classCd:classCd, itemCd:itemCd };
	
	$.get('/data/encode/text', {text:JSON.stringify(lecture)}, function(data){
		localStorage.setItem('lecture', data);
		
	}).done(function(){
		$.get('/data/lecture/program', {secureText:localStorage.getItem('lecture')}, function(data){			
			var programStartDate = dateUtil.getProgramStartDate(data.grpcd.startdate, comcd, day);
			var programEndDate = dateUtil.getProgramEndDate(programStartDate, data.monthCnt);			
			//신청할 강좌 시작종료값 임시 저장
			lectDate.sDate =  programStartDate;
			lectDate.eDate =  programEndDate;						
		}).done(function(){
			$.get('/data/lecture/basketIn', {secureText:localStorage.getItem('lecture'), "lectDate" : lectDate }, function(data){
				
				if (data.SEQ != undefined && data.SEQ != "" && data != null){					
					window.location.href='/lecture/lecturePaymentDetail?SEQ='+data.SEQ;
				} else if (data.code == "-9"){
					alert("세션이 종료되었거나 로그인 회원이 아닙니다.\n\n로그인 페이지로 이동합니다.");
					window.location.href='/mem/login';
				} else if(data.code == "-10"){
					alert("회원카드를 발급받은 회원만 신청이 가능합니다. 센터에 방문하셔서 발급 받으세요.");
					window.location.reload();
				} else {
					alert("접수오류 \n\n다시 시도 후 관리자에게 문의 하세요.");
					window.location.reload();
				}
			});
		});
	});
};






