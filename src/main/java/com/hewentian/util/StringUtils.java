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
	 * 判断一个字符是否是中文字符
	 * 
	 * @date 2017年8月3日 下午3:59:35
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		if ((c >= 0x4e00) && (c <= 0x9fbb)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断一个字符是否是英文字符
	 * 
	 * @date 2017年8月3日 下午4:01:54
	 * @param c
	 * @return
	 */
	public static boolean isEnglish(char c) {
		if ((c >= 0x41 && c <= 0x5a) || (c >= 0x61 && c <= 0x7a)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断字符串中是否包含中文
	 * 
	 * @date 2017年8月3日 上午10:47:39
	 * @param str
	 * @return true: contains chinese; false: not contains chinese
	 */
	public static boolean containsChinese(String str) {
		char[] c = str.toCharArray();
		for (int i = 0, len = c.length; i < len; i++) {
			if (isChinese(c[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断字符串中是否包含英文
	 * 
	 * @date 2017年8月3日 上午11:44:19
	 * @param str
	 * @return true: contains english; false: not contains english
	 */
	public static boolean containsEnglish(String str) {
		char[] c = str.toCharArray();
		for (int i = 0, len = c.length; i < len; i++) {
			if (isEnglish(c[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 将字节数组转换成16进制的字符串
	 * 
	 * @date 2017年8月23日 下午3:39:57
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder sb = new StringBuilder();
		String stmp = "";

		for (int i = 0; i < b.length; i++) {
			stmp = (Integer.toHexString(b[i] & 0xFF));
			if (stmp.length() == 1) {
				sb.append("0");
			}

			sb.append(stmp);
		}

		return sb.toString().toLowerCase();
	}

	/**
	 * 将16进制的字符串转换成字节数组
	 * 
	 * @date 2017年8月23日 下午3:40:25
	 * @param hexString
	 * @return
	 */
	public static byte[] hex2byte(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}

		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];

		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (toByte(hexChars[pos]) << 4 | toByte(hexChars[pos + 1]));
		}

		return d;
	}

	public static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
}
