package bhz.springcloud.feign.hystrix;

import org.springframework.stereotype.Component;

import bhz.springcloud.feign.HelloFeignClient;


@Component
public class HelloFeignClientHystrixFallback implements HelloFeignClient{

	@Override
	public String hello() {
		System.err.println("---------hello方法的降级策略---------");
		return "---------hello方法的降级策略---------";
	}

	@Override
	public String hi() {
		System.err.println("---------hi方法的降级策略---------");
		return "---------hi方法的降级策略---------";
	}

}
