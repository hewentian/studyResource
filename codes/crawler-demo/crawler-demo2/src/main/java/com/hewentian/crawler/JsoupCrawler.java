package com.hewentian.crawler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hewentian.util.StringUtils;

/**
 * 
 * <p>
 * <b>JsoupCrawler</b> 是 Jsoup 爬取工具类
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年7月3日 下午8:00:21
 * @since JDK 1.8
 *
 */
public class JsoupCrawler {
	private static Logger log = Logger.getLogger(JsoupCrawler.class);

	// 这个URL的爬取比较特别， 它不是一步到位的。
	// 首先它会访问第一个URL，然后获取到的HTML，然后根据当前HTML执行里面的JS，在这一步中，它会得到第一个cookie的key，并且
	// 得到下一步跳转的URL地址，
	// 第二步，根据第一步的URL，跳转然后获得第二个 cookie 的key，
	// 第三步，根据第一、二步中获取到的 cookie 值执行最终的请求
	// 这就是有时候，我们直接执行第三步，然后啥也没有爬取到，而在浏览器上面直接输入终极URL，却有数据的原因。
	public static List<String[]> getIpProcessFromBaiten(String applyCode) throws Exception {
		List<String[]> processList = new ArrayList<String[]>();
		String url = "http://so.baiten.cn/detail/detail?type=63&id=" + applyCode;

		String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";

		// 第一步：获取 yunsuo_session_verify
		Connection conn = Jsoup.connect(url).userAgent(userAgent).timeout(10000);
		Response response = conn.execute();
		String html = response.body(); // html 内容详见 baiten.html
		log.info(html);
		String yunsuoSessionVerify = response.cookie("yunsuo_session_verify");

		String securityVerifyData = StringUtils.toHexString("1920,1080");
		String srcurl = StringUtils.toHexString(url);

		// 第二步：获取 security_session_mid_verify
		conn = Jsoup.connect(url + "&security_verify_data=" + securityVerifyData).userAgent(userAgent)
				.cookie("yunsuo_session_verify", yunsuoSessionVerify).cookie("srcurl", srcurl).cookie("path", "/")
				.timeout(10000);

		response = conn.execute();
		String securitySessionMidVerify = response.cookie("security_session_mid_verify");

		Document document = Jsoup.connect(url + "&security_verify_data=" + securityVerifyData).userAgent(userAgent)
				.cookie("yunsuo_session_verify", yunsuoSessionVerify).cookie("srcurl", srcurl)
				.cookie("security_session_mid_verify", securitySessionMidVerify).cookie("path", "/").timeout(10000).get();

		if (document == null)
			return null;

		Elements trs = document.select("#pd-d-content .pd-d-c-g-table tr");
		if (trs == null || trs.isEmpty())
			return null;

		for (Element tr : trs) {
			Elements tds = tr.select("td");
			if (tds == null || tds.size() < 2)
				continue;
			String date = tds.get(0).text();
			String step = tds.get(1).text();
			String stepSec = tds.get(2).text();
			if (StringUtils.isEmpty(step) || StringUtils.isEmpty(date))
				continue;
			date = date.replaceAll("-", "").trim();

			String[] process = new String[3];
			process[0] = step.trim();
			process[1] = date;
			process[2] = stepSec;

			// 法律状态详情
			processList.add(process);

			log.info(date + "--" + step + "--" + stepSec);
		}

		return processList;
	}

	public static void main(String[] args) {
		try {
			getIpProcessFromBaiten("CN201110050597.7");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
