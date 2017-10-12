package bhz.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy		//启用网关路由
//@EnableDiscoveryClient	//表示我是一个服务,注册到服务中心上
//@SpringBootApplication
@SpringCloudApplication
public class Application {

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
