package cn.zj.shiro.shiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import cn.zj.shiro.pojo.User;

public class CustomRealm  extends AuthorizingRealm{


	/*
	 * 认证方法，获取认证信息，开发者自己内部完成认证逻辑
	 * 
	 * 认证逻辑分析
	 * 
	 * 1，注入UserService 层
	 * @Autowired
	 * private UserService userService;
	 * 
	 * 2,获取方法参数token的身份（账号）
	 * 3,调用service根据账号去数据库查询是否存在的方法
	 * User user = userService.selectUserByUsername(username);
	 * 	user==null: Shiro框架底层就会抛出：UnknownAccountException 账号不存在
	 *  user!=null;
	 * 4，如果user！=null 创建认证信息（AuthenticationInfo）对象，将数据库查询出的身份（user对象），凭证（密码）信息设置到身份信息对象中
	 * 
	 * 5,返回认证信息对象
	 * 
	 * 6，Shiro底层完成
	 * 		比对凭证（密码）
	 * 		Shiro底层回去拿数据库中查询的凭证（密码）和 token（外界传递进来）的凭证（密码）进行比对
	 * 	  比对成功：认证成功
	 *   比对失败，抛异常：IncorrectCredentialsException 密码错误异常	
	 *   
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		//获取方法参数token的身份（账号）
		String username = (String) token.getPrincipal();
		
		//模拟：调用service根据账号去数据库查询是否存在的方法
		List<String> users = Arrays.asList("lucy","lili","jack","rose");
		if(!users.contains(username)) {
			System.out.println("数据库中账号不存在");
			return null;
		}
		//模拟数据库查询user对象（包含用户的所有信息，账号，密码....）
		//User user = userService.selectUserByUsername(username);
		User user = new User(username, "142a04d176b6960cf517c6b2bac95630","qwer");
		
		/*创建认证信息对象
		 * 
		 *  Object principal; 身份就是数据库查询的user对象
			Object hashedCredentials; 数据库中查询出来加密的凭证（密码）
			String realmName; realm的名称（可以随便见名知意的取名）
			ByteSource credentialsSalt; 当前数据库查询出的user的salt
			new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, realmName);
		 * 
		 */
		Object principal = user;
		
		String hashedCredentials = user.getPassword();
		String salt = user.getSalt();
		
		ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
		
		//返回的认证信息对象
		return new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, this.getName());
		
	}
	
	/*
	 * 授权方法，获取授权信息，开发者自己内部完成授权逻辑
	 * 
	 * 1，获取当前认证的身份：认证成功的user对象
	 * 
	 * 2, 根据user的角色信息，查询出对应的权限表达式
	 * 	一个用户一个角色，一个角色有多个权限（权限表达式）
	 * 3, 创建授权信息对象
	 * 		AuthorizationInfo，并且将第二步用户的权限表达式添加到授权信息对象中
	 * 4, 返回授权信息对象
	 * 
	 * 5, Shiro底层会将程序入口使用权限表达和授权方法当前身份添加到Shiro中的多个权限表达式匹配
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权方法执行了-----");
		//获取当前认证的身份：认证成功的user对象
		User user = (User) principals.getPrimaryPrincipal();
		//根据user的角色信息，查询出对应的权限表达式
		List<String> permissions = new ArrayList<String>();
		permissions.add("user:list");
		permissions.add("user:insert");
		permissions.add("role:list");
		//... 实际开发不同的用户有不同的角色对应不同权限表达式
		
		//创建授权信息对象 ：AuthorizationInfo，并且将第二步用户的权限表达式添加到授权信息对象中
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(permissions);
		
		return authorizationInfo;
	}

	

}
