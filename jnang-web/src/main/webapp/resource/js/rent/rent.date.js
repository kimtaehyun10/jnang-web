/**
* @projectDescription rent.list.js
*
* @author 
* @version 1.21
*/
var rntYMD;
function send(tabCnt) {
	$.ajaxSetup({ cache: false });
	var frm = document.frm3;
	var chkCnt = $("input:checkbox[name='tseq']:checked").length;
	if (chkCnt == 0) {
		alert("신청하실 시간을 선택하세요.");
		return false;
	}
	
	for(var ii=0; ii<noSave.length; ii++){
		
		if (noSave[ii] < 0) {
			
			//축구장 야구장
			var MSG = (tabCnt == "0") ? "" : "[ "+ ii +" 코트] ";  
			alert(MSG +"예약 불가 2시간(2개)이상 연속되게  예약을 선택하여 주세요.\n(★★ 한개만 선택할수 없습니다. ★★ );");
			return false;
		}
	}
	if (chkCnt <= 1) {
		alert("예약 불가 2시간(2개)이상 연속되게  예약을 선택하여 주세요.\n(★★ 한개만 선택할수 없습니다. ★★ );");
		return false;
	}
	
	
	if (confirm("\n 총 [ "+ chkCnt +" ] 건 선택\n\n              대관 신청  하시겠습니까?\n ")) {
		
	} else {
		return false;	
	}
	$("#ymd").val(rntYMD);
	$.post('/rent/save', $("#frm2").serialize() , function(data){
		if (data != null && data != "null") {
			$("#val1").val(data.rentIdx);
			$("#val2").val(data.rentYN);			
			frm.submit();
		} else if (data == "-9") {
			alert("사용자 동의후 접수 가능합니다.");
		} else {
			alert('접수 오류 !  다시 신청 하십시요!~');
			top.location.reload();
		}
	}).done(function(data){
		
	},"json");
	return false;
	
	
	
};

function selectSport(selCT) {
	$.ajaxSetup({ cache: false });
	var ct1 = $("#ct1").val();
	var ct2 = $("#ct2").val();

	if (selCT == "1") {
		
		$.post('/data/rentGroup', { "sp_type" : ct1 } , function(data){
			try {
				var dataList = "";
				$('#ct2').empty();
				if(data.length != 0){
					var option = "<option value='all' selected>--- 전체 ---</option>'";
					 $('#ct2').append(option);
					for(var i=0; i<data.length; i++){
						if (data[i].COMCD != undefined) {
							option = $("<option value='"+data[i].COMCD+"'>"+data[i].COMNM+"</option>");
			                $('#ct2').append(option);
						}
					}
				}
				else
				{
					var option = "<option value='all' selected>--- 전체 ---</option>'";
					 $('#ct2').append(option);
				}
			
			} catch (exception) {
				alert("대관 내역 출력오류 : 잠시 후 다시 시도하여 주세요..");
				return;
			}
		});
	}
		
	var ct1 = $("#ct1").val();
	var ct2 = $("#ct2").val();
		
		$.post('/data/rentList', { "ct1" : ct1, "ct2" : ct2 }, function(data){
			//try {
				var dataList = "";
				if(data.length != 0){
					for(var i=0; i<data.length; i++){
						
						if (data[i].COMNM != undefined) {
							var saleAmt = data[i].RENT_AMT;
							var info_url = (data[i].info_url != "") ? "<a href='"+ data[i].info_url +"' class='size_m2 btn_green1'>안내</a>" : ""; ;
							dataList += "<tr>"
								+ "<td>"+ data[i].COMNM +"</td> "
								+ "<td>"+ data[i].PLACE_NM +"</td> "
								+ "<td>"+ comma_str_y(saleAmt) +"</td>"
								+ "<td>"+ info_url +"</td>"
								+ "<td><a href='/calendar/"+ data[i].COMCD +"?q="+ data[i].PLACE_CD +"' class='size_m2 btn_green1'>대관 현황</a></td>"
								+ "<td><a href='/rent/date/?q="+ data[i].PLACE_CD +"/0' class='size_m2 btn_green1'>대관 신청</a></td>"
								+ "</tr>";
						}
					}
				
					$("#dataList").html(dataList);
					
					
				}
				else
				{
					$("#dataList").html("<tr><td colspan='10'>검색 데이타가  없습니다.</td></tr>");
				}
			
/*			} catch (exception) {
				alert("주문 내역 출력오류 : 잠시후 다시 시도하여 주세요..");
				return;
			}*/

		}).done(function(data){
	
		});
	//return false;
	
};


