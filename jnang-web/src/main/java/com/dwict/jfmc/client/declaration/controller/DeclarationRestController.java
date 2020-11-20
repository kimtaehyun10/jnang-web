package com.dwict.jfmc.client.declaration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dwict.jfmc.client.board.model.Brd;
import com.dwict.jfmc.client.declaration.model.DecComment;
import com.dwict.jfmc.client.declaration.model.Declaration;
import com.dwict.jfmc.client.declaration.service.DeclarationService;

@RestController
@RequestMapping("/data")
public class DeclarationRestController {

	@Resource(name = "declarationService")
	private DeclarationService declarationService;
	
	@PostMapping(value = "/declaration/{cmsCd}")
	public Map<String, Object> declarationWrite(HttpServletRequest request, @PathVariable String cmsCd, MultipartHttpServletRequest files) throws Exception {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		final List<MultipartFile> fileList = files.getFiles("files");
		param.put("cmsCd", cmsCd);
		param.put("dType", request.getParameter("dType"));
		param.put("dTitle", request.getParameter("dTitle"));
		param.put("dWho", request.getParameter("dWho"));
		param.put("dWhen", request.getParameter("dWhen"));
		param.put("dWhere", request.getParameter("dWhere"));
		param.put("dContents", request.getParameter("dContents"));
		param.put("dAcquaintance", request.getParameter("dAcquaintance"));
		param.put("dConfirm", request.getParameter("dConfirm"));
		param.put("dInspector", request.getParameter("dInspector"));
		param.put("dTime", request.getParameter("dTime"));
		param.put("dPass", request.getParameter("dPass"));
		param.put("dAgree", request.getParameter("dAgree"));		
		param.put("fileList", fileList);		
		try {
			declarationService.declarationWrite(param);
			resultMap.put("result", "저장되었습니다.");
			resultMap.put("dCode", param.get("dCode"));			
		} catch (Exception e) {
			resultMap.put("result", "저장오류");
		}
		return resultMap;
	}
	
	@GetMapping(value = "/declaration/{dCode}/{dPass}")
	public Declaration declarationDetailPage(HttpServletRequest request, @PathVariable String dCode, @PathVariable String dPass) {
		final Map<String, Object> param = new HashMap<>();
		param.put("dCode", dCode);
		param.put("dPass", dPass);		
		return declarationService.declarationDetailPage(param);
	}
	
	@GetMapping(value = "/decComment/{cmsCd}/{dNo}")
	public DecComment decCommentDetailPage(HttpServletRequest request, @PathVariable String cmsCd, @PathVariable int dNo) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("dNo", dNo);		
		return declarationService.decCommentDetailPage(param);
	}

}
