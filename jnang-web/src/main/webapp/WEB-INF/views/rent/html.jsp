<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="COMCD" value="${COMCD}" />
<%
String COMCD = (String)pageContext.getAttribute("COMCD") ;
String subTitle = "";
//out.println("COMCD:"+ COMCD +"<BR>");
switch(COMCD) {
	case "JUNGNANG01" :
		subTitle = "구민체육센터 > 이용안내";
	break;
	
	case "JUNGNANG02" :
		subTitle = "중랑문화체육관 > 시설안내";
	break;
}

%>
	<script type="text/javaScript" language="javascript">
	$(function(){
		setMenuTitle('bg_99', '체육시설', '<%=subTitle%>',true);
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
	});
	
 
</script>


<div class='sub_useinfo01'>

<%
//##################################################################################################################################################
//##################################################################################################################################################
//##################################################################################################################################################
if (COMCD.equals("JUNGNANG01")) {
%>

	<div id='대관' class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>대관안내</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 절차</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/접수기간/방문접수시간/인터넷접수시간 정보로 구성되어 있습니다.'>
			<caption>대관상담</caption>
			<tr>
				<th>대관상담</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>홈페이지 신청</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>심사중</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>담당자 심사</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>담당자 대관허용</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>온라인결재</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>시설물 이용</th>
			</tr>
			<tr>
				<td>방문 및 전화<br>☎ 02-3423-1070~4</td>
				<td>인터넷 <br>다운로드 후 <br>신청서 작성</td>
				<td>심사중</td>
				<td>담당자 심사</td>
				<td>대관 허가 <br>여부 검토 <br>후 허용</td>
				<td>사용료 납부<br>(온라인,카드, 계좌)</td>
				<td>시설물 이용 <br>후 정리정돈</td>
			</tr>
			</table>
		</div>
		<br>
		<br>

		<div class="ali_r margin_b10">
		<a href="/data/file/01010100_20210120.hwp" class="size_m2 btn_green1 fontsize_1dot50" >대관신청 다운로드</a>
		</div>

		<br>
		<br>


		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 안내</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관 안내</caption>
			<tr>
				<th>구분</th>
				<th>이용형태</th>
				<th>내 용</th>
			</tr>
			<tr>
				<td>대체육관(2층)</td>
				<td class='ali_l' rowspan=3>일일대관<br>(대관 담당자와 협의 후<br>이용가능)</td>
				<td class='ali_l'>
					- 각종 생활체육 동호회<br>
					- 농구, 배구 등 운동경기 및 체육관련 행사<br>
					- 체육시설 이용료 기준에 의해 실시
				</td>
			</tr>
			</table>
		</div>

		<br>
		<br>


		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 신청</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption> 대관 신청</caption>
			
			<tr>
				<td class='ali_l'>- 대관 신청은 행사일 3개월 전부터 접수 가능하며, 대관료는 사용승인일 이후 7일 이내에 납부하셔야 합니다.<br>
- 대관 사용 승인일 7일 이내에 대관료를 납부하지 않을 시에는 사용허가를 취소할 수 있습니다.</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		
		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>관련조례규정</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption> 대관 신청</caption>
			
			<tr>
				<td class='ali_l'>- 서울특별시 중랑구립체육시설 설치 및 운영에 관한 조례 제5조 2항 별표2</td>
			</tr>
			</table>
		</div>

		<br>
		<br>


		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>시설사용료</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>시설사용료</caption>
			<tr>
				<th>장  소</th>
				<th>면 적(건평)</th>
				<th>대관료(원)</th>
				<th>비  고</th>
			</tr>
			<tr>
				<td>대체육관(2층)</td>
				<td class='ali_l'>1,120㎡(338평)</td>
				<td class='ali_l'>120,100</td>
				<td class='ali_l' rowspan=3>- 체육경기 기준요금이며, 
  기타 행사의 경우 추가요금이 발생될 수 있습니다.<br>
 - 휴일 2시간 기준 (전기료별도)<br>
 - 대체육관은 일요일만 대관가능<br>
 ※ 부가세 포함 </td>
			</tr>
			<tr>
				<td>다목적체육관(2층)</td>
				<td class='ali_l'>238㎡(72평)</td>
				<td class='ali_l'>45,000</td>
			</tr>
			<tr>
				<td>생활체육실(1층)</td>
				<td class='ali_l'>225㎡(68평)</td>
				<td class='ali_l'>45,000</td>
			</tr>
			</table>
		</div>
		<br>
		<br>
		
		<div class="ali_l margin_b10">
<strong><span class="fc_orange1">※ 대관은 기본 2시간 기준이며, 부가세포함 요금입니다. </span></strong><br>
<strong>※ 토·일 및 공휴일은 기준 액의 30% 가산적용 (18:00 이후는 30% 추가적용)</strong><br>
<strong>※ 전기료 및 부속시설사용료는 별도 납부입니다.</strong><br>
<strong>※ 대체육관, 다목적체육관, 생활체육실 프로그램 운영 시간에는 대관사용이 불가합니다. </strong><br>
<strong>※ 시간경과 시 초과 사용료는 시간당 50% 가산하고 1시간미만은 1시간으로 간주합니다.</strong><br>
<strong>※ 기타 궁금한 사항은 중랑구민체육센터 ☎ 02-3423-1070~4 으로 문의하시기 바랍니다.</strong>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>부속시설사용료</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>부속시설사용료</caption>
			<tr>
				<th>구 분 </th>
				<th>사용시간</th>
				<th>사용료</th>
				<th>비  고</th>
			</tr>
			<tr>
				<td>음향장비</td>
				<td class='ali_l'>3시간</td>
				<td class='ali_l'>10,000</td>
				<td class='ali_l' rowspan=3>- 부가세별도입니다.<br>
- 야간 사용료는 기본사용료에 30%가산됩니다.<br>
- 각 기본사용시간 미만 사용이라도 기본시간을 사용한 것으로
 간주합니다.<br>
- 1시간 초과 시마다 기본사용료에 50%가산,<br>
 1시간 미만인 경우 1시간으로 간주합니다.</td>
			</tr>

			<tr>
				<td>조명시설</td>
				<td class='ali_l'>1시간</td>
				<td class='ali_l'>10,000</td>
			</tr>
			<tr>
				<td>난방 등</td>
				<td class='ali_l'>2시간</td>
				<td class='ali_l'>20,000</td>
			</tr>
			</table>
		</div>
		
		<br>
		<br>

			<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 납부 방법</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관 납부 방법</caption>
			
			<tr>
				<td class='ali_l'>- 현금 및 신용카드 결제 (단체명 또는 대표자명으로 입금)<br>
- 무통장 입금 / 우리 / 1005-101-941275 / 서울시중랑구시설관리공단 </td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		
			<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관료 환불</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관료 환불</caption>
			
			<tr>
				<td class='ali_l'>- 대관료를 납부하고 대관을 취소하고자 할 경우 사용 당일포함 7일전에 취소통보를 하여야 하며, <br>
 통보가 없을시 대관료는 반환하지 아니함을 원칙으로 합니다.<br>
- 체육경기 대관 환불은 서식5호에 의하여 사용 당일포함 7일전에 통보 후 전액 환불합니다.</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		
			<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 시 유의사항</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관 시 유의사항</caption>
			
			<tr>
				<td class='ali_l'>- 사용자는 이용과 관련하여 다음 사항을 준수하여야 한다.<br>
 1) 체육관과 관련된 사항은 담당자의 지시에 따라야 한다. <br>
 2) 체육관 이용수칙을 준수하여야 한다. <br>
 3) 체육관 이용 시 지정된 실내화(실내용 운동화)를 착용해야 한다. <br>
 4) 계약된 대관 시간은 엄수해야 한다. <br>
 5) 계약된 대관 시간 중 안전사고에 대한 책임은 주관 단체가 모든 책임을 진다. <br>
 6) 지정된 장소이외의 운동시설에서는 음식물 반입을 금지한다. (자판기 커피포함) <br>
 7) 대관은 허가된 시설에만 적용되므로 일체의 집기나 운동용품은 사전 허락 후 사용한다. <br>
 8) 지정된 대관 시설 외에는 사전허가 없이 출입 또는 사용을 금지한다.(샤워시설 등) <br>
 9) 시설 등을 파손하였을 때에는 사용자의 과실로 배상해야함을 원칙으로 한다.<br>
 10) 기타 사항은 체육시설이용내규에 따른다.</td>
			</tr>
			</table>
		</div>

		<br>
		<br>


		
	</div>




