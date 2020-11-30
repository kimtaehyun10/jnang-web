
/// 실시간 예약현황 출력
function getDataList(p_cd,p_tab) {
		
		$.ajaxSetup({ cache: false });
		console.clear();
		var aItemList = new Array();
		$.get('/data/getPlaceTimeTabList', { "place_cd" : p_cd, "place_tab" : p_tab} , function(data){
			try {

			console.log(data);
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
			//console.log("p_tab===>"+ aItemList);
			
			//예약 정보 가져오기
			$.get('/data/getPlaceCalendarTab', { "YYMM" : YYMM, "PLACE_CD" : p_cd, "PLACE_TAB" : p_tab} , function(data){
			
				var rentAppList = new Array();
				if(data.length != 0){
					
					for(var i=0; i<data.length; i++){
						
						var oItem = new Object();
						oItem.RESERVE_DATE	= String(data[i].RESERVE_DATE);
						oItem.PLACE_CD 		= data[i].PLACE_CD;
						oItem.TIME_SEQ 		= data[i].PLACE_TIME_SEQ;
						oItem.RENT_IDX 		= data[i].RENT_IDX;
						oItem.place_tab 	= data[i].place_tab;
						oItem.days 			= (oItem.RESERVE_DATE).slice(-2);
						//console.log("day :"+ days +", seq: "+ TIME_SEQ +", DATE: "+ RESERVE_DATE);
						//var oItem = new Object();
						//var tmpList = "";
						
						//arr[days] = (arr[days] == undefined) ? tmpList : arr[days] +"<BR>"+tmpList;
						
						//arr[days][TIME_SEQ] = days +"/"+ TIME_SEQ +"/"+ RESERVE_DATE;
						//arr[days]= days +"/"+ TIME_SEQ +"/"+ RESERVE_DATE;
						rentAppList.push(oItem);
					}
				} else {
					//alert("대관 일정이 없습니다. 잠시 후 다시 시도하여 주시거나 관리자에게 문의 하여주세요.");
					//return false;
				}
			
				for(var ii=0; ii< rentAppList.length; ii++) {
					console.log(rentAppList[ii].days +"/" + rentAppList[ii].TIME_SEQ +"/"+rentAppList[ii].RESERVE_DATE);
					
				}
				
				//달력 출력
				for(var ii=0; ii< 30; ii++) {
					
					
					var tmpList = "";
					//예약 일정
					for(var xx=0; xx< aItemList.length; xx++) {
						var seq 		= aItemList[xx].seq;
						var item 		= aItemList[xx].item;
						
					
						//예약 리스트
						var dataIn = false;
						for(var jj=0; jj< rentAppList.length; jj++) {

							var days 		= rentAppList[jj].days;
							var TIME_SEQ 	= rentAppList[jj].TIME_SEQ;
							var RESERVE_DATE= rentAppList[jj].RESERVE_DATE;
							
							
							if (days== (ii+1) && TIME_SEQ == seq && TIME_SEQ != undefined) {
								console.log(days +"<==========>" + (ii+1) +" TIME_SEQ:"+ TIME_SEQ +",  "+RESERVE_DATE);
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
					$('#d'+(ii+1)).html(tmpList);
					
				}//ii for
				
				
			}).done(function(data){

				
			});
		});
	
}