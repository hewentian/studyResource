package com.hewentian.solr;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Correction;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;

import com.hewentian.solr.entity.User;
import com.hewentian.solr.util.SolrUtils;

/**
 * 
 * <p>
 * <b>App</b> 是 一个简单的例子
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年4月23日 下午2:11:54
 * @since JDK 1.8
 *
 */
public class App {
	private static Logger logger = Logger.getLogger(App.class);

	private static String baseSolrUrl = "http://localhost:8983/solr/";
	private static String collection = "mysqlCore";
	private static String zkHost = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

	public static void main(String[] args) throws Exception {
		// addDocMap();
		// addDocBean();
		// deleteDocument();
		// queryDocument();
		// queryBeans();
		// getSpell();
		// facetQuery();
		// groupQuery();
//		simpleQuery();
		cloudQuery();
	}

	public static void addDocMap() throws Exception {
		Map<String, Object> docMap = new HashMap<String, Object>();
		docMap.put("id", "5"); //
		docMap.put("name", "陈六");
		docMap.put("age", 50);
		docMap.put("ability", "小学老师");
		docMap.put("address", "北京市三环");
		docMap.put("update_time", new Date());
		docMap.put("schools", Arrays.asList("北京一小", "北京中学"));

		UpdateResponse response = SolrUtils.addDocument(baseSolrUrl, collection, docMap);
		logger.info(response);
	}

	public static void addDocBean() throws Exception {
		User user = new User();
		user.setId("6");
		user.setName("李七");
		user.setAge(50);
		user.setAbility("初中老师");
		user.setAddress("北京市四环");
		user.setUpdate_time(new Date());
		user.setSchools(Arrays.asList("北京二小", "北京第一中学"));

		UpdateResponse response = SolrUtils.addDocument(baseSolrUrl, collection, user);
		logger.info(response);
	}

	public static void deleteDocument() throws Exception {
		UpdateResponse response = SolrUtils
				.deleteDocument(baseSolrUrl, collection, "359de0a9-5cdd-44f4-8d41-c39a8eddc49b");
		logger.info(response);
	}

	public static void queryDocument() {
		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setQuery("*:*");
		// solrQuery.setQuery("name:张三");

		// filter 查询
		solrQuery.addFilterQuery("id:[1 TO 3]");

		// 排序
		solrQuery.setSort("id", SolrQuery.ORDER.asc);

		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(10);

		// 设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name");

		// 设置高亮样式
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");

		SolrDocumentList results = SolrUtils.query(baseSolrUrl, collection, solrQuery);

		if (CollectionUtils.isEmpty(results)) {
			return;
		}
		logger.info("numFound: " + results.getNumFound());

		results.stream().forEach(u -> logger.info(u));
	}

	public static void queryBeans() {
		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setQuery("*:*");
		// solrQuery.setQuery("name:张三");

		// filter 查询
		solrQuery.addFilterQuery("id:[1 TO 3]");

		// 排序
		solrQuery.setSort("id", SolrQuery.ORDER.asc);

		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(10);

		List<User> results = SolrUtils.queryBeans(baseSolrUrl, collection, solrQuery);

		if (CollectionUtils.isEmpty(results)) {
			return;
		}

		logger.info("numFound: " + results.size());

		results.stream().forEach(u -> logger.info(u));
	}

	public static void getSpell() throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("qt", "/spell");
		solrQuery.set("q", "工程");

		QueryResponse response = SolrUtils.getQueryResponse(baseSolrUrl, collection, solrQuery);
		SolrDocumentList documentList = response.getResults();

