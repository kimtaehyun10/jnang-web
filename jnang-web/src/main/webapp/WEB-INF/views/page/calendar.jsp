<%@page import="java.lang.reflect.Array"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/page/calendar.js"></script>
<%
  // 오늘 날짜로 달력 취득
  Calendar currentCal = Calendar.getInstance();
  Calendar todayCheck_currentCal = Calendar.getInstance();
  String sYY = (String)request.getParameter("yy");
  String sMM = (String)request.getParameter("mm");
 
  try {
	  if(sYY != null && sMM != null){
		
		/*
		if (sMM.equals("-1")) {
			currentCal.set(Calendar.YEAR, Integer.parseInt(sYY)-1);
			currentCal.set(Calendar.MONTH, 11);
		}
		else
		{
			currentCal.set(Calendar.YEAR, Integer.parseInt(sYY));
			currentCal.set(Calendar.MONTH, Integer.parseInt(sMM));
		} 
		*/
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
  
  
  // 현재 '일'을 취득
  int day = currentCal.get(Calendar.DATE);
  //int day = currentCal.get(Calendar.DAY_OF_MONTH);

%>

<c:set var="PLACE_GROUP" value="${rentCfg.PLACE_GROUP}" />
<%
int PLACE_GROUP = (int) pageContext.getAttribute("PLACE_GROUP");
//out.println("PLACE_GROUP:"+ PLACE_GROUP +"<BR>");
%>
<c:set var="PLACE_CD" value="${rentCfg.PLACE_CD}" />
<%
int PLACE_CD = (int) pageContext.getAttribute("PLACE_CD");
//out.println("PLACE_CD:"+ PLACE_CD +"<BR>");
%>
<c:set var="otherCfg" value="${rentCfg.other_cfg}" />
<%
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
		
	});
	
	setTimeout("getDataList('<%=PLACE_CD%>',1);",500);
	
	 
</script>
<style TYPE="text/css">
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
if (int_tabCnt > 0 ) {
	//int result = 10 % 5
	out.println("<table border=1 width='100%' style='margin:0 0 20px 0;' align='center'><tr>");
	for (int ii = 1 ; ii <= int_tabCnt; ii++) {
	
		%>
		<td align="center"><a onclick="getDataList('<%=PLACE_CD%>','<%=ii%>');"><%=ii %> <%=tabName%></td>
		<%
		if ((ii % 4) == 0) {
			out.println("</tr><tr>");
		}
		
	}
	out.println("</tr></table>");

}
if (PLACE_CD == 6 || PLACE_CD == 10) {
	out.println("<table border=1 width='100%' style='margin:0 0 20px 0;' align='center'><tr>");
	%>
	<td align="center"><a href="/calendar/JUNGNANG09?q=6"> 축구장 </a></td>
	<td align="center"><a href="/calendar/JUNGNANG09?q=10"> 야구장 </a></td>
	<%
	out.println("</tr></table>");
}
%>



<%
/* String [] rentDays = new String [32];
for (int ii = 0 ; ii < rentDays.length; ii++) {
	rentDays[ii] = "";
}
int tmpDay =0; */
%>
<c:forEach items="${rentList}" var="result" varStatus="status">
	<c:set var="RESERVE_DATE" value="${result.RESERVE_DATE}" />
	<%
/* 		int RESERVE_DATE = (int)pageContext.getAttribute("RESERVE_DATE") ;
		//out.print("RESERVE_DATE==>:"+ RESERVE_DATE +"<BR>");
		if (RESERVE_DATE != 0) {
			String strRESERVE_DATE = Integer.toString(RESERVE_DATE);
			tmpDay = Integer.parseInt(strRESERVE_DATE.substring(6));
		}
		else
		{
			tmpDay = 0;
		} */
		//out.print("tmpDay:"+ tmpDay);
 	%>
	<c:set var="APP_TYPE_NM" value="${result.APP_TYPE_NM}" /> 
	<%
		//String APP_TYPE_NM = (String)pageContext.getAttribute("APP_TYPE_NM") ;
		//out.print(APP_TYPE_NM);
 	%>
	<c:set var="PLACE_NM" value="${result.PLACE_NM}" /> 
	<%
		//String PLACE_NM = (String)pageContext.getAttribute("PLACE_NM") ;
		//out.print(PLACE_NM);
 	%> 
	<c:set var="BOSSNM" value="${result.BOSSNM}" /> 
	<%
		/*	
		String BOSSNM = (String)pageContext.getAttribute("BOSSNM") ;
		BOSSNM = (BOSSNM.length() > 1) ? BOSSNM.substring(0,1)+"**" : BOSSNM; 
		//out.print(BOSSNM);
		String strData = "["+ APP_TYPE_NM +"] "+ PLACE_NM +"("+ BOSSNM +")";
		rentDays[tmpDay] = ((rentDays[tmpDay]).equals("")) ? strData : rentDays[tmpDay] +"<BR>"+ strData;
		*/
	%>
</c:forEach>




<form name="calendarFrm" id="calendarFrm" action="" method="GET">

<div class="calendar" >


	<!--날짜 네비게이션  -->
	<div class="navigation">
		<a class="before_after_year" href="?yy=<%=preYear%>&mm=<%=preMonth%>&q=<%=param%>">
			&lt;&lt;
		<!-- 이전해 -->
		</a> 
		<span class="this_month" value="<%=year +""+ month%>">
			&nbsp;<%=year %>. 
			<%=month %>
		</span>
		<a class="before_after_year" href="?yy=<%=nextYear%>&mm=<%=nextMonth%>&q=<%=param%>">
			<!-- 다음해 -->
			&gt;&gt;
		</a>
		
	</div>
	
	<div class="ali_r margin_b10">
	<a href="/rentlist?cm=${rentCfg.cmsCd}" class="size_m2 btn_green1 fontsize_1dot50" >대관신청 바로가기</a>
	</div>

<div class="autoscroll_x1 margin_top_0dot5">
	<table class="calendar_body stbl_l1a con_wid" align="center" >
	
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
	       //out.println(rentDays[day] +"</span>");
	       out.println("<div id='d"+day+"'></div></span>");
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
	       //out.println("<div>"+ rentDays[day] +"</div></div>");
	       out.println("<div id='d"+day+"'></div></span>");
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
	       //out.println(rentDays[day] +"</span>");
	       out.println("<div id='d"+day+"'></div></span>");
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

  

