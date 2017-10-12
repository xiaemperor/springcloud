package bhz.springcloud.service.api;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LuckController {


	@RequestMapping(value = "/luck", method = {RequestMethod.GET})
	public String luck() throws InterruptedException{
		System.err.println("luck service ... ");
		return "luck!";
	}
	
	@RequestMapping(value = "/upload", method = {RequestMethod.POST})
	public String upload(@RequestParam("file") MultipartFile file) {
		
		System.err.println("名称:" + file.getOriginalFilename());
		try {
			FileUtils.writeByteArrayToFile(new File("/Users/sam/Downloads/" + file.getOriginalFilename()), file.getBytes());
			return "ok";
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e);
			return "error";
		}
		
	}

	
}
