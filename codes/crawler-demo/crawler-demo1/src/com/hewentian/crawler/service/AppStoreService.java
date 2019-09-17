package com.hewentian.crawler.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

import com.hewentian.crawler.entity.App;
import com.hewentian.crawler.util.ErrorOutput;
import com.hewentian.crawler.util.ParserUtil;

/***
 * @Description 获取AppStore中的热门App
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-7-24 下午04:09:55
 * @version 1.0
 * @since JDK 1.7
 */
public class AppStoreService {
	private static Logger log = Logger.getLogger(AppStoreService.class);

	/** AppStore官网地址 */
	private static final String BASE_URL = "https://itunes.apple.com/cn/genre/ios/id36?mt=8";

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

		NodeFilter filter1 = new HasAttributeFilter("class", "top-level-genre");
		NodeFilter filter2 = new HasAttributeFilter("class", "list top-level-subgenres");
		OrFilter orFilter = new OrFilter(filter1, filter2);

		NodeList nodeList = ParserUtil.parse(BASE_URL, orFilter);

		if (null == nodeList) {
			return;
		}

		visitMap.clear();
		// 首先抽取大的分类
		NodeList nodeList2 = nodeList.extractAllNodesThatMatch(filter1);
		SimpleNodeIterator sni = nodeList2.elements();
		while (sni.hasMoreNodes()) {
			Node node = sni.nextNode();
			TagNode tagNode = (TagNode) node;

			String key = node.toPlainTextString().trim();
			String value = tagNode.getAttribute("href");
			visitMap.put(key, value);
		}

		// 然后抽取小的分类
		NodeList nodeList3 = nodeList.extractAllNodesThatMatch(filter2);
		if (null == nodeList3) {
			return;
		}

		for (int i = 0; i < nodeList3.size(); i++) {
			NodeList nodeList4 = nodeList3.elementAt(i).getChildren();
			if (null == nodeList4) {
				continue;
			}

			sni = nodeList4.elements();
			while (sni.hasMoreNodes()) {
				Node node = sni.nextNode().getFirstChild();
				TagNode tagNode = (TagNode) node;

				String key = node.toPlainTextString().trim();
				String value = tagNode.getAttribute("href");
				visitMap.put(key, value);
			}
		}

