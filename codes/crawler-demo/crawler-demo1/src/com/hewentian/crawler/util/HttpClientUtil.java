package com.hewentian.crawler.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

/**
 * @Description Http工具类
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-7-29 下午03:29:18
 * @version 1.0
 * @since JDK 1.7
 */
public final class HttpClientUtil {
	private static HttpClient client = null;
	private static HttpResponse response = null;

	public static HttpResponse getResponse() {
		return response;
	}

	private HttpClientUtil() {
	}

	public static HttpClient getHttpClient() {
		return client;
	}

	static {
		if (client == null) {
			client = new DefaultHttpClient();
			client = WebClientDevWrapper.wrapClient(client);
		}
	}

	public static void excute(String url) {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("accept-language",
				"zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,fr-FR;q=0.2,fr;q=0.2,zh-TW;q=0.2,ja;q=0.2,es;q=0.2");

		try {
			response = client.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeClient(HttpClient client) {
		if (client != null) {
			client.getConnectionManager().shutdown();
		}
	}

	public static HttpEntity getEntity(String url) {
		if (response != null) {
			return response.getEntity();
		} else {
			return null;
		}
	}
}

/**
 * @Description 避免HttpClient的”SSLPeerUnverifiedException: peer not
 *              authenticated”异常，不用导入SSL证书
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-7-29 下午03:18:15
 * @version 1.0
 * @since JDK 1.7
 */
class WebClientDevWrapper {
	public static HttpClient wrapClient(HttpClient base) {
		try {
			X509TrustManager tm = new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws CertificateException {
				}
			};

			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] { tm }, null);

			SSLSocketFactory ssf = new SSLSocketFactory(ctx,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));

			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);

			return new DefaultHttpClient(mgr, base.getParams());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}