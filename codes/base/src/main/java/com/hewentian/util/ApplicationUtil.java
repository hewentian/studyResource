//package com.hewentian.util;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
///**
// * 
// * <p>
// * <b>ApplicationUtil</b> 是 用来获取applicationContext.xml中的bean的工具类
// * 在applicationContext.xml中要配置：<bean id="applicationUtil" class="com.dt.operate.util.ApplicationUtil" />
// * </p>
// * 
// * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
// * @date 2016年6月23日 下午5:56:22
// * @since JDK 1.7
// * 
// */
//public class ApplicationUtil implements ApplicationContextAware {
//	private static ApplicationContext applicationContext;
//
//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext)
//			throws BeansException {
//		ApplicationUtil.applicationContext = applicationContext;
//	}
//
//	public static Object getBean(String name) {
//		return applicationContext.getBean(name);
//	}
//}