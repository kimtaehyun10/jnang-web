<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
 <head>
  <title>중랑구 시설관리공단</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
  <script type="text/javascript" charset="utf-8" src="/html/js/common.js"></script>
  <script type="text/javascript" charset="utf-8" src="/_js/common.js"></script>

<style type='text/css'>
/* 팝업레이어 */
#popdiv {z-index:1000;position:relative;}
.popups {position:absolute;border:1px solid #e9e9e9;background:#fff}
.popups_con {}
.popups_footer {padding:10px 0;background:#000;color:#fff;text-align:right}
.popups_footer button {margin-right:5px;padding:5px 10px;border:0;background:#393939;color:#fff}
</style>
 </head>

 <body>
  <div id="popdiv">

	<div id="popups_15" class="popups">
		<div class="popups_con" style="cursor:pointer">
			<img src="/resource/images/popup/popup20.png" title="코로나바이러스 휴관안내" alt="코로나바이러스 휴관안내" />
			<map name="Map">
			
				<area shape="rect" coords="30,665,250,700" onclick="javascript:window.close()" href="http://www.jungnangimc.or.kr/noticeView.do?sgrp=S01&siteCmsCd=CM0001&topCmsCd=CM0003&cmsCd=CM0035&pnum=10&cnum=17&ntNo=1207" target="_blank"/> 
				<area shape="rect" coords="295,665,595,700" onclick="javascript:window.close()" href="http://www.jungnangimc.or.kr/noticeView.do?sgrp=S01&siteCmsCd=CM0001&topCmsCd=CM0003&cmsCd=CM0035&pnum=10&cnum=17&ntNo=1208" target="_blank"/>
			</map>
				<br style="clear:both;" />
			
		</div>
		<div class="popups_footer">
			<button class="popups_reject popups_15">오늘은 다시 열람하지 않습니다.</button>
			<button class="popups_close popups_15">닫기</button>
		</div>
	</div>

</div>
 </body>

 <script>

 $(function() {
	    $(".popups_reject").click(function() {
	        var id = $(this).attr('class').split(' ');
	        var cname = id[1];
			set_Cookie(cname, "no", 1);
			window.close()
	    });
	    $('.popups_close').click(function() {
	        window.close()
	    });
		$('.popups_con').click(function() {
			window.opener.top.location.href="/noticeView.do?sgrp=S01&siteCmsCd=CM0001&topCmsCd=CM0003&cmsCd=CM0035&pnum=10&cnum=17&ntNo=1207"
	        window.close()
	    });
	});

function set_Cookie(name, value, expiredays) {
	var todayDate = new Date(); 
	todayDate.setDate( todayDate.getDate() + expiredays ); 
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toUTCString() + ";" 
}
</script>
</html>
