<%@page import="java.util.Date"%>
<%@page import="java.util.*"%>
<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.lang.reflect.Array"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/rent/rent.date.js"></script>
<%

	Date from = new Date();
	SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
	String today = transFormat.format(from);

  // 오늘 날짜로 달력 취득
  Calendar currentCal = Calendar.getInstance();
  Calendar todayCheck_currentCal = Calendar.getInstance();
  String sYY = (String)request.getParameter("yy");
  String sMM = (String)request.getParameter("mm");
  
  try {
	  if(sYY != null && sMM != null){
 		if (sMM.equals("12")) {
			int intYY = Integer.parseInt(sYY);
			int intMM = Integer.parseInt(sMM);
			currentCal.set(Calendar.YEAR, intYY);
			currentCal.set(Calendar.MONTH, 11);
		} else {
			int intYY = Integer.parseInt(sYY);
			int intMM = Integer.parseInt(sMM) -1;
			currentCal.set(Calendar.YEAR, intYY);
			currentCal.set(Calendar.MONTH, intMM);
		}
	  }
  } catch (Exception ex) {
  }
  
  //out.println("today:"+ today +"<BR>");
  
  // 날을 1일로 셋팅
  currentCal.set(Calendar.DATE, 1);
  
  // 1일의 '요일'을 취득
  int oneDayNum = currentCal.get(Calendar.DAY_OF_WEEK);
  // 현재달의 최대 일 수
  int monthMaxNum = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);
  // 현재달의 주 수
  int weekSize = currentCal.getActualMaximum(Calendar.WEEK_OF_MONTH);
  // 현재 '년'을 취득
  int year = currentCal.get(Calendar.YEAR);
  // 현재 '월'을 취득(0월 부터 11월 까지)
  int month = currentCal.get(Calendar.MONTH);
  month = month +1;
  

  int preYear = year;
  int preMonth = month - 1;  
  if (month == 1) {
	  preYear = year - 1;
	  preMonth = 12;
  } 
  
  int nextYear = year;
  int nextMonth = month + 1;
  if (month >= 12) {
	  nextYear = year + 1;
	  nextMonth = 1;
  }
  //out.print("year:"+ year +"<BR>");
  //out.print("MONTH:"+ month +"<BR>");
  
  
  int day = currentCal.get(Calendar.DATE);
  //int day = currentCal.get(Calendar.DAY_OF_MONTH);

  sMM = (month > 9) ? Integer.toString(month) : "0"+month;
%>

<c:set var="teamMemCnt" value="${rentCfg.teamMemCnt}" />
<c:set var="PLACE_GROUP" value="${rentCfg.PLACE_GROUP}" />
<c:set var="PLACE_CD" value="${rentCfg.PLACE_CD}" />
<c:set var="otherCfg" value="${rentCfg.other_cfg}" />
<%
int teamMemCnt = (int) pageContext.getAttribute("teamMemCnt");
int PLACE_GROUP = (int) pageContext.getAttribute("PLACE_GROUP");
//out.println("PLACE_GROUP:"+ PLACE_GROUP +"<BR>");
int PLACE_CD = (int) pageContext.getAttribute("PLACE_CD");
//out.println("PLACE_CD:"+ PLACE_CD +"<BR>");
String otherCfg = (String)pageContext.getAttribute("otherCfg") ;
//out.println("otherCfg:"+ otherCfg +"<BR>");
String [] arryCfg = otherCfg.split("\\/");

String str_tabCnt = arryCfg[2];
int int_tabCnt = Integer.parseInt(str_tabCnt);
//out.println("tabCnt:"+ int_tabCnt +"<BR>");
String tabName = arryCfg[1];
//out.println("tabName:"+ tabName +"<BR>");
//String tabCnt = arryCfg[1];


%>
<c:set var="param" value="${rentCfg.param}" />
<%
String param = (String)pageContext.getAttribute("param") ;
param = (param == null) ? "": param;

//out.println("param:"+ param +"<BR>");
%>
	<script type="text/javaScript" language="javascript">
	$(function(){
		setMenuTitle('bg_99', '체육시설', '${rentCfg.COMNM} >  ${rentCfg.PLACE_NM}', true);
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
		
		
		let cont = '';
		<c:forEach items="${centerGroup}" var="result" varStatus="status">
			cont += '<a href="/rent/date/?q=${result.PLACE_CD}"><li>${result.PLACE_NM}</li></a>';
		</c:forEach>
		$(".mbx1:eq(1)>ul").append(cont);	
		
		
	});
	
