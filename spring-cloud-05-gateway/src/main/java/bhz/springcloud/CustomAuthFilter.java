package bhz.springcloud;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component		//注入到spring容器
public class CustomAuthFilter extends ZuulFilter {

	
	public static final String TOKEN_VALUE = "123456";
	/**
	 * 		pre:在请求被路由之前调用
			routing: 在请求被路由之中调用
			post: 在请求被路由之后调用
			error: 处理请求发生错误时调用

	 * <B>方法名称：</B><BR>
	 * <B>概要说明：</B><BR>
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";	//post/error/routing
	}
	
	// 表示执行filter的优先级顺序: 值越小 优先级越高（也就是最先被调用）
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;		//0
	}
	
	// 是否需要执行当前的filter的开关
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * <B>方法名称：</B>具体执行filter里面的代码逻辑<BR>
	 * <B>概要说明：</B><BR>
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	@Override
	public Object run() {
		//http请求 ----> zuul (run方法) ---> RequestContext
		RequestContext ctx = RequestContext.getCurrentContext();
		
		HttpServletRequest request = ctx.getRequest();
		
		//
		String uri = request.getRequestURI();	//请求路径
		
		//token (header)
		String token = request.getHeader("x-auth-token");
		
		boolean check = true;
		if(uri.equals("/luck-service/upload") || uri.equals("/zuul/luck-service/upload")){
			check = false;
		}
		
		if(StringUtils.isBlank(token) && check) {
			System.err.println("没有token...校验失败....");
			
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody("--------no token !---------");
			
			return null;
		} else {
			
			// 拿到token  redis
			if(TOKEN_VALUE.equals(token) || !check ){
				//1 我需要解密:  userId_orgId_roleId
				ctx.addZuulRequestHeader("userInfo", "maven");
				ctx.addZuulRequestHeader("otherParams", "ticket123");
			} else {
				ctx.setSendZuulResponse(false);
				ctx.setResponseStatusCode(401);
				ctx.setResponseBody("--------token auth fail ! ---------");
				return null;
			}
		}
		
		return ctx;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
