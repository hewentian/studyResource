package com.hewentian.util;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * <b>CsvUtil</b> 是 Csv工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年11月12日 下午4:05:40
 * @since JDK 1.7
 * 
 */
public class CsvUtil {
	Logger logger = Logger.getLogger(CsvUtil.class);

	public void hostDownStatSumDown(HttpServletResponse response, String startTime, String endTime) throws Exception {
		StringBuilder sb = new StringBuilder("日期,下载数,处理数,修改数,新增数");

		sb.append(System.getProperty("line.separator")); // 换行用这个
		sb.append("1,2,3,4,5");
		sb.append(System.getProperty("line.separator"));
		sb.append("10,20,30,40,50");

		byte[] bytes = sb.toString().getBytes("UTF-8");

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		int contentLength = bais.available();
		OutputStream os = null;

		try {
			String filename = "数据汇总-20161112.csv";
			response.setContentType("application/force-download;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.setContentLength(contentLength + 3); // bom has 3
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("UTF-8");
			os = response.getOutputStream();

			byte[] b = new byte[1024];
			int len = 0;
			os.write(0xEF);
			os.write(0xBB);// bom EF BB BF
			os.write(0xBF);
			while ((len = bais.read(b)) != -1) {
				os.write(b, 0, len);
			}
		} catch (Exception e) {
			logger.error("数据汇总", e);
		} finally {
			if (null != bais) {
				try {
					bais.close();
					bais = null;
				} catch (Exception e2) {
					logger.error("数据汇总", e2);
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (Exception e2) {
					logger.error("数据汇总", e2);
				}
			}
		}
	}
}