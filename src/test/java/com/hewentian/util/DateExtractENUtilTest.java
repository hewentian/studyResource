package com.hewentian.util;

import java.util.Date;

public class DateExtractENUtilTest {
	public static void main(String[] args) throws Exception {
//		 Korean War Time is ：1950/6/25—1953/7/27
		DateExtractENUtil de = new DateExtractENUtil();
		System.out.println("----------------- 有 指定日期格式 -----------------");
		System.out.println(de.getDate("China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm:ss", -7));
		System.out.println(de.getDate("China on 21st June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm:ss", -7));
		System.out.println(de.getDate("China on 21 June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm:ss", -7));
		System.out.println(de.getDate("China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH:mm", -7));
		System.out.println(de.getDate("China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy HH", -7));
		System.out.println(de.getDate("China on 25th June, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy", -7));
		System.out.println(de.getDate("China on 25th Jun, 1950 13:45:12 drive the Army to Korea", "dd MMM, yyyy", -7));
		System.out.println(de.getDate("China on 25th Jun., 1950 13:45:12 drive feb. the Army to Korea.", "dd MMM., yyyy", -7));
		System.out.println(de.getDate("China on Jun. 25th,1950 13:45:12 drive the Army to Korea", "MMM dd,yyyy", -7)); // 故意产生异常

		System.out.println("\n----------------- 没有 指定日期格式：英式 -----------------");
		System.out.println(de.getDate("China on 25th June,1950 13:45:12 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on 25th Jun,1950 13:45:12 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on 25th June,1950 13:45 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on 25th June,1950 13 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on 25th June,1950 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("25th June,1950 13:45:12", "", -7));
		
		System.out.println("\n----------------- 没有 指定日期格式：美式 -----------------");
		System.out.println(de.getDate("China on June 25th,1950 13:45:12 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on Jun 25th,1950 13:45:12 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on June 25th,1950 13:45 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on June 25th,1950 13 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("China on June 25th,1950 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("June 25th,1950 13:45:12", "", -7));

		System.out.println("\n----------------- 没有 指定日期格式：其他 -----------------");
		System.out.println(de.getDate("China on 1953.7.27 end the Korea war", "", -7));
		System.out.println(de.getDate("China on 1953-7-27 end the Korea war", "", -7));
		System.out.println(de.getDate("China on 1953/7/27 end the Korea war", "", -7));
		System.out.println(de.getDate("China on 19530727 end the Korea war", "", -7));
		System.out.println(de.getDate("China on 1953|07|27 end the Korea war", "", -7));
		System.out.println(de.getDate("China on June 25th end the Korea war", "", -7));
		System.out.println(de.getDate("China on 25th  June  end the Korea war", "", -7));
		System.out.println(de.getDate("China on Jun 25 end the Korea war", "", -7));

		System.out.println("\n----------------- 没有 指定日期格式：中间有空格 -----------------");
		System.out.println(de.getDate("China on 25th   June,           1950    13:45:12 drive the Army to Korea", " ", -7));
		System.out.println(de.getDate("a12 June 2016 From the section Entertainment & Arts", "", -7));
		System.out.println(new Date() + " ------ now");
		System.out.println(de.getDate("he 12 seconds ago have lunch", "", -7));
		System.out.println(de.getDate("he twelve minutes ago have lunch", "", -7));
		System.out.println(de.getDate("he 12   hours    ago have lunch", "", -7)); // 故意写成不规则, 加一些空格
		System.out.println(de.getDate("he 1 hour ago have lunch", "", -7));
		System.out.println(de.getDate("he 1 day ago have lunch", "", -7));

		System.out.println("----------");
		System.out.println(de.getDate("he 1 week ago have lunch", "", -7));
		System.out.println(de.getDate("he 1 month ago have lunch", "", -7));
		System.out.println(de.getDate("he a month ago have lunch", "", -7));
		System.out.println(de.getDate("he one month ago have lunch", "", -7));
		System.out.println(de.getDate("he two minutes ago have lunch", "", -7));
		System.out.println(de.getDate("he two minutes ago have lunch", "", -6));
		
		System.out.println("----------");
		System.out.println(de.getDate("China on 25th June,1950 13:45:12 -0700 drive the Army to Korea", "", -7));
		System.out.println(de.getDate("China on 25th June,1950 13:45:12 -0700 drive the Army to Korea", "", -6));
		System.out.println(de.getDate("China on 2016-06-07 09:34 drive the Army to Korea", "", -6));
	}
}