package bhz.springcloud.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


	@RequestMapping(value = "/hello", method = {RequestMethod.GET})
	public String hello() {
		System.err.println("hello feign ... ");
		return "hello feign!";
	}
	

	
}
