package com.hewentian.crawler.util;

import org.apache.log4j.Logger;

/***
 * @Description 错误输出工具类
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-7-28 下午02:46:49
 * @version 1.0
 * @since JDK 1.7
 */
public class ErrorOutput {
	private static final Logger LOGGER = Logger.getLogger(ErrorOutput.class);

	public static void log(Throwable e) {
		LOGGER.error(e.getMessage(), e);
	}

	public static void log(String msg) {
		LOGGER.error(msg);
	}
}