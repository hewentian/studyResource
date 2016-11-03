package com.hewentian.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hewentian.entity.MyDate;

/**
 * 
 * <p>
 * <b>DateENUtil</b> 是 日期处理类，主要用于处理英文日期
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月24日 下午5:08:07
 * @since JDK 1.7
 * 
 */
public class DateENUtil {
	/** 英文所有月份 */
	private static final String MONTH = "january|jan|february|feb|march|mar|april|apr|may|june|jun" + "|july|jul|august|aug|september|sept|sep|october|oct|november|nov|december|dec";
	
	private static final String TIMEZONE = "fwt|acst|awst|cast|wetdst|hmt|kst|wat|akdt|pdt|nst|mvt|cat|gst|bst|idlw|wet|cct|ist|est|cdt|nzst|mut|hst|ligt|east|nt|act|irt|sast|bdst|adt|iot|aft|cadt|fnt|sat|pst|mst|edt|eet|bt|hdt|jst|almst|eat|cetdst|zulu|met|dnt|jt|nzt|eetdst|sadt|ast|cest|akst|swt|idle|yst|fnst|nor|sst|ahst|nzdt|aest|sct|cst|acsst|mart|brst|mht|mmt|brt|ret|almt|tft|aesst|cxt|metdst|cet|ut|acst|ndt|klt|fst|wst|mdt|gmt|utc|mest|hkt";

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

	/** 正则表达式 - 对应的日期格式 */
	private static Map<String, String> datePtnMap = new LinkedHashMap<String, String>();

	/** 正则表达式MAP, key=regex, value=pattern */
	private static Map<String, Pattern> regexPtnMap = new HashMap<String, Pattern>();

	/** 日期格式MAP, key=datePattern, value=SimpleDateFormat */
	private static Map<String, SimpleDateFormat> dateFormatMap = new HashMap<String, SimpleDateFormat>();

