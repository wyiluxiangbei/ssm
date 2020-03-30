<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 引入Shiro标签库 -->
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>



 <div >
 	欢迎：<shiro:principal property="username"/>
	
	<a style="float: right;" href="${pageContext.request.contextPath}/logout">退出登录</a> 		
 </div>
 <hr>
 
<shiro:hasPermission name="user:list">
 	<a href="${pageContext.request.contextPath}/user/list.do">用户管理</a><br>	
</shiro:hasPermission> 

<shiro:hasPermission name="role:list">
 	<a href="${pageContext.request.contextPath}/role/list.do">角色管理</a><br>	
</shiro:hasPermission> 

<shiro:hasPermission name="permission:list">
 	<a href="${pageContext.request.contextPath}/permission/list.do">权限管理</a><br>	
</shiro:hasPermission> 
