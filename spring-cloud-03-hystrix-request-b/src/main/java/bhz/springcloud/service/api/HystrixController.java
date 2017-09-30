package bhz.springcloud.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import bhz.springcloud.service.HelloService;

@RestController
public class HystrixController {

	
	@Autowired
	private HelloService helloService;
	
	@RequestMapping(value = "/hystrix-hello", method = {RequestMethod.GET})
	public String hello() {
		return helloService.callHello();
	}
	
	
	@RequestMapping(value = "/hystrix-handler", method = {RequestMethod.GET})
	public String handler() {
		return helloService.handler();
	}
	
	
	@RequestMapping(value = "/hystrix-hi", method = {RequestMethod.GET})
	public String hi() {
		return helloService.callhi();
	}
		
	
	@RequestMapping(value = "/hystrix-request", method = {RequestMethod.GET})
	public String request() {
		return helloService.callrequest();
	}
		
	
	
	
	
	
	
	
	
	
}
