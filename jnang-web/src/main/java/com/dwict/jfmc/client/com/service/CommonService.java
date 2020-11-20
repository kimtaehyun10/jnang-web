package com.dwict.jfmc.client.com.service;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;

import com.dwict.jfmc.client.com.model.AtchFile;

public interface CommonService {

	String encodeText(String text);
	String decodeText(String text);
	void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String atchFileId) throws IOException;
	AtchFile atchFile(AtchFile param);
	boolean copy(FileChannel in, WritableByteChannel out);
	void boardDownload(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response);

}
