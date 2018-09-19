package com.hewentian.es;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hewentian.util.EsJestUtil;

/**
 * 
 * <p>
 * <b>EsJestDemo</b> 是 {@linkplain com.hewentian.util.EsJestUtil EsJestUtil}
 * 工具类的测试
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2018-09-18 4:25:48 PM
 * @since JDK 1.8
 * 
 */
public class EsJestDemo {
	private static Logger log = Logger.getLogger(EsJestDemo.class);
	private static String indexName = "user_index";
	private static String typeName = "user";
	private static String id = "200824131208";

	public static void createIndex() {
		try {
			Map<String, Object> settings = new HashMap<String, Object>();
			settings.put("number_of_shards", 5); // default is 5
			settings.put("number_of_replicas", 2); // default is 1

			boolean res = EsJestUtil.createIndex(indexName, settings);
			log.info("res: " + res);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static void deleteteIndex() {

		boolean res = EsJestUtil.deleteIndex(indexName);
		log.info("res: " + res);

	}

	public static void putMappings() {
		Map<String, Object> mappings = new HashMap<String, Object>();
		Map<String, Object> properties = new HashMap<String, Object>();
		mappings.put("properties", properties);

		for (String s : new String[] { "name", "age", "sex", "address" }) {
			Map<String, String> field = new HashMap<String, String>();
			field.put("type", "keyword");
			properties.put(s, field);
		}

		boolean res = EsJestUtil.putMappings(indexName, typeName, mappings);
		log.info("res: " + res);
	}

	public static void deleteMapping() {
		boolean res = EsJestUtil.deleteMapping(indexName, typeName);
		log.info("res: " + res);
	}

	public static void getMapping() {
		JsonObject res = EsJestUtil.getMapping(indexName, typeName);
		log.info("res: " + res);
	}

	public static void addDoc() {
		JsonObject source = new JsonObject();
		source.addProperty("name", "Tim");
		source.addProperty("age", "23");
		source.addProperty("sex", "male");
		source.addProperty("address", "Canton, Guangdong, China");
		source.addProperty("birthday", "1989.06.30");

		boolean res = EsJestUtil.addDoc(indexName, typeName, source, id);
		log.info("res: " + res);
	}

	public static void getDoc() {
		JsonObject res = EsJestUtil.getDoc(indexName, id);
		log.info("res: " + res);
	}

	public static void updateDoc() {
		Map<String, String> updateFields = new HashMap<String, String>();
		updateFields.put("address", "Guangzhou, Guangdong, China");

		boolean res = EsJestUtil.updateDoc(indexName, typeName, id, updateFields);
		log.info("res: " + res);
	}

	public static void search() {
		JsonObject json = new JsonObject();
		JsonObject query = new JsonObject();
		JsonObject bool = new JsonObject();
		JsonArray must = new JsonArray();

		JsonObject mustField1 = new JsonObject();
		JsonObject name = new JsonObject();
		name.addProperty("name", "Tim");
		mustField1.add("term", name);

		json.add("query", query);
		json.addProperty("from", 0);
		json.addProperty("size", 10);
		query.add("bool", bool);
		bool.add("must", must);
		must.add(mustField1);

		JsonObject jsonObject = EsJestUtil.search(indexName, typeName, json);

		JsonObject hits = jsonObject.getAsJsonObject("hits");

		log.info("total:" + hits.get("total").getAsLong());
		JsonArray hitArray = hits.getAsJsonArray("hits");
		hitArray.forEach(h -> log.info(h));
	}

	public static void deleteDoc() {
		boolean res = EsJestUtil.deleteDoc(indexName, typeName, id);
		log.info("res: " + res);
	}

	public static void main(String[] args) throws Exception {
		// createIndex();
		// deleteteIndex();
		// putMappings();
		// deleteMapping();
		// getMapping();
		// addDoc();
		// getDoc();
		// updateDoc();
		// search();
		// deleteDoc();
		// http://www.xdemo.org/lucene4-8-ikanalyzer-springmvc4-jsoup-quartz/
	}
}
