<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.dwict.jfmc.client.mem.model.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/mypageCommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/mypage/classStatus.js"></script>

<%
Date from = new Date();
SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
String today = transFormat.format(from);

Member member 	= (Member) session.getAttribute("member");
String MEM_ID	= member.getId();
String MEM_NM 	= member.getMemNm();
String MEM_MAIL = member.getEmail();
//out.println("MEM_ID:"+ MEM_ID +"<BR>");
//out.println("MEM_NM:"+ MEM_NM +"<BR>");
//out.println("MEM_MAIL:"+ MEM_MAIL +"<BR>");
%>

<div id="sub_my_status" class=''>
	<div id="notes" class="border_box_a_gray_1a margin_b50">
		<h4 class="margin_b20">유의사항</h4>
		<ul class="ul_hyphen1a13" >
			<li>수강취소는 당일에만 가능하며, 이후에는 환불규정에 따라 처리됩니다.</li>
			<li>반 변경은 방문접수만 가능합니다.</li>
			<li>반 변경은 개강 후 7일 이전까지만 가능합니다.<span class="cap_green" style=""> (7일 이전 1회만 가능)</span></li>
			<li>카드결제 취소는 당일만 가능합니다.</li>
			<!-- <li>환불은 자세한 환불규정을 확인하시기 바랍니다. <a href="#" class="size_s2 btn_line_green margin_l5">환불규정</a></li> -->
		</ul>
	</div>
	<!-- 
	<div class='sch_box_01 noslt'>
		<form name='frm_sch01' method='get' action=''>
			<ul>
				<li><input type='text' name='' maxlength='10'></li>
				<li><input type='submit' value='검색하기'></li>
			</ul>
		</form>
	</div>
	 -->	
	<table class="stbl_l1a">
		<colgroup>
			<col width="5%"><col width="*"><col width="25%"><col width="10%"><col width="10%"><col width="20%">
		</colgroup>
		<thead>
			<tr>
				<th>번호</th>
				<th>[시설명] 강좌명</th>
				<th>대상</th>
				<th>수강료</th>
				<th>결제상태</th>
				<th>수강기간</th>
			</tr>
		</thead>
		<tbody>
			<c:set var = "TODAY" value="<%=today%>"/>
			<c:forEach items="${rentList}" var="result" varStatus="status">
			<tr>
				<c:set var="COMCD" value="${result.COMCD}"/>
				<c:set var="WRITE_YMD" value="${result.WRITE_YMD}"/>
				<c:set var="ITEM_SDATE" value="${result.ITEM_SDATE}"/>
				<c:set var="ITEM_EDATE" value="${result.ITEM_EDATE}"/>
				<c:set var="COST_AMT" value="${result.COST_AMT}"/>
				
				<%
					String COMCD = (String)pageContext.getAttribute("COMCD") ;
					String WRITE_YMD = (String)pageContext.getAttribute("WRITE_YMD") ;
					String ITEM_SDATE = (String)pageContext.getAttribute("ITEM_SDATE") ;
					String ITEM_EDATE = (String)pageContext.getAttribute("ITEM_EDATE") ;
					String sCOST_AMT = (String)pageContext.getAttribute("COST_AMT") ;
					sCOST_AMT = sCOST_AMT.replace(",","");
					int COST_AMT = Integer.parseInt(sCOST_AMT);
				%>
				
				<td>${status.count}</td>
				<td>[${result.COMNM}] ${result.CLASS_NM}</td>
				<td>${result.ITEM_NM}</td>
				<td>${result.COST_AMT}원</td>
				<td>
					<span id="" class="cap_blue">${result.MIDCANCEL_YN_NM}</span>
					<%
					//<!-- 당일취소 -->
					if (today.equals(WRITE_YMD)){
						if (COST_AMT > 0) { %>
						<br><a href="#none" onclick="cancelPay('${result.TID}', '${result.SLIP_NO}', '${result.COST_AMT}','${result.COMCD}','${result.SALE_SEQ}');" class="btn_green1">당일취소</a>
						<%
						}
					}
					%>
					
					<%
					//<!-- 재등록 버튼 -->
					String a_dateYM = ITEM_EDATE.substring(0,6); //구매 종료 월
					String t_dateYM = today.substring(0,6); //date 댱월
					
					String a_dateD = ITEM_EDATE.substring(6); //구매 종료일
					String t_dateD = today.substring(6); //date 당일
					int toDay = Integer.parseInt(t_dateD);
					//out.println("COMCD:"+ COMCD);
					//out.println("toDay:"+ toDay);
					
					
					//재구매 여부
					boolean rePay = false;
					
					//메인 스포츠센터 재등록 11~23일
					if (a_dateYM.equals(t_dateYM) && toDay >= 11 && toDay <= 23 ) {
						if (COMCD.equals("JUNGNANG01") || COMCD.equals("JUNGNANG02") || COMCD.equals("JUNGNANG03")) {
							rePay = true;
						}
					//신대 다목적 재등록 18~24일
					} else if (COMCD.equals("JUNGNANG04") && a_dateYM.equals(t_dateYM) && toDay >= 18 && toDay <= 24 ) {
						rePay = true;						
					}
					
					//재 수강신청 버튼					
					if (rePay == true){
					%>
						<a class="size_s2 btn_pink_blueWrite" onclick="addBasket1('${result.COMCD}', '${result.CLASS_CD}', '${result.ITEM_CD}');">재 수강신청</a>
					<%
					}
					%>

				</td>
				<td>
					<div id="" class="">${result.MONTH_CNT}개월</div>
					<div>${result.ITEM_SDATE_DT} ~ ${result.ITEM_EDATE_DT}</div>
					<!-- <div><a class="size_s2 btn_blue2" onclick="">재등록</a></div>-->
				</td>
			</tr>
			</c:forEach>
			<!-- 					
			<tr>
				<td>1</td>
				<td>즐거운 꼬마요리터A</td>
				<td>90,000원</td>
				<td><span id="" class="cap_red">전액환불</span></td>
				<td>
					<div id="" class="">1개월</div>
					<div>2020.03.01 ~ 2020.04.01</div>
					<div><a class="size_s2 btn_blue2" onclick="">재등록</a></div>
				</td>
			</tr>
			 -->
		</tbody>
	</table>
</div>

<form name='frmCancel' method='post' action=''>
	<input type='hidden' name='p1' maxlength='2'>
	<input type='hidden' name='p2' maxlength='2'>
	<input type='hidden' name='p3' maxlength='2'>
	<input type='hidden' name='p4' maxlength='2'>
	<input type='hidden' name='p5' maxlength='2' value="class">
	<input type='hidden' name='otherParam' maxlength='2'></form>