package com.dwict.jfmc.client.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.com.model.Paging;
import com.dwict.jfmc.client.mem.service.LogintalkService;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.mypage.service.MypageService;

@RestController
@RequestMapping("/data")
public class MypageRestController {

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "mypageService")
	private MypageService service;

	@Resource(name = "logintalkService")
	private LogintalkService logintalk;

	//과목 => 장바구니 담기
	@GetMapping(value = "/mypage/basketList")
	@ResponseBody
	public List<Map<String, Object>> basketList(@RequestParam Map<String, Object> requestMap, HttpServletRequest request) {
		return service.basketList(requestMap, request);
	}

	//수강신청 당일 취소처리
	@GetMapping(value = "/mypage/classCancelPay")
	public int classCancelPay(@RequestParam Map<String, Object> requestMap, HttpServletRequest request) {

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();
		//세션저장
		memberService.memSession(request, userId);	

		//final HttpSession session = request.getSession(false);
		//Member members = (Member) session.getAttribute("member");
		//System.out.println("세션=========================>"+ members.getId());		
		return service.classCancelPay(requestMap, request);
	}	


	@GetMapping(value = "/mypage/logintalk/token")
	public ModelAndView getUserInfoByLogintalkTokenForLogin(HttpServletRequest request, ModelAndView modelAndView, @RequestParam String token) {
		final Object response = logintalk.sendToken(token);
		modelAndView = logintalk.verifyForChangePwd(request, response);
		return modelAndView;
	}

	@GetMapping(value = "/mypage/myBoard/{userId}")
	public Paging myBoard(HttpServletRequest request, @PathVariable String userId) {
		final Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("pageIndex", request.getParameter("pageIndex"));
		param.put("pageSize", request.getParameter("pageSize"));
		param.put("startRow", request.getParameter("startRow"));
		param.put("searchKey", request.getParameter("searchKey"));
		param.put("searchValue", request.getParameter("searchValue"));		
		return service.myBoard(param);
	}

	@PutMapping(value = "/mypage/modify/{userId}")
	public Map<String, Object> modifyUpdate(HttpServletRequest request, @PathVariable String userId) {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		param.put("userId", userId);
		param.put("birthSec", request.getParameter("birthSec"));
		param.put("email", request.getParameter("email"));
		param.put("emailYn", request.getParameter("emailYn"));		
		param.put("gender", request.getParameter("gender"));
		param.put("homeAddr", request.getParameter("homeAddr"));
		param.put("homeTel", request.getParameter("homeTel"));
		param.put("homeZip", request.getParameter("homeZip"));
		param.put("secBirthDate", request.getParameter("secBirthDate"));
		param.put("smsYn", request.getParameter("smsYn"));
		param.put("etcNo", request.getParameter("etcNo"));
		try {
			service.modifyUpdate(param);
			resultMap.put("msg", "수정완료");
		} catch (final Exception e) {
			resultMap.put("msg", "수정실패");
		}		
		return resultMap;
	}

	@DeleteMapping(value = "/mypage/modify/{userId}")
	public Map<String, Object> modifyDelete(HttpServletRequest request, @PathVariable String userId) {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		param.put("userId", userId);		
		try {
			service.modifyDelete(param);
			resultMap.put("msg", "탈퇴완료-그동안 이용해주셔서 감사합니다.");
		} catch (final Exception e) {
			resultMap.put("msg", "탈퇴실패");
		}		
		return resultMap;
	}

	@GetMapping(value = "/mypage/lockerStatus/{memNo}")
	public List<Map<String, Object>> lockerStatusList(HttpServletRequest request, @PathVariable String memNo) {
		final Map<String, Object> param = new HashMap<>();
		param.put("memNo", memNo);				
		return service.lockerStatusList(param);
	}
	
	//myPage 대관 선택  취소
	@GetMapping(value = "/mypage/rentSelectCancel")
	public int rentSelectCancel(HttpServletRequest request) {
		return service.rentSelectCancel(request);
	}
	
	@PostMapping(value = "/mypage/lockerStatus/{memNo}")
	public Map<String, Object> reLocker(HttpServletRequest request, @PathVariable String memNo) {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();		
		param.put("MEM_NO", memNo);
		param.put("COMCD", request.getParameter("COMCD"));
		param.put("RENT_NO", request.getParameter("RENT_NO"));				
		//Map<String, Object> reLocker = service.reLocker(param); // 결제후 사물함 정보 저장
		resultMap.put("result", "개발중");
		return resultMap;
	}

	@GetMapping(value = "/mypage/lockerPayDetail")
	public Map<String, Object> lockerPayDetail(HttpServletRequest request) {
		final Map<String, Object> param = new HashMap<>();
		param.put("RENT_AMT", request.getParameter("RENT_AMT"));
		param.put("COMNM", request.getParameter("COMNM"));
		param.put("PLACE_CD", request.getParameter("PLACE_CD"));
		param.put("LOCKER_CD", request.getParameter("LOCKER_CD"));
		return service.lockerPayDetail(request,param);
	}
}
