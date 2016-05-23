package com.hewentian.cassandra;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.TableMetadata;
import com.hewentian.util.FileUtil;

/**
 * 
 * <p>
 * <b>CassandraService</b> 是 Cassandra服务类, 主要使用了datastax公司提供的驱动包实现
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月19日 下午5:19:58
 * 
 */
public class CassandraService {
	/** Cassandra 集群 */
	private Cluster cluster;
	
	/** Cassandra session */
	private Session session;

	public void connect() {
		// 下面是连接池版本
		int port = ProtocolOptions.DEFAULT_PORT; // 默认端口，9042
		String[] server = { "127.0.0.1" };
		PoolingOptions options = new PoolingOptions();
		options.setCoreConnectionsPerHost(HostDistance.LOCAL, 4)
				.setMaxConnectionsPerHost(HostDistance.LOCAL, 10)
				.setCoreConnectionsPerHost(HostDistance.REMOTE, 2)
				.setMaxConnectionsPerHost(HostDistance.REMOTE, 4);
		cluster = Cluster.builder().addContactPoints(server).withPort(port)
				.withPoolingOptions(options).build();

		// 下面的是简单版本，可以忽略上面的
		// cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

		Metadata metadata = cluster.getMetadata();
		System.out.println("Connect to cluster: " + metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.println("Datacenter: " + host.getDatacenter()
					+ ", Host: " + host.getAddress() + ", Rack: "
					+ host.getRack());
		}

		List<KeyspaceMetadata> keyspaceMetadatas = metadata.getKeyspaces();
		for (KeyspaceMetadata km : keyspaceMetadatas) {
			System.out
					.println("\n--------------------------------\nkeyspace name: "
							+ km.getName());
			Collection<TableMetadata> tableMetadatas = km.getTables();
			for (TableMetadata tm : tableMetadatas) {
				System.out.println("table name: " + tm.getName());
				System.out.println("table cql:" + tm.asCQLQuery());
			}
		}

		session = cluster.connect();
	}

	public void createDb() {
		ResultSet rs = getSession()
				.execute(
						"CREATE KEYSPACE IF NOT EXISTS hewentian  WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 3}");
		System.out.println(rs);
	}

	public void createTable() {
		getSession().execute("DROP TABLE IF EXISTS hewentian.user;");

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS hewentian.user (");
		sb.append("id text,");
		sb.append("name varchar,");
		sb.append("age int,");
		sb.append("avatar blob,");
		sb.append("data map<text,text>,");
		sb.append("PRIMARY KEY (id)");
		sb.append(");");

		ResultSet rs = getSession().execute(sb.toString());
		System.out.println(rs);
	}

	/**
	 * 在列上面建索引，Cassandra 里面只能查询主建和有索引的列
	 */
	public void createIndex() {
		getSession()
				.execute(
						"CREATE INDEX IF NOT EXISTS index_name ON hewentian.user(name)");
	}

	public boolean insertData() {
		PreparedStatement ps = getSession()
				.prepare(
						"INSERT INTO hewentian.user(id,name,age,data,avatar) VALUES(?,?,?,?,?);");
		BoundStatement bs = new BoundStatement(ps);

		ByteBuffer avatar = null;

		try {
			avatar = FileUtil.readByteBufferFromFile("e:\\1.png");
			System.out.println("头像大小: " + avatar.capacity());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");

		ResultSet rs1 = getSession().execute(
				bs.bind("1", "tim.ho", 20, map, avatar));
		ResultSet rs2 = getSession().execute(
				bs.bind("2", "tim.ho2", 21, map, avatar));
		System.out.println(rs1);
		System.out.println(rs2);

		rs1.wasApplied();
		return rs1.isExhausted();
	}

	public void loadData() {
		ResultSet rs = getSession().execute(
				"SELECT * FROM hewentian.user WHERE id='1';");
		System.out.println("res=" + rs.one());
		rs = getSession().execute("SELECT * FROM hewentian.user;");
		for (Row row : rs) {
			System.out.println(row.getString("id") + "\t"
					+ row.getString("name") + "\t" + row.getInt("age") + "\t"
					+ row.getMap("data", String.class, String.class));

			ByteBuffer avatar = row.getBytes("avatar");
			System.out.println("头像大小: " + (avatar.limit() - avatar.position()));

			try {
				System.out.println(UUID.randomUUID().toString());
				FileUtil.writeByteBufferToFile(avatar,
						"e:\\" + System.currentTimeMillis() + ".png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteDate() {
		ResultSet rs = getSession().execute(
				"DELETE FROM hewentian.user WHERE id='2'");
		System.out.println(rs.isExhausted());
	}

	public ResultSet getData(String query, Object... params) {
		ResultSet rs = null;
		if (null == params) {
			rs = getSession().execute(query);
		} else {
			rs = getSession().execute(query, params);
		}

		return rs;
	}

	public void query() {
		ResultSet rs = getData("SELECT * FROM hewentian.user");
		for (Row row : rs) {
			System.out.println("id=" + row.getString("id") + ", name="
					+ row.getString("name"));
		}

		rs = getData("SELECT * FROM hewentian.user WHERE name = ?", "tim.ho");
		for (Row row : rs) {
			System.out.println("id=" + row.getString("id") + ", name="
					+ row.getString("name"));
		}
	}

	public void close() {
		session.close();
		cluster.close();
	}

	public Cluster getCluster() {
		return cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}