var teamMemCnt = "${rentCfg.teamMemCnt}";
if (teamMemCnt == "" || Number(teamMemCnt) == 0) {
	//alert("팀이 없는 개인으로 신청됩니다.");
} else if (Number(teamMemCnt) < 10) {
	//alert("팀이 있으나 10명 이내 입니다.");
}
</script>
<%
if (teamMemCnt < 10) {
	//return;
}
%>
<style TYPE="text/css">
		.chkbxSize { width:25px; height:25px; }
		.rented { text-decoration:line-through ; color:red;}
		.fntSize10 { font-size:1em; }
		.day{
			width:100px; 
			/* height:30px; */
			font-weight: bold;
			font-size:15px;
			font-weight:bold;
			text-align: left;
		}
		.sat{
			color:#529dbc;
		}
		.sun{
			color:red;
		}
		.today_button_div{
			float: right;
		}
		.today_button{
			width: 100px; 
			height:30px;
		}

		.navigation{
			margin-top:0px;
			margin-bottom:30px;
			text-align: center;
			font-size: 25px;
			vertical-align: middle;
		}
		.calendar_body{
			width:100%;
			background-color: #FFFFFF;
			border:1px solid white;
			margin-bottom: 50px;
			border-collapse: collapse;
			text-align: left;
		}
		.calendar_body .today{
			/* border:1px solid white; */
			/* height:120px; */
			/* background-color:#c9c9c9; */
			text-align: left;
			vertical-align: top;
		}
		.calendar_body .date{
			font-weight: bold;
			font-size: 15px;
			text-align: left;
			
		}
		.calendar_body .sat_day{
			/* border:0.5px solid white; */
			/* height:120px; */
			background-color:#EFEFEF;
			text-align:left;
			vertical-align: top;
		}
		.calendar_body .sat_day .sat{
			color: #529dbc; 
			font-weight: bold;
			font-size: 15px;
			padding-left: 3px;
		}
		.calendar_body .sun_day{
			/* border:0.5px solid white; */
			/* height:120px; */
			background-color:#EFEFEF;
			text-align: left;
			vertical-align: top;
		}
		.calendar_body .sun_day .sun{
			color: red; 
			font-weight: bold;
			font-size: 15px;
			padding-left: 3px;
		}
		.calendar_body .normal_day{
			/* border:0.1px solid white; */
			/* height:120px; */
			background-color:#EFEFEF;
			vertical-align: top;
			text-align: left;
		}
		.before_after_month{
			margin: 10px;
			font-weight: bold;
		}
		.before_after_year{
			font-weight: bold;
		}
		.this_month{
			margin: 10px;
		}
	</style>
	

<%
String [] rentDays = new String [32];
for (int ii = 0 ; ii < rentDays.length; ii++) {
	rentDays[ii] = "<a class='size_m2 btn_gray1'>예약 종료</a>";
}

int tmpDay =0;
%>
<c:set var="itemTot" value="${rentList[0].tot}" />
<%
int itemTot = (int)pageContext.getAttribute("itemTot") ;
//out.print("itemTot:"+ itemTot +"<BR>");
%>
<c:set var="days" value="${rentList[0].days}" />
<%
String days = (String)pageContext.getAttribute("days") ;
//out.print("days:"+ days +"<BR>");

String [] arryDays = days.split("\\/");
//현재 예약 갯수
for (int ii = 0 ; ii < arryDays.length; ii++) {
	//out.print("days["+(ii+1)+"]:"+ arryDays[ii] +"<BR>");
	int rentCnt = Integer.parseInt(arryDays[(ii)]);
	//예약갯수 비교해서 예약 가능/종료 확인
	String strii = ((ii+1) > 9) ? Integer.toString(ii+1) : "0"+(ii+1);
	//다음달
	String strNextMonth = (nextMonth > 9) ? Integer.toString(nextMonth) : "0"+ nextMonth;
	
	int int_today	= Integer.parseInt(today);
	int todayYYMM = Integer.parseInt(today.substring(0,6));
	int cntYYMM = Integer.parseInt(year + sMM);
	int int_toYYMM	= Integer.parseInt(today.substring(0,6) + 23);
	
	int int_rentYmd = Integer.parseInt(year + sMM + strii);
	int int_LimitYmd = Integer.parseInt(year + sMM + 23);
	int nextYmd 	= Integer.parseInt(nextYear + strNextMonth + strii);
	//out.print("today :"+ int_toYYMM +"<BR>");
	//out.print("rentYmd :"+ int_rentYmd +"<BR>");
	//out.print("nextYmd :"+ int_LimitYmd +"<BR><BR>");
	
	int sortEndTime = Integer.parseInt(nextYear + strNextMonth + 31);
	
	Date date1 = transFormat.parse(today);
    Date date2 = transFormat.parse(year + sMM + strii);
    int diffMonth = getMonthsDiff(date1 , date2);
	//접수 가능/종료 버튼 
	if (itemTot > rentCnt && int_today <= int_rentYmd ) {

		//if (1 >= diffMonth && ((int_rentYmd < int_LimitYmd) || ( ii > 23)) ) {
		if ( (todayYYMM == cntYYMM && ii < (23-1)) || (int_today <= sortEndTime && int_today >= int_LimitYmd) 
				|| (diffMonth <= 1 && todayYYMM < cntYYMM && ii < (23-1) && int_toYYMM <= int_today) ) {
			rentDays[ii+1] = " <a class='size_m2 btn_green1' onclick=\"getRent('"+ int_rentYmd +"','"+PLACE_CD+"');\">예약 가능 ("+ (itemTot - rentCnt) +"건) </a>";	
		} else {
			rentDays[ii+1] = " <a class='size_m2 btn_gray1'>준비중</a> ";
		}
	}
	//전체보기용 rentDays[ii+1] = " <a class='size_m2 btn_green1' onclick=\"getRent('"+ int_rentYmd +"','"+PLACE_CD+"');\">예약 가능  ("+ (itemTot - rentCnt) +"건) </a>";
}


