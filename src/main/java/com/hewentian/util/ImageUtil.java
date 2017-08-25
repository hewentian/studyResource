package com.hewentian.util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

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

	/**
	 * 获取CMYK图片的宽高, 超时时间为 60 秒
	 * 
	 * @date 2017年3月20日 上午10:47:54
	 * @param filepath
	 * @return
	 */
	public static Integer[] getCmykWidthHeight(final String filepath) {
		Integer[] wh = null;

		Callable<Integer[]> task = new Callable<Integer[]>() {
			@Override
			public Integer[] call() throws Exception {
				return CMYK.getWidthHeight(filepath);
			}
		};

		ExecutorService executorService = null;

		try {
			executorService = Executors.newSingleThreadExecutor();
			Future<Integer[]> future = executorService.submit(task);
			wh = future.get(60, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				executorService.shutdown();
			} catch (Exception e2) {
				log.error(e2);
			}
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
			imageUrl = encodeUrl(imageUrl);
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
	
	/**
	 * 将URL中的汉字编码成UTF-8格式, 如果出错，则返回原来的url
	 * 
	 * @date 2017年5月4日 下午2:45:39
	 * @param url
	 * @return
	 */
	public static String encodeUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return url;
		}

		try {
			StringBuffer sb = new StringBuffer(url.length());
			char[] c = url.toCharArray();
			for (int i = 0, len = c.length; i < len; i++) {
				String s = String.valueOf(c[i]);
				if ((c[i] >= 0x4e00) && (c[i] <= 0x9fbb)) {
					s = URLEncoder.encode(s, "UTF-8");
				}

				sb.append(s);
			}

			return sb.toString();
		} catch (Exception e) {
			log.error(e);
			return url;
		}
	}

	/**
	 * 将图片旋转，旋转后，会丢失图片 EXIF 信息
	 * 注意：不是所有的图片都能成功转
	 * @date 2017年8月25日 上午10:44:26
	 * @param srcPath 源图片位置
	 * @param destPath 旋转后的图片的保存位置
	 * @param degree 旋转度数，仅支持 90, 180, 270, 360
	 */
	public static void rotate(String srcPath, String destPath, int degree) throws Exception {
		File srcFile = new File(srcPath);
		BufferedImage srcImg = (BufferedImage) ImageIO.read(srcFile);
		int w = srcImg.getWidth();
		int h = srcImg.getHeight();

		BufferedImage destImg = null;
		if (degree == 90 || degree == 270) {
			destImg = new BufferedImage(h, w, BufferedImage.TYPE_INT_BGR);
		} else {
			destImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);
		}

		Graphics2D g2d = destImg.createGraphics();

		AffineTransform srcXform = g2d.getTransform();
		AffineTransform destXform = (AffineTransform) (srcXform.clone());

		// center of rotation is center of the panel
		double xRot = w / 2.0;
		destXform.rotate(Math.toRadians(degree), xRot, xRot);

		g2d.setTransform(destXform);
		// draw image centered in panel
		g2d.drawImage(srcImg, 0, 0, null);
		// Reset to Original
		g2d.setTransform(srcXform);

		// 写到新的文件
		FileOutputStream out = new FileOutputStream(destPath);
		try {
			ImageIO.write(destImg, "JPG", out);
		} finally {
			out.close();
		}
	}

	/**
	 * 读取图片的 EXIF 信息
	 * @date 2017年8月25日 上午10:58:05
	 * @param file
	 * @throws Exception
	 */
	public static void readExif(File file) throws Exception {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
//		 Directory directory =
//		 metadata.getFirstDirectoryOfType(ExifDirectoryBase.class
//		 );

		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				System.out.format("[%s] - %s = %s \n", directory.getName(), tag.getTagName(), tag.getDescription());
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
//		rotate("f://a1.jpg", "f://a3.jpg", 360);
		readExif(new File("f:/ff2.jpg"));
	}
}
