package com.dwict.jfmc.client.page.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.InetAddress;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.board.model.Price;
import com.dwict.jfmc.client.board.service.BoardService;
import com.dwict.jfmc.client.main.service.MainService;
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
	
	@Resource(name = "mainService")
	private MainService mainService;
	
	@GetMapping(value = "/html/{cmsCd}")
	public ModelAndView htmlPage(ModelAndView modelAndView, @PathVariable String cmsCd, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		modelAndView.addObject("cmsCd", cmsCd);
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
		
		modelAndView.setViewName("page/html");
		return modelAndView;
	}

	@GetMapping(value = "/emp/{cmsCd}")
	public ModelAndView empPage(ModelAndView modelAndView, @PathVariable String cmsCd, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		final Menu menu = service.boardPage(cmsCd);
		modelAndView.addObject("cmsCd", cmsCd);
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
		
		modelAndView.addObject("mType", menu.getMType());
		modelAndView.setViewName("page/emp");
		return modelAndView;
	}

	@GetMapping(value = "/board/{cmsCd}")
	public ModelAndView boardPage(ModelAndView modelAndView, @PathVariable String cmsCd, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		final Menu menu = service.boardPage(cmsCd);
		
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
		
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
	public ModelAndView ntcDetailPage(ModelAndView modelAndView, @PathVariable String cmsCd, @PathVariable int brdNo, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
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
	public ModelAndView boardWritePage(ModelAndView modelAndView, HttpServletRequest request, @PathVariable String cmsCd, @RequestParam Map<String, Object> requestMap) {		
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
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
	public ModelAndView boardUpdatePage(ModelAndView modelAndView, @PathVariable String cmsCd, @PathVariable String brdNo, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {		
		final Menu menu = service.boardPage(cmsCd);
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
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
	public ModelAndView pricePage(ModelAndView modelAndView, @PathVariable String cmsCd, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
		modelAndView.addObject("cmsCd", cmsCd);
		modelAndView.setViewName("board/contract/pricePage");
		return modelAndView;
	}
	
	@GetMapping(value = "/price/{cmsCd}/{brdNo}")
	public ModelAndView priceDetailPage(ModelAndView modelAndView, @PathVariable String cmsCd, @PathVariable int brdNo, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		requestMap.put("cmsCd",cmsCd);
		mainService.updateMainCnt(request, requestMap);
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
		
		final List<Map <String,Object>> rentSportsList = rentService.rentSportsRentList(request);
		modelAndView.addObject("rentSportsList",rentSportsList);
		
		
		//센터별 대관 그룹 가져오기(대강당 ,소강당, 강의실)
		List<Map <String,Object>> centerGroup = service.getCenterGroup(requestMap, request);
		modelAndView.addObject("centerGroup", centerGroup);
		
		modelAndView.setViewName("page/calendar");
		return modelAndView;
	}
	
	
	//대관신청 리스트 (/rent/ 로그인 페이지 안타도록 여기에 적용)
	@RequestMapping(value = "/rentlist")
	public ModelAndView step1(ModelAndView modelAndView, HttpServletRequest request) throws IOException {
		
		/*
		 * URL whatismyip = new URL("http://checkip.amazonaws.com"); BufferedReader in =
		 * new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		 * 
		 * String ip = in.readLine(); //you get the IP as a String
		 */
		
		
        String ip = null;

        ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-Real-IP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-RealIP"); 
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        }
		
		modelAndView.addObject("ip", ip);
		
		final Map <String,Object> rentCfg = rentService.rentConfig(request);
		if (rentCfg != null) {
			modelAndView.addObject("rentCfg", rentCfg);			
		} 
	
		modelAndView.setViewName("/rent/rentlist");
		return modelAndView;
	}
	
	
	//특정 안내페이지는 꼭 수동으로 별도 페이지로 해달라고 해서 해준다.
	@GetMapping(value = "/html/rent/{COMCD}")
	public ModelAndView rentPage(ModelAndView modelAndView, @PathVariable String COMCD) {
		modelAndView.addObject("COMCD", COMCD);			
		modelAndView.setViewName("/rent/html");
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
