package com.dwict.jfmc.client.page.mapper;

import java.util.List;
import java.util.Map;

import com.dwict.jfmc.client.page.model.Cont;
import com.dwict.jfmc.client.page.model.Menu;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("pageMapper")
public interface PageMapper {

	Cont htmlPage(String cmsCd);
	List<Cont> htmlPageChilds(String cmsCd);

	Cont empPage(String cmsCd);
	List<Cont> empPageChilds(String cmsCd);

	Menu boardPage(String cmsCd);
}