/// 실시간 예약현황 출력
function getRent(ymd,seq) {
		$.ajaxSetup({ cache: false });
		//예약불가 초기화
		noSave = [];
	//for(var ii=1; ii<= coteCnt; ii++){
		console.clear();
		console.log('ymd:'+ ymd +', seq==>'+seq);
		$("#ymd").val(''); rntYMD = ymd;
		$.get('/data/getRentList', { "ymd" : ymd, "q" : seq +"/0" } , function(data){
			//try {
				var arr = [];
				var arrTmp = []; //임시저장
				var arrTabCnt = []; 
				var arrTmpIdx = []; //버튼 생성  임시 비교용
				if(data.length != 0){

					var firstStr = "" ;
					var tmpList = "";
					for(var i=0; i<data.length; i++){
						
						var place_tab = data[i].place_tab;
						var checked = (data[i].rentIdx == 0) ? "" : " checked ";
						arrTabCnt[place_tab] = (isNaN(arrTabCnt[place_tab])) ? 0 : arrTabCnt[place_tab]+1;
						//console.log("arrTabCnt2 :"+ arrTabCnt[place_tab]);
						
						firstStr = '<div><label><input type="checkbox" sid="chk_tab_'+ place_tab +'" ';
						
						tmpList = firstStr;
						
						var tmpNoItem = ' value="" class="chkbxSize" checked disabled> <span class="rented margin_l5">'+ data[i].item +'</span></label></div>';
						
						// 접수가능
						if (data[i].rentIdx == "0") {

							tmpList += ' name="tseq" value="'+ data[i].seq +'" class="chkbxSize" onClick="selectCheck('+ place_tab +','+ data[i].seq +');" >'
									+ '<span class="margin_l5">'+ data[i].item +'</span></label></div>';
							arrTmpIdx[arrTabCnt[place_tab]] = data[i].seq;
						
						//접수 불가능
						} else {
							
							//2번째가 예약이 되어있으면 첫번째도 예약 못하도록
							if (arrTabCnt[place_tab] == 1 && arrTmpIdx[arrTabCnt[0]] != 0 ) {
								//arr[0] = "xxxxx";
							}
							
							tmpList += tmpNoItem;
							arrTmpIdx[arrTabCnt[place_tab]] = 0;
						}
						
						if (place_tab == 3) {
							console.log("i["+ arrTabCnt[place_tab] +"]: "+ arrTmpIdx[arrTabCnt[place_tab]-1] +", "+ arrTmpIdx[arrTabCnt[place_tab]] +", "+ data[i].item);
						}						

						arr[place_tab] = (arr[place_tab] == undefined) ? tmpList : arr[place_tab] + tmpList;
					} //end for
					
				}
				else
				{
					//var option = "<option value='all' selected>--- 전체 ---</option>'";
					// $('#ct2').append(option);
				}
				//console.log("tmpList["+ arr[2] +"]");
				for(var i=1; i< arr.length; i++){
					$('#data_tab'+i).html(arr[i]);
				}
				$("#val3").val(ymd);
				$("#val4").val(seq);
				
				ymd = fn_convertDate(2,ymd);
				$('#selectDate').html("&bull;"+ ymd +" 시간선택");
				$('#info1').hide();
			
	/*		} catch (exception) {
				alert("대관 내역 출력오류 : 잠시 후 다시 시도하여 주세요..");
				return;
			}*/
		}).done(function(data){
		
		});
	
	//}//end for
	
	
}

