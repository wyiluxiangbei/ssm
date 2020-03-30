package cn.zj.shiro.pojo;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 146351704175930227L;
	private String username;
	private String password;
	private String salt;//Shiro框架用的salt字段
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public User(String username, String password, String salt) {
		super();
		this.username = username;
		this.password = password;
		this.salt = salt;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", salt=" + salt + "]";
	}
	public User() {
		super();
	}
	
	
}
