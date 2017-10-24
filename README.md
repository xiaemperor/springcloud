Spring Cloud实际应用中，最核心的部分是**hystrix断路器**,**feign声明式调用**和**zuul网关**。其他模块较简单，也常会有其他技术来替代。


####  第一部分：注册中心、服务提供者、消费者
eureka和zookeeper的区别：eureka保证的是CP zookeeper保证的是AP.

   1. **注册中心**为高可用模式  代码位置：``spring-cloud-01-eureka-a``   ``spring-cloud-01-eureka-b``   同时启动a和b 。默认使用application-dev.yml 中的配置。两个eureka-server互相注册。
    2. **服务提供者** 代码位置：  ``spring-cloud-01-provider``   启动它。PS：由于注册中心为高可用故注册在上面的服务需要配置所有注册中心地址:
  ```java
    eureka:
      client:
        service-url:
        ##高可用配置
          defaultZone: http://127.0.0.1:8001/eureka/,http://127.0.0.1:8002/eureka/
  ```
  3.  **服务消费者** 代码位置：``spring-cloud-01-consumer`` 启动它。同服务提供者。Spring Cloud的提供者和消费者没有区别，他们的角色可以互相转换。这点和dubbo需要指定不同。 
  4. 四个程序启动后的状态： ![注册中心状态](http://upload-images.jianshu.io/upload_images/7114162-d53ad50cc276a7a3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
    5. 此时访问消费端的 http://localhost:7002/consumer/getByAppName 和 http://localhost:7002/consumer/getByUrl  可看到调用成功。区别：由于/getByAppName启用了LoadBalance 。需要从注册中心读取application的name来进行调用。而/getByUrl是纯粹的http url的调用，没有从注册中心获取注册列表。
#### 第二部分：Ribbon负载均衡和Retry重试机制
  1. 启动注册中心 ``spring-cloud-02-ribbon-eureka``
  2. 启动服务集群 ``spring-cloud-02-ribbon-client-1`` 和``spring-cloud-02-ribbon-client-2``
  3. 启动消费者 ``spring-cloud-02-ribbon-request`` 
  4. 查看eureka控制台，保证都已注册成功。
  5. 调用消费者API  http://localhost:7003/get  发现交替返回两个服务端的数据。负载均衡实现
  6. 重试机制。重试机制中的坑：只使用ribbon组件的话，ConnectTimeout和ReadTimeout是不起作用的
      ```java
        client-service: ## service的application name
            ribbon:
          ## 只使用ribbon组件下面这个配置 是不起作用的!!~~~
                ConnectTimeout: 250   # 链接超时时间
                ReadTimeout: 3000     # 处理超时时间
                OkToRetryOnAllOperations: true #是否对所有的请求都进行重试
                MaxAutoRetriesNextServer: 1  # 重试时切换实例的次数
                MaxAutoRetries: 5
      ```
      需要在RestTemplate中传入配置好ConnectTimeout和      ReadTimeout等参数的HttpComponentsClientHttpRequestFactory来让重试生效。
    7. **重试机制测试**  在上面1、2条的基础上，再启动 ``spring-cloud-02-ribbon-retry``  请求  http://localhost:7004/retry 会发现请求了六次client-1之后，再请求了一次client-2.并返回了ret: client 2
可从配置的参数和client-1的代码中解释这个重试的现象。
        ```
        custom:
            rest: 
              connect-timeout: 1000
              connection-request-timeout: 1000
              read-timeout: 3000 #读取超时时间3s
        client-service: ## service的application name
            ribbon:
                OkToRetryOnAllOperations: true #是否对所有的请求都进行重试
                MaxAutoRetriesNextServer: 1  # 重试时切换实例的次数
                MaxAutoRetries: 5  #一个实例中最大重试次数
          ```
          ```java
        @RequestMapping(value="/retry",  method = {RequestMethod.GET})
	    public String retry(){
		System.err.println("client 1 call ...........");
		
		///client 睡眠 6s 超过了配置的响应等待3s。故会进行重试。超过五次后请求实例切换为client-2
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "client 1";
	    }	
        ```

#### 第三部分：Hystrix 断路器
   由于hystrix 篇幅较大，故单独成文，请见:
    [Spring Cloud Hystrix 断路器](http://www.jianshu.com/p/88af040caf2a)

#### 第四部分：Feign 声明式服务调用 
Feign 内部集成了Ribbon和Hystrix
  该部分比较简单，先后启动注册中心``spring-cloud-04-feign-server``，和生产者``spring-cloud-04-feign-provider`` 、消费者``spring-cloud-04-feign-consumer `` 
调用消费端API
 http://localhost:7002/hello
http://localhost:7002/hi  (带有Hystrix降级)
__注意__：在写断路器的实现``HelloFeignClientHystrixFallback``时，不要忘了把它注入到spring容器中。

#### 第五部分：Zuul 网关权限
Zuul 集成了Hystrix和Ribbon
__核心功能__：路由和权限的过滤验证功能。所有请求先经过zuul来进行路由到各个子服务系统中，token的验证也往往放在这一层。这样可以让各个微服务的只关心自己的业务。

1.启动注册中心 ``spring-cloud-05-gateway-server``
2.启动两个服务 ``spring-cloud-05-hello-service``  ``spring-cloud-05-luck-service``
3.启动网关 ``spring-cloud-05-gateway``
4.确认两服务一网关都已开启 http://localhost:8001/
  ```java
 zuul:
   routes: 
    api-hello:
      path: /hello-service/**
      service-id: hello-service
    api-luck:
      path: /luck-service/**
      service-id: luck-service
   ```

5. 网关的配置如上，直接访问网关服务所配置的path，将由网关路由到hello和luck服务。http://localhost:5000/luck-service/luck
http://localhost:5000/hello-service/hello
6. 访问以上地址时会发现提示__--------no token !---------__ 那是因为在gateway里面配置了过滤器，用来做token权限的验证。只需继承ZuulFilter，并重写其中的方法，注入到spring容器中。具体的过滤参数和方法见CustomAuthFilter类的注释中。
7. CustomAuthFilter过滤器中验证的为header中的token参数“123456”。故需要postman来进行测试。
![POSTMAN](http://upload-images.jianshu.io/upload_images/7114162-4c34a9c4d3c92f19.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
若更换token的value。也将验证失败。
8. 将请求换成http://localhost:5000/luck-service/luck 将得到另外一个服务luck的返回串。
9. 可在gateway中定义熔断器。实现ZuulFallbackProvider接口，并注入即可。Demo中对luck-service做了熔断。停止luck-service服务，再请求时，会发现收到了熔断器指定的返回内容。
  ```java
@Component
public class LuckServiceZuulFallBackProvider  implements ZuulFallbackProvider {

	// 指定要段熔的服务名字:appName
	@Override
	public String getRoute() {
		return "luck-service";
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse(){

			@Override
			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream("service error.... retry..".getBytes());
			}
			
			//response的响应头信息
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				return headers;
			}

			//自定义响应的状态
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.BAD_REQUEST;	
			}

			//自定义响应的状态码
			@Override
			public int getRawStatusCode() throws IOException {
				return this.getStatusCode().value();	//400
			}

			//状态文本信息
			@Override
			public String getStatusText() throws IOException {
				return this.getStatusCode().getReasonPhrase();
			}

			@Override
			public void close() {
				
			}};
	}

}
  ```
![与熔断器LuckServiceZuulFallBackProvider 设置的返回参数和状态一致](http://upload-images.jianshu.io/upload_images/7114162-2b9e38b5cb8db8f6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 第六部分：Config 配置中心
分布式的系统需要有一个统一的配置中心以方便管理
本部分Demo使用github上的在线配置方式。在本项目的config文件夹中
1. 启动配置中心``spring-cloud-06-config-server``。其配置文件如下：
```java
spring:
  application: 
    name: config-server
  cloud:
    config:
      server:
        git:
          uri: https://github.com/xiaemperor/springcloud # prefix
          search-paths: config #相对路径
server: 
  context-path: /
  port: 4000
```
2. 启动使用该配置的服务端 ``spring-cloud-06-config-client``  服务端的配置路径为上面的配置中心地址。服务名字与配置文件的前半部分一致。这里都为``evn``。遵循了springboot约定优于配置的原则。
配置内容为：
```java
spring:
  application: 
    name: evn  # name遵循配置文件evn-${value}.properties的约定
  cloud:
    config:
      uri: http://localhost:4000/ #表示配置中心的地址
      profile: dev
      label: master


management:
  security:
    enabled: false #禁止之后动态刷新。/refresh就不需要密码
     
server:
  context-path: /
  port: 7001
```
启动之后访问http://localhost:7001/from 会发现返回了``git-dev``
。正好是evn-dev.properties中的内容
3. 动态刷新：若更改了github上的配置文件信息，需要不重启服务来刷新时，需要用post方式请求服务端的refresh接口http://localhost:7001/refresh。但是这样的方式有一个缺点，要对每一个用到配置的服务进行该操作，系统庞大时，没法维护。这时可以使用消息总线__Bus__模块来进行动态刷新。

#### 第七部分：Bus 消息总线
Bus支持的MQ为Kafka和RabbitMQ。本Demo使用RabbitMQ，需要自行安装RabbitMQ进行测试。关于RabbitMQ的安装见：
[RabbitMQ安装方式地址](http://www.jianshu.com/p/25387719a06a)
1. 启动配置中心 ``spring-cloud-07-bus-config-server`` Rabbitmq配置如下：
```java
spring:
  rabbitmq:
    host: 172.16.0.96
    port: 5672
    username: guest
    password: guest
management:
  security:
    enabled: false  ##注意，此处需要配置为false，否则请求刷新API无效
```
2. 启动客户端``spring-cloud-07-bus-config-client`` Rabbitmq的配置和上面一致。
3. 请求http://localhost:7001/from 这时的内容为git-dev
4. 改变github上的配置文件evn-dev.properties 中的内容，用POST方式请求配置中心API  http://localhost:4000/bus/refresh 
5. 再次请求http://localhost:7001/from 发现内容已变为更改后的内容。

此方式只需要管理配置中心这一端。不需要像第六部分中的config那样在客户端上刷新。这才是在真正的微服务项目中使用的方式

__PS: 具体的完整项目中，一般使用Zuul+Feign（集成了Ribbon、Hystrix）+ Eureka + Config__

