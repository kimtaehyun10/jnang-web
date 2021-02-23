/**
 * 
 */
/**
 * @author powerjyc
 *
 */
package com.dwict.jfmc.client.smpay.mapper;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("payMapper")
public interface PayMapper {

	List<Map<String, Object>> VoPay(Map<String, Object> requestMap);

	//장바구니
	Map<String, Object> mem_cart(String mem_no);
	
	void isExistingMember(Map<String, Object> requestMap);

	void testSlect(HttpServletRequest request);

	void testSlectx(HttpServletRequest request);
	
	String getNextSlipNo();
	
	String getNextReceiptNo();

	int setMemSale(Map requestMap);

	void setTrainHist(Map<String, Object> requestMapTrainHist);
	
//	int setPayList(Map requestMap);
//
//	int setPayList2(Map requestMap);
	
	//주문_결제정보(CALC_MASTER) 저장
	int setCalcMaster(Map requestMap);

	//주문_현금결제정보 등록 저장
	int setPayChangeInfo(Map requestMap);

	//주문 내역 저장1
	void setPayList(Map<String, Object> requestMapPayList);
	
	void setPayList2(Map<String, Object> requestMapPayList);
	
	//void updatePayList(Map<String, Object> requestMapPayList);
	
	//void setPayRentList(Map<String, Object> maps);

	int rentDblChk(Map<String, Object> maps);
	
	int rentOrderSEQ(Map<String, Object> maps);
	
	int rentOrderSave(Map<String, Object> maps);

	void lockerOrderSave(Map<String, Object> maps);

	Map<String, Object> payKeyInfo(Map<String, Object> maps);

	void updateCancelRentApp(Map<String, Object> maps);

	String getNextAppNo(Map<String, Object> maps);

	void rentSuceess(String tID);

	void rentUpdateTid(Map<String, Object> maps);



	
}
