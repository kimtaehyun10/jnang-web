/**
* @projectDescription rent.list.js
*
* @author 
* @version 1.21
*/
$(function(){
	setMenuTitle('bg_99', '체육시설', '대관현황', true);
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
	
	//$("#SDATE").datepicker();
	selectSport(1);
});

function send() {
	
	if (confirm("\n 대관 신청  하시겠습니까?\n ")) {
		
	} else {
		return false;	
	}
	
	$.post('/rent/save', $("#frm1").serialize() , function(data){
		
		if (data == "1") {
			alert('접수 되었습니다.');
			top.location.href ='/mypage/rent';
		} else if (data == "-9") {
			alert("사용자 동의후 접수 가능합니다.");
		} else {
			alert('접수 오류 !  다시 신청 하십시요!~');
		}
	}).done(function(data){
		
	},"json");
	return false;
	
	
	
};

function selectSport(selCT) {
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
							var rent_url = (data[i].PLACE_GROUP == "1") ? "write" : "date";
							dataList += "<tr>"
								+ "<td>"+ data[i].COMNM +"</td> "
								+ "<td>"+ data[i].PLACE_NM +"</td> "
								//+ "<td>"+ comma_str_y(saleAmt) +"</td>"
								+ "<td>"+ info_url +"</td>"
								+ "<td><a href='/calendar/?q="+ data[i].PLACE_CD +"' class='size_m2 btn_green1'>대관 현황</a></td>"
								//+ "<td><a href='/rent/"+ rent_url +"/?q="+ data[i].PLACE_CD +"/0' class='size_m2 btn_green1' onclick=\"alert('테스트중입니다. 예약불가능합니다. ==>실수로 결제시 환불 불가능합니다.');\")>대관 신청</a></td>"
								+ "<td><a href='/rent/"+ rent_url +"/?q="+ data[i].PLACE_CD +"/0' class='size_m2 btn_green1' onclick=\"\")>대관 신청</a></td>"
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


