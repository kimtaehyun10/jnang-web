package com.dwict.jfmc.client.smpay.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.security.service.ErrorService;
import com.dwict.jfmc.client.smpay.service.PayService;

@RestController
@RequestMapping("/smartPay")
public class PayController {


	@Resource(name = "errorService")
	private ErrorService errorService;
	
	@Resource(name = "payService")
	private PayService service;
	
	
	@RequestMapping(value = "/{dirPath}")
	public ModelAndView otherPay(ModelAndView modelAndView, @PathVariable String dirPath, HttpServletRequest request, HttpServletResponse response
		,@RequestParam Map<String, Object> testMap) throws Exception {
		String rtn = "";
		final HttpSession session = request.getSession(false);
		
		System.out.println("testMap =" + testMap);
		
		int dirPathLen = dirPath.length();
		//try {
			dirPath = (dirPathLen >= 20) ? dirPath.substring(0,20) : dirPath.substring(0,dirPathLen);
			dirPath = dirPath.replace(" ", "");
			dirPath = URLEncoder.encode(dirPath,"utf-8");
			rtn = "/smartPay/returnPay.none";
			
			Map<String, Object> rtnMap;
			switch(dirPath) {
			
				//결제 테스트 페이지
				case "testPay":				
				case "mainPay":
					rtn = "/smartPay/"+ dirPath;
					break;

				case "returnTestPay":
					//창닫을경우 이동할 URL
					//rtnMap.put("goURL", "/mypage/classStatus");
					rtn = "/smartPay/"+ dirPath;
					break;
					
				//장바구니 강좌 결제
				case "returnPay":
					//결과
					rtnMap = service.lecOrderInsert(request);
					
					//정원초과 확인후 취소처리############
					String ResultMsgDe = (String) rtnMap.get("ResultMsgDe");
					if (ResultMsgDe.equals("정원초과")) {
						
						String COMCD = (String) rtnMap.get("COMCD");
						String TID 	= (String) rtnMap.get("TID");
						String MEM_NO = (String) rtnMap.get("MEM_NO");
						String Amt 	= (String) rtnMap.get("Amt");
						
						//rtnMap.put("SLIP_NO", NULL);
						rtnMap.put("PAY_AMT", Amt);
						Map<String, Object> maps = new HashMap<>();
						maps = service.payKeyInfoCancel(rtnMap);
						rtnMap.put("goURL", "close1");
						modelAndView.addObject("rtnMap", rtnMap);
						break;
				

						//이동할 URL
						//rtnMap.put("goURL", "/mypage/classStatus");
						//modelAndView.addObject("rtnMap", rtnMap);
					} else if(ResultMsgDe.equals("결제오류")) {
						//결제오류일 경우 취소처리
						String COMCD = (String) rtnMap.get("COMCD");
						String TID 	= (String) rtnMap.get("TID");
						String MEM_NO = (String) rtnMap.get("MEM_NO");
						String Amt 	= (String) rtnMap.get("Amt");
						
						//rtnMap.put("SLIP_NO", NULL);
						rtnMap.put("PAY_AMT", Amt);
						Map<String, Object> maps = new HashMap<>();
						maps = service.payKeyInfoCancel(rtnMap);
						rtnMap.put("goURL", "close2");
						modelAndView.addObject("rtnMap", rtnMap);
						break;
						
					} else {
						//창닫을경우 이동할 URL
						rtnMap.put("goURL", "/mypage/classStatus");
						modelAndView.addObject("rtnMap", rtnMap);
					}
					break;

					//대관 (축구장/야구장,테니스장) 결제
				case "rentPay":
					
					//결과
					rtnMap = service.rentOrderInsert(request);
					//창닫을경우 이동할 URL
					rtnMap.put("goURL", "/mypage/rent");
					modelAndView.addObject("rtnMap", rtnMap);
					break;
					
				case "vbankPay":
					//결과
					rtnMap = service.vbankPayOrderInsert(request);
					//창닫을경우 이동할 URL
					rtnMap.put("goURL", "/mypage/rent");
					modelAndView.addObject("rtnMap", rtnMap);
					break;
					//사물함 결제
				case "lockerPay":
					//결과
					rtnMap = service.lockerOrderInsert(request);					
					//창닫을경우 이동할 URL
					rtnMap.put("goURL", "/mypage/lockerStatus");					
					modelAndView.addObject("rtnMap", rtnMap);
					break;

					//결제취소
				case "mainCancelPay":
					rtn = "/smartPay/"+ dirPath;
					break;
					
				//정원초과 취소처리
				case "payCancel":
						rtnMap = service.classCancelPay(request);
						rtnMap.put("rtn_url", "/lecture/list"); //취소완료후 처음되돌아가는 부분
						modelAndView.addAllObjects(rtnMap);
						rtn = "/smartPay/CancelPayAct";
						break;
				//강좌 결제 취소	
				case "classCancelPayAct":
					rtnMap = service.classCancelPay(request);
					rtnMap.put("rtn_url", "/mypage/classStatus"); //취소완료후 처음되돌아가는 부분
					modelAndView.addAllObjects(rtnMap);
					rtn = "/smartPay/CancelPayAct";
					break;

				///대관 결제 취소
				case "CancelPayAct":
					String userParam	= request.getParameter("q")==null? "//" : request.getParameter("q"); // 사용자 파람
					String [] arrayTmp 	= userParam.split("\\/"); //20201225/8
					//String COMCD		= arrayTmp[0]; //장소
					//String MEM_NO		= arrayTmp[1]; //회원번호
					
					//String CancelAmt = request.getParameter("CancelAmt");
					//String slipOn = request.getParameter("slipOn");
					//String TID = request.getParameter("TID");
					//String COMCD = request.getParameter("COMCD");
					
					
					Map<String, Object> maps = new HashMap<>();
					maps.put("COMCD", request.getParameter("COMCD"));
					maps.put("SLIP_NO", request.getParameter("slipOn"));
					maps.put("TID", request.getParameter("TID"));
					maps.put("PAY_AMT", request.getParameter("CancelAmt"));
					maps.put("SUB_MENU", request.getParameter("subMenu"));
					Member	members = (Member) session.getAttribute("member");
					String MEM_ID = members.getId();
			    	String MEM_NO = members.getMemNo();
			    	String MEM_NM = members.getMemNm();
					maps.put("MEM_ID", MEM_ID);
					maps.put("MEM_NO", MEM_NO);
					maps.put("MEM_NM", MEM_NM);
					
					System.out.println(maps);
					
					rtnMap = service.payKeyInfoCancel(maps);
					rtnMap.put("rtn_url", "/mypage/rent"); //취소완료후 처음되돌아가는 부분
					modelAndView.addAllObjects(rtnMap);
					rtn = "/smartPay/"+ dirPath;
					break;				
			
			}
			/*
			if (dirPath.equals("mainPay")) {
				//modelAndView.addObject("VoPay",VoPay);
			}
			else if (dirPath.equals("returnPay")) {
				

			}
			else if (dirPath.equals("returnPayxxx")) {
				service.testInsert(dirPath, request);
			}
			//final Menu menu = service.boardPage(dirPath);
			*/
		 	
//		} catch (Exception ex) {
//			modelAndView = errorService.error("404");
//		}
	
		modelAndView.setViewName(rtn);
		return modelAndView;
	}


	@RequestMapping(value = "/2/{dirPath}")
	public ModelAndView cancelPay(ModelAndView modelAndView, @PathVariable String dirPath, HttpServletRequest request, HttpServletResponse response)
	{
		String rtn = "";
		int dirPathLen = dirPath.length();
		//try {
			dirPath = (dirPathLen >= 20) ? dirPath.substring(0,20) : dirPath.substring(0,dirPathLen);
			dirPath = dirPath.replace(" ", "");
			//dirPath = URLEncoder.encode(dirPath,"utf-8");
			rtn = "/smartPay/2/"+dirPath;
			

			
		 	modelAndView.setViewName(rtn);
//		} catch (Exception ex) {
//			modelAndView = errorService.error("404");
//		}
	
		
		return modelAndView;
	}
	

}
