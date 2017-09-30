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

	/**
	 * 配置的断路时间为2秒,但是调用的服务睡眠了3秒。进入降级策略
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "callHelloFailback")
	public String callHello() {
		return restTemplate.getForObject("http://client-service/hello", String.class);
	}
	
	public String callHelloFailback(){
		System.err.println("----------执行降级策略------------");
		return "----------执行降级策略------------";
	}

	/**
	 * 异常捕获方式的断路器
	 */
	@HystrixCommand(fallbackMethod = "handlerFailback",  ignoreExceptions = {BadRequestException.class})
	public String handler() {
		throw new RuntimeException("运行时异常...");
	}

	/**
	 * 异常信息可传递到fallback方法中
	 */
	public String handlerFailback(Throwable e){
		System.err.println("异常信息: " + e.getMessage());
		return "获取异常信息并可以做具体的降级处理";
	}

	/**
	 * 对单独API 配置断路由规则,不受配置文件中的配置参数影响
	 * 如下,配置8秒读取超时,配置文件中的2秒将失效
	 */
	@HystrixCommand(
			commandKey = "hiKey",
			commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", /*KEY*/ value = "8000" /*VALUE*/)},
			fallbackMethod = "callHiFailback")
	public String callhi() {
				System.err.println("--客户端调用-----");
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
