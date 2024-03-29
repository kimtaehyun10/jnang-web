package com.dwict.jfmc.client.com.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dwict.jfmc.client.board.model.Attach;
import com.dwict.jfmc.client.board.service.BoardService;
import com.dwict.jfmc.client.com.service.CommonService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/data")
public class CommonController {

	@Value("#{appConfig['server.template.location']}")
	private String templateLocation;

	@Resource(name = "commonService")
	private CommonService commonService;

	@Resource(name = "boardService")
	private BoardService boardService;

	@GetMapping(value = "/encode/text")
	public String encodeText(@RequestParam String text) throws Exception {
		return commonService.encodeText(text);
	}

	@GetMapping(value = "/decode/text")
	public String decodeText(@RequestParam String text) throws Exception {
		return commonService.decodeText(text);
	}

	@GetMapping(value = "/image/{atchFileId}")
	public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String atchFileId) throws IOException {
		commonService.download(request, response, atchFileId);
	}

	@GetMapping(value = "/file/{filename:.+}")
	public ModelAndView download(HttpServletRequest request, Model model, @PathVariable String filename) {
		log.info("::: File download :::");
		final File downloadFile = new File(templateLocation, filename);
		model.addAttribute("downloadFile", downloadFile);
		return new ModelAndView("fileDownloadView", "downloadFile", downloadFile);
	}

	
	//imageView
 	@RequestMapping(value="/file/{path}/{filename:.+}")
    public void imageView(HttpServletRequest request, HttpServletResponse response, @PathVariable String path , @PathVariable String filename) throws ServletException, IOException{
        
        //서버에 저장된 이미지 경로
 		//String path = request.getServletContext().getRealPath("/common/ckeditor/ckupload/");
 		//String path = fileStorePath+"/";
 		path = "/data/files/"+ path +"/";
        String sDirPath = path + filename;
        File imgFile = new File(sDirPath);
        
        //사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
        if(imgFile.isFile()){
            byte[] buf = new byte[1024];
            int readByte = 0;
            int length = 0;
            byte[] imgBuf = null;
            
            FileInputStream fileInputStream = null;
            ByteArrayOutputStream outputStream = null;
            ServletOutputStream out = null;
            
            try{
                fileInputStream = new FileInputStream(imgFile);
                outputStream = new ByteArrayOutputStream();
                out = response.getOutputStream();
                
                while((readByte = fileInputStream.read(buf)) != -1){
                    outputStream.write(buf, 0, readByte);
                }
                
                imgBuf = outputStream.toByteArray();
                length = imgBuf.length;
                out.write(imgBuf, 0, length);
                out.flush();
                
            }catch(IOException e){
                //logger.info(e);
            }finally {
            	outputStream.close();
                fileInputStream.close();
                out.close();
            }
        }
    }
 	
 	
	
	
	@GetMapping(value = "/file/board/{attachId}/{attachNo}")
	public void boardDownload(@PathVariable String attachId, @PathVariable int attachNo ,HttpServletRequest request, HttpServletResponse response) {		
		final Map<String, Object> param = new HashMap<>();				
		param.put("attachId", attachId);		
		param.put("attachNo", attachNo);		
		final Attach ntcAttachView = boardService.ntcAttachView(param).get(0);
		param.put("orignFileNm", ntcAttachView.getFileNm());
		param.put("downFileNm", ntcAttachView.getVirFileNm());
		commonService.boardDownload(param, request, response);				
	}

}
