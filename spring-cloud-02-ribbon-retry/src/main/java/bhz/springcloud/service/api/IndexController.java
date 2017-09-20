package bhz.springcloud.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class IndexController {

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "retry" , method = {RequestMethod.GET})
	public String retry(){
		ResponseEntity<String> response = restTemplate.getForEntity("http://client-service/retry", String.class);
		String ret = response.getBody();
		System.err.println("ret: " + ret);
		return "ret: " + ret;
	}
	
}
