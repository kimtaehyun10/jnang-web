/**
* @projectDescription class.list.js
*
* @author RGJ
* @version 1.22
*/
'use strict'

function selectSport(selCT) {
	
	alert(selCT);
	var ct1 = $("#ct1").val();
	var ct2 = $("#ct2").val();

	if (selCT == "1") {
		
		$.post('/data/lecture/classGroup', { "sp_type" : ct1 } , function(data){
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
				alert("리스트 출력오류 : 잠시 후 다시 시도하여 주세요..");
				return;
			}
		});
	}
		
	var ct1 = $("#ct1").val();
	var ct2 = $("#ct2").val();
		
		$.post('/data/lecture/classList', { "ct1" : ct1, "ct2" : ct2 }, function(data){
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

