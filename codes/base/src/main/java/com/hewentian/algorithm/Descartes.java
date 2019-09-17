package com.hewentian.algorithm;

import java.util.Arrays;

/**
 * 
 * <p>
 * <b>Descartes</b> 是 计算迪卡尔积 工具
 * </p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年6月5日 下午4:33:57
 * @since JDK 1.8
 *
 */
public class Descartes {

	/**
	 * 根据二维数组计算迪卡尔积, 以填充的方式实现，性能有待改进
	 * 
	 * @date 2017年6月5日 下午4:39:54
	 * @param array
	 *            二维数组
	 * @return String[] 结果数组
	 */
	public static String[] getDescartes(String[][] array) {
		if (null == array || array.length == 0) {
			return null;
		}

		// 首先计算目标数组大小
		int size = 1;
		for (int i = 0; i < array.length; i++) {
			size *= array[i].length;
		}

		// 创建目标数组并初始化
		String[] res = new String[size];
		Arrays.fill(res, "");

		// 开始计算产生结果
		for (int i = 0; i < array.length; i++) {
			String[] subArray = array[i];

			for (int j = 0, len = subArray.length; j < len; j++) {
				for (int k = 0; k < size; k++) {
					if ((k - j) % len == 0) {
						res[k] += subArray[j];
					}
				}
			}
		}

		return res;
	}

	public static void main(String[] args) {
		String[][] array = { { "A", "B", "C" }, { "D", "E" }, { "F", "G", "H" } };

		String[] res = getDescartes(array);

		for (String s : res) {
			System.out.println(s);
		}

		long startTime = System.currentTimeMillis();

		for (int i = 0; i < 1000; i++) {
			getDescartes(array);
		}

		long costed = System.currentTimeMillis() - startTime;
		System.out.println("cost: " + costed); // 10
	}
}

// output: /////////////////////////////////////////
// ADF
// BEG
// CDH
// AEF
// BDG
// CEH
// ADF
// BEG
// CDH
// AEF
// BDG
// CEH
// ADF
// BEG
// CDH
// AEF
// BDG
// CEH
///////////////////////////////////////////////////
