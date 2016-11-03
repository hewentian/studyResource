package com.hewentian.util;

import com.hewentian.entity.MyDate;

/**
 * 
 * <p>
 * <b>MyDateUtil</b> 是 日期处理工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月24日 下午4:47:51
 * @since JDK 1.7
 * 
 */
public class MyDateUtil {
	/** 中文日期处理类 */
	private static DateCNUtil dateCNUtil = new DateCNUtil();

	/** 英文日期处理类 */
	private static DateENUtil dateENUtil = new DateENUtil();

	/**
	 * 从指定的字符串中按指定的语言，日期格式抽取日期，如果有多个日期，则只会获取第一个日期
	 * 
	 * @date 2016年9月24日 下午4:48:20
	 * @param country 国家的简写，如中国为CN, 台湾地区为TW， 香港地区为HK
	 * @param language 国家所使用的语言, 如中国大陆、香港、台湾为zh, 英语为：en
	 * @param timeZone 时区, 如中国为：8
	 * @param str 存在日期的字符串, 不可为空
	 * @param datePtn
	 *            文中的日期格式，如：yyyy年MM月dd日 HH时mm分ss秒, 可为空
	 * @return 文中的日期，格式为：{@linkplain com.hewentian.entity.MyDate MyDate} or 没有找到时间时为null
	 * @throws Exception
	 */
	public static MyDate getDate(String country, String language, int timeZone, String str, String datePtn) throws Exception {
		MyDate date = null;

		try {
//			long startTime = System.currentTimeMillis();

			boolean noFindCountry = false; // 没有找到处理这个国家的时间处理类, 默认为false

			if ("zh".equals(language)) {
				if ("CN".equals(country) || "TW".equals(country) || "HK".equals(country)) {
					date = dateCNUtil.getDate(str, datePtn, timeZone);
				} else {
					noFindCountry = true;
					// 默认还是用这个类来处理
					date = dateCNUtil.getDate(str, datePtn, timeZone);
				}
			} else if ("en".equals(language)) {
				if ("US".equals(country) || "GB".equals(country)) {
					date = dateENUtil.getDate(str, datePtn, timeZone);
				} else {
					noFindCountry = true;
					// 默认还是用这个类来处理
					date = dateENUtil.getDate(str, datePtn, timeZone);
				}
			}

			if (noFindCountry && null == date) {
				//throw new Exception("没有找到处理这个国家的时间处理类, 国家: " + country + ", 语言: " + language);
			} else if (noFindCountry && null != date) {
				// 用默认时间处理类来处理这个国家的时间时，评分要减一
//				int score = date.getScore();
//				if (score > 1) {
//					score--;
//				}
//				date.setScore(score);
				date.setMsg("用默认时间处理类来处理这个国家的时间");
			}

//			long endTime = System.currentTimeMillis();
//			System.out.println("cost time: " + (endTime - startTime) + " milliseconds");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return date;
	}
}