package bhz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer		//开启spring cloud cofig 配置中心
@SpringBootApplication
public class Application {
	
	//刷新配置地址: post: http://localhost:4000/bus/refresh
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
