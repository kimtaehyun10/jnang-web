package com.dwict.jfmc.client.security.mapper;

import com.dwict.jfmc.client.security.model.Account;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("accountMapper")
public interface AccountMapper {

	Account findById(String userId);

	void userVisitDt(String userId);

}
