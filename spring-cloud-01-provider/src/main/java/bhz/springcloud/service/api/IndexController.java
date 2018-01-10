package bhz.springcloud.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author maven http://mawenxia.com
 * 服务提供端
 */
@RestController
public class IndexController {

	
	@RequestMapping(value = "/hello", method = {RequestMethod.GET})
	public String hello(){
		return "Hello World";
	}
	
	//....
	
	
}
