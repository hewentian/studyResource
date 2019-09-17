package com.hewentian.util;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * <p>
 * <b>JsoupUtil</b> 是 Jsoup 常用工具类, 这里只是列出它的一些常用方法,
 * 参考：http://www.open-open.com/jsoup/
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年10月11日 下午3:45:15
 * @since JDK 1.7
 * 
 */
public class JsoupUtil {
	/**
	 * 解析一个HTML字符串
	 */
	public static void m1() {
		String html = "<html><head><title>First parse</title></head>" + "<body><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		System.out.println(doc);
	}

	/**
	 * 解析一个body片断
	 */
	public static void m2() {
		String html = "<div><p>Lorem ipsum.</p>";
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();
		System.out.println(doc);
		System.out.println(body);
	}

	/**
	 * 从一个URL加载一个Document
	 * 
	 * @throws Exception
	 */
	public static void m3() throws Exception {
		Document doc = Jsoup.connect("http://example.com").data("query", "Java").userAgent("Mozilla").cookie("auth", "token").timeout(3000).post();
		System.out.println(doc);
	}

	/**
	 * 从一个文件加载一个文档
	 * 
	 * @throws Exception
	 */
	public static void m4() throws Exception {
		File input = new File("F://input.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		System.out.println(doc);
	}

	/**
	 * 使用DOM方法来遍历一个文档
	 * 
	 * @throws Exception
	 */
	public static void m5() throws Exception {
		File input = new File("F://input.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com");

		Element content = doc.getElementById("content");
		System.out.println(content);

		Elements links = content.getElementsByTag("a");
		for (Element link : links) {
			System.out.println(link.text() + ", " + link.attr("href"));
		}
	}

	/**
	 * 使用选择器语法来查找元素
	 * 
	 * @throws Exception
	 */
	public static void m6() throws Exception {
		File input = new File("F://input.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com");

		Elements links = doc.select("a[href]");// 带有href属性的a元素
		Elements pngs = doc.select("img[src$=.jpg");// 扩展名为.jpg的图片
		Element masthead = doc.select("div.masthead").first();// class等于masthead的div标签
		Elements h3a = doc.select("h3.r > a");// 在h3元素,class等于r,之后的a元素

		System.out.println(links);
		System.out.println(pngs);
		System.out.println(masthead);
		System.out.println(h3a);
	}

	/**
	 * 从元素抽取属性，文本和HTML
	 */
	public static void m7() {
		String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
		Document doc = Jsoup.parse(html);// 解析HTML字符串返回一个Document实现
		Element link = doc.select("a").first();// 查找第一个a元素

		String text = doc.body().text(); // "An example link"//取得字符串中的文本
		String linkHref = link.attr("href"); // "http://example.com/"//取得链接地址
		String linkText = link.text(); // "example""//取得链接地址中的文本

		String linkOuterH = link.outerHtml();
		// "<a href="http://example.com"><b>example</b></a>"
		String linkInnerH = link.html(); // "<b>example</b>"//取得链接内的html内容

		System.out.println(text);
		System.out.println(linkHref);
		System.out.println(linkText);
		System.out.println(linkOuterH);
		System.out.println(linkInnerH);
	}

	/**
	 * 处理URLs
	 * 
	 * @throws Exception
	 */
	public static void m8() throws Exception {
		Document doc = Jsoup.connect("http://www.open-open.com").get();

		Element link = doc.select("a").first();
		String relHref = link.attr("href"); // == "/"
		String absHref = link.attr("abs:href"); // "http://www.open-open.com/"
		System.out.println(relHref);
		System.out.println(absHref);
	}

	public static void main(String[] args) throws Exception {
		m1();
		m2();
		m3();
		m4();
		m5();
		m6();
		m7();
		m8();
	}
}