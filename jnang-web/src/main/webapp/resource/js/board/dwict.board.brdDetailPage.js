/**
* @projectDescription dwict.board.brdDetailPage.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	if(!$('#emptyDep1').text()) addUIEvnet.navi();
	if($('#h_cmsNm').val() != '') $('#sub_navi2').text($('#h_cmsNm').val());
	$('#boardCon').append(brdDtailPageHtml);
	searchBrdDetailPage();
});