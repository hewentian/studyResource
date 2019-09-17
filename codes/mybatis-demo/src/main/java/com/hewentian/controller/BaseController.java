package com.hewentian.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台基类
 */
public abstract class BaseController {
	// 日志
	private static Logger logger = Logger.getLogger(BaseController.class);

	/**
	 * 返回json数据
	 * 
	 * @param statusCode
	 *            状态
	 * @param message
	 *            提示
	 * @param forwardUrl
	 *            跳转页面
	 * @return
	 */
	protected ModelAndView ajaxDone(int statusCode, String message, String navTabId) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		mav.addObject("navTabId", navTabId);
		return mav;
	}

	/**
	 * 返回json数据
	 * 
	 * @param statusCode
	 *            状态
	 * @param message
	 *            提示
	 * @param forwardUrl
	 *            跳转页面
	 * @return
	 */
	protected ModelAndView ajaxDone(int statusCode, String message, String forwardUrl, String navTabId) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		mav.addObject("forwardUrl", forwardUrl);
		if (forwardUrl != null && !forwardUrl.trim().equals(""))
			mav.addObject("callbackType", "forward");
		mav.addObject("navTabId", navTabId);
		return mav;
	}

	/**
	 * 返回json数据
	 * 
	 * @param statusCode
	 *            状态
	 * @param message
	 *            提示
	 * @param forwardUrl
	 *            跳转页面
	 * @param isCloseCurrent
	 *            是否关闭当前窗口
	 * @return
	 */
	protected ModelAndView ajaxDone(int statusCode, String message, String forwardUrl, boolean isCloseCurrent, String navTabId) {
		ModelAndView mav = new ModelAndView("ajaxDone");
		mav.addObject("statusCode", statusCode);
		mav.addObject("message", message);
		mav.addObject("forwardUrl", forwardUrl);
		if (isCloseCurrent) {
			mav.addObject("callbackType", "closeCurrent");
		}
		mav.addObject("navTabId", navTabId);
		return mav;
	}

	protected ModelAndView ajaxDoneSuccess(String message, boolean isCloseCurrent) {
		return ajaxDone(200, message, "", isCloseCurrent, "");
	}

	protected ModelAndView ajaxDoneSuccess(String message, String navTabId) {
		return ajaxDone(200, message, "", true, navTabId);
	}

	protected ModelAndView ajaxDoneSuccess(boolean isCloseCurrent, String navTabId) {
		return ajaxDone(200, "操作成功", "", isCloseCurrent, navTabId);
	}

	protected ModelAndView ajaxDoneSuccess(boolean isCloseCurrent, String navTabId, String forwardUrl) {
		return ajaxDone(200, "操作成功", forwardUrl, isCloseCurrent, navTabId);
	}

	protected ModelAndView ajaxDoneSuccess(String message) {
		return ajaxDone(200, message, "");
	}

	protected ModelAndView ajaxDoneError(String message) {
		return ajaxDone(300, message, "");
	}

	protected ModelAndView ajaxDoneError(String message, boolean isCloseCurrent) {
		return ajaxDone(403, message, "", isCloseCurrent, "");
	}

	protected ModelAndView ajaxDoneSuccess() {
		return ajaxDone(200, "操作成功！", "");
	}

	protected ModelAndView ajaxDoneError() {
		return ajaxDone(300, "操作失败！", "");
	}

	protected ModelAndView ajaxDoneParamError() {
		return ajaxDone(300, "参数错误！", "");
	}

	protected ModelAndView ajaxDoneSystemError() {
		return ajaxDone(300, "系统内部错误！", "");
	}

	public ModelAndView exception(Exception e, HttpServletRequest request) {
		e.printStackTrace();
		logger.error("异常：" + this.getClass().getName(), e);
		request.setAttribute("exception", e);

		if (request.getParameter("ajax") != null) {
			return ajaxDoneError(e.getMessage());
		}

		ModelAndView mav = new ModelAndView("error/error");
		mav.addObject("statusCode", 300);
		mav.addObject("message", e.getMessage());

		return mav;
	}
}
