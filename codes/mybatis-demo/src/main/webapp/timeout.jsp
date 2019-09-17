<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
{
	"statusCode":"301", 
	"message":"用户登录超时，请重新 登录！", 
	"confirmMsg":"${param.callbackType eq 'forwardConfirm' ? message : ''}",
	"navTabId":"${empty navTabId ? param.navTabId : navTabId}", 
	"rel":"${param.rel}",
	"callbackType":"${empty callbackType ? param.callbackType : callbackType}",
	"forwardUrl":"<c:url value='/login.do' />"
}