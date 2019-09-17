package com.hewentian.entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <p>
 * <b>Student</b> 是学生实体类, 要注意其中的set方法
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月25日 下午5:47:23
 * @since JDK 1.7
 * 
 */
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	private String id;

	/** 学生姓名 */
	private String name;

	/** 年龄 */
	private int age;

	/** 生日 */
	private Date birthday;

	public Student() {
		super();
	}

	public String getId() {
		return id;
	}

	public Student setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Student setName(String name) {
		this.name = name;
		return this;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Student setBirthdayStr(String birthday) {
		if (StringUtils.isBlank(birthday)) {
			this.birthday = new Date();
		} else {
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.birthday = df.parse(birthday);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (null == this.birthday) {
				this.birthday = new Date();
			}
		}

		return this;
	}
}