		if (0 == documentList.getNumFound()) {
			SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
			// TODO: 不知道为什么，这里一直无法有建议条数
			List<Collation> collatedResults = spellCheckResponse.getCollatedResults();
			collatedResults.stream().forEach(c -> {
				long numberOfHits = c.getNumberOfHits();
				logger.info("建议条数为：" + numberOfHits);

				List<Correction> misspellingsAndCorrections = c.getMisspellingsAndCorrections();
				misspellingsAndCorrections.stream().forEach(m -> {
					logger.info("原始的输入为：" + m.getOriginal() + ", 推荐词语为：" + m.getCorrection());
				});
			});
		} else {
			documentList.stream().forEach(d -> logger.info(d));
		}
	}

	public static void facetQuery() throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("老师");
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1); // 统计结果大于 1 的才会返回
		solrQuery.setFacetLimit(10); // 对于每一个 field 最多返回 10 个结果
		solrQuery.addFacetField("age", "name");
		solrQuery.setRows(0); // 设置返回结果条数，如果分组查询，就设置为0

		QueryResponse queryResponse = SolrUtils.getQueryResponse(baseSolrUrl, collection, solrQuery);
		List<FacetField> facetFields = queryResponse.getFacetFields();
		facetFields.stream().forEach(ff -> {
			ff.getValues().stream().forEach(c -> {
				System.out.println(c.getName() + ", " + c.getCount());
			});
			System.out.println();
		});
	}

	public static void groupQuery() throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		solrQuery.set(GroupParams.GROUP, true);
		solrQuery.set(GroupParams.GROUP_FIELD, "age", "name");
		solrQuery.set(GroupParams.GROUP_LIMIT, 3);
		solrQuery.set(GroupParams.GROUP_FORMAT, "grouped");
		solrQuery.set(GroupParams.GROUP_MAIN, false);
		solrQuery.set(GroupParams.GROUP_TOTAL_COUNT, true);
		solrQuery.set(GroupParams.GROUP_SORT, "age asc");

		QueryResponse queryResponse = SolrUtils.getQueryResponse(baseSolrUrl, collection, solrQuery);
		// 通过GroupResponse获取GroupCommand，进而通过GroupCommand获取Group，而Group又可以获得SolrDocumentList
		GroupResponse groupResponse = queryResponse.getGroupResponse();
		List<GroupCommand> groupCommands = groupResponse.getValues();
		for (GroupCommand gc : groupCommands) {
			System.out.println("\n########## group name: " + gc.getName());
			// 只有设置 GroupParams.GROUP_TOTAL_COUNT 这里 getNGroups 才有效果
			System.out.println("########## Num of Groups Found: " + gc.getNGroups());
			System.out.println("########## Num of documents Found: " + gc.getMatches());

			List<Group> groups = gc.getValues();
			for (Group g : groups) {
				SolrDocumentList solrDocumentList = g.getResult();
				System.out.println("\n########## group name: " + gc.getName() + ", groupValue: " + g.getGroupValue()
						+ ", Num of Documents in this group: " + solrDocumentList.getNumFound());
				if (CollectionUtils.isNotEmpty(solrDocumentList)) {
					for (SolrDocument sd : solrDocumentList) {
						System.out.println(sd);
					}
				}
			}
		}
	}

	public static void simpleQuery() throws Exception {
		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setQuery("name:张三 AND id:[1 TO 3]");

		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(10);

		SolrDocumentList results = SolrUtils.query(baseSolrUrl, collection, solrQuery);

		if (CollectionUtils.isEmpty(results)) {
			return;
		}
		logger.info("numFound: " + results.getNumFound());

		results.stream().forEach(logger::info);
	}
	
	public static void cloudQuery() throws Exception {
		CloudSolrClient cloudSolrClient = SolrUtils.getCloudSolrClient(zkHost, collection);
		
		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setQuery("schools:\"北京第一中学\" AND id:[1 TO 3]");

		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(10);

		SolrDocumentList results = cloudSolrClient.query(solrQuery).getResults();

		logger.info("numFound: " + results.getNumFound());
		if (CollectionUtils.isEmpty(results)) {
			return;
		}

		results.stream().forEach(logger::info);
	}
}
