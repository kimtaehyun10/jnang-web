<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<script src="//code.jquery.com/jquery-3.4.1.min.js"></script>
<html>
    <head>
<style type="text/css">
@charset "utf-8";
/* input[type="checkbox"], input[type="radio"] { display: none; } */
input[type="checkbox"] + label, input[type="radio"] + label { margin-right: 5px; width: initial; }
input[type="checkbox"] + label span, input[type="radio"] + label span {
	display: inline-block;
	margin-right: 10px;
	width: 19px;
	height: 19px;
	background-image: url(../../images/dfmc/input.png);
	vertical-align: middle;
	cursor: pointer;
}
input[type="checkbox"][disabled] + label, input[type="radio"][disabled] + label { color: #aaa; }
input[type="checkbox"][disabled] + label span, input[type="radio"][disabled] + label span { cursor: default; }

input[type="checkbox"] + label span { background-position: 0 0; }
input[type="checkbox"]:checked + label span { background-position: -19px 0; }
input[type="checkbox"][disabled] + label span { background-position: -38px 0; }
input[type="checkbox"][disabled]:checked + label span { background-position: -57px 0; }

input[type="radio"] + label span { background-position: -76px 0; }
input[type="radio"]:checked + label span { background-position: -95px 0; }
input[type="radio"][disabled] + label span { background-position: -114px 0; }
input[type="radio"][disabled]:checked + label span { background-position: -133px 0; }

/* AXISj 그리드 내부 input 기본사용 */
.AXGrid input[type="checkbox"], .AXGrid input[type="radio"] { display: inline-block; width: 19px; height: 19px; }
.table input[type="checkbox"], .table input[type="radio"] { display: block; }

/* #Page input.AXInput { width: 63%; min-width: 100px; } */
.div_table { display: table; width: 100%; }
.div_block { padding: 5px 5px 5px 5px; overflow: hidden; }.select_box, .input_box { position: relative; display: inline-block; /*AXInput Select 위치 맞추기 위한 트릭*/ }
.section_map { background-color: #efefef; border: 5px solid white; vertical-align: top; }

/* Popup */
.popup_box {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    font-size: 12px;
    background-color: white;
    border: 1px solid #aaa;
    /* -webkit-box-shadow: 0 0 3px 0 rgba(0,0,0,0.2);
    -moz-box-shadow: 0 0 3px 0 rgba(0,0,0,0.2);
    box-shadow: 0 0 3px 0 rgba(0,0,0,0.2); */
}
.pop_title {
    padding: 0 20px;
    height: 50px;
    line-height: 45px;
    border-top: 4px solid #e7eaec;
    border-bottom: 1px solid #ccc;
}
.pop_title h3 { color: #2c82c9; }
.pop_title_s { 
	margin-bottom: 5px; 
	padding-left: 15px; 
	font-weight: bold;
	background-image: url(../../images/egovframework/com/tit_icon_pop.gif); 
	background-repeat: no-repeat;
	background-position: left center;
}
.popup_box .div_block { padding: 5px; }
/*
.popup_box .div_block { margin-bottom: 30px; padding: 5px; }
*/

.blockmask, .blockempty {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
}
.blockmask { background-color: rgba(0,0,0,.2);}
.blockempty {
	background-color: #efefef;
	text-align: center;
	line-height: 200px;
	color: #aaa;
	border: 1px solid #ccc;
}

.div_over {
	position: absolute;
    top: 50px;
    left: 10px;
    right: 0;
    bottom: 50px;
    padding: 10px 0;
    overflow-y: auto;
}
.div_position { position: relative; }
.div_ab { position: absolute; }

.main_disc { margin-top: 20px; padding: 10px; background-color: #efefef; line-height: 20px; }
.main_disc span { color: #2c82c9; }

.pop_foot {
    position: absolute;
    padding: 12px 20px;
    bottom: 0;
    width: 93%;
    height: 50px;
    text-align: right;
    background-color: #efefef;
    border-top: 1px solid #ccc;
}
.pop_foot .side_disc { position: absolute; left: 20px; }
.popup_box textarea { padding: 0 10px; line-height: 30px; }
.popup_box textarea[readonly], textarea[readonly] { background-color: #efefef; color: #999; }
.pop_info { padding: 10px 20px; min-height: 45px; line-height: 25px; border-bottom: 1px solid #ccc; background-color: #efefef; }
.pop_block { float: left; width: 100%;  }
.pop_left, .pop_right, .pop_middle {
	position: relative;
    padding: 10px 0 0 10px;
    border: 1px solid #ccc;
}
.pop_middle { width: 100%; padding-right: 10px}
.pop_left { width: 542px; }
.pop_right { width: 302px; }
.pop_gridblock {
    position: relative;
    margin: 0 30px;
}
.sale_list, .sale_list li, .sale_select {
	-webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    border-radius: 2px;
}
.sale_list, .sale_select {
    margin-right: 10px;
    padding: 10px 10px 0 10px;
	height: 150px;
	background-color: #efefef;
	border: 1px solid #ccc;
	overflow-y: auto;
}
.sale_list.score {
	position: absolute;
	top: -14px;
	left: 80px;
	padding: 5px 5px 0 5px;
}
.sale_list.page { position: relative; display: inline-block; }
.sale_list li {
	margin-bottom: 10px;
	padding: 5px 10px;
	background-color: white;
	color: #777;
	border: 1px solid #ccc;
}
.sale_list.score li {
	margin-bottom: 5px;
	padding: 2px 10px;
}
.sale_list li > label { width: 100%; line-height: 20px; }
.sale_list li > label > span { float: right; margin-right: 0; }
.sale_list .before_text { margin-top: 50px; text-align: center; color: #777; }

.sale_select {
	padding: 0 0 0 10px;
	overflow: hidden;
}
.sale_detail {
	margin: 10px 10px 0 0;
    padding-top: 30px;
    border-top: 1px dashed #ccc;
}
.sale_set {
	padding: 0 10px;
	height: 40px;
	line-height: 40px;
	background-color: #efefef;
	color: #aaa;
	border: 1px solid #ccc;
}
.sale_set span { margin-left: 5px; font-size: 16px; font-weight: bold; color: #777; }

.section_set { font-size: 16px; font-weight: bold; color: #2c82c9; }

.score_box {
	position: absolute;
	width: 140px;
	height: 60px;
	text-align: center;
	line-height: 60px;
	font-weight: bold;
	background-color: #efefef;
	border: 1px solid #ccc;
}
.score_box p { display: inline-block; font-size: 30px; }
.score_box span { font-size: 20px; color: #999; }

.assign_card {
	padding: 10px 5px 0 5px;
	background-color: white;
	border: 1px solid #ccc;
	overflow-y: auto;
}
.assign_card li {
	float: left;
	margin: 0 5px 10px 5px;
	padding: 10px;
	width: 193px;
	height: 175px;
	background-color: #efefef;
	border: 1px solid #ccc;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    cursor: pointer;
    opacity: .7;
}
.assign_card li:hover, .assign_card li.active { border-width: 2px; opacity: 1; }
.assign_card li h2 {
	margin-left: -10px;
    padding-left: 10px;
    width: 181px;
    height: 30px;
    line-height: 30px;
    background-color: #ccc;
    color: white;
    font-weight: normal;
}
.assign_card li p {
	margin: 10px 0;
	height: 24px;
    font-size: 14px;
    font-weight: bold;
    vertical-align:middle;
}
.assign_card li .num_1, .assign_card li .num_2 { display: block; }
.assign_card li .daynight span, .assign_card li .total, .assign_card li .standby, .assign_card li .num_3 { display: inline-block; }
.assign_card li .num_1, .assign_card li .num_2, .assign_card li .num_3,
.assign_card li .daynight span, .assign_card li .total, .assign_card li .standby {
    height: 20px;
    line-height: 20px;
    text-align: center;
}
.assign_card li .num_1, .assign_card li .num_2, .assign_card li .daynight span {
	-webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
}
.assign_card li .num_1, .assign_card li .num_2, .assign_card li .num_3 { background-color: white; }
.assign_card li .num_1 { margin-bottom: 5px; }
.assign_card li .num_3 { margin-bottom: 2px; }
.assign_card li .num_2 { width: 99px; }
.assign_card li .num_3 { width: 92px; }
.assign_card li .daynight { font-size: 0; letter-spacing: 0; word-spacing: 0; }
.assign_card li .daynight span { margin: 5px 1px; width: 54px; background-color: #ccc; font-size: 12px; font-weight: bold; color: #ddd; }
.assign_card li .total, .assign_card li .standby { width: 84px; text-align: left; }

.assign_card li.in { border-color: #2c82c9; }
.assign_card li.out { border-color: #00a885; }
.assign_card li.in h2 { background-color: #2c82c9; }
.assign_card li.out h2 { background-color: #00a885; }
.assign_card li .daynight span.on { background-color: #f7da64; color: #777; }

.assign_card.page li { width: 213px; }
.assign_card.page li h2 { width: 201px; height:60px;text-align: center;align-content: center;}
.assign_card.page li .num_2 { width: 109px; }
.assign_card.page li .daynight span { width: 60px; }
.assign_card.page li .total, .assign_card.page li .standby { width: 94px; }


.assign_locker {
	padding: 10px 5px 0 5px;
	background-color: white;
	border: 1px solid #ccc;
	overflow-y: auto;
}
.assign_locker li {
	float: left;
	margin: 0 5px 10px 5px;
	padding: 10px;
	width: 193px;
	height: 50px;
	background-color: #efefef;
	border: 2px solid #ccc;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    cursor: pointer;
    opacity: .7;
}
.assign_locker li:hover, .assign_locker li.active { border-width: 2px; opacity: 1; }

.assign_locker li p {
	margin: 10px 0;
	height: 24px;
    font-size: 14px;
    font-weight: bold;
    vertical-align:middle;
}
.assign_locker li .num_1, .assign_locker li .num_2 { display: block; }
.assign_locker li .daynight span, .assign_locker li .total, .assign_locker li .standby, .assign_locker li .num_3 { display: inline-block; }
.assign_locker li .num_1, .assign_locker li .num_2, .assign_locker li .num_3,
.assign_locker li .daynight span, .assign_locker li .total, .assign_locker li .standby {
    height: 20px;
    line-height: 20px;
    text-align: center;
}
.assign_locker li .num_1, .assign_locker li .num_2, .assign_locker li .daynight span {
	-webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
}
.assign_locker li .num_1, .assign_card li .num_2, .assign_card li .num_3 { background-color: white; }
.assign_locker li .num_1 { margin-bottom: 5px; }
.assign_locker li .num_3 { margin-bottom: 2px; }
.assign_locker li .num_2 { width: 99px; }
.assign_locker li .num_3 { width: 92px; }
.assign_locker li .daynight { font-size: 0; letter-spacing: 0; word-spacing: 0; }
.assign_locker li .daynight span { margin: 5px 1px; width: 54px; background-color: #ccc; font-size: 12px; font-weight: bold; color: #ddd; }
.assign_locker li .total, .assign_locker li .standby { width: 84px; text-align: left; }

.assign_locker li.in { border-color: red; }
.assign_locker li.out { border-color: #00a885; }
.assign_locker li .daynight span.on { background-color: #f7da64; color: #777; }

.assign_locker.page li { width: 50px; }
.assign_locker.page li .num_2 { width: 109px; }
.assign_locker.page li .daynight span { width: 60px; }
.assign_locker.page li .total, .assign_card.page li .standby { width: 94px; }




.assign_messageCD {
	padding: 2px 2px 2px 2px;
	background-color: white;
	border: 1px solid #ccc;
	overflow-y: auto;
}
.assign_messageCD li {
	float: left;
	margin: 0 2px 2px 2px;
	padding: 2px;
	width: 193px;
	height: 40px;
	background-color: #efefef;
	border: 2px solid #ccc;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    cursor: pointer;
    opacity: .7;
}
.assign_messageCD li:hover, .assign_messageCD li.active { border-width: 2px; opacity: 1; }

.assign_messageCD li p {
	margin: 10px 0;
	height: 24px;
    font-size: 14px;
    font-weight: bold;
    vertical-align:middle;
}
.assign_messageCD li .num_1, .assign_messageCD li .num_2 { display: block; }
.assign_messageCD li .daynight span, .assign_messageCD li .total, .assign_messageCD li .standby, .assign_messageCD li .num_3 { display: inline-block; }
.assign_messageCD li .num_1, .assign_messageCD li .num_2, .assign_messageCD li .num_3,
.assign_messageCD li .daynight span, .assign_messageCD li .total, .assign_messageCD li .standby {
    height: 20px;
    line-height: 20px;
    text-align: center;
}
.assign_messageCD li .num_1, .assign_messageCD li .num_2, .assign_messageCD li .daynight span {
	-webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
}
.assign_messageCD li .num_1, .assign_messageCD li .num_2, .assign_messageCD li .num_3 { background-color: white; }
.assign_messageCD li .num_1 { margin-bottom: 5px; }
.assign_messageCD li .num_3 { margin-bottom: 2px; }
.assign_messageCD li .num_2 { width: 99px; }
.assign_messageCD li .num_3 { width: 92px; }
.assign_messageCD li .daynight { font-size: 0; letter-spacing: 0; word-spacing: 0; }
.assign_messageCD li .daynight span { margin: 5px 1px; width: 54px; background-color: #ccc; font-size: 12px; font-weight: bold; color: #ddd; }
.assign_messageCD li .total, .assign_messageCD li .standby { width: 84px; text-align: left; }

.assign_messageCD li.in { border-color: red; }
.assign_messageCD li.out { border-color: #00a885; }
.assign_messageCD li .daynight span.on { background-color: #f7da64; color: #777; }

.assign_messageCD.page li { width: 140px; }
.assign_messageCD.page li .num_2 { width: 109px; }
.assign_messageCD.page li .daynight span { width: 60px; }
.assign_messageCD.page li .total, .assign_messageCD.page li .standby { width: 94px; }






.assign_Item {
	padding: 10px 5px 0 5px;
	background-color: white;
	border: 1px solid #ccc;
	overflow-y: auto;
}
.assign_Item li {
	float: left;
	margin: 0 5px 10px 5px;
	padding: 10px;
	width: 193px;
	height: 78px;
	background-color: #efefef;
	border: 2px solid #ccc;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    cursor: pointer;
    opacity: .7;
}
.assign_Item li:hover, .assign_Item li.active { border-width: 2px; opacity: 1; }

.assign_Item li p {
	margin: 10px 0;
	height: 24px;
    font-size: 14px;
    vertical-align:middle;
}
.assign_Item li .num_1, .assign_Item li .num_2 { display: block; }
.assign_Item li .daynight span, .assign_Item li .total, .assign_Item li .standby, .assign_Item li .num_3 { display: inline-block; }
.assign_Item li .num_1, .assign_Item li .num_2, .assign_Item li .num_3,
.assign_Item li .daynight span, .assign_Item li .total, .assign_Item li .standby {
    height: 20px;
    line-height: 20px;
    text-align: center;
}
.assign_Item li .num_1, .assign_Item li .num_2, .assign_Item li .daynight span {
	-webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
}
.assign_Item li .num_1, .assign_Item li .num_2, .assign_Item li .num_3 { background-color: white; }
.assign_Item li .num_1 { margin-bottom: 5px; }
.assign_Item li .num_3 { margin-bottom: 2px; }
.assign_Item li .num_2 { width: 99px; }
.assign_Item li .num_3 { width: 92px; }
.assign_Item li .daynight { font-size: 0; letter-spacing: 0; word-spacing: 0; }
.assign_Item li .daynight span { margin: 5px 1px; width: 54px; background-color: #ccc; font-size: 12px; font-weight: bold; color: #ddd; }
.assign_Item li .total, .assign_Item li .standby { width: 84px; text-align: left; }

.assign_Item li.in { border-color: red; }
.assign_Item li.out { border-color: #00a885; }
.assign_Item li .daynight span.on { background-color: #f7da64; color: #777; }

.assign_Item.page li { width: 156px; text-align:center;}
.assign_Item.page li .num_2 { width: 109px; }
.assign_Item.page li .daynight span { width: 60px; }
.assign_Item.page li .total, .assign_Item.page li .standby { width: 94px; }

.score_detail { position: relative; }
.score_detail > div {  }
.score_detail > div:last-child { border: none; }
.sc_select, .sc_score { position: relative; float: left; }
.sc_select { width: 180px; }
.sc_score { width: 100px; }
.sc_score p {
	width: 80px;
	height: 30px;
	text-align: center;
	line-height: 30px;
	font-size: 20px;
}
.sc_desc {
	position: relative;
    padding: 10px 20px;
    margin-left: 250px;
    line-height: 24px;
    border-right: 1px solid #ccc;
    border-left: 1px solid #ccc;
    border-bottom: 1px dashed #ccc;
}
.score_detail > div:nth-child(odd) .sc_desc { background-color: #efefef; }
.score_detail > div:first-child .sc_desc { border-top: 1px solid #ccc; }
.score_detail > div:last-child .sc_desc { border-bottom: 1px solid #ccc; }
.sc_score p, .sc_desc p { font-weight: bold; }

/* col + input, select */
.col_s, .col_m, .col_l, .col_x, col_xl { float:left;}
/*
.col_s, .col_m, .col_l, .col_x, col_xl { float:left; margin-right: 10px; }
*/

.col_s { width: 200px; }
.col_s1 { width: 200px; }
.col_m { width: 300px; }
/*.col_l { width: 600px; }*/
.col_l {  }
.col_x { width: 1000px; }
.row_line { height: 4px; border-bottom: 1px solid #e6e6f1; margin-bottom: 3px; width:100%}
.row_line_bottom { height: 4px;}
.col_btn { position: relative; right: 0; }

.col_s label, .col_m label, .col_l label, .col_x label {
	position: relative;
	display: inline-block;
	line-height: 25px;
	vertical-align: top;
}
.col_s label { width: 60px;  text-align: right; padding-right: 10px}
.col_m label, .col_l label, .col_x label { width: 80px; text-align: right; padding-right: 15px}
 div[class^="col_"].disable { display: none; }
div[class^="col_"] label > .btn { padding: 2px 5px 1px 5px; }
.popup_box .col_s { width: 199px; }
.popup_box .pop_middle .col_s { width: 197px; }
.popup_box label { width: 90px; text-align: right;padding-left: 10px;padding-right: 10px; font-weight:bold;}
.popup_box .div_table label { width: 70px; text-align: right;padding-left: 0px;padding-right: 10px; font-weight:bold;}
.popup_box .AXInput { width: 110px; }

.popup_box .col_s, .popup_box .col_m, .popup_box .col_l .popup_box .col_x { margin-right: 10px; }
.popup_box .col_m { width: 255px; }
/*.popup_box .col_l { width: 500px; }*/
.popup_box .col_l { }


/* Tab */
.tab_content { display: none; }
.tab_content .ct_gridblock { float: left; margin: 0; width:100%}

/* Grid */
.grid_con { background-color: #ccc; border: 1px solid #ccc; }

/* BTN */
.btn {
    display: inline-block;
    padding: 1px 10px 1px 10px;
    background-color: white;
    line-height: 1.6em;
    color: #777;
    border: 1px solid #ccc;
    -webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    border-radius: 2px;
    cursor: pointer;
}
.btn:hover {
    background-color: #ccc;
    color: white;
}
.btn.in {
    background-color: #ccc;
    color: white;
    border-color: #ccc;
}
.btn.in:hover {
    background-color: white;
    color: #777;
}

.AXGrid .btn { margin: 0 2px; padding: 0 5px; line-height: 18px; }

.btn.red { color: #e25041; border-color: #e25041; text-align:center;}
.btn.orange { color: #f37934; border-color: #f37934; text-align:center;}
.btn.blue { color: #2c82c9; border-color: #2c82c9; text-align:center;}
.btn.green { color: #00a885; border-color: #00a885; text-align:center;}

.btn.red:hover { color: white; background-color: #e25041; }
.btn.orange:hover { color: white; background-color: #f37934; }
.btn.blue:hover { color: white; background-color: #2c82c9; }
.btn.green:hover { color: white; background-color: #00a885; }

.btn.in.red { background-color: #e25041; color: white; border-color: #e25041; }
.btn.in.orange { background-color: #f37934; color: white; border-color: #f37934; }
.btn.in.blue { background-color: #2c82c9; color: white; border-color: #2c82c9; }
.btn.in.green { background-color: #00a885; color: white; border-color: #00a885; }

.btn.in.red:hover { background-color: white; color: #e25041; }
.btn.in.orange:hover { background-color: white; color: #f37934; }
.btn.in.blue:hover { background-color: white; color: #2c82c9; }
.btn.in.green:hover { background-color: white; color: #00a885; }

.btn.in.disabled, .btn.disabled {
	background-color: #ccc;
    border-color: transparent;
	color: #ddd;
    cursor: not-allowed;
	/* pointer-events 는 ie11 부터만 작동됩니다. 현재 webkit 도 없음 */
    pointer-events: none;
}

 .col_m > .btn { padding: 2px 5px; }
 
 div[class^="col_"] img { width: 100%; height: 100%; }
 .col_img {
    position: relative;
    display: inline-block;
    margin: 0 28px;
    padding: 10px;
    width: 150px;
    height: 150px;
    background-color: white;
}
.img_cover {
    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background-color: rgba(0,0,0,.1);
    text-align: center;
 }
 .img_cover p {
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -10px;
    margin-left: -50px;
    width: 100px;
    height: 20px;
    line-height: 20px;
    background-color: rgba(0,0,0,.2);
    color: white;
    -webkit-border-radius: 3px;
    -moz-border-radius: 3px;
    border-radius: 3px;
    font-weight: bold;
}
 .col_img:hover .img_cover { display: none; }
 
 .transfer_img {
    position: relative;
    margin-top: -20px;
    margin-left: 192px;
    font-size: 16px;
    color: #777;
    font-weight: bold;
 }

/* File Upload */
.file_box input[type="file"] {
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip:rect(0,0,0,0);
    border: 0;
}
.file_box label {
    display: inline-block;
    padding: .5em .75em;
    color: #999;
    text-align: center;
    font-size: inherit;
    line-height: normal;
    vertical-align: middle;
    background-color: #fdfdfd;
    cursor: pointer;
    border: 1px solid #ebebeb;
    border-bottom-color: #e2e2e2;
    border-radius: .25em;
}

.file_box .upload_name {
    display: inline-block;
    padding: .5em .75em;  /* label의 패딩값과 일치 */
    background-color: #f5f5f5;
    border: 1px solid #ebebeb;
    border-bottom-color: #e2e2e2;
    border-radius: .25em;
    -webkit-appearance: none; /* 네이티브 외형 감추기 */
    -moz-appearance: none;
    appearance: none;
    color: #999;
}
.file_box.pop { position: relative; margin-top: 15px; margin-left: -10px; }
.file_box.pop label {
    position: absolute;
    width: inherit;
    -webkit-border-radius: 0 3px 3px 0;
    -moz-border-radius: 0 3px 3px 0;
    border-radius: 0 3px 3px 0;
}
.file_box.pop .upload_name {
    margin-bottom: 15px;
    width: 120px;
    border-color: #aaa;
    -webkit-border-radius: 3px 0 0 3px;
    -moz-border-radius: 3px 0 0 3px;
    border-radius: 3px 0 0 3px;
}

/* 다음지도 */
#container {overflow:hidden;height:380px;position:relative;}
#btnRoadview,  #btnMap {position:absolute;top:5px;left:5px;padding:7px 12px;font-size:14px;border: 1px solid #dbdbdb;background-color: #fff;border-radius: 2px;box-shadow: 0 1px 1px rgba(0,0,0,.04);z-index:1;cursor:pointer;}
#btnRoadview:hover,  #btnMap:hover{background-color: #fcfcfc;border: 1px solid #c1c1c1;}
#container.view_map #mapWrapper {z-index: 10;}
#container.view_map #btnMap {display: none;}
#container.view_roadview #mapWrapper {z-index: 0;}
#container.view_roadview #btnRoadview {display: none;}

/* AXISJ CSS 변경내용 - 후에 이동, 따로 작성할 것 */
.AXInput { height: inherit; }
.AXGrid .AXInput { padding: 3px 4px 2px 4px; }
.AXSelect { height: 22px; line-height: 22px; }
.AXGrid .AXgridScrollBody .AXGridBody { background-color: #eee; }
.AXGrid .AXgridPageBody { height: 30px; }
.AXGrid .AXgridPageBody .AXgridPagingUnit .AXgridPageNo { width: 45px; }/* 임시로 늘려준 길이값 */
.AXTree_none .AXTreeScrollBody .AXTreeBody { background-color: #efefef; }
.userSelectBox { border: none; padding: 0; border-radius: 0; overflow: auto; }
.btn > .axi { font-size: 14px; vertical-align: text-bottom; }
.AXGrid .AXgridScrollBody .AXGridBody .gridBodyTable tbody tr td .bodyTdText,
.AXGrid .AXgridScrollBody .AXGridBody .gridBodyTable tfoot tr td .bodyTdText,
.AXGrid .AXgridScrollBody .AXGridBody .gridBodyTable thead tr td .bodyTdText { text-shadow: none; }
.AXGrid .AXgridScrollBody .AXGridBody .gridBodyTable tbody tr.noListTr td { background: #eee; border-bottom: none; }
.userSelectBox { border: none; padding: 10px 10px 0 0; border-radius: 0; overflow: auto; }
.userSelectBox .readyDrag {
	margin-bottom: 10px;
	padding: 5px 10px;
	background-image: inherit;
	background-color: white;
	border-color: #2c82c9;
	-webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    border-radius: 2px;
}
.userSelectBox .beSelected { background-color: #2c82c9; color: white; }
.userSelectBox .bedraged { border: 2px solid #aaa !important; }

table.tbl_l1 {width:100%;border-spacing:0;}
table.tbl_l1 th {text-align:center;font-weight:bold;padding:5px;border:1px solid #e3e3e3;}
table.tbl_l1 th.l {text-align:left;}
table.tbl_l1 th.r {text-align:right;}
table.tbl_l1 td {text-align:center;padding:5px;border:1px solid #e3e3e3;}
table.tbl_l1 td.l {text-align:left;}
table.tbl_l1 td.r {text-align:right;}

</style>
        <script>        

        var discountItemList;
        var listCount = 0;
    
        $(document.body).ready(function(){
    
            //버튼초기화
            condirectBtn("1");
        });
        
      	//즉시감면 대상 확인
        function checkGpkiMember() {
			//유효성 검사	
			if ($('#gpkiagree').is(":checked") == false){
				alert("즉시감면서비스 이용 사전안내에 동의하여야 합니다.");
				$('#gpkiagree').focus();
                return;
			}
			
        	if ($('#mberNm').val() == null || $('#mberNm').val().length == 0){
				alert("이름은 필수 입력 사항입니다.");
				$('#mberNm').focus();
                return;
			}

			if ($('#jumin1').val() == null || $('#jumin1').val().length != 6){
				alert("주민등록번호 앞자리가 올바르지 않습니다.");
				$('#jumin1').focus();
                return;
			}

			if ($('#jumin2').val() == null || $('#jumin2').val().length != 7){
				alert("주민등록번호 뒷자리가 올바르지 않습니다.");
				$('#jumin2').focus();
                return;
			}
			
            var mberNm = $("#mberNm").val();
            var jumin1 = $("#jumin1").val();
            var jumin2 = $("#jumin2").val();
            var arrDiscountItemKey = [];
            var arrDiscountItemId = [];
            $('input[name="discountItemList"]:checkbox:checked').each(function () {
                arrDiscountItemKey.push($(this).val());
                arrDiscountItemId.push($(this).attr("id"));
            });
                      	
          	if(arrDiscountItemKey == null || arrDiscountItemKey.length == 0) {
                alert("할인항목을 선택하지 않았습니다.");
                return;
        	}

          	$("#btnGpki").html("자격 확인중...");
          	$("#btnGpki").attr('onclick', '').unbind('click');
          	$("#btnGpki").css('cursor','default');
          	$("#btnGpki").css('background-color','grey');
          	
          	$("#saveButton").attr('onclick', '').unbind('click');
          	$("#saveButton").css('cursor','default');
          	$("#saveButton").css('background-color','grey');

            $.ajaxSettings.traditional = true;
            $.ajax({
                  url: "/imre/imReCk.json",
                  type: "get",
                  data: {
                	  mberNm: mberNm,
                	  jumin1: jumin1,
                	  jumin2: jumin2,
                      arrDiscountItemKey: arrDiscountItemKey,
                      arrDiscountItemId: arrDiscountItemId
                  },
                  dataType: "json",
                  success: function (data, textStatus, jqXHR) {
						$("#btnGpki").html("자격 확인");
                    	$("#btnGpki").bind("click",checkGpkiMember);
          				$("#btnGpki").css('cursor','');
          				$("#btnGpki").css('background-color','');
          				
                    	$("#saveButton").bind("click",saveDirectDis);
          				$("#saveButton").css('cursor','');
          				$("#saveButton").css('background-color','');
          				
                      if (data.result == "OK") {
                          alert("자격 확인이 완료 되었습니다.");
                          
                          var html = "";
                          var jdiscountName = "";
                          
                          $.each(data, function(returnkey, returnval){
                        	    $.each(arrDiscountItemId, function(key, val){
	                        	    if (val == returnkey){
	                        	    	
	                        	    	if (returnval == "1"){	                      
	                        	    		html = html + ' <input type="radio" name="resultItemList" id="r'+ returnkey +'" value="' + returnkey + '"/>';
	                        	    		html = html + ' <label for="r'+ returnkey +'" style="text-align:left; width:150px; margin-right: 0px; padding-right: 0px;">' + $("label[for='"+ returnkey +"']").html() + '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(자격확인완료)</label>';
	                        	    	}else{
	                        	    		html = html + ' <input type="radio" name="resultItemList" id="r'+ returnkey +'" value="' + returnkey + '" disabled />';
	                        	    		html = html + ' <label for="r'+ returnkey +'" style="text-align:left; width:150px; margin-right: 0px; padding-right: 0px;">' + $("label[for='"+ returnkey +"']").html() + '<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(' + returnval + ')</label>';
	                                    }
	                        	    	
	                        	    	 $('input[id='+val+']').attr('checked',false);
	                        	    }
                        	    });
                        	});
                          
                          $("#resultItemUl").html(html);
                          $("#discountItemUl").css("display","none");
                          $("#resultItemUl").css("display","");	  
                          $("#resultDivnotice").html("<p><br><span style='color:blue;font-weight:bold;'>&nbsp;&nbsp;※ 즉시감면을 적용할 대상을 선택하십시요.</p>");
                          
                          condirectBtn("2");
                      } else {
                      	alert("자격 확인에 실패/해당사항이 없습니다.");
						$("#btnGpki").html("자격 확인");
                    	$("#btnGpki").bind("click",checkGpkiMember);
          				$("#btnGpki").css('cursor','');
          				$("#btnGpki").css('background-color','');

                    	$("#saveButton").bind("click",saveDirectDis);
          				$("#saveButton").css('cursor','');
          				$("#saveButton").css('background-color','');
          				
                        $("#resultDivnotice").html("");
                        
                        condirectBtn("1");
                      }
                  },
                  error: function (jqXHR, textStatus, errorThrown) {                	  
                      alert("code:" + jqXHR.status + "\nmessage:" + textStatus + "\nerror:" + errorThrown);
                  }
              });
          }
        
        //즉시감면 버튼 컨트롤
        function condirectBtn(flg){
        	if (flg == "1"){
        		$("#btnGpki").show();
        		$("#clearGpki").hide();
        	}else{
        		$("#btnGpki").hide();
        		$("#clearGpki").show();
        	}
        }
        
        //즉시감면대상 확인 초기화
        function clearGpkiMember(){

            $("#discountItemUl").css("display","");
            $("#resultItemUl").css("display","none");
            $("#resultItemUl").html("");
            
            condirectBtn("1");
            
        }  
        
        //즉시감면 적용
        function saveDirectDis(){
        	var returnval = $('input[name="resultItemList"]:checked').val();        	
        	if (returnval == undefined){
        		alert("적용할 대상을 선택하십시요.");
        		return;
        	}
            	      	
        	opener.parent.returnPopImre(returnval);
            
            window.close();
            
        	
        }
        
    </script>
    <title>즉시감면서비스 대상자 확인</title>
    </head>
    
    
    <body>
        <form name="frm">
        <div class="div_block">
            <div class="div_table">
            
                <div class="pop_block">
            
	                
	                <div class="pop_middle" style="border:0px">
	                    <div><!-- 블럭 안에 캡션은 스타일 달라짐 -->
	                        <p>
	                            	[행정정보공동이용센터를 통한 감면자격 즉시 확인 서비스]를 활용하여 할인 서비스를 제공하고 있습니다.<br/>
	                            	(해당 서비스를 통한 감면대상자로 확인 되는 경우 별도의 증빙서류 제출을 하지 않아도 할인 적용을 받을 수 있습니다.) <br/>
	                            	* 해당 서비스를 이용하지 않더라도 서비스 이용은 가능하며, 별도의 할인을 원하시는 경우 방문 부탁 드립니다.<br/><br/>
	                        </p>
	                            	
	                        <h3>* 즉시감면서비스 이용 사전 안내(선택)</h3>
	                        <p>
	                        	&nbsp;- 본인이 동의한 위 사무에 대한 행정정보를 중랑구시설관리공단이 「전자정부법」 제36조에 따른 행정정보 공동이용을 통해 「개인정보 보호법」 제23조에 따른
	                        	건강에 관한 정보나 같은 법 시행령 제19조에 따른 주민등록번호가 포함된 행정정보를 처리하는데 동의합니다.<br/>
	 							&nbsp;- 만일 위 행정정보를 이용기관이 처리에 대해 본인이 동의를 하지 아니할 경우에도 불이익은 없습니다.<br/>
	 							&nbsp;&nbsp;&nbsp;다만, 동의하지 않은 경우에는 그 해당 부분에 대해서는 직접 서류를 제출하여야 합니다.
	                        </p>
	                        
	                        <br/>
	                        <p>
	                    	<input name="gpkiagree" id="gpkiagree" type="checkbox" value="Y"/>
		                	<label for="gpkiagree" style="color:black;">위 즉시감면서비스 이용 사전 안내에 동의합니다.</label>
	                        </p>
		                	
	                        <br/>
	                    </div>                       
	                    <div class="div_table">
	                        <div class="line">
	                            <div class="col_l">
		                        	<label for="mberNm" style="width:100px;">이름</label>
	                        		<input type="text" name="mberNm" id="mberNm" maxlength="16" class="AXInput av-required" value="<c:if test="${not empty imreName && imreName ne ''}">${imreName}</c:if>" <c:if test="${not empty imreName && imreName ne ''}">readonly</c:if> />
		                        </div>
		                    </div>
	                        <div class="line">
	                            <div class="col_l">
		                            <label for="mberBirth" style="width:100px;">주민등록번호</label>
		                            <input type="text" name="jumin1" id="jumin1" title="주민등록번호" value="<c:if test="${not empty imreBirth && imreBirth ne ''}">${imreBirth}</c:if>" maxlength="6"
		                                   class="AXInput" <c:if test="${not empty imreBirth && imreBirth ne ''}">readonly</c:if> />
		                                   -
		                            <input type="password" name="jumin2" id="jumin2" title="주민등록번호" value="" maxlength="7"
		                                   class="AXInput" />
		                                   
		                    		&nbsp;&nbsp;&nbsp;<a id="btnGpki" class="btn in blue" onclick="checkGpkiMember();">자격확인</a>	                    		
		                    		&nbsp;&nbsp;&nbsp;<a id="clearGpki" class="btn in green" onclick="clearGpkiMember();">결과 초기화</a>
		                        </div>
	                    	</div>
	                    </div>
	                    <div class="div_table">
	                    	
	                    	<div style="padding: 10px 0px;">                    	
	                        	<p >
	                            	&nbsp;즉시감면서비스를 받고자 하는 할인 대상을 선택해 주십시요
	                        	</p>
	                        	<div class="row_line"></div>
								<div id="discountItemUl" class="col_l"  style="line-height:25px;">
									<c:forEach items="${imreDisList}" var="map"> 
					                    <input type="checkbox" name="discountItemList" id="${map.IMREDISCODE}" value="${map.IMREDISGUBN}"/>
					                    <label for="${map.IMREDISCODE}" style="text-align:left; width:153px; margin-right: 0px; padding-right: 0px;">${map.IMREDISNAME}</label>
				                    </c:forEach>
								</div>			
								<div id="resultItemUl" class="col_l"  style="line-height:25px;display:none">
								</div>									
		            			<div id="resultDivnotice" >
		            			</div>
	                		</div>
	                    </div>   
	                </div>
	            </div>
            </div>
        </div>
        
        <div class="pop_foot">
           	<a href="#" id="saveButton" class="btn in green" onclick="saveDirectDis();">적용</a>
           	<a href="#" id="close" class="btn in" onclick="window.close();">취소</a>
	    </div>
            
        </form>
    </body>
    
</html>