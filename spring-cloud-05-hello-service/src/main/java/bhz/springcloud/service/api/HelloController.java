package bhz.springcloud.service.api;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


	@RequestMapping(value = "/hello", method = {RequestMethod.GET})
	public String hello(@RequestHeader("userInfo") String userInfo, @RequestHeader("otherParams") String otherParams) throws InterruptedException{
		System.err.println("hello service.... ");
		System.err.println("userInfo: " + userInfo);
		System.err.println("otherParams: " + otherParams);
		return "hello!"+userInfo+";params:"+otherParams;
	}
	

	
}
