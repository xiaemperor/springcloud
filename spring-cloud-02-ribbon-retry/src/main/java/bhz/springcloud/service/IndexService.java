package bhz.springcloud.service;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

/**
 * 此Service只是为了测试Retryable是否有效,不对Controller造成影响。测试类在test中
 */

@Service
public class IndexService {

	//方法: 需要进行重试[远程服务调用失败的情况.再次进行调用]
	
	@Retryable(
			value = {RemoteAccessException.class,ResourceAccessException.class} ,	//value 意思就是什么样的异常进行重试策略的执行
			maxAttempts = 2,	//重试次数
			backoff = @Backoff(delay = 5000, multiplier = 5))	//delay: 间隔 , 多少个执行者
	public void call() throws Exception {
		System.err.println("调用了 call 方法 执行.........");
		throw new RemoteAccessException("RPC调用异常......");
	}
	
	@Recover //做最终失败的补偿
	public void recover(RemoteAccessException e) {
		System.err.println("------------最终处理 ------------- message: " + e.getMessage());
	}

	@Recover
	public void recover(ResourceAccessException e){
		System.err.println("------------最终处理 ------------- message: " + e.getMessage());
	}
	
	
	
	
	
}
