<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/board/park/dwict.board.pubParkApply.js"></script>


<script type="text/javascript">
$(function(){		
	/**
	 * @Summary 서브
	 * @Discription 서브 > 본문html > 탭메뉴2__onoff
	 */
	$('.tab_ty2_selbox_1a > div > a').on('click', function(){
		$(this).next().slideToggle(150);
	});
	/**
	 * @Summary 서브
	 * @Discription 서브 > 본문html > 탭클릭에따른콘텐츠onoff
	 */
	$('.jsc_tab1a_con').css('display','none');
	$('.jsc_tab1a_con').eq(0).css('display','');
	$('.jsc_tab1a_btn a').on('click',function(){
		if(!$(this).parent().hasClass('on')){
			var idx=$(this).parent().index();
			$('.jsc_tab1a_btn li').removeClass('on');
			$('.jsc_tab1a_con').css('display','none');
			$('.jsc_tab1a_btn li').eq(idx).addClass('on');
			$('.jsc_tab1a_con').eq(idx).css('display','');
		}
	});
});
</script>

<div class='sub_useinfo01'>

	<div class='clbx'>
		<ul class='tab_ty1_cnt_4a jsc_tab1a_btn'>
			<li class='on'><a href='javascript:;'>회원등록</a></li>
			<li><a href='javascript:;'>접수방법</a></li>
			<li><a href='javascript:;'>강좌환불</a></li>
			<li><a href='javascript:;'>자유이용</a></li>
			<li><a href='javascript:;'>이용약관</a></li>
		</ul>
	</div>

	<br>
	<br>
	<br>

	<!-- 회원등록안내 -->
	<div class='jsc_tab1a_con'>
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>회원등록안내</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>이용기간</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>			
			<div class='margin_top_0dot5 padding_left_1dot0'>
				<span class='fc_orange1'>매월 1일 해당 요일부터 말일까지</span>월단위 접수제(단, 헬스는 수시접수이며, 방문접수만 가능합니다.)<br>
				※접수기간은 사정에 따라 변동 될 수 있으니 접수 전 꼭 확인바랍니다.<br>
			</div>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>접수기간</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/접수기간/방문접수시간/인터넷접수시간 정보로 구성되어 있습니다.'>
			<caption>접수시간</caption>
			<tr>
				<th colspan="2">구분</th>				
				<th>접수기간</th>
				<th>비고</th>
			</tr>
			<tr>
				<td rowspan="3">방문접수</td>
				<td>기존회원</td>
				<td>매월 11일~23일(07:00~20:00)</td>
				<td>토 07:00~19:00<br>공휴일은 접수 불가</td>
			</tr>
			<tr>				
				<td>반변경</td>
				<td>매월 11일~23일(07:00~20:00)</td>
				<td>동일 종목에 한함(1회 가능)</td>
			</tr>
			<tr>				
				<td>신규회원</td>
				<td>매월 24일~익원 10일까지(07:00~20:00)<br>단, 첫날 06:00~</td>
				<td>선착순모집<br>단, 24일이 일.공휴일일 경우 25일부터~</td>
			</tr>
			<tr>
				<td rowspan="2">인터넷접수</td>
				<td>기존회원</td>
				<td>매월 11일~23일(09:00~)</td>
				<td></td>
			</tr>
			<tr>				
				<td>신규회원</td>
				<td>매월 24일~익월 10일까지(09:00~)</td>
				<td></td>
			</tr>
			</table>
		</div>
		<div class='margin_top_0dot5 lineheight_1dot8'>
			<ul class='ul_hyphen1a13'>
				<li>환불은 방문, 전화, 팩스로 접수 가능합니다.</li>
				<li>신규회원의 경우 회원카드는 안내데스크에서 수령 가능합니다.</li>				
			</ul>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>유의사항</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			종목선택 후 센터에 비치되어있는 수강신청서을 작성하시고 강습료와 함께 제출하시기 바랍니다.<br>
			법정공휴일은 휴강합니다.<br>
			매월첫째, 셋째, 다섯째주 일요일은 구민체육센터 정기휴관입니다.<br>
			회원증 분실 및 훼손시는 1층 접수처에서 재발급 받으시기 바랍니다.<br>(분실시 재발급비용은 본인 부담금1,000원입니다.)<br>
			센터의 사정에 따라 프로그램이 변경 폐강 될 수 있습니다.<br>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>기존 회원 반변경</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			매월11일~익월10일까지 1회에 한해 변경가능함(07:00~21:00)<br>
			기존회원은 기존접수전 반변경신청상담을 받으시고 재등록하시기바랍니다.<br>
			1회만 가능합니다.(반변경 차액이 동일금이상 발생시에 변경가능함.)<br>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>회원 할인안내</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			<span class='fc_orange1'>인터넷 등록 시 할인적용이 불가합니다.</span><br>
			해당증빙서류를 준비하시어 센터에 직접 방문 후 현장 수납하여 주시기 바랍니다.<br>
		</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 할인율/할인대상 정보로 구성되어 있습니다.'>
			<caption>회원할인안내</caption>
			<tr>
				<th>구분</th>
				<th>할인율</th>
				<th>증빙서류</th>
				<th>비고</th>
			</tr>
			<tr>
				<td>장애인(1~6급)</td>
				<td>50%</td>
				<td>장애인복지카드</td>
				<td>본인</td>
			</tr>
			<tr>
				<td>장애인(1~3급)<br>+ 동반1인</td>
				<td>50%</td>
				<td>장애인복지카드</td>
				<td>본인 및 운동보호자 1인</td>
			</tr>
			<tr>
				<td>국가유공자<br>+ 배우자</td>
				<td>50%</td>
				<td>국가유공자증,<br>유족증카드</td>
				<td>본인 및 배우자 1인,<br>국가유공자 또는 유족증 소지 대상자</td>				
			</tr>
			<tr>
				<td>의사상자</td>
				<td>50%</td>
				<td>의사상자증</td>
				<td>의사상자 소지 대상자</td>
			</tr>
			<tr>
				<td>기초생활수급자</td>
				<td>50%</td>
				<td>확인서류</td>
				<td>본인 기초생활수급대상자 서류<br>(매월 증명서 제출)</td>
			</tr>
			<tr>
				<td>다자녀가구<br>(세자녀)</td>
				<td>50%</td>
				<td>다둥이행복카드 등본서류<br>(가족관계증명서, 보험증 등)</td>
				<td>세자녀 이상 다둥이 행복카드 소지자의 자녀할인<br>(만 18세 이하 자녀만 할인적용)</td>
			</tr>
			<tr>
				<td>경로우대</td>
				<td>30%</td>
				<td>주민등록증</td>
				<td>본인 만 65세 이상</td>
			</tr>
			<tr>
				<td>다자녀가구<br>(두자녀)</td>
				<td>20%</td>
				<td>다둥이행복카드 등본서류</td>
				<td>두자녀 이상 다둥이 행복카드 소지자의 자녀할인<br>(만 18세 이하 자녀만 할인적용)</td>
			</tr>
			<tr>
				<td>가임여성</td>
				<td>10%</td>
				<td>주민등록증</td>
				<td>13세~55세 여성<br>(단, 수영종목에 한함)</td>
			</tr>
			</table>
		</div>
		<div class='margin_top_0dot5 lineheight_1dot8'>
			<ul class='ul_hyphen1a13'>
				<li>할인 종목이 2개 이상 중복되는 경우, 감면비율이 가장 높은 1개 항목만 적용됩니다.</li>								
			</ul>
		</div>
	</div>


	<!-- 접수방법 -->
	<div class='jsc_tab1a_con'>
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>접수방법안내</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>방문접수</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			직접방문 -> 강좌선택 -> 신청서작성 -> 회원가입 접수 -> 회원등록회원등록 신청서(센터비치), 신분증, 수강료
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>인터넷접수</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			1.중랑구 시설관리공단 체육센터(http://jungnangspo.seoul.kr)에 접속<br>
			(기존시설이용자는 [[ 기존시설이용자 회원가입]] 팝업창 클릭 후 회원가입)<br>
			2.회원가입 및 로그인<br>
			3.수강신청>중랑문화체육관 에서 수강신청 클릭 후 수강신청<br>
			개강시 중랑문화체육관 1층 안내데스크에서 회원카드 수령 후 이용<br>
			<div class='padding_left_1dot0'>
				<ul class='ul_hyphen1a13'>
					<li>인터넷 접수 시 결재가 동시에 진행되므로 카드사별 인터넷 결재 방법을 사전 숙지 하시기 바랍니다.</li>
					<li>온라인 결제 취소는 평일 업무시간(09:00~18:00)내에만 가능합니다.</li>
					<li>인터넷 접수로 등록하신 경우라도 반변경은 방문신청만 가능합니다.</li>
					<li>강좌에 따라 인터넷 접수가 제한되는 것도 있습니다.</li>
					<li>접수상황에 따라 프로그램의 조정이 있을 수 있습니다.</li>
				</ul>
			</div>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>사물함 대여</div>
			<div class='padding_left_1dot0 margin_top_1dot0'>

				<ul class='ul_hyphen1a13'>
					<li>사물함 임대비용 : 20,000원(현금)</li>
					<div class='padding_left_1dot0'>보증금(현금) 10,000원,</div>
					<div class='padding_left_1dot0'>사용료 10,000원(3개월)</div>
					<li>사물함키 반납시 보증금 환불(계좌입금)</li>
					<li>반납일이 사용기간을 경과할 경우 보증금에서 일할계산 공제후 환불</li>
					<li>키분실 제작비용 10,000원</li>
					<li>회원카드 분실시 재발급비용 1,000원</li>								
				</ul>
			</div>
		

	</div>


	<!-- 강좌환불 -->
	<div class='jsc_tab1a_con'>
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>강좌환불</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>강좌환불</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			<span class='fc_orange1'>근거 : 소비자피해보상규정(재정경제부 고시 제2006-36호)에 의거 함.</span><br>
			<div class='padding_left_1dot0'>
				<ul class='ul_hyphen1a13'>
					<li>개강일 이전 환불 : 총수강료의 10% 공제 후 은행계좌로 송금해드립니다.</li>
					<li>개강일 이후 환불 : 총수강료의 10% +발생된이용일수(당일포함) 공제후 은행계좌로 송금해드립니다.</li>
					<li><span class='fc_orange1'>개강일 이후에 접수하였더라도 환불은 개강일 기준으로 정산됩니다.</span></li>
					<li>인원미달등의 사유로 강좌가 폐강될 수 있으며, 폐강시 전액환불 해드립니다.</li>
					<li>환불신청시 통장사본을 지참하여 주시기 바랍니다.</li>
					<li>수강기간이 만료 또는 지난 강좌에 대해서는 환불되지 않습니다.</li>
				</ul>
				<div class='padding_left_1dot0'>※수강을 연기할 수는 없으며 환불과 동일하게 처리합니다.</div>
			</div>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>환불 절차</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			1. 환불신청서 작성<br>
			2. 구비서류(신분증, 회원증, 영수증, 통장사본)제출<br>
			3. 환불 행정조치<br>
			4. 환불 시행(신청일로부터 3일 이내 계좌 입금)<br>
			※ 체육관 사정에 의해 변동될 수 있음
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>환불 신청시 유의사항</div>
		<div class='padding_left_1dot0 margin_top_1dot0 lineheight_1dot8'>
			<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>회원본인</div>
			<div class='margin_top_0dot5 padding_left_1dot0'>
				<ul class='ul_hyphen1a13'>
					<li>본인확인(주민등록증), 환불신청서 작성</li>
					<li>환불금 송금시 통장명의는 반드시 회원 본인의 통장이여야 함.</li>
				</ul>
			</div>

			<br>

			<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>친권자 및 직계 존비속</div>
			<div class='margin_top_0dot5 padding_left_1dot0'>
				<ul class='ul_hyphen1a13'>
					<li>관계증명서 제출(등본 또는 의료보험증 등), 통장사본, 환불신청서작성</li>
				</ul>
			</div>

			<br>

			<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>제3자</div>
			<div class='margin_top_0dot5 padding_left_1dot0'>
				<ul class='ul_hyphen1a13'>
					<li>양도인의 인감증명서 및 양도신청서 , 통장사본, 환불신청서 작성</li>
					<li>환불금 송금시 통장명의는 반드시 회원 본인의 통장이여야 함.</li>
					<li>환불 신청시 해당 첨부서류를 반드시 제출하여 주시기 바랍니다.</li>
				</ul>
			</div>
		</div>
	</div>


	<!-- 자유이용 -->
	<div class='jsc_tab1a_con'>
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>자유이용안내</div>
		
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>헬스</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/운영시간/회비/유의사항 정보로 구성되어 있습니다.'>
			<caption>헬스</caption>
			<tr>
				<th>구분</th>
				<th>운영시간</th>
				<th>회비(원)</th>
				<th>유의사항</th>
			</tr>
			<tr>
				<td>토요일</td>
				<td>06:00~21:00</td>
				<td rowspan='2' class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>성인 2,500</li>
						<li>중고생 1,800</li>
					</ul>
				</td>
				<td rowspan='2'>휴일 개별운동</td>
			</tr>
			<tr>
				<td>일요일</td>
				<td>09:00~17:00(1,3주)</td>
			</tr>
			</table>
		</div>

		<div class='margin_top_0dot5 lineheight_1dot8'>
			<span class='fc_orange1'>※ 정기휴관 : 매월 둘째, 넷째, 다섯째 일요일 및 법정공휴일</span>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>유의사항</div>
		<div class='padding_left_1dot0 margin_top_0dot5 lineheight_1dot8'>
			<ul class='ul_hyphen1a13'>
				<li>중환자 또는 지병(고혈압, 심장질환등) 보유로 운동이 곤란하신 분은 전문의사의 지시에 따라 체육프로그램에 참여하시기 바랍니다.</li>
				<li>개인의 부주의, 질병으로 불미스런 일이 발생하였을 경우 책임은 전적으로 본인에게 있습니다.</li>
			</ul>
		</div>
	</div>


	<!-- 아기스포츠단 -->
	<div class='jsc_tab1a_con'>
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>아기스포츠단</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>아기스포츠단 구성</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/총원 등의 정보로 구성되어 있습니다.'>
			<caption>아기스포츠단 구성</caption>
			<tr>
				<th>구분</th>
				<th>총원</th>
				<th>5세<br>토끼</th>
				<th>6세<br>호랑이</th>
				<th>7세<br>백곰</th>
			</tr>
			<tr>
				<td>정원</td>
				<td>70명</td>
				<td>16명</td>
				<td>22명</td>
				<td>26명</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>교육시간</div>
		<div class='padding_left_1dot0 margin_top_0dot5 lineheight_1dot8'>
			<ul class='ul_hyphen1a13'>
				<li>09:00~14:00(월~금)</li>
			</ul>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>교육과정 구성</div>
		<div class='padding_left_1dot0 margin_top_0dot5 lineheight_1dot8'>
			<ul class='ul_hyphen1a13'>
				<li>아기스포츠단 교육 과정 운영은 연간, 월간, 주간, 일일 지도계획을 수립하여 시행합니다.</li>
				<li>학습교육 활동은 유치부 교육과정에 적합하고 다양한 프로그램식 수업으로 진행합니다.</li>
				<li>모든 교육 활동 속에서 예절, 질서, 청결, 절제의 기본 생활 습관이 형성되도록 지도합니다.</li>
			</ul>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>주요 교육과정</div>

		<br>

		<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>수업시수</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 총시간/구분/내용 정보로 구성되어 있습니다.'>
			<caption>주요 교육과정</caption>
			<tr>
				<th>총시간</th>
				<th>구분</th>
				<th>내용</th>
			</tr>
			<tr>
				<td rowspan='3' class='border_r1'>20</td>
				<td>유아교육</td>
				<td class='ali_l'>이야기나누기, 기본생활습관, 식사지도, 언어, 문학, 과학, 미술, 동요,동시, 종이접기, 악기연주, NIE, 초등대비교육(7세)</td>
			</tr>
			<tr>
				<td>외부강사</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>7세 : 영어(4), 음악줄넘기(1), 키즈플레이(1), 유아체육(1), 악기놀이(1)</li>
						<li>6세 : 영어(2), 음악줄넘기(1), 키즈플레이(1), 유아체육(1), 악기놀이(1)</li>
						<li>5세 : 키즈플레이(1) 유아체육(1) 악기놀이(1)</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>수 영</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>7세 : 수영(5)</li>
						<li>6세 : 수영(3)</li>
						<li>5세 : 수영(2)</li>
					</ul>
				</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>유아교육 프로그램 주요내용</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/내용 정보로 구성되어 있습니다.'>
			<caption>유아교육 프로그램 주요내용</caption>
			<tr>
				<th>구분</th>
				<th>내용</th>
			</tr>
			<tr>
				<td>이야기나누기</td>
				<td class='ali_l'>단원들이 모여 앉아 하루 일과를 계획하고, 계절의 변화, 생활 경험과 느낌, 놀이 활동에서 얻어지는 여러 가지 학습내용을 토의ㆍ질문ㆍ발표하는 기회를 갖는다.</td>
			</tr>
			<tr>
				<td>기본생활습관</td>
				<td class='ali_l'>놀이감을 정리ㆍ정돈하며 급식지도, 위생습관지도로 단원들의 청결 한 생활 및 질서에 대한 올바른 습관형성을 돕는다.</td>
			</tr>
			<tr>
				<td>언 어</td>
				<td class='ali_l'>보고, 느끼고, 듣고, 생각한 것 등을 바르게 이야기하며, 남의 말 을 정확히 듣는 경험을 갖는다.</td>
			</tr>
			<tr>
				<td>한 자</td>
				<td class='ali_l'>어릴 때 부터 바른 한자를 익혀 성인이 되어서도 부담 없이 한자 문화권인 우리 문화를 이해할 수 있고 더 나아가 국제화 시대의 주역이 될 수 있다.</td>
			</tr>
			<tr>
				<td>수 학</td>
				<td class='ali_l'>창의적이고 논리적인 사고를 통해 종합적으로 문제를 해결할 수 있도록 한다.</td>
			</tr>
			<tr>
				<td>과 학</td>
				<td class='ali_l'>생명의 소중함을 느끼고 자연의 고마움에 감사할 줄 알게 하며, 실험을 통해 ‘왜?’라는 의문을 갖게 하여 미지의 세계에 대한 흥미를 일깨워 준다. 또한, 과학적으로 사고하는 방법을 배운다.</td>
			</tr>
			<tr>
				<td>미 술</td>
				<td class='ali_l'>찢고 그림을 그리고, 자르고, 붙이는 등 여러 가지 자료를 이용하 여 구성하는 미술 활동은 어린이들의 생각을 표현하는 창의적 활동인 동시에 작품을 생산하는 작업으로서의 가치를 지닌다.</td>
			</tr>
			<tr>
				<td>동 요</td>
				<td class='ali_l'>아름다운 노래 부르기, 즐거운 동작(춤)놀이, 생각과 느낌을 표현 하기, 다양한 악기연주, 좋은음악 감상하기 등의 경험을 제공한다.</td>
			</tr>
			<tr>
				<td>블 록</td>
				<td class='ali_l'>손쉬운 조립 및 조작을 통하여 아이들의 오감발달 및 창의력 향상</td>
			</tr>
			<tr>
				<td>문 학</td>
				<td class='ali_l'>언어 및 창의적 사고력을 유발하기 위하여 동화를 통해 표현력, 발표력, 문예 창작을 개발할 수 있는 프로그램</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>외부강사 수업 주요 내용</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/내용 정보로 구성되어 있습니다.'>
			<caption>외부강사 수업 주요 내용</caption>
			<tr>
				<th>구분</th>
				<th>내용</th>
			</tr>
			<tr>
				<td>영 어</td>
				<td class='ali_l'>다양한 활동 교수법(Songs, Games, Chants, Storytelling)을 통해 유아들이 재미있게 영어로 보고, 듣고, 말하고 생각 할 수 있도록 이끌어 주는 프로그램</td>
			</tr>
			<tr>
				<td>오르프<br>(악 기)</td>
				<td class='ali_l'>음악 및 악기연주를 통하여 유아들이 자신의 감정과 생각을 표현하며 정서적 안정과 음악적 감성을 기르며, 민첩성과 두뇌 발달 및 협응력, 인지 능력 향상</td>
			</tr>
			<tr>
				<td>음악줄넘기</td>
				<td class='ali_l'>다양한 스텝과 기술을 통해 줄넘기를 재미있게 배우고, 신체발달이 빠른 유아기 아이들의 성장판을 자극하여 체력 증진 및 키크기에 도움을 주는 프로그램</td>
			</tr>
			<tr>
				<td>체 육</td>
				<td class='ali_l'>다양한 대기구 및 소기구 수업을 통해 유아들의 신체 각 부위의 균형발달을 촉진시키며, 흥미를 유발하는 그룹게임으로 협동심 함양과 사회생활을 배우고 율동을 통한 정서 창의력 발달</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>수영교육 주요내용(단계식 유아 수영지도)</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/연차/학기/내용 정보로 구성되어 있습니다.'>
			<caption>수영교육 주요내용(단계식 유아 수영지도)</caption>
			<tr>
				<th>구분</th>
				<th>연차</th>
				<th>학기</th>
				<th>내용</th>
			</tr>
			<tr>
				<td rowspan='2' class='border_r1'>5세</td>
				<td colspan='2'>1학기</td>
				<td>물적응, 호흡법, 수평뜨기, 발차기</td>
			</tr>
			<tr>
				<td colspan='2'>2학기</td>
				<td>사이드킥, 호흡법, 킥판 잡고 자유형 팔 돌리기</td>
			</tr>
			<tr>
				<td rowspan='4' class='border_r1'>6세</td>
				<td rowspan='2' class='border_r1'>1년차</td>
				<td>1학기</td>
				<td>물적응, 호흡법, 킥판 잡고 자유형 팔 돌리기</td>
			</tr>
			<tr>
				<td>2학기</td>
				<td>자유형, 배영</td>
			</tr>
			<tr>
				<td rowspan='2' class='border_r1'>2년차</td>
				<td>1학기</td>
				<td>자유형, 배영</td>
			</tr>
			<tr>
				<td>2학기</td>
				<td>자유형, 배영, 평영</td>
			</tr>
			<tr>
				<td rowspan='6' class='border_r1'>7세</td>
				<td rowspan='2' class='border_r1'>1년차</td>
				<td>1학기</td>
				<td>물적응, 호흡법, 킥판 잡고 자유형 팔 돌리기</td>
			</tr>
			<tr>
				<td>2학기</td>
				<td>자유형, 배영, 평영</td>
			</tr>
			<tr>
				<td rowspan='2' class='border_r1'>2년차</td>
				<td>1학기</td>
				<td>자유형, 배영, 평영 킥</td>
			</tr>
			<tr>
				<td>2학기</td>
				<td>자유형, 배영, 평영</td>
			</tr>
			<tr>
				<td rowspan='2' class='border_r1'>3년차</td>
				<td>1학기</td>
				<td>자유형, 배영, 평영, 접영 킥</td>
			</tr>
			<tr>
				<td>2학기</td>
				<td>자유형, 배영, 평영, 접영</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>체육교육 주요내용(단계식 지도)</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 구분/내용/활동 정보로 구성되어 있습니다.'>
			<caption>체육교육 주요내용(단계식 지도)</caption>
			<tr>
				<th>구분</th>
				<th>내용</th>
				<th>활동</th>
			</tr>
			<tr>
				<td>율동체조</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>음악을 통한 정서발달</li>
						<li>음악을 통한 창조력 발달</li>
						<li>율동을 통한 리듬감각 및 자기표현 능력 발달</li>
					</ul>
				</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>꿀벌들의 여행</li>
						<li>아기 염소</li>
						<li>멋진 사나이 등</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>신체적성</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>신체 각 부위의 균형된 발달 및 신체 기능의 발달</li>
						<li>신체의 각 기능발달, 기초체력 육성</li>
						<li>스포츠의 생활화 습관 육성</li>
					</ul>
				</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>팔 벌려 뛰기</li>
						<li>물구나무 서기</li>
						<li>산 만들기</li>
						<li>복수 릴레이</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>기구놀이</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>기구를 다루는 능력 발달</li>
						<li>자신감을 가질 수 있는 담력 육성</li>
						<li>강인한 정신력 육성</li>
						<li>자신의 안전 능력 발달</li>
					</ul>
				</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>매트 구르기</li>
						<li>뜀틀, 철봉</li>
						<li>평균대 건너기</li>
						<li>줄넘기 등</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>그룹게임</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>흥미를 유발하는 창조력 발달</li>
						<li>마스게임을 통한 협동심 함양</li>
					</ul>
				</td>
				<td class='ali_l'>
					<ul class='ul_hyphen1a13'>
						<li>꽃 만들기</li>
						<li>지네 게임</li>
					</ul>
				</td>
			</tr>
			</table>
		</div>

		<br>
		<br>

		<div class='bg_icon_round_gray3c10 fontsize_1dot15 padding_left_1dot0'>연간 행사 계획</div>

		<div class='autoscroll_x1 margin_top_1dot0'>
			<table class='stbl_l1a con_tbl1' summary='이 표는 월/행사명/비고 정보로 구성되어 있습니다.'>
			<caption>연간 행사 계획</caption>
			<tr>
				<th>월</th>
				<th>행사명</th>
				<th>비고</th>
			</tr>
			<tr>
				<td>3</td>
				<td>입단식 / 제1차 신체적성검사 / 요리활동</td>
				<td></td>
			</tr>
			<tr>
				<td>4</td>
				<td>안전교육(소방훈련) / 도서관 현장학습</td>
				<td></td>
			</tr>
			<tr>
				<td>5</td>
				<td>어린이날 행사 / 봄 소풍 / 안전교육(성교육)</td>
				<td></td>
			</tr>
			<tr>
				<td>6</td>
				<td>학부모 참여수업 / 요리활동</td>
				<td></td>
			</tr>
			<tr>
				<td>7</td>
				<td>여름캠프 / 수영ㆍ체육 공개수업 / 안전교육(수상안전교육)/ 여름방학</td>
				<td rowspan='2'>여름방학 2주간</td>
			</tr>
			<tr>
				<td>8</td>
				<td>여름방학 / 제2차 신체적성검사</td>
			</tr>
			<tr>
				<td>9</td>
				<td>제1차 견학학습 / 전통놀이 / 요리활동</td>
				<td></td>
			</tr>
			<tr>
				<td>10</td>
				<td>가족 운동회 / 안전교육(교통안전교육)</td>
				<td></td>
			</tr>
			<tr>
				<td>11</td>
				<td>제2차 견학학습 / 요리활동</td>
				<td></td>
			</tr>
			<tr>
				<td>12</td>
				<td>산타잔치 / 수영ㆍ체육 공개수업 / 겨울방학</td>
				<td rowspan='2'>겨울방학 2주간</td>
			</tr>
			<tr>
				<td>1</td>
				<td>겨울방학 / 제3차 신체적성검사 / 전통놀이</td>
			</tr>
			<tr>
				<td>2</td>
				<td>료식 / 학부모 오리엔테이션</td>
				<td></td>
			</tr>
			</table>
		</div>

		<div class='margin_top_0dot5 lineheight_1dot8'>
			상기일정은 센터 사정에 의하여 변경ㆍ조정될 수 있습니다.
		</div>
	</div>


	<!-- 이용약관 -->
	<div class='jsc_tab1a_con'>
		<div class='bg_icon_circle_green1a fontsize_1dot60 padding_left_1dot5'>중랑구 시설관리공단 체육시설 이용약관</div>

		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 1 장 총 칙</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제1조 (목적)</strong><br>
			<br>

			① 이 내규는 서울특별시 중랑구 시설관리공단에서 운영하는 중랑구민체육센터, 중랑문화체육관, 면목2동체육관의 이용에 관한사항에 대하여 정함을 목적으로 한다.<br>

			<br>
			<br>


			<strong>제2조 (정의)</strong><br>
			<br>

			① 이 내규에서 사용하는 용어의 정의는 다음과 같다.<br>
			<div class='padding_left_1dot0'>1. “회원”이라 함은 중랑구민체육센터, 중랑문화체육관, 면목2동체육관(이하 “체육시설”이라 한다) 이용을 위한 소정의 등록절차를 거쳐 일정기간 동안 이용하고 있는 자를 말한다.</div>
			<div class='padding_left_1dot0'>2. “체육시설물”이라 함은 회원의 체육활동에 지속적으로 이용되는 체육시설내 모든 시설과 그 부대시설을 말한다.</div>
			<div class='padding_left_1dot0'>3. “등록일”이라 함은 체육시설의 회원이 되고자 등록을 신청하여 소정의 절차를 마친 일자를 말한다.</div>
			<div class='padding_left_1dot0'>4. “개시일”이라 함은 체육시설에 회원의 등록신청에 따른 체육시설 이용시작일자를 말한다.</div>

			<br>
			<br>


			<strong>제3조 (효력발생 및 변경)</strong><br>
			<br>

			① 이 내규는 체육시설 안내게시판을 통해 게시 하거나 서울특별시 중랑구 시설관리공단(이하“공단”이라한다) 홈페이지, 체육관 홈페이지에 공지함으로서 효력이 발생된다. (개정 2013.03.21)<br>
			② 이 내규는 정당한 사유의 발생 등으로 변경할 수 있으며, 변경된 내규는 제1항과 동일한 방법으로 공지하여야 한다.<br>

			<br>
			<br>


			<strong>제4조 (이용계약의 성립)</strong><br>
			<br>

			① 체육시설을 이용하고자 하는 자는 이 내규에서 정한 신청서로 이용신청을 하고 이용료를 납부함으로서 이용계약이 성립된다.<br>
			②〈삭제 2013.03.21〉<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 2 장 체육시설과 회원과의 의무</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제5조 (체육시설의 의무)</strong><br>
			<br>

			① 체육시설은 이용자에게 쾌적하고 안전한 시설물 사용에 최선의 노력과, 이용에 불편함이 없도록 유지관리 제공의 의무를 갖는다. 단, 천재지변, 비상사태 및 기타 부득이한 경우 체육시설물 이용일을 일시 중지하거나 부분적으로 제한할 수 있다.<br>

			<br>
			<br>


			<strong>제6조 (게시의 의무)</strong><br>
			<br>

			① 체육시설은 회원이 용이하게 볼 수 있는 장소에 다음 사항이 게시하여야한다.<br>
			<div class='padding_left_1dot0'>1. 시간별 프로그램 내용 및 정원</div>
			<div class='padding_left_1dot0'>2. 프로그램 신설 및 증설</div>
			<div class='padding_left_1dot0'>3. 프로그램 이용료</div>
			<div class='padding_left_1dot0'>4. 이용내규 내용</div>
			<div class='padding_left_1dot0'>5. 시설물을 이용하는데 필요한 주의사항</div>

			<br>
			<br>


			<strong>제7조 (회원의 의무)</strong><br>
			<br>

			① 회원은 쾌적하고 안전한 체육시설물 이용을 위해 체육시설 관계자의 주의사항에 따라야한다.<br>
			② 회원은 체육시설물을 보호하고 아껴 쓸 의무를 지닌다.<br>
			③ 회원은 이용내규 및 사용자 준수사항 등 명시된 제반사항을 준수하여야 하며, 지도자 또는 안전요원의 지시에 응해야 한다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 3 장 회원 가입</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제8조 (회원의 종류)</strong><br>
			<br>

			① “월 강습회원”이라 함은 매월 1일부터 말일까지 프로그램별로 강습이용등록을 체결한 회원을 말한다.<br>
			② “월 개인회원”이라 함은 매월 1일부터 말일까지 체육시설이 정한 시간에 개인연습을 체육관과 체결한 회원을 말한다.(수시접수 종목은 당월 개시일 부터 익월 개시일 전일까지 이용함.)<br>
			③ “일일회원”이라 함은 체육시설의 프로그램에 명시되어 있는 시간에 이용을 체결한 회원을 말한다.<br>

			<br>
			<br>


			<strong>제9조 (회원의 자격)</strong><br>
			<br>

			① 체육시설을 이용할 수 있는 회원의 자격은 이용등록을 체결한 회원을 말한다.<br>

			<br>
			<br>


			<strong>제10조 (회원 모집)</strong><br>
			<br>

			① 체육시설의 회원을 모집하는 방법은 다음과 같다.<br>
			<div class='padding_left_1dot0'>
				1. “정시 모집”이라 함은 매월 11일부터 익월10일까지 회원등록을 받는 방법을 말하며, 통상 월 회원 모집의 방법은 이에 준한다.<br>
				<div class='padding_left_1dot0'>
					(기존회원 : 매월 11일～23일, 신규회원 : 매월 24일～익월10일까지) 〈개정 2017.01.26〉
				</div>
				2. 모집시기 및 모집방법은 체육시설의 사정에 따라 조정할 수 있다.<br>
				3. 회원모집정원은 체육시설의 최대 수용인원을 초과하지 않는 범위 내에서 정하며, 운영상 필요에 따라 조정할 수 있다.<br>
			</div>

			<br>
			<br>


			<strong>제11조 (회원 가입신청)</strong><br>
			<br>
			① 체육시설의 이용을 원하는 자가 체육시설이 정한 서식1호에 의하여 신청서를 기재하여 제출하고 회비를 납부함으로서 회원의 자격을 갖게 되며 이용내규에 동의한 것으로 간주한다.<br>
			② 회원 가입은 인터넷 홈페이지 및 당 체육시설에 직접 내방하여 신청하며, 대리신청은 신청인의 신분증을 지참하여야 한다.<br>
			③ 체육센터에서 정한 회원가입신청서의 기재내용이 사실과 다르거나 고의 또는 부주의로 누락된 경우 이로 인한 불이익은 회원이 부담하여야 한다.<br>

			<br>
			<br>


			<strong>제12조 (사용료)</strong><br>
			<br>
			① 체육시설의 사용료는 변경 및 조정이 가능하며, 이에 대한 공지는 2주전부터 게시판에 게시하고, 이용기간이 종료된 후부터 변경된 사용료를 적용한다.<br>
			② 사용료의 납부는 현금수납과 신용카드결재방법으로 납부한다.(대관료 및 인터넷 회원등록의 경우 계좌이체 가능)<br>
			③ 현금수납시 회원이 원할 경우 국세청에 등록된 현금영수증을 발행한다.<br>
			④ 사용료의 할인 및 할증에 대한 사항은 「서울특별시중랑구립체육시설설치및운영에관한조례」 및 공단 이사장 방침사항에 따라 적용한다.<br>

			<br>
			<br>


			<strong>제13조 (회원증)</strong><br>
			<br>
			① 이 내규에 의해 정당한 절차를 거쳐 회원 자격을 취득한 회원에게 회원증을 발급하며, 회원은 관계자의 요구가 있을 시에 회원증을 제시하여야 한다.<br>
			② 회원증의 양도, 양수 및 명의 변경은 다른 규정에 특별히 정하는 사항이 없는 경우 사용료 환불제도에 따라 변경처리 한다.〈개정 2016.08.30.〉<br>
			③ 회원증은 최초 월 회원 등록 시 무상으로 발급하며, 분실 및 훼손 등으로 재발급 시 1,000원의 수수료를 부담하여야 한다.〈개정 2013.03.21.〉<br>
			단, 1년 이상 사용 후 노후로 인한 바코드 훼손 시는 무상으로 발급한다.〈개정 2015.09.21〉<br>

			<br>
			<br>


			<strong>제14조 (회원의 자격제한)</strong><br>
			<br>

			① 체육시설의 회원 또는 회원이 되고자하는 자가 다음의 각 호에 해당하는 경우에는 등록거부나 회원의 자격을 한시적, 영구적 이용제한 조치를 취할 수 있다.<br>
			<div class='padding_left_1dot0'>
				1. 심신질환이나 전염성 질병 등으로 다른 회원에게 피해를 주거나 혐오감을 느끼게 하는 경우<br>
				2. 삭제〈개정 2016.08.30.〉<br>
				3. 고의적(상습적)으로 도강을 하는 회원<br>
				4. 지도자의 수업방식에 대해서 지나치게 간섭을 하거나 회원들을 선동하는 행위<br>
				5. 다른 회원에게 상습적으로 폭언 및 폭행하는 회원<br>
				6. 체육시설의 이용내규를 위반하거나, 강습질서를 문란하게 한 회원<br>
			</div>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 4 장 이용회원의 해제(환불 및 변경)</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제15조 (이용회원의 해제 및 해지사유)</strong><br>
			<br>

			① 회원은 다음 각 호의 사유가 있을 경우 이용사항 해제 또는 해지할 수 있다.<br>
			<div class='padding_left_1dot0'>
				1.이용시설 및 기구 등의 이상으로 정상적인 이용이 곤란한 경우<br>
				2.이전, 휴업, 폐업, 정원초과 등으로 체육시설의 프로그램 이용이 곤란한 경우<br>
				3.기타 체육시설의 부득이한 사유로 인한 정상적인 이용이 곤란한 경우<br>
				4.모집 후 정원에 크게 미달되어 운영이 불가 할 경우<br>
			</div>

			<br>
			<br>


			<strong>제16조 (이용회원의 해제 및 해지로 인한 환불)</strong><br>
			<br>

			① 회원은 체육시설이 정한 절차를 통해 프로그램 개시일 또는 종료일 이전에 서식2호에 의하여 환불신청서를 제출하여 체육시설에 환불을 요청할 수 있으며, 환불기준은 다음 각 호의 기준을 준용한다.<br>
			<div class='padding_left_1dot0'>
				1. 환불금 정산 기준일자는 작성 완료한 환불신청서 제출일로 한다.〈개정 2015.09.21〉<br>
				2. 체육시설 개시일 이전 회원의 귀책사유로 인한 계약해제 및 해지 시 사용료의 10%공제 후 환불한다.<br>
				3 .개시일 이후 회원의 귀책사유로 인한 계약 해지 시 사용료의 10% 공제 및 개시일부터 해지요청일까지의 이용(강습)일수공제 후 환불하며, 환불은 방문, 전화, 팩스 접수로 한다.〈개정 2015.09.21〉<br>
				4. 개시일 이전 체육시설의 귀책사유로 인한 계약해제 및 해지 시에는 체육시설이 회원에게 회비를 전액 환불하고 회비의 10%배상(일일자유이용권으로 배상할 수 있다)〈개정 2013.03.21〉<br>
				5. 개시일 이후 체육시설의 귀책사유로 인한 계약해제 및 해지 시에는 체육시설이 회원에게 개시일 부터 해제일 까지의 프로그램 잔여횟수의 일할 계산한 금액과 회비의 10％배상(일일자유이용권으로 배상할 수 있다)〈개정 2013.03.21〉<br>
				6. 환불은 환불신청서를 제출한 날로부터 3영업일 이내에 회원 본인의 지정 계좌로 입금한다.〈개정 2017.02.22〉<br>
				7. 천재지변 등 불가항력의 사유로 계약이 해지되는 경우, 체육시설은 회비 전액을 환불하고 배상책임은 없다.<br>
				8. 등록인원 미달에 따른 프로그램 폐강인 경우에는 수강료 전액을 환불한다.<br>
				9. 환불일수의 계산은 환불신청서를 제출한 당일을 기준으로 하고, 월 이용요금은 해당 월의 잔여 이용일수를 기준으로 일할 계산한다.〈개정 2015.09.21〉<br>
				10. 환불신청은 회원본인이 직접 체육관의 소정 양식을 작성하여 제출하는 것을 원칙으로 하며, 대리 신청은 가족에 한하며 신청인은 가족관계를 확인할 수 있는 증빙서류를 제시하여야 한다.〈개정 2013.03.21〉<br>
				11. 계약연기 및 일시정지는 적용되지 않으며, 환불을 원칙으로 한다.〈개정 2015.09.21〉<br>
				12. 기타 사항은 환불 세부약관에 따른다.<br>
			</div>

			<br>
			<br>


			<strong>제17조 (회원자격의 소멸)</strong><br>
			<br>

			① 회원이 체육시설로부터 회비의 환불을 신청 하였을 경우 회원자격은 자동 소멸된다.〈개정 2013.03.21〉<br>
			다음 각 호에 해당하는 경우, 공단(체육시설)은 회원자격을 일시정지 또는 소멸시킬 수 있다.<br>
			<div class='padding_left_1dot0'>
				1. 회원이 소정의 월 이용료를 납부하지 아니한 경우<br>
				2. 회원증을 개인사정에 따라 환불받은 경우〈개정 2016.08.30.〉<br>
				3. 공단의 명예를 훼손시키고 체육시설 및 서비스 이용질서를 문란하게 하였을 때<br>
				4. 공단이 정한 회원 이용내규 및 기타 제규정을 위반하였을 때<br>
			</div>

			<br>
			<br>


			<strong>제18조 (프로그램 및 회원종류의 변경)</strong><br>
			<br>
			① 체육시설이 정한 회원가입신청서의 기재내역이 변경되었을 경우, 회원은 이 사실을 체육시설에 통보해야 하며, 이를 태만히 함으로써 발생되는 불이익은 회원이 부담하여야 한다.<br>
			② 동일 프로그램의 반 변경은 정원 범위 내에서 방문 및 상담을 통하여 환불수수료 공제 없이 가능하며, 수강료 차액에 대하여는 환불 및 추가 납부하여야 한다.〈개정 2015.09.21, 2016.08.30.〉<br>
			③ 승급에 의한 반변경은 직원에 의하여 일괄적으로 처리할 수도 있다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 5 장 체육시설 운영</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제19조 (이용시간)</strong><br>
			<br>

			① 체육시설의 운영시간은 평일 개장을 06시 00분부터 폐장은 22시 00분으로 하고, 회원은 프로그램별 이용시간을 준수해야 한다.(헬스프로그램은 1일 1회 이용을 원칙으로 한다.) 〈개정 2019.01.31〉<br>
			② 프로그램별 이용시간은 정당한 절차를 거쳐 조정할 수 있으며, 이에 대한 공지는 15일전부터 안내게시판이나 홈페이지에 게시한다.〈개정 2013.03.21, 2013.03.21, 2019.01.31〉<br>

			<br>
			<br>


			<strong>제20조 (이용기간 및 휴관)</strong><br>
			<br>

			① 체육시설을 이용하는 기간은 매월 1일에서 말일까지로 체육관이 정한 요일 및 시간에 한하며, 휴일(일요일 및 공휴일) 미대관시에는 휴관을 하며, 정당한 절차에 의해 조정할 수 있다.<br>
			② 체육시설은 사전에 정한 정기 휴관일 및 행정지시, 또는 시설의 정비 및 보수, 기타 운영상 불가피한 사유가 발생할 경우에는 휴관 또는 휴장을 할 수 있다.<br>
			③ 체육시설의 연간 시설정비 및 보수기간은 필요에 따라 정할 수 있으며, 회원에게는 이 기간만큼의 손실에 대하여 가능한 범위 내에서 최대한 보전해 주어야 한다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 6 장 대관행사 운영</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제21조(사용시간)</strong><br>
			<br>

			① 체육시설의 대관 사용은 평일 공실시간 및 휴일(일요일,공휴일 주간시간(09:00~18:00))에 한하며, 정당한 사유 및 절차에 따라 조정할 수 있다.〈개정 2015.09.21〉<br>

			<br>
			<br>


			<strong>제22조(사용신청)</strong><br>
			<br>

			① 대관 신청은 3개월 전부터 사용신청 가능하며, 서식3호 및 서식4호에 의하여 신청하고, 이에 대한 대관료는 사용 승인일 7일이내에 납부하여야 한다.〈개정 2013.03.21〉<br>
			② 대관 사용 승인일 7일이내에 대관료를 납부하지 않을 시에는 사용허가를 취소할 수 있으며, 이에 대하여 신청인에게 통보한다.<br>

			<br>
			<br>


			<strong>제23조 (대관료 환불)</strong><br>
			<br>

			① 대관료를 납부하고 대관을 취소하고자 할 경우 사용 당일포함 7일전에 취소통보를 하여야 하며, 통보가 없을시 대관료는 반환하지 아니한다.<br>
			② 체육경기 대관 환불은 서식5호에 의하여 사용 당일포함 7일전에 통보 후 전액 환불한다.<br>

			<br>
			<br>


			<strong>제24조 (대관행사 준수사항)</strong><br>
			<br>

			① 대관행사 사용자는 이용관련 각호사항을 준수하여야 한다.<br>
			<div class='padding_left_1dot0'>
				1. 체육시설과 관련된 사항은 담당자의 지시에 따라야 한다.<br>
				2. 체육관 이용 시 지정된 실내화(실내용 실내화)를 착용해야 한다.<br>
				3. 계약된 대관 시간은 엄수해야 한다.<br>
				4. 계약된 대관 시간 중 안전사고에 대한 책임은 계약 당사자간의 귀책사유에 따라 책임을 부담한다.〈개정 2016.08.30.〉<br>
				5. 지정된 장소이외의 운동시설에서는 음식물 반입을 금지한다.<br>
				6. 대관은 허가된 시설에만 적용되므로 일체의 집기나 운동용품은 사전 허락 후 사용한다.<br>
				7. 지정된 대관 시설 외에는 사전허가 없이 출입 또는 사용을 금지한다.<br>
				8. 사용자의 과실로 시설 등을 파손하였을 때에는 배상하여야 한다.<br>
				9. 기타 사항은 체육시설의 이용내규에 따른다.<br>
			</div>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 7 장 임대락카 사용</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제25조 (이용규정)</strong><br>
			<br>

			① 임대락카 임대기간은 3개월로 하며, 보증금 및 임대료는 체육시설 이용내규에 따른다.〈개정 2013.03.21〉<br>
			② 임대락카는 1인 1개 락카 사용으로 하여 키를 지급하고 임대기간 종료 후 반납한다.<br>
			③ 임대락카 이용을 희망하는 회원은 체육시설이 정한 바에 따라 서식6호에 의하여 시용신청서를 제출하고 약정금액(보증금, 임대료)을 납부하여야 한다.<br>
			④ 회원은 체육시설의 이용 종료와 동시에 사물함 열쇠를 반납하여야 한다.<br>
			⑤ 회원이 이용을 종료한 날로부터 3개월이 경과하여도 사물함 열쇠를 반환하지 않을 경우 락카 내부의 회원 보관 물품은 종료일로부터 3개월간 별도 보관 후 폐기한다.〈개정 2015.09.21〉<br>
			⑥ 열쇠의 미반납 및 분실로 인한 사고의 책임은 회원에게 있다.<br>
			⑦ 열쇠를 분실 및 훼손한 회원은 손해배상금(10,000원)을 변상하여야 한다.<br>

			<br>
			<br>


			<strong>제26조 (사용료 반환)</strong><br>
			<br>

			① 락카임대료 및 보증금 환불은 서식7호에 의하여 임대기간 만료일전에 임대락카 키를 반납 후, 환불신청서 제출 시 락카키 반납이 동일하게 이루어져야 하며 이용일수 공제, 락카보증금과 함께 3영업일 이내에 개인계좌로 입금한다.〈개정 2018.01.31〉<br>
			② 임대기간이 경과된 락카는 보증금에서 이용일수 공제하여, 차액금을 환불한다. 기타 정하지 않은 사항은 체육시설 이용내규에 따른다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제8장 사고처리〈개정 2013.03.21〉</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제27조 (시설물 관련 사고처리)</strong><br>
			<br>

			① 체육시설의 시설물에 의해 회원이 신체상의 피해를 입은 경우, 공단이 가입한 배상책임보험, 신체손해보험으로 처리한다.〈개정 2013.03.21〉<br>
			② 회원의 고의 또는 과실로 인한 시설물의 파손 등 체육시설에 손해가 발생한 경우, 회원은 이를 배상하여야 한다.〈개정 2013.03.21〉<br>

			<br>
			<br>


			<strong>제28조 (의무 소홀로 인한 안전사고)</strong><br>
			<br>

			① 강사의 안전의식 소홀로 인한 사고책임은 체육시설에 있다.<br>
			② 병력을 의도적 또는 무의도적으로 누락시킨 회원에게 차후 병력과 관련 있는 사고가 발생 했을 경우, 그에 대한 사고책임은 회원에게 있다.<br>
			③ 회원의 부주의 및 이용회원 간 발생하는 사고책임은 회원에게 있다.<br>
			④ 체육시설의 준수사항 위반 시 발생하는 사고책임은 회원에게 있다.<br>

			<br>
			<br>


			<strong>제29조 (면책조항)</strong><br>
			<br>

			① 체육시설은 천재지변 등 불가항력의 사유로 인한 사고의 경우에는 그 책임이 면제될 수 있으며, 회원의 정상적인 이용이 곤란한 때에는 그 사유가 종료될 때까지 이용을 중단시킬 수 있다.<br>
			② 제1항의 경우, 회원은 계약을 해지할 수 있으나 체육시설에 대해 손해 배상을 청구할 수 없다.<br>

			<br>
			<br>


			<strong>제30조 (책임사항)</strong><br>
			<br>

			① 화폐, 유가증권 기타의 고가물에 대하여는 회원이 그 종류와 가액을 명시하여 보관하지 아니하면 체육시설은 그 물건의 멸실 또는 훼손에 대하여 배상할 책임이 없다.<br>
			② 회원 또는 회원 동반자의 귀책사유로 인하여 상해 등의 사고와 시설물의 훼손 또는 파손에 대하여는 회원 또는 회원동반자에게 그 책임이 있다.<br>

			<br>
			<br>


			<strong>제31조 (이용내규 외 준칙)</strong><br>
			<br>

			① 이용내규에 명시되지 아니한 사항은 관계법령 및 거래 관례를 고려하여 신의와 성실의 원칙에 따라 체육시설과 회원이 협의하여 해결한다.<br>
			② 이용내규과 관련된 소송의 관할법원은 민사소송법상의 관련법규에 의한다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>제 9 장 주차장 운영 관련</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<strong>제32조 (주차장 이용)</strong><br>
			<br>

			① 체육시설별로 설치된 주차장은 주차질서 및 주차순환을 위해 유료화 운영한다.<br>
			② 승용차이용 억제 차원에서 요일제 부착을 권장하고, 미부착 차량에 대하여 출입을 통제 할 수 있다.<br>
			③ 주차요금은 10분당 200원 부과한다.〈개정 2018.01.31〉<br>
			④ 체육시설의 주차장이용은 회원차량을 우선하며 공단 이사장의 방침에 따라 주차요금을 전부 또는 일부 면제할 수 있다.<br>
			⑤ 차량파손 및 물품도난의 책임은 이용자의 책임으로 한다.<br>
			⑥ 주차권 분실 시 차량소유자임을 증명하여야 하며, 주차요금은 09:00부터 주차한 것으로 계산한다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>부칙 〈2012.09.25〉</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			① (시행일) 이 체육시설이용내규가 제정되는 시점부터 적용된다.<br>
			② (경과조치) 이 내규 시행전에 시행된 사항에 대하여는 이 내규에 의하여 시행된 것으로 본다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>부칙 〈2015.09.21〉</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			① (시행일) 이 내규는 2015.09.21부터 시행한다.<br>
			② (경과조치) 이 내규 시행일 이전에 시행된 사항은 이 내규에 따라 시행된 것으로 본다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>부 칙〈2016.08.30〉</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			①(시행일) 이 내규는 2016.08.30부터 시행한다.<br>
			②제18조(프로그램 및 회원종류의 변경) 2항은 조례개정 후 시행한다.<br>
			③(경과조치) 이 내규 시행일 이전에 시행된 사항은 이 내규에 따라 시행된 것으로 본다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>부칙 〈2017.01.26〉</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			①(시행일) 이 내규는 2017.01.11부터 적용하여 시행한다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>부칙 〈2019.01.31〉</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			①(시행일) 이 내규는 2019.02.01부터 적용하여 시행한다.<br>
		</div>




		<br>
		<br>
		<br>

		<div class='bg_icon_round_green1a05 fontsize_1dot30 padding_left_1dot0'>서 식</div>
		<br>

		<div class='padding_left_1dot0 lineheight_1dot8'>
			<!-- 파일다운로드처리 -->
			제1호서식 : <a href='/data/file/제1호서식_신규회원가입수강신청서.hwp' class='fc_blue1 line_under'>신규회원 가입/수강 신청서</a> 〈개정. 2019.01.31.〉<br>
			제2호서식 : <a href='/data/file/제2호서식_프로그램 환불신청서.hwp' class='fc_blue1 line_under'>프로그램 환불신청서</a> 〈개정. 2015.09.21.〉<br>
			제3호서식 : <a href='/data/file/제3호서식_대관 사용 허가 신청서.hwp' class='fc_blue1 line_under'>대관 사용 허가 신청서</a> 〈개정. 2015.09.21.〉<br>
			제4호서식 : <a href='/data/file/제4호서식_대관 계약서.hwp' class='fc_blue1 line_under'>대관 계약서</a> 〈개정. 2016.08.30.〉<br>
			제5호서식 : <a href='/data/file/제5호서식_대관 환불신청서.hwp' class='fc_blue1 line_under'>대관 환불신청서</a> 〈개정. 2015.09.21.〉<br>
			제6호서식 : <a href='/data/file/제6호서식_임대락카 신청서.hwp' class='fc_blue1 line_under'>임대락카 신청서</a> 〈개정. 2013.03.21.〉<br>
			제7호서식 : <a href='/data/file/제7호서식_임대락카 환불 신청서.hwp' class='fc_blue1 line_under'>임대락카 환불신청서</a> 〈개정. 2013.03.21.〉<br>
		</div>


	</div>

</div>
