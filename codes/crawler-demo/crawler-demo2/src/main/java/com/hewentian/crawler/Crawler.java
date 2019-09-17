package com.hewentian.crawler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 
 * <p>
 * <b>Crawler</b> is demo to crawl, to get some image
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年3月17日 下午4:33:55
 * @since JDK 1.8
 *
 */
public class Crawler {
	public static void main(String[] args) throws Exception {
		List<String> images = getImageUrl(""); //TODO 这里写要爬取的目标网页

		System.out.println("images size: " + images.size());

		int i = 0; // download 10 prefix images
		for (String image : images) {
			i++;
			System.out.println(image);

			String name = image.substring(image.lastIndexOf("."));
			if (!(".jpg".equals(name) || ".png".equals(name) || ".jpeg".equals(name))) {
				if (image.contains("jpg")) {
					name = ".jpg";
				} else if (image.contains("png")) {
					name = ".png";
				} else if (image.contains("jpeg")) {
					name = ".jpeg";
				} else {
					continue;
				}
			}

			// download the image
			FileUtils.copyURLToFile(new URL(image), new File("f:/crawler/" + i + name));

			if (i > 10) {
				break;
			}
		}
	}

	public static HtmlPage getPage(String url) throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);

		// set webClient params
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setTimeout(12000);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		WebRequest webRequest = new WebRequest(new URL(url));
		webRequest.setAdditionalHeader("Accept-Language", "zh-cn");

		// simulate a browser to open a web page
		HtmlPage htmlPage = webClient.getPage(webRequest);

		System.out.println("wait for the js to execute, to load data...");
		Thread.sleep(3000);

		webClient.close();

		return htmlPage;
	}

	public static List<String> getImageUrl(String url) throws Exception {
		List<String> images = new ArrayList<String>();

		HtmlPage page = getPage(url);
		DomNodeList<DomElement> imgs = page.getElementsByTagName("img");

		for (DomElement img : imgs) {
			NamedNodeMap attributes = img.getAttributes();
			Node src = attributes.getNamedItem("src");

			String imgUrl = src.getNodeValue();
			images.add(imgUrl);
		}

		return images;
	}
}
