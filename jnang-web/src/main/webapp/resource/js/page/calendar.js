
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
			
				//for(var ii=0; ii< rentAppList.length; ii++) {
					//console.log(rentAppList[ii].days +"/" + rentAppList[ii].TIME_SEQ +"/"+rentAppList[ii].RESERVE_DATE);
				//}
				
				var today = new Date();
				var yyyy = today.getFullYear();
				var mm 	 = today.getMonth();
				var today = new Date(yyyy,mm,today.getDate());
				//달력 출력
				for(var ii=1; ii<= 31; ii++) {
					
					var loopDay = new Date(YYMM.substring(0,4)+"/"+ Number(YYMM.substring(4,6)) +"/"+ii);
					var tmpList = "";
					
					//console.log(today.getFullYear() +"/"+today.getMonth() +"/"+today.getDate()  +" > 비교 "+ loopDay.getFullYear() +"/"+loopDay.getMonth() +"/"+loopDay.getDate() +" ");
					
					var xxx = fn_dateDiff("m",today.getFullYear() +"/"+today.getMonth() +"/"+today.getDate(), loopDay.getFullYear() +"/"+loopDay.getMonth() +"/"+loopDay.getDate());
					xxx = isNaN(xxx) ? 1: xxx;
					//console.log("xxxx:"+ isNaN(xxx));
					if (today.getTime() > loopDay.getTime()) {
						
						tmpList += "[<span class='btn_gray1'>예약종료</span>]<br>";
					} else if (xxx > 1) {
						tmpList += "[<span class='btn_gray1'>준비중</span>]<br>";
					} else {
							
						//DB 예약 일정 매칭 
						for(var xx=0; xx< aItemList.length; xx++) {
							var seq 		= aItemList[xx].seq;
							var item 		= aItemList[xx].item;
							
						
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
							
							
							
							tmpList += item;
							if (dataIn) {
								tmpList += "[<span class='btn_red1'>예약완료</span>]<br>";
							} else {
								tmpList += "[<span class='btn_green1'>예약가능</span>]<br>";
							}
									
	
						} //xx for
					} //end if
					$('#d'+ii).html(tmpList);
					
				}//ii for
				
			} else {
				
				//console.log(data);
				//if(data.length != 0) {
									
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
					var yyyy = today.getFullYear();
					var mm 	 = today.getMonth();
					var today = new Date(yyyy,mm,today.getDate());
					//달력 출력
					for(var ii=1; ii<= 31; ii++) {
						
						var loopDay = new Date(YYMM.substring(0,4)+"/"+ Number(YYMM.substring(4,6)) +"/"+ii);
						var tmpList = "";
						
						//console.log(today.getFullYear() +"/"+today.getMonth() +"/"+today.getDate()  +" > 비교 "+ loopDay.getFullYear() +"/"+loopDay.getMonth() +"/"+loopDay.getDate() +" ");
						
						var xxx = fn_dateDiff("m",today.getFullYear() +"/"+today.getMonth() +"/"+today.getDate(), loopDay.getFullYear() +"/"+loopDay.getMonth() +"/"+loopDay.getDate());
						xxx = isNaN(xxx) ? 1: xxx;
						//console.log("xxxx:"+ xxx +", "+ isNaN(xxx));
						if (today.getTime() > loopDay.getTime()) {
							
							tmpList += "[<span class='btn_gray1'>예약종료</span>]<br>";
						} else if (xxx > 1) {
							tmpList += "[<span class='btn_gray1'>준비중</span>]<br>";
						} else {
								
								//예약 리스트
								var dataIn = false;
								for(var jj=0; jj< rentAppList.length; jj++) {
		
									var days 		= rentAppList[jj].days;
									var TIME_SEQ 	= rentAppList[jj].TIME_SEQ;
									var RESERVE_DATE= rentAppList[jj].RESERVE_DATE;
									var STIME= rentAppList[jj].STIME;
									var ETIME= rentAppList[jj].ETIME;
									var APP_TYPE_NM= rentAppList[jj].APP_TYPE_NM;
									
									
									if (days== ii && TIME_SEQ != undefined) {
										//console.log(days +"<==========>" + (ii+1) +" TIME_SEQ:"+ TIME_SEQ +",  "+RESERVE_DATE);
										//dataIn = true;
										tmpList += STIME +"~"+ ETIME + "["+ APP_TYPE_NM +"]<br>";
									} 
								}//jj for
								
										
		
						} //end if
						$('#d'+ii).html(tmpList);
						
					}//ii for

					
					
				//}
			}
			
			}).done(function(data){

				
			});
		});
	
}