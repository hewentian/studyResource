package com.hewentian.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <p>
 * <b>DateExtractENUtil</b> 是 {@linkplain com.hewentian.util.DateExtractUtil DateExtractUtil}子类，主要用于处理英文日期
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年6月13日 下午5:08:07
 * @since JDK 1.7
 * 
 */
public class DateExtractENUtil extends DateExtractUtil {
	/** 英文所有月份 */
	private static final String MONTH = "january|jan|february|feb|march|mar|april|apr|may|june|jun"
			+ "|july|jul|august|aug|september|sept|october|oct|november|nov|december|dec";

	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

	/** 正则表达式 - 对应的日期格式 */
	private static Map<String, String> datePtnMap = new LinkedHashMap<String, String>();

	static {
		// 有时区的:像 +0800 -0700---------------------------------------------------------
		// 一般格式，我们也适配它
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "yyyy-MM-dd HH:mm:ss Z");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "yyyy-MM-dd HH:mm Z");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2} \\d{1,2} (\\+|\\-)?\\d{1,2}00)", "yyyy-MM-dd HH Z");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "yyyy-MM-dd Z");

		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)",	"yyyyMMdd HH:mm:ss Z");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2} \\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "yyyyMMdd HH:mm Z");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2} \\d{1,2} (\\+|\\-)?\\d{1,2}00)", "yyyyMMdd HH Z");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "yyyyMMdd Z");

		// 下面是英式的日期写法：日 月,年
		// 月与年之间有,
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "dd MMM, yyyy HH:mm:ss Z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4} \\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "dd MMM, yyyy HH:mm Z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4} \\d{1,2} (\\+|\\-)?\\d{1,2}00)", "dd MMM, yyyy HH Z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4} (\\+|\\-)?\\d{1,2}00)", "dd MMM, yyyy Z");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "dd MMM yyyy HH:mm:ss Z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4} \\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "dd MMM yyyy HH:mm Z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4} \\d{1,2} (\\+|\\-)?\\d{1,2}00)", "dd MMM yyyy HH Z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4} (\\+|\\-)?\\d{1,2}00)", "dd MMM yyyy Z");

		// 下面是美式的日期写法：月 日,年
		// 月与年之间有,
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "MMM dd, yyyy HH:mm:ss Z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4} \\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "MMM dd, yyyy HH:mm Z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4} \\d{1,2} (\\+|\\-)?\\d{1,2}00)", "MMM dd, yyyy HH Z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4} (\\+|\\-)?\\d{1,2}00)", "MMM dd, yyyy Z");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "MMM dd yyyy HH:mm:ss Z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4} \\d{1,2}:\\d{1,2} (\\+|\\-)?\\d{1,2}00)", "MMM dd yyyy HH:mm Z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4} \\d{1,2} (\\+|\\-)?\\d{1,2}00)", "MMM dd yyyy HH Z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4} (\\+|\\-)?\\d{1,2}00)", "MMM dd yyyy Z");

		// 只有月和日
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? (\\+|\\-)?\\d{1,2}00)", "MMM dd Z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") (\\+|\\-)?\\d{1,2}00)", "dd MMM Z");
		
		// 下面是没有时区的 ---------------------------------------------------------
		// 一般格式，我们也适配它
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})", "yyyy-MM-dd HH:mm:ss");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2})", "yyyy-MM-dd HH:mm");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2} \\d{1,2})", "yyyy-MM-dd HH");
		datePtnMap.put("(\\d{1,4}-\\d{1,2}-\\d{1,2})", "yyyy-MM-dd");

		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})",	"yyyyMMdd HH:mm:ss");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2} \\d{1,2}:\\d{1,2})", "yyyyMMdd HH:mm");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2} \\d{1,2})", "yyyyMMdd HH");
		datePtnMap.put("(\\d{1,2}:\\d{1,2} (am|pm) \\d{1,2}-\\d{1,2}-\\d{1,4})", "h:mm aa MM-dd-yyyy");
		datePtnMap.put("(\\d{1,4}\\d{1,2}\\d{1,2})", "yyyyMMdd");

		// 下面是英式的日期写法：日 月,年
		// 月与年之间有,
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "dd MMM, yyyy HH:mm:ss");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4} \\d{1,2}:\\d{1,2})", "dd MMM, yyyy HH:mm");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4} \\d{1,2})", "dd MMM, yyyy HH");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{1,4})", "dd MMM, yyyy");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "dd MMM yyyy HH:mm:ss");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4} \\d{1,2}:\\d{1,2})", "dd MMM yyyy HH:mm");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4} \\d{1,2})", "dd MMM yyyy HH");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{1,4})", "dd MMM yyyy");

		// 下面是美式的日期写法：月 日,年
		// 月与年之间有,
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "MMM dd, yyyy HH:mm:ss");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4} \\d{1,2}:\\d{1,2})", "MMM dd, yyyy HH:mm");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4} \\d{1,2})", "MMM dd, yyyy HH");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,4})", "MMM dd, yyyy");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "MMM dd yyyy HH:mm:ss");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4} \\d{1,2}:\\d{1,2})", "MMM dd yyyy HH:mm");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4} \\d{1,2})", "MMM dd yyyy HH");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{1,4})", "MMM dd yyyy");

		// 只有月和日
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?)", "MMM dd");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "))", "dd MMM");
	}

	/**
	 * 对方法 {@linkplain #getDate(String, String)} 的重载，增加一个参数：时区
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月12日 上午10:43:33
	 * @param str 存在日期的字符串, 不可为空
	 * @param datePtn 文中的日期格式，如：yyyy年MM月dd日 HH时mm分ss秒, 可为空
	 * @param timeZone 时区值，为目标网站所在的时区。东半球为正，西半球为负，如中国为：8，美国为-7
	 * @return 文中的日期，格式为：java.util.Date
	 */
	public Date getDate(String str, String datePtn, int timeZone) {
		if (CommonUtil.isBlank(str)) {
			System.out.println("要抽取的字符串不能为空.");
			return null;
		}

		Date date = null; // 最终要返回的日期
		String regex = ""; // 抽取的正则表达式
		Pattern p = null;
		Matcher m = null;

		// 将str转为半角、小写
		str = CommonUtil.fullWidth2halfWidth(str);
		str = str.toLowerCase();
		str = englishNumToArabicNum(str);

		// 先将str变成比较标准的日期格式，如：dd MMMM, yyyy
		// 在,后面加上一个空格（如果有,）
		p = Pattern.compile(",");
		m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, ", ");
		}
		m.appendTail(sb);
		str = sb.toString();

		// 去掉月份中缩写点号
		p = Pattern.compile("jan\\.|feb\\.|mar\\.|apr\\.|jun\\.|jul\\.|aug\\.|sept\\.|oct\\.|nov\\.|dec\\.");
		m = p.matcher(str);
		sb = new StringBuffer();
		while (m.find()) {
			String s = m.group();
			s = s.replace(".", "");
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		str = sb.toString();

		// 替换./|为-，并将str中的多个空格替换为一个
		str = str.replaceAll("[./|]", "-").replaceAll("\\s+", " ");

		// 如果有指定格式，就直接按指定格式来获取
		if (CommonUtil.isNotBlank(datePtn)) {
			datePtn = datePtn.trim().replace(".", "");
			regex = datePtn;
			if (regex.contains("yyyy")) {
				regex = regex.replace("yyyy", "\\d{1,4}");
			} else if (regex.contains("yy")) {
				regex = regex.replace("yy", "\\d{1,2}");
			}
			if (regex.contains("MMMM")) {
				regex = regex.replace("MMMM", "(" + MONTH + ")");
			} else if (regex.contains("MMM")) {
				regex = regex.replace("MMM", "(" + MONTH + ")");
			} else if (regex.contains("MM")) {
				regex = regex.replace("MM", "\\d{1,2}");
			}
			regex = regex.replace("dd", "\\d{1,2}(st|nd|rd|th)?");
			regex = regex.replace("HH", "\\d{1,2}");
			regex = regex.replace("mm", "\\d{1,2}");
			regex = regex.replace("ss", "\\d{1,2}");

			p = Pattern.compile(regex);
			m = p.matcher(str);
			if (m.find()) {
				String dateStr = m.group();
				// drop the st,nd,rd,th
				dateStr = dateStr.replace("st", "").replace("nd", "")
						.replace("rd", "").replace("th", "");

				try {
					dateFormat = new SimpleDateFormat(datePtn, Locale.ENGLISH);
					date = dateFormat.parse(dateStr);
					
					if (datePtn.contains("Z") == false) {
						date = handleTimeZone(date, timeZone);
					}
				} catch (ParseException e) {
					date = null;
					e.printStackTrace();
				}
			}
		}

		if (null != date) {
			return date;
		}

		// 如果指定的格式出错或者没指定日期格式
		for (Entry<String, String> entry : datePtnMap.entrySet()) {
			regex = entry.getKey();
			datePtn = entry.getValue();

			p = Pattern.compile(regex);
			m = p.matcher(str);
			if (m.find()) {
				String dateStr = m.group();
				// drop the st,nd,rd,th
				dateStr = dateStr.replace("st", "").replace("nd", "")
						.replace("rd", "").replace("th", "");

				try {
					dateFormat = new SimpleDateFormat(datePtn, Locale.ENGLISH);
					date = dateFormat.parse(dateStr);

					// 如果只有月和日，则默认为当前年
					if ("MMM dd".equals(datePtn) || "dd MMM".equals(datePtn)
							|| "MMM dd Z".equals(datePtn)
							|| "dd MMM Z".equals(datePtn)) {
						Calendar c = Calendar.getInstance();
						int currYear = c.get(Calendar.YEAR);
						c.setTime(date);
						c.set(Calendar.YEAR, currYear);
						date = c.getTime();
					}
					
					if (datePtn.contains("Z") == false) {
						date = handleTimeZone(date, timeZone);
					}
				} catch (ParseException e) {
					date = null;
//					e.printStackTrace();
				}
			}

			if (null != date) {
				break;
			}
		}
		
		if (null != date) {
			return date;
		}

		if (str.contains("ago")) {
			date = getAgo(str);
			date = handleTimeZone(date, timeZone);
		}
		
//		System.out.println(date);
		return date;
	}

	/**
	 * 请使用 {@linkplain #getDate(String, String, int)} 方法
	 */
	@Override
	@Deprecated
	public Date getDate(String str, String datePtn) {
		return null;
	}
	
	/**
	 * 处理时区问题
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月17日 下午5:16:46
	 * @param date 日期
	 * @param timeZone 时区值，为目标网站所在的时区。东半球为正，西半球为负，如中国为：8，美国为-7
	 * @return
	 */
	public Date handleTimeZone(Date date, int timeZone) {
		if (null == date) {
			return null;
		}
		
		Calendar c = Calendar.getInstance();
		int currTimeZone = c.getTimeZone().getRawOffset() / 3600000;

		// 处理时差		
		c.setTime(date);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		c.set(Calendar.HOUR_OF_DAY, hour + currTimeZone - timeZone);
		date = c.getTime();

		return date;
	}

	/**
	 * 从含有age的字符串中抽取日期
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月14日 下午5:50:19
	 * @param str 有age的字符串
	 * @return java.util.Date
	 */
	public Date getAgo(String str) {
		long ms = 0L; // 多少毫秒，由age之前的数字算出

		// 如果是秒
		Pattern p = Pattern.compile("(\\d{1,2}) second(s)? ago");
		Matcher m = p.matcher(str);
		if (m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L;
		}

		// 如果是分
		p = Pattern.compile("(\\d{1,2}) minute(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60;
		}

		// 如果是时
		p = Pattern.compile("(\\d{1,2}) hour(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 60;
		}
		
		// 如果是日
		p = Pattern.compile("(\\d{1,2}) day(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 60 * 24;
		}
		
		// 如果是week
		p = Pattern.compile("(\\d{1,2}) week(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 60 * 24 * 7;
		}
		
		// 如果是month
		p = Pattern.compile("(\\d{1,2}) month(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 60 * 24 * 30;
		}
		
		if (ms == 0) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		long millsecond = date.getTime() - ms;
		date.setTime(millsecond);

		return date;
	}

	/**
	 * 将英式数字one, two, three, 等转为阿拉伯数字
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年6月15日 上午10:24:06
	 * @param s
	 * @return 阿拉伯式的数字
	 */
	public static String englishNumToArabicNum(String s) {
		s = s.replaceAll("\\s+(a|an|one)\\s+", " 1 ");
		s = s.replaceAll("\\s+(two)\\s+", " 2 ");
		s = s.replaceAll("\\s+(three)\\s+", " 3 ");
		s = s.replaceAll("\\s+(four)\\s+", " 4 ");
		s = s.replaceAll("\\s+(five)\\s+", " 5 ");
		s = s.replaceAll("\\s+(six)\\s+", " 6 ");
		s = s.replaceAll("\\s+(seven)\\s+", " 7 ");
		s = s.replaceAll("\\s+(eight)\\s+", " 8 ");
		s = s.replaceAll("\\s+(nine)\\s+", " 9 ");
		s = s.replaceAll("\\s+(ten)\\s+", " 10 ");
		s = s.replaceAll("\\s+(eleven)\\s+", " 11 ");
		s = s.replaceAll("\\s+(twelve)\\s+", " 12 ");
		s = s.replaceAll("\\s+(thirteen)\\s+", " 13 ");
		s = s.replaceAll("\\s+(fourteen)\\s+", " 14 ");
		s = s.replaceAll("\\s+(fifteen)\\s+", " 15 ");
		s = s.replaceAll("\\s+(sixteen)\\s+", " 16 ");
		s = s.replaceAll("\\s+(seventeen)\\s+", " 17 ");
		s = s.replaceAll("\\s+(eighteen)\\s+", " 18 ");
		s = s.replaceAll("\\s+(nineteen)\\s+", " 19 ");

		return s;
	}
}