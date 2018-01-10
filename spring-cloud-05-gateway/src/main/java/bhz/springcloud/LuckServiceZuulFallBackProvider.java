package bhz.springcloud;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author maven http://mawenxia.com
 * 指定段熔某个服务/自定义响应信息内容
 */
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
