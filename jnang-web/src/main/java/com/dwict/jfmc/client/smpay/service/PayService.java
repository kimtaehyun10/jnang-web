/**
 * 
 */
/**
 * @author powerjyc
 *
 */
package com.dwict.jfmc.client.smpay.service;

//package com.dwict.jfmc.client.smpay.model.PayService;
//package com.dwict.jfmc.client.smpay.model.;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

public interface PayService {

	//List<Map<String, Object>> VoPay(Map<String, Object> requestMap);

	//test Insert
	int testInsert(String dirPath, HttpServletRequest request);

	//기본테스트 결제 
	Map<String, Object> lecOrderInsert(HttpServletRequest request);

	Map<String, Object> rentOrderInsert(HttpServletRequest request);

	Map<String, Object> lockerOrderInsert(HttpServletRequest request);

	//취소처리 키값 가져오기
	Map<String, Object> payKeyInfo(Map<String, Object> maps);

}
