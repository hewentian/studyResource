package com.hewentian.cassandra;

/**
 * 
 * <p>
 * <b>Test</b> 是 Cassandra测试类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月19日 下午5:28:27
 * 
 */
public class CassandraServiceTest {
	public static void main(String[] args) {
		CassandraService cs = new CassandraService();
		cs.connect();
		// cs.createDb();
		// cs.alterDB();
		// cs.createTable();
		// cs.insertData();
		// cs.loadData();
		// cs.updateData();
		// cs.insertJson();
		// cs.loadJson();
		// cs.createIndex();
		// cs.deleteDate();
		// cs.deleteDate2();
		// cs.query();
		// cs.alterTableAddColumn();
		// cs.alterTableDropColumn();
		cs.close();
	}
}