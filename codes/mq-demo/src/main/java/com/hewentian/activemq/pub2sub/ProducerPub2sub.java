package com.hewentian.activemq.pub2sub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * <p>
 * <b>ProducerPub2sub.java</b> 是
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2018-01-30 9:12:28 AM
 * @since JDK 1.8
 *
 */
public class ProducerPub2sub {
	public static void main(String[] args) throws Exception {
		String userName = "admin";
		String password = "admin";
		String brokerURL = "tcp://localhost:61616";

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);

		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		// 这里创建的是 Topic
		Destination destination = session.createTopic("helloTopic");
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		for (int i = 0; i < 100; i++) {
			TextMessage message = session.createTextMessage("message: " + i);
			producer.send(message);
		}

		System.out.println("100 message sent.");
		producer.close();
		connection.close();
	}
}
