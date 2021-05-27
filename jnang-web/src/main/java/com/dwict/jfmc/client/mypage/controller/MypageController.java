package com.dwict.jfmc.client.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.page.model.Menu;
import com.dwict.jfmc.client.page.service.PageService;
import com.dwict.jfmc.client.rent.service.RentService;
import com.dwict.jfmc.client.security.model.Account;
import com.dwict.jfmc.client.smpay.service.PayService;



@RestController
public class MypageController {

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "rentService")
	RentService rentService;
	
	@Resource(name = "mypageService")
	private MypageService service;

	@Resource(name = "pageService")
	private PageService pageService;

	@Resource(name = "payService")
	private PayService payService;
	
	
/*	@GetMapping(value = "/imre/imReView")
	public ModelAndView saleGo(ModelAndView modelAndView, HttpServletRequest request) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		
		
		memberService.memSession(request, userId);
		final HttpSession session = request.getSession(false);
		modelAndView.addObject("member",session.getAttribute("member"));
		modelAndView.setViewName("mypage/imreView_P");

	

		return modelAndView;
	}*/
    
//    //즉시감면 확인
//    @RequestMapping(value = "/imre/imReCk.json")
//    @ResponseBody
//    public Map<String, String> imReCk(@RequestParam Map<String, Object> requestMap, DiscountItemVO discountItemVO) throws Exception {
//    	
////    	Map<String, String> result = imreService.mainService(requestMap, discountItemVO);
////    	
////    	if (result.size()>0){
////    		result.put("result", "OK");
////    	}
//        return ;
//    }
	
	
	//장바구니
	@GetMapping(value = "/mypage/cart")
	public ModelAndView cart(ModelAndView modelAndView, HttpServletRequest request) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		//세션저장
		memberService.memSession(request, userId);		

		final HttpSession session = request.getSession(false);
		final Member members = (Member) session.getAttribute("member");
		System.out.println("세션=========================>"+ members.getId());
		
		
		Map<String, Object> maps = new HashMap<>();
		//사업장 별로 PG결제코드로 변경 maps = payService.payKeyInfo(request);
		//modelAndView.addAllObjects(maps);
		
		maps.put("MEM_ID", members.getId());
		maps.put("MEM_NO", members.getMemNo());
		modelAndView.addObject("otherData", maps);
		modelAndView.setViewName("mypage/cart");
		return modelAndView;
	}

	@GetMapping(value = "/mypage/modify")
	public ModelAndView modify(ModelAndView modelAndView, HttpServletRequest request) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();		
		memberService.memSession(request, userId);
		final HttpSession session = request.getSession(false);		
		modelAndView.addObject("member", session.getAttribute("member"));
		modelAndView.setViewName("mypage/modify");
		return modelAndView;
	}

	@GetMapping(value = "/mypage/classStatus")
	public ModelAndView classStatus(ModelAndView modelAndView) {

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		//구매 리스트 가져오기
		final List<Map <String,Object>> rentList = service.getMyClssList(userId);
		modelAndView.addObject("rentList", rentList);


		modelAndView.setViewName("mypage/classStatus");
		return modelAndView;
	}

	@GetMapping(value = "/mypage/lockerStatus")
	public ModelAndView lockerStatus(ModelAndView modelAndView, HttpServletRequest request) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		memberService.memSession(request, userId);
		final HttpSession session = request.getSession(false);
		modelAndView.addObject("member",session.getAttribute("member"));
		modelAndView.setViewName("mypage/lockerStatus");

		//결제키
		Map<String, Object> maps = new HashMap<>();
		//maps = payService.payKeyInfo(request);
		//modelAndView.addAllObjects(maps);

		return modelAndView;
	}

	//내 대관 리스트
	@GetMapping(value = "/mypage/rent")
	public ModelAndView rentList(ModelAndView modelAndView, HttpServletRequest request) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		
		final HttpSession session = request.getSession(false);
		final Member members = (Member) session.getAttribute("member");
		
		
		final List<Map <String,Object>> rentList = service.getMyRentList1(userId);
		modelAndView.addObject("rentList", rentList);
 
		//final List<Map <String,Object>> cancelPay = service.getMyClssList(userId);
		//modelAndView.addObject("cancelPay", cancelPay);
		
		Map<String, Object> maps = new HashMap<>();
		maps.put("MEM_ID", userId); 
		maps.put("MEM_NO", members.getMemNo());
		//memberService.memSession(request, userId);		
		modelAndView.addObject("otherData", maps);
		modelAndView.setViewName("mypage/rent");
		
		System.out.println(modelAndView);
		
		return modelAndView;
	}

	@GetMapping(value = "/mypage/myBoard")
	public ModelAndView myBoard(ModelAndView modelAndView, HttpServletRequest request) {
		
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		memberService.memSession(request, userId);		
		final HttpSession session = request.getSession(false);		
		modelAndView.addObject("member", session.getAttribute("member"));
		modelAndView.setViewName("mypage/myBoard");
		return modelAndView;
	}

	@GetMapping(value = "/mypage/myBoard/{cmsCd}/{brdNo}")
	public ModelAndView myBoardDetailPage(ModelAndView modelAndView, @PathVariable String cmsCd, @PathVariable int brdNo) {		
		final Menu menu = pageService.boardPage(cmsCd);
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
		modelAndView.addObject("pType", "MYB");
		modelAndView.addObject("userId", userId);
		modelAndView.setViewName("board/brdDetailPage");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/mypage/orderCancel")
	public ModelAndView orderCancel(ModelAndView modelAndView, HttpServletRequest request, @RequestParam Map<String, Object> requestMap) {
		final Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final String MEM_ID = account.getUsername();
		
		String TID = request.getParameter("TID")==null?"":request.getParameter("TID"); // 거래번호
		final List<Map <String,Object>> rentList = service.forOrderCancel(TID);
		modelAndView.addObject("rentList", rentList);
		
		//회원정보 가져오기
		final Map <String,Object> myData = service.myInfo(MEM_ID);
		modelAndView.addObject("myData", myData);

		modelAndView.setViewName("rent/rentOrderCancel");
		return modelAndView;
	}
	
	@GetMapping(value = "/mypage/rentDetail")
	public ModelAndView rentDetail(ModelAndView modelAndView, HttpServletRequest request) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		
		final HttpSession session = request.getSession(false);
		final Member members = (Member) session.getAttribute("member");
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId",userId);
		map.put("writeDh",request.getParameter("writeDh"));
		map.put("placeCd",request.getParameter("placeCd"));
		String appType = request.getParameter("appType");
		final List<Map <String,Object>> rentList;
		String val1 = "";
		String val2 = "";
		if(appType.equals("5")) {
			rentList = service.getMyRentIdxList(map);
			for(int i=0; i<rentList.size(); i++) {
				val1 = val1 + "," + rentList.get(i).get("PLACE_TIME_SEQ");
				val2 = val2 + ",Y";
				request.setAttribute("val3", rentList.get(0).get("RESERVE_DATE"));
				request.setAttribute("val4", request.getParameter("placeCd"));
				request.setAttribute("val5", rentList.get(0).get("COMCD"));
			}
			request.setAttribute("val1", val1);
			request.setAttribute("val2", val2);
			request.setAttribute("val6", "standBy");
			
			request.setAttribute("PLACE_CD", request.getParameter("placeCd"));
			
			String MEM_NO = members.getMemNo();
			
			
			final Map <String,Object> rentCfg = rentService.rentConfig(request);
			if (rentCfg == null) {
				modelAndView.setViewName("/rent/rentlist");
				return modelAndView;
			}
			modelAndView.addObject("rentCfg", rentCfg);
			
			Map <String, Object> rentOrderList= rentService.rentOrder(MEM_NO, request);
			modelAndView.addObject("dataList", rentOrderList);
			
			List <Map <String, Object>> rentPriceList= rentService.rentPriceList(MEM_NO, request);
			modelAndView.addObject("rentPriceList", rentPriceList);
			
			//결제키
			Map<String, Object> maps = new HashMap<>();
			maps = payService.payKeyInfo(rentCfg);
			modelAndView.addAllObjects(maps);
			
			modelAndView.setViewName("/rent/rent_order");
			return modelAndView;
			
		}else {
			rentList = service.getMyRentList2(map);
			modelAndView.addObject("rentList", rentList);
			Map<String, Object> maps = new HashMap<>();
			maps.put("MEM_ID", userId); 
			maps.put("MEM_NO", members.getMemNo());
			//memberService.memSession(request, userId);		
			modelAndView.addObject("otherData", maps);
			modelAndView.setViewName("mypage/rentDetail");
			
			System.out.println(modelAndView);
			
			return modelAndView;
		}
				
		//final List<Map <String,Object>> cancelPay = service.getMyClssList(userId);
		//modelAndView.addObject("cancelPay", cancelPay);
		
		
	}
	
	
	
}
