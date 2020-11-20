package com.dwict.jfmc.client.security.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.security.service.ErrorService;

import lombok.extern.slf4j.Slf4j;

@Service("errorService")
@Slf4j
public class ErrorServiceImpl implements ErrorService {

	@Override
	public ModelAndView error(String error_code) {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		final ModelAndView mav = new ModelAndView("/error.nomenu");
		String msg = null;
		final Map<String, Object> map = new HashMap<>();
		map.put("STATUS_CODE", request.getAttribute("javax.servlet.error.status_code") != null ? request.getAttribute("javax.servlet.error.status_code") : error_code);
		map.put("REQUEST_URI", request.getAttribute("javax.servlet.error.request_uri"));
		map.put("EXCEPTION_TYPE", request.getAttribute("javax.servlet.error.exception_type"));
		map.put("EXCEPTION", request.getAttribute("javax.servlet.error.exception"));
		map.put("SERVLET_NAME", request.getAttribute("javax.servlet.error.servlet_name"));

		try {
			final int status_code = Integer.parseInt(error_code);
			switch (status_code) {
			case 400: msg = "잘못된 요청입니다."; break;
			case 403: msg = "접근이 금지되었습니다."; break;
			case 404: msg = "죄송합니다. 요청하신 페이지를 찾을 수 없습니다."; break;
			case 405: msg = "요청된 메소드가 허용되지 않습니다."; break;
			case 500: msg = "서버에 오류가 발생하였습니다."; break;
			case 503: msg = "서비스를 사용할 수 없습니다."; break;
			default: msg = "알 수 없는 오류가 발생하였습니다."; break;
			}
		} catch(final Exception e) {
			msg = "기타 오류가 발생하였습니다.";
		} finally {
			map.put("MESSAGE", msg);
		}

		if(map.isEmpty() == false ) {
			final Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
			Entry<String, Object> entry = null;
			while(iterator.hasNext()) {
				entry = iterator.next();
				log.info("key : " + entry.getKey() + ", value : " + entry.getValue());
			}
		}

		mav.addObject("error", map);
		return mav;
	}

}
