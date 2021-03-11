
package com.dwict.jfmc.client.imre.com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.dwict.jfmc.client.imre.com.model.DiscountItemVO;

public interface imReService {
	
	//할인항목 조회
	List<HashMap<String,Object>> selectDisItemList(Map requestMap);
	
	//즉시감면 서비스 메인 서비스
	Map<String, String> mainService(Map requestMap, DiscountItemVO discountItemVO);
	
	//국가유공상이자[DCITMKEY_00000000003]
	Map<String, String> getNationalMerit1(Map requestMap, String targetid);

	//국가유공자[DCITMKEY_00000000003]
	Map<String, String> getNationalMerit1Car(Map requestMap, String targetid);

	//518민주유공자[DCITMKEY_00000000018]
	Map<String, String> getNationalMerit2(Map requestMap, String targetid);

	//고엽제자동차[DCITMKEY_00000000007]
	Map<String, String> getNationalMerit3(Map requestMap, String targetid);

	//[거주자 ]DCITMKEY_00000000017   
	Map<String, String> getResident(Map requestMap, String targetid);

	//[장애인]DCITMKEY_00000000002       
	Map<String, String> getHandicap(Map requestMap, String targetid);	

	//[다둥이(세자녀이상)]DCITMKEY_00000000005    
	Map<String, String> getCountChildren1(Map requestMap, String targetid);

	//[다둥이(두자녀)]DCITMKEY_00000000006   
	Map<String, String> getCountChildren2(Map requestMap, String targetid);

	//[모범납세자]DCITMKEY_00000000008        
	Map<String, String> getGreateTax(Map requestMap, String targetid);

	//[병역명문가]DCITMKEY_00000000009        
	Map<String, String> getGreateArmy(Map requestMap, String targetid);

	//[저공해 ]DCITMKEY_00000000004         
	Map<String, String> getLowCar(Map requestMap, String targetid);

	//[경차]DCITMKEY_00000000001         
	Map<String, String> reductionCar(Map requestMap, String targetid);
	
	//[기초수급자]DCITMKEY_00000000021         
	Map<String, String> getBasePerson(Map requestMap, String targetid);
	
	
}
