package bhz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer		//启用服务器的配置中心
@SpringBootApplication
public class EurekaApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);
	}
	
	
	
}







