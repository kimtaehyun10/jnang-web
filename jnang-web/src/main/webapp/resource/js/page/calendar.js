
/// 실시간 예약현황 출력
function getDataList(p_cd,p_tab) {
		
		$.ajaxSetup({ cache: false });
		console.clear();
		var aItemList = new Array();
		$.get('/data/getPlaceTimeTabList', { "place_cd" : p_cd, "place_tab" : p_tab} , function(data){
			try {

			//console.log(data);
			if(data.length != 0){		
				for(var i=0; i<data.length; i++){
						var oItem = new Object();
						oItem.seq		= data[i].seq;
						oItem.place_cd	= data[i].place_cd;
						oItem.place_tab	= data[i].place_tab;
						oItem.item		= data[i].item;
						aItemList.push(oItem);
						
				} //end for
					
			} else {
				//alert("대관 일정이 없습니다. 잠시 후 다시 시도하여 주시거나 관리자에게 문의 하여주세요.");
			}
					
			
			} catch (exception) {
				alert("대관 내역 출력오류 : 잠시 후 다시 시도하여 주세요..");
				return;
			}
		}).done(function(data){
		
			var YYMM = $(".this_month").attr("value");
			console.log("aItemList===>"+ aItemList.length);
			
			//게시판 문의 예약용
			var RB = (aItemList.length ==0) ? "1" : "";
			
			//예약 정보 가져오기
			$.get('/data/getPlaceCalendarTab', { "YYMM" : YYMM, "PLACE_CD" : p_cd, "PLACE_TAB" : p_tab, "RB" : RB } , function(data){
			
			//축구장, 야구장, 테니스장 등 예야시스템
			if (RB =="") { 
				var rentAppList = new Array();
				if(data.length != 0) {
					
					for(var i=0; i<data.length; i++){
						
						var oItem = new Object();
						oItem.RESERVE_DATE	= String(data[i].RESERVE_DATE);
						oItem.PLACE_CD 		= data[i].PLACE_CD;
						oItem.TIME_SEQ 		= data[i].PLACE_TIME_SEQ;
						oItem.RENT_IDX 		= data[i].RENT_IDX;
						oItem.place_tab 	= data[i].place_tab;
						oItem.days 			= (oItem.RESERVE_DATE).slice(-2);
						rentAppList.push(oItem);
					}
				} else {
					//alert("대관 일정이 없습니다. 잠시 후 다시 시도하여 주시거나 관리자에게 문의 하여주세요.");
					//return false;
				}
			
				var today = new Date();
				var arrayTmp = (today.toLocaleString()).split(".");
				
				var yy = arrayTmp[0];
				var mm = Number(arrayTmp[1]); //(Number(mm) > 9) ? mm : "0"+mm;
				var dd = Number(arrayTmp[2]); //(Number(dd) > 9) ? dd : "0"+dd;
				var hours = today.getHours(); // 시
				hours = Number(hours);
				
				if(hours < 10){
					
					hours = "0"+hours;
				}
				mm = (Number(mm) > 9) ? mm : "0"+ mm;
				dd = (Number(dd) > 9) ? dd : "0"+ dd;
				today = new Date(yy,mm,dd ,0,0,0);
				console.log("today1: "+ yy +"/"+ mm +"/"+ dd);
			
				var yymmdd = yy +""+ mm +""+ dd;
				var yymm = yy +""+ mm;
				var nyymm = YYMM;
				var nowToday = yymmdd + "" + hours;
				

				//달력 출력
				for(var ii=1; ii<= 31; ii++) {
					var ndd = (Number(ii) > 9) ? ii : "0"+ ii;
					var nyymmdd = YYMM +""+ ndd;
					var tmpList = "";
					//console.log(" xxxx:"+ yymmdd +" > "+ nyymmdd);
					var m_diff = fn_dateDiff("m", fn_convertDate("2",yymmdd), fn_convertDate("2",nyymmdd));
					//console.log("diff :"+ m_diff);
					if (Number(yymmdd) > Number(nyymmdd)) {
						tmpList += "[<span class='gray1'>예약종료</span>]<br>";
					//if ( (todayYYMM == cntYYMM && ii < (23-1)) || (int_today <= sortEndTime && int_today >= int_LimitYmd) 
					//	|| (diffMonth <= 1 && todayYYMM < cntYYMM && ii < (23-1) && int_toYYMM <= int_today) ) {
						
					//이전작업본} else if ( (Number(yymmdd) < Number(nyymm+""+23) && ii >= 23) ||  m_diff > 1 ) {
						
					} else if ( (Number(yymmdd) < Number(nyymm+""+31) && ii >= 31) ||  m_diff > 1 
							|| (Number(yymm) < Number(nyymm) && Number(yymmdd) < Number(yymm+""+31) )) {
						tmpList += "[<span class='gray1'>준비중</span>]<br>";
					} else {
							
						//DB 예약 일정 매칭 
						for(var xx=0; xx< aItemList.length; xx++) {
							var seq 		= aItemList[xx].seq;
							var item 		= aItemList[xx].item;
							var place_cd	= aItemList[xx].place_cd;
							
						
							//예약 리스트
							var dataIn = false;
							for(var jj=0; jj< rentAppList.length; jj++) {
	
								var days 		= rentAppList[jj].days;
								var TIME_SEQ 	= rentAppList[jj].TIME_SEQ;
								var RESERVE_DATE= rentAppList[jj].RESERVE_DATE;
								
								
								if (days== ii && TIME_SEQ == seq && TIME_SEQ != undefined) {
									//console.log(days +"<==========>" + (ii+1) +" TIME_SEQ:"+ TIME_SEQ +",  "+RESERVE_DATE);
									dataIn = true;
									break;
								} 
							}//jj for
							
							var rTime = nyymmdd.substring(0,4) + "" + nyymmdd.substring(4,6) + "" + nyymmdd.substring(6,8) + "" + item.substring(0,2);
							rTime = Number(rTime);
							nowToday = Number(nowToday); 
							
							
							console.log(rTime);
							console.log(nowToday);
							
							tmpList += item;
							if (dataIn) {
								tmpList += "[<span class='red1'>예약완료</span>]<br>";
							} else {
								//코로나 4단계 격상으로 인한 일시적 예약제한
								if(rTime >= 2021071206){
									tmpList += "[<span class='red1'>예약종료</span>]<br>";
								}else {
									tmpList += "<a href='/rent/date/?yy="+ nyymmdd.substring(0,4) +"&mm="+  nyymmdd.substring(4,6) +"&q="+ p_cd +"/0'>[<span class='btn_green1'>예약가능</span>]</a><br>";
								}
								
								//코로나가 완화될 시 이걸로 변경해주어야 함
//								if(rTime > nowToday){
//									tmpList += "<a href='/rent/date/?yy="+ nyymmdd.substring(0,4) +"&mm="+  nyymmdd.substring(4,6) +"&q="+ p_cd +"/0'>[<span class='btn_green1'>예약가능</span>]</a><br>";	
//								}else {
//									tmpList += "[<span class='red1'>예약종료</span>]<br>";
//								}
							
							
							
								
								//tmpList += "[<span class='gray1'>예약불가</span>]<br>";
								
							}
									
	
						} //xx for
					} //end if
					$('#d'+ii).html(tmpList);
					
				}//ii for
				
			} else {
				
					//체육관 대관 ########################################
					var rentAppList = new Array();
					if(data.length != 0) {
						
						for(var i=0; i<data.length; i++){
							
							var oItem = new Object();
							oItem.RESERVE_DATE	= String(data[i].RESERVE_DATE);
							oItem.PLACE_CD 		= data[i].PLACE_CD;
							oItem.TIME_SEQ 		= data[i].PLACE_TIME_SEQ;
							oItem.RENT_IDX 		= data[i].RENT_IDX;
							oItem.STIME 		= data[i].SDATE;
							oItem.ETIME 		= data[i].EDATE;
							oItem.APP_TYPE_NM 	= data[i].APP_TYPE_NM; //10:접수대기, 20:확인중, 
							oItem.days 			= (oItem.RESERVE_DATE).slice(-2);
							rentAppList.push(oItem);
						}
					} else {
						//alert("대관 일정이 없습니다. 잠시 후 다시 시도하여 주시거나 관리자에게 문의 하여주세요.");
						//return false;
					}


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
						//if ( (todayYYMM == cntYYMM && ii < (23-1)) || (int_today <= sortEndTime && int_today >= int_LimitYmd) 
						//	|| (diffMonth <= 1 && todayYYMM < cntYYMM && ii < (23-1) && int_toYYMM <= int_today) ) {
							
						//이전작업본} else if ( (Number(yymmdd) < Number(nyymm+""+23) && ii >= 23) ||  m_diff > 1 ) {
							
						} 
						/*else if ( (Number(yymmdd) < Number(nyymm+""+23) && ii >= 23) ||  m_diff > 1 
								|| (Number(yymm) < Number(nyymm) && Number(yymmdd) < Number(yymm+""+23) )) {
							tmpList += "[<span class='gray1'>준비중</span>]<br>";
						} */
						else {
							
								
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
								if(PLACE_CD == '08' && PLACE_CD == '11'){
									tmpList += "<a href='/rent/write/?ymd="+ nyymmdd +"&q="+ p_cd +"/0'>[<span class='btn_green1'>대관문의</span>]</a><br>";	
								}else {
									tmpList += "[<span class='gray1'>예약불가</span>]<br>";
								}
								
								//tmpList += "[<span class='gray1'>예약불가</span>]<br>";
										
		
						} //end if
						$('#d'+ii).html(tmpList);
						
					}//ii for

					
					
				//}
			}
			
			}).done(function(data){

				//TAB(코드 ) 선택
				var lentTabCnt = $(".rentTab").length;
				var cssObj = {
						"background-color": "#ffffff",
						"color": "#999999",
						"font-weight": "",
						}
				$(".rentTab").css(cssObj);
				
				var cssObj = {
						"background-color": "#8a64e4",
						"color": "#ffffff",
						"font-weight": "bold",
						}				
				$(".rentTab").eq(p_tab-1).css(cssObj);
			
			});
		});
	
}