package com.hewentian.util;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class XPathUtilTest {

	public static void main(String[] args) {
		try {
			List<String> list = null;
			list = XPathUtil.getXPathContent("F://books.xml", "/bookstore/book/title/text()");
			System.out.println(list); // [Everyday Italian, Harry Potter, XQuery Kick Start, Learning XML]

			list = XPathUtil.getXPathContent("F://books.xml", "/bookstore/book[1]/title/text()");
			System.out.println(list); // [Everyday Italian]

			list = XPathUtil.getXPathContent("F://books.xml", "/bookstore/book/price/text()");
			System.out.println(list); // [30.00, 29.99, 49.99, 39.95]

			list = XPathUtil.getXPathContent("F://books.xml", "/bookstore/book[price>35]/price/text()");
			System.out.println(list); // [49.99, 39.95]

			list = XPathUtil.getXPathContent("F://books.xml", "/bookstore/book[price>35]/title/text()");
			System.out.println(list); // [XQuery Kick Start, Learning XML]

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}