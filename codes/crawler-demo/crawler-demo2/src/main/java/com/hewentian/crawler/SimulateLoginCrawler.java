package com.hewentian.crawler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

/**
 * 
 * <p>
 * <b>SimulateLoginCrawler</b> 是 模拟登录之后执行爬取，手动登录后，就可以看到登录后的 cookie 了
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年5月31日 上午11:17:24
 * @since JDK 1.8
 *
 */
public class SimulateLoginCrawler {
	private static Logger log = Logger.getLogger(SimulateLoginCrawler.class);

	public static HtmlPage getPage(String zch) throws Exception {
		// 这参考本页底端找到
		String cookieString = "SPRING_SECURITY_REMEMBER_ME_COOKIE=MTM3MTcyMjg4Njc6MTQ5Njk5NTMxMTU3MDpiZjFjY2Q1ZTQxMWY4YzAxMTczYjZkNTliYWM3MmYxMw; JSESSIONID=DC7F95D1082269E987B785940553EEEA; Hm_lvt_55d3982f9d41b99f56a44ddcd44b78be=1495764905; Hm_lpvt_55d3982f9d41b99f56a44ddcd44b78be=1495786571";

		URL url = new URL(""); //TODO 这里写要爬取的目标网页
		WebClient webClient = new WebClient(BrowserVersion.CHROME);

		// 设置webClient的相关参数
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setTimeout(15000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		// 加入 cookie
		webClient.addCookie(cookieString, url, null);

		WebRequest webRequest = new WebRequest(url);
		List<NameValuePair> requestParameters = new ArrayList<NameValuePair>();
		requestParameters.add(new NameValuePair("zch", zch));

		webRequest.setRequestParameters(requestParameters);
		webRequest.setHttpMethod(HttpMethod.POST);
		webRequest.setAdditionalHeader("Accept-Language", "zh-cn");

		// 模拟浏览器打开一个目标网址
		HtmlPage rootPage = webClient.getPage(webRequest);
		log.info("为了获取js执行的数据 线程开始沉睡等待");
		Thread.sleep(2000);// 主要是这个线程的等待 因为js加载也是需要时间的

		webClient.close();
		return rootPage;
	}

	public static void main(String[] args) throws Exception {
		try {
			HtmlPage rootPage = getPage("1059157,20544071");
			DomElement domElement = rootPage.getElementById("messageList");
			DomNodeList<DomNode> as = domElement.querySelectorAll("dl dt span[class='zch'] a");
			for (DomNode dn : as) {
				try {
					String imgUrl = dn.getAttributes().getNamedItem("href").getNodeValue();
					imgUrl = imgUrl.replace("/detail", "http:"); //TODO 作相应处理
					imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf("/"));

					FileUtils.copyURLToFile(new URL(imgUrl), new File("F:/sb/" + System.currentTimeMillis() + ".jpg"));
				} catch (Exception e) {
					log.error(e);
				}
			}
		} catch (Exception e) {
			log.error(e);
		}

	}
}

//浏览器，按F12，切换到【网络】选项卡，就可以看到 cookie 了
//Request Headers
//Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
//Accept-Encoding:gzip, deflate
//Accept-Language:zh-CN,zh;q=0.8
//Cache-Control:no-cache
//Connection:keep-alive
//Content-Length:102
//Content-Type:application/x-www-form-urlencoded
//Cookie:SPRING_SECURITY_REMEMBER_ME_COOKIE=MTM3MTcyMjg4Njc6MTQ5Njk5NTMxMTU3MDpiZjFjY2Q1ZTQxMWY4YzAxMTczYjZkNTliYWM3MmYxMw; JSESSIONID=06E7CEB8F7529016D561EAA73112B66B; Hm_lvt_55d3982f9d41b99f56a44ddcd44b78be=1495764905; Hm_lpvt_55d3982f9d41b99f56a44ddcd44b78be=1496198438
//Host:
//Origin:
//Pragma:no-cache
//Referer:
//Upgrade-Insecure-Requests:1
//User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36