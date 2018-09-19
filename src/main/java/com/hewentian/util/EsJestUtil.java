package com.hewentian.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.DeleteMapping;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;

/**
 * 
 * <p>
 * <b>EsJestUtil</b> 是 EsJestUtil 工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2018-09-18 4:25:01 PM
 * @since JDK 1.8
 * 
 */
public class EsJestUtil {
	private static Logger log = Logger.getLogger(EsJestUtil.class);

	private static JestClientFactory jestClientFactory;

	private static JestClient jestClient;

	static {
		List<String> serverUris = new ArrayList<String>();
		// serverUris.add("http://localhost:9200"); // 单节点时

		serverUris.add("http://localhost:9201"); // 集群时
		serverUris.add("http://localhost:9202");
		serverUris.add("http://localhost:9203");

		HttpClientConfig httpClientConfig = new HttpClientConfig.Builder(serverUris).maxTotalConnection(5)
				.multiThreaded(true).connTimeout(30000).readTimeout(60000).build();

		jestClientFactory = new JestClientFactory();
		jestClientFactory.setHttpClientConfig(httpClientConfig);

		jestClient = jestClientFactory.getObject();
	}

	public static JestClient getJestClient() {
		return jestClientFactory.getObject();
	}

	public static boolean createIndex(String indexName, Map<String, Object> settings) throws Exception {
		// 首先判断是否已经存在该索引
		JestResult jr = jestClient.execute(new IndicesExists.Builder(indexName).build());

		if (jr.isSucceeded()) {
			return false;
		}

		CreateIndex createIndex = new CreateIndex.Builder(indexName).settings(settings).build();
		jr = jestClient.execute(createIndex);

		return jr.isSucceeded();
	}

	public static boolean deleteIndex(String indexName) {
		DeleteIndex deleteIndex = new DeleteIndex.Builder(indexName).build();

		try {
			JestResult jr = jestClient.execute(deleteIndex);
			return jr.isSucceeded();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}

	public static boolean putMappings(String indexName, String type, Object mappings) {
		PutMapping putMapping = new PutMapping.Builder(indexName, type, mappings).build();

		try {
			JestResult jr = jestClient.execute(putMapping);
			return jr.isSucceeded();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}

	/**
	 * 好像删不掉
	 */
	@Deprecated
	public static boolean deleteMapping(String indexName, String type) {
		DeleteMapping deleteMapping = new DeleteMapping.Builder(indexName, type).build();

		try {
			JestResult jr = jestClient.execute(deleteMapping);
			return jr.isSucceeded();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}

	public static JsonObject getMapping(String indexName, String type) {
		GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(type).build();

		try {
			JestResult jr = jestClient.execute(getMapping);
			if (jr.isSucceeded()) {
				return jr.getJsonObject();
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	public static boolean addDoc(String indexName, String type, Object source, String id) {
		Index index = new Index.Builder(source).index(indexName).type(type).id(id).build();

		try {
			DocumentResult dr = jestClient.execute(index);

			return dr.isSucceeded();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}

	public static JsonObject getDoc(String indexName, String id) {
		Get get = new Get.Builder(indexName, id).build();
		JsonObject jsonObject = null;

		try {
			DocumentResult dr = jestClient.execute(get);
			if (dr.isSucceeded()) {
				jsonObject = dr.getJsonObject();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return jsonObject;
	}

	public static boolean updateDoc(String indexName, String typeName, String id, Map<String, String> updateFields) {
		Get get = new Get.Builder(indexName, id).build();

		try {
			DocumentResult dr = jestClient.execute(get);
			if (!dr.isSucceeded()) {
				return false;
			}

			JsonObject jsonObject = dr.getJsonObject();
			JsonObject source = jsonObject.getAsJsonObject("_source");

			for (Entry<String, String> en : updateFields.entrySet()) {
				source.addProperty(en.getKey(), en.getValue());
			}

			JsonObject json = new JsonObject();
			json.add("doc", source);
			Update update = new Update.Builder(json.toString()).index(indexName).type(typeName).id(id).build();
			dr = jestClient.execute(update);
			return dr.isSucceeded();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}

	public static JsonObject search(String indexName, String typeName, Object query) {
		Search search = new Search.Builder(query.toString()).addIndex(indexName).addType(typeName).build();

		try {
			SearchResult sr = jestClient.execute(search);

			if (sr.isSucceeded()) {
				return sr.getJsonObject();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return null;
	}

	public static boolean deleteDoc(String indexName, String typeName, String id) {
		Delete delete = new Delete.Builder(id).index(indexName).type(typeName).build();

		try {
			DocumentResult dr = jestClient.execute(delete);

			return dr.isSucceeded();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return false;
	}
}