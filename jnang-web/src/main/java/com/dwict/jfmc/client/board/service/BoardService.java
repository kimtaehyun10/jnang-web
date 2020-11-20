package com.dwict.jfmc.client.board.service;

import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.board.model.Attach;
import com.dwict.jfmc.client.board.model.Brd;
import com.dwict.jfmc.client.board.model.Ntc;
import com.dwict.jfmc.client.board.model.Price;
import com.dwict.jfmc.client.com.model.Paging;

public interface BoardService {

	Paging faqPage(Map<String, Object> param);

	Paging ntcPage(Map<String, Object> param);

	Ntc ntcDetailPage(Map<String, Object> param);

	int boardHit(Map<String, Object> param);

	List<Attach> ntcAttachView(Map<String, Object> param);	

	Paging brdPage(Map<String, Object> param);

	Brd brdDetailPage(Map<String, Object> param);

	void brdWrite(Map<String, Object> param) throws Exception;

	void brdupdate(Map<String, Object> param) throws Exception;

	void boardDelete(Map<String, Object> param);

	List<Map<String, Object>> searchKeyword(Map<String, Object> param);
	
	Paging pricePage(Map<String, Object> param);

	Price priceDetailPage(Map<String, Object> param);

	void priceUpdate(Map<String, Object> param);
}
