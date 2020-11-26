package com.dwict.jfmc.client.mypage.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.MemberService;
import com.dwict.jfmc.client.mypage.service.MypageService;
import com.dwict.jfmc.client.page.model.Menu;
import com.dwict.jfmc.client.page.service.PageService;

@RestController
public class MypageController {

	@Resource(name = "memberService")
	private MemberService memberService;

	@Resource(name = "mypageService")
	private MypageService service;

	@Resource(name = "pageService")
	private PageService pageService;

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
		final HttpSession session = request.getSession(false);
		modelAndView.addObject("member",session.getAttribute("member"));
		modelAndView.setViewName("mypage/lockerStatus");
		return modelAndView;
	}

	//내 대관 리스트
	@GetMapping(value = "/mypage/rent")
	public ModelAndView rentList(ModelAndView modelAndView, HttpServletRequest request) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final String userId = auth.getName();

		final List<Map <String,Object>> rentList = service.getMyRentList(userId);
		modelAndView.addObject("rentList", rentList);

		//memberService.memSession(request, userId);		
		modelAndView.setViewName("mypage/rent");
		return modelAndView;
	}

	@GetMapping(value = "/mypage/myBoard")
	public ModelAndView myBoard(ModelAndView modelAndView, HttpServletRequest request) {		
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
}
