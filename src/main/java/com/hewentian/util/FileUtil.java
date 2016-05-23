package com.hewentian.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * <p>
 * <b>FileUtil</b> 是 文件工具类
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年5月20日 下午6:03:24
 * 
 */
public class FileUtil {
	/**
	 * 
	 * 将一个文件读出并保存在一个ByteBuffer中
	 * 
	 * @date 2016年5月20日 下午6:04:00
	 * @param filepath
	 * @return
	 * @throws IOException
	 */
	public static ByteBuffer readByteBufferFromFile(String filepath)
			throws IOException {
		File file = new File(filepath);
		FileChannel fileChannel = null;
		ByteBuffer buf = ByteBuffer.allocate((int) file.length());

		try {
			fileChannel = new FileInputStream(file).getChannel();
			fileChannel.read(buf);
			buf.rewind();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fileChannel) {
					fileChannel.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return buf;
	}

	/**
	 * 将byteBuffer写到指定文件中
	 * 
	 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
	 * @date 2016年5月23日 下午3:33:27
	 * @param byteBuffer
	 * @param filepath 文件路径
	 * @return
	 * @throws IOException
	 */
	public static boolean writeByteBufferToFile(ByteBuffer byteBuffer,
			String filepath) throws IOException {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filepath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		try {
			fileOutputStream.write(byteBuffer.array(), byteBuffer.position(),
					byteBuffer.limit() - byteBuffer.position());
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}