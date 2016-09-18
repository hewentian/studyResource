package com.hewentian.es;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.CreateIndex.Builder;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.Stats;
import io.searchbox.indices.mapping.DeleteMapping;
import io.searchbox.indices.mapping.GetMapping;
import io.searchbox.indices.mapping.PutMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

/**
 * 
 * <p>
 * <b>EsUtil</b> 是 EsUtil工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月18日 下午4:27:16
 * @since JDK 1.7
 * 
 */
public class EsUtil {
	private static JestClientFactory jestClientFactory;
	static {
		List<String> serverUris = new ArrayList<String>();
		serverUris.add("http://localhost:9200");

		HttpClientConfig httpClientConfig = new HttpClientConfig.Builder(serverUris).maxTotalConnection(5).multiThreaded(true).connTimeout(30000).readTimeout(60000).build();

		jestClientFactory = new JestClientFactory();
		jestClientFactory.setHttpClientConfig(httpClientConfig);
	}

	public static JestClient getJestClient() {
		return jestClientFactory.getObject();
	}

	/**
	 * 创建索引
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年9月18日 下午4:39:16
	 * @param jestClient
	 * @param indexName
	 * @return 0:已经存在；1.创建成功；其他：失败
	 * @throws Exception
	 */
	public static int createIndex(JestClient jestClient, String indexName) throws Exception {
		int res = 1;

		// 首先判断是否已经存在该索引
		JestResult jr1 = jestClient.execute(new Stats.Builder().addIndex(indexName).build());

		if (!jr1.isSucceeded()) {
			if (jr1.getResponseCode() == 404) {

				JsonObject jo = new JsonObject();
				JsonObject settings = new JsonObject();
				JsonObject analysis = new JsonObject();
				JsonObject analyzer = new JsonObject();
				JsonObject zh_index = new JsonObject();
				JsonObject zh_query = new JsonObject();
				jo.add("settings", settings);
				settings.add("analysis", analysis);
				analysis.add("analyzer", analyzer);
				analyzer.add("zh_index", zh_index);
				analyzer.add("zh_query", zh_query);
				zh_index.addProperty("type", "ansj_index");
				zh_query.addProperty("type", "ansj_query");

				Builder builder = new CreateIndex.Builder(indexName);
				builder.settings(jo);

				JestResult jr2 = jestClient.execute(builder.build());
				if (jr2.isSucceeded()) {
					res = 1;
				}
			} else {
				res = jr1.getResponseCode();
			}
		} else {
			res = 0;
		}

		return res;
	}

	public static boolean addMapping(JestClient jestClient, String indexName, String type) {
		boolean res = false;

		JsonObject source = new JsonObject();
		JsonObject properties = new JsonObject();
		source.add("properties", properties);
		for (String s : new String[] { "name", "age", "sex", "address" }) {
			JsonObject field = new JsonObject();
			field.addProperty("type", "string");
			properties.add(s, field);
		}

		PutMapping putMapping = new PutMapping.Builder(indexName, type, source.toString()).build();
		try {
			JestResult jr = jestClient.execute(putMapping);
			if (jr.isSucceeded()) {
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public static boolean deleteIndex(JestClient jestClient, String indexName) {
		boolean res = false;
		DeleteIndex deleteIndex = new DeleteIndex.Builder(indexName).build();
		try {
			JestResult jr = jestClient.execute(deleteIndex);
			if (jr.isSucceeded()) {
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public static boolean deleteMapping(JestClient jestClient, String indexName, String type) {
		boolean res = false;
		DeleteMapping deleteMapping = new DeleteMapping.Builder(indexName, type).build();
		try {
			JestResult jr = jestClient.execute(deleteMapping);
			if (jr.isSucceeded()) {
				res = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}
	
	public static String getMapping(JestClient jestClient, String indexName, String type) {
		String res = null;
		GetMapping getMapping = new GetMapping.Builder().addIndex(indexName).addType(type).build();
		try {
			JestResult jr = jestClient.execute(getMapping);
			if (jr.isSucceeded()) {
				res = jr.getJsonString();
				System.out.println(res);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static boolean addDoc(JestClient jestClient, String indexName, String type) throws Exception {
		boolean res = false;
		JsonObject source = new JsonObject();
		source.addProperty("name", "Tim");
		source.addProperty("age", "25");
		source.addProperty("sex", "male");
		source.addProperty("address", "Canton, Guangdong, China");
		
		String id = "200824131208";
		
		Index index = new Index.Builder(source).index(indexName).type(type).id(id).build();
		DocumentResult dr = jestClient.execute(index);
		if (dr.isSucceeded()) {
			res = true;
		}
		
		return res;
	}
	
	public static DocumentResult getDoc(JestClient jestClient, String indexName, String id) throws Exception {
		DocumentResult dr = null;
		
		Get get = new Get.Builder(indexName, id).build();
		dr = jestClient.execute(get);
		System.out.println(dr.getJsonString());
		
		return dr;
	}
	
	public static boolean deleteDoc(JestClient jestClient, String indexName,String type, String id) {
		boolean res = false;
		
		Delete delete = new Delete.Builder(id).index(indexName).type(type).build();
		try {
			DocumentResult dr = jestClient.execute(delete);
			if (dr.isSucceeded()) {
				res = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	public static void main(String[] args) throws Exception {
		JestClient jestClient = getJestClient();
		String indexName = "es_test";
		String type = "user";
		// createIndex(jestClient, indexName);
		// deleteIndex(jestClient, indexName);
		// addMapping(jestClient, indexName ,type);
		getMapping(jestClient, indexName, type);
		// deleteMapping(jestClient, indexName, type);
//		addDoc(jestClient, indexName, type);
//		getDoc(jestClient, indexName, "200824131208");
//		deleteDoc(jestClient, indexName, type, "200824131208");
		//http://www.xdemo.org/lucene4-8-ikanalyzer-springmvc4-jsoup-quartz/
		//http://blog.csdn.net/qian_348840260/article/details/20644445
	}
}
