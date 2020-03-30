package cn.zj.shiro.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	
	@RequiresPermissions("user:list")
	@RequestMapping("/list")
	public String index() {
		
		
		return "userList";
	}
	
	
	@RequiresPermissions("user:delete")
	@RequestMapping("/delete")
	public void delete(Integer id) {
		
	}
	
	
	/*
	 * 
	 * Shiro框架认证失败以后跳转的页面
	 * 在此功能获取Shiro认证失败的错误信息，共享以后并跳转登录页面
	 * 
	 * 如果表单认证失败，会将异常信息共享的请求对象中，共享异常信息的属性名称 ： shiroLoginFailure
	 * 
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model m) {
		String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
		System.out.println("shiroLoginFailure :"+shiroLoginFailure);
		//shiroLoginFailure :org.apache.shiro.authc.UnknownAccountException
		//shiroLoginFailure :org.apache.shiro.authc.IncorrectCredentialsException
		
		if(UnknownAccountException.class.getName().equals(shiroLoginFailure)) {
			m.addAttribute("errorMsg", "亲，账号不存在");
		}else if(IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)) {
			m.addAttribute("errorMsg", "亲，密码错误");
		}
		
		return "forward:/login.jsp";
	}
}
