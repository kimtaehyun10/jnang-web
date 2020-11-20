package com.dwict.jfmc.client.declaration.service;

import java.util.Map;

import com.dwict.jfmc.client.declaration.model.DecComment;
import com.dwict.jfmc.client.declaration.model.Declaration;

public interface DeclarationService {

	void declarationWrite(Map<String, Object> param) throws Exception;

	Declaration declarationDetailPage(Map<String, Object> param);

	DecComment decCommentDetailPage(Map<String, Object> param);

}
