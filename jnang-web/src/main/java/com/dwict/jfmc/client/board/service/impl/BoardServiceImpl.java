package com.dwict.jfmc.client.board.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dwict.jfmc.client.board.mapper.BoardMapper;
import com.dwict.jfmc.client.board.model.Attach;
import com.dwict.jfmc.client.board.model.Brd;
import com.dwict.jfmc.client.board.model.Ntc;
import com.dwict.jfmc.client.board.model.Price;
import com.dwict.jfmc.client.board.service.BoardService;
import com.dwict.jfmc.client.com.model.Paging;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Value("#{appConfig['Globals.fileUploadStorePath']}")
	private String storePath;

	@Resource(name = "boardMapper")
	private BoardMapper mapper;

	@Override
	public Paging faqPage(Map<String, Object> param) {
		final Paging obj = new Paging();
		obj.handlePaging(Integer.valueOf((String) param.get("pageIndex")), Integer.valueOf((String) param.get("pageSize")));
		param.put("startRow", obj.getStartRow());
		param.put("pageSize", obj.getPageSize());
		obj.handlePagingList(mapper.faqPage(param), mapper.faqPageCnt(param));
		return obj;
	}

	@Override
	public Paging ntcPage(Map<String, Object> param) {
		final Paging obj = new Paging();
		obj.handlePaging(Integer.valueOf((String) param.get("pageIndex")), Integer.valueOf((String) param.get("pageSize")));
		param.put("startRow", obj.getStartRow());
		param.put("pageSize", obj.getPageSize());
		obj.handlePagingList(mapper.ntcPage(param), mapper.ntcPageCnt(param));
		return obj;
	}

	@Override
	public Ntc ntcDetailPage(Map<String, Object> param) {
		return mapper.ntcDetailPage(param);
	}

	@Override
	public int boardHit(Map<String, Object> param) {
		return mapper.boardHit(param);
	}

	@Override
	public List<Attach> ntcAttachView(Map<String, Object> param) {
		return mapper.ntcAttachView(param);
	}

	@Override
	public Paging brdPage(Map<String, Object> param) {
		final Paging obj = new Paging();
		obj.handlePaging(Integer.valueOf((String) param.get("pageIndex")), Integer.valueOf((String) param.get("pageSize")));
		param.put("startRow", obj.getStartRow());
		param.put("pageSize", obj.getPageSize());
		obj.handlePagingList(mapper.brdPage(param), mapper.brdPageCnt(param));
		return obj;
	}

	@Override
	public Brd brdDetailPage(Map<String, Object> param) {
		return mapper.brdDetailPage(param);
	}

	@Override
	public void brdWrite(Map<String, Object> param) throws Exception {
		@SuppressWarnings("unchecked")
		final List<MultipartFile> fileList = (List<MultipartFile>) param.get("fileList");
		int index = 1;
		mapper.brdWrite(param);
		if (!fileList.isEmpty()) {
			final File saveFolder = new File(storePath);
			if (!saveFolder.exists() || saveFolder.isFile()) {
				saveFolder.mkdirs();
			}								
			for(final MultipartFile mf : fileList) {
				final String originFileName = mf.getOriginalFilename();
				final String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
				final String mType = (String) param.get("mType");
				final String reFileName = mType + "_" + UUID.randomUUID().toString().replaceAll("-", "") + extName;
				final String saveFile = storePath + reFileName;
				mf.transferTo(new File(saveFile));
				param.put("attachNo", index);
				param.put("fileNm", originFileName);
				param.put("virFileNm", reFileName);
				param.put("fileExtsn", extName.replace(".", ""));
				mapper.saveAttach(param);
				index++;
			}
		}
	}

	@Override
	public void brdupdate(Map<String, Object> param) throws Exception {
		@SuppressWarnings("unchecked")
		final List<MultipartFile> fileList = (List<MultipartFile>) param.get("fileList");		
		mapper.brdUpdate(param);
		final List<Attach> attachList = mapper.ntcAttachView(param);
		if(param.get("checkboxYn").equals("Y")) {
			final File saveFolder = new File(storePath);
			if (!saveFolder.exists() || saveFolder.isFile()) {
				saveFolder.mkdirs();
			}
			for(int i=0; i<attachList.size(); i++) {
				final String deleteFilePath = storePath + attachList.get(i).getVirFileNm();
				final File deleteFile = new File(deleteFilePath);
				deleteFile.delete();
			}
			mapper.deleteAttach(param);
		}
		if (!fileList.isEmpty()) {
			final List<Attach> attachListSize = mapper.ntcAttachView(param);
			int index = 1;
			if(attachListSize.size()>0) {
				index = attachListSize.get(attachListSize.size()-1).getAttachNo()+1;
			}
			for(final MultipartFile mf : fileList) {
				final String originFileName = mf.getOriginalFilename();
				final String extName = originFileName.substring(originFileName.lastIndexOf("."), originFileName.length());
				final String mType = (String)param.get("mType");
				final String reFileName = mType + "_" + UUID.randomUUID().toString().replaceAll("-", "") + extName;
				final String saveFile = storePath + reFileName;
				mf.transferTo(new File(saveFile));
				param.put("attachNo", index);
				param.put("fileNm", originFileName);
				param.put("virFileNm", reFileName);
				param.put("fileExtsn", extName.replace(".", ""));
				mapper.saveAttach(param);
				index++;
			}			
		}
	}

	@Override
	public void boardDelete(Map<String, Object> param) {
		mapper.boardDelete(param);
	}

	@Override
	public List<Map<String, Object>> searchKeyword(Map<String, Object> param) {
		return mapper.searchKeyword(param);
	}
	
	@Override
	public Paging pricePage(Map<String, Object> param) {
		final Paging obj = new Paging();
		obj.handlePaging(Integer.valueOf((String) param.get("pageIndex")), Integer.valueOf((String) param.get("pageSize")));
		param.put("startRow", obj.getStartRow());
		param.put("pageSize", obj.getPageSize());
		obj.handlePagingList(mapper.pricePage(param), mapper.pricePageCnt(param));
		return obj;
	}

	@Override
	public Price priceDetailPage(Map<String, Object> param) {
		return mapper.priceDetailPage(param);
	}

	@Override
	public void priceUpdate(Map<String, Object> param) {
		mapper.priceUpdate(param);
	}
}