	static {
		// 有时区的:像 IST
		// IST---------------------------------------------------------
		// 一般格式，我们也适配它
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{1,2}:\\d{1,2}-\\d{3}(\\+|-)\\d{2}:\\d{2})", "yyyy-MM-dd't'HH:mm:ss-SSSZ");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{1,2}:\\d{1,2}(\\+|-)\\d{4})", "yyyy-MM-dd't'HH:mm:ssZ");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{1,2}:\\d{1,2}(\\+|-)\\d{2}:\\d{2})", "yyyy-MM-dd't'HH:mm:ssZ");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{1,2}:\\d{1,2}-\\d{3})", "yyyy-MM-dd't'HH:mm:ss-SSS");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{1,2}:\\d{1,2}(" + TIMEZONE +"))", "yyyy-MM-dd't'HH:mm:ssz");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{1,2}:\\d{1,2})", "yyyy-MM-dd't'HH:mm:ss");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2}t\\d{1,2}:\\d{1,2})", "yyyy-MM-dd't'HH:mm");
		
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "yyyy-MM-dd HH:mm:ss z");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "yyyy-MM-dd HH:mm z");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2} (" + TIMEZONE +"))", "yyyy-MM-dd HH z");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} (" + TIMEZONE +"))", "yyyy-MM-dd z");

		// 下面是英式的日期写法：日 月,年
		// 月与年之间有,
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "dd MMM, yyyy HH:mm:ss z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4} \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "dd MMM, yyyy HH:mm z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4} \\d{1,2} (" + TIMEZONE +"))", "dd MMM, yyyy HH z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4} (" + TIMEZONE +"))", "dd MMM, yyyy z");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "dd MMM yyyy HH:mm:ss z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4} \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "dd MMM yyyy HH:mm z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4}, \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "dd MMM yyyy, HH:mm z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4} \\d{1,2} (" + TIMEZONE +"))", "dd MMM yyyy HH z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4} (" + TIMEZONE +"))", "dd MMM yyyy z");

		// 下面是美式的日期写法：月 日,年
		// 月与年之间有,
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "MMM dd, yyyy HH:mm:ss z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4}, \\d{1,2}:\\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "MMM dd, yyyy, HH:mm:ss z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} \\d{1,2}:\\d{1,2} (am|pm) (" + TIMEZONE +"))", "MMM dd, yyyy hh:mm a z");
		datePtnMap.put("(\\d{1,2}:\\d{1,2} (am|pm) (" + TIMEZONE +") (" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4})", "hh:mm a z MMM dd, yyyy");
		datePtnMap.put("(\\d{1,2}:\\d{1,2} (" + TIMEZONE +"), (" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4})", "HH:mm z, MMM dd, yyyy");
		datePtnMap.put("(\\(\\d{1,2}\\d{1,2} (" + TIMEZONE +")\\) (" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4})", "(HHmm z) MMM dd, yyyy"); // especial for CNN 
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "MMM dd, yyyy HH:mm z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4}, \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "MMM dd, yyyy, HH:mm z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} \\d{1,2} (" + TIMEZONE +"))", "MMM dd, yyyy HH z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} (" + TIMEZONE +"))", "MMM dd, yyyy z");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "MMM dd yyyy HH:mm:ss z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4}, \\d{1,2}:\\d{1,2} (am|pm) (" + TIMEZONE +"))", "MMM dd yyyy, hh:mm a z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4}, \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "MMM dd yyyy, HH:mm z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4} \\d{1,2}:\\d{1,2} (" + TIMEZONE +"))", "MMM dd yyyy HH:mm z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4} \\d{1,2} (" + TIMEZONE +"))", "MMM dd yyyy HH z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4} (" + TIMEZONE +"))", "MMM dd yyyy z");

		// 只有月和日
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? (" + TIMEZONE +"))", "MMM dd z");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") (" + TIMEZONE +"))", "dd MMM z");

		// 下面是没有时区的 ---------------------------------------------------------
		// 一般格式，我们也适配它
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} (am|pm) \\d{1,2}:\\d{1,2}:\\d{1,2})", "yyyy-MM-dd a hh:mm:ss");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} (am|pm) \\d{1,2}:\\d{1,2})", "yyyy-MM-dd a hh:mm");
		
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2})", "yyyy-MM-dd HH:mm:ss");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2})", "yyyy-MM-dd HH:mm");
		datePtnMap.put("(\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2})", "yyyy-MM-dd HH");
		datePtnMap.put("(\\d{1,2}:\\d{1,2} (am|pm) \\d{1,2}-\\d{1,2}-\\d{4})", "h:mm aa MM-dd-yyyy");
		datePtnMap.put("(\\d{4}-(0?[1-9]|1[0-2])-(3[0-1]|[1-2][0-9]|0?[1-9]))", "yyyy-MM-dd");

		// 下面是英式的日期写法：日 月,年
		// 月与年之间有,
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "dd MMM, yyyy HH:mm:ss");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4} \\d{1,2}:\\d{1,2})", "dd MMM, yyyy HH:mm");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4} \\d{1,2})", "dd MMM, yyyy HH");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "), \\d{4})", "dd MMM, yyyy");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)?-(" + MONTH + ")-\\d{2})", "dd-MMM-yy");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "dd MMM yyyy HH:mm:ss");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4} \\d{1,2}:\\d{1,2})", "dd MMM yyyy HH:mm");
		datePtnMap.put("(\\d{1,2}:\\d{1,2}, \\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4})", "HH:mm, dd MMM yyyy");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4} \\d{1,2})", "dd MMM yyyy HH");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + ") \\d{4})", "dd MMM yyyy");

		// 下面是美式的日期写法：月 日,年
		// 月与年之间有,
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} at \\d{1,2}:\\d{1,2} (am|pm))", "MMM dd, yyyy 'at' h:mm aa");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{1,2}:\\d{1,2} (am|pm))", "MMM dd, h:mm aa");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "MMM dd, yyyy HH:mm:ss");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4}, \\d{1,2}:\\d{1,2} (am|pm) (" + TIMEZONE +"))", "MMM dd, yyyy, hh:mm a z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4}, \\d{1,2}:\\d{1,2} (am|pm))", "MMM dd, yyyy, hh:mm a");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4}\\d{1,2}:\\d{1,2} (am|pm))", "MMM dd, yyyyhh:mm a");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4}, \\d{1,2}-\\d{1,2} (am|pm) (" + TIMEZONE +"))", "MMM dd, yyyy, hh-mm a z");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4}, \\d{1,2}-\\d{1,2} (am|pm))", "MMM dd, yyyy, hh-mm a");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} \\d{1,2}:\\d{1,2})", "MMM dd, yyyy HH:mm");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4} \\d{1,2})", "MMM dd, yyyy HH");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?, \\d{4})", "MMM dd, yyyy");
		
		// MM-dd-yyyy
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{4}, \\d{1,2}:\\d{1,2} (am|pm))", "MM-dd-yyyy, h:mm aa");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{2,4} \\d{1,2}:\\d{1,2} (am|pm) (" + TIMEZONE +"))", "MM-dd-yyyy h:mm aa z");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2} (am|pm))", "MM-dd-yyyy h:mm aa");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{2}, \\d{1,2}:\\d{1,2} (am|pm))", "MM-dd-yy, h:mm aa");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{2} \\d{1,2}:\\d{1,2} (am|pm))", "MM-dd-yy h:mm aa");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} (am|pm) (" + TIMEZONE +"))", "MM-dd-yyyy hh:mm:ss a z");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "MM-dd-yyyy HH:mm:ss");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2}:\\d{1,2})", "MM-dd-yyyy HH:mm");
		datePtnMap.put("(\\d{1,2}-\\d{1,2}-\\d{4} \\d{1,2})", "MM-dd-yyyy HH");
		datePtnMap.put("(\\d{1,2}:\\d{1,2} \\d{1,2}-\\d{1,2}-\\d{4})", "hh:mm dd-MM-yyyy");
		datePtnMap.put("((0?[1-9]|1[0-2])-(3[0-1]|[1-2][0-9]|0?[1-9])-\\d{4})", "MM-dd-yyyy");
		datePtnMap.put("((0?[1-9]|1[0-2])-(3[0-1]|[1-2][0-9]|0?[1-9])-\\d{2})", "MM-dd-yy");

		// 月与年之间无, 这种形式不正规，但是，我们要能适应它
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2})", "MMM dd yyyy HH:mm:ss");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4} \\d{1,2}:\\d{1,2})", "MMM dd yyyy HH:mm");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4} \\d{1,2})", "MMM dd yyyy HH");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)? \\d{4})", "MMM dd yyyy");

		// 只有月和日
		datePtnMap.put("((" + MONTH + ") \\d{4})", "MMM yyyy");
		datePtnMap.put("((" + MONTH + ") \\d{1,2}(st|nd|rd|th)?)", "MMM dd");
		datePtnMap.put("(\\d{1,2}(st|nd|rd|th)? (" + MONTH + "))", "dd MMM");

		// -------------------- add all regexPtnMap begin ----------------------
		regexPtnMap.put(",", Pattern.compile(","));
		regexPtnMap.put("jan\\.|feb\\.|mar\\.|apr\\.|jun\\.|jul\\.|aug\\.|sep\\.|oct\\.|nov\\.|dec\\.",
				Pattern.compile("jan\\.|feb\\.|mar\\.|apr\\.|jun\\.|jul\\.|aug\\.|sep\\.|oct\\.|nov\\.|dec\\."));
		regexPtnMap.put("(\\d{1,2}) second(s)? ago", Pattern.compile("(\\d{1,2}) second(s)? ago"));
		regexPtnMap.put("(\\d{1,2}) minute(s)? ago", Pattern.compile("(\\d{1,2}) minute(s)? ago"));
		regexPtnMap.put("(\\d{1,2}) hour(s)? ago", Pattern.compile("(\\d{1,2}) hour(s)? ago"));
		regexPtnMap.put("(\\d{1,2}) day(s)? ago", Pattern.compile("(\\d{1,2}) day(s)? ago"));
		regexPtnMap.put("(\\d{1,2}) week(s)? ago", Pattern.compile("(\\d{1,2}) week(s)? ago"));
		regexPtnMap.put("(\\d{1,2}) month(s)? ago", Pattern.compile("(\\d{1,2}) month(s)? ago"));

		for (String regex : datePtnMap.keySet()) {
			regexPtnMap.put(regex, Pattern.compile(regex));
		}
		
		// add update method' ptn
		regexPtnMap.put("(yesterday)?\\s+(\\d{1,2}:\\d{1,2}\\s+(am|pm))", Pattern.compile("(yesterday)?\\s+(\\d{1,2}:\\d{1,2}\\s+(am|pm))"));
		regexPtnMap.put("(yesterday)?\\s+(\\d{1,2}:\\d{1,2})", Pattern.compile("(yesterday)?\\s+(\\d{1,2}:\\d{1,2})"));
		// -------------------- add all regexPtnMap end ------------------------
	
		
		// -------------------- add all dateFormatMap begin --------------------
		for (String datePtn : datePtnMap.values()) {
			dateFormatMap.put(datePtn, new SimpleDateFormat(datePtn, Locale.ENGLISH));
		}
		
		// add update method' ptn
		dateFormatMap.put("hh:mm a", new SimpleDateFormat("hh:mm a", Locale.ENGLISH));
		dateFormatMap.put("hh:mm", new SimpleDateFormat("hh:mm", Locale.ENGLISH));
		// -------------------- add all dateFormatMap end ----------------------
	}

	/**
	 * 从输入的最符串，按指定的日期格式、时区抽取时间
	 * 
	 * @date 2016年6月12日 上午10:43:33
	 * @param str
	 *            存在日期的字符串, 不可为空
	 * @param datePtn
	 *            文中的日期格式，如：yyyy年MM月dd日 HH时mm分ss秒, 可为空
	 * @param timeZone
	 *            时区值，为目标网站所在的时区。东半球为正，西半球为负，如中国为：8，美国为-7
	 * @return 文中的日期，格式为：{@linkplain com.hewentian.entity.MyDate MyDate}
	 * @throws Exception 
	 */
	public MyDate getDate(String str, String datePtn, int timeZone) throws Exception {
		String strOri = str;
		if (CommonUtil.isBlank(str)) {
			throw new Exception("要抽取的字符串不能为空.");
		}

		MyDate myDate = null;// 最终要返回的日期
		String regex = ""; // 抽取的正则表达式
		Pattern p = null;
		Matcher m = null;

		// 将str转为半角、小写
		str = CommonUtil.fullWidth2halfWidth(str);
		str = str.toLowerCase();
		str = englishNumToArabicNum(str);

		// 先将str变成比较标准的日期格式，如：dd MMMM, yyyy
		// 在,后面加上一个空格（如果有,）
		p = regexPtnMap.get(",");
		m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, ", ");
		}
		m.appendTail(sb);
		str = sb.toString();

		// sept.转成sep., 因为sept.是不正规的写法
		str = str.replace("sept.", "sep.");

		// 去掉月份中缩写点号
		p = regexPtnMap.get("jan\\.|feb\\.|mar\\.|apr\\.|jun\\.|jul\\.|aug\\.|sep\\.|oct\\.|nov\\.|dec\\.");
		m = p.matcher(str);
		sb = new StringBuffer();
		while (m.find()) {
			String s = m.group();
			s = s.replace(".", "");
			m.appendReplacement(sb, s);
		}
		m.appendTail(sb);
		str = sb.toString();

		// 有些英文网站可能会用到中文日期格式，这里作一个简单的替换
		str = str.replaceAll("(上午)|(a\\.m\\.)|(am)", " am ");
		str = str.replaceAll("(下午)|(p\\.m\\.)|(pm)", " pm ");
		
		// 替换./|为-，并将str中的多个空格替换为一个
		str = str.replaceAll("[./|]", "-").replaceAll("\\s+", " ");
		str = CommonUtil.transformTimeZone(str);

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

			p = regexPtnMap.get(regex);
			if (null == p) {
				p = Pattern.compile(regex);
				regexPtnMap.put(regex, p);
			}
			m = p.matcher(str);
			if (m.find()) {
				String dateStr = m.group();
				// drop the st,nd,rd,th
				dateStr = dateStr.replace("st", "").replace("nd", "").replace("rd", "").replace("th", "");

				try {
					dateFormat = dateFormatMap.get(datePtn);
					if (null == dateFormat) {
						SimpleDateFormat sdf = new SimpleDateFormat(datePtn, Locale.ENGLISH);
						dateFormat = sdf;
						dateFormatMap.put(datePtn, sdf);
					}
					
					Date date = dateFormat.parse(dateStr);

					if (datePtn.contains("z") == false && (datePtn.contains("H") || datePtn.contains("h") || datePtn.contains("mm") || datePtn.contains("ss"))) {
						date = CommonUtil.handleTimeZone(date, timeZone);
					}

					myDate = new MyDate();
					myDate.setIsDate(true);
					myDate.setDate(date);
					myDate.setStr(strOri);
					myDate.setPattern(datePtn);
					int score = CommonUtil.getScoreEN(strOri, datePtn);
					myDate.setScore(score);
				} catch (ParseException e) {
					myDate = null;
					e.printStackTrace();
				}
			}
		}

		if (null != myDate) {
			return myDate;
		}

		// 如果指定的格式出错或者没指定日期格式
		for (Entry<String, String> entry : datePtnMap.entrySet()) {
			regex = entry.getKey();
			datePtn = entry.getValue();

			p = regexPtnMap.get(regex);
			m = p.matcher(str);
			if (m.find()) {
				String dateStr = m.group();
				// drop the st,nd,rd,th
				dateStr = dropStNdRdTh(dateStr);

				// 处理一些特殊情况：yyyy-MM-dd't'HH:mm:ssZ， 2016-10-26T17:41:48+01:00
				if ("yyyy-MM-dd't'HH:mm:ssZ".equals(datePtn) || "yyyy-MM-dd't'HH:mm:ss-SSSZ".equals(datePtn)) {
					char c = dateStr.charAt(dateStr.length() - 3);
					if (':' == c) {
						dateStr = dateStr.substring(0, dateStr.length() - 3) + dateStr.substring(dateStr.length() - 2);
					}
				}

				try {
					dateFormat = dateFormatMap.get(datePtn);
					Date date = dateFormat.parse(dateStr);

					// 如果只有月和日，则默认为当前年
					if ("MMM dd".equals(datePtn) || "dd MMM".equals(datePtn) || "MMM dd z".equals(datePtn) || "dd MMM z".equals(datePtn)) {
						Calendar c = Calendar.getInstance();
						int currYear = c.get(Calendar.YEAR);
						c.setTime(date);
						c.set(Calendar.YEAR, currYear);
						date = c.getTime();
					}

					if (datePtn.contains("z") == false && (datePtn.contains("H") || datePtn.contains("h") || datePtn.contains("mm") || datePtn.contains("ss"))) {
						date = CommonUtil.handleTimeZone(date, timeZone);
					}

					myDate = new MyDate();
					myDate.setIsDate(true);
					myDate.setDate(date);
					myDate.setStr(strOri);
					myDate.setPattern(datePtn);
					int score = CommonUtil.getScoreEN(strOri, datePtn);
					myDate.setScore(score);
				} catch (ParseException e) {
					myDate = null;
					// e.printStackTrace();
				}
			}

			if (null != myDate) {
				break;
			}
		}

		if (null != myDate) {
			return myDate;
		}

		if (str.contains("ago")) {
			Date date = getAgo(str, timeZone);
			if (null != date) {
				myDate = new MyDate();
				myDate.setIsDate(true);
				myDate.setDate(date);
				myDate.setStr(strOri);
				myDate.setPattern("*age");
				int score = CommonUtil.getScoreEN(strOri, "*age");
				myDate.setScore(score);
			}
		}

		if (null != myDate) {
			return myDate;
		}

		// handle this kind: Updated: Yesterday 10:37 a.m.
		if (str.contains("updated")) {
			myDate = getUpdate(str, timeZone);
			if (null != myDate) {
				myDate.setStr(strOri);
			}
		}

		return myDate;
	}

	/**
	 * 从含有age的字符串中抽取日期
	 * 
	 * @date 2016年6月14日 下午5:50:19
	 * @param str
	 *            有age的字符串
	 * @return java.util.Date
	 */
	public Date getAgo(String str, int timeZone) {
		boolean needHandleTimeZone = false; // 是时，分，秒的时候，要处理时区
		long ms = 0L; // 多少毫秒，由age之前的数字算出

		// 如果是秒
		Pattern p = regexPtnMap.get("(\\d{1,2}) second(s)? ago");
		Matcher m = p.matcher(str);
		if (m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L;
			needHandleTimeZone = true;
		}

		// 如果是分
		p = regexPtnMap.get("(\\d{1,2}) minute(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60;
			needHandleTimeZone = true;
		}

		// 如果是时
		p = regexPtnMap.get("(\\d{1,2}) hour(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 60;
			needHandleTimeZone = true;
		}

		// 如果是日
		p = regexPtnMap.get("(\\d{1,2}) day(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 60 * 24;
		}

		// 如果是week
		p = regexPtnMap.get("(\\d{1,2}) week(s)? ago");
		m = p.matcher(str);
		if (ms == 0 && m.find()) {
			String s = m.group(1);
			ms = Integer.valueOf(s);
			ms *= 1000L * 60 * 60 * 24 * 7;
		}

		// 如果是month
		p = regexPtnMap.get("(\\d{1,2}) month(s)? ago");
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

		if (needHandleTimeZone) {
			date = CommonUtil.handleTimeZone(date, timeZone);
		}

		return date;
	}
	
	/**
	 * 处理这种情况：Updated: Yesterday 10:37 a.m.
	 * @date 2016年10月18日 下午7:03:46
	 * @param str
	 * @param timeZone
	 * @return
	 */
	public MyDate getUpdate(String str, int timeZone) {
		MyDate myDate = null;
		Map<String, String> datePtnMap_ = new LinkedHashMap<String, String>();
		datePtnMap_.put("(yesterday)?\\s+(\\d{1,2}:\\d{1,2}\\s+(am|pm))", "hh:mm a");
		datePtnMap_.put("(yesterday)?\\s+(\\d{1,2}:\\d{1,2})", "hh:mm");

		for (Entry<String, String> e : datePtnMap_.entrySet()) {
			String regex = e.getKey();
			String datePtn = e.getValue();

			Pattern p = regexPtnMap.get(regex);
			Matcher m = p.matcher(str);

			if (m.find()) {
				String yesterday = m.group(1);
				String dateStr = m.group(2);
				dateFormat = dateFormatMap.get(datePtn);

				try {
					Date date = dateFormat.parse(dateStr);

					Calendar c = Calendar.getInstance();
					int year = c.get(Calendar.YEAR);
					int month = c.get(Calendar.MONTH);
					int day = c.get(Calendar.DAY_OF_MONTH);
					c.setTime(date);
					c.set(Calendar.YEAR, year);
					c.set(Calendar.MONTH, month);
					if (null != yesterday) {
						day--;
					}
					c.set(Calendar.DAY_OF_MONTH, day);

					date = c.getTime();

					// 默认要处理时区
					date = CommonUtil.handleTimeZone(date, timeZone);

					myDate = new MyDate();
					myDate.setIsDate(true);
					myDate.setDate(date);
					myDate.setPattern("*updated*");

					int score = 2;
					if (str.length() < 30) {
						score += 3;
					} else if (str.length() < 50) {
						score += 2;
					} else if (str.length() < 70) {
						score += 1;
					}

					myDate.setScore(score);
				} catch (ParseException e1) {

				}
			}

			if (null != myDate) {
				break;
			}
		}

		return myDate;
	}

	/** 将英式数字one, two, three, 等转为阿拉伯数字
	 * @date 2016年6月15日 上午10:24:06
	 * @param s
	 * @return 阿拉伯式的数字 */
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
	
	public String dropStNdRdTh(String dateStr) {
		boolean contains = false;

		// 处理同时有august和时区ist or bst的情况
		dateStr = dateStr.replace("august", "AUGUST");

		for (String s : TIMEZONE.split("\\|")) {
			if (dateStr.contains(s)) {
				// 先将s用#####代替
				dateStr = dateStr.replace(s, "#####");
				dateStr = dateStr.replace("st", "").replace("nd", "").replace("rd", "").replace("th", "");
				dateStr = dateStr.replace("#####", s);

				contains = true;
			}
		}

		if (!contains) {
			dateStr = dateStr.replace("st", "").replace("nd", "").replace("rd", "").replace("th", "");
		}

		// 替换回来
		dateStr = dateStr.replace("AUGUST", "august");

		return dateStr;
	}
}