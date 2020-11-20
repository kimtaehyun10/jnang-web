package com.dwict.jfmc.client.smpay.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
		) throws Exception {
		String rtn = "";
		final HttpSession session = request.getSession(false);
		int dirPathLen = dirPath.length();
		//try {
			dirPath = (dirPathLen >= 20) ? dirPath.substring(0,20) : dirPath.substring(0,dirPathLen);
			dirPath = dirPath.replace(" ", "");
			dirPath = URLEncoder.encode(dirPath,"utf-8");
			rtn = "/smartPay/"+dirPath;
			

			if (dirPath.equals("mainPay")) {
				//modelAndView.addObject("VoPay",VoPay);
			}
			else if (dirPath.equals("returnPay")) {
				//결과
				Map<String, Object> rtnMap = service.lecOrderInsert(request);
				
				rtnMap.put("test1", "11111");
				rtnMap.put("test2", "11112");
				rtnMap.put("test3", "11113");
				modelAndView.addObject("rtnMap", rtnMap);
				modelAndView.setViewName(rtn+".none");
			}
			else if (dirPath.equals("returnPayxxx")) {
				service.testInsert(dirPath, request);
			}
			//final Menu menu = service.boardPage(dirPath);
			
		 	
//		} catch (Exception ex) {
//			modelAndView = errorService.error("404");
//		}
	
		
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
