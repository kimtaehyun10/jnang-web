package com.dwict.jfmc.client.page.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.board.model.Price;
import com.dwict.jfmc.client.board.service.BoardService;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.page.model.Menu;
import com.dwict.jfmc.client.page.service.PageService;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;

@RestController
public class PageController {

	@Resource(name = "pageService")
	private PageService service;

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "boardService")
	private BoardService boardService;
	
	@Resource(name = "rentService")
	RentService rentService;
	
	@GetMapping(value = "/html/{cmsCd}")
	public ModelAndView htmlPage(ModelAndView modelAndView, @PathVariable String cmsCd) {
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("page/html");
		return modelAndView;
	}

	@GetMapping(value = "/emp/{cmsCd}")
	public ModelAndView empPage(ModelAndView modelAndView, @PathVariable String cmsCd) {
		final Menu menu = service.boardPage(cmsCd);
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.addObject("mType", menu.getMType());
		modelAndView.setViewName("page/emp");
		return modelAndView;
	}

	@GetMapping(value = "/board/{cmsCd}")
	public ModelAndView boardPage(ModelAndView modelAndView, @PathVariable String cmsCd) {
		final Menu menu = service.boardPage(cmsCd);
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final Object principal = auth.getPrincipal();
		String userId="";
		if(principal.equals("anonymousUser")) {
			userId = null;
		}else {
			userId = auth.getName();
		}	
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.addObject("cmsNm", menu.getCmsNm());
		modelAndView.addObject("mType", menu.getMType());		
		switch (menu.getMType()) {
		case "FAQ":
			modelAndView.setViewName("board/faqList");
			break;
		case "NTC":
			modelAndView.setViewName("board/ntcList");
			break;
		case "BRD":
			modelAndView.addObject("userId", userId);
			modelAndView.setViewName("board/brdList");
			break;
		default:

			break;
		}
		return modelAndView;
	}

	@GetMapping(value = "/board/{cmsCd}/{brdNo}")
	public ModelAndView ntcDetailPage(ModelAndView modelAndView, @PathVariable String cmsCd, @PathVariable int brdNo) {		
		final Menu menu = service.boardPage(cmsCd);
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final Object principal = auth.getPrincipal();
		String userId="";
		if(principal.equals("anonymousUser")) {
			userId = null;
		}else {
			userId = auth.getName();
		}
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.addObject("brdNo", brdNo);
		modelAndView.addObject("cmsNm", menu.getCmsNm());
		modelAndView.addObject("mType", menu.getMType());
		switch (menu.getMType()) {
		case "NTC":
			modelAndView.setViewName("board/ntcDetailPage");
			break;
		case "BRD":
			modelAndView.addObject("userId", userId);
			modelAndView.setViewName("board/brdDetailPage");
			break;
		default:

			break;
		}
		return modelAndView;
	}

	@GetMapping(value = "/board/write/{cmsCd}")
	public ModelAndView boardWritePage(ModelAndView modelAndView, HttpServletRequest request, @PathVariable String cmsCd) {		
		final Menu menu = service.boardPage(cmsCd);
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final Object principal = auth.getPrincipal();			
		if(principal.equals("anonymousUser")) {
			modelAndView.setViewName("mem/login");
		}else {        	
			final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        	        	    		    		
			modelAndView.addObject("cmsCd", cmsCd);
			modelAndView.addObject("cmsNm", menu.getCmsNm());
			modelAndView.addObject("mType", menu.getMType());
			modelAndView.addObject("userId", account.getUsername());
			modelAndView.addObject("userNm", account.getMemNm());
			switch (menu.getMType()) {	
			case "BRD":
				modelAndView.setViewName("board/brdWritePage");
				break;
			default:

				break;
			}
		}				
		return modelAndView;
	}

	@GetMapping(value = "/board/update/{cmsCd}/{brdNo}")
	public ModelAndView boardUpdatePage(ModelAndView modelAndView, @PathVariable String cmsCd, @PathVariable String brdNo) {		
		final Menu menu = service.boardPage(cmsCd);
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final Object principal = auth.getPrincipal();			
		if(principal.equals("anonymousUser")) {
			modelAndView.setViewName("mem/login");
		}else {
			modelAndView.addObject("cmsCd", cmsCd);
			modelAndView.addObject("cmsNm", menu.getCmsNm());
			modelAndView.addObject("mType", menu.getMType());    		
			modelAndView.addObject("brdNo", brdNo);    		
			switch (menu.getMType()) {	
			case "BRD":    			
				modelAndView.setViewName("board/brdUpdatePage");
				break;
			default:

				break;
			}
		}				
		return modelAndView;
	}

	@GetMapping(value = "/search/{keyword}")
	public ModelAndView searchPage(ModelAndView modelAndView, @PathVariable String keyword) {
		modelAndView.addObject("keyword", keyword);
		modelAndView.setViewName("board/searchPage");
		return modelAndView;
	}
	
	@GetMapping(value = "/price/{cmsCd}")
	public ModelAndView pricePage(ModelAndView modelAndView, @PathVariable String cmsCd) {
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("board/contract/pricePage");
		return modelAndView;
	}
	
	@GetMapping(value = "/price/{cmsCd}/{brdNo}")
	public ModelAndView priceDetailPage(ModelAndView modelAndView, @PathVariable String cmsCd, @PathVariable int brdNo) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		Price price = boardService.priceDetailPage(param);
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.addObject("brdNo", brdNo);
		modelAndView.addObject("ceo", price.getCeo());
		modelAndView.addObject("busiNumber", price.getBusiNumber());
		modelAndView.addObject("brdPwd", price.getBrdPwd());
		modelAndView.setViewName("board/contract/priceDetailPage");
		return modelAndView;
	}

	//대관 리스트 출력 (달력)
	@GetMapping(value = "/calendar/")
	public ModelAndView calendar(ModelAndView modelAndView, Map<String, Object> requestMap,HttpServletRequest request) {

		//requestMap.put("cmsCd", cmsCd);
		
		//셋팅 값
		final Map <String,Object> rentCfg = rentService.rentConfig(request);
		if (rentCfg == null) {
			modelAndView.setViewName("/rentlist");
			return modelAndView;
		}
		modelAndView.addObject("rentCfg", rentCfg);
		
		//센터별 대관 그룹 가져오기(대강당 ,소강당, 강의실)
		List<Map <String,Object>> centerGroup = service.getCenterGroup(requestMap, request);
		modelAndView.addObject("centerGroup", centerGroup);
		
		modelAndView.setViewName("page/calendar");
		return modelAndView;
	}
	
	
	//대관신청 리스트 (/rent/ 로그인 페이지 안타도록 여기에 적용)
	@RequestMapping(value = "/rentlist")
	public ModelAndView step1(ModelAndView modelAndView, HttpServletRequest request) {
		
		final Map <String,Object> rentCfg = rentService.rentConfig(request);
		if (rentCfg != null) {
			modelAndView.addObject("rentCfg", rentCfg);			
		} 
	
		modelAndView.setViewName("/rent/rentlist");
		return modelAndView;
	}
	
	
	/*
	//전체맵
	@RequestMapping(value = "/map/parkingMap.c")
	public String parkingMap(
			@RequestParam("openType") String openType
			, @RequestParam("searchManageZone") String searchManageZone
			, @RequestParam("searchParkSectionKey") String searchParkSectionKey
			, @RequestParam("searchParkBlockKey") String searchParkBlockKey
			, @RequestParam("searchParkDivisionKey") String searchParkDivisionKey
			, ModelMap model) throws Exception {

		return "map/parkingMap";
	}

	//하루보기 팝업 창
	@RequestMapping(value = "/map/dailyPopup")
	public String dailyPopup(HttpServletRequest request, ModelMap model) throws Exception {
		//Map<String, Object> list = publicTimeParkingManageService.selectEntryBillList(timeParkReceiveKey, nowFinalDisparity);
		//model.addAttribute("list", list.get("list"));
		return "map/dailyPopup";
	}

	//이미지 팝업 창
	@RequestMapping(value = "/map/imagePopup")
	public String imagePopup(HttpServletRequest request, ModelMap model) throws Exception {
		return "map/imagePopup";
	}

	@RequestMapping(value = "/map/emailPopup")
	public String emailPopup(HttpServletRequest request, ModelMap model) throws Exception {
		return "map/emailPopup";
	}
	 */
}
