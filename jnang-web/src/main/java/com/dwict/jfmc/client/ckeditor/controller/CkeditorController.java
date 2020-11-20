package com.dwict.jfmc.client.ckeditor.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dwict.jfmc.client.ckeditor.service.CkeditorService;

@RestController
@RequestMapping("/data")
public class CkeditorController {

	@Resource(name = "ckeditorService")
	private CkeditorService ckeditorService;

	@GetMapping(value="/image/ckeditorImg.json")
	public void ckeditorImg(@RequestParam(value="uid") String uid, @RequestParam(value="fileName") String fileName, 
			HttpServletRequest request, HttpServletResponse response) {	        
		ckeditorService.ckeditorImg(uid, fileName, request, response);	
	}

	@PostMapping(value="/image/cheditorImgUpload")
	public void cheditorImgUpload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiFile, 
			@RequestParam MultipartFile upload) throws Exception{
		final String fileName = upload.getOriginalFilename();
		final byte[] bytes = upload.getBytes();
		ckeditorService.cheditorImgUpload(fileName, bytes, response);				
	}
}
