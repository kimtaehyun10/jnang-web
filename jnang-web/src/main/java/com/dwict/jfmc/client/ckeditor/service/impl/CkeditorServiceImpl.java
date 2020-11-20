package com.dwict.jfmc.client.ckeditor.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dwict.jfmc.client.ckeditor.service.CkeditorService;

import lombok.extern.slf4j.Slf4j;

@Service("ckeditorService")
@Slf4j
public class CkeditorServiceImpl implements CkeditorService {

	@Value("#{appConfig['Globals.ckeditorImageUploadStorePath']}")
	private String storePath;

	@Override
	public void ckeditorImg(String uid, String fileName, HttpServletRequest request, HttpServletResponse response) {
		final String sDirPath = storePath + uid + "_" + fileName;
		final File imgFile = new File(sDirPath);	        
		if(imgFile.isFile()){
			final byte[] buf = new byte[1024];
			int readByte = 0;
			int length = 0;
			byte[] imgBuf = null;            

			try(FileInputStream fileInputStream = new FileInputStream(imgFile);
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ServletOutputStream out = response.getOutputStream();) {
				while ((readByte = fileInputStream.read(buf)) != -1) {
					outputStream.write(buf, 0, readByte);
				}                
				imgBuf = outputStream.toByteArray();
				length = imgBuf.length;
				out.write(imgBuf, 0, length);
				out.flush();                
			} catch (final IOException e) {
				log.info("ckeditorImg : IOException");
			}
		} 
	}

	@Override
	public void cheditorImgUpload(String fileName, byte[] bytes, HttpServletResponse response) {
		UUID uid = UUID.randomUUID();
		String ckUploadPath = storePath + uid + "_" + fileName;
    	File folder = new File(storePath);        
    	if(!folder.exists()){
    		try{
    			folder.mkdirs();
    		}catch(Exception e){
    			e.getStackTrace();
    		}
    	}                
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try(OutputStream out = new FileOutputStream(new File(ckUploadPath));
        	PrintWriter printWriter = response.getWriter();){        	        		         	         	                 	        	        
        out.write(bytes);
        out.flush();                         
        String fileUrl = "/data/image/ckeditorImg.json?uid=" + uid + "&fileName=" + fileName;
        printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
        printWriter.flush();         
        }catch(final IOException e){
         e.printStackTrace();
        }      		
	}
}
