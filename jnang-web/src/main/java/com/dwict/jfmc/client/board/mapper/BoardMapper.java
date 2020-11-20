package com.dwict.jfmc.client.board.mapper;

import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.board.model.Attach;
import com.dwict.jfmc.client.board.model.Brd;
import com.dwict.jfmc.client.board.model.Ntc;
import com.dwict.jfmc.client.board.model.Price;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("boardMapper")
public interface BoardMapper {

	List<Map<String, ?>> faqPage(Map<String, Object> param);
	int faqPageCnt(Map<String, Object> param);
	List<Map<String, ?>> ntcPage(Map<String, Object> param);
	int ntcPageCnt(Map<String, Object> param);
	Ntc ntcDetailPage(Map<String, Object> param);
	int boardHit(Map<String, Object> param);
	List<Attach> ntcAttachView(Map<String, Object> param);
	List<Map<String, ?>> brdPage(Map<String, Object> param);
	int brdPageCnt(Map<String, Object> param);
	Brd brdDetailPage(Map<String, Object> param);
	void brdWrite(Map<String, Object> param);
	void saveAttach(Map<String, Object> param);
	void brdUpdate(Map<String, Object> param);
	void deleteAttach(Map<String, Object> param);
	void boardDelete(Map<String, Object> param);
	List<Map<String, Object>> searchKeyword(Map<String, Object> param);
	List<Map<String, ?>> pricePage(Map<String, Object> param);
	int pricePageCnt(Map<String, Object> param);
	Price priceDetailPage(Map<String, Object> param);
	void priceUpdate(Map<String, Object> param);
}
