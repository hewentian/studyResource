package com.hewentian.util;

public class WindowsUtilTest {
	public static void main(String[] args) {
		String res = null;
		res = WindowsUtil.exec(WindowsUtil.CmdType.C, "dir");
		System.out.println(res);

		// 如果在列出的目录下面有 js 目录， 那可以
		res = WindowsUtil.exec(WindowsUtil.CmdType.C, "dir js");
		System.out.println(res);

		res = WindowsUtil.exec(WindowsUtil.CmdType.C, "explorer");
		System.out.println(res);

		res = WindowsUtil.exec(WindowsUtil.CmdType.C, "notepad");
		System.out.println(res);

		res = WindowsUtil.exec(WindowsUtil.CmdType.C, "java -version");
		System.out.println(res);
	}
}