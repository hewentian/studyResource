package com.hewentian.solr.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.solr.client.solrj.beans.Field;

/**
 * 
 * <p>
 * <b>User</b> 是 solr 中的文档
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年10月28日 下午12:07:21
 * @since JDK 1.8
 *
 */
public class User implements Serializable {
	/** 用户id */
	@Field
	private String id;

	/** 名字 */
	@Field
	private String name;

	/** 年龄 */
	@Field
	private Integer age;

	/** 资格；等级 */
	@Field
	private String ability;

	/** 详细地址 */
	@Field
	private String address;

	/** 就读过的所有学校 */
	@Field
	private List<String> schools;

	/** 更新时间 */
	@Field
	private Date update_time;

	public User() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getSchools() {
		return schools;
	}

	public void setSchools(List<String> schools) {
		this.schools = schools;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
