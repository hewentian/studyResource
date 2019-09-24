<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
{
	"statusCode":"${statusCode}", 
	"message":"${param.callbackType ne 'forwardConfirm' ? message : ''}", 
	"confirmMsg":"${param.callbackType eq 'forwardConfirm' ? message : ''}",
	"navTabId":"${empty navTabId ? param.navTabId : navTabId}", 
	"rel":"${param.rel}",
	"callbackType":"${empty callbackType ? param.callbackType : callbackType}",
	"forwardUrl":"<c:url value='${empty forwardUrl ? param.forwardUrl : forwardUrl}' />"
}