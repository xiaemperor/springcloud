package bhz.springcloud.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@RequestMapping(value = "/getByAppName", method = {RequestMethod.GET})
	public String getByAppName(){
		/**
		 * 使用application中注入的restTemplate .由于启用了LoadBalanced.故需要使用在注册中心注册的application name方式访问
		 */
		//调用地址: http:// [请求协议头]  provider-service [服务名] /provider [context-path] /hello [调用方法的地址]
		//建议 如果使用服务名称, 就不需要使用context-path了
		ResponseEntity<String> reponse = restTemplate.getForEntity("http://provider-service/provider/hello", String.class);
		String body = reponse.getBody();
		System.err.println("body : " + body);
		
		return "调用成功!";
	}
	
	/**
	 * <B>方法名称：</B>RestTemplate方式调用[HTTP方式调用,不需要注册中心。直接用ip方式访问]<BR>
	 * <B>概要说明：</B><BR>
	 * @return
	 */
	@RequestMapping(value = "/getByUrl", method = {RequestMethod.GET})
	public String getByUrl(){
		RestTemplate urlTemplate = new RestTemplate();
		ResponseEntity<String> reponse = urlTemplate.getForEntity("http://localhost:7001/provider/hello", String.class);
		String body = reponse.getBody();
		System.err.println("body : " + body);
		
		return "调用成功!";
	}
	
}
