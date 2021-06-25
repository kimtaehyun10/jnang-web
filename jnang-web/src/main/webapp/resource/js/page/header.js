/**
* @projectDescription header.js
*
* @author RGJ
* @version 1.0
*/
'use strict'
$(function(){
	//날씨 오류 주석처리
	//var weatherInfo = getWeatherInfo();
});

function getWeatherInfo(){
	var dNow = new Date();
	var sTm = localStorage.getItem('tm');
	if(sTm !== null && sTm !== undefined){
		var mm = dNow.getMonth()+1;
		var dd = dNow.getDate();
		var nForecastYMD = Number(sTm.substring(0,8));
		var nObserveYMD = Number(dNow.getFullYear().toString()+(mm>9?'':'0')+mm+(dd>9 ?'':'0')+dd);
		if(nForecastYMD < nObserveYMD){
			requestWeatherInfo();
		}else{
			var nTemp = Number(localStorage.getItem("tm").substring(8,10))+3;
			var nDateDiffA = nTemp >= 24 ? nTemp-24 : nTemp;
			var nDateDiffB = dNow.getHours();
			if(nDateDiffA < nDateDiffB){
				requestWeatherInfo();
			}else{
				var sTemp = localStorage.getItem('temp');
				var sPty = localStorage.getItem('pty');
				var sSky = localStorage.getItem('sky');
				var sStatus = localStorage.getItem('status');
				var sPm10Value = localStorage.getItem('pm10Value');
				var sAirStatus = localStorage.getItem('airStatus');
				changeWeatherImage(sPty, sSky);
				$('li.ondo').text(sTemp+'℃');
				$('li.weatherStatus').text(sStatus);
				$('li.airStatus').html('미세먼지 '+sPm10Value+' ㎍/㎥ <span style="margin-left: 10px;">대기환경지수 '+sAirStatus+'</span>');
			}
		}
	}else{
		//console.log('최초접속');
		requestWeatherInfo();
	}
}

function changeWeatherImage(pty, sky){
	//var hostIndex = location.href.indexOf(location.host)+location.host.length;
	//var contextPath = location.href.substring(hostIndex,location.href.indexOf('/',hostIndex+1));
	var sImageUrl = '';
	if(pty > 0){
		//sImageUrl = contextPath+'/resource/images/common/ico_weather_pty_'+pty+'.png';
		sImageUrl = '//'+location.host+'/resource/images/common/ico_weather_pty_'+pty+'.png';
	}else{
		//sImageUrl = contextPath+'/resource/images/common/ico_weather_sky_'+sky+'.png';
		sImageUrl = '//'+location.host+'/resource/images/common/ico_weather_sky_'+sky+'.png';
		location.host
	}
	$('#weatherImg').attr('src',sImageUrl);
}

function requestWeatherInfo(){
	$.get('/data/weather', {}, function(data){
		setWeatherInfoToLocalStorage(data);
		changeWeatherImage(data.pty, data.sky);
		$('li.ondo').text(data.temp+'℃');
		$('li.weatherStatus').text(data.status);
		$('li.airStatus').html('미세먼지 '+data.pm10Value+' ㎍/㎥ <span style="margin-left: 10px;">대기환경지수 '+data.airStatus+'</span>');
	}, 'json');
}

function setWeatherInfoToLocalStorage(data){
	localStorage.setItem('tm', data.tm);
	localStorage.setItem('temp', data.temp);
	localStorage.setItem('pty', data.pty);
	localStorage.setItem('sky', data.sky);
	localStorage.setItem('status', data.status);
	localStorage.setItem('pm10Value', data.pm10Value);
	localStorage.setItem('airStatus', data.airStatus);
}

function searchPage(){
	var hostIndex = location.href.indexOf(location.host)+location.host.length;
	var contextPath = location.href.substring(hostIndex,location.href.indexOf('/',hostIndex+1));
	var win_w = $(window).innerWidth();
	var keyword = '';
	if (window.event.keyCode == 13 || !window.event.keyCode) {
		if(win_w>=1200){
			keyword = $.trim($('input[name=keyword]').eq(0).val());
		}else{
			keyword = $.trim($('input[name=keyword]').eq(1).val());
		}				
		if(!keyword){
			alert('검색어를 입력해주세요');
			return;
		}
		window.location.href='/search/'+keyword;
	}	 
}
