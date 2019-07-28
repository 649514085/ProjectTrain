package cn.techaction.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.sun.org.apache.bcel.internal.generic.I2F;

import cn.techaction.common.SverResponse;

@Controller
@RequestMapping("/image")
public class ActionImageController {
	
	@RequestMapping("/uploadimage.do")
	@ResponseBody
	public SverResponse<String> uploadImage(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException{
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			while(iterator.hasNext()) {
				MultipartFile file = multipartHttpServletRequest.getFile(iterator.next());
				if(file!=null) {
					String curFileName = file.getOriginalFilename();
					System.out.println(curFileName);
					if(!curFileName.equals("")) {
						String path = "D:\\文件\\项目实训\\电商平台\\Test\\WebContent\\admin\\recieve\\"+curFileName;
						System.out.println(path);
						File localFile = new File(path);
						file.transferTo(localFile);
						return SverResponse.createRespBySuccess(path);
					}
				}
			}
		}
		return SverResponse.createByErrorMessage("图片上传失败！");
	}
}