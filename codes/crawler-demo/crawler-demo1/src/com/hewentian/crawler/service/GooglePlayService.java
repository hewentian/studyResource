package com.hewentian.crawler.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import com.hewentian.crawler.entity.App;
import com.hewentian.crawler.util.ErrorOutput;
import com.hewentian.crawler.util.ParserUtil;

/***
 * @Description 获取GooglePlay中的热门App
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-7-23 上午09:49:21
 * @version 1.0
 * @since JDK 1.7
 */
public class GooglePlayService {
	private static Logger log = Logger.getLogger(GooglePlayService.class);

	/** GooglePlay官网地址 */
	private static final String BASE_URL = "https://play.google.com";

	/** 要扒的目录分类 */
	private Map<String, String> visitMap = new HashMap<String, String>();

	/** 扒到的每个目录的app的部分信息，然后要根据app的url获取app的详细信息 */
	private Queue<App> apps = new LinkedList<App>();

	/** 开始扒的时间 */
	private long startTime = 0;

	/**
	 * 获取所有要扒的目录分类
	 */
	public void init() {
		log.info("---------- init start ----------");

		NodeFilter filter1 = new TagNameFilter("a");
		NodeFilter filter2 = new HasAttributeFilter("class", "child-submenu-link");
		AndFilter andFilter = new AndFilter(filter1, filter2);

		NodeList nodeList = ParserUtil.parse(BASE_URL + "/store/apps", andFilter);

		if (null == nodeList) {
			return;
		}

		visitMap.clear();
		SimpleNodeIterator sni = nodeList.elements();
		while (sni.hasMoreNodes()) {
			Node node = sni.nextNode();
			TagNode tagNode = (TagNode) node;

			String key = node.toPlainTextString().trim();
			String value = tagNode.getAttribute("href");
			visitMap.put(key, value);
		}

		log.info("---------- init end----------");
		if (!visitMap.isEmpty()) {
			log.info("获取到的所有目录如下：");
			int i = 1, len = visitMap.size();
			for (Entry<String, String> e : visitMap.entrySet()) {
				log.info(i++ + "/" + len + " : " + e.getKey() + " : " + BASE_URL + e.getValue());
			}
		}
	}

	/**
	 * 扒在init中获取的所有目录：获取该目录app的简单信息，如详细信息的url等
	 */
	public void crawl() {
		log.info("---------- crawl start----------");

		if (visitMap.isEmpty()) {
			log.info("---------- crawl visitMap is empty ----------");
			return;
		}

		apps.clear();
		int i = 1, size = visitMap.size();
		for (Entry<String, String> e : visitMap.entrySet()) {
			String category = e.getKey();
			String categoryUrl = e.getValue();
			log.info("---------- crawl " + i++ + "/" + size + " : start to crawl :" + BASE_URL
					+ categoryUrl + "----------");

			NodeFilter filter1 = new TagNameFilter("div");
			NodeFilter filter2 = new HasAttributeFilter("class",
					"card no-rationale square-cover apps small");

			AndFilter andFilter = new AndFilter(filter1, filter2);
			NodeList nodeList = ParserUtil.parse(BASE_URL + categoryUrl, andFilter);

			if (null == nodeList || nodeList.size() == 0) {
				continue;
			}

			SimpleNodeIterator sni = nodeList.elements();
			while (sni.hasMoreNodes()) {
				App app = new App();
				app.setFrom("Google Play");
				app.setCategory(category);
				app.setCreateAt(System.currentTimeMillis());

				try {
					NodeList nodeList1 = sni.nextNode().getChildren().elementAt(1).getChildren();

					// 获取URL
					TagNode tagNode1 = (TagNode) nodeList1.elementAt(1);
					if (tagNode1 instanceof LinkTag) {
						String href = tagNode1.getAttribute("href");
						app.setUrl(BASE_URL + href);
						app.setId(href.substring(href.lastIndexOf("=") + 1));
					}

					// 获取截图URL
					TagNode tagNode2 = (TagNode) nodeList1.elementAt(3).getChildren().elementAt(1)
							.getChildren().elementAt(1).getChildren().elementAt(1).getChildren()
							.elementAt(1);
					if (tagNode2 instanceof ImageTag) {
						String src = tagNode2.getAttribute("src");
						app.setImgUrl(src);
					}

					NodeList nodeListDetail = nodeList1.elementAt(5).getChildren();
					// 获取name
					Node node1 = nodeListDetail.elementAt(3).getChildren().elementAt(1)
							.getChildren().elementAt(0);
					app.setName(node1.toPlainTextString().trim());

					// 获取游戏开发商
					Node node2 = nodeListDetail.elementAt(5).getChildren().elementAt(1);
					app.setGameFactory(node2.toPlainTextString().trim());

					// 获取是否免费
					Node node3 = nodeListDetail.elementAt(5).getChildren().elementAt(3)
							.getChildren().elementAt(3).getChildren().elementAt(5).getChildren()
							.elementAt(0);
					app.setIsFree("免费".equals(node3.toPlainTextString()) ? 0 : 1);

					// 获取description
					Node node4 = nodeListDetail.elementAt(7);
					app.setDescription(node4.toPlainTextString().trim());

					apps.add(app);
				} catch (Exception e2) {
					ErrorOutput.log(app.getUrl() + "\t" + e2.getMessage());
					e2.printStackTrace();
				}
			}
		}

		log.info("---------- crawl end. total apps: " + apps.size() + "----------");
	}

