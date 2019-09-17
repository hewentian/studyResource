package com.hewentian.lucene.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

/**
 * 
 * <p>
 * <b>TikaUtil</b> 是 TikaUtil工具类
 * </p>
 * 
 * 使用GUI界面解释文本: 进入Tika所有的目录，运行以下命令：java -jar tika-app-1.13.jar -g
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月18日 上午10:10:20
 * @since JDK 1.7
 * 
 */
public class TikaUtil {

	public static String extractContent(File f) {
		// 1、创建一个parser
		Parser parser = new AutoDetectParser();
		InputStream is = null;

		try {
			Metadata metadata = new Metadata();
			metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
			is = new FileInputStream(f);
			ContentHandler handler = new BodyContentHandler();
			ParseContext context = new ParseContext();
			context.set(Parser.class, parser);

			// 2、执行parser的parse()方法。
			parser.parse(is, handler, metadata, context);

			String returnString = handler.toString();

			System.out.println(returnString.length());
			return returnString;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "No Contents";
	}
}