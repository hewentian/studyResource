package com.hewentian.util;

/**
 * j-ui统一结果对象
 */
public class JuiResult {
	private String statusCode;// 状态码
	private String message;// 文字描述
	private String navTabId;
	private String rel;
	private String callbackType;// 是否关闭dwz打开的窗口
	private String forwardUrl;
	private String confirmMsg;
	private Object content;// 内容

	protected JuiResult() {
	}

	protected JuiResult(String message, String statusCode) {
		this(message, statusCode, null);
	}

	public JuiResult(String statusCode, String message, String callbackType,
			Object content) {
		this.statusCode = statusCode;
		this.message = message;
		this.callbackType = callbackType;
		this.content = content;
	}

	public JuiResult(String message, String statusCode, Object content) {
		this.message = message;
		this.statusCode = statusCode;
		this.content = content;
	}

	public JuiResult(String statusCode, String message, String navTabId,
			String callbackType, String forwardUrl) {
		this.statusCode = statusCode;
		this.message = message;
		this.navTabId = navTabId;
		this.callbackType = callbackType;
		this.forwardUrl = forwardUrl;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

}
