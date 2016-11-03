package com.hewentian.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * <p>
 * <b>DateUtil</b> 是 日期格式工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年11月3日 下午4:12:06
 * @since JDK 1.7
 * 
 */
public class DateUtil {
	/**
	 * 将date转换成yyyy-MM-dd HH:mm:ss格式
	 * @date 2016年11月3日 下午4:14:39
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null) {
			return "1900-01-01 00:00:00";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}