package com.dwict.jfmc.client.com.mapper;

import com.dwict.jfmc.client.com.model.AtchFile;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("commonMapper")
public interface CommonMapper {

	AtchFile atchFile(AtchFile param);

}
