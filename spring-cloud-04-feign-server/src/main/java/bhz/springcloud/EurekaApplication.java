/**
 * Copyright 2017 BAIHZ All Rights Reserved.
 */
/**
 * <B>系统名称：</B><BR>
 * <B>模块名称：</B><BR>
 * <B>中文类名：</B><BR>
 * <B>概要说明：</B><BR>
 * @author baihezhuo
 * @since 2017年9月7日 下午8:31:54
 */
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







