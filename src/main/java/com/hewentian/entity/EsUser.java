package com.hewentian.entity;

/**
 * 
 * <p>
 * <b>EsUser</b> 是 测试es用的bean
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月19日 上午10:32:14
 * @since JDK 1.7
 * 
 */
public class EsUser {
	private String name;
	private String age;
	private String sex;
	private String address;

	public EsUser() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "{name:" + name + ", age:" + age + "}";
	}
}