%>



<table class="con_wid1" border=0>
<tr>
<%
if (PLACE_GROUP != 4) {
%>
<td style="width:950px;">
<%
} else {
%>
<td style="width:1200px;">
<%
}
%>
<div class="calendar" >


	<!--날짜 네비게이션  -->
	<div class="navigation">
		<a class="before_after_year" href="?yy=<%=preYear%>&mm=<%=preMonth%>&q=<%=param%>">
			&lt;&lt;
		<!-- 이전해 -->
		</a> 
		<span class="this_month">
			&nbsp;<%=year %>. 
			<%=month %>
		</span>
		<a class="before_after_year" href="?yy=<%=nextYear%>&mm=<%=nextMonth%>&q=<%=param%>">
			<!-- 다음해 -->
			&gt;&gt;
		</a>
		
	</div>
	
<div class="autoscroll_x1 margin_top_0dot5">
	<table class="calendar_body" >
	
	<thead>
		<tr bgcolor="#CECECE">
			<td class="day sun" >
				일
			</td>
			<td class="day" >
				월
			</td>
			<td class="day" >
				화
			</td>
			<td class="day" >
				수
			</td>
			<td class="day" >
				목
			</td>
			<td class="day" >
				금
			</td>
			<td class="day sat" >
				토
			</td>
		</tr>
	</thead>
 	<tbody>
		<tr>
	 <%
	  //int dayNum = 1;
	  boolean dayCheck = false;
	
	  // 현재 달의 주 수만큰 반복
	  for(int i=0; weekSize > i;i++){
	   out.println("<tr height='100' >");
	   
	   // 일주일이 7일 이니 7번 반복
	   for(int j=0; (7 > j); j++){
		   
		if(todayCheck_currentCal.equals(currentCal)){
	    	out.println("<td style='background-color:#c9c9c9;text-align: left;' valign=\"top\" >");
		}
		else
		{
			out.println("<td class=\"normal_day\" style='border:0.1px solid white;' valign=\"top\">");
		}
	    
	    // 현재 달의 1을의 요일에 해당하는 곳부터 출력하기위함
	    if(oneDayNum == j+1 || dayCheck){
	     // 현재 달의 최대 일수일경우
	     if(monthMaxNum >= day ){
	      /// 일요일의 경우 글자색 '빨강'
	      if(currentCal.get(Calendar.DAY_OF_WEEK) == 1){
	       // 일요일이면서 오늘일 경우 글자색은 '빨강', 글자 진하게 아니면 글자색만 '빨강'
	       if(todayCheck_currentCal.equals(currentCal)){
	    	   out.println("<span class=\"today\"><span class=\"date\">" + day + "</span><br>");
	       }else{
	    	   out.println("<span class=\"sun_day\"><span class=\"sun\">" + day + "</span><br>");
	       }
	       out.println(rentDays[day] +"</span>");
	       currentCal.set(Calendar.DATE, ++day);
	       dayCheck = true;
	      // 토요일의 경우 글자색 '파랑'
	      }else if(currentCal.get(Calendar.DAY_OF_WEEK) == 7){
	       // 토요일이면서 오늘일 경우 글자색은 '파랑', 글자 진하게 아니면 글자색만 '파랑'
	       if(todayCheck_currentCal.equals(currentCal)){
	    	out.println("<div class=\"today\"><div class=\"date\"><b>" + day + "</b></div>");
	       }else{
	        //out.println("<font color='blue'>" + day + " 토</font>");
	    	out.println("<div class=\"sat_day\"><div class=\"sat\">" + day + "</div>");
	       }
	       out.println("<div>"+ rentDays[day] +"</div></div>");
	       currentCal.set(Calendar.DATE, ++day);
	       dayCheck = true;
	      // 일요일도 아니고 토요일도 아니면 기본속성 글자출력 
	      }else {
	       //  일요일도 아니고 토요일도 아닌데 오늘이면 글자진하게
	       if(todayCheck_currentCal.equals(currentCal)){
	    	   //out.println("<b>" + day + "오늘</b>");
	    	   out.println("<span class=\"today\"><span class=\"date\"><b>" + day + "</b></span><Br>");
	    	   
	       }else{
	    	   out.println("<span class=\"normal_day\"><span class=\"date\">" + day + "</span><Br>");
	       }
	       out.println(rentDays[day] +"</span>");
	       currentCal.set(Calendar.DATE, ++day);
	       dayCheck = true;
	      }
	       
	     }else{
	      out.println("&nbsp;");
	     }
	    }else{
	     out.println("&nbsp;");
	    }
	    
	    out.println("</td>");
	    
	   }
	   out.println("</tr>");
	  }
	
	 %>
 