//다수 첵크 방지
var start_disabled = [0,0,0,0,0,0,0,0,0,0,0];
var rentCnt = [];
var noSave = [];
function selectCheck(tab, mycnt) {
	$.ajaxSetup({ cache: false });
	tab = Number(tab);
	//mycnt = Number(mycnt);
	rentCnt[tab] = (isNaN(rentCnt[tab])) ? 0 : rentCnt[tab];
	noSave[tab] = (isNaN(noSave[tab])) ? 0 : noSave[tab];
	
	var index= 0;
	$('input:checkbox[sid="chk_tab_'+tab+'"]').each(function() {
		
		if (this.value == mycnt) {
			console.log("mycnt["+ index +"]:"+ this.value +" == "+ mycnt);
			mycnt = index;
		}
		index++;		
	});
	//console.log("mycnt :"+mycnt +", index :"+ index);
	var tabCnt 		= $('input:checkbox[sid="chk_tab_'+tab+'"]').length;
	//var selectCnt 	= $('input[sid="chk_tab_'+tab+'"]:checked [value != ""]').length;
	//첵크된 갯수
	var selectCnt = 0;
	$('input[sid="chk_tab_'+tab+'"]:checked').each(function() {
		if (this.value != "") {
			selectCnt++;
		}
	});
	console.log("총 코스별 갯수:"+tabCnt);
	console.log("총선택 갯수:"+selectCnt);
	
	if (selectCnt == 1) {
		var chk1 = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(mycnt).prop('checked');
		console.log("chk1:"+chk1);
		alert("최소 2시간(2개)이상 연속으로 선택하셔야 예약이 가능합니다.\n(1시간만 선택된 경우 무효 처리됨)\n\n");
		//var chk1 = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(mycnt).prop('checked');
		if (chk1) {
			$('input:checkbox[sid="chk_tab_'+tab+'"]').eq((mycnt+1)).prop("checked",true);
		}else {
			$('input:checkbox[sid="chk_tab_'+tab+'"]').eq((mycnt+1)).prop("checked",false);
		}
	}
	
	//초기화
	//selectCnt 	= $("input:checkbox[name='tseq']:checked").length; //$('input:checkbox[sid="chk_tab_'+tab+'"]:checked').length;
	selectCnt = 0;
	$('input[sid="chk_tab_'+tab+'"]:checked').each(function() {
		if (this.value != "") {
			selectCnt++;
		}
	});	
	if (selectCnt == 0) {
		$("input:checkbox[sid=chk_tab_"+ tab +"]:not(:checked)").prop('disabled', false);
		$("input:checkbox[sid=chk_tab_"+ tab +"]:not(:checked)").prop('checked', false);
		//setTimeout("getRent('"+ rntYMD +"',"+tab+");",300);
		//$("#btn"+ rntYMD).trigger("click");
		return false;
	}
	//console.log("총선택 갯수:"+selectCnt);
	

	//예약오류 값 초기화
	noSave[tab] = 0;
	var groupTemp = false;
	var thisChkCnt = 0;
	for (ii=0; ii < tabCnt; ii++) {
		
		var chk1 = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(ii).prop('checked');
		
		if (chk1 == false && start_disabled[tab] == 0) {
			$('input:checkbox[sid="chk_tab_'+tab+'"]').eq(ii).prop('disabled', true);
		} else {
			start_disabled[tab] = 1;
		}

		//2번째가 예약되어있으면 1번째는 예약을 못하도록 비활성으로 적용
		/*
		if (ii==1 && chk1) {
			var tmpVal = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(ii).val();
			alert("tab:"+ tab + " / "+ tmpVal);
			if (tmpVal == "") {
				$('input:checkbox[sid="chk_tab_'+tab+'"]').eq(0).prop('disabled', true);
			}
		}
		*/

		//연속선택 첵크
		if (chk1) {
			
			if (selectCnt == 1) {
				rentCnt[tab] = 1;
			} else if (ii >= mycnt) {
				//이전 첵크여부
				var preChk = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(ii-1).prop('checked');
				
				if (preChk) {
					rentCnt[tab] += 1;
				} else {
					var thisChk = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(ii).prop('checked');
					//alert("최소 2시간(2개)이상 연속으로 선택하셔야 예약이 가능합니다.\n(1시간만 선택된 경우 무효 처리됨)\n\n.."+ preChk);
					if (thisChk) {
						$('input:checkbox[sid="chk_tab_'+tab+'"]').eq((ii+1)).prop("checked",true);
						rentCnt[tab] += 1;
					} else {
						rentCnt[tab] =1;	
					}
				} 
			}
		}
		
		
		//연속된 첵크값인지  확인
		var thisChk = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(ii).prop('checked');
		var thisChkVal = $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(ii).val();
		
		if (!thisChk || thisChkVal == '') {
			if (thisChkCnt >= 2 || thisChkCnt == 0) {
				thisChkCnt = 0;
			} else if (thisChkCnt == 1) {
				noSave[tab] = -1;
			}
		//} else if ( thisChkVal == '') {
		//	thisChkCnt = 1;
		} else if (thisChk) {
			thisChkCnt ++;
		} 
		//if (selectCnt==1) {
		//	noSave[tab] = -1;
		//}
		//console.log("이상이상"+ selectCnt +"=================>");

		
		//console.log("ii:"+ ii +", thisChkCnt:"+ thisChkCnt +", thisChkVal: "+ thisChkVal+", thisChk: "+ thisChk +", noSave[tab]"+ noSave[tab]);
		//alert('ssss');
		/*
		if ((thisChkCnt == 0 && thisChk) || thisChkCnt == 1 && thisChk) {
			thisChkCnt++;
			groupTemp = true;
		} else {
			thisChkCnt == 1;
		}
		*/
		
		
		
	}//end for
	
	if (noSave[tab] == -1) {
		console.log("이상이상"+ ii +"=================>");
	}
	
	console.log("thisChkCnt"+ noSave[tab]);
	//마지막 한시간만 첵크 여부 확인
	var lastpreChk 		= $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(tabCnt-2).prop('checked');
	var lastpreChkVal 	= $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(tabCnt-2).val();
	var lastChk 		= $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(tabCnt-1).prop('checked');
	var lastChkVal 		= $('input:checkbox[sid="chk_tab_'+tab+'"]').eq(tabCnt-1).val();
	
	if (lastpreChk == false && lastChk == true && lastChkVal != "") {
		$('input:checkbox[sid=chk_tab_'+ tab +']').eq((tabCnt-2)).prop('disabled', false);
		$('input:checkbox[sid="chk_tab_'+tab+'"]').eq((tabCnt-2)).prop("checked",true);
	}
}

