package com.hewentian.crawler.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.hewentian.crawler.util.ErrorOutput;

/**
 * @Description 从AppStoreFile文件中读出存储的JSON格式的app
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-7-30 上午10:53:50
 * @version 1.0
 * @since JDK 1.7
 */
public class ReadAppStoreFile {
	private static final Logger LOGGER = Logger.getLogger(ReadAppStoreFile.class);

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();

		String pathname = "/data/logs/crawler/appStoreFile.log.2014-07-28";
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(pathname)),
					"UTF-8"));

			LOGGER.info("---------- ReadAppStoreFile: start ----------\n");
			String s = null;
			int i = 1;

			while ((s = br.readLine()) != null) {
				if ("".equals(s) || !s.contains("{") || !s.contains("}")) {
					continue;
				}

				int beginIndex = s.indexOf("{");
				int endIndex = s.indexOf("}", beginIndex) + 1;

				String appJSONString = s.substring(beginIndex, endIndex);
				System.out.println(i++ + "\t: " + appJSONString);

				// 接下来是入库之类的操作

			}

			LOGGER.info("---------- ReadAppStoreFile: end, costs: "
					+ (System.currentTimeMillis() - startTime) / 1000 + " seconds ----------");

			br.close();
		} catch (Exception e) {
			ErrorOutput.log(e);
			e.printStackTrace();
		}
	}
}