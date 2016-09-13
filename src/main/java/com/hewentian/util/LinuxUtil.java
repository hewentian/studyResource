package com.hewentian.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 
 * <p>
 * <b>LinuxUtil</b> 是 Linux下面执行命令的工具类
 * </p>
 * 
 * Runtime.getRuntime().exec()这种调用方式在java虚拟机中是十分消耗资源的，即使命令可以很快的执行完毕，<br />
 * 频繁的调用时创建进程消耗十分客观 。java虚拟机执行这个命令的过程是，首先克隆一个和当前虚拟机拥有一样环境变量的进程，<br />
 * 再用这个新的进程执行外部命令，最后退出这个进程。 频繁的创建对CPU和内存的消耗很大
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年9月12日 下午2:30:58
 * @since JDK 1.7
 * 
 */
public class LinuxUtil {
	public static String exec(String cmd) {
		String res = null;
		LineNumberReader lnr = null;

		try {
			String[] cmdA = { "/bin/sh", "-c", cmd };
			Process process = Runtime.getRuntime().exec(cmdA);

			// 简单命令可以这样执行：
			// Process process2 = Runtime.getRuntime().exec("ls -l");

			process.waitFor();
			lnr = new LineNumberReader(new InputStreamReader(process.getInputStream()));

			StringBuilder sb = new StringBuilder();
			String readLine;
			while ((readLine = lnr.readLine()) != null) {
				sb.append(readLine + "\n");
			}
			res = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != lnr) {
				try {
					lnr.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		return res;
	}
}