<%
} 
%>

<%
//##################################################################################################################################################
//##################################################################################################################################################
//##################################################################################################################################################
if (COMCD.equals("JUNGNANG02")) {
%>


	<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>대관안내</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 절차</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/접수기간/방문접수시간/인터넷접수시간 정보로 구성되어 있습니다.'>
			<caption>대관상담</caption>
			<tr>
				<th>대관상담</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>홈페이지 신청</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>심사중</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>담당자 심사</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>담당자 대관허용</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>온라인결재</th>
				<th rowspan=2 style="background:#ffffff;border-top:#fff;border-bottom:#fff;">▶</th>
				<th>시설물 이용</th>
			</tr>
			<tr>
				<td>방문 및 전화<br>☎ 02-436-9200</td>
				<td>인터넷 <br>다운로드 후 <br>신청서 작성</td>
				<td>심사중</td>
				<td>담당자 심사</td>
				<td>대관 허가 <br>여부 검토 <br>후 허용</td>
				<td>사용료 납부<br>(온라인,카드, 계좌)</td>
				<td>시설물 이용 <br>후 정리정돈</td>
			</tr>
			</table>
		</div>
		<br>
		<br>

		<div class="ali_r margin_b10">
                   <a href="/data/file/01010100_20210120.hwp" class="size_m2 btn_green1 fontsize_1dot50" >대관신청 다운로드</a>
		</div>

		<br>
		<br>


		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 안내</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관 안내</caption>
			<tr>
				<th>구분</th>
				<th>이용형태</th>
				<th>내 용</th>
			</tr>
			<tr>
				<td>다목적체육관</td>
				<td class='ali_l'>일일대관<br>(대관 담당자와 협의 후<br>이용가능)</td>
				<td class='ali_l'>
					- 각종 생활체육 동호회<br>
					- 농구, 배구 등 운동경기 및 체육관련 행사<br>
					- 체육시설 이용료 기준에 의해 실시
				</td>
			</tr>
			</table>
		</div>

		<br>
		<br>


		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 신청</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption> 대관 신청</caption>
			
			<tr>
				<td class='ali_l'>- 대관 신청은 행사일 3개월 전부터 접수 가능하며, 대관료는 사용승인일 이후 7일 이내에 납부하셔야 합니다.<br>
- 대관 사용 승인일 7일 이내에 대관료를 납부하지 않을 시에는 사용허가를 취소할 수 있습니다.</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		
		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>관련조례규정</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption> 대관 신청</caption>
			
			<tr>
				<td class='ali_l'>- 서울특별시 중랑구립체육시설 설치 및 운영에 관한 조례 제5조 2항 별표2</td>
			</tr>
			</table>
		</div>

		<br>
		<br>


		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>시설사용료</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>시설사용료</caption>
			<tr>
				<th>장  소</th>
				<th>면 적(건평)</th>
				<th>대관료(원)</th>
				<th>비  고</th>
			</tr>
			<tr>
				<td>다목적체육관(2층)</td>
				<td class='ali_l'>950.1㎡(287.4평)</td>
				<td class='ali_l'>120,120<br>(체육 경기)</td>
				<td class='ali_l'>- 휴일 2시간 기준 (전기료별도) <br>
- 휴일 할증 30% 적용함 * 부가세 포함<br>
- 기타 행사의 경우 별도요금이 징수됩니다. </td>
			</tr>
			
			</table>
		</div>
		<br>
		<br>
		
		<div class="ali_l margin_b10">
<strong><span class="fc_orange1">※ 대관은 기본 2시간 기준이며, 부가세포함 요금입니다. </span></strong><br>
<strong>※ 토·일 및 공휴일은 기준 액의 30% 가산적용 (18:00 이후는 30% 추가적용)</strong><br>
<strong>※ 전기료 및 부속시설사용료는 별도 납부입니다.</strong><br>
<strong>※ 다목적체육관 프로그램 운영 시간에는 대관사용이 불가합니다. </strong><br>
<strong>※ 시간경과 시 초과 사용료는 시간당 50% 가산하고 1시간미만은 1시간으로 간주합니다.</strong><br>
<strong>※ 기타 궁금한 사항은 중랑문화체육관 ☎ 02-436-9200 으로 문의하시기 바랍니다.</strong>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>부속시설사용료</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>부속시설사용료</caption>
			<tr>
				<th>구 분 </th>
				<th>사용시간</th>
				<th>사용료</th>
				<th>비  고</th>
			</tr>
			<tr>
				<td>음향장비</td>
				<td class='ali_l'>3시간</td>
				<td class='ali_l'>10,000</td>
				<td class='ali_l' rowspan=3>- 부가세별도입니다.<br>
- 야간 사용료는 기본사용료에 30%가산됩니다.<br>
- 각 기본사용시간 미만 사용이라도 기본시간을 사용한 것으로 간주합니다.<br>
- 1시간 초과 시마다 기본사용료에 50%가산,<br>
 1시간 미만인 경우 1시간으로 간주합니다.</td>
			</tr>

			<tr>
				<td>조명시설</td>
				<td class='ali_l'>1시간</td>
				<td class='ali_l'>10,000</td>
			</tr>
			<tr>
				<td>난방 등</td>
				<td class='ali_l'>2시간</td>
				<td class='ali_l'>20,000</td>
			</tr>
			</table>
		</div>
		
		<br>
		<br>

			<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 납부 방법</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관 납부 방법</caption>
			
			<tr>
				<td class='ali_l'>- 현금 및 신용카드 결제 (단체명 또는 대표자명으로 입금)<br>
- 무통장 입금 / 농협 / 100054-55-001426 / 서울시중랑구시설관리공단 </td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		
			<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관료 환불</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관료 환불</caption>
			
			<tr>
				<td class='ali_l'>- 대관료를 납부하고 대관을 취소하고자 할 경우 사용 당일포함 7일전에 취소통보를 하여야 하며, <br>
 통보가 없을시 대관료는 반환하지 아니함을 원칙으로 합니다.<br>
- 체육경기 대관 환불은 서식5호에 의하여 사용 당일포함 7일전에 통보 후 전액 환불합니다.</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		
			<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>대관 시 유의사항</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>대관 시 유의사항</caption>
			
			<tr>
				<td class='ali_l'>- 사용자는 이용과 관련하여 다음 사항을 준수하여야 한다.<br>
 1) 체육관과 관련된 사항은 담당자의 지시에 따라야 한다. <br>
 2) 체육관 이용수칙을 준수하여야 한다. <br>
 3) 체육관 이용 시 지정된 실내화(실내용 운동화)를 착용해야 한다. <br>
 4) 계약된 대관 시간은 엄수해야 한다. <br>
 5) 계약된 대관 시간 중 안전사고에 대한 책임은 주관 단체가 모든 책임을 진다. <br>
 6) 지정된 장소이외의 운동시설에서는 음식물 반입을 금지한다. (자판기 커피포함) <br>
 7) 대관은 허가된 시설에만 적용되므로 일체의 집기나 운동용품은 사전 허락 후 사용한다. <br>
 8) 지정된 대관 시설 외에는 사전허가 없이 출입 또는 사용을 금지한다.(샤워시설 등) <br>
 9) 시설 등을 파손하였을 때에는 사용자의 과실로 배상해야함을 원칙으로 한다.<br>
 10) 기타 사항은 체육시설이용내규에 따른다.</td>
			</tr>
			</table>
		</div>

		<br>
		<br>


		
	</div>
	
	
<%
} 
%>

</div>