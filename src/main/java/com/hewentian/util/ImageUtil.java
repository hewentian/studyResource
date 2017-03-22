package com.hewentian.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * <b>ImageUtil</b> 是 图片工具类
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年3月17日 上午10:01:55
 * @since JDK 1.8
 *
 */
public class ImageUtil {
	private static Logger log = Logger.getLogger(ImageUtil.class);
	
	/**
	 * 获取图片宽高
	 * 
	 * @date 2017年3月17日 上午10:05:42
	 * @param filepath
	 * @return
	 */
	public static Integer[] getWidthHeight(String filepath) {
		Integer[] wh = null;

		try {
			File file = new File(filepath);

			BufferedImage bi = ImageIO.read(new FileInputStream(file));
			wh = new Integer[] { bi.getWidth(), bi.getHeight() };
		} catch (Exception e) {
			e.printStackTrace();
		}

		return wh;
	}

	public static Integer[] getCmykWidthHeight(String filepath) {
		Integer[] wh = null;

		try {
			wh = CMYK.getWidthHeight(filepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wh;
	}
	
	/**
	 * 下载图片, 可以下载重定向的图片
	 * 
	 * @date 2017年3月11日 上午9:19:03
	 * @param imageUrl 图片的URL
	 * @param localPath 本地的文件路径
	 */
	public static boolean download(String imageUrl, String localPath) {
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			int rCode = conn.getResponseCode();
			if (!String.valueOf(rCode).startsWith("2")) {
				conn.setInstanceFollowRedirects(false);
				conn.setConnectTimeout(5000);
				String imageUrl_ = conn.getHeaderField("Location");
				if (null != imageUrl_) {
					url = new URL(imageUrl_);
					conn = (HttpURLConnection) url.openConnection();
				}
			}

			InputStream is = conn.getInputStream();

			FileUtil.checkParentPath(localPath, true);
			FileOutputStream fos = new FileOutputStream(new File(localPath));

			byte[] buf = new byte[1024];
			int length = 0;

			log.info("开始下载:" + imageUrl);
			while ((length = is.read(buf, 0, buf.length)) != -1) {
				fos.write(buf, 0, length);
			}

			fos.close();
			is.close();
			conn.disconnect();
			log.info(localPath + " 下载完成");

			return true;
		} catch (Exception e) {
			log.error(e + "#################################\t" + imageUrl);
		}

		return false;
	}
}
