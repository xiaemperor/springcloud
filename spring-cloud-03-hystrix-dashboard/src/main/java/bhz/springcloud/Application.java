package bhz.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard		//启动断路器监控 
@EnableDiscoveryClient	
@SpringBootApplication
public class Application {

	// 监控台地址: http://localhost:5001/hystrix
	
	// 查看[我们要监控哪一个断路器服务]什么样的数据,写上具体的地址: http://localhost:6001/hystrix.stream
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
