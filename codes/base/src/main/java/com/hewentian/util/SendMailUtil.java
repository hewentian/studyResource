package com.hewentian.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * <b>SendMailUtil</b> 是 发送邮件工具类，要发送邮件，直接用 {@link #send(String, String, String)
 * send} 方法即可
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年7月21日 上午10:30:32
 * @since JDK 1.7
 * 
 */
public class SendMailUtil {
	private static Logger logger = Logger.getLogger(SendMailUtil.class);

	/** smtp服务器 */
	private static String smtpHost;

	/** 用户名 */
	private static String user;

	/** 密码 */
	private static String password;

	/** 发件者，一般为{@link #user} */
	private static String from;

	static {
		try {
			smtpHost = "smtp.126.com";
			user = "wt20110302@126.com";
			password = ""; // 密码在这里就保密了，大家用自已的邮箱来发就可以了， 记得要开启SMTP服务
			from = user;
		} catch (Exception e) {
			logger.error("获取smtp服务器, user, password", e);
		}
	}

	private SendMailUtil() {
	}

	/**
	 * 发送邮件
	 * 
	 * @date 2016年7月21日 上午10:57:29
	 * @param to
	 *            收件者,多个邮箱之间用逗号分隔
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @return 成功：true; 失败：false
	 * @throws Exception
	 */
	public static boolean send(String to, String subject, String content) throws Exception {
		boolean result = false;

		try {
			// 创建一个属性对象
			Properties props = new Properties();

			// 指定Smtp服务器
			props.put("mail.smtp.host", smtpHost);

			// 指定是否需要验证
			props.put("mail.smtp.auth", "true");

			// 创建一个验证对象
			SmtpAuth auth = new SmtpAuth();
			auth.setAccount(user, password);

			// 创建一个session
			Session mailSession = Session.getDefaultInstance(props, auth);
			mailSession.setDebug(true);

			// 创建一个message的对象
			Message message = new MimeMessage(mailSession);

			// 制定发件人
			message.setFrom(new InternetAddress(from));

			// 指定收件人
			for (String tos : to.split(",")) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(tos.trim()));
			}

			// 指定主题
			message.setSubject(subject);

			// 指定内容
			message.setText(content);

			// 指定日期
			message.setSentDate(new Date());

			// 指定邮件的优先级别 1紧急 3普通 5缓慢
			message.setHeader("X-Priority", "1");
			message.saveChanges();

			// 创建一个Transport对象
			Transport transport = mailSession.getTransport("smtp");

			// 连接服务器
			transport.connect(smtpHost, user, password);

			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			result = true;
			logger.debug("发送邮件成功, title:" + subject);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送邮件出错", e);
			result = false;
			throw e;
		}

		return result;
	}

	static class SmtpAuth extends Authenticator {
		String user;
		String password;

		void setAccount(String user, String password) {
			this.user = user;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password);
		}
	}
}