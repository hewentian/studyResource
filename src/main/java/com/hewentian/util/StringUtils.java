package com.hewentian.util;

/**
 * 
 * <p>
 * <b>StringUtils</b> 是 字符串工具类, 继承 org.apache.commons.lang3.StringUtils,
 * 在其基础上增加额外的功能
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年7月3日 下午7:45:18
 * @since JDK 1.8
 *
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	/**
	 * 将字符串的每个字符转为 16 进制表示
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2017年7月3日 下午5:41:07
	 * @param str
	 *            字符串，not empty
	 * @return str的 16 进制表示
	 */
	public static String toHexString(String str) {
		if (isEmpty(str)) {
			return null;
		}

		StringBuilder hexStr = new StringBuilder();
		char[] charArray = str.toCharArray();
		for (char c : charArray) {
			hexStr.append(Integer.toHexString(c));
		}

		return hexStr.toString();
	}
}
