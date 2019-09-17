<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<form id="pagerForm" method="post"
	onsubmit="return validateCallback(this, navTabAjaxDone)">
	<input type="hidden" name="keywords" value="${param.keywords}" /> <input
		type="hidden" name="pageNum" value="1" /> <input type="hidden"
		name="numPerPage" value="${page.numPerPage}" /> <input type="hidden"
		name="orderField" value="${param.orderField}" />
</form>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="<c:url value='/studentController/list.do?token=1'/>" method="post">
		<div class="searchBar">
			<table class="searchContent" width="30%">
				<tr>
					<td align="left">
						学生姓名：<input type="text" name="name"	value="${name}" />
								 <button style="margin-left: 20px;" type="submit">检索</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="<c:url value='/studentController/edit.do'/>"
				target="dialog" rel="addStudent" width="450" height="330" mask=true><span>添加</span>
			</a></li>
			<li class="line">line</li>

			<li><a class="edit"
				href="<c:url value='/studentController/edit.do?id={student_id}'/>"
				target="dialog" rel="updateStudent" width="450" height="330"
				mask=true><span>修改</span> </a></li>
			<li class="line">line</li>

			<li><a class="delete"
				href="<c:url value='/studentController/delete.do?id={student_id}&navTabId=studentList'/>"
				target="ajaxTodo" title="你确定删除此记录吗？"> <span>删除</span>
			</a></li>
			<li class="line">line</li>

		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="10"><input type="checkbox" group="uids" class="checkboxCtrl"></th>
				<th width="50">学生编号</th>
				<th width="50">姓名</th>
				<th width="50">年龄</th>
				<th width="50">生日</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${!empty list}">
				<c:forEach items="${list}" var="student" varStatus="s">
					<tr target="student_id" rel="${student.id}">
						<td><input name="uids" value="${student.id}" type="checkbox"></td>
						<td>${student.id}</td>
						<td>${student.name}</td>
						<td>${student.age}</td>
						<td><fmt:formatDate value="${student.birthday}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span> <select class="combox"
				name="numPerPage"
				onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="10">10</option>
				<option value="20"
					<c:if test="${page.numPerPage==20 }"> selected="selected"</c:if>>20</option>
				<option value="50"
					<c:if test="${page.numPerPage==50 }"> selected="selected"</c:if>>50</option>
				<option value="100"
					<c:if test="${page.numPerPage==100 }"> selected="selected"</c:if>>100</option>
				<option value="200"
					<c:if test="${page.numPerPage==200 }"> selected="selected"</c:if>>200</option>
			</select> <span>条，总共${page.totalCount}条</span>
		</div>

		<div class="pagination" targetType="navTab"
			totalCount="${page.totalCount}" numPerPage="${page.numPerPage}"
			pageNumShown="${page.pageNumShown}" currentPage="${page.pageNum}"></div>

	</div>
</div>
