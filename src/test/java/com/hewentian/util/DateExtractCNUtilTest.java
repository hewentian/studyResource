package com.hewentian.util;

import java.util.Date;

public class DateExtractCNUtilTest {
	public static void main(String[] args) throws Exception {
		// 朝鲜战争时间为：1950年6月25日—1953年7月27日
		DateExtractUtil de = new DateExtractCNUtil();
		System.out.println(de.getDate("中华人民共和国于1950年6月25日13时45分12秒将军队开进朝鲜", "yyyy年MM月dd日HH时mm分ss秒"));
		System.out.println(de.getDate("中华人民共和国于1950年6月25日13时45分将军队开进朝鲜", "yyyy年MM月dd日HH时mm分"));
		System.out.println(de.getDate("中华人民共和国于1950年6月25日13时45分将军队开进朝鲜", "yyyy年MM月dd日HH时"));
		System.out.println(de.getDate("中华人民共和国于50年6月25日13时45分将军队开进朝鲜", "yy年MM月dd日HH时"));
		System.out.println(de.getDate("中华人民共和国于1950-6-25 13:45:12将军队开进朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国于1950/6/25 13:45:12将军队开进朝鲜", ""));

		System.out.println("-----------------");
		System.out.println(de.getDate("中华人民共和国于1950年6月 25 日13时 45分 12 秒将军队开进朝鲜", " "));
		System.out.println(de.getDate("中华人民共和国于1950 年6月25 日13时45分将军队开进朝鲜", " "));
		System.out.println(de.getDate("中华人民共和国于1950年 6月25日13时将军队开进朝鲜", " "));
		System.out.println(de.getDate("中华人民共和国于1950年6月 25日将军队开进朝鲜", " "));
		System.out.println(de.getDate("中华人民共和国于1950年六 月25日将军队开进朝鲜", " ")); 

		System.out.println("-----------------");
		System.out.println(de.getDate("中华人民共和国于1953.7.27朝鲜战争结束", ""));
		System.out.println(de.getDate("中华人民共和国于1953-7-27朝鲜战争结束", ""));
		System.out.println(de.getDate("中华人民共和国于1953/7/27朝鲜战争结束", ""));
		System.out.println(de.getDate("中华人民共和国于19530727朝鲜战争结束", ""));
		System.out.println(de.getDate("中华人民共和国于1953|07|27朝鲜战争结束", ""));

		System.out.println("-----------------");
		System.out.println(de.getDate("中华人民共和国于一九四九年十月 初一日13时45分12秒成立", ""));
		System.out.println(de.getDate("中华人民共和国于一九四九年十月初一日成立", ""));
		System.out.println(de.getDate("中华人民共和国于一九四九年十月初一成立", ""));
		System.out.println(de.getDate("中华人民共和国于一九五三年一月初十日朝鲜战争", ""));
		System.out.println(de.getDate("中华人民共和国于一九五三年七月廿七日朝鲜战争结束", ""));
		System.out.println(de.getDate("二〇〇八年七月六日", ""));
		System.out.println(de.getDate("二〇零八年七月十日", ""));
		System.out.println(de.getDate("二〇〇8年七月初十日", ""));
		System.out.println("-----------------");
		System.out.println(de.getDate("中华人民共和国七月初十日13时45分12秒成立", ""));
		System.out.println(de.getDate("中华人民共和国7月10日", ""));
		System.out.println(de.getDate("中华人民共和国2016-6-12 10:50进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国06/13 14:08进入朝鲜", ""));

		System.out.println("-----------------");
		System.out.println(new Date() + "---------- now");
		System.out.println(de.getDate("中华人民共和国10 秒前进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国10分钟前进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国10小时前进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国一刻钟前进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国半小时前进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国昨天10:10进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国前天12:10进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国前一天10:10进入朝鲜", ""));
		System.out.println(de.getDate("中华人民共和国前两天12:10进入朝鲜", ""));
		
		System.out.println(de.getDate("丙申年 甲午月 丙寅日", "")); // 未提供处理方法，将输出null

		System.out.println("-----------------");

		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("一九五三", 1));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("一九五五", 1));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("五五", 1));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("一五", 1));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("二五", 1));
		System.out.println("-----------------");

		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("一", 2));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("十", 2));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("十一", 2));
		System.out.println("-----------------");

		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("初一", 3));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("初十", 3));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("十一", 3));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("二十", 3));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("廿一", 3));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("廿六", 3));
		System.out.println(DateExtractCNUtil.chinaNumToArabicNum("三十", 3));
		
		System.out.println(de.getDate("中華人民共和國于一九四九年十月 初一日13时45分12秒成立", ""));
		System.out.println(de.getDate("中華人民共和國于2016-06-13T15:03:28+08:00成立", ""));
	}
}