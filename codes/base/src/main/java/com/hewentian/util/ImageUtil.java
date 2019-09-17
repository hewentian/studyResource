package com.hewentian.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;

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
	 * @param imageUrl
	 *            图片的URL
	 * @param localPath
	 *            本地的文件路径
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
	 * 将图片旋转，旋转后，会丢失图片 EXIF 信息 注意：不是所有的图片都能成功转
	 * 
	 * @date 2017年8月25日 上午10:44:26
	 * @param srcPath
	 *            源图片位置
	 * @param destPath
	 *            旋转后的图片的保存位置
	 * @param degree
	 *            旋转度数，仅支持 90, 180, 270, 360
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
	 * 给图片加上水印，会丢失图片 EXIF 信息
	 * 
	 * @date 2017年8月25日 上午11:34:33
	 * @param srcPath
	 *            要加水印的图片
	 * @param destPath
	 *            加水印后的图片位置
	 * @param waterMark
	 *            水印的字符
	 * @param x
	 *            x位置
	 * @param y
	 *            y位置
	 * @throws Exception
	 */
	public static void addWaterMark(String srcPath, String destPath, String waterMark, int x, int y) throws Exception {
		ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(srcPath));
		ImageReader reader = (ImageReader) ImageIO.getImageReaders(iis).next();
		reader.setInput(iis);

		BufferedImage image = reader.read(0);
		Graphics graphics = image.getGraphics();
		graphics.setColor(new Color(240, 100, 255, 255));
		graphics.drawString(waterMark, x, y);

		// Save modified image
		ImageIO.write(image, "JPG", new FileOutputStream(destPath));
		iis.close();
	}

	/**
	 * 读取图片的 EXIF 信息
	 * 
	 * @date 2017年8月25日 上午10:58:05
	 * @param file
	 * @throws Exception
	 */
	public static void readExif(File file) throws Exception {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		// Directory directory =
		// metadata.getFirstDirectoryOfType(ExifDirectoryBase.class
		// );

		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				System.out.format("[%s] - %s = %s \n", directory.getName(), tag.getTagName(), tag.getDescription());
			}
		}
	}

	/**
	 * tif to jpg
	 * 
	 * @date 2017-12-27 11:03:28 PM
	 * @since JDK 1.8
	 * @param src
	 *            tif文件路径，如：/home/hewentian/Documents/a.tif
	 * @param dest
	 *            jpg文件路径，如：/home/hewentian/Documents/a.jpg
	 * @throws IOException
	 * @throws Exception
	 */
	public static void tifToJpg(String src, String dest) throws IOException {
		// 创建父目录，如果不存在的话
		File destFile = new File(dest);
		FileUtils.forceMkdirParent(destFile);

		RenderedOp renderedOp = JAI.create("fileload", src);
		OutputStream os = new FileOutputStream(destFile);
		JPEGEncodeParam param = new JPEGEncodeParam();
		ImageEncoder imageEncoder = ImageCodec.createImageEncoder("JPEG", os, param);

		try {
			imageEncoder.encode(renderedOp);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static void tifToJpg2(String src, String dest) throws IOException {
		BufferedImage tif = ImageIO.read(new File(src));
		ImageIO.write(tif, "jpg", new File(dest));
	}

	/**
	 * converter tif to bmp
	 * 
	 * @date 2017-12-27 11:10:21 PM
	 * @since JDK 1.8
	 * @param src
	 *            tif文件路径，如：/home/hewentian/Documents/a.tif
	 * @param dest
	 *            bmp文件路径，如：/home/hewentian/Documents/a.bmp
	 * @throws IOException
	 */
	public static void tifToBmp(String src, String dest) throws IOException {
		// 创建父目录，如果不存在的话
		File destFile = new File(dest);
		FileUtils.forceMkdirParent(destFile);

		RenderedOp renderedOp = JAI.create("fileload", src);
		OutputStream os = new FileOutputStream(destFile);
		BMPEncodeParam param = new BMPEncodeParam();
		ImageEncoder imageEncoder = ImageCodec.createImageEncoder("BMP", os, param);

		try {
			imageEncoder.encode(renderedOp);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 将一个目录下的所有TIF文件，转换成另一个目录下的JPG文件
	 * 
	 * @date 2017-12-28 11:32:19 AM
	 * @since JDK 1.8
	 * @param tifDir
	 *            存放有TIF文件的目录
	 * @param jpgDir
	 *            保存JPG文件的目录
	 * @param recursive
	 *            查找TIF文件的时候是否查找子目录
	 * @throws IOException
	 */
	public static void tifDirToJpgDir(String tifDir, String jpgDir, boolean recursive) throws IOException {
		File tifDirFile = new File(tifDir);
		if (!tifDirFile.exists() || !tifDirFile.isDirectory()) {
			log.error("源文件夹必须存在，且必须为目录");
			throw new IOException("源文件夹必须存在，且必须为目录");
		}

		Collection<File> listFiles = FileUtils.listFiles(tifDirFile, new String[] { "tif" }, recursive);
		for (File srcFile : listFiles) {
			String srcFilePath = srcFile.getPath();
			String destFilePath = StringUtils.replace(srcFilePath, tifDir, jpgDir);
			destFilePath = FilenameUtils.removeExtension(destFilePath);
			destFilePath += ".jpg";

			tifToJpg(srcFilePath, destFilePath);
		}
	}

	/**
	 * tif to jpg, 可以处理tif多页的情况
	 * 
	 * @date 2017-12-28 4:25:15 PM
	 * @since JDK 1.8
	 * @param tiffPath
	 *            tif文件路径，如：/home/hewentian/Documents/a.tif
	 * @throws IOException
	 */
	public static void tifToJpg(String tiffPath) throws IOException {
		FileSeekableStream fss = new FileSeekableStream(tiffPath);
		ImageDecoder imageDecoder = ImageCodec.createImageDecoder("tiff", fss, null);
		int count = imageDecoder.getNumPages();

		if (log.isInfoEnabled()) {
			log.info("tiff count: " + count);
		}

		JPEGEncodeParam param = new JPEGEncodeParam();
		// 将多张图片在当前目录保存，以原文件名为文件夹名
		String savePath = FilenameUtils.removeExtension(tiffPath);
		String tiffName = FilenameUtils.getBaseName(tiffPath);

		for (int i = 0; i < count; i++) {
			RenderedImage page = null;
			File f = new File(savePath, tiffName + "_" + i + ".jpg");

			try {
				page = imageDecoder.decodeAsRenderedImage(i);
				if (!f.exists()) {
					f.getParentFile().mkdirs();
					f.createNewFile();
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}

			if (f.exists() && page != null) {
				ParameterBlock pb = new ParameterBlock();
				pb.addSource(page);
				pb.add(f.toString());
				pb.add("JPEG");
				pb.add(param);

				RenderedOp r = JAI.create("filestore", pb);
				r.dispose();
			}
		}
	}

	/**
	 * tif to jpg, 可以处理tif多页的情况，采用JDK自带方法
	 * 
	 * @date 2017-12-28 3:58:25 PM
	 * @since JDK 1.8
	 * @param tiffPath
	 *            tif文件路径，如：/home/hewentian/Documents/a.tif
	 */
	public static void tifToJpgByImageIO(String tiffPath) {
		ImageInputStream input = null;
		ImageReader reader = null;

		try {
			// 以图片输入流形式读取到tif
			input = ImageIO.createImageInputStream(new File(tiffPath));

			// 获得image阅读器，阅读对象为tif文件转换的流
			reader = ImageIO.getImageReaders(input).next();
			reader.setInput(input);

			// 将多张图片在当前目录保存，以原文件名为文件夹名
			String savePath = FilenameUtils.removeExtension(tiffPath);
			String tiffName = FilenameUtils.getBaseName(tiffPath);

			int count = reader.getNumImages(true);// tif文件页数
			if (log.isInfoEnabled()) {
				log.info("tiff count: " + count);
			}

			for (int i = 0; i < count; i++) {
				BufferedImage image = reader.read(i, null);
				File f = new File(savePath, tiffName + "_" + i + ".jpg");
				try {
					if (!f.exists()) {
						f.getParentFile().mkdirs();
						f.createNewFile();
					}

					ImageIO.write(image, "JPEG", f);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				reader.dispose();
				input.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// rotate("/home/hewentian/Documents/b.jpg", "/home/hewentian/Documents/bb.jpg",
		// 180);
		// readExif(new File("/home/hewentian/Documents/b.jpg"));
		// addWaterMark("/home/hewentian/Documents/b.jpg",
		// "/home/hewentian/Documents/bb.jpg", "Hello World.", 20, 40);

		// String tifDir = "/home/hewentian/Documents/tif/";
		// String jpgDir = "/home/hewentian/Documents/jpg/";
		// String src = "/home/hewentian/Documents/tif/tif2/b.tif";
		// tifDirToJpgDir(tifDir, jpgDir, true);
		// tifToJpg(src);
	}
}
