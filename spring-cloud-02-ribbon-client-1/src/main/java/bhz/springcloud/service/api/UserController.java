package bhz.springcloud.service.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bhz.springcloud.entity.User;

@RestController
public class UserController {


	@RequestMapping(value = "/getUser", method = {RequestMethod.GET})
	public User getUser(@RequestParam("id") String id){
		System.err.println("client 1 -------------- id: " + id);
		return new User(id, "张1", 18,"client-1");
	}
	
	@RequestMapping(value = "/postUser", method = {RequestMethod.POST})
	public User postUser(@RequestParam("id") String id){
		System.err.println("client 1 -------------- id: " + id);
		return new User(id, "李四", 19,"client-1");
	}
	
	@RequestMapping(value = "/insertUser", produces={"application/json;charset=UTF-8"}, consumes = {"application/json;charset=UTF-8"}, method = {RequestMethod.POST})
	public User insertUser(@RequestBody User user){
		System.err.println("client 1 insert -------------- name: " + user.getName());
		return user;
	}

	@RequestMapping(value = "/putUser",  produces={"application/json;charset=UTF-8"}, consumes = {"application/json;charset=UTF-8"}, method = {RequestMethod.PUT})
	public void putUser(@RequestBody User user, @RequestParam("id") String id){
		System.err.println("client 1 update -------------- name: " + user.getName());
	}
	
	@RequestMapping(value = "/deleteUser", method = {RequestMethod.DELETE})
	public void deleteUser(@RequestParam("id") String id){
		System.err.println("client 1 delete -------------- id: " + id);
	}	
	
	
	@RequestMapping(value="/retry",  method = {RequestMethod.GET})
	public String retry(){
		System.err.println("client 1 call ...........");
		
		///client 睡眠 6s 超过了配置的响应等待3s
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "client 1";
	}	
	
	
	
}
