package bhz.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bhz.springcloud.feign.hystrix.HelloFeignClientHystrixFallback;

@FeignClient(name="feign-provider", fallback = HelloFeignClientHystrixFallback.class)
public interface HelloFeignClient {

	@RequestMapping(value = "/hello", method = {RequestMethod.GET})
	public String hello();
	
	@RequestMapping(value = "/hi", method = {RequestMethod.GET})
	public String hi();
	
}
