package com.dwict.jfmc.client.interceptor;

import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dwict.jfmc.client.main.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonInterceptor extends HandlerInterceptorAdapter {

	@Resource(name = "mainService")
	private MainService mainService;

	//Controller가 수행되기 전 호출 (이후 Controller를 수행할지 여부를 boolean으로 return 함)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("============================== START ===============================");
		log.info(" Class       \t:  " + handler.getClass());
		log.info(" Request URI \t:  " + request.getRequestURI());
		log.info(" Servlet URI \t:  " + request.getServletPath());

		final Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {
			final String key = paramNames.nextElement();  
			final String value = request.getParameter(key);
			log.info("# RequestParameter: " + key + "=" + value + "");
		}
		log.info("====================================================================");

		return super.preHandle(request, response, handler);
	}

	//Controller가 수행된 후 View를 호출하기 전 호출
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("========================== ADD MENU LIST ===========================");
		final HttpSession session = request.getSession(true);
		session.setAttribute("menuList", mainService.getMenuList());

		log.info("========================== ADD DOC TITLE ===========================");
		final String cmsCd = getCmsCdFromURI(request.getRequestURI());
		final String cmsNm = getCmsNmFromCmsCd(request.getRequestURI(), cmsCd);
		session.setAttribute("cmsNm", cmsNm);
	}

	//view 처리 이후 이벤트 작동
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		log.info("=============================== END ================================");
	}

	private String getCmsCdFromURI(String URI) {
		String result = null;
		final Pattern pattern = Pattern.compile("\\d{8}");
		final Matcher matcher = pattern.matcher(URI);
		while (matcher.find()) {
			result = matcher.group();
			if(matcher.group() ==  null)
				break;
		}
		return result;
	}

	private String getCmsNmFromCmsCd(String URI, String cmsCd) {
		String result = null;
		if(cmsCd != null) {
			result = mainService.getCmsNm(cmsCd);
		} else {
			if(URI.contains("/lecture/")) {
				result = "수강신청";
			} else if(URI.contains("/rent/") || URI.contains("/calendar/")) {
				result = "대관신청";
			} else if(URI.contains("/locker/")) {
				result = "사물함신청";
			} else if(URI.contains("/etc/")) {
				if(URI.contains("/sitemap")) {
					result = "사이트맵";
				} else if(URI.contains("/guide1")) {
					result = "개인정보처리방침";
				} else if(URI.contains("/guide2")) {
					result = "이메일무단수집거부";
				} else if(URI.contains("/guide3")) {
					result = "이용안내";
				} else if(URI.contains("/guide4")) {
					result = "뷰어다운로드";
				}
			} else if(URI.contains("/price/")) {
				result = "전자가격협상";
			} else {
				result = "기타 페이지";
			}
		}
		return result;
	}
}
