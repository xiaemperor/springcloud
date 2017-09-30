package bhz.springcloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.undertow.util.BadRequestException;

@Service
public class HelloService {

	@Autowired
	private RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "callHelloFailback")
	public String callHello() {
		return restTemplate.getForObject("http://client-service/hello", String.class);
	}

	/**
	 * 降级的意义:往往服务都有重试机制,如果没有制定降级策略,一旦服务提供方有异常,所有的客户端配置的重制都将执行,加重了双方的压力。
	 * @return
	 */
	public String callHelloFailback(){
		System.err.println("----------执行降级策略------------");
		return "----------执行降级策略------------";
	}

	@HystrixCommand(fallbackMethod = "handlerFailback",  ignoreExceptions = {BadRequestException.class})
	public String handler() {
		throw new RuntimeException("运行时异常...");
	}

	public String handlerFailback(Throwable e){
		System.err.println("异常信息: " + e.getMessage());
		return "获取异常信息并可以做具体的降级处理";
	}
	
	@HystrixCommand(
			commandKey = "hiKey",
			commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", /*KEY*/ value = "8000" /*VALUE*/)},
			fallbackMethod = "callHiFailback")
	public String callhi() {
		return restTemplate.getForObject("http://client-service/hi", String.class);
	}
	
	public String callHiFailback(){
		System.err.println("----------执行降级策略------------");
		return "----------执行降级策略------------";
	}
	
	
	
	@HystrixCommand(fallbackMethod = "callrequestFailback")
	public String callrequest() {
		return restTemplate.getForObject("http://client-service/request", String.class);
	}
	
	public String callrequestFailback(){
		System.err.println("----------执行降级策略------------");
		return "----------执行降级策略------------";
	}
	
	
}
