/**
* @projectDescription dwict.board.faq.js
*
* @author RGJ
* @version 1.0
*/
'use strict'
$(function(){
	if($('#h_cmsNm').val() != '') $('#sub_navi2').text($('#h_cmsNm').val());
	$('#boardCon').append(faqHtml);
	searchFaq();
});
