package com.dwict.jfmc.client.smpay.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/data")
public class PayRestController {

	
	
	
	/*
	강좌 당일 결제 취소처리 부분################################################
	select * from PAY_CHANGE_INFO order by  WRITE_DH desc
	Select * from CALC_MASTER order by  WRITE_DH desc
	Select * from PAY_LIST where SLIP_NO in (Select SLIP_NO from CALC_MASTER where MEM_NO ='00135090') order by  WRITE_DH desc
	Select * from card_app_hist_damo where SLIP_NO in (Select SLIP_NO from CALC_MASTER where MEM_NO ='00135090') order by  WRITE_DH desc
	//APP_GBN = 2, 당일취소 메모
	select *, APP_GBN , CNL_DATE, REMARK from card_app_hist_damo
	-- UPDATE card_app_hist_damo SET APP_GBN=2 , CNL_DATE= , 

	select * from train_hist where MEM_NO ='00135090' order by  WRITE_DH desc
	select *, CANCEL_YN, REMARK from mem_sale  where MEM_NO ='00135090' order by  WRITE_DH desc 
	
	*/
}
