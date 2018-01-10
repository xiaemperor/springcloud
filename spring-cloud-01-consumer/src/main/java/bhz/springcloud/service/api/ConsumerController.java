package bhz.springcloud.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author maven http://mawenxia.com
 * 消费端demo
 */
@RestController
public class ConsumerController {

	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 依赖注册中心的application name调用
	 * @return String
	 */
	@RequestMapping(value = "/getByAppName", method = {RequestMethod.GET})
	public String getByAppName(){
		/**
		 * 使用application中注入的restTemplate 启用了LoadBalanced.
		 * 调用地址: http:// [请求协议头]  provider-service [服务名] /provider [context-path] /hello [调用方法的地址]
		 * 建议 如果使用服务名称, 就不需要使用context-path了
		 */
		ResponseEntity<String> reponse = restTemplate.getForEntity("http://provider-service/provider/hello", String.class);
		String body = reponse.getBody();

		return "getByAppName success! info from provider:"+body;
	}
	
	/**
	 * getByUrl:RestTemplate方式调用[HTTP方式调用,不需要注册中心。直接用ip方式访问
	 * @return String
	 */
	@RequestMapping(value = "/getByUrl", method = {RequestMethod.GET})
	public String getByUrl(){
		RestTemplate urlTemplate = new RestTemplate();
		ResponseEntity<String> reponse = urlTemplate.getForEntity("http://localhost:7001/provider/hello", String.class);
		String body = reponse.getBody();

		return "getByUrl success! info from provider:"+body;
	}
	
}
