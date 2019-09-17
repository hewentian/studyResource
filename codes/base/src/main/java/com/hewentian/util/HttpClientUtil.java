package com.hewentian.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

/**
 * 
 * <p>
 * <b>HttpClientUtil</b> 是 httpClient 工具类
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年10月18日 上午11:19:13
 * @since JDK 1.8
 *
 */
public class HttpClientUtil {
	private static Logger log = Logger.getLogger(HttpClientUtil.class);

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static final int MAX_TIMEOUT = 6000;

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(100);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}

	/**
	 * 发送 GET 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String get(String url, Map<String, String> heads) {
		return get(url, new HashMap<String, Object>(), heads);
	}

	/**
	 * 发送 GET 请求（HTTP），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String get(String url, Map<String, Object> params, Map<String, String> heads) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String result = null;

		StringBuffer paramSb = new StringBuffer();
		for (String key : params.keySet()) {
			if (paramSb.length() == 0) {
				paramSb.append("?");
			} else {
				paramSb.append("&");
			}

			paramSb.append(key).append("=").append(params.get(key));
		}

		url += paramSb;

		if (log.isInfoEnabled()) {
			log.info("ready to get: " + url);
		}

		try {
			HttpGet httpGet = new HttpGet(url);
			// 添加 header
			if (null != heads && !heads.isEmpty()) {
				for (Entry<String, String> e : heads.entrySet()) {
					httpGet.addHeader(e.getKey(), e.getValue());
				}
			}

			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (log.isInfoEnabled()) {
				log.info("执行状态码 : " + statusCode);
			}

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.error(e);
		}

		return result;
	}

	/**
	 * 发送 GET 请求（HTTPS），不带输入数据
	 * 
	 * @param url
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String getSSL(String url, Map<String, String> heads) {
		return getSSL(url, new HashMap<String, Object>(), heads);
	}

	/**
	 * 发送 GET 请求（HTTPS），K-V形式
	 * 
	 * @param url
	 * @param params
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String getSSL(String url, Map<String, Object> params, Map<String, String> heads) {
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setSSLSocketFactory(createSSLConnectionSocketFactory()).setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).build();
		String result = null;

		StringBuffer paramSb = new StringBuffer();
		for (String key : params.keySet()) {
			if (paramSb.length() == 0) {
				paramSb.append("?");
			} else {
				paramSb.append("&");
			}

			paramSb.append(key).append("=").append(params.get(key));
		}

		url += paramSb;

		if (log.isInfoEnabled()) {
			log.info("ready to get: " + url);
		}

		try {
			HttpGet httpGet = new HttpGet(url);
			// 添加 header
			if (null != heads && !heads.isEmpty()) {
				for (Entry<String, String> e : heads.entrySet()) {
					httpGet.addHeader(e.getKey(), e.getValue());
				}
			}

			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (log.isInfoEnabled()) {
				log.info("执行状态码 : " + statusCode);
			}

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.error(e);
		}

		return result;
	}

	/**
	 * 发送 POST 请求（HTTP），不带输入数据
	 * 
	 * @param url
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String post(String url, Map<String, String> heads) {
		return post(url, new HashMap<String, Object>(), heads);
	}

	/**
	 * 发送 POST 请求（HTTP），K-V形式
	 * 
	 * @param url
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String post(String url, Map<String, Object> params, Map<String, String> heads) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		String result = null;

		if (log.isInfoEnabled()) {
			log.info("ready to post kv: " + url);
		}

		try {
			List<NameValuePair> pairList = new ArrayList<>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}

			HttpPost httpPost = new HttpPost(url);
			// 添加 header
			if (null != heads && !heads.isEmpty()) {
				for (Entry<String, String> e : heads.entrySet()) {
					httpPost.addHeader(e.getKey(), e.getValue());
				}
			}
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));

			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (log.isInfoEnabled()) {
				log.info("执行状态码 : " + statusCode);
			}

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (Exception e) {
					log.error(e);
				}
			}
		}

		return result;
	}

	/**
	 * 发送 POST 请求（HTTP），JSON形式
	 * 
	 * @param url
	 * @param json
	 *            json对象
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String post(String url, Object json, Map<String, String> heads) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse response = null;
		String result = null;

		if (log.isInfoEnabled()) {
			log.info("ready to post json: " + url);
		}

		try {
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");

			HttpPost httpPost = new HttpPost(url);
			// 添加 header
			if (null != heads && !heads.isEmpty()) {
				for (Entry<String, String> e : heads.entrySet()) {
					httpPost.addHeader(e.getKey(), e.getValue());
				}
			}
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(stringEntity);

			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (log.isInfoEnabled()) {
				log.info("执行状态码 : " + statusCode);
			}

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					log.error(e);
				}
			}
		}

		return result;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），K-V形式
	 * 
	 * @param url
	 *            API接口URL
	 * @param params
	 *            参数map
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 */
	public static String postSSL(String url, Map<String, Object> params, Map<String, String> heads) {
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setSSLSocketFactory(createSSLConnectionSocketFactory()).setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).build();
		CloseableHttpResponse response = null;
		String result = null;

		if (log.isInfoEnabled()) {
			log.info("ready to postssl kv: " + url);
		}

		try {
			List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				pairList.add(pair);
			}

			HttpPost httpPost = new HttpPost(url);
			// 添加 header
			if (null != heads && !heads.isEmpty()) {
				for (Entry<String, String> e : heads.entrySet()) {
					httpPost.addHeader(e.getKey(), e.getValue());
				}
			}
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));

			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (log.isInfoEnabled()) {
				log.info("执行状态码 : " + statusCode);
			}

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					log.error(e);
				}
			}
		}

		return result;
	}

	/**
	 * 发送 SSL POST 请求（HTTPS），JSON形式
	 * 
	 * @param url
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @param heads
	 *            请求头
	 * @see #postC(String, Object, Map)
	 * @return
	 */
	public static String postSSL(String url, Object json, Map<String, String> heads) {
		CloseableHttpClient httpClient = HttpClientBuilder.create()
				.setSSLSocketFactory(createSSLConnectionSocketFactory()).setConnectionManager(connMgr)
				.setDefaultRequestConfig(requestConfig).build();
		CloseableHttpResponse response = null;
		String result = null;

		if (log.isInfoEnabled()) {
			log.info("ready to postssl json: " + url);
		}

		try {
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");

			HttpPost httpPost = new HttpPost(url);
			// 添加 header
			if (null != heads && !heads.isEmpty()) {
				for (Entry<String, String> e : heads.entrySet()) {
					httpPost.addHeader(e.getKey(), e.getValue());
				}
			}
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(stringEntity);

			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (log.isInfoEnabled()) {
				log.info("执行状态码 : " + statusCode);
			}
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					log.error(e);
				}
			}
		}

		return result;
	}

	/**
	 * 发送 POST 请求（HTTP, HTTPS），JSON形式
	 * 
	 * @param url
	 * @param json
	 *            json对象
	 * @param heads
	 *            请求头, 可为 null
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static String postC(String url, Object json, Map<String, String> heads) throws KeyManagementException,
			NoSuchAlgorithmException {
		// 采用绕过验证的方式处理https请求
		SSLContext sslcontext = createSSLContext();

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext)).build();

		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

		// 创建自定义的httpClient对象
		CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connManager).build();

		CloseableHttpResponse response = null;
		String result = null;

		if (log.isInfoEnabled()) {
			log.info("ready to post json: " + url);
		}

		try {
			StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");// 解决中文乱码问题
			stringEntity.setContentEncoding("UTF-8");
			stringEntity.setContentType("application/json");

			HttpPost httpPost = new HttpPost(url);
			// 添加 header
			if (null != heads && !heads.isEmpty()) {
				for (Entry<String, String> e : heads.entrySet()) {
					httpPost.addHeader(e.getKey(), e.getValue());
				}
			}
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(stringEntity);

			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (log.isInfoEnabled()) {
				log.info("执行状态码 : " + statusCode);
			}

			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					log.error(e);
				}
			}
		}

		return result;
	}

	/**
	 * 创建SSL安全连接
	 *
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnectionSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();

			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
		} catch (GeneralSecurityException e) {
			log.error(e);
		}
		return sslsf;
	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	private static SSLContext createSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

	public static void main(String[] args) throws Exception {
		String url = "https://{替换成你请求的地址}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", "Tim Ho");
		params.put("password", "passwd");

		Map<String, String> heads = new HashMap<String, String>();
		heads.put("Content-Type", "application/json;charset=utf-8");
		heads.put("Accept", "application/json");

		String token = postSSL(url, JSON.toJSON(params), heads);
		if (log.isInfoEnabled()) {
			log.info(token);
		}
	}
}
