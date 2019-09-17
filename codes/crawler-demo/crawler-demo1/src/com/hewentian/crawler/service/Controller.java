package com.hewentian.crawler.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @Description 线程在零时5分启动，00:05
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2014-8-1 上午10:43:41
 * @version 1.0
 * @since JDK 1.7
 */
public class Controller implements Runnable {
	private Logger log = Logger.getLogger(Controller.class);

	private String START_HOUR = "00"; // 启动的时
	private String START_MINUTE = "05"; // 启动的分钟

	@Override
	public void run() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

		while (true) {
			String hm = sdf.format(new Date());
			String h = hm.substring(0, 2);
			String m = hm.substring(3);

			if (START_HOUR.equals(h) && START_MINUTE.equals(m)) {
				new AppStoreService().start();
				new GooglePlayService().start();
			}

			try {
				log.info("Thread : " + Thread.currentThread().getName()
						+ " start to sleep 40 seconds.");
				Thread.sleep(40 * 1000); // 睡眠40秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		// Thread t = new Thread(new Controller());
		// t.setName("Controller");
		// t.start();
		new GooglePlayService().start();
		new AppStoreService().start();
	}
}