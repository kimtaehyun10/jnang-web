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
	if(teamCnt == 0) {
		alert("대관신청 전 팀이 있어야 합니다.");
		return false;
		
	} else if(10 >= teamCnt) {
		alert("대관신청을 하시기 위해선  팀원이 10명 이상이어야 합니다.");
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
		
		$.get('/data/rentGroup', { "sp_type" : ct1 } , function(data){
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
		
		$.get('/data/rentList', { "ct1" : ct1, "ct2" : ct2 }, function(data){
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
function getRent(ymd,seq,mode) {
		$.ajaxSetup({ cache: false });
		//예약불가 초기화
		noSave = [];
	//for(var ii=1; ii<= coteCnt; ii++){
		console.clear();
		console.log('ymd:'+ ymd +', seq==>'+seq);
		if (ymd =='') {
			ymd = $("#RENT_DATE").val();
		}		
		ymd = ymd.replace(/[^0-9\.]/g, '');
		var YYMM = ymd.substring(0,6);
		var STIME = $("#STIME").val();
		var ETIME = $("#ETIME").val();
		//$.get('/data/getRentList', { "ymd" : ymd, "q" : seq +"/0" } , function(data){
		$.get('/data/getPlaceCalendarTab', { "YYMMDD" : ymd, "PLACE_CD" : seq, "PLACE_TAB" : 1 , "RB" : "1" } , function(data){

			var tmpList = "";
			var rentAppList = new Array();
			if(data.length != 0) {
				
				for(var i=0; i<data.length; i++){
					
					//처음 로드
					if (STIME == "" || ETIME == "") {
						
					} else { 
						var dbSDATE = (data[i].SDATE).substring(0,2);
						var dbEDATE = (data[i].EDATE).substring(0,2);
						console.log((dbSDATE +">="+ STIME) +" && "+ dbEDATE +"<="+ ETIME);
						console.log((Number(dbSDATE) >= Number(STIME)) +" && "+  (Number(dbEDATE) <= Number(ETIME)));
						//debugger;
						if (Number(STIME) >= Number(ETIME)) {
							alert("시간을 정확히 확인하세요\n(시작시간과 종료시간이 바뀌었습니다.)");
							$("#ETIME").val("");
							return;
						} else if ((Number(dbSDATE) < Number(ETIME)) && (Number(dbEDATE) > Number(STIME))  ) {
						//} else if ((Number(dbSDATE) >= Number(STIME)) && (Number(dbEDATE) <= Number(ETIME))  ) {
							alert("이미 예약된 중복되는 시간이 있습니다.\n시간을 정확히 확인하세요.)");
							$("#ETIME").val("");
						} else {
							//console.log(Number(STIME) +"<="+ ETIME +" && "+  (Number(dbSDATE) +"<="+ ETIME) +" && "+ dbEDATE +">="+ STIME);
						}
					}
					
					tmpList += data[i].SDATE +"~"+ data[i].EDATE + "["+ data[i].APP_TYPE_NM +"]<br>";
				}
			} else {
				//alert("대관 일정이 없습니다. 잠시 후 다시 시도하여 주시거나 관리자에게 문의 하여주세요.");
				//return false;
			}
			
			if (tmpList.length > 10) {
				$("#reservedList").html(tmpList);
			} else {
				$("#reservedList").html("예약된 내역이  없습니다.");
			}

			/*
			var today = new Date();
			var yyyy = today.getFullYear();
			var mm 	 = today.getMonth();
			var today = new Date(yyyy,mm,today.getDate());
			*/

			/*
			var today = new Date();
			//console.log(today.toLocaleString());
			var arrayTmp = (today.toLocaleString()).split(".");
			var yy = arrayTmp[0];
			var mm = Number(arrayTmp[1]); //(Number(mm) > 9) ? mm : "0"+mm;
			var dd = Number(arrayTmp[2]); //(Number(dd) > 9) ? dd : "0"+dd;
			mm = (Number(mm) > 9) ? mm : "0"+ mm;
			dd = (Number(dd) > 9) ? dd : "0"+ dd;
			today = new Date(yy,mm,dd ,0,0,0);
			console.log("today1: "+ yy +"/"+ mm +"/"+ dd);
		
			var yymmdd = yy +""+ mm +""+ dd;
			var yymm = yy +""+ mm;
			var nyymm = YYMM;
			
			//달력 출력
			for(var ii=1; ii<= 31; ii++) {
				
				var ndd = (Number(ii) > 9) ? ii : "0"+ ii;
				var nyymmdd = YYMM +""+ ndd;
				var tmpList = "";
				//console.log(" xxxx:"+ yymmdd +" > "+ nyymmdd);
				var d_diff = fn_dateDiff("d", fn_convertDate("2",yymmdd), fn_convertDate("2",nyymmdd));
				var m_diff = fn_dateDiff("m", fn_convertDate("2",yymmdd), fn_convertDate("2",nyymmdd));
				console.log("YYMM :"+ YYMM);
				
				//지난기간 이거나 , 당일 예약불가능 3일후 가능하도록
				if ((Number(yymmdd) > Number(nyymmdd)) || (Number(yymm) >= Number(nyymm) && d_diff < 2 ) ) {
					tmpList += "[<span class='gray1'>예약종료</span>]<br>";

				} else if ( (Number(yymmdd) < Number(nyymm+""+23) && ii >= 23) ||  m_diff > 1 
						|| (Number(yymm) < Number(nyymm) && Number(yymmdd) < Number(yymm+""+23) )) {
					tmpList += "[<span class='gray1'>준비중</span>]<br>";
				} else {
					
						
						//예약 리스트
						var dataIn = false;
						for(var jj=0; jj< rentAppList.length; jj++) {

							var days 		= rentAppList[jj].days;
							var TIME_SEQ 	= rentAppList[jj].TIME_SEQ;
							var RESERVE_DATE= rentAppList[jj].RESERVE_DATE;
							var STIME= rentAppList[jj].STIME;
							var ETIME= rentAppList[jj].ETIME;
							var PLACE_CD 	= rentAppList[jj].PLACE_CD;
							var APP_TYPE_NM= rentAppList[jj].APP_TYPE_NM;
							
							
							if (days== ii && TIME_SEQ != undefined) {
								//console.log(days +"<==========>" + (ii+1) +" TIME_SEQ:"+ TIME_SEQ +",  "+RESERVE_DATE);
								//dataIn = true;
								tmpList += STIME +"~"+ ETIME + "["+ APP_TYPE_NM +"]<br>";
							} 
						}//jj for
						//tmpList += "<a href='/rent/write/?ymd="+ yymmdd +"&q="+ seq +"/0'>[<span class='btn_green1'>대관문의</span>]</a><br>";
						console.log("sss"+ tmpList);
				} //end if
				
				
				//$("#reservedList").html(tmpList);
				
			}//ii for
			*/
			
			

	/*		} catch (exception) {
				alert("대관 내역 출력오류 : 잠시 후 다시 시도하여 주세요..");
				return;
			}*/
		}).done(function(data){
		
		});
	
	//}//end for
	
	
}


