package cn.zj.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController {
	
	/*
	 * 角色权限
	 * @RequiresRoles({"role1","role2","roleN"})
	 * 
	 * 权限表达式注解授权:推荐，一个资源永远都对应一个权限对应一个权限表达式
	 * @RequiresPermissions("role:list")
	 */
	//@RequiresRoles({"role1","role2","role3"})
	
	@RequiresPermissions("role:list")
	@RequestMapping("/list")
	public String index() {
		
		
		return "roleList";
	}
	
}
