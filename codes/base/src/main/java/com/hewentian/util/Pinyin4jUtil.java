package com.hewentian.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hewentian.algorithm.Descartes;

/**
 * 
 * <p>
 * <b>Pinyin4jUtil</b> 是 拼音工具类
 * </p>
 * 
 * PinyinHelper类中的静态方法 toHanyuPinyinStringArray 返回的数据类型是一个 String 数组，<br />
 * 它用来接收一个汉字的多个发音，如果toHanyuPinyinStringArray 中的参数不是汉字，那么它会返回null。<br />
 * 
 * 其中使用HanyuPinyinOutputFormat来格式化返回拼音的格式还有例如以下几种： 
 * <ul>
 * <li>
 * 1.format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE); <br />
 * - WITH_V：用v表示ü (nv) <br />
 * - WITH_U_AND_COLON：用”u:”表示ü (nu:) <br />
 * - WITH_U_UNICODE：直接用ü (nü) 
 * </li>
 * <li>
 * 2.format.setCaseType(HanyuPinyinCaseType.LOWERCASE); <br />
 * - UPPERCASE：大写 (XING) <br />
 * - LOWERCASE：小写 (xing)
 * </li>
 * <li>
 * 3.format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK); <br />
 * - WITHOUT_TONE：无音标 (xing) <br />
 * - WITH_TONE_NUMBER：1-4数字表示英标 (xing2) <br />
 * - WITH_TONE_MARK：直接用音标符
 * </li>
 * </ul>
 *
 * <p>虽然pinyin4j很好用，但是还是有局限的。以上代码只能获取单个汉字的拼音，但是不能获取一个包含多音字的词的拼音。</p>
 *
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2017年6月5日 上午11:52:30
 * @since JDK 1.8
 *
 */
public class Pinyin4jUtil {
	private static Logger log = Logger.getLogger(Pinyin4jUtil.class);

	public static String[] getPinyin(String chinese) throws Exception {
		if (StringUtils.isBlank(chinese)) {
			return null;
		}

		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		char[] chineseChar = chinese.toCharArray();
		String[][] temp = new String[chinese.length()][];

		for (int i = 0; i < chineseChar.length; i++) {
			char c = chineseChar[i];

			if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
				temp[i] = PinyinHelper.toHanyuPinyinStringArray(chineseChar[i], hanYuPinOutputFormat);
			} else if ((c < 65 || c > 90) && (c < 97 || c > 122)) {
				temp[i] = new String[] { "" };
			} else {
				temp[i] = new String[] { String.valueOf(chineseChar[i]) };
			}
		}

		String[] pinyins = Descartes.getDescartes(temp);

		return pinyins;
	}

	public static String getAbbr(String chinese) {
		String abbr = "";
		char[] nameChar = chinese.toCharArray();

		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					abbr += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				abbr += nameChar[i];
			}
		}

		return abbr;
	}

	public static void example1() {
		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray('行');
		for (String s : pinyins) {
			log.info(s);
		}
	}

	public static void example2() throws BadHanyuPinyinOutputFormatCombination {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray('行', format);
		for (String s : pinyins) {
			log.info(s);
		}
	}

	public static void main(String[] args) throws Exception {
		example1();
		example2();

		log.info(getAbbr("行动"));

		String[] pinyins = getPinyin("行动");

		if (null != pinyins && pinyins.length != 0) {
			for (String pinyin : pinyins) {
				log.info(pinyin);
			}
		}
	}
}
