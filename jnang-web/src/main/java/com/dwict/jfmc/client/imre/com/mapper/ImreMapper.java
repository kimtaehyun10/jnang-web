/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
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
package com.dwict.jfmc.client.imre.com.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * The interface base manage mapper.
 */
@Mapper("imreMapper")
public interface ImreMapper {
	
    /**
     * 공통설정분류 코드를 조회한다.
     *
     * @param vo
     * @return
     * @throws Exception
     */
     List<HashMap<String,Object>> selectDisItemList(Map requestMap);
	 
}
