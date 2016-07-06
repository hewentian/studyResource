package com.hewentian.util;

/**
 * 
 * <p>
 * <b>CommonUtil</b> 是 常用工具类，一些常用的，通用的方法可以写在这里
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年6月15日 下午4:17:02
 * @since JDK 1.7
 *
 */
public class CommonUtil {
	/**
	 * 全角字符串转换为半角字符串 <br />
	 * 全角字符与半角字符的关系 <br />
	 * 可以通过下面的程序看看Java中所有字符以及对应编码的值 <br />
     * for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; ++i) { <br />
     *		System.out.println(i + " " + (char)i); <br />
     *  } <br />
     *  
     *  从输出可以看到 <br />
     *	1.半角字符是从33开始到126结束 <br />
     *	2.与半角字符对应的全角字符是从65281开始到65374结束 <br />
     *	3.其中半角的空格是32.对应的全角空格是12288 <br />
     *	4.半角和全角的关系很明显,除空格外的字符偏移量是65248(65281-33 = 65248)
     *  
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月15日 下午4:19:20
	 * @param fullWidthStr 非空的全角字符串
	 * @return 半角字符串
	 */
	public static String fullWidth2halfWidth(String fullWidthStr) {
		if (null == fullWidthStr || fullWidthStr.length() <= 0) {
			return "";
		}

		char[] charArray = fullWidthStr.toCharArray();

		// 对全角字符转换的char数组遍历
		for (int i = 0; i < charArray.length; ++i) {
			int charIntValue = charArray[i];

			// 如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
			if (charIntValue >= 65281 && charIntValue <= 65374) {
				charArray[i] = (char) (charIntValue - 65248);
			} else if (charIntValue == 12288) {
				charArray[i] = (char) 32;
			}
		}

		return new String(charArray);
	}

	public static boolean isEmpty(final CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	public static boolean isNotEmpty(final CharSequence cs) {
		return !isEmpty(cs);
	}

	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(final CharSequence cs) {
		return !isBlank(cs);
	}

	public static void main(String[] args) {
		System.out.println(fullWidth2halfWidth("a９；ａｂｃａｂ")); // output: a9;abcab

	}
}