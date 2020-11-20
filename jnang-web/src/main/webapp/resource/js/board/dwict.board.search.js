/**
* @projectDescription dwict.board.search.js
*
* @author KTH
* @version 1.0
*/
'use strict'
$(function(){
	setMenuTitle('bg_99', '통합검색', '검색결과', true);
	$('#sub_navi2').append(':'+$('#searchValue').val());
	searchKeyword();
});