	/**
	 * 根据获取到的app的详细信息URL，获取app的详细信息
	 * 
	 * @param lists
	 */
	public void getDetail() {
		log.info("---------- getDetail start----------");

		if (apps.isEmpty()) {
			log.info("---------- getDetail: apps is empty ----------");
			return;
		}

		Queue<String> saveQueue = new LinkedList<String>();

		NodeFilter filter1 = new HasAttributeFilter("itemprop", "genre");
		NodeFilter filter2 = new HasAttributeFilter("class", "score-container");
		NodeFilter filter3 = new HasAttributeFilter("class", "details-section metadata");

		OrFilter orFilter = new OrFilter(new NodeFilter[] { filter1, filter2, filter3 });

		for (int i = 0, len = apps.size(); i < len; i++) {
			App app = apps.poll();

			log.info("-------------------- getDetail start to parse " + (i + 1) + "/" + len
					+ ", time : " + (System.currentTimeMillis() - startTime) / 1000 + " seconds : "
					+ app.getUrl() + " ----------");

			NodeList nodeList = ParserUtil.parse(app.getUrl(), orFilter);

			if (null == nodeList || nodeList.size() == 0) {
				continue;
			}

			try {
				// 获取type
				Node node1 = nodeList.extractAllNodesThatMatch(filter1).elementAt(0).getChildren()
						.elementAt(0);
				app.setType(node1.toPlainTextString().trim());

				// 获取score
				Node node2 = nodeList.extractAllNodesThatMatch(filter2).elementAt(0);
				String score = node2.getChildren().elementAt(5).toPlainTextString();
				app.setScore(Double.valueOf(score).floatValue());

				// 获取commentNum
				String commentNum = node2.getChildren().elementAt(9).getChildren().elementAt(2)
						.getChildren().elementAt(0).toPlainTextString().trim();
				app.setCommentNum(Integer.valueOf(commentNum.replace(",", "")));

				NodeList nodeList2 = nodeList.extractAllNodesThatMatch(filter3).elementAt(0)
						.getChildren().elementAt(3).getChildren();
				// 获取最近更新时间
				String updateTime = nodeList2.elementAt(1).getChildren().elementAt(3)
						.toPlainTextString().trim();
				app.setUpdateTime(updateTime);

				// 获取大小
				String size = nodeList2.elementAt(3).getChildren().elementAt(3).toPlainTextString()
						.trim();

				if (size.contains("k")) {
					size = size.substring(0, size.indexOf("k")).trim();
					double d = Double.valueOf(size).doubleValue();
					d = d / 1024;
					size = d + "";
				} else if (size.contains("M")) {
					size = size.substring(0, size.indexOf("M")).trim();
				} else if (size.contains("G")) {
					size = size.substring(0, size.indexOf("G")).trim();
					double d = Double.valueOf(size).doubleValue();
					d = d * 1024;
					size = d + "";
				} else {
					size = "0";
				}

				app.setSize(Double.valueOf(size).doubleValue());

				// 获取安装次数
				String installNum = nodeList2.elementAt(5).getChildren().elementAt(3)
						.toPlainTextString().trim();
				app.setInstallNum(installNum);

				// 获取当前版本
				String version = nodeList2.elementAt(7).getChildren().elementAt(3)
						.toPlainTextString().trim();
				app.setVersion(version);

				// 获取系统版本要求
				String systemRequire = nodeList2.elementAt(9).getChildren().elementAt(3)
						.toPlainTextString().trim();
				app.setSystemRequire(systemRequire);

				saveQueue.add(app.toString());
			} catch (Exception e) {
				ErrorOutput.log(app.getUrl() + "\t" + e.getMessage());
				e.printStackTrace();
			}

			// 保存队列，每10个保存一次或当已遍历所有app的时候也要保存
			if (saveQueue.size() == 10 || i == len - 1) {
				log.info("\n\n--------------------start to save 10 apps--------------------");
				while (!saveQueue.isEmpty()) {
					log.info(saveQueue.poll());
				}

				try {
					log.info("---------- getDetail sleep 10 seconds start ----------");
					Thread.sleep(10 * 1000);
					log.info("---------- getDetail sleep 10 seconds end ----------");
				} catch (InterruptedException e) {
					ErrorOutput.log(e);
					e.printStackTrace();
				}
			}
		}

		log.info("---------- getDetail end----------");
	}

	/**
	 * 开始扒
	 */
	public void start() {
		startTime = System.currentTimeMillis();

		init();
		crawl();
		getDetail();

		log.info("---------- total costs: " + (System.currentTimeMillis() - startTime) / 1000
				+ " seconds ----------");
	}
}