		log.info("---------- init end----------");
		if (!visitMap.isEmpty()) {
			log.info("获取到的所有目录如下：");
			int i = 1, len = visitMap.size();
			for (Entry<String, String> e : visitMap.entrySet()) {
				log.info(i++ + "/" + len + " : " + e.getKey() + " : " + e.getValue());
			}
		}
	}

	/**
	 * 扒在init中获取的所有目录：获取该目录app的名字、URL
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
			log.info("---------- crawl " + i++ + "/" + size + " : start to crawl : " + categoryUrl
					+ "----------");

			NodeFilter filter1 = new TagNameFilter("div");
			NodeFilter filter2 = new HasAttributeFilter("id", "selectedcontent");

			AndFilter andFilter = new AndFilter(filter1, filter2);
			NodeList nodeList = ParserUtil.parse(categoryUrl, andFilter);

			if (null == nodeList) {
				continue;
			}

			nodeList = nodeList.elementAt(0).getChildren().extractAllNodesThatMatch(filter1);

			for (int j = 0; j < nodeList.size(); j++) {
				NodeList nodeList2 = nodeList.elementAt(j).getChildren();
				if (null == nodeList2) {
					continue;
				}
				// 现在nodeList2里面的都是li
				nodeList2 = nodeList2.elementAt(1).getChildren()
						.extractAllNodesThatMatch(new TagNameFilter("li"));
				if (null == nodeList2) {
					continue;
				}

				SimpleNodeIterator sni = nodeList2.elements();
				while (sni.hasMoreNodes()) {
					App app = new App();
					app.setFrom("App Store");
					app.setCategory(category);
					app.setCreateAt(System.currentTimeMillis());

					Node node = sni.nextNode().getFirstChild();
					TagNode tagNode = (TagNode) node;

					// 获取name
					app.setName(node.toPlainTextString().trim());

					// 获取URL
					if (tagNode instanceof LinkTag) {
						String href = tagNode.getAttribute("href");
						app.setUrl(href);
						app.setId(href.substring(href.lastIndexOf("app/") + 4));
					}

					apps.add(app);
				}
			}
			// if( i == 3) break; // 测试用，仅让其扒三个目录
		}

		log.info("---------- crawl end. total apps: " + apps.size() + "----------");
	}

	/**
	 * 根据获取到的app的详细信息URL，获取app的详细信息
	 * 
	 * @param lists
	 */
	public void getDetail() {
		// App a = new App();
		// a.setUrl("https://itunes.apple.com/cn/app/xiang-gang-de-tie-xian-lu-tu/id361202747?mt=8");
		// a.setUrl("https://itunes.apple.com/cn/app/yi-shu-xin-wen/id598848324?mt=8");
		// apps.add(a);
		log.info("---------- getDetail start----------");

		if (apps.isEmpty()) {
			log.info("---------- getDetail: apps is empty ----------");
			return;
		}

		Queue<String> saveQueue = new LinkedList<String>();

		NodeFilter filter1 = new HasAttributeFilter("id", "left-stack");
		NodeFilter filter2 = new HasAttributeFilter("metrics-loc", "Titledbox_内容提要");

		OrFilter orFilter = new OrFilter(filter1, filter2);

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
				// 获取description
				Node node1 = nodeList.extractAllNodesThatMatch(filter2).elementAt(0).getChildren()
						.elementAt(3);
				app.setDescription(node1.toPlainTextString().trim());

				// 获取score、commentNum
				NodeList nodeList2 = nodeList.extractAllNodesThatMatch(filter1).elementAt(0)
						.getChildren();
				Node node2 = nodeList2.extractAllNodesThatMatch(
						new HasAttributeFilter("class", "extra-list customer-ratings"))
						.elementAt(0);
				if (node2.getChildren().size() >= 5) {
					node2 = node2.getChildren().elementAt(5);
					TagNode tagNode = (TagNode) node2;

					String[] sc = tagNode.getAttribute("aria-label").split(",");
					String score = sc[0].substring(0, sc[0].indexOf("星")).trim();
					String commentNum = sc[1].substring(0, sc[1].indexOf("份评分")).trim();
					app.setScore(Double.valueOf(score).floatValue());
					app.setCommentNum(Integer.valueOf(commentNum));
				}

				NodeList nodeList3 = nodeList2
						.extractAllNodesThatMatch(new HasAttributeFilter("rating-software"))
						.elementAt(0).getChildren();

				// 获取imgURL
				TagNode tagNode2 = (TagNode) nodeList3.elementAt(2).getFirstChild();
				app.setImgUrl(tagNode2.getAttribute("src"));

				// 获取系统版本要求
				Node node3 = nodeList3.extractAllNodesThatMatch(new TagNameFilter("p"))
						.elementAt(0).getChildren().elementAt(1);
				app.setSystemRequire(node3.toPlainTextString().trim());

				// 获取 ageLimit
				Node node4 = nodeList3.extractAllNodesThatMatch(
						new HasAttributeFilter("class", "app-rating")).elementAt(0);
				node4 = null != node4 ? node4.getFirstChild() : null;
				node4 = null != node4 ? node4.getFirstChild() : null;
				if (null != node4) {
					app.setAgeLimit(node4.toPlainTextString());
				}

				NodeList nodeList4 = nodeList3
						.extractAllNodesThatMatch(new HasAttributeFilter("class", "list"))
						.elementAt(0).getChildren();

				// 获取isFree
				Node node5 = nodeList4.elementAt(0).getFirstChild().getFirstChild();
				String isFree = node5.toPlainTextString().trim();
				app.setIsFree("免费".equals(isFree) ? 0 : 1);

				// 获取type
				NodeList tmpNodeList = nodeList4.extractAllNodesThatMatch(new HasAttributeFilter(
						"class", "genre"));
				if (null != tmpNodeList && tmpNodeList.size() >= 1) {
					Node node6 = tmpNodeList.elementAt(0).getChildren().elementAt(1)
							.getFirstChild();
					app.setType(node6.toPlainTextString().trim());
				}

				// 获取updateTime
				tmpNodeList = nodeList4.extractAllNodesThatMatch(new HasAttributeFilter("class",
						"release-date"));
				if (null != tmpNodeList && tmpNodeList.size() >= 1) {
					Node node7 = tmpNodeList.elementAt(0).getChildren().elementAt(1);
					app.setUpdateTime(node7.toPlainTextString().trim());
				}

				// 获取version
				if (nodeList4.size() >= 3) {
					Node node8 = nodeList4.elementAt(3).getChildren().elementAt(1);
					app.setVersion(node8.toPlainTextString().trim());
				}

				// 获取size
				if (nodeList4.size() >= 4) {
					Node node9 = nodeList4.elementAt(4).getChildren().elementAt(1);
					String size = node9.toPlainTextString().trim();

					if (size.contains("M")) {
						size = size.substring(0, size.indexOf("M")).trim();
					} else if (size.contains("G")) {
						size = size.substring(0, size.indexOf("G")).trim();
						double d = Double.valueOf(size).doubleValue();
						d = d * 1024;
						size = d + "";
					}

					app.setSize(Double.valueOf(size).doubleValue());
				}

				// 获取language
				tmpNodeList = nodeList4.extractAllNodesThatMatch(new HasAttributeFilter("class",
						"language"));
				if (null != tmpNodeList && tmpNodeList.size() >= 1) {
					Node node10 = tmpNodeList.elementAt(0).getChildren().elementAt(1);
					app.setLanguage(node10.toPlainTextString().trim());
				}

				// 获取gameFactory
				Node node11 = nodeList4.elementAt(nodeList4.size() - 2).getChildren().elementAt(1);
				String gameFactory = node11.toPlainTextString().trim();

				tmpNodeList = nodeList4.extractAllNodesThatMatch(new HasAttributeFilter("class",
						"copyright"));
				if (null != tmpNodeList && tmpNodeList.size() >= 1) {
					Node node12 = tmpNodeList.elementAt(0).getFirstChild();
					gameFactory = gameFactory + " " + node12.toPlainTextString().trim();
					app.setGameFactory(gameFactory);
				}

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