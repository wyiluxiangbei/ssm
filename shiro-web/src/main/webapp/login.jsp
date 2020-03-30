<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>    
<!-- 判断是否认证成功，如果认证成功，执行标签内部的代码 -->
<shiro:authenticated>
	<%
		response.sendRedirect(request.getContextPath()+"/index.do");
	%>
</shiro:authenticated>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 
	认证的表单
		action
		method
		表单名称必须遵循一定规则
		
	1，action 必须是认证失败的页面
	2，method 必须是post
	3，账号密码记住我的默认名称（后期可改）
		username
		password
		rememberMe
	问题：为什么？
	答：因为 认证（登录）请求，也会进去Shiro框架，配置到Shiro过滤器链中的  /**=authc 表单认证过滤器
	
	authc	org.apache.shiro.web.filter.authc.FormAuthenticationFilter
	底层会自动进入 表单认证过滤器的onAccessDenied的方法
	方法内部会判断你的请求地址是否和<property name="loginUrl" value="/user/login.do"/>一样，并且方法是否post
	如果不是，Shiro框就认为你当前不是做认证操作，就认为是一个普通请求，直接就返回false，认为你没有认证
	
	如果地址和方法满足条件，此时Shiro走认证流程，接受账号密码 调用自定Realm的认证方法
	cn.zj.shiro.shiro.CustomRealm的 doGetAuthenticationInfo
		
	
	
	
	

 -->
 <span style="color: red">${errorMsg}</span>
<form action="${pageContext.request.contextPath}/user/login.do" method="post">
	账号：<input type="text" name="username"><br>
	密码：<input type="password" name="password"><br>
	记住我：<input type="checkbox" name="rememberMe"><br>
	<button type="submit">登录</button>
</form>
</body>
</html>