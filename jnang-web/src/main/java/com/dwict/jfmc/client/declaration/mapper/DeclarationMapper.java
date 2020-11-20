package com.dwict.jfmc.client.declaration.mapper;

import java.util.Map;

import com.dwict.jfmc.client.declaration.model.DecComment;
import com.dwict.jfmc.client.declaration.model.Declaration;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("declarationMapper")
public interface DeclarationMapper {

	void declarationWrite(Map<String, Object> param);

	Declaration declarationDetailPage(Map<String, Object> param);

	DecComment decCommentDetailPage(Map<String, Object> param);

}
