package com.hewentian.util;

public class LinuxUtilTest {
	public static void main(String[] args) {
		String res = null;
		res = LinuxUtil.exec("ps -ef | grep java");
		System.out.println(res);

		res = LinuxUtil.exec("ls");
		System.out.println(res);

		res = LinuxUtil.exec("./test.sh");
		System.out.println(res);
	}
}