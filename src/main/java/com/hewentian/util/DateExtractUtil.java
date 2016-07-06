package com.hewentian.util;

import java.util.Date;

/**
 * 
 * <p>
 * <b>DateExtractUtil</b> 是 日期抽取工具（抽象）类，主要用于从输入的一段String中抽取出日期
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年6月12日 上午10:40:46
 * 
 */
public abstract class DateExtractUtil {
	/**
	 * 从指定的字符串中按指定的日期格式抽取日期，如果有多个日期，则只会获取第一个日期
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月12日 上午10:43:33
	 * @param str 存在日期的字符串, 不可为空
	 * @param datePtn 文中的日期格式，如：yyyy年MM月dd日 HH时mm分ss秒, 可为空
	 * @return 文中的日期，格式为：java.util.Date
	 */
	public abstract Date getDate(String str, String datePtn);
}