package com.hewentian.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hewentian.entity.SysUser;
import com.hewentian.util.IConst;

/**
 * 
 * <p>
 * <b>Interceptor</b> 是 拦截器
 * </p>
 * 
 * @author <a href="mailto:wentian.he@qq.com">hewentian</a>
 * @date 2016年8月26日 上午10:37:17
 * @since JDK 1.7
 * 
 */
public class Interceptor extends HandlerInterceptorAdapter {
	private static Logger log = Logger.getLogger(Interceptor.class);
	private List<String> uncheckUrls;

	/*
	 * 利用正则映射到需要拦截的路径
	 * 
	 * private String mappingURL;
	 * 
	 * public void setMappingURL(String mappingURL) { this.mappingURL =
	 * mappingURL; }
	 */
	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// log.info("==============执行顺序: 1、preHandle================");
		String requestUri = request.getRequestURI();
		if (log.isDebugEnabled()) {
			log.debug("requestUri=>" + requestUri);
		}
		// 是否使用拦截规则
		if (!IConst.USE_INTERCEPTOR.equals("y")) {
			return true;
		}
		// 一些指定url不拦截
		for (String s : uncheckUrls) {
			if (requestUri.contains(s)) {
				return true;
			}
		}

		// TODO 做拦截的事情

		return true;
	}

	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// log.info("==============执行顺序: 2、postHandle================");
		SysUser user = (SysUser) request.getSession().getAttribute(IConst.LOGIN_USER);
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			if (viewName != null) {
				if (user == null && !viewName.startsWith("login")) {
					// request.setAttribute("message", "用户登录超时，请重新登录！");
					// modelAndView.setView(new RedirectView("/login/logout.do",
					// true));
					modelAndView.setViewName("/timeout");

				}
			}
		}

	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// log.info("==============执行顺序: 3、afterCompletion================");
	}

	public List<String> getUncheckUrls() {
		return uncheckUrls;
	}

	public void setUncheckUrls(List<String> uncheckUrls) {
		this.uncheckUrls = uncheckUrls;
	}

}