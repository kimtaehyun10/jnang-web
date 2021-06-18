package com.dwict.jfmc.client.mem.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.mem.model.Member;
import com.dwict.jfmc.client.mem.service.IpinService;
import com.dwict.jfmc.client.mypage.mapper.MypageMapper;
import com.dwict.jfmc.client.security.service.ErrorService;

@RestController
public class MemberController {

	@Resource(name = "ipinService")
	private IpinService ipin;

	@Resource(name = "errorService")
	private ErrorService errorService;

	@Resource(name="mypageMapper")
	private MypageMapper mypageMapper;
	
	@GetMapping(value = "/mem/login")
	public ModelAndView login(HttpServletRequest request, ModelAndView modelAndView) {
		modelAndView.addObject("status", request.getSession().getAttribute("status"));
		modelAndView.addObject("errorMsg", request.getSession().getAttribute("errorMsg"));
		request.getSession().setAttribute("status", null);
		request.getSession().setAttribute("errorMsg", null);
		modelAndView.setViewName("mem/login");
		return modelAndView;
	}

	@GetMapping(value = "/mem/logout")
	public ModelAndView logout(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {

			try {
				Map<String, Object> maps = new HashMap<>();
				maps.put("allClear", "ok");
				maps.put("MEM_ID", auth.getName());
				//장바구니 오래된 주문 비우기 #######################
				mypageMapper.basketClear(maps);
			} catch ( Exception ex) {
				
			}
			
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		modelAndView.setViewName("redirect:/");
		return modelAndView;
	}

	@GetMapping(value = "/mem/join{no}")
	public ModelAndView join(ModelAndView modelAndView, @PathVariable int no, 
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		final String before_address = request.getHeader("referer");
		switch (no) {
		case 1:
			modelAndView.setViewName("mem/join" + no);
			break;
		case 2:
			if (before_address != null && before_address.contains("/mem/join" + String.valueOf(no-1))) {
				modelAndView.setViewName("mem/join" + no);
			} else {
				modelAndView = errorService.error("403");
			}
			break;
		case 3:
			if (before_address != null && before_address.contains("/mem/join" + String.valueOf(no-1))) {
				final HttpSession session = request.getSession(false);
				if(session != null && session.getAttribute("join.adult") != null) {
					modelAndView.addObject("adult", session.getAttribute("join.adult"));
					modelAndView.setViewName("mem/join" + no);
				} else {
					modelAndView = errorService.error("403");
				}
			} else {
				modelAndView = errorService.error("403");
			}
			break;
		case 4:
			if (before_address != null && before_address.contains("/mem/join" + String.valueOf(no-1))) {
				final HttpSession session = request.getSession(false);
				if(session != null && session.getAttribute("join.adult") != null 
						&& session.getAttribute("join.exist") != null && session.getAttribute("join.member") != null) {
					modelAndView.addObject("adult", session.getAttribute("join.adult"));
					modelAndView.addObject("exist", session.getAttribute("join.exist"));
					modelAndView.addObject("member", session.getAttribute("join.member"));
					modelAndView.setViewName("mem/join" + no);
				} else {
					modelAndView = errorService.error("403");
				}
			} else {
				modelAndView = errorService.error("403");
			}
			break;
		case 5:
			if (before_address != null && before_address.contains("/mem/join" + String.valueOf(no-1))) {
				final HttpSession session = request.getSession(false);
				if(session != null && session.getAttribute("join.adult") != null) {
					modelAndView.addObject("adult", session.getAttribute("join.adult"));
					modelAndView.setViewName("mem/join" + no);
				} else {
					modelAndView = errorService.error("403");
				}
			} else {
				modelAndView = errorService.error("403");
			}
			break;
		case 6:
			if (before_address != null && before_address.contains("/mem/join3")) {
				final HttpSession session = request.getSession(false);
				if(session != null && session.getAttribute("join.adult") != null 
						&& session.getAttribute("join.exist") != null && session.getAttribute("join.member") != null) {
					modelAndView.addObject("adult", session.getAttribute("join.adult"));
					modelAndView.addObject("exist", session.getAttribute("join.exist"));
					modelAndView.addObject("member", session.getAttribute("join.member"));
					modelAndView.setViewName("mem/join" + no);
				} else {
					modelAndView = errorService.error("403");
				}
			} else {
				modelAndView = errorService.error("403");
			}
			break;
		default:
			modelAndView = errorService.error("403");
			break;
		}
		return modelAndView;
	}

	@GetMapping(value = "/mem/join2/{ageType}")
	public ModelAndView join(HttpServletRequest request, HttpServletResponse response,
			ModelAndView modelAndView, @PathVariable String ageType) {
		final HttpSession session = request.getSession(true);
		switch (ageType) {
		case "more":
			session.setAttribute("join.adult", true);
			break;
		case "less":
			session.setAttribute("join.adult", false);
			break;
		default:
			modelAndView = errorService.error("404");
			break;
		}
		modelAndView.setViewName("mem/join2");
		return modelAndView;
	}

	@GetMapping(value = "/mem/find/id")
	public ModelAndView findId(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
		final HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("find.exist") != null) {
			modelAndView.addObject("exist", session.getAttribute("find.exist"));
			modelAndView.setViewName("mem/findId");
		} else {
			modelAndView = errorService.error("403");
		}
		return modelAndView;
	}

	@GetMapping(value = "/mem/find/password")
	public ModelAndView findPassword(ModelAndView modelAndView) {
		modelAndView.setViewName("mem/findPwd");
		return modelAndView;
	}

	@GetMapping(value = "/mem/find/password/complete")
	public ModelAndView findPasswordComplete(ModelAndView modelAndView) {
		modelAndView.setViewName("mem/findComplete");
		return modelAndView;
	}

	@GetMapping(value = "/mem/ipin/process")
	public ModelAndView ipinProcess(ModelAndView modelAndView, @RequestParam String enc_data) {
		modelAndView.addObject("enc_data", enc_data);
		modelAndView.setViewName("mem/ipinProcess.none");
		return modelAndView;
	}

	@GetMapping(value = "/mem/ipinResult")
	public ModelAndView ipinResult(HttpServletRequest request, ModelAndView modelAndView) {
		return ipin.result(request, modelAndView);
	}

	@GetMapping(value = "/mem/isNotMatched")
	public ModelAndView memberInfoIsNotMatched(HttpServletRequest request, ModelAndView modelAndView) {
		final HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("find.exist") != null) {
			modelAndView.addObject("exist", session.getAttribute("find.exist"));
			modelAndView.setViewName("mem/isNotMatched");
		} else {
			modelAndView = errorService.error("403");
		}
		return modelAndView;
	}

}
