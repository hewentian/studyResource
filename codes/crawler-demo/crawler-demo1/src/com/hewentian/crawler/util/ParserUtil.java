package com.hewentian.crawler.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.PrototypicalNodeFactory;
import org.htmlparser.util.NodeList;

public class ParserUtil {
	private static Logger log = Logger.getLogger(ParserUtil.class);
	private static final PrototypicalNodeFactory factory;
	public static Parser parser = null;
	public static HttpEntity entity = null;

	static {
		factory = new PrototypicalNodeFactory();
		factory.registerTags();
	}

	public static NodeList parse(String url, NodeFilter filter) {
		log.info("当前访问的链接是:  " + url);
		HttpClientUtil.excute(url);
		entity = HttpClientUtil.getEntity(url);
		Header[] headers = HttpClientUtil.getResponse().getAllHeaders();
		String value = "utf-8";
		for (Header header : headers) {
			if (header.getName().equalsIgnoreCase("content-type")) {
				value = header.getValue();
				value = value.substring(value.lastIndexOf("=") + 1);
			}
		}

		try {
			parser = Parser.createParser(EntityUtils.toString(entity), value);
			parser.setNodeFactory(factory);
			return parser.parse(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}