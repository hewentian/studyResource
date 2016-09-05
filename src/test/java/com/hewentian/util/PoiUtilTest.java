package com.hewentian.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class PoiUtilTest {
	private static Logger log = Logger.getLogger(PoiUtilTest.class);

	public static void main(String[] args) throws Exception {
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "原文标题", "中文标题", "中文摘要", "来源", "链接", "时间" });
		data.add(new String[] { "Nightline’ Is No.1 in Total Viewers for the 6th Straight Week for Week of June 27", "中华人民共和国万岁 ", "AFDFDFAFD", "abcnews.go.com",
				"http://abcnews.go.com/Press_Release/center-styletext-aligncenternightline-no1-total-viewers-6th-straight/story?id=40437259", "2016-10-10" });

		for (int i = 0; i < 10; i++) {
			data.add(data.get(1));
		}

		
		XWPFDocument doc = PoiUtil.exportWord(data);
		
		FileOutputStream out = new FileOutputStream("d://word_" + System.currentTimeMillis() + ".docx");
		doc.write(out);
		out.close();
		
		HSSFWorkbook excel = PoiUtil.createHSSFWorkbook("sheet1", data);
		out = new FileOutputStream("d://excel_" + System.currentTimeMillis() + ".xlsx");
		excel.write(out);
		out.close();
	}

	
	/**
	 * 将指定的收藏信息下载到EXCEL
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年8月31日 下午4:14:47
	 * @param response
	 * @param collectInfoIds
	 *            收藏信息IDS，多个之间以,分隔
	 * @throws Exception 
	 */
//	@RequestMapping("{language}/collect/downloadToExcel")
	public void downloadToExcel(HttpServletResponse response, String collectInfoIds) throws Exception {
		if (StringUtils.isBlank(collectInfoIds)) {
			return;
		}
		
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "原文标题", "中文标题", "中文摘要", "来源", "链接", "时间" });
		data.add(new String[] { "Nightline’ Is No.1 in Total Viewers for the 6th Straight Week for Week of June 27", "中华人民共和国万岁 ", "AFDFDFAFD", "abcnews.go.com",
				"http://abcnews.go.com/Press_Release/center-styletext-aligncenternightline-no1-total-viewers-6th-straight/story?id=40437259", "2016-10-10" });
		XWPFDocument doc = PoiUtil.exportWord(data);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.write(baos);
		byte[] buf = baos.toByteArray();
		baos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(buf);
		int contentLength = bais.available();
		OutputStream os = null;

		try {
			String filename = "简报下载-" + System.currentTimeMillis() + ".xls";
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.setContentLength(contentLength/* + 3 */); // bom has 3
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("UTF-8");
			os = response.getOutputStream();

			byte[] b = new byte[1024];
			int len = 0;
			// os.write(0xEF);
			// os.write(0xBB);// bom EF BB BF
			// os.write(0xBF);
			while ((len = bais.read(b)) != -1) {
				os.write(b, 0, len);
			}
		} catch (Exception e) {
			log.error("将指定的收藏信息下载到EXCEL", e);
		} finally {
			if (null != bais) {
				try {
					bais.close();
					bais = null;
				} catch (Exception e2) {
					e2.printStackTrace();
					log.error("将指定的收藏信息下载到EXCEL", e2);
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					log.error("将指定的收藏信息下载到EXCEL", e2);
				}
			}
		}
	}
	
	/**
	 * 将指定的收藏信息下载到word
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年9月1日 下午5:09:05
	 * @param response
	 * @param collectInfoIds
	 *            收藏信息IDS，多个之间以,分隔
	 * @throws Exception 
	 */
//	@RequestMapping("{language}/collect/downloadToWord")
	public void downloadToWord(HttpServletResponse response, String collectInfoIds) throws Exception {
		if (StringUtils.isBlank(collectInfoIds)) {
			return;
		}
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] { "原文标题", "中文标题", "中文摘要", "来源", "链接", "时间" });
		data.add(new String[] { "Nightline’ Is No.1 in Total Viewers for the 6th Straight Week for Week of June 27", "中华人民共和国万岁 ", "AFDFDFAFD", "abcnews.go.com",
				"http://abcnews.go.com/Press_Release/center-styletext-aligncenternightline-no1-total-viewers-6th-straight/story?id=40437259", "2016-10-10" });
		XWPFDocument doc = PoiUtil.exportWord(data);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		doc.write(baos);
		byte[] buf = baos.toByteArray();
		baos.close();

		ByteArrayInputStream bais = new ByteArrayInputStream(buf);
		int contentLength = bais.available();
		OutputStream os = null;

		try {
			String filename = "简报下载-" + System.currentTimeMillis() + ".docx";
			response.setContentType("application/vnd.ms-word;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.setContentLength(contentLength);
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("UTF-8");
			os = response.getOutputStream();

			byte[] b = new byte[1024];
			int len = 0;
			while ((len = bais.read(b)) != -1) {
				os.write(b, 0, len);
			}
		} catch (Exception e) {
			log.error("将指定的收藏信息下载到word", e);
		} finally {
			if (null != bais) {
				try {
					bais.close();
					bais = null;
				} catch (Exception e2) {
					e2.printStackTrace();
					log.error("将指定的收藏信息下载到word", e2);
				}
			}
			if (null != os) {
				try {
					os.close();
				} catch (Exception e2) {
					e2.printStackTrace();
					log.error("将指定的收藏信息下载到word", e2);
				}
			}
		}
	}
}
