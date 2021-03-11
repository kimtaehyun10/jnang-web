/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dwict.jfmc.client.imre.com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.imre.com.model.DiscountItemVO;
import com.dwict.jfmc.client.imre.com.service.imReService;

/**
 * The type Member controller.
 */
@RestController
public class imReController {

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "imreService")
    private imReService imreService;    

	//즉시감면 화면 호출
    @RequestMapping(value = "/imre/imReView.p")
    public ModelAndView imReView_P(HttpServletRequest request, @RequestParam Map<String, Object> requestMap, ModelAndView model) throws Exception {    	    			    	
				
		requestMap.put("comCd","JUNGNANG01");//센터ID 즉시감면 할인 목록은 센터별로 똑같으므로 1센터에서 가져온다		
		
		List<HashMap<String,Object>> imreDisList = imreService.selectDisItemList(requestMap);
		
		model.addObject("imreDisList", imreDisList);
		model.addObject("imreName", requestMap.get("imreName"));
		model.addObject("imreBirth", requestMap.get("imreBirth"));
		model.addObject("imreDisCode", requestMap.get("imreDisCode"));
		model.setViewName("imre/imreView.none");
        return model;
    }
    
    //즉시감면 확인
    @RequestMapping(value = "/imre/imReCk.json")
    @ResponseBody
    public Map<String, String> imReCk(@RequestParam Map<String, Object> requestMap, DiscountItemVO discountItemVO) throws Exception {
    	
    	Map<String, String> result = imreService.mainService(requestMap, discountItemVO);
    	
    	if (result.size()>0){
    		result.put("result", "OK");
    	}
        return result;
    }
    
}
