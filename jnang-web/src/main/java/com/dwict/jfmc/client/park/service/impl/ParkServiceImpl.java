package com.dwict.jfmc.client.park.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dwict.jfmc.client.board.mapper.BoardMapper;
import com.dwict.jfmc.client.park.mapper.ParkMapper;
import com.dwict.jfmc.client.park.service.ParkService;

@Service("parkService")
public class ParkServiceImpl implements ParkService {

	@Value("#{appConfig['Globals.fileUploadStorePath']}")
	private String storePath;
	
	@Resource(name="parkMapper")
	private ParkMapper parkMapper;
	
	@Resource(name = "boardMapper")
	private BoardMapper boardMapper;
	
	@Override
	public void pubParkApplyWrite(Map<String, Object> param) throws Exception{
		@SuppressWarnings("unchecked")
		final List<MultipartFile> fileList = (List<MultipartFile>) param.get("fileList");
		int index = 1;		
		parkMapper.pubParkApplyWrite(param);
		if (!fileList.isEmpty()) {
			final File saveFolder = new File(storePath);
			if (!saveFolder.exists() || saveFolder.isFile()) {
				saveFolder.mkdirs();
			}								
			for(final MultipartFile mf : fileList) {
				final String originFileName = mf.getOriginalFilename();
				final String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());				
				final String reFileName = "PPK" + "_" + UUID.randomUUID().toString().replaceAll("-", "") + extName;
				final String saveFile = storePath + reFileName;
				mf.transferTo(new File(saveFile));
				param.put("attachNo", index);
				param.put("attachId", param.get("attach_id"));
				param.put("fileNm", originFileName);
				param.put("virFileNm", reFileName);
				param.put("fileExtsn", extName.replace(".", ""));
				boardMapper.saveAttach(param);
				index++;
			}
		}		
	}

	@Override
	public int pubParkCheck(Map<String, Object> requestMap) {

		return parkMapper.pubParkCheck(requestMap);
	}

	@Override
	public List<Map<String, Object>> pubParkComCd(Map<String, Object> requestMap) {

		return parkMapper.pubParkComCd(requestMap);
	}
}