</table>
</div>
</div>
</td>
<%
//테니스장 제외한 신청 폼
if (PLACE_GROUP != 4) {
%>
<td valign="top">
		<form name="frm2" id="frm2" action="" method="GET">
		<table style="margin:70px 0 0 50px;" class="stbl_l1a">
		<caption>시간 선택</caption>
		<colgroup>
			<col class="col04">
			<col>
		</colgroup>
		<thead>
			<tr>
				<th scope="col"><span id="selectDate" style="font-weigh:500;color:#4467ff;font-size:15px; "> &bull; 예약가능한 시간을 선택하세요</span></th>
			</tr>
		</thead>
		<tbody id="dataList">
		<tr>
			<td><div id="data_tab1"></td>
		</tr>

		</tbody>
		</table>
		<input type="hidden" name="q" value="<%=param %>">
		<input type="hidden" id="ymd" name="ymd" value="">
	</form>
</td>
<%
}
%>
</tr>
</table>


<%
//테니스장 예약 신청폼
if (PLACE_GROUP == 4) {
%>
<br>
<div id="selectDate" style="font-weigh:500;color:#4467ff;font-size:22px; "> &bull; 예약가능한 시간을 선택하세요</div>

<form name="frm2" id="frm2" action="" method="GET">
	<table class="stbl_l1a con_wid">
	 <thead>
	  <tr>
	  <%
	  for (int ii = 0 ; ii < int_tabCnt; ii++) {
	  %>
	    <td><%=(ii+1) +" "+ tabName %></td>
	  <%
	  }
	  %>
	  </tr>
	 </thead>
	 <tbody id="dataList">
	  <tr>
	  <%
	  for (int ii = 0 ; ii < int_tabCnt; ii++) {
	  %>
	    <td><div id="data_tab<%=(ii+1)%>"></td>
	  <%
	  }
	  %>
	  </tr>
	  <tr id="info1"><td colspan="20"> 예약가능한 날짜를 선택해주세요. </td></tr>
	</tbody>
	</table>
	<input type="hidden" name="q" value="<%=param %>">
	<input type="hidden" id="ymd" name="ymd" value="">
</form>
<%
}
%>


    <div class="btnarea margin_t80">
        <a href="#none" onClick="send('<%=int_tabCnt %>');" id=" " class="green">예 약</a>
        <a href="/rent/team" id=" " class="gray2">팀 수정</a>
        <a href="/rentlist" id=" " class="gray2">취 소</a>
    </div>
    <form name="frm3" id="frm3" action="/rent/rentOrder" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	<input type="hidden" id="val1" name="val1" value="0">
    	<input type="hidden" id="val2" name="val2" value="0">
    	<input type="hidden" id="val3" name="val3" value="0">
    	<input type="hidden" id="val4" name="val4" value="0">
    	<input type="hidden" name="q" value="<%=param %>">
    </form>
    
<%!
/**
 * 두날자 차이 day
 */
 public int getDayDiff (int today , int nextYMD) {
	/** yyyyMMddHHmmss Date Format */
	int intDiff = 0;
	try { 
		String a = Integer.toString(today);
		String b = Integer.toString(nextYMD);
		String format = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
		Date firstDate = sdf.parse(a); Date secondDate = sdf.parse(b);
		long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		System.out.println(String.format("sssssssssssssA %s , B %s Diff %s Days", a, b, diff));
		intDiff = Long.valueOf(diff).intValue();
		
	} catch (Exception e) { 
		e.printStackTrace(); 
	}
	
	return intDiff;

}

/**
 * 두날자 차이 month
 */

private int getMonthsDiff(Date date1, Date date2){

	/* 해당년도에 12를 곱해서 총 개월수를 구하고 해당 월을 더 한다. */
	int month1 = date1.getYear() * 12 + date1.getMonth();
	int month2 = date2.getYear() * 12 + date2.getMonth();

return month2 - month1;
}

%>    