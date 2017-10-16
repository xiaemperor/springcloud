package bhz.springcloud.service.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope//不需要重启,动态刷新配置
@RestController
public class FromController {

	
	@Value("${from}")
	private String from;
	
	
	@RequestMapping(value = "/from")
	public String from(){
		System.err.println("from: " + from);
		return this.from;
	}
	
}
