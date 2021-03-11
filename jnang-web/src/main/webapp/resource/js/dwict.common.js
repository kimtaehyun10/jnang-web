/**
* @ProjectDescription dwict.common.js.
*
* @Author RGJ
* @Version 1.32
*/
'use strict'
let pageIndex, startIndex, lastPage, lastIndex;
$(function(){
	const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});
	
const csrfSafeMethod = function(method) {
	return (/^(HEAD|GET|POST|PATCH|DELETE)$/.test(method));
};

const setMenuTitle = function(bgClass, upTitleName, subTitleName, isChangeNaviText) {
	$('#subNavMenu').addClass(bgClass);
	$('#upTitle').text(upTitleName);
	$('#sub_navi2').text(subTitleName);
	if(isChangeNaviText){
		$('#emptyDep1').text(upTitleName);
		$('#emptyDep2').text(subTitleName);		
	}
};

const movePage = function(value, searchFunction){
	switch(value){
	case 0:
		movePage(1, searchFunction);
		break;
	case -1:
		pageIndex = Number($('#h_pageIndex').val());
		if(pageIndex!=1){
			movePage(pageIndex-1, searchFunction);
		}else{
			movePage(pageIndex, searchFunction);
		}
		break;
	case -2:
		pageIndex = Number($('#h_pageIndex').val());
		if(pageIndex!=lastIndex){
			movePage(pageIndex+1, searchFunction);
		}else{
			if(pageIndex!=lastPage){
				movePage(pageIndex+1, searchFunction);
			}
		}
		break;
	case -3:
		movePage(lastPage, searchFunction);
		break;
	default:
		$('#h_pageIndex').val(value);
		let execute = new Function(searchFunction);
		execute();
		break;
	}
};

const paging = function(data, searchFunction){
	let paging = '';
	lastPage = Math.ceil(data.totalCount/data.pageSize);
	startIndex = Math.floor((data.pageIndex-1)/10)*10+1;
	lastIndex = Math.ceil(data.pageIndex/10)*10 > lastPage ? lastPage : Math.ceil(data.pageIndex/10)*10;
	paging += '<a onclick="movePage(0, \''+searchFunction+'\')">&lt;&lt;</a>';
	paging += '<a onclick="movePage(-1, \''+searchFunction+'\')">&lt;</a>';
	for(var i=startIndex;i<=lastIndex;i++){
		if(i === data.pageIndex){
			paging += '<a onclick="movePage('+i+', \''+searchFunction+'\')" class="on">'+i+'</a>';
		}else{
			paging += '<a onclick="movePage('+i+', \''+searchFunction+'\')">'+i+'</a>';
		}
	}
	paging += '<a onclick="movePage(-2, \''+searchFunction+'\')">&gt;</a>';
	paging += '<a onclick="movePage(-3, \''+searchFunction+'\')">&gt;&gt;</a>';
	$('#paging').empty().append(paging);
};

const dateUtil = {
	getYear: function(){
		return new Date().getFullYear();
	},
	getYearFromStr: function(str){
		if(typeof str != 'string') return false;
		return str.substring(0,4);
	},
	getMonth: function(){
		return new Date().getMonth()+1;
	},
	getMonthFromStr: function(str){
		if(typeof str != 'string') return false;
		return str.substring(4,6);
	},
	getDay:function(){
		return new Date().getDate();
	},
	getDayFromStr: function(str){
		if(typeof str != 'string') return false;
		return str.substring(6,8);
	},
	getJson: function(str){
		if(typeof str != 'string') return false;
		return { year:str.substring(0,4), month:str.substring(4,6), day:str.substring(6,8) };
	},
	getDate: function(str){
		if(typeof str != 'string') return false;
		return Date(str.substring(0,4), str.substring(4,6), str.substring(6,8));
	},
	addHyphen: function(str){
		if(typeof str != 'string') return false;
		return str.substring(0,4) + '-' + str.substring(4,6) + '-' + str.substring(6,8);
	},
	addPoint: function(str){
		if(typeof str != 'string') return false;
		return str.substring(0,4) + '.' + str.substring(4,6) + '.' + str.substring(6,8);
	},
	getProgramStartDate: function(startdate, comcd, day){
		
		/*if(typeof startdate != 'string') return false;
		const now = new Date();
		const temp = now.getMonth() + 2;
		const month = temp.toString().length < 2 ? '0'+temp : temp;
		return now.getFullYear().toString() + month.toString() + startdate.toString();*/
		
		if(typeof startdate != 'string') return false;
		var now = new Date();
		
		if(day <='10' && comcd == 'JUNGNANG01' || comcd == 'JUNGNANG02' || comcd == 'JUNGNANG03'){
			var temp = now.getMonth() + 1;
		}else if(day <='07' && comcd == 'JUNGNANG04' || comcd == 'JUNGNANG05'){
			var temp = now.getMonth() + 1;
		}else{
			var temp = now.getMonth() + 2;
		}		
		
		var month = temp.toString().length < 2 ? '0'+temp : temp;
		return now.getFullYear().toString() + month.toString() + startdate.toString();
	},
	getProgramEndDate: function(str, monthCnt){
		if(typeof str != 'string') return false;
		const baseDay = new Date(str.substring(0,4), Number(str.substring(4,6)) + monthCnt - 1, Number(str.substring(6,8)) - 1)
		const year = baseDay.getFullYear();
		const temp1 = baseDay.getMonth() + 1;
		const temp2 = baseDay.getDate();
		const month = temp1.toString().length < 2 ? '0'+temp1 : temp1;
		const day = temp2.toString().length < 2 ? '0'+temp2 : temp2;
		return year.toString() + month.toString() + day.toString();
	}
};

const lpad = function(str, padLen, padStr){
	if(padStr.length > padLen){        
        return str;
    }
    str += '';
    padStr += '';
    while (str.length < padLen)
        str = padStr + str;
    str = str.length >= padLen ? str.substring(0, padLen) : str;
    return str;		
};

const makeParam = {
	input: function(){
		var param = new Object();
		for(var i=0; i<arguments.length; i++){
			var dom = arguments[i].toString()
			param[dom] = $('input[name='+arguments[i]+']').val();
		}
		return param;
	},
	select: function(){
		var param = new Object();
		for(var i=0; i<arguments.length; i++){
			var dom = arguments[i].toString()
			param[dom] = $('select[name='+arguments[i]+'] option:selected').val();
		}
		return param;
	},
	radio: function(){
		var param = new Object();
		for(var i=0; i<arguments.length; i++){
			var dom = arguments[i].toString()
			param[dom] = $('input:radio[name='+arguments[i]+']:checked').val();
		}
		return param;
	},
	concat: function(domName, param){
		var obj = new Object();
		obj[domName] = Object.values(param).join('');
		return obj;
	}
}
