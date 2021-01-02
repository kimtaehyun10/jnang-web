package com.dwict.jfmc.client.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dwict.jfmc.client.board.model.Attach;
import com.dwict.jfmc.client.board.model.Brd;
import com.dwict.jfmc.client.board.model.Ntc;
import com.dwict.jfmc.client.board.model.Price;
import com.dwict.jfmc.client.board.service.BoardService;
import com.dwict.jfmc.client.com.model.Paging;

@RestController
@RequestMapping("/data")
public class BoardRestController {

	@Resource(name = "boardService")
	private BoardService service;

	@GetMapping(value = "/faq/{cmsCd}")
	public Paging faqPage(HttpServletRequest request, @PathVariable String cmsCd) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("pageIndex", request.getParameter("pageIndex"));
		param.put("pageSize", request.getParameter("pageSize"));
		param.put("startRow", request.getParameter("startRow"));
		param.put("searchKey", request.getParameter("searchKey"));
		param.put("searchValue", request.getParameter("searchValue"));
		return service.faqPage(param);
	}

	@GetMapping(value = "/ntc/{cmsCd}")
	public Paging ntcPage(HttpServletRequest request, @PathVariable String cmsCd) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("pageIndex", request.getParameter("pageIndex"));
		param.put("pageSize", request.getParameter("pageSize"));
		param.put("startRow", request.getParameter("startRow"));
		param.put("searchKey", request.getParameter("searchKey"));
		param.put("searchValue", request.getParameter("searchValue"));
		return service.ntcPage(param);
	}

	@GetMapping(value = "/ntc/{cmsCd}/{brdNo}")
	public Ntc ntcDetailPage(@PathVariable String cmsCd, @PathVariable int brdNo) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		return service.ntcDetailPage(param);
	}

	@PostMapping(value = "/board/hit/{cmsCd}/{brdNo}")
	public int boardHit(HttpServletRequest request, @PathVariable String cmsCd, @PathVariable int brdNo) {	
		final Map<String, Object> param = new HashMap<>();				
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		param.put("mType", request.getParameter("mType"));
		return service.boardHit(param);
	}

	@GetMapping(value = "/ntc/{cmsCd}/{brdNo}/{attachId}")
	public List<Attach> ntcAttachView(@PathVariable String cmsCd, @PathVariable int brdNo, @PathVariable String attachId) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		param.put("attachId", attachId);		
		return service.ntcAttachView(param);
	}

	@GetMapping(value = "/brd/{cmsCd}")
	public Paging brdPage(HttpServletRequest request, @PathVariable String cmsCd) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("pageIndex", request.getParameter("pageIndex"));
		param.put("pageSize", request.getParameter("pageSize"));
		param.put("startRow", request.getParameter("startRow"));
		param.put("searchKey", request.getParameter("searchKey"));
		param.put("searchValue", request.getParameter("searchValue"));
		param.put("secYn", request.getParameter("secYn"));
		return service.brdPage(param);
	}

	@GetMapping(value = "/brd/{cmsCd}/{brdNo}")
	public Brd brdDetailPage(HttpServletRequest request, @PathVariable String cmsCd, @PathVariable int brdNo) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		param.put("upBrdNo", request.getParameter("upBrdNo"));
		return service.brdDetailPage(param);
	}

	@PostMapping(value = "/brd/{cmsCd}")
	public Map<String, Object> brdWrite(HttpServletRequest request, @PathVariable String cmsCd, MultipartHttpServletRequest files) throws Exception {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		final List<MultipartFile> fileList = files.getFiles("files");
		param.put("cmsCd", cmsCd);
		param.put("title", request.getParameter("title"));
		param.put("cont", request.getParameter("cont"));
		param.put("secretYn", request.getParameter("secretYn"));
		param.put("regId", request.getParameter("regId"));
		param.put("fileList", fileList);
		param.put("mType", request.getParameter("mType"));
		param.put("ips", request.getRemoteAddr());
		service.brdWrite(param);
		resultMap.put("result", "저장되었습니다.");
		return resultMap;
	}

	@PostMapping(value = "/brd/{cmsCd}/{brdNo}")
	public Map<String, Object> brdUpdate(HttpServletRequest request, @PathVariable String cmsCd, @PathVariable int brdNo, MultipartHttpServletRequest files) throws Exception{		
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		final List<MultipartFile> fileList = files.getFiles("files");
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		param.put("mType", request.getParameter("mType"));
		param.put("regId", request.getParameter("regId"));
		param.put("title", request.getParameter("title"));
		param.put("cont", request.getParameter("cont"));
		param.put("secretYn", request.getParameter("secretYn"));
		param.put("checkboxYn", request.getParameter("checkboxYn"));
		param.put("attachId", request.getParameter("attachId"));
		param.put("fileList", fileList);
		service.brdupdate(param);
		resultMap.put("result", "저장되었습니다.");
		return resultMap;
	}

	@DeleteMapping(value = "/board/{cmsCd}/{brdNo}")
	public Map<String, Object> boardDelete(HttpServletRequest request, @PathVariable String cmsCd, @PathVariable int brdNo) {		
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		param.put("mType", request.getParameter("mType"));
		service.boardDelete(param);
		resultMap.put("result", "삭제되었습니다.");
		return resultMap;
	}

	@GetMapping(value = "/board/search/{keyword}")
	public List<Map<String, Object>> searchKeyword(HttpServletRequest request, @PathVariable String keyword) {
		final Map<String, Object> param = new HashMap<>();
		param.put("keyword", keyword);
		final List<Map<String, Object>> resultList = service.searchKeyword(param);
		return resultList;
	}

	@GetMapping(value = "/board/price/{cmsCd}")
	public Paging pricePage(HttpServletRequest request, @PathVariable String cmsCd) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("pageIndex", request.getParameter("pageIndex"));
		param.put("pageSize", request.getParameter("pageSize"));
		param.put("startRow", request.getParameter("startRow"));
		param.put("searchKey", request.getParameter("searchKey"));
		param.put("searchValue", request.getParameter("searchValue"));
		return service.pricePage(param);
	}
	
	@GetMapping(value = "/board/price/{cmsCd}/{brdNo}")
	public Price priceDetailPage(HttpServletRequest request, @PathVariable String cmsCd, @PathVariable String brdNo) {
		final Map<String, Object> param = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);		
		return service.priceDetailPage(param);
	}
	
	@PatchMapping(value = "/board/price/{cmsCd}/{brdNo}")
	public Map<String, Object> priceUpdate(HttpServletRequest request, @PathVariable String cmsCd, @PathVariable String brdNo) {
		final Map<String, Object> param = new HashMap<>();
		final Map<String, Object> resultMap = new HashMap<>();
		param.put("cmsCd", cmsCd);
		param.put("brdNo", brdNo);
		param.put("columnValue", request.getParameter("columnValue"));
		param.put("price", request.getParameter("price"));
		service.priceUpdate(param);
		resultMap.put("result", "저장되었습니다.");
		return resultMap;
	}
}
