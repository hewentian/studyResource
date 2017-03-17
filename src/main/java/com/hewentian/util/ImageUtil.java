package com.hewentian.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

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
}
