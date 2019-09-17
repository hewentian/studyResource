<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
	<form class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone)"
		action="<c:url value='/studentController/save.do?navTabId=studentList&callbackType=closeCurrent'/>"
		method="post">
		<input type="hidden" name="oldId" value="${student.id}" />
		<div class="pageFormContent" layoutH="56">
			<dl>
				<dt>学生编号：</dt>
				<dd>
					<input type="text" name="id" class="required" minlength="1"
						maxlength="50" value="${student.id}" />
				</dd>
			</dl>
			<dl>
				<dt>姓名：</dt>
				<dd>
					<input type="text" name="name" class="required" minlength="1"
						maxlength="50" value="${student.name}" />
				</dd>
			</dl>
			<dl>
				<dt>年龄：</dt>
				<dd>
					<input type="text" name="age" class="required digits" minlength="1"
						maxlength="50" value="${student.age}" />
				</dd>
			</dl>
			<dl>
				<dt>生日：</dt>
				<dd>
					<input type="text" class="date" readonly="readonly"
						datefmt="yyyy-MM-dd HH:mm:ss" name="birthdayStr" class="required"
						value="<fmt:formatDate value='${student.birthday}' pattern='yyyy-MM-dd HH:mm:ss' />" />
				</dd>
			</dl>
		</div>

		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
