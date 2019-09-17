package com.hewentian.entity;

/**
 * 
 * <p>
 * <b>SysUser</b> 是 模拟系统登录的用户
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月26日 下午3:57:24
 * @since JDK 1.7
 * 
 */
public class SysUser {
	/** 用户名 */
	private String name;

	/** 密码 */
	private String password;

	public SysUser() {
		super();
	}

	public String getName() {
		return name;
	}

	public SysUser setName(String name) {
		this.name = null == name ? null : name.trim();
		return this;
	}

	public String getPassword() {
		return password;
	}

	public SysUser setPassword(String password) {
		this.password = null == password ? null : password.trim();
		return this;
	}
}