package bhz.springcloud.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


	@RequestMapping(value = "/hello", method = {RequestMethod.GET})
	public String hello() throws InterruptedException{
		Thread.sleep(3000);
		System.err.println("hello hystrix ... ");
		return "hello hystrix!";
	}
	
	@RequestMapping(value = "/hi", method = {RequestMethod.GET})
	public String hi() throws InterruptedException{
		System.err.println("client--/hi--");
		Thread.sleep(5000);
		System.err.println("hi hystrix ... ");
		return "hi hystrix!";
	}
	
	@RequestMapping(value = "/request", method = {RequestMethod.GET})
	public String request() throws InterruptedException{
		System.err.println("request ... ");
		return "request!";
	}

	
}
