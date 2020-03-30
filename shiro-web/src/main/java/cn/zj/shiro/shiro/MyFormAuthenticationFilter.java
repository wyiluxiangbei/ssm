package cn.zj.shiro.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import cn.zj.shiro.pojo.User;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	
	
	/*
	 * 
	 * 重写父类方法，访问的资源是否允许访问，true，允许，false 不允许
	 * 
	 */
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		//从请求中获取Shiro的 主体
		Subject subject = getSubject(request, response);
		
		System.out.println(subject.isAuthenticated());
		//如果主体没有认证（Session中认证）并且 主体已经设置记住我了
		if (!subject.isAuthenticated() && subject.isRemembered()) {
			
			
			//获取主体的身份（从记住我的Cookie中获取的）
			User principal = (User) subject.getPrincipal();
			
			//从主体中获取Shiro框架的Session
			Session session = subject.getSession();
			
			System.out.println("principal :"+principal);
			
			//将身份认证信息共享到 Session中（Shiro底层判断是否认证，不是通过属性名称判断，通过身份类型判断）
			session.setAttribute("USER_IN_SESSION", principal);
		}
		return subject.isAuthenticated() || subject.isRemembered();
	}
	
	
	/*
	 * 认证成功以后跳转页面
	 * 
	 * 包装/装饰 设计模式
	 * 
	 * 子类重写父类方法，不改变原有功能基础之上对父类方法做功能增强
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		
		//将保存到Session的 SaveAndRequest数据清除掉
		WebUtils.getAndClearSavedRequest(request);
		
		return super.onLoginSuccess(token, subject, request, response);
	}
	
}
