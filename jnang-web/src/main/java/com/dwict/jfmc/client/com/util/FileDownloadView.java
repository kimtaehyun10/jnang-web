package com.dwict.jfmc.client.com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.dwict.jfmc.client.com.service.CommonService;

@Component
public class FileDownloadView extends AbstractView {

	@Resource(name = "commonService")
	private CommonService commonService;

	@SuppressWarnings("resource")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		setContentType("application/download; utf-8");
		final File file = (File) model.get("downloadFile");
		response.setContentType(getContentType());
		response.setContentLength((int) file.length());
		final String fileName = URLEncoder.encode(file.getName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transter-Encoding", "binary");
		final OutputStream out = response.getOutputStream();
		commonService.copy(new FileInputStream(file).getChannel(), Channels.newChannel(out));
		out.flush();
	}

}
