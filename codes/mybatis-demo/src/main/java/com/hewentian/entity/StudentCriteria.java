package com.hewentian.entity;

import org.apache.ibatis.session.RowBounds;

/**
 * 
 * <p>
 * <b>StudentCriteria</b> 是 学生类的条件
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月25日 下午5:49:17
 * @since JDK 1.7
 * 
 */
public class StudentCriteria {
	private String name;
	private RowBounds rowBounds;

	public StudentCriteria() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RowBounds getRowBounds() {
		return rowBounds;
	}

	public void setRowBounds(RowBounds rowBounds) {
		this.rowBounds = rowBounds;
	}
}