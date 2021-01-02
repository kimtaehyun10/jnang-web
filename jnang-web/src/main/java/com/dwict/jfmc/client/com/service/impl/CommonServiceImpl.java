package com.dwict.jfmc.client.com.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.com.mapper.CommonMapper;
import com.dwict.jfmc.client.com.model.AtchFile;
import com.dwict.jfmc.client.com.service.CommonService;
import com.dwict.jfmc.client.com.util.AES;

import lombok.extern.slf4j.Slf4j;

@Service("commonService")
@Slf4j
public class CommonServiceImpl implements CommonService {

	@Value("#{appConfig['server.admin.location1']}")
	private String adminLocation1;

	@Value("#{appConfig['server.admin.location2']}")
	private String adminLocation2;

	@Value("#{appConfig['crypto.secretKey']}")
	private String secretKey;

	@Value("#{appConfig['Globals.fileUploadStorePath']}")
	private String storePath;

	@Resource(name = "commonMapper")
	private CommonMapper commonMapper;

	@Override
	public String encodeText(String text) {
		final AES aes = new AES();
		return aes.encrypt(text, secretKey);
	}

	@Override
	public String decodeText(String text) {
		final AES aes = new AES();
		return aes.decrypt(text, secretKey);
	}

	@SuppressWarnings("resource")
	@Override
	public void download(HttpServletRequest request, HttpServletResponse response, String atchFileId)
			throws IOException {
		final AtchFile image = atchFile(new AtchFile(atchFileId));
		final File downloadFile = getFileNameByCmsAdmin(image);
		String type = "";
		if (image.getFileExtsn() != null && !"".equals(image.getFileExtsn())) {
			if ("jpg".equals(image.getFileExtsn().toLowerCase())) {
				type = "image/jpeg";
			} else {
				type = "image/" + image.getFileExtsn().toLowerCase();
			}
			type = "image/" + image.getFileExtsn().toLowerCase();
		} else {
			log.debug("Image fileType is null.");
		}
		response.setHeader("Content-Type", type);
		response.setContentLength((int) downloadFile.length());
		final String fileName = URLEncoder.encode(downloadFile.getName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		response.setHeader("Content-Transter-Encoding", "binary");
		copy(new FileInputStream(downloadFile).getChannel(), Channels.newChannel(response.getOutputStream()));

	}

	@Override
	public AtchFile atchFile(AtchFile param) {
		return commonMapper.atchFile(param);
	}

	public boolean copy(FileChannel in, WritableByteChannel out) {
		if (in == null) return false;
		if (out == null) return false;
		try {
			in.transferTo(0, in.size(), out);
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (final IOException e) {}
			if (out != null)
				try {
					out.close();
				} catch (final IOException e) {}
		}
		return true;
	}

	@Override
	public void boardDownload(Map<String, Object> param, HttpServletRequest request, HttpServletResponse response) {
		String orignFileNm = (String) param.get("orignFileNm");
		final String downFileNm = (String) param.get("downFileNm");
		if (orignFileNm == null || "".equals(orignFileNm)) {
			orignFileNm = downFileNm;
		}
		try {
			final String browser = request.getHeader("User-Agent");              
			if (browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")) {
				orignFileNm = URLEncoder.encode(orignFileNm, "UTF-8").replaceAll("\\+", "%20");
			} else {
				orignFileNm = new String(orignFileNm.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch (final UnsupportedEncodingException ex) {
			log.info("UnsupportedEncodingException");
		}
		final String realPath = storePath + downFileNm;
		final File file1 = new File(realPath);
		if (file1.exists()) {
			response.setContentType("application/octer-stream");
			response.setHeader("Content-Transfer-Encoding", "binary;");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + orignFileNm + "\"");
			try ( OutputStream os = response.getOutputStream();
					final FileInputStream fis = new FileInputStream(realPath)) {
				int ncount = 0;
				final byte[] bytes = new byte[512];
				while ((ncount = fis.read(bytes)) != -1 ) {
					os.write(bytes, 0, ncount);
				}
			} catch (final FileNotFoundException ex) {
				log.info("FileNotFoundException");
			} catch (final IOException ex) {
				log.info("IOException");
			}
		} else {
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} catch (final IOException ex) {
				log.info("IOException");
			}
		}
	}

	private File getFileNameByCmsAdmin(AtchFile image) {
		File downloadFile = new File(image.getFileStreCours(), image.getStreFileNm());
		if(!downloadFile.exists()) {
			downloadFile = new File(image.getFileStreCours(), image.getStreFileNm());
		}
		return downloadFile;
	}

}
