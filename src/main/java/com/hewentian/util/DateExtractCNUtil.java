package com.hewentian.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p>
 * <b>DateExtractCNUtil</b> 是 {@linkplain com.hewentian.util.DateExtractUtil DateExtractUtil}子类，主要用于处理中文日期
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年6月12日 上午10:40:46
 * @since JDK 1.7
 * 
 */
public class DateExtractCNUtil extends DateExtractUtil {
	/** 中文数字 */
	private static String[] cn = "零,〇,一,二,两,三,四,五,六,七,八,九".split(",");

	/** 与{@linkplain #cn}对应的阿拉伯数字 */
	private static int[] an = new int[] { 0, 0, 1, 2,2, 3, 4, 5, 6, 7, 8, 9 };

	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/** 正则表达式 - 对应的日期格式 */
	private static Map<String, String> datePtnMap = new LinkedHashMap<String, String>();

	static {
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2})", "yyyy-MM-ddHH:mm:ss");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2}\\d{1,2}:\\d{1,2})", "yyyy-MM-ddHH:mm");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2}\\d{1,2})", "yyyy-MM-ddHH");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2})", "yyyy-MM-dd");

		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2})", "yyyyMMddHH:mm:ss");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2}\\d{1,2}:\\d{1,2})", "yyyyMMddHH:mm");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2}\\d{1,2})", "yyyyMMddHH");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2})", "yyyyMMdd");

		// 没有年的情况
		datePtnMap.put("(\\d{1,2}-\\d{1,2}\\d{1,2}:\\d{1,2}:\\d{1,2})", "MM-ddHH:mm:ss");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}\\d{1,2}:\\d{1,2})", "MM-ddHH:mm");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}\\d{1,2})", "MM-ddHH");
		datePtnMap.put("(\\d{1,2}-\\d{1,2})", "MM-dd");
	}

	@Override
	public Date getDate(String str, String datePtn) {
		if (CommonUtil.isBlank(str)) {
			System.out.println("要抽取的字符串不能为空.");
			return null;
		}

		Date date = null; // 最终要返回的日期
		String regex = "yyyy-MM-dd"; // 抽取的正则表达式
		Pattern p = null;
		Matcher m = null;

		// 将str转为半角
		str = CommonUtil.fullWidth2halfWidth(str);

		// 将繁体字转化为简体字
//		str = CommonUtil.chineseConvertorToS(str);

		// 如果有指定格式，就直接按指定格式来获取
		if (CommonUtil.isNotBlank(datePtn)) {
			datePtn = datePtn.trim();
			regex = datePtn;
			if (regex.contains("yyyy")) {
				regex = regex.replace("yyyy", "\\d{1,4}");
			} else if (regex.contains("yy")) {
				regex = regex.replace("yy", "\\d{1,2}");
			}
			regex = regex.replace("MM", "\\d{1,2}");
			regex = regex.replace("dd", "\\d{1,2}");
			regex = regex.replace("HH", "\\d{1,2}");
			regex = regex.replace("mm", "\\d{1,2}");
			regex = regex.replace("ss", "\\d{1,2}");

			p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			m = p.matcher(str);
			if (m.find()) {
				String dateStr = m.group();

				try {
					dateFormat = new SimpleDateFormat(datePtn);
					date = dateFormat.parse(dateStr);
				} catch (ParseException e) {
					date = null;
					// e.printStackTrace();
				}
			}
		}

		if (null != date) {
			return date;
		}

		// drop the empty
		str = str.replaceAll("\\s+", "");

		// 如果指定的格式出错或者没指定日期格式
		// 有年月的情况
		// 将 二〇一五年十二月廿六日，中的年月日转换为阿拉伯数字
		regex = "([零〇一二三四五六七八九\\d]{2,4})?年?([一二三四五六七八九十\\d]{1,2})月([初一二三四五六七八九十廿\\d]{1,2})日?";
		p = Pattern.compile(regex);
		m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String s = m.group();
			String year = m.group(1); // 有可能没有年，比如: 七月初六
			String month = m.group(2);
			String day = m.group(3);

			String year_ = null == year ? null : chinaNumToArabicNum(year, 1);
			String month_ = chinaNumToArabicNum(month, 2);
			String day_ = chinaNumToArabicNum(day, 3);

			// 这里不能直接用 String.replace方法，因为如果月和日相同就会出现问题
			// 如一九五三年七月廿七日，先按年月分段再用String.replace方法
			String s1 = null == year ? "" : s.substring(0, s.indexOf("年"));
			String s2 = s.substring(null == year ? 0 : s.indexOf("年"),
					s.indexOf("月"));
			String s3 = s.substring(s.indexOf("月"));

			s1 = null == year ? "" : s1.replace(year, year_);
			s2 = s2.replace(month, month_);
			s3 = s3.replace(day, day_);

			m.appendReplacement(sb, s1 + s2 + s3);
		}
		m.appendTail(sb);
		str = sb.toString();

		// 将年、月、日、.、\转为-
		// 1.处理公历日期标准格式，如：1950年6月25日13时45分12秒
		String str2 = str;
		str2 = str2.replaceAll("[年月./|]", "-");
		str2 = str2.replaceAll("[日秒A-Z]", "");
		str2 = str2.replaceAll("[时分時]", ":");
		str2 = str2.replaceAll("\\s+", "");

		for (Entry<String, String> entry : datePtnMap.entrySet()) {
			regex = entry.getKey();
			datePtn = entry.getValue();

			p = Pattern.compile(regex);
			m = p.matcher(str2);

			if (m.find()) {
				String dateStr = m.group();

				try {
					dateFormat = new SimpleDateFormat(datePtn);
					date = dateFormat.parse(dateStr);

					// 如果只有月和日，则默认为当前年
					if (datePtn.contains("yyyy") == false) {
						Calendar c = Calendar.getInstance();
						int currYear = c.get(Calendar.YEAR);
						c.setTime(date);
						c.set(Calendar.YEAR, currYear);
						date = c.getTime();
					}
				} catch (ParseException e) {
					date = null;
					// e.printStackTrace();
				}
			}

			if (null != date) {
				break;
			}
		}

		if (null != date) {
			return date;
		}

		if (str.contains("前") || str.contains("天")) {
			date = getAgo(str);
		}

		// 3.处理中文（农历）日期标准格式为：丙申年 甲午月 丙寅日
		// 暂未提供该方法, 以后需要时提供

		System.out.println(date);
		return date;
	}

	/**
	 * 将中文的年、月、日转为阿拉伯式的数字
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月13日 下午12:30:07
	 * @param s
	 *            中文的年、月、日
	 * @param type
	 *            1.年、2.月、3.日
	 * @return 阿拉伯式的数字
	 */
	public static String chinaNumToArabicNum(String s, int type) {
		int n = 0;
		if (type == 1) { // 年：直接替换即可
			s = getNumStr(s);
			if (s.length() == 2) { // 年份只有两位数字
				int cYear = Calendar.getInstance().get(Calendar.YEAR);
				n = Integer.valueOf(s);
				n += 2000;
				if (n > cYear) {
					n -= 100;
				}
				s = n + "";
			}
		} else if (type == 2) { // 月份
			if (s.length() == 2) {
				n = 10;
				s = s.substring(1);
				s = getNumStr(s);
				n += Integer.valueOf(s);
				s = n + "";
			} else {
				if ("十".equals(s)) {
					s = "10";
				} else {
					s = "0" + getNumStr(s);
				}
			}
		} else if (type == 3) { // 日
			if (s.startsWith("初") || s.length() == 1) {
				if (s.startsWith("初")) {
					s = s.substring(1);
				}

				if ("十".equals(s)) {
					s = "10";
				} else {
					s = getNumStr(s);
				}
			} else if ((s.startsWith("十") || s.startsWith("廿"))
					&& s.length() == 2) {
				n = s.startsWith("十") ? 10 : 20;
				s = s.substring(1);
				s = getNumStr(s);
				n += Integer.valueOf(s);
				s = n + "";
			} else if (s.startsWith("二") || s.startsWith("三")) {
				s = s.startsWith("二") ? "20" : "30";
			}
			
			if (s.length() == 1) {
				s = "0" + s;
			}
		}

		return s;
	}

	/**
	 * 将中文数字直接替换成阿拉伯数字
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月13日 下午12:29:16
	 * @param s
	 *            中文数字
	 * @return 阿拉伯数字
	 */
	public static String getNumStr(String s) {
		for (int i = 0; i < cn.length; i++) {
			s = s.replace(cn[i], an[i] + "");
		}

		return s;
	}
	
	/**
	 * 从含有 多少秒/分钟/小时/刻钟之前、昨天/前天/前一天/前两天的字符串中抽取日期
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月16日 下午4:02:55
	 * @param str 含有 多少秒/分钟/小时/刻钟之前、昨天/前天/前一天/前两天的字符串
	 * @return java.util.Date
	 */
	public Date getAgo(String str) {
		long ms = 0L; // 多少毫秒，由XX前、X天前等的数字算出

		// 如果是秒
		Pattern p = Pattern.compile("(\\d{1,2})秒前");
		Matcher m = p.matcher(str);
		if (m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L;
		}

		// 如果是分
		p = Pattern.compile("(\\d{1,2})分钟前");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60;
		}

		// 如果是刻钟
		p = Pattern.compile("(\\d{1,2}|一|二|三|四)刻钟前");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			s = getNumStr(s);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 15;
		}

		// 如果是时
		p = Pattern.compile("(\\d{1,2}|半)小时前");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			if ("半".equals(s)) {
				ms = 1000L * 60 * 30;
			} else {
				ms = Integer.valueOf(s);
				ms *= 1000L * 60 * 60;
			}
		}

		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		if (ms != 0) {
			long millsecond = date.getTime() - ms;
			date.setTime(millsecond);

			return date;
		}

		// 下面处理指定多少天前的，比较特殊，因为指定了 hh:mm
		// 如果是日:昨天、前天
		p = Pattern
				.compile("(昨|前)(\\d{1,2}|一|两|二|三|四|五|六|七|八|九)?天(\\d{1,2}):(\\d{1,2})");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			String nd = m.group(2);
			String hh = m.group(3);
			String mm = m.group(4);

			if ("昨".equals(s)) {
				nd = "1";
			} else {
				if (null == nd) {
					nd = "2";
				} else {
					nd = getNumStr(nd);
				}
			}

			int currDay = c.get(Calendar.DAY_OF_MONTH);
			c.set(Calendar.DAY_OF_MONTH, currDay - Integer.valueOf(nd));
			c.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hh));
			c.set(Calendar.MINUTE, Integer.valueOf(mm));
			date = c.getTime();
		}

		return date;
	}
}