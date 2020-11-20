package com.dwict.jfmc.client.declaration.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dwict.jfmc.client.board.mapper.BoardMapper;
import com.dwict.jfmc.client.declaration.mapper.DeclarationMapper;
import com.dwict.jfmc.client.declaration.model.DecComment;
import com.dwict.jfmc.client.declaration.model.Declaration;
import com.dwict.jfmc.client.declaration.service.DeclarationService;

@Service("declarationService")
public class DeclarationServiceImpl implements DeclarationService {

	@Value("#{appConfig['Globals.fileUploadStorePath']}")
	private String storePath;
	
	@Resource(name = "declarationMapper")
	private DeclarationMapper declarationMapper;
	
	@Resource(name = "boardMapper")
	private BoardMapper boardMapper;
	
	@Override
	public void declarationWrite(Map<String, Object> param) throws Exception{
		@SuppressWarnings("unchecked")
		final List<MultipartFile> fileList = (List<MultipartFile>) param.get("fileList");
		int index = 1;
		param.put("dCode", UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12));
		declarationMapper.declarationWrite(param);
		if (!fileList.isEmpty()) {
			final File saveFolder = new File(storePath);
			if (!saveFolder.exists() || saveFolder.isFile()) {
				saveFolder.mkdirs();
			}								
			for(final MultipartFile mf : fileList) {
				final String originFileName = mf.getOriginalFilename();
				final String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());				
				final String reFileName = "DEC" + "_" + UUID.randomUUID().toString().replaceAll("-", "") + extName;
				final String saveFile = storePath + reFileName;
				mf.transferTo(new File(saveFile));
				param.put("attachNo", index);
				param.put("attachId", param.get("dFile"));
				param.put("fileNm", originFileName);
				param.put("virFileNm", reFileName);
				param.put("fileExtsn", extName.replace(".", ""));
				boardMapper.saveAttach(param);
				index++;
			}
		}		
	}

	@Override
	public Declaration declarationDetailPage(Map<String, Object> param) {
		return declarationMapper.declarationDetailPage(param);
	}

	@Override
	public DecComment decCommentDetailPage(Map<String, Object> param) {
		return declarationMapper.decCommentDetailPage(param);
	}

}
