package com.hewentian.activemq.bean;

import java.util.Arrays;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * <p>
 * <b>ProducerBean.java</b> 是
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2018-01-28 7:52:37 PM
 * @since JDK 1.8
 *
 */
public class ProducerBean {
	public static void main(String[] args) throws Exception {
		String userName = ActiveMQConnection.DEFAULT_USER; // null
		String password = ActiveMQConnection.DEFAULT_PASSWORD; // null
		String brokerURL = ActiveMQConnection.DEFAULT_BROKER_URL; // failover://tcp://localhost:61616

		// 如果直接用上面的配置，只有在本地起作用，实际情况是ActiveMQ很少在本地
		userName = "admin";
		password = "admin";
		brokerURL = "tcp://localhost:61616";

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		// connectionFactory.setTrustedPackages(Arrays.asList("com.hewentian.activemq.bean"));
		connectionFactory.setTrustAllPackages(true);

		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("hello");
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		User user = new User();
		for (int i = 0; i < 100; i++) {
			user.setId(i);
			user.setAge(i + 10);
			user.setName("user" + i);

			producer.send(session.createObjectMessage(user));
		}

		System.out.println("100 message sent.");
		producer.close();
		connection.close();
	}
}
