package com.hewentian.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.hewentian.util.DateUtil;

/**
 * <p>
 * <b>MyDate</b> 是 日期实体类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月24日 下午4:15:13
 * @since JDK 1.7
 * 
 */
public class MyDate implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 是否是日期:true/false */
	private boolean isDate;

	/** 日期模式：yyyy-MM-dd HH:mm:ss */
	private String pattern;

	/** 日期字符串: 2016-10-12 10:12:33 */
	private String str;

	/** 从{@link #str}中获取到日期的分值，分值越高，时间越准确，range: [1-10] */
	private int score = 1;

	/** 日期:{@link #str}对应的 java.util.Date */
	private Date date;

	/** 描述 */
	private String msg;

	public MyDate() {
		super();
	}

	public boolean getIsDate() {
		return isDate;
	}

	public void setIsDate(boolean isDate) {
		this.isDate = isDate;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("isDate", isDate);
		json.put("pattern", pattern);
		json.put("str", str);
		json.put("score", score);

		String dateStr = null;
		if (null != date) {
			dateStr = DateUtil.dateToString(date);
		}
		json.put("date", dateStr);
		json.put("msg", msg);

		return json.toJSONString();
	}
}