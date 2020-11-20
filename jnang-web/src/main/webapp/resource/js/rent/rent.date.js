/**
* @projectDescription rent.list.js
*
* @author 
* @version 1.21
*/
var rntYMD;
function send() {
	var chkCnt = $("input:checkbox[name='tseq']:checked").length;
	if (chkCnt == 0) {
		alert("신청하실 시간을 선택하세요.")
		return false;
	}
	
	if (confirm("\n 총 [ "+ chkCnt +" ] 건 선택\n\n              대관 신청  하시겠습니까?\n ")) {
		
	} else {
		return false;	
	}
	$("#ymd").val(rntYMD);
	$.post('/rent/save', $("#frm2").serialize() , function(data){
		alert(data);
		if (data == "1") {
			alert('접수 되었습니다.');
			top.location.href ='/rent/rentOrder';
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
	
	//for(var ii=1; ii<= coteCnt; ii++){
		console.clear();
		console.log('ymd:'+ ymd +', seq==>'+seq);
		$("#ymd").val(''); rntYMD = ymd;
		$.get('/data/getRentList', { "ymd" : ymd, "q" : seq +"/0" } , function(data){
			//try {
				var arr = []; 
				if(data.length != 0){
					//var option = "<option value='all' selected>--- 전체 ---</option>'";
					// $('#ct2').append(option);
					//console.log("length:    ===> "+ data.length);
					//console.log(data);
					tmpList = "";
					for(var i=0; i<data.length; i++){
						
						var place_tab = data[i].place_tab;
						//if (data[i].COMCD != undefined) {
						var checked = (data[i].rentIdx == 0) ? "" : " checked "; 
						tmpList = '<div id="'+i+'"><label><input type="checkbox" ';
							
						if (data[i].rentIdx == "0") {
							tmpList += 'sid="chk_tab_'+ place_tab +'" name="tseq" value="'+ data[i].seq +'" class="chkbxSize" onClick="selectCheck(0,'+ place_tab +','+ data[i].seq +');" >'
									+ '<span class="margin_l5">'+ data[i].item +'</span></label></div>';
						} else {
							tmpList += ' class="chkbxSize" checked disabled >'
									+ '<span class="rented margin_l5">'+ data[i].item +'</span></label></div>';
						}							

						arr[place_tab] = (arr[place_tab] == undefined) ? tmpList : arr[place_tab] + tmpList;
					}
				}
				else
				{
					//var option = "<option value='all' selected>--- 전체 ---</option>'";
					// $('#ct2').append(option);
				}
				//console.log("tmpList["+ arr[2] +"]");
				for(var i=0; i<data.length; i++){
					$('#data_tab'+i).html(arr[i]);
				}
				
				ymd = fn_convertDate(2,ymd);
				$('#selectDate').html("&bull;"+ ymd +" 시간선택");
				$('#info1').hide();
			
	/*		} catch (exception) {
				alert("대관 내역 출력오류 : 잠시 후 다시 시도하여 주세요..");
				return;
			}*/
		});
	
	//}//end for
	
	
}

//다수 첵크 방지
function selectCheck(tabCnt, tab, val1) {
	
	var chkCnt = $("input:checkbox[sid=chk_tab_"+ tab +"]:checked").length;
	if (chkCnt == 0) {
		$("input:checkbox[sid=chk_tab_"+ tab +"]:checkbox:not(:checked)").removeAttr("disabled");
	} else {
		$("input:checkbox[sid=chk_tab_"+ tab +"]:checkbox:not(:checked)").attr("disabled", "disabled");
	}

}

