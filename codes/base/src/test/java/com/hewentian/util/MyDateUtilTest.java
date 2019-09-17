package com.hewentian.util;

import java.util.Date;

public class MyDateUtilTest {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		testCN();
		testEN();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public static void testCN() throws Exception {
		// 朝鲜战争时间为：1950年6月25日—1953年7月27日
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950年6月25日13时45分12秒将军队开进朝鲜", "yyyy年MM月dd日HH时mm分ss秒"));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950年6月25日13时45分将军队开进朝鲜", "yyyy年MM月dd日HH时mm分"));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950年6月25日13时45分将军队开进朝鲜", "yyyy年MM月dd日HH时"));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于50年6月25日13时45分将军队开进朝鲜", "yy年MM月dd日HH时"));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950 - 6- 25 13 : 45: 12将军队开进朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950/6/25 13:45:12将军队开进朝鲜", ""));

		System.out.println("-----------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950年6月 25 日13时 45分 12 秒将军队开进朝鲜", " "));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950 年6月25 日13时45分将军队开进朝鲜", " "));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950年 6月25日13时将军队开进朝鲜", " "));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950年6月 25日将军队开进朝鲜", " "));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1950年六 月25日将军队开进朝鲜", " "));

		System.out.println("-----------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1953.7.27朝鲜战争结束", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1953-7-27朝鲜战争结束", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1953/7/27朝鲜战争结束", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于19530727朝鲜战争结束", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于1953|07|27朝鲜战争结束", ""));

		System.out.println("-----------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于一九四九年十月 初一日13时45分12秒成立", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于一九四九年十月初一日成立", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于一九四九年十月初一成立", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于一九五三年一月初十日朝鲜战争", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国于一九五三年七月廿七日朝鲜战争结束", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "二〇〇八年七月六日", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "二〇零八年七月十日", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "二〇〇8年七月初十日", ""));
		System.out.println("-----------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国七月初十日13时45分12秒成立", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国7月10日", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国2016-6-12 10:50进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国06/13 14:08进入朝鲜", ""));

		System.out.println("-----------------" + new Date() + "---------- now");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国10 秒前进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国10分钟前进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国10小时前进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国一刻钟前进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国半小时前进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国昨天10:10进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国前天12:10进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国前一天10:10进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中华人民共和国前两天12:10进入朝鲜", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "4分钟前", ""));
		System.out.println(MyDateUtil.getDate("HK", "zh", 8, "7小时前", ""));
		System.out.println(MyDateUtil.getDate("HK", "zh", 8, "7小時前", ""));
		System.out.println(MyDateUtil.getDate("TW", "zh", 8, "21分钟前", ""));

		System.out.println("-----------------");
		System.out.println(DateCNUtil.chinaNumToArabicNum("一九五三", 1));
		System.out.println(DateCNUtil.chinaNumToArabicNum("一九五五", 1));
		System.out.println(DateCNUtil.chinaNumToArabicNum("五五", 1));
		System.out.println(DateCNUtil.chinaNumToArabicNum("一五", 1));
		System.out.println(DateCNUtil.chinaNumToArabicNum("二五", 1));
		System.out.println("-----------------");

		System.out.println(DateCNUtil.chinaNumToArabicNum("一", 2));
		System.out.println(DateCNUtil.chinaNumToArabicNum("十", 2));
		System.out.println(DateCNUtil.chinaNumToArabicNum("十一", 2));
		System.out.println("-----------------");

		System.out.println(DateCNUtil.chinaNumToArabicNum("初一", 3));
		System.out.println(DateCNUtil.chinaNumToArabicNum("初十", 3));
		System.out.println(DateCNUtil.chinaNumToArabicNum("十一", 3));
		System.out.println(DateCNUtil.chinaNumToArabicNum("二十", 3));
		System.out.println(DateCNUtil.chinaNumToArabicNum("廿一", 3));
		System.out.println(DateCNUtil.chinaNumToArabicNum("廿六", 3));
		System.out.println(DateCNUtil.chinaNumToArabicNum("三十", 3));

		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中華人民共和國于一九四九年十月 初一日13时45分12秒成立", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "中華人民共和國于2016-06-13T15:03:28+08:00成立", ""));
		
		System.out.println("-----------------------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "巴彦乌拉项目部开展入冬前设备检修工作", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "最冷酷的人 最冷酷的人 当前离线 积分 1648", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "最冷酷的人 当前离线", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "小黑屋手机版Archiver 京ICP备12032851号-1", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "点击数：2779774", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "发表于 2015-01-01", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015-01-01", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "Android Studio 2.2 来啦", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "u012804018", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "111111111111111", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2016年2月2日", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2-2", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2月2日", ""));
		
		System.out.println("------------ HK -----------------");
		System.out.println(MyDateUtil.getDate("HK", "zh", 8, "2016年10月9日 星期日 18:35", ""));
		System.out.println(MyDateUtil.getDate("HK", "zh", 8, "2016年10月7日 18:17 星期五", ""));
		System.out.println(MyDateUtil.getDate("HK", "zh", 8, "時間：2016-10-10 03:15:10", ""));
		System.out.println(MyDateUtil.getDate("HK", "zh", 8, "2016年10月09日 19:34", ""));
		System.out.println(MyDateUtil.getDate("HK", "zh", 8, "2016/10/10 星期一 12:17", ""));
		
		System.out.println("------------ TW -----------------");
		System.out.println(MyDateUtil.getDate("TW", "zh", 8, "2016.10.10 / 12:37", ""));
		System.out.println(MyDateUtil.getDate("TW", "zh", 8, "2016年10月10日 07:48:28  来源：中国台湾网", ""));
		System.out.println(MyDateUtil.getDate("TW", "zh", 8, "發表日期：2016/10/10", ""));
		System.out.println(MyDateUtil.getDate("TW", "zh", 8, "2016年10月10日 下午14:15", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2012年06月15日17:37来源：龙虎网-金陵晚报", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2010-01-22 20:22:07　来源: 新华网　跟贴 0 条 手机看股票", ""));
		
		System.out.println("------------ NEW -----------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "发表于 2012-10-24 10:36:52", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "发表于 2012-10-29 12:08:54?|只看该作者", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "发表于 2014-5-19 00:00:08?|只看该作者?|倒序浏览", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015-07-14 23:02:27 归档在", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015-03-19 07:22:51来源", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "	2008年04月29日 07:20", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "		2010年06月10日 21:05", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015年01月16日 09:39 来源：凤凰财经", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2016-10-20 22:04:08 来源：政知圈", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "阿咩 2016-03-18 08:46:56", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2010-02-10 17:14:48　来源: 经济观察报(北京)　跟贴 0 条 手机看股票", ""));
		
		System.out.println("------------ NEW2 -----------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "张家界旅游2016-01-17 10:28:32阅读(3346) 评论", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "时间：2006年09月21日11:54", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2009年03月30日18:57", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "来源：新华网 2011年09月20日08:26", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2012年10月12日11:11来源：环球网", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015年04月29日17:24", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2016-10-19 09:20:40　来源: 网易体育", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2011年01月17日08:27", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2012年02月13日11:04 来源：京华时报", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015-03-19 09:34:23 来源：搜狐体育 作者：晨晨", ""));
		
		System.out.println("------------ NEW3 -----------------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2016年04月23日 16:15 来源：FX168财经网 用微信扫描二维码 分享至好友和朋友圈 人参与 评论", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2016年04月23日 16:15 来源：FX168财经网", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2016年4月18日", ""));
		
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015年03月18日 05:38 来源：中国证券报 作者：张玉洁 用微信扫描二维码 分享至好友和朋友圈 人参与 评论", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015年03月18日 05:38 来源：中国证券报 作者：张玉洁", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015年03月18日 05:38", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "319116 8 分析：空头警报未除 7月17日可能将成", ""));
		
		System.out.println("--------");
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2012-10-14 09:49:21　来源: 新华网(广州)　有0人参与", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "浙江:舟山港综合保税区获批成立 2012/10/12", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "国务院正式批复设立舟山港综合保税区 2012/10/12", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "舟山港综合保税区获批 关键规划尚无进展 2012/10/12", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "舟山群岛新区规划仍在报批 2012/10/12", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2012/10/12", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "125楼星民 发表于 2012-7-9 14:35:10 |只看该作者", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "电梯直达 楼主 发表于 2016-9-3 20:02:16 来自手机 |只看该作者 |倒序浏览", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "漳州农产品寻求突围 慧聪网产业带助力转型 复制链接 打印 大 中 小 2015年01月09日10:57 | 我来说两句(人参与) | 保存到博客", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015年01月09日10:57 | 我来说两句(人参与) | 保存到博客", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "2015年01月09日10:57", ""));
		System.out.println(MyDateUtil.getDate("CN", "zh", 8, "北极星电力网新闻中心  我要投稿  2013/10/18 10:34:33   北极星电力网", ""));
	}

	public static void testEN() throws Exception {
		System.out.println("----------------- 有 指定日期格式 -----------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm:ss"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 21st June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm:ss"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 21 June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm:ss"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th Jun, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th Jun., 1950 13:45:12 drive feb. the Army to Korea.", "dd MMM., yyyy"));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on Jun. 25th,1950 13:45:12 drive the Army to Korea", "MMM dd,yyyy")); // 故意产生异常

		System.out.println("\n----------------- 没有 指定日期格式：英式 -----------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June,1950 13:45:12 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th Jun,1950 13:45:12 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June,1950 13:45 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June,1950 13 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June,1950 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "25th June,1950 13:45:12", ""));

		System.out.println("\n----------------- 没有 指定日期格式：美式 -----------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on June 25th,1950 13:45:12 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on Jun 25th,1950 13:45:12 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on June 25th,1950 13:45 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on June 25th,1950 13 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on June 25th,1950 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "June 25th,1950 13:45:12", ""));

		System.out.println("\n----------------- 没有 指定日期格式：其他 -----------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 1953.7.27 end the Korea war", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 1953-7-27 end the Korea war", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 1953/7/27 end the Korea war", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 19530727 end the Korea war", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 1953|07|27 end the Korea war", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on June 25th end the Korea war", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th  June  end the Korea war", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on Jun 25 end the Korea war", ""));

		System.out.println("\n----------------- 没有 指定日期格式：中间有空格 -----------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th   June,           1950    13:45:12 drive the Army to Korea", " "));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "a12 June 2016 From the section Entertainment & Arts", ""));
		System.out.println(new Date() + " ------ now");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he 12 seconds ago have lunch", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he twelve minutes ago have lunch", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he 12   hours    ago have lunch", "")); // 故意写成不规则,
																										// 加一些空格
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he 1 hour ago have lunch", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he 1 day ago have lunch", ""));

		System.out.println("----------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he 1 week ago have lunch", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he 1 month ago have lunch", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he a month ago have lunch", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he one month ago have lunch", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "two minutes ago", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "he two minutes ago have lunch", ""));

		System.out.println("----------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June,1950 13:45:12 -0700 drive the Army to Korea", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 25th June,1950 13:45:12 -0700 drive the Army to Korea", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 2016-06-07 09:34 drive the Army to Korea", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "China on 4:10 PM 08/25/2016 drive the Army to Korea", ""));
		
		System.out.println("-----------------------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "abcdfjefij ago ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "cold person age  1648", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "coldest person age ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Archiver  12032851 backup -1", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Posted 07/24/2015", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Mr Yasuaki Yamashita, “Nagasaki, 9.08.1945”", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Edit 7/18/16", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "11/08/2016, 9:52pm", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "8/29/16 11:50am", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "08/26/2016 07:30 am ET", ""));
		
		System.out.println("-----------------------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "July 2015", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "June 29, 2016 at 2:17 pm", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "March 22, 2:45 am", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "09:46, UK, Friday 19 August 2015", ""));
		
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Android Studio 2.2 coming", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "u012804018", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "111111111111111", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2-2", ""));
		System.out.println(MyDateUtil.getDate("CA", "en", -7, "July 2015", ""));
		System.out.println(MyDateUtil.getDate("CA", "en", -7, "2-2", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Rajneesh Gupta | September 14, 2005 17:50 IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Sep 6, 2010 10:37 GMT  ·  By Marius Oiaga  ·  Share: ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016/10/18 下午3:48:56", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Feb 2, 2013 02:01 GMT \u00A0·\u00A0 By Cosmin Vasile \u00A0·\u00A0 Share:\u00A0", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "PTI | Sep 30, 2016, 09.43 AM IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Updated: Yesterday  10:37   a.m.", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Updated:   10:37   a.m.", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "TNN | Updated: Oct 18, 2016, 03:33 IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Tuesday 18 October 2016 13:24 BST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct. 28, 2013 at 4:40 PM", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Tuesday 30 August 2016 08:45 BST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "08/26/2016 07:30 am EST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Wednesday, October 19, 2016 2:30PM EDT   ", ""));
		
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Wednesday 19 October 2016 16:45 BST|  ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Thursday, Mar. 22, 2012 7:59AM EDT    ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "March 22, 2008 at 1:58 PM    ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "4:35 p.m. EDT October 7, 2016 ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "17:06 19.10.2016   ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "		Oct 2, 2016, 20:12 IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Apr 20, 2010 14:12 GMT", ""));

		System.out.println("-----------------------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct 19, 2016, 3:15 PM ET", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Monday, October 17, 2016 5:46", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "3:16 PM 02/20/2015 ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Mar 26, 2013 07:38 GMT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "October 19, 2016, 05:28 pm ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct 11, 2016, 08.09AM IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct 7, 2016, 08.58 AM IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "	Wednesday 24 December 2014", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Friday 7 August 2015", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "10/20/2016 00:15", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct 19 2016, 3:18 pm ET ", ""));//TODOOK
		System.out.println(MyDateUtil.getDate("US", "en", -7, "6/29/2005 8:01:24 AM ET ", ""));//TODOOK
		System.out.println(MyDateUtil.getDate("US", "en", -7, "October 20, 201610:24am", ""));//TODOOK
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Wednesday, Jul. 24, 2013 5:00AM EDT ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "October 18, 2016 23:28 IST ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Jan. 11, 2006 at 12:50 PM ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "19 Oct, 2016 19:16", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "19-Oct-16", ""));//TODOOK
		
		System.out.println("-----------------------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Monday, October 17, 2016 15:22", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "October 19, 2016, 8:00 PM EDT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Aug 16, 2013 14:51 GMT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "09:28, UK,Thursday 20 October 2016 ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct 18, 2016, 10:30 IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct 11, 2016, 06.57 AM IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "8/27/2012 8:06 AM", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "WASHINGTON, Oct. 19, 2016 ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Wednesday 5 October 2016 14:20 BST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Thursday 30 April 2015", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct 19 2016, 5:52 pm ET", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Oct. 02, 2016", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016/9/9", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "	October 18th, 2016", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "October 18, 2016 23:28 IST    ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Last updated: Oct 5th 2016, 14:25 GMT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Updated: October 21, 2016 05:36 IST", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Aug. 23, 2008 at 8:59 AM Follow @upisports", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "LAST UPDATED : 22 Oct 2016, 10:13 IST ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Updated 1/20/2012 9:59 AM ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "July 18, 2011 at 10:35 AM", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-10-24T16:09:30.020Z", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2013-05-24T21:15:00CDT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-10-25T00:00:00-0700", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-10-26T17:41:48+01:00", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-10-26T10:56:26.000-04:00", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-10-28T12:09:01.000+11:00", ""));
		
		System.out.println("--------------------------");
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Published Friday, Apr. 13, 2012 12:14PM EDT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Friday, Apr. 13, 2012 12:14PM EDT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Last updated Friday, Apr. 13, 2012 1:38PM EDT", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Friday, Apr. 13, 2012 1:38PM EDT", ""));
		
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Apr 26, 2006 11:12 GMT  ·  By Carmen Ivanov  ·  Share: ", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Flatpak 0.0.13", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Flatpak 1.16.13", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Flatpak 10.31.13", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Photos of the day - October 14, 2016", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Sept. 27, 2014 at 1:55 AM Follow @upisports", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "RTQuestion more live 02:47 GMT, Oct 28, 2016 search Menu mobile", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "By SHAUN WATERMAN, UPI Homeland and National Security Editor   |   Dec. 7, 2006 at 7:32 AM Follow @upi", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Posted: Yesterday 4:37 p.m.", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-10-29T06:22", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "09:28, 20 October 2016", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "This one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen 09:38, 20 October 2016 This one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen 09:38, 20 October 2016 This one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen 09:38, 20 October 2016 This one two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen sixteen seventeen eighteen nineteen 09:38, 20 October 2016", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "November 1, 20161:22pm", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-11-01T12:45:08.000+11:00", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "Updated 0104 GMT (0904 HKT) November 1, 2016", ""));
		System.out.println(MyDateUtil.getDate("US", "en", -7, "2016-11-01T06:26:23Z", ""));
	}
}