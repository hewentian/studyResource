package com.hewentian.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * <p>
 * <b>XPathUtil</b> 是 获取XPATH内容的工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年10月11日 下午2:53:03
 * @since JDK 1.7
 * 
 */
public class XPathUtil {
	/**
	 * 获取XPATH内容
	 * 
	 * @date 2016年10月11日 下午3:00:40
	 * @param xmlFilePath
	 *            xml文件的路径, 如：F://books.xml
	 * @param xPathExpression
	 *            xpath表达式, 如：//price/text()
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static List<String> getXPathContent(String xmlFilePath, String xPathExpression) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		List<String> results = new ArrayList<String>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(xmlFilePath);
		System.out.println(doc.getChildNodes().getLength());

		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();

		XPathExpression expr = xpath.compile(xPathExpression);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);

		NodeList nodes = (NodeList) result;
		System.out.println(nodes.getLength());
		for (int i = 0, len = nodes.getLength(); i < len; i++) {
			Node node = nodes.item(i);
			results.add(node.getNodeValue());
		}

		return results;
	}
}