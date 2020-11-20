package com.dwict.jfmc.client.ckeditor.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CkeditorService {

	void ckeditorImg(String uid, String fileName, HttpServletRequest request, HttpServletResponse response);

	void cheditorImgUpload(String fileName, byte[] bytes, HttpServletResponse response);

}
