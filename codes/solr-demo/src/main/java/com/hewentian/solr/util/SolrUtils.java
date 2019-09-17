package com.hewentian.solr.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.core.CoreContainer;

import com.hewentian.solr.entity.User;

/**
 * 
 * <p>
 * <b>SolrUtils</b> 是 solr工具类
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年4月23日 下午12:32:31
 * @since JDK 1.8
 *
 */
public class SolrUtils {
	private static Logger logger = Logger.getLogger(SolrUtils.class);

	public static SolrClient createEmbeddedSolrClient(String coreName) {
		try {
			System.setProperty("solr.solr.home", "/home/hewentian/ProjectD/solr-6.5.0/server/solr");
			CoreContainer coreContainer = new CoreContainer("/home/hewentian/ProjectD/solr-6.5.0/server/solr");

			// don't forge to invoke this method since solr 4.4.0
			coreContainer.load();

			return new EmbeddedSolrServer(coreContainer, coreName);
		} catch (Exception e) {
			logger.error("无效的Solr服务", e);
		}

		return null;
	}

	public static SolrClient createSolrClient(String baseSolrUrl) {
		HttpSolrClient solr = null;

		try {
			solr = new HttpSolrClient.Builder().withBaseSolrUrl(baseSolrUrl).build();
			solr.setConnectionTimeout(1000);
			solr.setDefaultMaxConnectionsPerHost(100);
			solr.setMaxTotalConnections(100);
		} catch (Exception e) {
			logger.error("创建SOLR出错", e);
		}

		return solr;
	}

	public static CloudSolrClient getCloudSolrClient(String zkHost, String collection) {
		CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder().withZkHost(zkHost).build();
		cloudSolrClient.setDefaultCollection(collection);
		return cloudSolrClient;
	}

	public static SolrDocumentList query(String baseSolrUrl, String collection, SolrParams solrParams) {
		SolrDocumentList results = null;

		try {
			QueryResponse response = getQueryResponse(baseSolrUrl, collection, solrParams);
			results = response.getResults();
		} catch (Exception e) {
			logger.error("查询solrDocumentList出错", e);
		}

		return results;
	}

	public static List<User> queryBeans(String baseSolrUrl, String collection, SolrParams solrParams) {
		List<User> list = null;

		try {
			QueryResponse response = getQueryResponse(baseSolrUrl, collection, solrParams);
			list = response.getBeans(User.class);
		} catch (Exception e) {
			logger.error("查询solrDocumentList出错", e);
		}

		return list;
	}

	/**
	 * 获取返回结果
	 * 
	 * @date 2017年4月23日 下午12:52:48
	 * @param baseSolrUrl
	 * @param collection
	 * @param solrParams
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public static QueryResponse getQueryResponse(String baseSolrUrl, String collection, SolrParams solrParams)
			throws SolrServerException, IOException {
		SolrClient solrClient = createSolrClient(baseSolrUrl);

		long startTime = System.currentTimeMillis();

		QueryResponse response = solrClient.query(collection, solrParams);

		long endTime = System.currentTimeMillis();
		logger.info("query cost: " + (endTime - startTime) + " milliseconds");

		solrClient.close();
		return response;
	}

	/**
	 * 插入doc
	 * <ol>
	 * <li>如果没有指定 ID, 则 SOLR 会自动新建一个;</li>
	 * <li>如果有指定ID，如果库里面已经存在该ID的记录，则会执行更新操作</li>
	 * </ol>
	 * 
	 * @date 2017年10月28日 下午4:00:19
	 * @param baseSolrUrl
	 * @param collection
	 * @param docMap
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public static UpdateResponse addDocument(String baseSolrUrl, String collection, Map<String, Object> docMap)
			throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		for (Entry<String, Object> e : docMap.entrySet()) {
			doc.addField(e.getKey(), e.getValue());
		}

		SolrClient solrClient = createSolrClient(baseSolrUrl);
		UpdateResponse response = solrClient.add(collection, doc);

		solrClient.commit(collection);
		solrClient.close();

		return response;
	}

	/**
	 * @see #addDocument(String, String, Map) addDocument
	 */
	public static UpdateResponse addDocument(String baseSolrUrl, String collection, User user) throws IOException,
			SolrServerException {
		SolrClient solrClient = createSolrClient(baseSolrUrl);
		UpdateResponse response = solrClient.addBean(collection, user);

		solrClient.commit(collection);
		solrClient.close();

		return response;
	}

	public static UpdateResponse deleteDocument(String baseSolrUrl, String collection, String id) throws Exception,
			IOException {
		SolrClient solrClient = createSolrClient(baseSolrUrl);
		UpdateResponse response = solrClient.deleteById(collection, id);

		solrClient.commit(collection);
		solrClient.close();

		return response;
	}

	/**
	 * 查询条件特殊字符处理
	 * 
	 * @param query
	 * @return
	 */
	public static String escapeQueryChars(String query) {
		if (StringUtils.isEmpty(query)) {
			return query;
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < query.length(); i++) {
			char c = query.charAt(i);
			if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == '^' || c == '[' || c == ']'
					|| c == '\"' || c == '{' || c == '}' || c == '~' || c == '*' || c == '?' || c == '|' || c == '&'
					|| c == ';' || c == '/' || Character.isWhitespace(c)) {
				sb.append('\\');
			}

			sb.append(c);
		}

		return sb.toString();
	}
}
