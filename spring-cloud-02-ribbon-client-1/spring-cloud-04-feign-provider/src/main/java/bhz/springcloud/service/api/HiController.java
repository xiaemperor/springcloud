package bhz.springcloud.service.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {


	@RequestMapping(value = "/hi", method = {RequestMethod.GET})
	public String hello() {
		System.err.println("hi feign ... ");
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hi feign!";
	}
	

	
}
